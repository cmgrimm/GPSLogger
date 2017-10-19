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

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location currentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ioBtn = (Button) findViewById(R.id.ioBtn);
        ioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //get Your Current Location
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        currentLocation = location;
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = new Date();
                        dateFormat.format(date);

                        //update location displayed in app
                        TextView latTextView = (TextView) findViewById(R.id.latTextView);
                        TextView longTextVIew = (TextView) findViewById(R.id.longTextView);
                        TextView timeTextView = (TextView) findViewById(R.id.timeTextView);

                        latTextView.setText(currentLocation.getLatitude()+"");
                        longTextVIew.setText(currentLocation.getLongitude() + "");
                        timeTextView.setText(date + "" + (System.currentTimeMillis() % 1000));
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                };
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);



            }
        });

    }
}
