package com.example.ollantay.restaurantpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsMenu extends AppCompatActivity {

    public static int radius;
    public static String keyword;
    private EditText distance;
    private EditText info;
    private Spinner minPrice;
    private Spinner maxPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        distance = findViewById(R.id.editText);
        info = findViewById(R.id.info);
        minPrice = findViewById(R.id.spinner);
        maxPrice = findViewById(R.id.spinner2);
        radius = Integer.parseInt(distance.getText().toString());
        keyword = info.getText().toString();
    }
}
