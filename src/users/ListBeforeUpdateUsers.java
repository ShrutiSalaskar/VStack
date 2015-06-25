package users;

import java.util.ArrayList;
import java.util.HashMap;

import openstack_connection.HttpConnection;
import openstack_connection.updateusers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class ListBeforeUpdateUsers extends ListActivity implements OnLoadingCompleted {
	 private updateusers req;
	 OnLoadingCompleted listener;
	 String idToBeUpdated;
	 String nameToBeUpdated;
	 String emailToBeUpdated;
	 String displayMessage;
	 private UpdateUser req2;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_users);

		ListView lv = getListView();
		
		listener = this;
		 
        // Listview on item click listener
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                idToBeUpdated = ((TextView) view.findViewById(R.id.id))
                        .getText().toString();
                nameToBeUpdated = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                emailToBeUpdated = ((TextView) view.findViewById(R.id.email))
                        .getText().toString();

                new DeleteUserRequest().UpdateSelectedUser(listener);
 
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_users, menu);
		 //final ListView listview = (ListView) findViewById(R.id.li);
		 ListUsersRequest req = new ListUsersRequest();
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
            Intent intent = new Intent(this,DeleteUser.class);
            this.startActivity(intent);
		}
	}
	
	private class ListUsersRequest extends HttpConnection{
		OnLoadingCompleted listener;

		ArrayList<HashMap<String, String>> usersList;
	    public ArrayList<HashMap<String, String>> getUsersList() {
			return usersList;
		}

		String url = ":35357/v2.0/users";
	    
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
	        	JSONArray users = null;
	        	// Hashmap for ListView
	            
	            try {
					users = jsonResponse.getJSONArray("users");
					usersList = new ArrayList<HashMap<String, String>>();
					
					for (int i = 0; i < users.length(); i++) {
	                    JSONObject c = users.getJSONObject(i);
	                     
	                    String username = c.getString("username");
	                    String name = c.getString("name");
	                    String email = c.optString("email");
	                    String id = c.getString("id");

	                    // tmp hashmap for single contact
	                    HashMap<String, String> user = new HashMap<String, String>();

	                    // adding each child node to HashMap key => value
	                    user.put("username", username);
	                    user.put("name", name);
	                    user.put("email", email);
	                    user.put("id", id);

	                    // adding contact to contact list
	                    usersList.add(user);
	                }
			        
				     ListAdapter adapter = new SimpleAdapter(
			                 ListBeforeUpdateUsers.this, usersList,
			                 R.layout.list_users, new String[] { "id", "username", "name",
			                         "email" }, new int[] { R.id.id, R.id.username,
			                         R.id.name, R.id.email });

			         setListAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
	        }
	        listener.onLoadingCompleted(true);
	    }
	        
	}
	
	private class DeleteUserRequest extends HttpConnection{
		OnLoadingCompleted listener;

		//String url = ":35357/v2.0/users/";
	    
	    public void UpdateSelectedUser(OnLoadingCompleted listener) {
	        this.listener = listener;
	        
	        try{
	        	req2 = new UpdateUser();
	        	Intent intent = new Intent(getBaseContext(), UpdateUser.class);
	        	intent.putExtra("ID", idToBeUpdated);
	        	intent.putExtra("NAME", nameToBeUpdated);
	        	intent.putExtra("EMAIL", emailToBeUpdated);
	        	
	        	startActivity(intent);
	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @Override
	    protected void onPostExecute(Boolean result) {
	    	super.onPostExecute(result);
	    	
	    	if (success) {
	        	Log.i("DeleteUserRequest: ", "User " + idToBeUpdated + " deleted successfully");
	        	displayMessage = "User deleted successfully";
	        }
	    	else {
	    		displayMessage = "Error in user deletion";
	    	}
	    	listener.onLoadingCompleted(false);
	    }
	}

}