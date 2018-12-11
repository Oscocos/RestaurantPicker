package com.example.ollantay.restaurantpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

public class SettingsMenu extends AppCompatActivity {

    public Switch delivery = (Switch) findViewById(R.id.deliverySwitch);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);
    }
}
