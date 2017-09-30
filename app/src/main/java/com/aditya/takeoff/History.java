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
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHandler = new MyDBHandler(this, null, null, 1);
        textView2 = (TextView)findViewById(R.id.textView2);


        // Navigation below
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
                        Intent intent = new Intent(History.this, main.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

//        printDatabase();
    }



//    public void printDatabase() {   //Dogfood
//        String dbString = dbHandler.databaseToString();
//        textView2.setText(dbString);
//    }

}
