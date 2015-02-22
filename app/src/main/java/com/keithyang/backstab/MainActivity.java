package com.keithyang.backstab;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    public Double latitude;
    public Double longitude;
    public Handler handler1;
    public Double LastLat;
    public Boolean temp;
    LocationManager lm;
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Location mLastLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LastLat = latitude;
            longitude = mLastLocation.getLongitude();
            latitude = mLastLocation.getLatitude();
            temp = !(LastLat == latitude);
            TextView t= new TextView(getApplicationContext());
            t.setText(temp.toString());
            handler1.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location mLastLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = mLastLocation.getLongitude();
        latitude = mLastLocation.getLatitude();
        TextView t=new TextView(this);
            t=(TextView)findViewById(R.id.TextView01);
        t.setText(latitude.toString() + " " + longitude.toString());
        handler1 = new Handler();
        handler1.postDelayed(runnable, 1000);
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
