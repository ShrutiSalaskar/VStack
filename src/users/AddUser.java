package users;

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

import com.example.vstack.Navigation;
import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.R;
import com.example.vstack.UserData;

import openstack_connection.Addusers;


public class AddUser extends ActionBarActivity implements OnLoadingCompleted {
    private Button addButton ;
    private EditText name ;
    private EditText email ;
    private Addusers req;
    protected volatile ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_user, menu);

        
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        addButton = (Button) findViewById(R.id.add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
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

    public void add() {
        req = new Addusers();
        loading = ProgressDialog.show(this, "Please wait", "Loading the data...", true);
        req.adduser(AddUser.this, name.getText().toString(), email.getText().toString());
        loading.setCancelable(false);
    }

    @Override
    public void onLoadingCompleted(Boolean successful) {
        loading.dismiss();
        if (successful) {
            new Toast(this).makeText(this,"User Added", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Navigation.class);
            this.startActivity(intent);
            this.finish();       
        } else {
            new Toast(this).makeText(this,"Sorry, user cannot be added", Toast.LENGTH_LONG).show();
        }
    }
}