package edu.iastate.cmgrimm.gpslogger;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private String time;
    private Location currentLocation;
    private ArrayList<Coordinates> coordinates;
    private boolean logLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinates = new ArrayList<Coordinates>();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;

                TextView timeTextView = (TextView) findViewById(R.id.timeTextView);
                timeTextView.setText("uh oh");

                if(logLocation){

                    Date date = new Date();
                    String time = date.getTime() + (System.currentTimeMillis() & 1000) + "";

                    //store location as Coordinates
                    Coordinates newCoords = new Coordinates(location.getLatitude(), location.getLongitude(), time);

                    //update text views
                    TextView latTextView = (TextView) findViewById(R.id.latTextView);
                    TextView longTextView = (TextView) findViewById(R.id.longTextView);
                        //TextView timeTextView = (TextView) findViewById(R.id.timeTextView);
                    latTextView.setText(newCoords.getLatitude()+"");
                    longTextView.setText(newCoords.getLongitude()+"");
                    timeTextView.setText("yay?");

                    //add new coordinates to array list
                    coordinates.add(newCoords);



            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };//end location listener
        Button ioBtn = (Button) findViewById(R.id.ioBtn);
        ioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView timeTextView = (TextView) findViewById(R.id.timeTextView);

                if(logLocation) {
                    logLocation = false;
                    timeTextView.setText("unclicked");

                    //upload data
                    sendData();

                    //reset data
                    coordinates = new ArrayList<Coordinates>();

                } else {
                    logLocation = true;
                    timeTextView.setText("clicked");
                }

            }//end onClick
        });//end onclickListener


    }//end on create

    private void sendData(){
        //TODO upload data to cloud
    }


}//end main activity
