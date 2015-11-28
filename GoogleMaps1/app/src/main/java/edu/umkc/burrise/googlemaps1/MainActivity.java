package edu.umkc.burrise.googlemaps1;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.*;
// To integrate Google Maps with your app, the device needs to be running
//   Google Play Services (a separate download).
// To use Google Maps with your app, you have to first get a product key from
//   Google. The product key is specified in the Manifest file.
// Manifest file also specifies permissions a google maps app will need.
// Check out the layout file. Notice the name of the fragment
//   is MapFragment from Google. MapFragment extends Fragment. This is
//   another way to specify a fragment. The fragment example we studied
//   earlier defined the fragment in code and set it dynamically at\
//   runtime.
public class MainActivity extends ActionBarActivity  implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    // UMKC: 39.0347558,-94.577007
    static final LatLng UMKC = new LatLng(39.0347558,-94.577007);
    static final LatLng SCE = new LatLng(39.028, -94.50);

    private GoogleMap map;
    private Marker umkc_marker;
    private Marker sce_marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Calling an async method on MapFragment class.
        ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView))
                .getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        umkc_marker = googleMap.addMarker(new MarkerOptions()
                .position(UMKC)
                .title("UMKC")
                .draggable(true));

        map.setOnMarkerClickListener(this);

        // MarkerOptions uses method chaining
        // Method Chaining = syntax for invoking multiple method calls.
        //   Each method returns an object, allowing the calls to be
        //   chained together in a single statement without requiring
        //   variables to store the intermediate results
        // Also, a good example of the builder design pattern
        sce_marker = googleMap.addMarker(new MarkerOptions()
                .position(SCE)
                .title("SCE")
                .draggable(true) // Need to setOnMarkerDragListener() for icon to be draggable
                .snippet("School of Comp/Eng")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_sce)));

        // Move the camera instantly to SCE with a zoom of 15.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SCE, 15));

        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        // Continiously draw an indication of users current location
        googleMap.setMyLocationEnabled(true);
        // You can change current location by going to
        //   tools / android / android device monitor
        Toast.makeText(getApplicationContext(),
                "animate camera", Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_zoomIn) {
            map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);

            // Not, necessary for Google Maps, but on a related note,
            //   here is how to query GPS location.
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            // Returns null if location isn't available
            // In emulator, you need to send your location (via android / tools)
            //   before getLostKnownLocation() will return a location.
            if (location != null) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                Toast.makeText(getApplicationContext(),
                        "Long " + longitude + " Lat " + latitude, Toast.LENGTH_LONG).show();
            }

            // Another option is to call requestLocationUpdates() on
            //   LocationManager to receive location updates at certain
            //   intervals or distance moved.
            return true;
        }
        else if (id == R.id.action_zoomOut) {
            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // umkc_marker?
        if(marker.equals(umkc_marker)){
            Toast.makeText(getApplicationContext(),
                    "More info on UMKC", Toast.LENGTH_LONG).show();
            // If you return true you don't get center on click behavior
            // (I assume returning false forwards the event for further processing
            return false;
        }
        return false;
    }
}
