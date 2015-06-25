package openstack_connection;

import android.util.Log;

import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.UserData;

import org.json.JSONException;
import org.json.JSONObject;

	public class UpdateVol extends HttpConnection {
	    OnLoadingCompleted listener;
	    String url = ":8776/v2/"+UserData.tenentId+"/volumes/";
	    String jsonString = "{\"volume\":{\"name\":\"\"}}";
	    
	    public void update_vols(OnLoadingCompleted listener, String id, String name) {
	        this.listener = listener;
	        try{
	        	JSONObject jsonObject = new JSONObject(jsonString);
	            jsonObject.getJSONObject("volume").put("name", name);
	            setPutRequest(url+id, jsonObject);
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
