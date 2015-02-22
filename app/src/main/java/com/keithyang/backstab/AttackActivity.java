package com.keithyang.backstab;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class AttackActivity extends ActionBarActivity {

    public Double latitude;
    public Double longitude;
    public Handler handler1;
    public Double LastLat;
    public Boolean temp;
    public TextView fooobarr;
    LocationManager lm;
	/* public Runnable runnable = new Runnable() {
          @Override
        public void run() {
            Location mLastLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LastLat = latitude;
            longitude = mLastLocation.getLongitude();
            latitude = mLastLocation.getLatitude();
            temp = !(LastLat == latitude);
            fooobarr.setText(temp.toString());
            handler1.postDelayed(this, 1000);
//          }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location mLastLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(mLastLocation == null){
            mLastLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        //longitude = mLastLocation.getLongitude();
        //latitude = mLastLocation.getLatitude();
        fooobarr =(TextView)findViewById(R.id.TextView01);
        //fooobarr.setText("hell");

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                Log.d("MainActivity", location.toString());
                if (location != null) {
                    fooobarr.setText(location.toString());
                } else {
                    fooobarr.setText("Location NOT FOUND");
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}
			            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };


// Register the listener with the Location Manager to receive location updates
        mLastLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(mLastLocation != null){
            Log.d("MainActivity","gps");
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        }
        mLastLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(mLastLocation != null) {
            Log.d("MainActivity","network");
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        }
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
        if (id == R.id.action_settings) {
    return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
