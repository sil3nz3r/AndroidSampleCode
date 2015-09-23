package edu.umkc.burrise.listviewexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import static junit.framework.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener, OnItemClickListener {

    private ArrayAdapter<String> adapter;

    // Any List Interface Data Structure
    private ArrayList<String> listItems = new ArrayList<String>();;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View prevButton = findViewById(R.id.button1);
        prevButton.setOnClickListener(this);

        ListView listView = (ListView)this.findViewById(R.id.listOfSomething);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , listItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    // This is for button clicks
    @Override
    public void onClick(View arg0) {
        Assert.assertNotNull(arg0);
        // Get string entered
        TextView tv = (TextView) findViewById(R.id.editText1);
        // Add string to underlying data structure
        listItems.add(tv.getText().toString());
        // Notify adapter that underlying data structure changed
        adapter.notifyDataSetChanged();
    }

    // This is for selecting an item from the list
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Get item from ListView
        String item = (String) parent.getItemAtPosition(position);
        String text = "You selected item " + position +
                " value = " + item;
        // Use a toast message to show which item selected
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
