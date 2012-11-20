/**
 * @author Jude Morrissey Cruz and Delgersuren Bold
 * 			San Francisco State University, Fall 2011, CSC 780
 */

package mobilePT.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/** IMPORTANT NOTE (Expand me!!) 
 *  in order to compile with the WEKA API, the heap size must be increased
 *  to accommodate building with the external JAR file provided
 *  
 *  the JAR file is "wekaSTRIPPED.jar" -- please add this file to the build path
 */

/** this is a reminder that all new activities MUST be registered with the manifest! */
public class MobilePTActivity extends Activity implements OnClickListener {
    /** all needed view elements, declare them here */
	private Button buttonExercise;
	private Button buttonProfile;
	private Button buttonHowTo;
	private TextView textTitle;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutmain);
        
        buttonExercise = (Button) findViewById(R.id.buttonExercise);
        buttonProfile = (Button) findViewById(R.id.buttonProfile);
        buttonHowTo = (Button) findViewById(R.id.buttonHowTo);
        textTitle = (TextView) findViewById(R.id.textTitle);
        
        buttonExercise.setOnClickListener(this);
        buttonProfile.setOnClickListener(this);
        buttonHowTo.setOnClickListener(this);
    }
    
    /**
     * this onClick function will do a check of which button was pressed by
     * checking which button is referenced by the passed View variable onto
     * this method, then perform an action for whatever button was pressed
     */
    public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.buttonExercise:
    		//put logic to call ExerciseMenuActivity here
    		startActivity(new Intent(this, ExerciseActivity.class));
    		break;
    	case R.id.buttonProfile:
    		//put logic to call ProfileMenuActivity here
    		startActivity(new Intent(this, ProfileActivity.class));
    		break;
    	case R.id.buttonHowTo:
    		//put logic to call HowToMenuActivity here
    		startActivity(new Intent(this, HowToActivity.class));
    		break;
    	}
    }
    
}