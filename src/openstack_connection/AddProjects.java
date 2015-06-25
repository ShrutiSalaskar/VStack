package openstack_connection;

import android.util.Log;

import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.UserData;

import org.json.JSONException;
import org.json.JSONObject;

	public class AddProjects extends HttpConnection {
	    OnLoadingCompleted listener;
	    String url = ":35357/v3/projects";
	    String jsonString = "{\"project\":{\"name\": \"\",\"description\": \"\",\"enabled\":true}}";
	    public void addproj(OnLoadingCompleted listener, String name, String des) {
	        this.listener = listener;
	        try{
	        	JSONObject jsonObject = new JSONObject(jsonString);
	            jsonObject.getJSONObject("project").put("name", name);
	            jsonObject.getJSONObject("project").put("description", des);
	            jsonObject.getJSONObject("project").put("enabled", true);
	            setPostRequestAfterlogin(url, jsonObject);
	            this.execute();
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	    }

    @Override
    protected void onPostExecute(Boolean result) {
    	// TODO Auto-generated method stub
    	super.onPostExecute(result);
    	
    	listener.onLoadingCompleted(success);
    }
  
}