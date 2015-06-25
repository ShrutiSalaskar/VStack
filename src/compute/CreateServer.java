package compute;

import com.example.vstack.Navigation;
import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.R;

import openstack_connection.AddInstance;
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

public class CreateServer extends ActionBarActivity implements OnLoadingCompleted {
    private Button UpdateButton ;
    private EditText name ;
    private EditText flavor ;
    private TextView id;
    private AddInstance req;
    String value;
    protected volatile ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_server);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_server, menu);

        name = (EditText) findViewById(R.id.name);
        flavor = (EditText) findViewById(R.id.flavor);
        UpdateButton = (Button) findViewById(R.id.update);
        value = getIntent().getStringExtra("ID");
        id = (TextView) findViewById(R.id.id);
        id.setText(value);
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
        req = new AddInstance();
        loading = ProgressDialog.show(this, "Please wait", "Loading the data...", true);
        req.launch(CreateServer.this, value, name.getText().toString(),flavor.getText().toString());
        loading.setCancelable(false);
    }

    @Override
    public void onLoadingCompleted(Boolean successful) {
        loading.dismiss();
        if (successful) {
            new Toast(this).makeText(this,"Server Added successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,Navigation.class);
            this.startActivity(intent);
            this.finish();
           } else {
            new Toast(this).makeText(this,"Sorry, server could not be created", Toast.LENGTH_LONG).show();
        }
    }
}