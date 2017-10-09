package com.aditya.takeoff;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailView extends AppCompatActivity {

    TextView dbDump;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        dbDump = (TextView) findViewById(R.id.dbDump);
        dbHandler = new MyDBHandler(this, null, null, 1);

        if (getIntent().hasExtra("com.aditya.takeoff.JOB_ID")) {
            String id = getIntent().getExtras().getString("com.aditya.takeoff.JOB_ID");
            Job viewJob = dbHandler.getJobDetails(id);

        }
    }
}
