package edu.iastate.cmgrimm.gpslogger;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

                if (logLocation) {
                    //TODO capture accelerometer data
                    String time = getCurrentTime() + ":" + (System.currentTimeMillis() & 1000) + "";

                    //store location as Coordinates
                    Coordinates newCoords = new Coordinates(location.getLatitude(), location.getLongitude(), time);

                    //update text views
                    TextView latTextView = (TextView) findViewById(R.id.latTextView);
                    TextView longTextView = (TextView) findViewById(R.id.longTextView);
                    TextView timeTextView = (TextView) findViewById(R.id.timeTextView);
                    latTextView.setText(newCoords.getLatitude() + "");
                    longTextView.setText(newCoords.getLongitude() + "");
                    timeTextView.setText(time);

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


        //ensure permissions have been accepted
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

        // Define where the location will be pulled from
        final String locationProvider = LocationManager.GPS_PROVIDER;


        Button ioBtn = (Button) findViewById(R.id.ioBtn);
        ioBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {


                if (logLocation) {
                    logLocation = false;

                    //stop updating location
                    locationManager.removeUpdates(locationListener);

                    //upload data
                    sendData(coordinates);

                    //change button color
                    Button ioBtn = (Button) findViewById(R.id.ioBtn);
                    ioBtn.setBackgroundColor(Color.RED);

                    //reset data
                    coordinates = new ArrayList<String[]>();

                } else {
                    logLocation = true;

                    //change button color
                    Button ioBtn = (Button) findViewById(R.id.ioBtn);
                    ioBtn.setBackgroundColor(Color.GREEN);


                    locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
                }

            }//end onClick
        });//end onclickListener


    }//end on create

    public static String getCurrentTime() {
        //date output format
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }// end getCurrentTime()

    public void sendData(List<String []> data){
        Date date = new Date();
        String time = date.getTime()+ "";

        try {
            String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+'/'+time;
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
