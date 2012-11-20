/**
 * @author Jude Morrissey Cruz and Delgersuren Bold
 * 			San Francisco State University, Fall 2011, CSC 780
 */

package mobilePT.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ExerciseActivity extends ListActivity {
	final private static String[] EXERCISES = { 
		"Wrist Flexor", "Arm Wave Up Down", "Arm Wave Side To Side" };

	private Intent intent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    /**
	     * This example uses an ArrayAdapter to fill the ListActivity with
	     * specific values. The ArrayAdapter needs a reference to a layout item
	     * representing a TextView and a String array with the values to be
	     * shown. The TextView is defined in layout resource
	     * res/layout/list_item.xml
	     */
	    setListAdapter(new ArrayAdapter<String>(this, R.layout.exercise_list_item, EXERCISES));
	}
    
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	intent = new Intent(this, ExerciseBriefingActivity.class);
        intent.putExtra("exerciseNameFromIntent", ((String) ((TextView) v).getText()) );
        startActivity(intent);
    }
}