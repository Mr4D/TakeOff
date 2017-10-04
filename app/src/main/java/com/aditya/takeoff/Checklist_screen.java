package com.aditya.takeoff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Checklist_screen extends AppCompatActivity {
    TextView usernameTextView;
    TextView partName;
    String userPasson;
    String NFC_ID;
    MyDBHandler dbHandler;

    CheckBox check0;
    CheckBox check1;
    CheckBox check2;
    CheckBox check3;
    CheckBox check4;
    CheckBox check5;
    CheckBox check6;
    CheckBox check7;
    CheckBox check8;
    CheckBox check9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_screen);
        dbHandler = new MyDBHandler(this, null, null, 1);

        partName = (TextView)findViewById(R.id.partName);

        check0 = (CheckBox)findViewById(R.id.checkBox0);
        check1 = (CheckBox)findViewById(R.id.checkBox1);
        check2 = (CheckBox)findViewById(R.id.checkBox2);
        check3 = (CheckBox)findViewById(R.id.checkBox3);
        check4 = (CheckBox)findViewById(R.id.checkBox4);
        check5 = (CheckBox)findViewById(R.id.checkBox5);
        check6 = (CheckBox)findViewById(R.id.checkBox6);
        check7 = (CheckBox)findViewById(R.id.checkBox7);
        check8 = (CheckBox)findViewById(R.id.checkBox8);
        check9 = (CheckBox)findViewById(R.id.checkBox9);

        // Get the username from previous activity
        if (getIntent().hasExtra("com.aditya.takeoff.USER_ID")) {
            usernameTextView = (TextView)findViewById(R.id.usernameTextView);
            userPasson = getIntent().getExtras().getString("com.aditya.takeoff.USER_ID");
            usernameTextView.setText(userPasson);
        }
        // Get the NFC tag ID from previous activity
        if (getIntent().hasExtra("com.aditya.takeoff.NFC_ID")) {
            NFC_ID = getIntent().getExtras().getString("com.aditya.takeoff.NFC_ID");
            Toast.makeText(this, NFC_ID, Toast.LENGTH_LONG).show();
        }
        setCheckList();
    }

    public void setCheckList() {
        String[] checks = dbHandler.getCheckList(NFC_ID);
        check0.setText(checks[0]);
        check1.setText(checks[1]);
        check2.setText(checks[2]);
        check3.setText(checks[3]);
        check4.setText(checks[4]);
        check5.setText(checks[5]);
        check6.setText(checks[6]);
        check7.setText(checks[7]);
        check8.setText(checks[8]);
        check9.setText(checks[9]);
        partName.setText(checks[10]);
    }

    public void goCameraScreen(View view) {
        if(check0.isChecked() && check1.isChecked() && check2.isChecked() && check3.isChecked() && check4.isChecked() && check5.isChecked() && check6.isChecked() && check7.isChecked() && check8.isChecked() && check9.isChecked()) {
            String username = usernameTextView.getText().toString();
            Intent startIntent = new Intent(getApplicationContext(), Camera_screen.class);
            startIntent.putExtra("com.aditya.takeoff.USER_ID", username);
            startIntent.putExtra("com.aditya.takeoff.NFC_ID", NFC_ID);
            startActivity(startIntent);
        }
        else {
            Toast.makeText(this, "Please complete all your tasks to continue.", Toast.LENGTH_SHORT).show();
        }
    }
}