package volume;

import com.example.vstack.Navigation;
import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.R;
import com.example.vstack.UserData;

import openstack_connection.UpdateVol;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Update_vols extends ActionBarActivity implements OnLoadingCompleted {
    private Button UpdateButton ;
    private EditText name ;
    private TextView id;
    private UpdateVol req;
    String valueid;
    String valuename;
    
    protected volatile ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vol);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update_vol, menu);

        valuename = getIntent().getStringExtra("NAME");
        name = (EditText) findViewById(R.id.name);
        name.setText(valuename);
        
        
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
        req = new UpdateVol();
        loading = ProgressDialog.show(this, "Please wait", "Loading the data...", true);
        req.update_vols(Update_vols.this, valueid, name.getText().toString());
        loading.setCancelable(false);
    }

    @Override
    public void onLoadingCompleted(Boolean successful) {
        loading.dismiss();
        if (successful) {
            new Toast(this).makeText(this,"Volume Updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Navigation.class);
            this.startActivity(intent);
            this.finish();
        } else {
            new Toast(this).makeText(this,"Sorry,Volume could not be updated", Toast.LENGTH_LONG).show();
        }
    }
}