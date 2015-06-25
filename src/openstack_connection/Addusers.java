package openstack_connection;

import android.util.Log;

import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.UserData;

import org.json.JSONException;
import org.json.JSONObject;

public class Addusers extends HttpConnection {
    OnLoadingCompleted listener;
    String url = ":35357/v2.0/users";
    String jsonString = "{\"user\":{\"name\": \"\",\"email\": \"\",\"enabled\":true}}";
    public void adduser(OnLoadingCompleted listener, String name, String email) {
        this.listener = listener;
        try{
        	JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.getJSONObject("user").put("name", name);
            jsonObject.getJSONObject("user").put("email", email);
            jsonObject.getJSONObject("user").put("enabled", true);
           
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
