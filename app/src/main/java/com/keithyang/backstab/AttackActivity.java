package com.keithyang.backstab;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.node.BooleanNode;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;


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
        }
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
                    TelephonyManager mTelephonyMgr;
                    mTelephonyMgr = (TelephonyManager)
                            getSystemService(Context.TELEPHONY_SERVICE);
                    String number = mTelephonyMgr.getLine1Number();
                    number = number.substring(2);
                    Double lat = location.getLatitude();
                    Double lon = location.getLongitude();
                    String url = "http://52.10.137.240:8888/updatecoordinatesbyId/" + number + "/@" + lat.toString() + ","  + lon.toString();
                    sendCor temp2 = new sendCor(url);
                    temp2.execute((Void) null);

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


    /**
     * Murder target
     */

    public void scanArea(View view){
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)
        getSystemService(Context.TELEPHONY_SERVICE);
        String number = mTelephonyMgr.getLine1Number();
        number = number.substring(2);
        gameArea temp = new gameArea(number);
        temp.execute((Void) null);

    }

    private class sendCor extends  AsyncTask<Void, Void, Boolean>{

        private String url;

        sendCor(String Murl){
            url = Murl;
        }

        protected Boolean doInBackground(Void... params){
            try {
                // Simulate network access.
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                URI website = new URI(url);
                Log.d("SignupActivity", website.toString());
                request.setURI(website);
                HttpResponse response = httpclient.execute(request);
            }
            catch (Exception e) {
                return false;
            }
           return true;
        }
    }
    private class gameArea extends AsyncTask<Void, Void, Boolean>{

        private String mId;
        private String otherId;

        gameArea(String id){
            mId = id;
        }

        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                URI website = new URI("http://52.10.137.240:8888/getplayerbyId/" + mId);
                Log.d("SignupActivity", website.toString());
                request.setURI(website);
                HttpResponse response = httpclient.execute(request);
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    InputStream inputStream = httpEntity.getContent();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    String ligneLue = bufferedReader.readLine();
                    while (ligneLue != null) {
                        stringBuilder.append(ligneLue + " \n");
                        ligneLue = bufferedReader.readLine();
                    }
                    bufferedReader.close();

                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    otherId = jsonObject.getString("target_id");
                    httpclient = new DefaultHttpClient();
                    request = new HttpGet();
                    website = new URI("http://52.10.137.240:8888/getplayerbyId/" + otherId);
                    request.setURI(website);
                    response = httpclient.execute(request);
                    httpEntity = response.getEntity();
                    if (httpEntity != null) {
                        inputStream = httpEntity.getContent();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        stringBuilder = new StringBuilder();
                        ligneLue = bufferedReader.readLine();
                        while (ligneLue != null) {
                            stringBuilder.append(ligneLue + " \n");
                            ligneLue = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                        JSONObject jsonObject2 = new JSONObject(stringBuilder.toString());
                        String player2C = jsonObject2.get("co-ordinates").toString();
                        int index = player2C.indexOf(",");
                        Double player2Lat = Double.parseDouble(player2C.substring(0, index - 1));
                        Double player2Long = Double.parseDouble(player2C.substring(index + 1));
                        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location mLastLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (mLastLocation == null) {
                            mLastLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        }
                        Double longitude = mLastLocation.getLongitude();
                        Double latitude = mLastLocation.getLatitude();
                        if (Math.abs(latitude - player2Lat) < 1 && Math.abs(latitude - player2Lat) < 1) {
                            httpclient = new DefaultHttpClient();
                            request = new HttpGet();
                            website = new URI("http://52.10.137.240:8888/deleteplayerbyId/" + mId);
                            request.setURI(website);
                            response = httpclient.execute(request);
                        }
                    }


                }
            } catch (Exception e) {
                return false;
            }

            return true;
        }

        protected void onPostExecute(final Boolean success) {

        }
    }
}
