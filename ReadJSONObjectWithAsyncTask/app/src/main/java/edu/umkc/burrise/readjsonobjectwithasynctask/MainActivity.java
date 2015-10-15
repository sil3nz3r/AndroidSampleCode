package edu.umkc.burrise.readjsonobjectwithasynctask;
/* Note, this app has a product key in OpenWeatherService that needs to be changed before
 *   it will run. Remove c's from beginning and end. */
/* References:
   1. http://www.javacodegeeks.com/2013/10/android-json-tutorial-create-and-parse-json-data.html
   2.
   3. http://www.tutorialspoint.com/android/android_json_parser.htm
   4. http://openweathermap.org/current

   Don't forget to add a network permission request to your Manifest.xml file.

   This program has a decent design. It has three code modules, each with
      a specific concern (good example of separation of concerns).
   An older version of this program had similar functionality but a
      weak design. It is available at:
      https://github.com/burrise/AndroidSampleCode/tree/31a0f99d4edb94c28684e2412432801c2930ded7/ReadJSONObjectWithAsyncTask/app/src/main/java/edu/umkc/burrise/readjsonobjectwithasynctask

 */
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import edu.umkc.burrise.OpenWeatherData.OpenWeatherService;

import junit.framework.Assert;
import junit.framework.Assert.*;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();
    private static final String DEFAULTCITYNAME = "Kansas%20City,MO";


    // First parm is input type to doInBackground
    // Second parm is input type to onProgressUpdate
    // Third parm is return type of doInBackground and
    //    input type of onPostExecute
    private class FetchWeatherTask extends AsyncTask<String, Void, String> {

        // Not sure what the three dots mean? See: http://stackoverflow.com/questions/3158730/java-3-dots-in-parameters?rq=1
        protected String doInBackground(String... parms) {

            // parms[0] is first parm, etc.
            OpenWeatherService weatherService = new OpenWeatherService(parms[0]);
            try {
                String temperature = weatherService.getTemperature();
                return temperature;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Not available";
        }

        protected void onProgressUpdate(Void... values) {

        }

        //  invoked on the UI thread after the background computation finishes
        protected void onPostExecute(String temperature) {
            // Example assert statements
            //Assert.assertNull(temperature);
            Assert.assertNotNull("Error: temperature is null",temperature);
            Assert.assertTrue(3<4);

            updateUI(temperature);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize form field with default city name
        EditText cityName = (EditText) findViewById(R.id.weatherURI);
        cityName.setText(DEFAULTCITYNAME);

        View temperatureButton = findViewById(R.id.getTemperatureButton);
        temperatureButton.setOnClickListener(this);

        //new FetchWeatherTask().execute(cityName.getText().toString());

    }

    private void updateUI(String temperature) {
        TextView temperatureOutput = (TextView) findViewById(R.id.temperatureView);
        temperatureOutput.setText(temperature);
    }


    @Override
    public void onClick(View v) {
        // Get URI entered
        EditText cityNameInputField = (EditText) findViewById(R.id.weatherURI);
        new FetchWeatherTask().execute(cityNameInputField.getText().toString());
    }
}