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
  2. http://www.tutorialforandroid.com/2010/10/take-picture-in-android-with.html

 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageView = null;
    private File mCurrentPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View pictureButton = findViewById(R.id.TakePictureButton);
        pictureButton.setOnClickListener(this);

        mImageView = (ImageView) findViewById(R.id.ImageViewComponent);
    }

    @Override
    public void onClick(View v) {
        Intent takePictureIntent = takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = getUniqueFileName();
            } catch (IOException ex) {
                // Error occurred while creating the File
                photoFile = null;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    // Generate a unique file name
    private File getUniqueFileName() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PHOTO_" + timeStamp;
        File storageDir = new File(Environment.getExternalStorageDirectory(), getPackageName());
        if (!storageDir.exists())
            storageDir.mkdir();

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        mCurrentPhoto = image;
        return image;
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

            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(mCurrentPhoto));
                int nh = (int) ( imageBitmap.getHeight() * (512.0 / imageBitmap.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(imageBitmap, 512, nh, true);
                mImageView.setImageBitmap(scaled);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
