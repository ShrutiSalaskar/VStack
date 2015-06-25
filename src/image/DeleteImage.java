package image;

import java.util.ArrayList;
import java.util.HashMap;

import openstack_connection.HttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.vstack.MainActivity;
import com.example.vstack.Navigation;
import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.R;
import com.example.vstack.R.id;
import com.example.vstack.R.layout;
import com.example.vstack.R.menu;

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

public class DeleteImage extends ListActivity implements OnLoadingCompleted {
	 private HttpConnection req;
	 OnLoadingCompleted listener;
	 String idToBeDeleted;
	 String displayMessage;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_images);

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

                new DeleteProjectRequest().deleteSelectedUser(listener);
 
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_images, menu);
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

		ArrayList<HashMap<String, String>> imageList;
	    
	    String url = ":9292/v2/images";
	    
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
	        	JSONArray ImageArray = null;
	        	// Hashmap for ListView
	            
	            try {
					ImageArray = jsonResponse.getJSONArray("images");
					imageList = new ArrayList<HashMap<String, String>>();
					
					for (int i = 0; i < ImageArray.length(); i++) {
	                    JSONObject c = ImageArray.getJSONObject(i);
	                     
	                    String id = c.getString("id");
	                    String name = c.getString("name");
	                    String size = c.optString("size");
	                    

	                    // tmp hashmap for single contact
	                    HashMap<String, String> image = new HashMap<String, String>();

	                    // adding each child node to HashMap key => value
	                    image.put("id", id);
	                    image.put("name", name);
	                    image.put("size", size);

	                    // adding contact to contact list
	                    imageList.add(image);
	                }
			        
				     ListAdapter adapter = new SimpleAdapter(
			                 DeleteImage.this, imageList,
			                 R.layout.listimage, new String[] { "id", "name", "size"
			                        }, new int[] { R.id.id,
			                         R.id.name, R.id.size });

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

		String url = ":9292/v2/images/";
	    
	    public void deleteSelectedUser(OnLoadingCompleted listener) {
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
	        	Log.i("Deleteimage: ", "Image " + idToBeDeleted + " deleted successfully");
	        	displayMessage = "Image Deleted";
	        }
	    	else {
	    		displayMessage = "Error in Image deletion";
	    	}
	    	listener.onLoadingCompleted(false);
	    }
	}

}