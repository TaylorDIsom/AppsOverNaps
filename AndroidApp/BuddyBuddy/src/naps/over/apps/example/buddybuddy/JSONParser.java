package naps.over.apps.example.buddybuddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
	public JSONParser() {
		
	}
	
	public JSONArray getJSONFromURL(String address) {

	    	StringBuilder builder = new StringBuilder();
	    	HttpClient client = new DefaultHttpClient();
	    	HttpGet httpGet = new HttpGet(address);
	    	try {
	    		HttpResponse response = client.execute(httpGet);
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
