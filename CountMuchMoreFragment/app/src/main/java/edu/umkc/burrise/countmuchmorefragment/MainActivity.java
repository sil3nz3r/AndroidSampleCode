package edu.umkc.burrise.countmuchmorefragment;

// When committing (VCS / Commit Changes) on the Commit
//   Changes dialog box, specify the author as: Your Name <userID@machine.com>

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        private int count = 0;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            View incrementButton = rootView.findViewById(R.id.inc_button);
            // This class implements the onClickListener interface.
            // Passing 'this' to setOnClickListener means the
            //   onClick method in this class will get called
            //   when the button is clicked.
            incrementButton.setOnClickListener(this);

            View decrementButton = rootView.findViewById(R.id.dec_button);
            decrementButton.setOnClickListener(this);

            return rootView;
        }

        private void updateCount() {
            // Note, getView() only works after onCreateView() executes.
            //   getView() can't be used in onCreateView().
            TextView t = (TextView)getView().findViewById(R.id.count_value);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
    }

}
