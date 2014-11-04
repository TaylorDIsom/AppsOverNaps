package naps.over.apps.example.buddybuddy;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TrainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train_draft);
		//populateSchedule();
		doSomething();
	}
	public void populateSchedule() {
		
		//Get the current time
		Calendar c = Calendar.getInstance();
		int currentHour = c.get(Calendar.HOUR);
		int currentMinutes = c.get(Calendar.MINUTE);
		int timeOfDay = c.get(Calendar.AM_PM);
		
		String timePeriod = " AM";
		
		if (timeOfDay == 1) {
			timePeriod = " PM";
		}
		
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
			
			
			
			timeSlots[i] = String.format("%02d", currentHour) + ":" + String.format("%02d", closestTime) + timePeriod;
			closestTime += 20;
			if (closestTime == 60) {
				closestTime = 0;
				currentHour++;
			}
			if (currentHour == 13) {
				currentHour = 0;
				if (timeOfDay == 1) {
					timePeriod = " AM";
				} else {
					timePeriod = " PM";
				}
			}
			

		}

		//Attach the strings to the listview
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,timeSlots);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
	}
	private void doSomething() {

		String[] list = {"yoooo"};
		//Attach the strings to the listview
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.train, menu);
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
	
	
}