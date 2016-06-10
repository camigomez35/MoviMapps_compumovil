package co.edu.udea.moviemapps.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.model.Cinema;
import co.edu.udea.moviemapps.persistence.CinemaDataManager;
import co.edu.udea.moviemapps.util.MovieMappsUtils;


public class CinemasMap extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnMyLocationButtonClickListener {

    public static final int ID = 8;

    private static final int SLOW_GPS_UPDATE = 30000;
    private static final int MIN_DISTANCE_GPS_UPDATES = 0;
    private static float zoom = (float) 15.5;
    private static DecimalFormat twoDecimalFormatter = new DecimalFormat("#.##");

    private GoogleMap Map;
    private LocationManager locationManager;
    private Double myLat = 6.2676721377548335, myLng = -75.56773066520691;
    private LatLng myLatLng;
    private boolean mapShouldBeMarked = true;

    private List<Cinema> cinemas = new ArrayList<>();
    private static View rootView;

    public static CinemasMap newInstance() {
        return new CinemasMap();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.cinemamap_fragment, container, false);
        } catch (InflateException e) {
            Log.e("ERROR", "Inflate failed at onCreateView: ", e);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Map = googleMap;
        Map.setMyLocationEnabled(true);
        Map.setOnMyLocationButtonClickListener(this);
        getPosition();
    }

    @Override
    public void onLocationChanged(Location location) {
        myLat = location.getLatitude();
        myLng = location.getLongitude();
        myLatLng = new LatLng(myLat, myLng);
        if (mapShouldBeMarked == true) {
            Map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, zoom));
            mapShouldBeMarked = false;
            markCinemas();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("Provider Change: ", provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("Provider Enabled: ", provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("Provider Disabled: ", provider);
    }


    private void getCinemas() {
        cinemas = CinemaDataManager.getInstance().getAllCinemas();
        markCinemas();
    }

    private void markCinemas() {
        LatLng cinemaPosition;
        LatLng myPosition;
        Cinema cine;
        String measureUnit;
        double distance;

        for (int i = 0; i < cinemas.size(); i++) {
            cine = cinemas.get(i);
            cinemaPosition = new LatLng(cine.getLatitude(), cine.getLongitude());
            myPosition = new LatLng(myLat,myLng);
            distance = MovieMappsUtils.calculateDistance(myPosition,cinemaPosition);
            if (distance < 1) {
                distance = distance * 1000;
                measureUnit = " m";
            } else {
                measureUnit = " km";
            }
            Map.addMarker(new MarkerOptions()
                    .position(cinemaPosition)
                    .title(cine.getName())
                    .snippet(twoDecimalFormatter.format(distance) + measureUnit)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }
    }

    private void getPosition() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            mapShouldBeMarked = true;
            myLat = location.getLatitude();
            myLng = location.getLongitude();
            myLatLng = new LatLng(myLat, myLng);
            Map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, zoom));
        } else {
            mapShouldBeMarked = false;
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                createLocationDialog();
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, SLOW_GPS_UPDATE, MIN_DISTANCE_GPS_UPDATES, this);
        }
        getCinemas();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mapShouldBeMarked = true;
            getPosition();
        }
        return false;
    }

    public void createLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Use geolocation feature?")
                .setMessage("In order to determine your location and show you custom information, it is required to activate the location feature.")
                .setPositiveButton("ACEPT",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mapShouldBeMarked = true;
                                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
        builder.create();
        builder.show();
    }
}
