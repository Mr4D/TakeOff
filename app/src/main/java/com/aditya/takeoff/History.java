package com.aditya.takeoff;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    // Code from Mitch Tabian's project (mitchtabian on GitHub), source code available at https://github.com/mitchtabian/ListAdapter and https://github.com/mitchtabian/ListViews
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
        setListView(false);
    }

    public void setListView(Boolean order) {
        jobList = new ArrayList<>();
        Cursor data = dbHandler.getListContents(order);
        if(!((data != null) && (data.getCount() > 0))){
            Toast.makeText(this, "There is nothing in this database!", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()){
                job = new Job(data.getString(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5));
                jobList.add(job);
            }
            LsAdapter adapter = new LsAdapter(this, R.layout.list_adapter_view, jobList);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Job selectedJob = (Job) parent.getItemAtPosition(position);
                    String jobId = selectedJob.getId();
                    Intent detail = new Intent(History.this, DetailView.class);
                    detail.putExtra("com.aditya.takeoff.JOB_ID", jobId);
                    detail.putExtra("com.aditya.takeoff.USER_ID", userPasson);
                    startActivity(detail);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // code based on video tutorial from Coding Demos and video turorial from PRABEESH R K, source code available at https://www.youtube.com/watch?v=EZ-sNN7UWFU&t=418s and https://www.youtube.com/watch?v=IyPemIJDerw&t=680s
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // code based on video tutorial from Coding Demos and video turorial from PRABEESH R K, source code available at https://www.youtube.com/watch?v=EZ-sNN7UWFU&t=418s and https://www.youtube.com/watch?v=IyPemIJDerw&t=680s
        if(item.getItemId() == R.id.new_old) {
            Toast.makeText(this, "Newset to oldest", Toast.LENGTH_LONG).show();
            setListView(false);
            return false;
        }
        else if (item.getItemId() == R.id.old_new) {
            Toast.makeText(this, "Oldest to newest", Toast.LENGTH_LONG).show();
            setListView(true);
            return true;
        }
        else {
            Toast.makeText(this, "Nothing selected, try again.", Toast.LENGTH_LONG).show();
            setListView(true);
            return true;
        }
    }
}