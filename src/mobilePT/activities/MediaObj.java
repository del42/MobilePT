/**
 * @author Jude Morrissey Cruz and Delgersuren Bold
 * 			San Francisco State University, Fall 2011, CSC 780
 */

package mobilePT.activities;

import java.io.IOException;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;


public class MediaObj extends Activity {
 
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        /**
	     * This is the media object class where all the method to play video objects are included here.
	     * So the programmer can use the methods simply just create media object 
	     */
    }
    //normal sound method with parameter of MediaPlayer and path of the media is sdcard
    	public void normalSound(MediaPlayer mp, String path){
    	try {
 	        mp.setDataSource(path);
 	    } catch (IllegalArgumentException e) {
 	        // TODO Auto-generated catch block
 	        e.printStackTrace();
 	    } catch (IllegalStateException e) {
 	        // TODO Auto-generated catch block
 	        e.printStackTrace();
 	    } catch (IOException e) {
 	        // TODO Auto-generated catch block
 	        e.printStackTrace();
 	    }
 	    try {
 	        mp.prepare();
 	    } catch (IllegalStateException e) {
 	        // TODO Auto-generated catch block
 	        e.printStackTrace();
 	    } catch (IOException e) {
 	        // TODO Auto-generated catch block
 	        e.printStackTrace();
 	    }
 	    //medai player starts with this call
 	    mp.start();
    }
    //slow sound method with parameter of MediaPlayer and path of the media is sdcard   
    public void slowSound(MediaPlayer mp, String path){
	    try {
	        mp.setDataSource(path);
	    } catch (IllegalArgumentException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IllegalStateException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    try {
	        mp.prepare();
	    } catch (IllegalStateException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    mp.start();

    }
  //fast sound method with parameter of MediaPlayer and path of the media is sdcard 
    public void fastSound(MediaPlayer mp, String path){
    	 try {
  	        mp.setDataSource(path);
  	    } catch (IllegalArgumentException e) {
  	        // TODO Auto-generated catch block
  	        e.printStackTrace();
  	    } catch (IllegalStateException e) {
  	        // TODO Auto-generated catch block
  	        e.printStackTrace();
  	    } catch (IOException e) {
  	        // TODO Auto-generated catch block
  	        e.printStackTrace();
  	    }
  	    try {
  	        mp.prepare();
  	    } catch (IllegalStateException e) {
  	        // TODO Auto-generated catch block
  	        e.printStackTrace();
  	    } catch (IOException e) {
  	        // TODO Auto-generated catch block
  	        e.printStackTrace();
  	    }
  	    mp.start();
  	    
    }
  //beep sound method with parameter of MediaPlayer and path of the media is sdcard
    public void beepSound(MediaPlayer mp, String path){
    	 try {
   	        mp.setDataSource(path);
   	    } catch (IllegalArgumentException e) {
   	        // TODO Auto-generated catch block
   	        e.printStackTrace();
   	    } catch (IllegalStateException e) {
   	        // TODO Auto-generated catch block
   	        e.printStackTrace();
   	    } catch (IOException e) {
   	        // TODO Auto-generated catch block
   	        e.printStackTrace();
   	    }
   	    try {
   	        mp.prepare();
   	    } catch (IllegalStateException e) {
   	        // TODO Auto-generated catch block
   	        e.printStackTrace();
   	    } catch (IOException e) {
   	        // TODO Auto-generated catch block
   	        e.printStackTrace();
   	    }
   	    mp.start();
    }
    
    //gives vibration to the phone with method call
    public void vibratorSound(Vibrator vib){
    	//Vibration will continue 1 second
    	long vibLen = 1000;
    	//giving the command along with itslenght of the vibration
		vib.vibrate(vibLen);
  
	}  
}
