package edu.umkc.burrise.readjsonobjectwithasynctask;
/* References:
   1. http://www.javacodegeeks.com/2013/10/android-json-tutorial-create-and-parse-json-data.html
   2.
   3. http://www.tutorialspoint.com/android/android_json_parser.htm
   4. http://openweathermap.org/current

   Don't forget to add a network permission request to your Manifest.xml file.
 */
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static String JSONEXAMPLE = "JSON Example";
    private static String defaultURI = "http://api.openweathermap.org/data/2.5/weather?q=Leawood,KS&units=imperial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // onCreate is called on the GUI thread. You shouldn't
        //   access network resources on the GUI thread.
        //   There is runtime checks that prevent you from
        //   accidently doing this. The following code will
        //   disable these runtime checks. Don't do this with
        //   production code.

        // ****** Delete after adding new thread to handle network IO
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // ****** End Delete Section

        EditText weatherURI = (EditText) findViewById(R.id.weatherURI);
        weatherURI.setText(defaultURI);

        View temperatureButton = findViewById(R.id.getTemperatureButton);
        // This class implements the onClickListener interface.
        // Passing 'this' to setOnClickListener means the
        //   onClick method in this class will get called
        //   when the button is clicked.
        temperatureButton.setOnClickListener(this);

        displayTemperatureForURI(defaultURI);
    }

    private void displayTemperatureForURI(String defaultURI) {

        String rawWeatherData = getRawWeatherData(defaultURI);

        try {
            JSONObject fullJSONData = new JSONObject(rawWeatherData);
            Log.i(JSONEXAMPLE, fullJSONData.toString());
            JSONObject JSONWeatherObj = fullJSONData.getJSONObject("main");
            String temperature = JSONWeatherObj.getString("temp");

            TextView temperatureOutput = (TextView) findViewById(R.id.temperatureView);
            temperatureOutput.setText(temperature);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRawWeatherData(String uri) {

        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(uri);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(JSONEXAMPLE, "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    @Override
    public void onClick(View v) {
        // Get URI entered
        EditText weatherURI = (EditText) findViewById(R.id.weatherURI);
        displayTemperatureForURI(weatherURI.getText().toString());
    }
}