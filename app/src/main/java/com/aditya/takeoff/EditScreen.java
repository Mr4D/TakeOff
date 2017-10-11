package com.aditya.takeoff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class EditScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);

        if(getIntent().hasExtra("com.aditya.takeoff.JOB_ID")) {
            String id = getIntent().getExtras().getString("com.aditya.takeoff.JOB_ID");
            Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        }
    }
}
