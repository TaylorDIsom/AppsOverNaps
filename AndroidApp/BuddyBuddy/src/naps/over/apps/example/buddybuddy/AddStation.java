package naps.over.apps.example.buddybuddy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddStation extends Activity {

	EditText stationName;
	Button button;
	String sessionName;
	String sessionId;
	
	private GoogleMap mMap;
	private Marker stationMarker;
	private String stationAddressString;
	private double stationLatitude;
	private double stationLongitude;
	private String stationNameString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_station);
		
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

		
		Intent intent = getIntent();
		sessionName = intent.getExtras().getString("sessionName");
		sessionId = intent.getExtras().getString("sessionId");
		stationName = (EditText) findViewById(R.id.editText1);
		button = (Button) findViewById(R.id.button_add_station);
		stationNameString = stationName.toString();
		geoLocate();
		
		mMap.setOnMapClickListener(new stationMapClickListener());
		
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Thread thread = new Thread(new Runnable(){
					@Override
					public void run(){
						EditText stationName = (EditText) findViewById(R.id.editText1);
						if (stationMarker != null ) {		
							if(!stationName.getText().toString().equals("")) stationNameString = stationName.getText().toString();
							addstationPost();
						}
					}
                });
                thread.start();
                finish();
			}
		});
	}

	public class stationMapClickListener implements OnMapClickListener {

		@Override
		public void onMapClick(LatLng point) {
			// TODO Auto-generated method stub
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			
			if (stationMarker != null) {
				stationMarker.remove();
				stationMarker = null;
			}

				stationMarker = mMap.addMarker(new MarkerOptions()
		        .position(point)
		        .title("New station"));
			
		        Geocoder gc = new Geocoder(AddStation.this);
		        List<Address> addressList = null;
		        try {
					addressList = gc.getFromLocation(point.latitude, point.longitude, 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        if (addressList != null) {
		        	Address stationAddress = addressList.get(0);
		        	TextView addressTextView = (TextView) findViewById(R.id.addressTextView);
		        	stationAddressString = stationAddress.getAddressLine(0);
		        	addressTextView.setText(stationAddressString);
		        	stationNameString = stationAddressString;
		        }
		        stationLatitude = point.latitude;
		        stationLongitude = point.longitude;
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_station, menu);
		return true;
	}

	 public void geoLocate() {
		 /*
	        //hideSoftKeyboard(view);
	        EditText et = (EditText)findViewById(R.id.editText1);
	        String location = et.getText().toString();

	        Geocoder gc = new Geocoder(this);
	        List<Address> list = gc.getFromLocationName(location,1);
	        Address add = list.get(0);
	       // String locality = add.getLocality();

	        //Toast.makeText(this,locality, Toast.LENGTH_LONG).show();
	        double lat = add.getLatitude();
	        double lng = add.getLongitude();

	      TextView  txtLat = (TextView) findViewById(R.id.textViewt);
	        txtLat.setText("Latitude:" + lat + ", Longitude:" + lng);
	        */
	       // Toast.makeText(this,lat,Toast.LENGTH_LONG).show();
	       // gotoLocation(lat,lng,10);
		 
		 LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		 Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		 double longitude = location.getLongitude();
		 double latitude = location.getLatitude();
		 mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 15));
		 Log.e(Double.toString(longitude), Double.toString(latitude));
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
	
	public void addstationPost() {
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://dev.m.gatech.edu/d/tisom3/w/pedestrain/c/api/stations");
	    Log.e("stationName", stationNameString);
	    
	    try {
	    	 // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
	        nameValuePairs.add(new BasicNameValuePair("name", stationNameString));
	        nameValuePairs.add(new BasicNameValuePair("address", stationAddressString));
	        nameValuePairs.add(new BasicNameValuePair("userId", "1"));
	        nameValuePairs.add(new BasicNameValuePair("latitude", Double.toString(stationLatitude)));
	        nameValuePairs.add(new BasicNameValuePair("longitude", Double.toString(stationLongitude)));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        httppost.setHeader("Cookie", sessionName + "=" + sessionId);
	        
	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }

	}
}
