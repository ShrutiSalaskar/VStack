package openstack_connection;

import android.util.Log;

import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.UserData;

import org.json.JSONException;
import org.json.JSONObject;

public class KeystoneRequest extends HttpConnection {
	OnLoadingCompleted listener;
	String username;
	String url = ":5000/v2.0/tokens";
	String jsonString = "{ \"auth\": { \"tenantName\": \"\", \"passwordCredentials\" : { \"username\" : \"\" , \"password\" : \"\" } } }";

	public void login(OnLoadingCompleted listener, String username,
			String password) {
		this.listener = listener;
		this.username = username;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			jsonObject.getJSONObject("auth").put("tenantName", username);
			jsonObject.getJSONObject("auth").getJSONObject("passwordCredentials").put("username", username);
			jsonObject.getJSONObject("auth").getJSONObject("passwordCredentials").put("password", password);
			setPostRequest(url, jsonObject);
			this.execute();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPostExecute(Boolean status) {
		if (success) {
			try {
				UserData.tokenId = jsonResponse.getJSONObject("access").getJSONObject("token").getString("id");
				UserData.tenentId = jsonResponse.getJSONObject("access").getJSONObject("token").getJSONObject("tenant").getString("id");
				Log.i("Token ID: ", UserData.tokenId);
				Log.i("Tenant ID: ", UserData.tenentId);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		listener.onLoadingCompleted(this.success);
	}
}
