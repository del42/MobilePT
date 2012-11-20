/**
 * @author Jude Morrissey Cruz and Delgersuren Bold
 * 			San Francisco State University, Fall 2011, CSC 780
 */

package mobilePT.activities.howto;

import java.io.File;

import mobilePT.activities.R;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class ArmWaveUpDownHowToActivity extends Activity {
	private TextView label;
	private boolean autoplay = false;
	private VideoView videoView1;
	private MediaPlayer mp;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howto);
        
        label = (TextView) findViewById(R.id.topLabel);
        label.setText(this.getIntent().getStringExtra("exerciseNameFromIntent"));
        videoView1 =(VideoView) findViewById(R.id.videoView1);
        mp = new MediaPlayer();
		File sdCard = Environment.getExternalStorageDirectory(); 
		String path = sdCard.getAbsolutePath() +"/"+"My Documents/My Recordings";
		String filename = "t2.3gp";
		boolean autoplay = true;
		videoPlayer(path,filename,autoplay);
        
    }
    public void videoPlayer(String path, String fileName, boolean autoplay){
        //get current window information, and set format, set it up differently, if you need some special effects
        getWindow().setFormat(PixelFormat.JPEG);
        //MediaController is the UI control howering above the video.
        videoView1.setMediaController(new MediaController(this));
        //accessing a video file to the video holder
        videoView1.setVideoURI(Uri.parse(path+"/"+fileName));
        //get focus, before playing the video.
        videoView1.requestFocus();
       // autoplay on activity call
        if(autoplay){
            videoView1.start();
        }
}}
