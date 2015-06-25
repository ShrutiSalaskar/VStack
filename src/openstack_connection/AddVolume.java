package openstack_connection;

import android.util.Log;

import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.UserData;

import org.json.JSONException;
import org.json.JSONObject;

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

public class AddVolume extends HttpConnection {
    OnLoadingCompleted listener;
    String url = ":8776/v2/"+UserData.tenentId+"/volumes";
 
    String jsonString ="{\"volume\":{\"size\":\"\",\"name\":\"\"}}";
    public void AddVol(OnLoadingCompleted listener, String name, String size) {
        this.listener = listener;
        try{
        	JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.getJSONObject("volume").put("name", name);
            jsonObject.getJSONObject("volume").put("size", size);
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