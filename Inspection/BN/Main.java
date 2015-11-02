//reference package at: https://github.com/klinker41/android-smsmms
//reference page at:  http://www.londatiga.net/it/how-to-create-android-image-picker/
package nelson.umkc.edu.bcctexter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Assert;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

public class Main extends ActionBarActivity {
    private final String copy = "copy", bundled = "bundled",
            trigger = "Trigger", boolTrigger = "BooleanTrigger",
            groupContent = "groupTent", editTextText = "",
            addGroupIntent = "addGroup", fromCamera = "From Camera",
            fromSDcard = "From SD Card", selectImage = "Select Image",
            MSGexception = "Draft a message and include contacts first.",
            conException = "Please select contacts first.", title = "BCC-SMS";
    private EditText editText, contactText;
    private TextView attachedType;
    private ImageView preview;

    private String addToDisplay = "", defaultTrigger = "";

    private static String triggerText ="";

    private ArrayList nameNo = new ArrayList();
    private ArrayList nameNoCopy = new ArrayList();
    private ArrayList contactError = new ArrayList();

    private Uri mImageCaptureUri;
    private static boolean mms = false;
    private static final int PICK_FROM_CONTACTS = 1, PICK_FROM_MESSAGES = 2,
            PICK_FROM_CAMERA = 4, PICK_FROM_FILE = 5;
    private static Bitmap bitmap   = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar AB = getSupportActionBar();
        AB.setTitle(title);
        AB.setIcon(R.drawable.ic_launcher);
        //initialize edit text for copied message and copy intent over
        editText = (EditText) (findViewById(R.id.messageEdit));
        //initialize contact text for displaying selected contacts
        contactText = (EditText) (findViewById(R.id.contactText));
        //initialize contacts button and click listener
        Button contacts_Button = (Button) (findViewById(R.id.contacts));
        contacts_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //switch activity to collect contacts
                Intent intent = new Intent(getBaseContext(), fetchContacts.class);
                startActivityForResult(intent, PICK_FROM_CONTACTS);
                contactText.setText("");
                addToDisplay="";
            }
        });

        //initialize text button and click listener
        Button text_Button = (Button) (findViewById(R.id.sendSMS));
        text_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //allow sending of texts --> clears if 0 errors
                try {
                    if (!mms)
                        sendSMS();
                    else
                        sendMMS();
                }
                catch (Exception EmptySMSException){
                    Toast.makeText(Main.this, EmptySMSException.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                reset();
            }
        });

        Button addGroup = (Button) (findViewById(R.id.addGroup));
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //allow addition of groups
                ArrayList<String> addGroup = getContactsToGroup();
                if (addGroup.size()<1){
                    Toast.makeText(Main.this, conException,
                            Toast.LENGTH_LONG).show();
                }else {
                    Intent groupTent = new Intent(getBaseContext(), saveGroups.class);
                    groupTent.putExtra(addGroupIntent, addGroup);
                    startActivity(groupTent);
                }
            }
        });

        final String [] items = new String [] {fromCamera, fromSDcard};
        ArrayAdapter<String> adapter  = new ArrayAdapter<> (this, android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(selectImage);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    final String fileAtt = "tmp_avatar_", fileExt = ".jpg", returnData = "return-data";
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory(),
                            fileAtt + String.valueOf(System.currentTimeMillis()) + fileExt);
                    mImageCaptureUri = Uri.fromFile(file);
                    try {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        intent.putExtra(returnData, true);
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.cancel();
                } else {
                    final String images = "image/*", complete = "Complete action using";
                    Intent intent = new Intent();
                    intent.setType(images);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, complete), PICK_FROM_FILE);
                }
            }
        });

        final AlertDialog dialog = builder.create();
        preview = (ImageView) findViewById(R.id.preview);
        (findViewById(R.id.attach)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        preview = (ImageView) (findViewById(R.id.preview));
        attachedType = (TextView) (findViewById(R.id.attachedText));

//only have keyboard up if user clicks edit text
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

//process sms & send it
    public void sendSMS() throws Exception{
        String message = editText.getText().toString();
        if (message.length() == 0 || contactText.getText().length() < 9) {
//dont send SMS if no message is provided
            Exception EmptySMSException = new Exception(MSGexception);
            throw EmptySMSException;
        }
        else {
            messageController messageController = new messageController();
            messageController.processMessage(triggerText, nameNoCopy, message, false);
        }
    }

//get mimetype processed in thumbnail
    public static String getMimeType(String fileUrl) throws java.io.IOException {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(fileUrl);
        return type;
    }

//send the MMS with image --> un-commented --> commented call on messageController sendMMS
    public void sendMMS() throws Exception{
        String message = editText.getText().toString();
        if (contactText.getText().length() < 9) {
//dont send MMS if no message is provided
            Exception EmptySMSException = new Exception(MSGexception);
            throw EmptySMSException;
        }
        else {
            messageController messageController = new messageController();
            Assert.assertNotNull(nameNoCopy);
            messageController.processMessage(triggerText, nameNoCopy, message, true);
        }
    }

 //get data from edit contacts and add to group
    public ArrayList<String> getContactsToGroup(){
        String contacts = "", tempContact = "";
        int counter = 0;
        ArrayList<String> contactGroup = new ArrayList<>();
        contacts = contactText.getText().toString();
        for (int i = 0; i < contacts.length(); i ++){
            switch (contacts.charAt(i)){
                case '\n':
                    contactGroup.add(counter, tempContact);
                    counter++;
                    tempContact = "";
                    break;
                case '=':
                    tempContact += ' ';
                    tempContact += '=';
                    tempContact += ' ';
                    break;
                default:
                    tempContact += contacts.charAt(i);
                    break;
            }
        }
        return contactGroup;
    }

//get info from intent
    public void extractIntent(int badContacts, ArrayList data){
        String contactInfo = "";
        int counter = 0;
        Iterator it = data.iterator();
        Assert.assertNotNull("Data extracted from intent is NULL", data);
        if (!data.isEmpty()) {
            while (it.hasNext()) {
                contactInfo = (String)it.next();
//setup properly to return valid numbers consistently
                messageController pC = new messageController();
                if(pC.isValidPhoneNo(contactInfo)&& !nameNoCopy.contains(contactInfo)){
//addToDisplay has a list of all working numbers --> use this data to sendSMS
                    addToDisplay+=(contactInfo  + "\n");
                    nameNoCopy.add(counter, contactInfo);
                    counter ++;
                }else{
//inform user of a bad contact
                    contactError.add(badContacts, contactInfo);
                    badContacts ++;
                }
                it.remove();
            }
            contactText.setText(addToDisplay);
        }
        if (badContacts > 0){
            Toast.makeText(Main.this, "The following " + badContacts +
                            " contacts could not be imported: " + contactError,
                    Toast.LENGTH_LONG).show();
        }
    }

//process data returned from contact selection and display it,  copy the contact
//arrayList for use in sending SMS
//reads back the saved messages to main
//if image returned handle it
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        int badContacts = 0;
        String path = "";
        if (requestCode == PICK_FROM_CONTACTS) {
            if (resultCode == RESULT_OK) {
                nameNo = (ArrayList)(data.getSerializableExtra(bundled));
                extractIntent(badContacts, nameNo);
            }
        }
        if (requestCode == PICK_FROM_MESSAGES) {
            if (resultCode == RESULT_OK) {
                String msg = data.getStringExtra(copy);
                editText.setText(msg);
            }
        }
        if (requestCode == PICK_FROM_FILE) {
//set bitmap if a file is selected
            if (true) {
                mImageCaptureUri = data.getData();
                path = getRealPathFromURI(mImageCaptureUri); //from Gallery
                if (path == null)
                    path = mImageCaptureUri.getPath(); //from File Manager
                if (path != null)
                    bitmap = BitmapFactory.decodeFile(path);
                mms = true;
            } else { //from Camera
                path = mImageCaptureUri.getPath();
                bitmap = BitmapFactory.decodeFile(path);
                mms = true;
            }
            bitmap  = getResizedBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
            preview.setImageBitmap(bitmap);
        }
    }
//returns the Bitmap of selected image resized appropriately
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

@SuppressWarnings("deprication")
//Returns the content path from the URI
    public String getRealPathFromURI(Uri contentUri) {
    final String attachType = "The attached type is a: ";
        String [] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri, proj, null, null,null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String indexPath = cursor.getString(column_index);
//to remove if only going to use images***************
        try {
            String retunredType = getMimeType(indexPath);
            if (retunredType!= null){
                attachedType.setText(attachType + retunredType);
            }
        }catch(IOException e){e.printStackTrace();}
//*****************************************************
        return cursor.getString(column_index);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_save_message:
                Intent savedMessages = new Intent(this, saveMessages.class);
                startActivityForResult(savedMessages, PICK_FROM_MESSAGES);
                return true;
            case R.id.menu_preferences:
                Intent settings = new Intent(this, preferences.class);
                startActivity(settings);
                return true;
/*            case R.id.set_default_app:
                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
                startActivity(intent);
                return true;*/
            case R.id.menu_save_group:
                Intent savedGroup = new Intent(this, saveGroups.class);
                startActivity(savedGroup);
                return true;
            case R.id.reset:
                reset();
                return true;
            case R.id.about:
                Intent about = new Intent(this, about.class);
                startActivity(about);
                return true;
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    public void reset(){
        editText.setText("");
        contactText.setText("");
        addToDisplay = "";
        nameNoCopy.clear();
        nameNo.clear();
        preview.setImageBitmap(null);
        attachedType.setText("");
        mms = false;
    }

    @Override
    public void onResume() {
//load preferences for trigger
        String savedMsg = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean triggerOn = preferences.getBoolean(boolTrigger, true);
        defaultTrigger = getResources().getString(R.string.trigger);
        triggerText = preferences.getString(trigger, defaultTrigger);
        savedMsg = preferences.getString(editTextText, "");
//get extras if sent from savedGroups
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            ArrayList returnedGroup = (ArrayList) extras.getSerializable(groupContent);
            if (returnedGroup != null && nameNoCopy.size() == 0) {
                extractIntent(0, returnedGroup);
            }
        }
        super.onResume();
    }

    @Override
    public void onPause() {
//set preferences for trigger
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(trigger, defaultTrigger);
        editor.putString(editTextText, editText.getText().toString());
        //editor.putBoolean(triggerOn, triggerSet);
        editor.commit();
        super.onPause();
    }

}
