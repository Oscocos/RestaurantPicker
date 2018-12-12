package com.example.ollantay.restaurantpicker;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.tasks.OnSuccessListener;


//temporary? might not use these
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback, LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ImageButton spinButton;
    private ImageButton settingsButton;
    // Location request code
    private static final int GRANTED_LOCATION_REQUEST = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    private static String API_KEY = "AIzaSyBHRWFidpYwj0Lgc39nYYwed65kPV2usoA\n";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";

    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String TYPE_DETAILS = "/details";
    private static final String TYPE_SEARCH = "/nearbysearch";
    private static final String OUT_JSON = "/json?";
    private static final String LOG_TAG = "ListRest";

    public static double longitude;
    public static double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        spinButton = (ImageButton) findViewById(R.id.spin_button);
        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        longitude = location.getLongitude();
                                        latitude = location.getLatitude();
                                        openSpinResult();
                                    } else {
                                        requestLocationPermission();
                                    }
                                }
                            });
                } else {
                    requestLocationPermission();
                }
            }
        });
        settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsMenu();
            }
        });

    }


    //Overridden functions for LocationListener interface
    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Location has been disabled. Location needed for the app to work at all!",
                Toast.LENGTH_SHORT).show();
    }

    //Overridden functions for the GoogleAPI interface

    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            double latitude = mLocation.getLatitude();
            double longitude = mLocation.getLongitude();
        } else {
            requestLocationPermission();
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    public void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Neeeded")
                    .setMessage("Location needed for the app to work at all!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, GRANTED_LOCATION_REQUEST);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, GRANTED_LOCATION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == GRANTED_LOCATION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openSpinResult() {
        Intent intent  = new Intent(this, SpinResult.class);
        startActivity(intent);
    }

    public void openSettingsMenu() {
         Intent intent = new Intent(this, SettingsMenu.class);
         startActivity(intent);
    }

}
