package co.edu.udea.moviemapps.test.util;

import android.graphics.Bitmap;
import android.test.AndroidTestCase;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import co.edu.udea.moviemapps.util.MovieMappsUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MovieMappsUtilsUnitTest {

    @Test
    public void calculateDistanceTest(){
        LatLng initialPosition = new LatLng(6.232321400000001, -75.60410279999996);
        LatLng finalPosition = new LatLng(6.268674619223301, -75.56475877761841);
        double distance = MovieMappsUtils.calculateDistance(initialPosition,finalPosition);
        assertEquals(5.94,distance,0.1);
    }

}
