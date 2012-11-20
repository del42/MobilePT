/**
 * @author Jude Morrissey Cruz and Delgersuren Bold
 * 			San Francisco State University, Fall 2011, CSC 780
 */

package mobilePT.activities;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProfileActivity extends Activity implements OnClickListener {
	private Button button1;
	private Button button2;
	private Button button3;
	  private Vibrator vib;
		private MediaPlayer mp;
		private File sdCard;
		private String pathNormal;
		private String pathSlow;
		private String pathFast;
		private String pathBeep;
	/** Called when the activity is first created. */
    @Override
    
    
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutprofilemenu);
        
        button1 = (Button) findViewById(R.id.HeyButton);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button1);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button2);
        button3.setOnClickListener(this);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mp = new MediaPlayer();
		sdCard = Environment.getExternalStorageDirectory(); 
		pathNormal = sdCard.getAbsolutePath() +"/"+"My Documents/My Recordings/n.amr";
		pathSlow = sdCard.getAbsolutePath() +"/"+"My Documents/My Recordings/s.amr";
		pathFast = sdCard.getAbsolutePath() +"/"+"My Documents/My Recordings/f.amr";
		pathBeep = sdCard.getAbsolutePath() +"/"+"media/audio/notifications/facebook_ringtone_pop.m4a";
		
    }
     
	public void onClick(View v) {
		// TODO Auto-generated method stub
	switch (v.getId()) {
    	case R.id.HeyButton:
    		MediaObj obj= new MediaObj();
    		obj.playSound(mp, pathNormal);
    		//obj.slowSound(mp, pathSlow);
    		//obj.fastSound(mp, pathFast);
    		//obj.beepSound(mp, pathBeep);
    		//obj.vibratorSound(vib);
		}
	}
}
