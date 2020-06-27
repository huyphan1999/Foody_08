package hcmute.edu.vn.foody_08;

import android.location.Location;

import java.text.DecimalFormat;

public class Distance {
    private static Location myLocation;
    private static DecimalFormat df = new DecimalFormat("#.#");

    public Distance() {
    }


    public static String getDistance(double latitude, double longitude){
        if (myLocation == null) return null;
        else {
            Location resLocation = new Location("");
            resLocation.setLatitude(latitude);
            resLocation.setLongitude(longitude);
            float distance = myLocation.distanceTo(resLocation);
            return df.format(distance / 1000) + "km";
        }
    }

    public static void setMyLocation(Location myLocation) {
        Distance.myLocation = myLocation;
    }
}
