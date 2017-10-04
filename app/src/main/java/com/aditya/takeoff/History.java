package com.aditya.takeoff;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class History extends AppCompatActivity {
    MyDBHandler dbHandler;
    TextView usernameTextView;
    TextView dbdump;
    String userPasson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        dbHandler = new MyDBHandler(this, null, null, 1);

        dbdump = (TextView)findViewById(R.id.dbdump);

        // Set username
        usernameTextView = (TextView)findViewById(R.id.usernameTextView);
        if (getIntent().hasExtra("com.aditya.takeoff.USER_ID")) {
            userPasson = getIntent().getExtras().getString("com.aditya.takeoff.USER_ID");
            usernameTextView.setText(userPasson);
        }
        // Navigation bar styling and activity managing
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_history:
                        break;
                    case R.id.ic_tasks:
                        String username = usernameTextView.getText().toString();
                        Intent mainIntent = new Intent(History.this, main.class);
                        mainIntent.putExtra("com.aditya.takeoff.USER_ID", username);
                        startActivity(mainIntent);
                        finish();
                        break;
                }
                return false;
            }
        });
        dbdump.setText(dbHandler.databaseToString());
    }
}