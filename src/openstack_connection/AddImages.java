package openstack_connection;

import android.util.Log;

import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.UserData;

import org.json.JSONException;
import org.json.JSONObject;

public class AddImages extends HttpConnection {
    OnLoadingCompleted listener;
    String url = ":9292/v2/images";
    String jsonString = "{\"name\": \"\"}";
    public void addimage(OnLoadingCompleted listener, String name) {
        this.listener = listener;
        try{
        	JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("name", name);
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