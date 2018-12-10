package com.example.ollantay.restaurantpicker;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SpinResult extends AppCompatActivity {
    private ImageButton tryAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_result);

        tryAgainButton = (ImageButton) findViewById(R.id.tryAgain);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSpinResult();
            }
        });
    }

    public void openSpinResult() {
        Intent intent  = new Intent(this, SpinResult.class);
        startActivity(intent);
    }

    /* Not used anymore, for now
    public void goBackToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/
}
