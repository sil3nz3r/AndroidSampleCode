package edu.umkc.burrise.countmuchmore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.os.Bundle;
//import android.support.v7.view.ActionMode;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/* If you don't mind limiting this app to users running 11 or higher,
   extend Activity and change the app theme in the Manifest to
   one that supports an action bar. I believe the default theme does
   support an action bar. So, if you extend Activity
   go to the manifest file and comment out or remove
   android:theme attribute.
 */
public class MainActivity extends ActionBarActivity implements OnClickListener {
    public final static String EXTRA_DATA = "edu.umkc.burrise.countmuchmore.ABOUTDATA";
    private final static String TAG = "Count Much More";

    private int count = 0;

    // The following two variables used for the contextual action mode menu
    private ActionMode mActionMode;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.contextIncrement:
                    count++;
                    updateCount();

                    mode.finish(); // Action picked, so close the Contextual Action Bar(CAB)
                    return true;
                case R.id.contextDecrement:
                    count--;
                    updateCount();

                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The following will print to LogCat.
        Log.i(TAG, "Starting onCreate...");
        setContentView(R.layout.activity_main);

        // Setup contectual action mode menu
        // Long clicking background of main activity will
        // bring up a contectual menu. Note, we could have
        // done the same for any view component
        // Example:
        //    View countText = findViewById(R.id.count_label);
        //    countText.setOnLongClickListener(new View.OnLongClickListener() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout);
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            // Called when the user long-clicks on someView
            public boolean onLongClick(View view) {
                // mActionMode is set back to null
                //    above when the context menu disappears.
                if (mActionMode != null) {
                    return false;
                }

                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = startActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
            }
        });

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

        switch (item.getItemId()) {
            case R.id.reset:
                count = 0;
                updateCount();
                return true;
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                intent.putExtra(EXTRA_DATA, "extra data or parameter you want to pass to activity");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
