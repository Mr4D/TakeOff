package com.aditya.takeoff;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Submission_form extends AppCompatActivity {

    LocationManager locationManager;
    TextView locationTextView;
    TextView usernameTextView;
    EditText descriptionBox;
    EditText alertBox;
    MyDBHandler dbHandler;

    // Storing intents pass on info
    String userPasson;
    String NFC_ID;
    String imgUri;

    // Will be used later for
    Double longitude;
    Double latitude;
    String description;
    String alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_form);

        descriptionBox = (EditText)findViewById(R.id.descriptionBox);
        alertBox = (EditText)findViewById(R.id.alertBox);

        dbHandler = new MyDBHandler(this, null, null, 1);

        locationTextView = (TextView)findViewById(R.id.locationTextView);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Get the username from previous activity
        if (getIntent().hasExtra("com.aditya.takeoff.USER_ID")) {
            usernameTextView = (TextView)findViewById(R.id.usernameTextView);
            userPasson = getIntent().getExtras().getString("com.aditya.takeoff.USER_ID");
            usernameTextView.setText(userPasson);
        }
        // Get the NFC tag ID from previous activity
        if (getIntent().hasExtra("com.aditya.takeoff.NFC_ID")) {
            NFC_ID = getIntent().getExtras().getString("com.aditya.takeoff.NFC_ID");
        }
        if (getIntent().hasExtra("com.aditya.takeoff.IMG_URI")) {
            imgUri = getIntent().getExtras().getString("com.aditya.takeoff.IMG_URI");
        }
        createLocationRequest ();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createLocationRequest ();
                }
        }
    }

    protected void createLocationRequest () {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        FusedLocationProviderClient mFusedLocationProviderClient;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        else {
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
//                        String locationMsg = "Latitude: " + latitude + "\n " + "Longitude: " + longitude;     // Dogfood
//                        locationTextView.setText(locationMsg);                                                // Dogfood
                    }
                }
            });
        }
    }

    public void submitTasks(View view) {
       // Get all data and store in database
        description = descriptionBox.getText().toString();
        alert = alertBox.getText().toString();

        String strLong = String.valueOf(longitude);
        String strLat = String.valueOf(latitude);

        Log.d("posDebug", strLat);
        Log.d("posDebug", strLong);

        dbHandler.submitJob(userPasson, NFC_ID, strLong, strLat, description, alert, imgUri);
        Intent back = new Intent(getApplicationContext(), main.class);
        back.putExtra("com.aditya.takeoff.USER_ID", userPasson);
        back.putExtra("com.aditya.takeoff.BACK", "Returned");
        startActivity(back);
    }
}
