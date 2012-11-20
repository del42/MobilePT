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

public class ExerciseBriefingActivity extends Activity implements OnClickListener {
	private TextView exerciseName;
	private Button buttonHowTo;
	private Button buttonStart;
	
	private String sPassed; //this variable will hold the exercise name passed from prev act
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutexercisebriefing);
        
        exerciseName = (TextView) findViewById(R.id.exerciseName);
        buttonHowTo = (Button) findViewById(R.id.buttonHowTo);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        
        sPassed = this.getIntent().getStringExtra("exerciseNameFromIntent");
        
        exerciseName.setText(sPassed);
        
        buttonHowTo.setOnClickListener(this);
        buttonStart.setOnClickListener(this);
    }
    

    public void onClick(View v) {
    	// this line will basically eliminate the white spaces from the exercise name
    	// so that we can use the sTrimmed String for calling the other activities/data associated
    	// with the exercise we want
    	String sTrimmed = sPassed.replaceAll("\\s+", "");
    	try {
    		Intent intent;
    		
	    	switch (v.getId()) {
	    	case R.id.buttonStart:
	    		//put logic to call ExerciseMenuActivity here
	    		intent = new Intent(this, 
	    				Class.forName("mobilePT.activities.exercises." + sTrimmed + "Activity"));
	    		intent.putExtra("exerciseNameTrimmedFromIntent", sTrimmed );
	    		startActivity(intent);
	    		break;
	    	case R.id.buttonHowTo:
	    		//put logic to call ProfileMenuActivity here
	    		intent = new Intent(this, 
	    				Class.forName("mobilePT.activities.howto." + sTrimmed + "HowToActivity"));
	    		intent.putExtra("exerciseNameTrimmedFromIntent", sTrimmed );
	    		startActivity(intent);
	    		break;
	    	}
    	}
    	catch (ClassNotFoundException e) {
    		exerciseName.setText("OMG NOT FOUND");
    	}
    }
}
