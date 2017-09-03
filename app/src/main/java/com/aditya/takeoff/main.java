package com.aditya.takeoff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class main extends AppCompatActivity {
    TextView mainTestText;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().hasExtra("com.aditya.takeoff.USER_ID")) {
            mainTestText = (TextView)findViewById(R.id.mainTestText);
            text = getIntent().getExtras().getString("com.aditya.takeoff.USER_ID");
            mainTestText.setText(text);
        }
    }
}
