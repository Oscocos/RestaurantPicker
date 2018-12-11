package com.example.ollantay.restaurantpicker;

import android.content.Intent;
import android.media.Image;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.util.Log;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;



import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import android.os.StrictMode;

public class SpinResult extends AppCompatActivity {
    private ImageButton tryAgainButton;

    // variables for Google API
    private ListView myListView;
    private static final String API_KEY = "AIzaSyBHRWFidpYwj0Lgc39nYYwed65kPV2usoA";

    private static final String HTML_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String AUTOCOMPLETE = "/autocomplete";
    private static final String DETAILS = "/details";
    private static final String JSON_OUTPUT = "/json?";
    private static final String SEARCH = "/nearbysearch";
    private static final String LOG_TAG = "ListRest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_result);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        tryAgainButton = (ImageButton) findViewById(R.id.tryAgain);

        final TextView restaurantName = (TextView) findViewById(R.id.restaurantName);
        final TextView rating = (TextView) findViewById(R.id.rating);
        //Intent passedIntent = getIntent();
        //String longitude = passedIntent.getStringExtra("Longitude");
        //String latitude = passedIntent.getStringExtra("Latitude");

        Double longit = (Double) MainActivity.longitude;
        Double latit = MainActivity.latitude;


        // radius to search in meters
        int radius = 5000;

        ArrayList<Restaurant> possibleRestaurants = search(latit, longit, radius);


        Log.d("myTag", String.valueOf(longit));
        Log.d("myTag", String.valueOf(latit));
        Log.d("myTag", String.valueOf(possibleRestaurants.size()));



        Restaurant pickedPlace;
        if (possibleRestaurants == null) {
            restaurantName.setText(getResources().getString(R.string.empty_message));
            pickedPlace = new Restaurant();
        } else {
            pickedPlace = picker(possibleRestaurants);
        }
        restaurantName.setText(pickedPlace.toString());
        rating.setText(String.valueOf(pickedPlace.getRating()));

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openSpinResult();
                goBackToMain();
            }
        });
    }

    public static Restaurant picker(ArrayList<Restaurant> input) {
        if (input == null) {
            return null;
        }
        Random temp = new Random();
        int toPick = temp.nextInt(Math.abs(input.size() - 2) + 1);
        System.out.println(input);
        return input.get(toPick);
    }

    /*public void openSpinResult() {
        Intent intent  = new Intent(this, SpinResult.class);
        startActivity(intent);
    }*/

    public static ArrayList<Restaurant> search(double latit, double longit, int radius) {
        ArrayList<Restaurant> toReturn = null;

        HttpURLConnection connectToAPI = null;
        StringBuilder toReturnJSON = new StringBuilder();
        try {
            StringBuilder apiCall = new StringBuilder(HTML_BASE);
            apiCall.append(SEARCH);
            apiCall.append(JSON_OUTPUT);
            apiCall.append("location=" + String.valueOf(latit) + "," + String.valueOf(longit));
            apiCall.append("&radius=" + String.valueOf(radius));
            apiCall.append("&type=restaurant");
            apiCall.append("&key=" + API_KEY);

            URL urlToCall = new URL(apiCall.toString());
            connectToAPI = (HttpURLConnection) urlToCall.openConnection();
            InputStreamReader input = new InputStreamReader(connectToAPI.getInputStream());

            // I think this places the results from the call into StringBuilder toReturnJson.
            int read;
            char[] buff = new char[1024];
            while ((read = input.read(buff)) != -1) {
                toReturnJSON.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with Places API URL", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
        } finally {
            if (connectToAPI != null) {
                connectToAPI.disconnect();
            }
        }

        /// Placing information about a list of restaurants here, each put into
        /// a Restaurant class, each being their own Restaurant
        try {
            JSONObject jsonTemp = new JSONObject(toReturnJSON.toString());
            JSONArray restaurantArray = jsonTemp.getJSONArray("results");

            toReturn = new ArrayList<>(restaurantArray.length());
            for (int i = 0; i < restaurantArray.length(); i++) {
                Restaurant current = new Restaurant();
                current.reference = restaurantArray.getJSONObject(i).getString("reference");
                current.name = restaurantArray.getJSONObject(i).getString("name");
                current.rating = restaurantArray.getJSONObject(i).getDouble("rating");
                toReturn.add(current);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error with Json result", e);
        }
        return toReturn;
    }

    public static class Restaurant {
        private String reference;
        private String name;
        private double rating;

        public Restaurant() {
            super();
        }

        public double getRating() {
            return rating;
        }
        @Override
        public String toString() {
            if (name == null) {
                return "Empty.";
            } else {
                return this.name;
            }
        }
    }

    public void goBackToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
