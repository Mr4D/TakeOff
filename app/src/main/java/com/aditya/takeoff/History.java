package com.aditya.takeoff;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    MyDBHandler dbHandler;
    TextView usernameTextView;
    String userPasson;

    ArrayList<Job> jobList;
    ListView listView;
    Job job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        dbHandler = new MyDBHandler(this, null, null, 1);

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

        jobList = new ArrayList<>();
        Cursor data = dbHandler.getListContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(this, "There is nothing in this database!", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()){
                job = new Job(data.getString(0), data.getString(1), data.getString(2), data.getString(3));
                jobList.add(job);
            }
            LsAdapter adapter = new LsAdapter(this, R.layout.list_adapter_view, jobList);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                }
//            });
        }
    }
}