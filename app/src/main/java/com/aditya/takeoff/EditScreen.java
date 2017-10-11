package com.aditya.takeoff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditScreen extends AppCompatActivity {

    TextView updateDiscription, updateAlert;
    Button deleteJob, updateJob;
    MyDBHandler dbHandler;
    String id;

    String userPasson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);
        dbHandler = new MyDBHandler(this, null, null, 1);

        if(getIntent().hasExtra("com.aditya.takeoff.USER_ID")) {
            userPasson = getIntent().getExtras().getString("com.aditya.takeoff.USER_ID");
        }

        if(getIntent().hasExtra("com.aditya.takeoff.JOB_ID")) {
            id = getIntent().getExtras().getString("com.aditya.takeoff.JOB_ID");
        }
        updateDiscription = (TextView) findViewById(R.id.updateDiscription);
        updateAlert = (TextView) findViewById(R.id.updateAlert);
        deleteJob = (Button) findViewById(R.id.deleteJob);
        updateJob = (Button) findViewById(R.id.updateJob);
    }
    public void deleteJob(View view) {
        dbHandler.deleteJob(id);
        Intent gackToHistory = new Intent(EditScreen.this, History.class);
        startActivity(gackToHistory);
    }
    public void updateJob(View view) {
        String description = updateDiscription.getText().toString();
        if(description != null && !description.isEmpty()) {
            dbHandler.updateDescription(id, description);
            Intent gackToHistory = new Intent(EditScreen.this, History.class);
            gackToHistory.putExtra("com.aditya.takeoff.USER_ID", userPasson);
            startActivity(gackToHistory);
        }
        String alert = updateAlert.getText().toString();
        if(alert != null && !alert.isEmpty()) {
            dbHandler.updateAlert(id, alert);
            Intent gackToHistory = new Intent(EditScreen.this, History.class);
            gackToHistory.putExtra("com.aditya.takeoff.USER_ID", userPasson);
            startActivity(gackToHistory);
        }
        if (!(description != null && !description.isEmpty()) && !(alert != null && !alert.isEmpty())) {
            Toast.makeText(this, "Nothing to update", Toast.LENGTH_SHORT).show();
        }
    }
}
