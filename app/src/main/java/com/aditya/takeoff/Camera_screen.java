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

public class Camera_screen extends AppCompatActivity {


    public static final int CAMERA_REQUEST = 10;
    TextView usernameTextView;
    ImageView imageDisp;
    String userPasson;
    int NFC_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_screen);
        imageDisp = (ImageView) findViewById(R.id.imageDisp);



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




    public void goSubmissionForm(View view) {
        String username = usernameTextView.getText().toString();
        Intent startIntent = new Intent(getApplicationContext(), Submission_form.class);
        startIntent.putExtra("com.aditya.takeoff.USER_ID", username);
        startIntent.putExtra("com.aditya.takeoff.NFC_ID", NFC_ID);
        startActivity(startIntent);
    }


    public void takeImage(View view) {
        // This method is called when the image view it clicked
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {  // Did the user choose ok? If yes excute the following

            if (requestCode == CAMERA_REQUEST) {    // We are hearing back from the camera
                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                // At this point, we have the image from the camera

                imageDisp.setImageBitmap(cameraImage);



            }

        }


    }
}
