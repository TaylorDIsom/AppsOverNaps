package naps.over.apps.example.buddybuddy;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ScheduleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule);
		populateSchedule();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void populateSchedule() {
		
		//Get the current time
		Calendar c = Calendar.getInstance();
		int currentHour = c.get(Calendar.HOUR);
		int currentMinutes = c.get(Calendar.MINUTE);
		
		int closestTime;
		
		//Determine the next train time by finding the next 20 minute interval
		if (currentMinutes >= 0 && currentMinutes < 20) {
			closestTime = 20;
		} else if (currentMinutes >= 20 && currentMinutes < 40) {
			closestTime = 40;
		} else {
			closestTime = 0;
			currentHour++;
		}

		String timeSlots[] = new String[10];
		
		//Create an array of strings of upcoming 20 minute intervals
		for (int i = 0; i < timeSlots.length; i++) {
			timeSlots[i] = currentHour + ":" + String.format("%02d", closestTime);
			closestTime += 20;
			if (closestTime == 60) {
				closestTime = 0;
				currentHour++;
			}
		}

		//Attach the strings to the listview
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,timeSlots);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
	}
}
