package com.example.ollantay.restaurantpicker;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton spinButton;
    private ImageButton settingsButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinButton = (ImageButton) findViewById(R.id.spin_button);


        settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsMenu();
            }
        });

    }

    public void openSettingsMenu() {
         Intent intent = new Intent(this, SettingsMenu.class);
         startActivity(intent);
    }

    /*ImageButton spinButton = (ImageButton) findViewById(R.id.spin_button);

    spinButton.OnClickListener(new View.OnClickListener()) {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, SettingsMenu.class));
        }
    }*/
}
