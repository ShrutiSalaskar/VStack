package network;

import openstack_connection.HttpConnection;

import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.R;
import com.example.vstack.R.id;
import com.example.vstack.R.layout;
import com.example.vstack.R.menu;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListNetworks extends ListActivity implements OnLoadingCompleted {
	 private HttpConnection req;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_networks);

		ListView lv = getListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_networks, menu);
		 //final ListView listview = (ListView) findViewById(R.id.li);
		 ListUsersRequest req = new ListUsersRequest();
	     req.getListOfNetworks(this);
	     
	     
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
	}

	@Override
	public void onLoadingCompleted(Boolean successful) {
		// TODO Auto-generated method stub
		
	}
	
	private class ListUsersRequest extends HttpConnection{
		OnLoadingCompleted listener;

		ArrayList<HashMap<String, String>> networksList;
	    public ArrayList<HashMap<String, String>> getNetworksList() {
			return networksList;
		}

		String url = ":9696/v2.0/networks";
	    
	    public void getListOfNetworks(OnLoadingCompleted listener) {
	        this.listener = listener;
	        
	        try{
	        	setGetRequest(url, null);
	            this.execute();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @Override
	    protected void onPostExecute(Boolean result) {
	    	super.onPostExecute(result);
	    	
	    	if (success) {
	        	JSONArray networks = null;
	        	// Hashmap for ListView
	            
	            try {
					networks = jsonResponse.getJSONArray("networks");
					networksList = new ArrayList<HashMap<String, String>>();
					
					for (int i = 0; i < networks.length(); i++) {
	                    JSONObject c = networks.getJSONObject(i);
	                     
	                    String id = c.getString("id");
	                    String name = c.getString("name");
	                    String status = c.getString("status");

	                    // tmp hashmap for single contact
	                    HashMap<String, String> network = new HashMap<String, String>();

	                    // adding each child node to HashMap key => value
	                    network.put("id", id);
	                    network.put("name", name);
	                    network.put("status", status);
	                    

	                    // adding contact to contact list
	                    networksList.add(network);
	                }
			        
				     ListAdapter adapter = new SimpleAdapter(
			                 ListNetworks.this, networksList,
			                 R.layout.listnetworks, new String[] { "id","name", "status"}, new int[] { R.id.id, R.id.name, R.id.status });

			         setListAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
	        }
	        listener.onLoadingCompleted(this.success);
	    }
	        
	}

}