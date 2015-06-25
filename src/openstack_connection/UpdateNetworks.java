package openstack_connection;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.R;
import com.example.vstack.R.id;
import com.example.vstack.R.layout;
import com.example.vstack.R.menu;

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

public class UpdateNetworks extends  HttpConnection {
    OnLoadingCompleted listener;
    String url = ":9696/v2.0/networks/";
    String jsonString = "{\"network\":{\"name\":\"\"}}";
    public void updateNetworks(OnLoadingCompleted listener, String id, String name) {
        this.listener = listener;
        try{
        	JSONObject jsonObject = new JSONObject(jsonString);
        	
            jsonObject.getJSONObject("network").put("name", name);
           
           
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