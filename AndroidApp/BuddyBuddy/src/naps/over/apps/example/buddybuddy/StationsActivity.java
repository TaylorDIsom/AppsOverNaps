package naps.over.apps.example.buddybuddy;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class StationsActivity extends Activity {

	public String apiURL = "http://dev.m.gatech.edu/d/tisom3/w/pedestrain/c/";
	
	
	String sessionName;
	String sessionId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stations);
		Intent intent = getIntent();
		sessionName = intent.getExtras().getString("sessionName");
		sessionId = intent.getExtras().getString("sessionId");

		new GetStations().execute(apiURL + "api/favorites");

	}

	
	@Override
	public void onResume() {
		super.onResume();
		new GetStations().execute(apiURL + "api/favorites");
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stations, menu);
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

    
    private class GetStations extends AsyncTask<String, String, JSONArray> {
        protected JSONArray doInBackground(String... urls) {
            JSONParser jParser = new JSONParser();

            JSONArray json = jParser.getJSONFromURL(urls[0]);
            return json;
        }

        
        protected void onPostExecute(JSONArray result) {
        	String[] stations;
        	if (result != null) {
        		stations = new String[result.length()];
        		JSONObject jsonObject = null;
                for (int i = 0; i < result.length(); i ++) {
                	stations[i] = "";
                	try {
    					jsonObject = result.getJSONObject(i);
    				} catch (JSONException e) {
    					e.printStackTrace();
    				}
                	try {
                		stations[i] = jsonObject.getString("Name");
    				} catch (JSONException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                }
        		
        		
        		ArrayAdapter<String> adapter = new ArrayAdapter<String>(StationsActivity.this, android.R.layout.simple_list_item_1,stations);
        		
        		ListView listView = (ListView) findViewById(R.id.listView1);
        		listView.setAdapter(adapter);
        	}



        }
    }
}
