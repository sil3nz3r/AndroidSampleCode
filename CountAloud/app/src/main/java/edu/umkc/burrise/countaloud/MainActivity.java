package edu.umkc.burrise.countaloud;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

// This program demonstrates Preferences.
// Ref: http://developer.android.com/guide/topics/ui/settings.html
//
public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View incButton = findViewById(R.id.inc_button);
        incButton.setOnClickListener(this);

        View decButton = findViewById(R.id.dec_button);
        decButton.setOnClickListener(this);

        updateUI();

        // This app shows how to set user preferences. Here is an
        //  example of how you would refer to a user preference:
        boolean announce = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(this.getString(R.string.announceKey), false);
        // The following key is hardcoded. I should make it a string reference
        //   like annouceKey above.
        String maxFailedAttempts = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("loginAttemptsPreferenceKey", "this parameter is not needed. preference will always have a value");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inc_button:
                count++;
                updateUI();
                break;
            case R.id.dec_button:
                count--;
                updateUI();
                break;
        }
    }

    private void updateUI() {
        TextView c = (TextView) findViewById(R.id.count_view);
        c.setText(Integer.toString(count));
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

        // If "Settings" selected, show the preferences activity
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, AppPreferencesActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
