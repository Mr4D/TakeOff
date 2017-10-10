package com.aditya.takeoff;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.IOException;

public class DetailView extends AppCompatActivity implements OnMapReadyCallback {

    TextView dbDump;
    MyDBHandler dbHandler;
    ImageView imgView;
    GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(googleServicesAvailable()){
            Toast.makeText(this, "Connection with Play services established", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_detail_view);
            initMap();
        } else {
            //No Google Maps Layout
        }

        dbDump = (TextView) findViewById(R.id.dbDump);
        imgView = (ImageView) findViewById(R.id.imgView);
        dbHandler = new MyDBHandler(this, null, null, 1);

        if (getIntent().hasExtra("com.aditya.takeoff.JOB_ID")) {
            String id = getIntent().getExtras().getString("com.aditya.takeoff.JOB_ID");
            Job viewJob = dbHandler.getJobDetails(id);

            Uri pathName = Uri.parse(viewJob.getImg_uri());
            dbDump.setText(pathName.toString());
            dbDump.append("\n" + viewJob.getId());
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pathName);
                imgView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void initMap(){
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServicesAvailable () {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can not connect to Play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public void onMapReady (GoogleMap googleMap){
        mGoogleMap = googleMap;
    }
}

