package edu.umkc.burrise.capturephotofromcamera;
/*
  This app demonstrates how to capture a photo from the camera app
    that is already installed on the phone.
  Run the app. Clicking the take picture button will start the
    camera app installed on the phone. Use the camera app to take
    a picture. When you are satisfied with the picture taken,
    select the checkmark and control will be returned to this
    app. The
  References:
  1. http://developer.android.com/training/camera/photobasics.html

 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView mImageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decButton = findViewById(R.id.TakePictureButton);
        decButton.setOnClickListener(this);

        mImageView = (ImageView) findViewById(R.id.ImageViewComponent);
        decButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
           The camera application returns a small Bitmap in the extras
             under the key "data". It's not the full image.
           The camera application saves a full-size photo if you give it
             a file to save into. You must provide a fully qualified file
             name where the camera app should save the photo.
           Generally, any photos that the user captures with the device camera
             should be saved on the device in the public external storage so they
             are accessible by all apps. The proper directory for shared photos
             is provided by getExternalStoragePublicDirectory(), with the
             DIRECTORY_PICTURES argument. Because the directory provided by this
             method is shared among all apps, reading and writing to it requires
             the READ_EXTERNAL_STORAGE and WRITE_EXTERNAL_STORAGE permissions,
             respectively. The write permission implicitly allows reading, so if
             you need to write to the external storage then you need to request only
             write permission.
           If you'd like the photos to remain private to your app only, you can
             instead use the directory provided by getExternalFilesDir(). On Android
             4.3 and lower, writing to this directory also requires the
             WRITE_EXTERNAL_STORAGE permission. Beginning with Android 4.4, the
             permission is no longer required because the directory is not accessible
             by other apps, so you can declare the permission should be requested only
             on the lower versions of Android by adding the maxSdkVersion attribute:
             <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
                     android:maxSdkVersion="18" />
         */
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }
}
