package projects;

import com.example.vstack.Navigation;
import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.R;
import com.example.vstack.UserData;

import openstack_connection.UpdateProjects;
import openstack_connection.updateusers;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateProject extends ActionBarActivity implements OnLoadingCompleted {
    private Button UpdateButton ;
    private EditText name ;
    private EditText description ;
    private TextView id;
    private UpdateProjects req;
    String valueid;
    String valuename;
    String valuedes;
    protected volatile ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_project);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update_project, menu);

        valuename = getIntent().getStringExtra("NAME");
        name = (EditText) findViewById(R.id.name);
        name.setText(valuename);
        
        valuedes = getIntent().getStringExtra("DES");
        description = (EditText) findViewById(R.id.description);
        description.setText(valuedes);
        
        UpdateButton = (Button) findViewById(R.id.update);
        
        valueid = getIntent().getStringExtra("ID");
        id = (TextView) findViewById(R.id.id);
        id.setText(valueid);
        
        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
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

    public void update() {
        req = new UpdateProjects();
        loading = ProgressDialog.show(this, "Please wait", "Loading the data...", true);
        req.updateprojects(UpdateProject.this, valueid, name.getText().toString(), description.getText().toString());
        loading.setCancelable(false);
    }

    @Override
    public void onLoadingCompleted(Boolean successful) {
        loading.dismiss();
        if (successful) {
            new Toast(this).makeText(this,"Project Updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Navigation.class);
            this.startActivity(intent);
            this.finish();
        } else {
            new Toast(this).makeText(this,"Sorry, Project cannot be updated", Toast.LENGTH_LONG).show();
        }
    }
}