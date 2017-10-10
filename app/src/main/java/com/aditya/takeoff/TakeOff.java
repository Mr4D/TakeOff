package com.aditya.takeoff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TakeOff extends AppCompatActivity {

    Button loginBtn;
    EditText usernameText;
    EditText passwordText;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_off);
        loginBtn = (Button)findViewById(R.id.btLogin);
        usernameText = (EditText)findViewById(R.id.username);
        passwordText = (EditText)findViewById(R.id.password);
        dbHandler = new MyDBHandler(this, null, null, 1);
    }

    public void login(View view) {
        String username = usernameText.getText().toString();
        if (!username.isEmpty()) {
            Intent mainIntent = new Intent(this, main.class);
            mainIntent.putExtra("com.aditya.takeoff.USER_ID", username);
            startActivity(mainIntent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter a username.", Toast.LENGTH_LONG).show();
        }
    }
}
