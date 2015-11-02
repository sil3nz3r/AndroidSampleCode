package com.delacourt.pheight.realmstatusnotify;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FileIO {
    //Populates variable from file
    public <T> T loadVariableFromFile( String fileName, Context context) throws IOException, ClassNotFoundException {
        T returnVariable = null;
        InputStream inputStream = context.openFileInput(fileName);
        if (inputStream != null) {
            FileInputStream fileIn = context.openFileInput(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            returnVariable = (T) in.readObject();
            in.close();
        }
        inputStream.close();

        return returnVariable;
    }

    // Saves variable data to file for later retrieval.
    public <T> void outputToFile(T variableName, String fileName, Context context) throws IOException {
        FileOutputStream fileOut = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
        outputStream.writeObject(variableName);
        outputStream.flush();
        outputStream.close();
        fileOut.close();

    }

}
