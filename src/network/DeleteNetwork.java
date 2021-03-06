package network;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import openstack_connection.HttpConnection;

import com.example.vstack.MainActivity;
import com.example.vstack.Navigation;
import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.R;
import com.example.vstack.R.id;
import com.example.vstack.R.layout;
import com.example.vstack.R.menu;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.TextView;
import android.os.Build;

public class DeleteNetwork extends ListActivity implements OnLoadingCompleted {
	 private HttpConnection req;
	 OnLoadingCompleted listener;
	 String idToBeDeleted;
	 String displayMessage;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_networks);

		ListView lv = getListView();
		
		listener = this;
		 
       // Listview on item click listener
       lv.setOnItemClickListener(new OnItemClickListener() {

           @Override
           public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
               // getting values from selected ListItem
               idToBeDeleted = ((TextView) view.findViewById(R.id.id)).getText().toString();

               new DeleteNetworksRequest().deleteSelectedNetwork(listener);

           }
       });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_networks, menu);
		 //final ListView listview = (ListView) findViewById(R.id.li);
		 ListNetworksRequest req = new ListNetworksRequest();
	     req.getListOfNetwork(this);
	     
	     
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
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

	}


	@Override
	public void onLoadingCompleted(Boolean successful) {
		if(null!=successful && !successful){
			new Toast(this).makeText(this,displayMessage, Toast.LENGTH_LONG).show();
           Intent intent = new Intent(this,Navigation.class);
           this.startActivity(intent);
           this.finish();
		}
	}
	
	private class ListNetworksRequest extends HttpConnection{
		OnLoadingCompleted listener;

		ArrayList<HashMap<String, String>> NetworksList;
	    public ArrayList<HashMap<String, String>> getNetworksList() {
			return NetworksList;
		}

		String url = ":9696/v2.0/networks";
	    
	    public void getListOfNetwork(OnLoadingCompleted listener) {
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
	        	JSONArray Networks = null;
	        	// Hashmap for ListView
	            
	            try {
					Networks = jsonResponse.getJSONArray("networks");
					NetworksList = new ArrayList<HashMap<String, String>>();
					
					for (int i = 0; i < Networks.length(); i++) {
	                    JSONObject c = Networks.getJSONObject(i);
	                     
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
	                    NetworksList.add(network);
	                }
			        
				     ListAdapter adapter = new SimpleAdapter(
			                 DeleteNetwork.this, NetworksList,
			                 R.layout.listnetworks, new String[] {"id", "name" , "status"}, new int[] { R.id.id,R.id.name, R.id.status});

			         setListAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
	        }
	        listener.onLoadingCompleted(true);
	    }
	        
	}
	
	private class DeleteNetworksRequest extends HttpConnection{
		OnLoadingCompleted listener;

		String url = ":9696/v2.0/networks/";
	    
	    public void deleteSelectedNetwork(OnLoadingCompleted listener) {
	        this.listener = listener;
	        
	        try{
	        	setDeleteRequest(url + idToBeDeleted, null);
	            this.execute();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @Override
	    protected void onPostExecute(Boolean result) {
	    	super.onPostExecute(result);
	    	
	    	if (success) {
	        	Log.i("DeleteNetworkRequest: ", "Network " + idToBeDeleted + " deleted successfully");
	        	displayMessage = "Network Deleted";
	        }
	    	else {
	    		displayMessage = "Error in network deletion";
	    	}
	    	listener.onLoadingCompleted(false);
	    }
	}

}