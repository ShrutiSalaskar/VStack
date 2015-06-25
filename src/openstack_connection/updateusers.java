package openstack_connection;

import android.util.Log;

import com.example.vstack.OnLoadingCompleted;

import org.json.JSONException;
import org.json.JSONObject;

public class updateusers extends HttpConnection {
    OnLoadingCompleted listener;
    String url = ":35357/v2.0/users/";
    String jsonString = "{\"user\":{\"id\": \"\",\"name\": \"\",\"email\": \"\",\"enabled\":true}}";
    public void updateUser(OnLoadingCompleted listener, String id, String name, String email) {
        this.listener = listener;
        try{
        	JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.getJSONObject("user").put("id", id);
            jsonObject.getJSONObject("user").put("name", name);
            jsonObject.getJSONObject("user").put("email", email);
            jsonObject.getJSONObject("user").put("enabled", true);
           
            setPutRequest(url+id , jsonObject);
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
