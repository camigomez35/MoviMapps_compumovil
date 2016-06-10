package co.edu.udea.moviemapps.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class MovieMappsUtils {

    private MovieMappsUtils() {
    }

    public static Bitmap getImage(String... params) {
        String baseImageUrl = "http://image.tmdb.org/t/p/w500";
        URL imageUrl;
        HttpURLConnection conn = null;
        Bitmap image = null;
        try {
            imageUrl = new URL(baseImageUrl + params[0]);
            conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            image = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (IOException e) {
            Log.e("ERROR", "Connection failed at downloadImage: ", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return image;
    }

    public static double calculateDistance(LatLng initialPosition, LatLng finalPosition) {
        double finalLatitude = finalPosition.latitude;
        double finalLongitude = finalPosition.longitude;
        double initialLatitude = initialPosition.latitude;
        double initialLongitude = initialPosition.longitude;
        double earthRadius = 6371;
        double latitudeDistance = Math.toRadians(finalLatitude - initialLatitude);
        double longitudeDistance = Math.toRadians(finalLongitude - initialLongitude);

        double distance = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2) +
                Math.cos(Math.toRadians(initialLatitude)) * Math.cos(Math.toRadians(finalLatitude)) *
                        Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);
        distance = 2 * Math.atan2(Math.sqrt(distance), Math.sqrt(1 - distance));
        distance = earthRadius * distance;
        return distance; //kilometers
    }

}
