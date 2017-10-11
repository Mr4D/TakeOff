package com.aditya.takeoff;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DetailView extends AppCompatActivity implements OnMapReadyCallback {

    TextView taskName, usernameTextView, dateTextView, timeTextView, descriptionTextView,alertTextView, localityTextView;
    MyDBHandler dbHandler;
    ImageView imgView;
    GoogleMap mGoogleMap;
    Job viewJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(googleServicesAvailable()){
//            Toast.makeText(this, "Connection with Play services established", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_detail_view);
            initMap();
        }
        else {
            //No Google Maps Layout
        }
        taskName = (TextView) findViewById(R.id.taskName);
        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        alertTextView = (TextView) findViewById(R.id.alertTextView);
        localityTextView = (TextView) findViewById(R.id.localityTextView);

        imgView = (ImageView) findViewById(R.id.imgView);
        dbHandler = new MyDBHandler(this, null, null, 1);

        if(getIntent().hasExtra("com.aditya.takeoff.JOB_ID")) {
            String id = getIntent().getExtras().getString("com.aditya.takeoff.JOB_ID");
            viewJob = dbHandler.getJobDetails(id);
            Uri pathName = Uri.parse(viewJob.getImg_uri());

            taskName.setText(viewJob.getTask());
            usernameTextView.setText(viewJob.getUsername());
            dateTextView.setText(viewJob.getDate());
            timeTextView.setText(viewJob.getTime());
            descriptionTextView.setText(viewJob.getDescription());
            alertTextView.setText(viewJob.getAlert());

            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pathName);

                if (imageBitmap.getWidth() > imageBitmap.getHeight()) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    imageBitmap = Bitmap.createBitmap(imageBitmap , 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
                }
                imgView.setImageBitmap(imageBitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServicesAvailable () {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS) {
            return true;
        }
        else if(api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        }
        else {
            Toast.makeText(this, "Can not connect to Play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public void onMapReady (GoogleMap googleMap) {
        mGoogleMap = googleMap;
        try {
            goToLocationZoom(Double.parseDouble(viewJob.getLongitude()), Double.parseDouble(viewJob.getLatitude()), 15);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToLocationZoom(double longitude, double lattitude, float zoom) throws IOException {
        LatLng ll = new LatLng(lattitude, longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(lattitude, longitude, 1);
        String address = addresses.get(0).getAddressLine(0);
        String locality = addresses.get(0).getLocality();

        localityTextView.setText(locality);
        MarkerOptions options = new MarkerOptions().title(address).position(ll);
        mGoogleMap.addMarker(options);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.edit) {
            // Go to edit screen, pass on the job ID
            Intent editScreen = new Intent(DetailView.this, EditScreen.class);
            editScreen.putExtra("com.aditya.takeoff.JOB_ID", viewJob.getId());
            startActivity(editScreen);

        }
        return true;
    }
}
