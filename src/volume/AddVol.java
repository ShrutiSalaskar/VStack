package volume;

import com.example.vstack.Navigation;
import com.example.vstack.OnLoadingCompleted;
import com.example.vstack.R;

import openstack_connection.AddVolume;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddVol extends ActionBarActivity implements OnLoadingCompleted {
    private Button addButton ;
    private EditText size;
    private EditText name ;
    private AddVolume req;
    protected volatile ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vol);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_vol, menu);

        name = (EditText) findViewById(R.id.name);
        size = (EditText) findViewById(R.id.size);
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

    public void add()
    {
        req = new AddVolume();
        req.AddVol(AddVol.this,  name.getText().toString(), size.getText().toString());
        loading = ProgressDialog.show(this, "Please wait", "Loading the data...", true);
        loading.setCancelable(false);
    }
    @Override
    public void onLoadingCompleted(Boolean successful) {
        loading.dismiss();
        if (successful) {
            new Toast(this).makeText(this,"volume Created", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Navigation.class);
            this.startActivity(intent);
            this.finish();
        } else {
            new Toast(this).makeText(this,"Sorry, volume could not be created", Toast.LENGTH_LONG).show();
        }
    }
}