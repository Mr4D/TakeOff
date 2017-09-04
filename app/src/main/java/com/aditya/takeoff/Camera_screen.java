package com.aditya.takeoff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Camera_screen extends AppCompatActivity {


    TextView usernameTextView;
    String userPasson;
    int NFC_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_screen);




        // Get the username from previous activity
        if (getIntent().hasExtra("com.aditya.takeoff.USER_ID")) {
            usernameTextView = (TextView)findViewById(R.id.usernameTextView);
            userPasson = getIntent().getExtras().getString("com.aditya.takeoff.USER_ID");
            usernameTextView.setText(userPasson);
        }

        // Get the NFC tag ID from previous activity
        if (getIntent().hasExtra("com.aditya.takeoff.NFC_ID")) {
            NFC_ID = getIntent().getExtras().getInt("com.aditya.takeoff.NFC_ID", 0);
            Toast.makeText(this, String.valueOf(NFC_ID), Toast.LENGTH_LONG).show();
        }
    }

    
}
