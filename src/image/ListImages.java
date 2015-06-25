package image;

import java.util.ArrayList;
import java.util.HashMap;

import openstack_connection.HttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListImages extends ListActivity implements OnLoadingCompleted {
	 private HttpConnection req;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_images);

		ListView lv = getListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_images, menu);
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
		// TODO Auto-generated method stub
		
	}
	
	private class ListUsersRequest extends HttpConnection{
		OnLoadingCompleted listener;

		ArrayList<HashMap<String, String>> ImageList;
	    public ArrayList<HashMap<String, String>> getImagesList() {
			return ImageList;
		}

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
	        	JSONArray images = null;
	        	// Hashmap for ListView
	            
	            try {
					images = jsonResponse.getJSONArray("images");
					ImageList = new ArrayList<HashMap<String, String>>();
					
					for (int i = 0; i < images.length(); i++) {
	                    JSONObject c = images.getJSONObject(i);
	                     
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
	                    ImageList.add(image);
	                }
			        
				     ListAdapter adapter = new SimpleAdapter(
			                 ListImages.this, ImageList,
			                 R.layout.listimage, new String[] {"id" , "name", "size"}, new int[] { R.id.id, R.id.name,R.id.size });

			         setListAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
	        }
	        listener.onLoadingCompleted(this.success);
	    }
	        
	}

}