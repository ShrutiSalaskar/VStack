package openstack_connection;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.UserData;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class AddInstance extends HttpConnection {
    OnLoadingCompleted listener;
    String url = ":8774/v2/"+UserData.tenentId+"/servers";
    String jsonString = "{\"server\": {\"name\": \"\",\"imageRef\": \"\",\"flavorRef\": \"\",\"max_count\": 1,\"min_count\": 1}}";
    public void launch(OnLoadingCompleted listener,String imageRef, String name, String flavor) {
        this.listener = listener;
        try{
        	JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.getJSONObject("server").put("name", name);
            jsonObject.getJSONObject("server").put("imageRef", imageRef);
            jsonObject.getJSONObject("server").put("flavorRef", flavor);
            jsonObject.getJSONObject("server").put("max_count", 1);
            jsonObject.getJSONObject("server").put("min_count", 1);
           
            
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