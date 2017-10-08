package com.aditya.takeoff;

public class Job {
    private String username;
    private String task;
    private String time;

    public Job(String cUsername, String cTask, String cTime) {
        username = cUsername;
        task = cTask;
        time = cTime;
    }

    public String getUsername() {
        return username;
    }

    public String getTask() {
        return task;
    }

    public String getTime() {
        return time;
    }
}
