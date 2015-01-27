package edu.umkc.burrise.countmuchmore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/* If you don't mind limiting this app to users running 11 or higher,
   extend Activity and change the app theme in the Manifest to
   HoloAppTheme (which is a new one I defined in styles.xml).
 */
public class MainActivity extends ActionBarActivity implements OnClickListener {

    static final private String TAG = "Count Much More";

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The following will print to LogCat.
        Log.i(TAG, "Starting onCreate...");
        setContentView(R.layout.activity_main);

        View incrementButton = findViewById(R.id.inc_button);
        // This class implements the onClickListener interface.
        // Passing 'this' to setOnClickListener means the
        //   onClick method in this class will get called
        //   when the button is clicked.
        incrementButton.setOnClickListener(this);

        View decrementButton = findViewById(R.id.dec_button);
        decrementButton.setOnClickListener(this);

        updateCount();
    }

    private void updateCount() {
        TextView t = (TextView)findViewById(R.id.count_value);
        t.setText(Integer.toString(count));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inc_button:
                // Start count over if user tries to increment
                //   beyond 9.
                if (count == 9) {
                    // Builder is an inner class so we have to qualify it
                    // with its outer class: AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    //builder.setTitle("Dialog box");
                    builder.setMessage("Max count reached. Start over?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            count = 0;
                            // Note, you have to call update count here because.
                            //   the call builder.show() below is non blocking.
                            updateCount();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Do nothing
                        }
                    });
                    builder.show();
                }
                else {
                    count++;
                }
                break;
            case R.id.dec_button:
                count--;
                break;
        }
        updateCount();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
