package edu.umkc.burrise.square;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.updateButton) {
            EditText inputText = (EditText)findViewById(R.id.inputText);
            int i = Integer.parseInt(inputText.getText().toString());
            i = i * i;
            TextView label = (TextView)findViewById(R.id.textLabel);
            label.setText(Integer.toString(i));
        }
    }
}