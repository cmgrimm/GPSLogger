package edu.iastate.cmgrimm.gpslogger;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private String time;
    private Location currentLocation;
    List<String[]> coordinates;
    private boolean logLocation = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.camera_view, Camera2VideoFragment.newInstance())
                    .commit();
        } */

        coordinates = new ArrayList<String[]>();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
                if (logLocation) {
=======
=======
>>>>>>> parent of 974185f... app now logs gps location
=======
>>>>>>> parent of 974185f... app now logs gps location
=======
>>>>>>> parent of 974185f... app now logs gps location
=======
>>>>>>> parent of 974185f... app now logs gps location
                TextView timeTextView = (TextView) findViewById(R.id.timeTextView);
                timeTextView.setText("uh oh");

                if(logLocation){
>>>>>>> parent of 974185f... app now logs gps location
                    //TODO capture accelerometer data
                    Date date = new Date();
                    String time = date.getTime() + (System.currentTimeMillis() & 1000) + "";

                    //store location as Coordinates
                    Coordinates newCoords = new Coordinates(location.getLatitude(), location.getLongitude(), time);

                    //update text views
                    TextView latTextView = (TextView) findViewById(R.id.latTextView);
                    TextView longTextView = (TextView) findViewById(R.id.longTextView);
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
                    TextView timeTextView = (TextView) findViewById(R.id.timeTextView);
                    latTextView.setText(newCoords.getLatitude() + "");
                    longTextView.setText(newCoords.getLongitude() + "");
                    timeTextView.setText(time);
=======
=======
>>>>>>> parent of 974185f... app now logs gps location
=======
>>>>>>> parent of 974185f... app now logs gps location
=======
>>>>>>> parent of 974185f... app now logs gps location
=======
>>>>>>> parent of 974185f... app now logs gps location
                        //TextView timeTextView = (TextView) findViewById(R.id.timeTextView);
                    latTextView.setText(newCoords.getLatitude()+"");
                    longTextView.setText(newCoords.getLongitude()+"");
                    timeTextView.setText("yay?");
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> parent of 974185f... app now logs gps location
=======
>>>>>>> parent of 974185f... app now logs gps location
=======
>>>>>>> parent of 974185f... app now logs gps location
=======
>>>>>>> parent of 974185f... app now logs gps location
=======
>>>>>>> parent of 974185f... app now logs gps location

                    //add new coordinates to array list
                    coordinates.add(new String[]{time, newCoords.getLatitude() + "", newCoords.getLongitude() + ""});


                } // end if
            }//end onLocationChange

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.INTERNET
                }, 10);
                return;
            }
        }

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, locationListener);

        Button ioBtn = (Button) findViewById(R.id.ioBtn);
        ioBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {

                TextView timeTextView = (TextView) findViewById(R.id.timeTextView);

                if (logLocation) {
                    logLocation = false;
                    timeTextView.setText("unclicked");

                    //upload data
                    sendData(coordinates);

                    //reset data
                    coordinates = new ArrayList<String[]>();

                } else {
                    logLocation = true;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD

                    //change button color
                    Button ioBtn = (Button) findViewById(R.id.ioBtn);
                    ioBtn.setBackgroundColor(Color.GREEN);


                    locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
=======
                    timeTextView.setText("clicked");
>>>>>>> parent of 974185f... app now logs gps location
=======
                    timeTextView.setText("clicked");
>>>>>>> parent of 974185f... app now logs gps location
=======
                    timeTextView.setText("clicked");
>>>>>>> parent of 974185f... app now logs gps location
=======
                    timeTextView.setText("clicked");
>>>>>>> parent of 974185f... app now logs gps location
=======
                    timeTextView.setText("clicked");
>>>>>>> parent of 974185f... app now logs gps location
                }

            }//end onClick
        });//end onclickListener


    }//end on create

    public void sendData(List<String []> data){
        Date date = new Date();
        String time = date.getTime()+ "";

        String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+'\\'+time;

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            String [] headers = "Time#Latitude#Longitude".split("#");
            writer.writeNext(headers);
            writer.writeAll(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }//end sendData


}//end main activity
