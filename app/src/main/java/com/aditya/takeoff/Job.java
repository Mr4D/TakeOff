package com.aditya.takeoff;

import android.util.Log;

public class Job {
    private String username;
    private String task;
    private String date;
    private String time;

    public Job(String cUsername, String cTask, String cDate, String cTime) {
        username = cUsername;
        task = cTask;
        date = cDate;
        time = cTime;


    }

    public String getUsername() {
        return username;
    }

    public String getTask() {
        return task;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
