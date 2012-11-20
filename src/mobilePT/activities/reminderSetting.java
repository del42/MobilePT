/**
 * @author Jude Morrissey Cruz and Delgersuren Bold
 * 			San Francisco State University, Fall 2011, CSC 780
 */
package mobilePT.activities;

import java.io.File;
import java.util.Calendar;



import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


/**
 * This part helps us to make set the reminder for the exercise, 
 * so the user can can reminded even if the they are not running the application
 * Part of this demonstration calls the AlarmService_Service class which is the google open source 
 * code to help us set the real time alarm on the monbile device.
 */
public class reminderSetting extends Activity implements OnClickListener{
    private PendingIntent mAlarmSender;
    private Button button1;
    private Button button2;
    private AlarmManager am;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);
        
        // Create an IntentSender that will launch our AlarmService_Service(form google open source), 
        // to set schedule an device alarm
        mAlarmSender = PendingIntent.getService(reminderSetting.this,
                0, new Intent(reminderSetting.this, AlarmService_Service.class), 0);

       
        //buttons corresponds to alarm on or off
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);
    }

        public void onClick(View v) {
        	switch (v.getId()) {
        	//buttons clicks corresponds to alarm on or off
        	case R.id.button1:
	            // Alarm will last 3 seconds long from this point of time
	            long currentElapsedTime = SystemClock.elapsedRealtime();
	            // turn on the alarm
	            am = (AlarmManager)getSystemService(ALARM_SERVICE);
	            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,currentElapsedTime, 3000, mAlarmSender);
	            //simple button switch, if on button clicked on button is transparent
	            button1.setBackgroundColor(Color.TRANSPARENT);
	            button2.setBackgroundColor(Color.GRAY);
	            // toast will notify the user that alarm is on
	            Toast.makeText(reminderSetting.this, R.string.alarm_on,Toast.LENGTH_LONG).show();
	            break;
	            
        	case R.id.button2:
	            // turn off the alarm
	            am = (AlarmManager)getSystemService(ALARM_SERVICE);
	            am.cancel(mAlarmSender);
	            //simple button switch,if on button clicked off button is transparent
	            button2.setBackgroundColor(Color.TRANSPARENT);
	            button1.setBackgroundColor(Color.GRAY);
	            // toast will notify the user that alarm is off
	            Toast.makeText(reminderSetting.this, R.string.alarm_off,Toast.LENGTH_LONG).show();
	            break;
        	}
}
}

