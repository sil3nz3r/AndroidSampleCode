package edu.umkc.burrise.luckycount;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    static final private String TAG = "LuckyCount";

    private CountModel model = new CountModel();

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
        t.setText(Integer.toString(model.getValue()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inc_button:
                model.increment();
                while (!model.isLucky())
                    model.increment();
                break;
            case R.id.dec_button:
                model.decrement();
                while (!model.isLucky())
                    model.decrement();
                break;
        }
        updateCount();
    }
}
