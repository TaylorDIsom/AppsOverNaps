package naps.over.apps.example.buddybuddy;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {
//TAKE ME BACK TO TWORLD
	//this is the master
	String sessionName;
	String sessionId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
	    
		//<CAS Login stuff
		// To get the action of the intent use
	    String action = intent.getAction();

	    if (!action.equals(Intent.ACTION_VIEW)) {
	        throw new RuntimeException("Should not happen");
	    }
	    
	    // To get the data use
	    Uri data = intent.getData();
	    sessionName = data.getQueryParameter("sessionName");
	    sessionId = data.getQueryParameter("sessionId");
	    //CAS Login >
	    
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public void openSchedule(View view) {
		Intent intent = new Intent(this, ScheduleActivity.class);
		startActivity(intent);
	}
	
	public void openFavorites(View view) {
		Intent intent = new Intent(this, FavoritesActivity.class);
		intent.putExtra("sessionName", sessionName);
		intent.putExtra("sessionId", sessionId);
		startActivity(intent);
	}
}
