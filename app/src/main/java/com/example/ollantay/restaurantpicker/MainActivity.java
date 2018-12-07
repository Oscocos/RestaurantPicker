package com.example.ollantay.restaurantpicker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private ImageButton spinButton;
    private ImageButton settingsButton;

    private static final int GRANTED_LOCATION_REQUEST = 0;
    private View snackBarLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinButton = (ImageButton) findViewById(R.id.spin_button);
        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickARestaurant();
                openSpinResult();
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

    public void pickARestaurant() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(snackBarLayout, "Location available, picking restaurant...", Snackbar.LENGTH_SHORT).show();
            pick();
        } else {
            //requestLocationPermission();
        }
    }

    public void pick() {

    }

    public void requestLocationPermission() {

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
