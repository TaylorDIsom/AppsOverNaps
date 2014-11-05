package naps.over.apps.example.buddybuddy;


import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class TrainActivity extends Activity {

	public String apiURL = "http://dev.m.gatech.edu/d/tisom3/w/pedestrain/c/";
	
	Button buttonStations;
	String sessionName;
	String sessionId;
	TextView countdown;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train);
		Intent intent = getIntent();
		sessionName = intent.getExtras().getString("sessionName");
		sessionId = intent.getExtras().getString("sessionId");
		
		
		countdown = (TextView) findViewById(R.id.countdown);	
		buttonStations = (Button) findViewById(R.id.button_stations);
		buttonStations.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Thread thread = new Thread(new Runnable(){
					@Override
					public void run(){
						openStations();
					}
                });
                thread.start();
			}
		});

		//15 minute countdown timer
		//TODO: sync countdown with current time
		
		//Get the current time
		Calendar c = Calendar.getInstance();
		int currentMinutes = c.get(Calendar.MINUTE);
		int currentSeconds = c.get(Calendar.SECOND);
		//int timeOfDay = c.get(Calendar.AM_PM);
		int minutesMod15 = currentMinutes%15;
		//minutesMod15 = 0-14
		int minutes = 14 - minutesMod15;
		//minutes * 60000 = milliseconds
		long minsToMillis = minutes * 60000;
		int seconds = 60 - currentSeconds;
		long secsToMillis = seconds * 600;
		long toMillis = minsToMillis + secsToMillis;
		//new CountDownTimer(900000, 1000){
		
		new CountDownTimer(toMillis, 1000){
			public void onTick(long millisUntilFinished){
				//14:33
				//("seconds remaining: " + millisUntilFinished / 1000);
				//minutes remaining: millisUntilFinished % 60000
				
				countdown.setText( millisUntilFinished / 60000 + " : " + (millisUntilFinished % 60000)/1000 );
			}
			public void onFinish(){
				countdown.setText("Departed");
			}
		}.start();
		
		new GetPassengers().execute(apiURL + "api/users");

	}

	
	@Override
	public void onResume() {
		super.onResume();
		new GetPassengers().execute(apiURL + "api/users");
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorites, menu);
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
	
	
	 
	public void openStations() {
		Intent intent = new Intent(this, StationsActivity.class);
		intent.putExtra("sessionName", sessionName);
		intent.putExtra("sessionId", sessionId);
		startActivity(intent);
	}

    
    private class GetPassengers extends AsyncTask<String, String, JSONArray> {
        protected JSONArray doInBackground(String... urls) {
            JSONParser jParser = new JSONParser();

            JSONArray json = jParser.getJSONFromURL(urls[0]);
            return json;
        }

        
        protected void onPostExecute(JSONArray result) {
        	String[] passengers;
        	if (result != null) {
        		passengers = new String[result.length()];
        		JSONObject jsonObject = null;
                for (int i = 0; i < result.length(); i ++) {
                	passengers[i] = "";
                	try {
    					jsonObject = result.getJSONObject(i);
    				} catch (JSONException e) {
    					e.printStackTrace();
    				}
                	try {
                		passengers[i] = jsonObject.getString("first_name");
    				} catch (JSONException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                }
        		
        		
        		ArrayAdapter<String> adapter = new ArrayAdapter<String>(TrainActivity.this, android.R.layout.simple_list_item_1,passengers);
        		
        		ListView listView = (ListView) findViewById(R.id.listView1);
        		listView.setAdapter(adapter);
        		/**
        		listView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                    	
                		Intent intent = new Intent(TrainActivity.this, StationsActivity.class);
                		intent.putExtra("sessionName", sessionName);
                		intent.putExtra("sessionId", sessionId);
                		startActivity(intent);
                    }
                });
                */
        	}



        }
    }
}
