package projects;

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

public class ListProject extends ListActivity implements
		OnLoadingCompleted {
	private HttpConnection req;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_project);

		ListView lv = getListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_project, menu);
		// final ListView listview = (ListView) findViewById(R.id.li);
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
		// TODO Auto-generated method stub

	}

	private class ListProjectsRequest extends HttpConnection {
		OnLoadingCompleted listener;

		ArrayList<HashMap<String, String>> projectList;

		String url = ":35357/v3/projects";

		public void getListOfUsers(OnLoadingCompleted listener) {
			this.listener = listener;

			try {
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
							ListProject.this, projectList,
							R.layout.listprojects, new String[] { "id",
									"name", "description" }, new int[] {
									R.id.id, R.id.name, R.id.description });

					setListAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			listener.onLoadingCompleted(this.success);
		}

	}

}