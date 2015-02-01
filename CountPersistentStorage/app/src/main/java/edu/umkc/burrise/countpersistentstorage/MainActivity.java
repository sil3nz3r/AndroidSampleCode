package edu.umkc.burrise.countpersistentstorage;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;

// This app demonstrates the lifecyle of an application and
//   persistent storage.
// Summary: If you want to save app state during screen rotation or
//   getting kicked out of memory, use onSaveInstanceState(). To
//   save data when app is closed by the user (by pressing the back
//   button during the last Activity), save state in shared
//   preferences in onPause() and restore in onCreate().
// Ref: http://eigo.co.uk/labs/managing-state-in-an-android-activity/
public class MainActivity extends ActionBarActivity {

    private static final String TAG = "Count Much More";

    private static final String PREFS_NAME = "PrefsFile";
    private static final String TENX_KEY = "TenXKey";

    private int count = 0;
    private int step = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The following will print to LogCat.
        Log.i(TAG, "Starting onCreate...");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("count");
        }

        View incrementButton = findViewById(R.id.inc_button);
        incrementButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count + step;
                updateCount();
            }
        });

        View decrementButton = findViewById(R.id.dec_button);
        decrementButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count - step;
                updateCount();
            }
        });

        CheckBox checkTenX = (CheckBox) findViewById(R.id.countByTen);
        // Example of reading from persistent storage.
        // Initialize the value of the 10X checkbox. Default value
        //   is false.
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        // If user hasn't checked or unchecked the box (see below)
        //   the value will not exist and the default
        //   value will be false (the second argument).
        boolean countByTen = settings.getBoolean(TENX_KEY, false);
        checkTenX.setChecked(countByTen);

        // Ternary operator
        step = (countByTen ? 10:1);

        checkTenX.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    step = 10;
                }
                else {
                    step = 1;
                }
                // Example of writing to persistent storage.
                // Save the value of the checkbox in persistent storage.
                // Data saved to shared preferences stays around
                // even between reinstalls of the application.
                // If you want to clear the stored data you will
                // need to go to (on the device): Settings/Applications/
                // Manage Applications / <name of app> / Clear Data
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(TENX_KEY, ((CheckBox) v).isChecked());
                // Commit the edits
                editor.commit();
            }
        });

        updateCount();
    }

    private void updateCount() {
        TextView t = (TextView)findViewById(R.id.count_value);
        t.setText(Integer.toString(count));
    }

    // Lifecycle methods follow

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    // Note that onDestroy() may not be called if
    // the need for RAM is urgent (e.g., an incoming
    // phone call), but the activity will still be
    // shut down. Consequently, it's a good idea
    // to save state that needs to persist between
    // sessions in onPause().
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    // Note that onDestroy() may not be called if
    // the need for RAM is urgent (e.g., an incoming
    // phone call), but the activity will still be
    // shut down. Consequently, it's a good idea
    // to save state that needs to persist between
    // sessions in onPause().
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    // "icicle" is sometimes used as the name of the
    // parameter because onSaveInstanceState() used to
    // be called onFreeze().
    // The Bundle updated here is the same one passed
    //   to onCreate() above.
    // This method isn't guaranteed to be called. If it
    //   is critical that data be saved, save it in
    //   persistent storage in onPause() rather than here
    //   because this method will not be called in every
    //   situation as described in its documentation.
    // Summary: save data that should persist while the
    //   application is running here. Save data that should
    //   persist between application runs in persistent storage.
    @Override
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);

        Log.i(TAG, "onSaveInstanceState()");
        icicle.putInt("count", count);
    }

    // Note, by convention most apps restore state in onCreate()
    // This method isn't used often.
    @Override
    protected void onRestoreInstanceState(Bundle icicle) {
        super.onRestoreInstanceState(icicle);
        Log.i(TAG, "onRestoreInstanceState()");
    }

}
