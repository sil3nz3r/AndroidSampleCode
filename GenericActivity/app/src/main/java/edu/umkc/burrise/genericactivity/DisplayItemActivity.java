package edu.umkc.burrise.genericactivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;


public class DisplayItemActivity extends ActionBarActivity {
    static final private String TAG = "GenericActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_item);

        // Receive extra data or parameter
        Intent intent = getIntent();
        String imageLabel = intent.getStringExtra(MainActivity.EXTRA_DATA_KEY);

        // Set label
        TextView label = (TextView)this.findViewById(R.id.imageTitle);
        label.setText(imageLabel);

        // Set image
        try
        {
            ItemGateway items = ItemGateway.getInstance();
            String imageFileName = items.getImageFileName(imageLabel);
            Log.i(TAG, "Reading: " + imageFileName);

            // get input stream
            InputStream ims = getAssets().open(imageFileName);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);

            ImageView image = (ImageView)this.findViewById(R.id.image);
            // set image to ImageView
            image.setImageDrawable(d);
        }
        catch(IOException ex)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Problem reading image from asset folder",
                    Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
