package naps.over.apps.example.buddybuddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class StationsByDistanceRetriever {
	public StationsByDistanceRetriever() {
		
	}
	
	public JSONArray getJSONFromURL(String address, String latitude, String longitude) {

	    	StringBuilder builder = new StringBuilder();
	    	HttpClient client = new DefaultHttpClient();
	    	HttpPost httpPost = new HttpPost(address);
	    	try {
	    		
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("userLatitude", latitude));
		        nameValuePairs.add(new BasicNameValuePair("userLongitude", longitude));
		        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        
	    		HttpResponse response = client.execute(httpPost);
	    		StatusLine statusLine = response.getStatusLine();
	    		int statusCode = statusLine.getStatusCode();
	    		if (statusCode == 200) {
	    			HttpEntity entity = response.getEntity();
	    			InputStream content = entity.getContent();
	    			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	    			String line;
	    			while((line = reader.readLine()) != null) {
	    				builder.append(line);
	    			}
	    		} else {
	    			Log.e(MainActivity.class.toString(),"Failed to get JSON object");
	    		}
	    	}catch(ClientProtocolException e){
	    		e.printStackTrace();
	    	} catch (IOException e){
	    		e.printStackTrace();
	    	}
	    	JSONArray json = null;
	    	try {
				json = new JSONArray(builder.toString());
			} catch (JSONException e) {
			    Log.e("JSON Parser", "Error parsing data " + e.toString());
			}
	    	return json;
	}
}
