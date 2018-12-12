package com.example.ollantay.restaurantpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsMenu extends AppCompatActivity {

    public static int radius;
    public static String keyword = "food";
    private EditText distance;
    private EditText info;
    private Spinner minPrice;
    private Spinner maxPrice;

    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        distance = findViewById(R.id.editText);
        info = findViewById(R.id.info);
        minPrice = findViewById(R.id.spinner);
        maxPrice = findViewById(R.id.spinner2);

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i = (distance.getText().toString());
                if (!i.equals("")) {
                    radius = Integer.parseInt(i) * 1609;
                }
                String j = info.getText().toString();
                Log.e("myTag", j);
                if (!j.equals("")) {
                    keyword = j;
                } else {
                    keyword = "food";
                }
            }
        });
    }
}
