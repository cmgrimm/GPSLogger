package edu.iastate.cmgrimm.gpslogger;

/**
 * Created by Chase on 10/25/2017.
 */

public class Coordinates {

    private double latitude;
    private double longitude;
    private String time;

    public Coordinates(double latitude, double longitude, String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public String getTime() {
        return time;
    }

}
