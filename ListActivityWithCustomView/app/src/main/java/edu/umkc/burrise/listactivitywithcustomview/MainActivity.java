package edu.umkc.burrise.listactivitywithcustomview;
// This example demonstrates two Android features:
// 1. ListActivity
// 2. Creating a custom list view

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// If you inherit from ListActivity, the layout must have a ListView
//   component with id @android:id/list.
public class MainActivity extends ListActivity  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<LunchItem> items = new ArrayList<LunchItem>();

        items.add(new LunchItem("Burger",
                "Monster Burger",
                R.drawable.burger));

        items.add(new LunchItem("Pizza",
                "Power Pizza",
                R.drawable.pizza));

        items.add(new LunchItem("Angioplasty",
                "Artery Plus",
                R.drawable.angioplasty
        ));

        // When using ListActivity, you set the list adapter by
        //  calling the inherited method setListAdapter() rather than
        //  getting a reference to the ListView and calling
        //  set adapter on the actual ListView.
        setListAdapter(new MyArrayAdapter(this,
                items));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        LunchItem item = (LunchItem) getListAdapter().getItem(position);

        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show();

    }
}

class MyArrayAdapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<LunchItem> values;

    public MyArrayAdapter(Context context, ArrayList<LunchItem> values) {
        super(context, R.layout.custom_list_element, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.custom_list_element, parent, false);

        TextView titleView = (TextView) rowView.findViewById(R.id.title);
        titleView.setText(values.get(position).title);

        TextView descriptionView = (TextView) rowView.findViewById(R.id.description);
        descriptionView.setText(values.get(position).description);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        imageView.setImageResource(values.get(position).imageResource);

        return rowView;
    }
}


class LunchItem {
    public String title;
    public String description;
    public int imageResource;


    // Parameters:
    //   title - business name
    //   description - short description of business
    //   imageResource - resource ID for drawable icon representing the item
    public LunchItem(String title, String description, int imageResource) {
        this.title = title;
        this.description = description;

        this.imageResource = imageResource;
    }

}
