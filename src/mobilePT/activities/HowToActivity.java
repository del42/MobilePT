/**
 * @author Jude Morrissey Cruz and Delgersuren Bold
 * 			San Francisco State University, Fall 2011, CSC 780
 */

package mobilePT.activities;

import android.app.Activity;
import android.os.Bundle;

public class HowToActivity extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layouthowtomenu);
    }
}
