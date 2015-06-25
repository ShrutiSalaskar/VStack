package com.example.vstack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import openstack_connection.KeystoneRequest;


public class MainActivity extends ActionBarActivity implements OnLoadingCompleted {
    private Button loginButton ;
    private EditText host;
    private EditText username ;
    private EditText password ;
    private KeystoneRequest req;
    protected volatile ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        host = (EditText) findViewById(R.id.host);
        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.signin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void signIn() {
        UserData.host = "http://"+host.getText().toString();
        req = new KeystoneRequest();
        req.setHost(host.getText().toString());
        req.login(MainActivity.this, username.getText().toString(), password.getText().toString());
        loading = ProgressDialog.show(this, "Please wait", "Loading the data...", true);
        loading.setCancelable(false);
    }

    @Override
    public void onLoadingCompleted(Boolean successful) {
        loading.dismiss();
        if (successful) {
            new Toast(this).makeText(this,"Log in successful", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,Navigation.class);
            this.startActivity(intent);
        //    new Toast(this).makeText(this,UserData.tokenId, Toast.LENGTH_LONG).show();
        } else {
            new Toast(this).makeText(this,"Sorry, could not login", Toast.LENGTH_LONG).show();
        }
    }
}