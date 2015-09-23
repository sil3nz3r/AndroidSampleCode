package edu.umkc.burrise.genericactivity;
/* This app demonstrates how to create and display an
     activity with custom (not hardcoded) data.
   It also demonstrates how to use assets.
   Assets are an alternative to resources for
     storing data with your app.
   More info on resources vs assets: http://stackoverflow.com/questions/5583608/difference-between-res-and-assets-directories
   How to create assets folder in Android Studio: http://stackoverflow.com/questions/18302603/where-to-place-assets-folder-in-android-studio
   Note: I wasn't able to drag and drop into my asset folder, but
     I was able to copy and paste (right click).

 */

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ListActivity {
    public final static String EXTRA_DATA_KEY = "edu.umkc.burrise.genericactivity.itemkey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ItemGateway items = ItemGateway.getInstance();
        items.add(new Item("Campus","campus.jpg"));
        items.add(new Item("Track","outdoortrack.jpg"));
        items.add(new Item("Student Union","studentunion.jpg"));

        ArrayList<String> listViewArray = new ArrayList<String>();
        // These have to match those above. It would be better to
        //   have one array of data for both the listview and
        //   the gateway data, but I wanted to keep this example
        //   simple.
        listViewArray.add("Campus");
        listViewArray.add("Track");
        listViewArray.add("Student Union");

        // When using ListActivity, you set the list adapter by
        //  calling the inherited method setListAdapter() rather than
        //  getting a reference to the ListView and calling
        //  set adapter on the actual ListView.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , listViewArray);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String itemDescription = (String) getListAdapter().getItem(position);

        Intent intent = new Intent(this, DisplayItemActivity.class);
        intent.putExtra(EXTRA_DATA_KEY, itemDescription);
        startActivity(intent);
    }
}
