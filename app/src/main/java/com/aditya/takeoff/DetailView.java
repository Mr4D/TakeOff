package com.aditya.takeoff;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class DetailView extends AppCompatActivity {

    TextView dbDump;
    MyDBHandler dbHandler;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        dbDump = (TextView) findViewById(R.id.dbDump);
        imgView = (ImageView) findViewById(R.id.imgView);
        dbHandler = new MyDBHandler(this, null, null, 1);

        if (getIntent().hasExtra("com.aditya.takeoff.JOB_ID")) {
            String id = getIntent().getExtras().getString("com.aditya.takeoff.JOB_ID");
            Job viewJob = dbHandler.getJobDetails(id);

            String pathName = viewJob.getImg_uri();

            if(pathName!=null && pathName!="") {
                File f = new File(pathName);
                if(f.exists()) {
                    Toast.makeText(this, "File exists!", Toast.LENGTH_SHORT).show();
                    Drawable d = Drawable.createFromPath(pathName);
                    imgView.setImageDrawable(d);
                }
            }
        }
    }
}
