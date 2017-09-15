package com.aditya.takeoff;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Camera_screen extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1313;

    //Permission request codes for functions
    private static final int REQUEST_CAMERA_PERMISSION = 6363;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 3939;

    TextView usernameTextView;
    ImageView imageDisp;
    String userPasson;
    String NFC_ID;
    String imgPath;

    Boolean permissionStatus;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startCameraIntent() {
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (callCameraApplicationIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            String authorities = getApplicationContext().getPackageName() + ".fileprovider";
            Uri imageUri = FileProvider.getUriForFile(Camera_screen.this, authorities, photoFile);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(callCameraApplicationIntent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_screen);

        imageDisp = (ImageView) findViewById(R.id.imageDisp);

        if (getIntent().hasExtra("com.aditya.takeoff.USER_ID")) { // Get the username from previous activity
            usernameTextView = (TextView)findViewById(R.id.usernameTextView);
            userPasson = getIntent().getExtras().getString("com.aditya.takeoff.USER_ID");
            usernameTextView.setText(userPasson);
        }

        if (getIntent().hasExtra("com.aditya.takeoff.NFC_ID")) { // Get the NFC tag ID from previous activity
            NFC_ID = getIntent().getExtras().getString("com.aditya.takeoff.NFC_ID");
            Toast.makeText(this, String.valueOf(NFC_ID), Toast.LENGTH_LONG).show();
        }

        imageDisp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= 23) { // check if permissions have been granted, if not request them, otherwise start the camera intent. All for marshmallow and above
                    if((ActivityCompat.checkSelfPermission(Camera_screen.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) { // If no camera permission granted, request it
                        ActivityCompat.requestPermissions(Camera_screen.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                    if ((ActivityCompat.checkSelfPermission(Camera_screen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) { // If no write to storage permission granted, request it
                        ActivityCompat.requestPermissions(Camera_screen.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
                        permissionStatus = true;
                    }
                    else { // already have permissions, with the camera intent
                        startCameraIntent();
                    }
                }
                else { // Running below android marshmallow, we already have required permissions. Just call the camera
                    startCameraIntent();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(imgPath);
            Uri contentUri = Uri.fromFile(f);
            Toast.makeText(this, contentUri.toString(), Toast.LENGTH_LONG).show();
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentUri);
                imageDisp.setImageBitmap(imageBitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg",storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        imgPath = image.getAbsolutePath();
        return image;
    }

    public void goSubmissionForm(View view) {
        Intent startIntent = new Intent(getApplicationContext(), Submission_form.class);
        startIntent.putExtra("com.aditya.takeoff.USER_ID", userPasson);
        startIntent.putExtra("com.aditya.takeoff.NFC_ID", NFC_ID);
        startActivity(startIntent);
    }
}
