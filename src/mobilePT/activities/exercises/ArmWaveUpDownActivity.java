/**
 * @author Jude Morrissey Cruz and Delgersuren Bold
 * 			San Francisco State University, Fall 2011, CSC 780
 */

package mobilePT.activities.exercises;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import mobilePT.activities.MediaObj;
import mobilePT.activities.R;
import mobilePT.decisionTrees.DecisionTreeHelperFunctions;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class ArmWaveUpDownActivity extends Activity implements SensorEventListener {
	
	/** global variables go here */
	private String sTrimmedExerciseName = "";
	private String sDecisionTreeFileName = "";
	
	// these are the strings that will contain the filepath(s) to the sound files
	// that will be played for feedback, and will be initialized in the helper function
	// initializeVariables()
	private String sNormalSoundFilePath = "";
	private String sFastSoundFilePath = "";
	private String sSlowSoundFilePath = "";
	
	// this is the decisionTree object, but we have to deserialize the supplied file first
	private Classifier dTree;
	
	// this FastVector of Attributes will contain all of the possible classifications for
	// the current exercise, such as "ArmWaveUpDown", "ArmWaveUpDownTooFast", etc
	// note that we have to use FastVector because its what the API supports
	private FastVector<String> classList;
	
	// and this FastVector contains the attributes, meaning the variables, plus
	// the list of possible classifications appended at the end
	// note that both FastVectors will be initialized with the initializeVariables() method
	private FastVector<Attribute> featureV;
	
	// this Instances object will hold all accelerometer data per a given time period
	// by means of storing snapshots of accelerometer data in an Instance object (note
	// the singular form, not plural), then adding that Instance to accelData
	private Instances accelData;
	
	// this Timer object will be used to do an ontime event that runs every n seconds to
	// classify all the accelerometer data collected during that time period
	private Timer accelTimer;
	
	// in milliseconds, this is the time interval in between executions of the timed method
	private final int iInterval = 5000;
	
	// the sensorManager will be used to retrieve accelerometer values
	private SensorManager sensorManager;
	
	// the powerManager will be used to prevent the phone from sleeping so the
	// sensors can keep on working without user intervention
	private PowerManager powerManager;
	// and this WakeLock object will acquire the lock to prevent sleeping
	private PowerManager.WakeLock wakeLock;
	
	// the MediaObj object is a helper object from Del's audio and haptic feedback
	// API, which has functions that simplifies the audio feedback calls
	private MediaObj mediaObj;
	// MediaPlayer object will be used for handling audio feedback
	private MediaPlayer mediaPlayer;
	// Vibrator object will be used for handling haptic feedback
	private Vibrator vibrator;
	
	
	//------------------- ONCREATE METHOD ------------------------------
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutexerciseinprogress);
        
        //initialize the necessary objects
        initializeVariables();
        
        try {
        	// this line simplifies the deserializing process and reads the decision tree
        	// bytestream object, cast it to a weka classifier to be used in the timed logic
        	dTree = (Classifier) weka.core.SerializationHelper.read(sDecisionTreeFileName);
        }
        catch (ClassNotFoundException e) {
        	Log.e("deserializeObject", "class not found error", e);
        }
        catch (Exception e) {
        	Log.e("deserializeObject", "general error", e);
        }
        
        // set up the timer
 		accelTimer = new Timer();

 		// schedule the task to be executed by the timer every n seconds
 		accelTimer.schedule(new TimerTask() {
 			@Override
 			public void run() {
 				timerMethod();
 			}
 		}, 1000, iInterval);
    }
    
    
    //------------- TIMER RELATED METHODS ------------------------
    /**
     * this method basically runs the Runnable getAverageAccelValues everytime
     * we hit the specified time interval
     */
    private void timerMethod() {
		this.runOnUiThread(getAverageAccelValues);
	}
    
    /**
	 * this runnable defines the action that will be performed everytime the timer
	 * reaches its specified interval
	 */
	private Runnable getAverageAccelValues = new Runnable() {
		// define the run() method for this runnable
		public void run() {
			// the following lines of code are only for debugging purposes
			Date dt = new Date();
			int hours = dt.getHours();
			int minutes = dt.getMinutes();
			int seconds = dt.getSeconds();
			String curTime = hours + ":" + minutes + ":" + seconds;
				
			// classify the instances by calling the method helper classifySet()
			ArrayList<ArrayList<String>> aClassified = DecisionTreeHelperFunctions.
									classifySet(accelData, dTree, featureV, classList);
			
			// reinitialize the instances set, so that we will have a new collection
			// of Instance objects for the next n seconds until the next timed event
			accelData = new Instances("accelData", featureV, 1);
			accelData.setClassIndex(featureV.size()-1);
		
			// finally, make the utility text view display the classification of the data
			// along with the new current time
			// this is for debugging purposes only!! 
			TextView txtv1 = (TextView) findViewById(R.id.textView1);
			txtv1.setText(aClassified + "\n" + curTime);
			
			// pass the ArrayList to the function that will process for feedback
			processFeedback(aClassified);
		}
	};
    
	
    //-------------------- SENSOR RELATED CODE ----------------------------
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// wont be implemented
	}

	/**
	 * everytime the sensor value changes (for the accelerometer), we add the current
	 * readings as one instance into the accelData Instances object -- keep on doing
	 * this until the timer times out and evaluates the current collected dataSet
	 * against the tree
	 */
	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			int count = sensorEvent.values.length+1;
			
			Instance instance = new DenseInstance(count);
			
			try {
				for (int i = 0; i < count-1; i++) {
					instance.setValue( (Attribute) featureV.elementAt(i), sensorEvent.values[i]);
				}
				// add the class
				instance.setValue( (Attribute) featureV.elementAt(count-1),
						"\"M\"");
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
	
			accelData.add(instance);
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Register this class as a listener for the accelerometer sensor
		sensorManager.registerListener(this, sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);
	}

	/**
	 * when the activity is all done, we have to release the wakeLock so the
	 * phone wont be an insomniac and stay on all the time
	 */
	@Override
	protected void onDestroy() {
		wakeLock.release();
		sensorManager.unregisterListener(this);
		super.onDestroy();
	}
	
	
	//-------------------- HELPER METHODS ------------------------
	/**
	 * this is a helper method to initialize necessary (but less dynamic) variables 
	 * such as the featureVector, etc, centralized into one method
	 * 
	 * basically this code is a mimic of the CreateClassifier method inside DataMiner
	 * of the original implementation based off of Jogger's Music App,
	 * created by Hunvil and Tilila, and then some
	 */
	private void initializeVariables() {
		// first and foremost lets get/set the accelerometer sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);
        
        // now lets get and set the stay awake manager
        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "AntiSleeping");
        wakeLock.acquire();
        
        // the following line will retrieve the exerciseName extra from the intent
        sTrimmedExerciseName = this.getIntent().getStringExtra("exerciseNameTrimmedFromIntent");
        
        // now setup the filenames to be accessed
        sDecisionTreeFileName = Environment.getExternalStorageDirectory() +
        		"/MobilePT/dTrees/" + sTrimmedExerciseName + "Tree.dat";
		
		// this variable is the number of attributes (variables) PLUS the classification label
        // essentially the number of columns in the CSV dataset
		int count = 4;
		
		// create an array list of the size of the number of attributes in the
		// set (number of variables columns in dataset)
		ArrayList<Attribute> attributes = new ArrayList<Attribute>(count - 1);

		// Initialize the name of the attributes
		// attributes are the variables per instance, (X, Y, Z)
		// so if we have more variables, say the CSV file includes calculated
		// velocity, we have to consider those too, but the count variable if made
		// dynamic by actually parsing the CSV files for the number of variable columns
		// will take care of this -- however we only consider three variables for now,
		// and also for our implementations the CSV file is not needed (offline tree gen)
		for (int i = 0; i < count - 1; i++) {
			Attribute A = new Attribute(String.valueOf(i));
			attributes.add(A);
		}

		// declare the different classifications available (different labels)
		// this one does not necessarily need to have only the available labels/
		// classifications in the generated tree
		FastVector<String> vTempClasses = DecisionTreeHelperFunctions.initializeClassifierVector();
		
		// populate the classList vector with the vclasses elements
		// these elements are the different classes to be expected in the dataset
		// passed for the decision tree genesis
		classList = new FastVector<String>();
		for (int i = 0; i < vTempClasses.size(); i++) {
			classList.add(vTempClasses.get(i));
		}
		
		Attribute classAttribute = new Attribute("the class", vTempClasses);

		// fill the feature vector with the data from the elements in the set
		featureV = new FastVector<Attribute>(count);

		for (int i = 0; i < count - 1; i++) {
			featureV.addElement(attributes.get(i));
		}
		featureV.addElement(classAttribute);
		
		// initialize the accelData Instances object
        accelData = new Instances("accelData", featureV, 1);
		accelData.setClassIndex(featureV.size()-1);
		
		// initialize the MediaObj API helper object
		mediaObj = new MediaObj();
		
		// initialize the necessary soundFilePaths here
		sNormalSoundFilePath = Environment.getExternalStorageDirectory()
				+ "/MobilePT/audio/normal.wav";
		sFastSoundFilePath = Environment.getExternalStorageDirectory()
				+ "/MobilePT/audio/fast.wav";
		sSlowSoundFilePath = Environment.getExternalStorageDirectory()
				+ "/MobilePT/audio/slow.wav";
		
		// initialize and get the vibrator system service
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	}
    
    
	/**
	 * this helper function serves as a centralized function to perform the
	 * necessary feedbacks for specific actions, such as when the user is moving
	 * too fast, too slow, etc
	 * 
	 * @param aClassified is the ArrayList of ArrayLists of Strings that contains
	 * 			the found classifications and the percentages of the data classified
	 */
	private void processFeedback(ArrayList<ArrayList<String>> aClassified) {
		// need to reinitialize mediaPlayer every call to let it play different sounds.
		mediaPlayer = new MediaPlayer();
		
		for (int index = 0; index < aClassified.size(); index++) {
			String classification = aClassified.get(index).get(0);
			
			// enter this if-block if the current ArrayList being inspected
			// is the ArrayList with the data for the TooFast motion
			if (classification == "ArmWaveUpDownTooFast") {
				// get the percentage of the TooFast motion
				float fTemp = Float.parseFloat(aClassified.get(index).get(1));
				
				// enter this if statement if the decision tree finds that more
				// than 70% of the collected data is classified as moving too fast
				if (fTemp > 0.70) {
					mediaObj.playSound(mediaPlayer, sFastSoundFilePath);
					mediaObj.vibrate(vibrator);
				}
				// enter this one otherwise (normal movement)
				else {
					mediaObj.playSound(mediaPlayer, sNormalSoundFilePath);
				}
				
				// since we have done the feedback for the collected data in
				// the past interval, no need to iterate further, so break
				break;
			}
		}
	}
	
}
