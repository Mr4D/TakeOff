package com.aditya.takeoff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class main extends AppCompatActivity {
    TextView usernameTextView;
    String userPasson;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().hasExtra("com.aditya.takeoff.USER_ID")) {
            usernameTextView = (TextView)findViewById(R.id.username);
            userPasson = getIntent().getExtras().getString("com.aditya.takeoff.USER_ID");
            usernameTextView.setText(userPasson);

        }
    }

    public void scanPart(View view) {
        String username = usernameTextView.getText().toString();

        // TODO -> Write code to identify which NFC tag was scanned
        int NFC_ID = 5;
        Intent startIntent = new Intent(this, Checklist_screen.class);
        startIntent.putExtra("com.aditya.takeoff.USER_ID", username);
        startIntent.putExtra("com.aditya.takeoff.NFC_ID", NFC_ID);
        startActivity(startIntent);


    }
}
