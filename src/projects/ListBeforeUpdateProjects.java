package projects;

import java.util.ArrayList;
import java.util.HashMap;

import openstack_connection.HttpConnection;
import openstack_connection.UpdateProjects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.vstack.Navigation;
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

public class ListBeforeUpdateProjects extends ListActivity implements OnLoadingCompleted {
	 private HttpConnection req;
	 OnLoadingCompleted listener;
	 String idToBeUpdated;
	 String nameToBeUpdated;
	 String desToBeUpdated;
	 String displayMessage;
	 private UpdateProject req2;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_project);

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
                desToBeUpdated = ((TextView) view.findViewById(R.id.description))
                        .getText().toString();

                new DeleteProjectRequest().deleteSelectedUser(listener);
 
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_project, menu);
		 //final ListView listview = (ListView) findViewById(R.id.li);
		 ListProjectsRequest req = new ListProjectsRequest();
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
	
	private class ListProjectsRequest extends HttpConnection{
		OnLoadingCompleted listener;

		ArrayList<HashMap<String, String>> projectList;
	    
	    String url = ":35357/v3/projects";
	    
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
	        	JSONArray projectArray = null;
	        	// Hashmap for ListView
	            
	            try {
					projectArray = jsonResponse.getJSONArray("projects");
					projectList = new ArrayList<HashMap<String, String>>();
					
					for (int i = 0; i < projectArray.length(); i++) {
	                    JSONObject c = projectArray.getJSONObject(i);
	                     
	                    String name = c.getString("name");
	                    String description = c.getString("description");
	                    String id = c.getString("id");

	                    // tmp hashmap for single contact
	                    HashMap<String, String> project = new HashMap<String, String>();

	                    // adding each child node to HashMap key => value
	                    project.put("id", id);
	                    project.put("name", name);
	                    project.put("description", description);

	                    // adding contact to contact list
	                    projectList.add(project);
	                }
			        
				     ListAdapter adapter = new SimpleAdapter(
			                 ListBeforeUpdateProjects.this, projectList,
			                 R.layout.listprojects, new String[] { "id", "name", "description"
			                        }, new int[] { R.id.id,
			                         R.id.name, R.id.description });

			         setListAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
	        }
	        listener.onLoadingCompleted(true);
	    }
	        
	}
	
	private class DeleteProjectRequest extends HttpConnection{
		OnLoadingCompleted listener;

		String url = ":35357/v3/projects/";
	    
	    public void deleteSelectedUser(OnLoadingCompleted listener) {
	        this.listener = listener;
	        
	        try{
	        	req2 = new UpdateProject();
	        	Intent intent = new Intent(getBaseContext(), UpdateProject.class);
	        	intent.putExtra("ID", idToBeUpdated);
	        	intent.putExtra("NAME", nameToBeUpdated);
	        	intent.putExtra("DES", desToBeUpdated);
	        	startActivity(intent);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @Override
	    protected void onPostExecute(Boolean result) {
	    	super.onPostExecute(result);
	    	
	    	if (success) {
	        	Log.i("Deleteproject: ", "Project " + idToBeUpdated + " deleted successfully");
	        	displayMessage = "Project deleted successfully";
	        }
	    	else {
	    		displayMessage = "Error in project deletion";
	    	}
	    	listener.onLoadingCompleted(false);
	    }
	}

}