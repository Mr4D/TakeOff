package com.aditya.takeoff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Checklist_screen extends AppCompatActivity {

    TextView usernameTextView;
    String userPasson;
    String NFC_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_screen);

        // Get the username from previous activity
        if (getIntent().hasExtra("com.aditya.takeoff.USER_ID")) {
            usernameTextView = (TextView)findViewById(R.id.usernameTextView);
            userPasson = getIntent().getExtras().getString("com.aditya.takeoff.USER_ID");
            usernameTextView.setText(userPasson);
        }

        // Get the NFC tag ID from previous activity
        if (getIntent().hasExtra("com.aditya.takeoff.NFC_ID")) {
            NFC_ID = getIntent().getExtras().getString("com.aditya.takeoff.NFC_ID");
            Toast.makeText(this, String.valueOf(NFC_ID), Toast.LENGTH_LONG).show();
        }
    }



    public void goCameraScreen(View view) {
        String username = usernameTextView.getText().toString();
        Intent startIntent = new Intent(getApplicationContext(), Camera_screen.class);
        startIntent.putExtra("com.aditya.takeoff.USER_ID", username);
        startIntent.putExtra("com.aditya.takeoff.NFC_ID", NFC_ID);
        startActivity(startIntent);
    }
}