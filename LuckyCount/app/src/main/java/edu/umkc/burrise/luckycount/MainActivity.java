package edu.umkc.burrise.luckycount;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    static final private String TAG = "LuckyCount";

    private int [] unluckyNumbers = {4,5,6,13};
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
                count++;
                while (!isLucky(count))
                    count++;
                break;
            case R.id.dec_button:
                count--;
                while (!isLucky(count))
                    count--;
                break;
        }
        updateCount();
    }

    private boolean isLucky(int count) {
        for(int i = 0; i < unluckyNumbers.length; i++)
            if (count == unluckyNumbers[i])
                return false;
        return true;
    }
}
