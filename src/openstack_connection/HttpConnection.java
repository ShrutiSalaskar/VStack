package openstack_connection;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.example.vstack.UserData;

public class HttpConnection extends AsyncTask<Object, Integer, Boolean> {
    String host = "";
    HttpClient client = new DefaultHttpClient();
    HttpPost post = new HttpPost();
    HttpGet get = new HttpGet();
    HttpDelete delete = new HttpDelete();
    HttpPut put = new HttpPut();
    HttpPatch patch;
    HttpResponse response;
    protected JSONObject jsonResponse;
    Boolean isGet = false;
    Boolean isPost = false;
    Boolean isDelete = false;
    Boolean isPut = false;
    Boolean isPatch = false;
    public Boolean success;

    public void setHost(String host) {
        this.host = host;
    }

    public Boolean setPostRequest(String urlPostfix, JSONObject obj) {
        try {
            StringEntity stringEntity = new StringEntity(obj.toString());
            post = new HttpPost(UserData.host+urlPostfix);
            post.setEntity(stringEntity);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            isPost = true;

        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }
    
    public Boolean setPostRequestAfterlogin(String urlPostfix, JSONObject obj) {
        try {
            StringEntity stringEntity = new StringEntity(obj.toString());
            post = new HttpPost(UserData.host+urlPostfix);
            post.setEntity(stringEntity);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            post.setHeader("x-auth-token", UserData.tokenId);
            isPost = true;

        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean setDeleteRequest(String urlPostfix, JSONObject obj) {
        try {
  //          StringEntity stringEntity = new StringEntity(obj.toString());
            delete = new HttpDelete(UserData.host+urlPostfix);
    //        delete.setEntity(stringEntity);
            delete.setHeader("Accept", "application/json");
            delete.setHeader("Content-type", "application/json");
            delete.setHeader("x-auth-token", UserData.tokenId);
            isDelete = true;

        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }
    
    public Boolean setPutRequest(String urlPostfix, JSONObject obj) {
        try {
            StringEntity stringEntity = new StringEntity(obj.toString());
            put = new HttpPut(UserData.host+urlPostfix);
            put.setEntity(stringEntity);
            put.setHeader("Accept", "application/json");
            put.setHeader("Content-type", "application/json");
            put.setHeader("x-auth-token", UserData.tokenId);
            isPut = true;

        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }
    public Boolean setPatchRequest(String urlPostfix, JSONObject obj) {
        try {
            StringEntity stringEntity = new StringEntity(obj.toString());
            patch = new HttpPatch(UserData.host+urlPostfix);
            patch.setEntity(stringEntity);
            patch.setHeader("Accept", "application/json");
            patch.setHeader("Content-type", "application/json");
            patch.setHeader("x-auth-token", UserData.tokenId);
            isPatch = true;

        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }
    
    public Boolean setGetRequest(String urlPostfix, JSONObject obj) {
        try {
//            StringEntity stringEntity = new StringEntity(obj.toString());
            get = new HttpGet(UserData.host+urlPostfix);
//            get.setEntity(stringEntity);
            get.setHeader("Accept", "application/json");
            get.setHeader("Content-type", "application/json");
            get.setHeader("x-auth-token", UserData.tokenId);
            isGet = true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        try {
            if (isGet) {
                Log.i("Running: ","Running get request");
                response = client.execute(get);
            } 
            else if(isPut){
                Log.i("Running: ","Running put request");
                response = client.execute(put);
            }
            else if(isPatch){
                Log.i("Running: ","Running patch request");
                response = client.execute(patch);
            }
            else if(isDelete){
                Log.i("Running: ","Running delete request");
                response = client.execute(delete);
            }
            else {
                Log.i("Running: ","Running post request");
                response = client.execute(post);
            }
            
            int responseStatus = response.getStatusLine().getStatusCode();
            Log.i("HTTPResponse","The response is: "+responseStatus);
            if (!isDelete){
            	String jsonString = EntityUtils.toString(response.getEntity());
            	Log.i("Recieved JSON Object: ", jsonString);
            	jsonResponse = new JSONObject(jsonString);
            }
            if (responseStatus == HttpStatus.SC_OK || responseStatus == HttpStatus.SC_NO_CONTENT
            		|| responseStatus == HttpStatus.SC_CREATED || responseStatus == HttpStatus.SC_ACCEPTED) {  //If status 200
                success = true;
            } else {
                success = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return success;
    }
}
class HttpPatch extends HttpPost {
    public static final String METHOD_PATCH = "PATCH";

    public HttpPatch(final String url) {
        super(url);
    }

    @Override
    public String getMethod() {
        return METHOD_PATCH;
    }
}