package naps.over.apps.example.buddybuddy;


import java.io.IOException;
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

public class FavoritesActivity extends Activity {

	public String apiURL = "http://dev.m.gatech.edu/d/tisom3/w/pedestrain/c/";
	
	Button buttonAddFavorite;
	String sessionName;
	String sessionId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);
		Intent intent = getIntent();
		sessionName = intent.getExtras().getString("sessionName");
		sessionId = intent.getExtras().getString("sessionId");
		buttonAddFavorite = (Button) findViewById(R.id.button_add_favorite);
		buttonAddFavorite.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Thread thread = new Thread(new Runnable(){
					@Override
					public void run(){
						openAddFavorite();
					}
                });
                thread.start();
			}
		});

		new GetFavorites().execute(apiURL + "api/favorites");

	}

	
	@Override
	public void onResume() {
		super.onResume();
		new GetFavorites().execute(apiURL + "api/favorites");
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
	
	
	 
	public void openAddFavorite() {
		Intent intent = new Intent(this, AddFavorite.class);
		intent.putExtra("sessionName", sessionName);
		intent.putExtra("sessionId", sessionId);
		startActivity(intent);
	}

    
    private class GetFavorites extends AsyncTask<String, String, JSONArray> {
        protected JSONArray doInBackground(String... urls) {
            JSONParser jParser = new JSONParser();

            JSONArray json = jParser.getJSONFromURL(urls[0]);
            return json;
        }

        
        protected void onPostExecute(JSONArray result) {
        	String[] favorites;
        	if (result != null) {
        		favorites = new String[result.length()];
        		JSONObject jsonObject = null;
                for (int i = 0; i < result.length(); i ++) {
                	favorites[i] = "";
                	try {
    					jsonObject = result.getJSONObject(i);
    				} catch (JSONException e) {
    					e.printStackTrace();
    				}
                	try {
    					favorites[i] = jsonObject.getString("Name");
    				} catch (JSONException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                }
        		
        		
        		ArrayAdapter<String> adapter = new ArrayAdapter<String>(FavoritesActivity.this, android.R.layout.simple_list_item_1,favorites);
        		
        		ListView listView = (ListView) findViewById(R.id.listView1);
        		listView.setAdapter(adapter);
        		listView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                    	
                		Intent intent = new Intent(FavoritesActivity.this, StationsActivity.class);
                		intent.putExtra("sessionName", sessionName);
                		intent.putExtra("sessionId", sessionId);
                		startActivity(intent);
                    }
                });
        	}



        }
    }
}
