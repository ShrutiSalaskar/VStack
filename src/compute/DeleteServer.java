package compute;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import openstack_connection.HttpConnection;

import com.example.vstack.Navigation;
import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.R;
import com.example.vstack.R.id;
import com.example.vstack.R.layout;
import com.example.vstack.R.menu;
import com.example.vstack.UserData;

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
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class DeleteServer extends ListActivity implements OnLoadingCompleted {
	 private HttpConnection req;
	 OnLoadingCompleted listener;
	 String idToBeDeleted;
	 String displayMessage;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_servers);

		ListView lv = getListView();
		listener = this;
		 
       // Listview on item click listener
       lv.setOnItemClickListener(new OnItemClickListener() {

           @Override
           public void onItemClick(AdapterView<?> parent, View view,
                   int position, long id) {
               // getting values from selected ListItem
               idToBeDeleted = ((TextView) view.findViewById(R.id.id))
                       .getText().toString();

               new DeleteServerRequest().deleteSelectedServer(listener);

           }
       });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_servers, menu);
		 //final ListView listview = (ListView) findViewById(R.id.li);
		 ListServerRequest req = new ListServerRequest();
	     req.getListOfUsers(this);
	     
	     
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
		if(null!=successful && !successful){
			new Toast(this).makeText(this,displayMessage, Toast.LENGTH_LONG).show();
           Intent intent = new Intent(this,Navigation.class);
           this.startActivity(intent);
           this.finish();
		}
	}
	
	private class ListServerRequest extends HttpConnection{
		OnLoadingCompleted listener;

		ArrayList<HashMap<String, String>> serverList;
	    
	    String url = ":8774/v2/"+UserData.tenentId+"/servers";
	    
	    public void getListOfUsers(OnLoadingCompleted listener) {
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
	        	JSONArray serverArray = null;
	        	// Hashmap for ListView
	            
	            try {
					serverArray = jsonResponse.getJSONArray("servers");
					serverList = new ArrayList<HashMap<String, String>>();
					
					for (int i = 0; i < serverArray.length(); i++) {
	                    JSONObject c = serverArray.getJSONObject(i);
	                     
	                    String id = c.getString("id");
	                    String name = c.getString("name");
	                    

	                    // tmp hashmap for single contact
	                    HashMap<String, String> servers = new HashMap<String, String>();

	                    // adding each child node to HashMap key => value
	                    servers.put("id", id);
	                    servers.put("name", name);

	                    // adding contact to contact list
	                    serverList.add(servers);
	                }
			        
				     ListAdapter adapter = new SimpleAdapter(
			                 DeleteServer.this, serverList,
			                 R.layout.listserver, new String[] { "id", "name"}, new int[] { R.id.id,
			                         R.id.name });

			         setListAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
	        }
	        listener.onLoadingCompleted(true);
	    }
	        
	}
	
	private class DeleteServerRequest extends HttpConnection{
		OnLoadingCompleted listener;

		String url = ":8774/v2/"+UserData.tenentId+"/servers/";
	    
	    public void deleteSelectedServer(OnLoadingCompleted listener) {
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
	        	Log.i("DeleteServer: ", "Servers " + idToBeDeleted + " deleted successfully");
	        	displayMessage = "Server deleted successfully";
	        }
	    	else {
	    		displayMessage = "Error in project deletion";
	    	}
	    	listener.onLoadingCompleted(false);
	    }
	}

}