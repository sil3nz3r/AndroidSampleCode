package edu.umkc.burrise.audioexample;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;

/* This program demonstrates how to play audio.
   References: http://developer.android.com/guide/topics/media/mediaplayer.html
      http://developer.android.com/reference/android/media/MediaPlayer.html
   You will need to create a raw resource folder and copy and paste
     a media file to this folder.
   To create a raw resource folder: (1) right click app, (2) select new/
     android resource folder, (3) specify directory name raw and resource
     type raw.
   To create an assets folder: (1) right click app, (2) select new/
     folder/assets folder.
 */

public class MainActivity extends ActionBarActivity {
    private MediaPlayer objPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objPlayer = MediaPlayer.create(this,R.raw.manmoon);
    }

    // Activity layout file specifies this method for handling button
    //   click events.
    public void onBtnClicked(View v){
        // Since there is only one button, I don't have to
        //   check here. Doing it to demonstrate what to do
        //   if this method handles events from more than one
        //   component.
        if(v.getId() == R.id.playButton){
            // Use one of the below to play the file

            //objPlayer.start();
            // Example of playing a media file from assets folder
            playFromAssets(this,"manmoon.mp3");
        }
    }

    private void playFromAssets(Context context, String fileName) {
        try {
            AssetFileDescriptor afd = context.getAssets().openFd(fileName);
            // MediaPlayer is stateful. See
            //   http://developer.android.com/reference/android/media/MediaPlayer.html
            //   for valid states for calling setDataSource().
            objPlayer.reset();
            objPlayer.setDataSource(
                    afd.getFileDescriptor(),
                    afd.getStartOffset(),
                    afd.getLength()
            );
            afd.close();
            // Prepare could take some time. It shouldn't be called
            //   on UI thread.
            objPlayer.prepare();
            objPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop(){
        // The player allocates operating system resources.
        // If you repeatedly recreate a player without stopping
        //   and releasing existing ones, you will run out of
        //   resources.
        objPlayer.stop();
        objPlayer.release();
    }

}
