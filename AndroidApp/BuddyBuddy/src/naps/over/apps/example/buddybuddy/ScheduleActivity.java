package naps.over.apps.example.buddybuddy;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ScheduleActivity extends Activity {

	String sessionName;
	String sessionId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule);
		
		Intent intent = getIntent();
		sessionName = intent.getExtras().getString("sessionName");
		sessionId = intent.getExtras().getString("sessionId");
		
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
		listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){

        		Intent intent = new Intent(ScheduleActivity.this, FavoritesActivity.class);
        		intent.putExtra("sessionName", sessionName);
        		intent.putExtra("sessionId", sessionId);
        		startActivity(intent);
            }
        });
	}
}
