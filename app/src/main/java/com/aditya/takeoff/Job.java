package com.aditya.takeoff;

public class Job {

    // Code based on Mitch Tabian's project (mitchtabian on GitHub), source code available at https://github.com/mitchtabian/ListAdapter
    private String id;
    private String username;
    private String task;
    private String date;
    private String time;
    private String longitude;
    private String latitude;
    private String description;
    private String alert;
    private String img_uri;


    public Job(String cId, String cUsername, String cTask, String cDate, String cTime, String cAlert) {
        id = cId;
        username = cUsername;
        task = cTask;
        date = cDate;
        time = cTime;
        alert = cAlert;
    }
    public Job(String cId, String cUsername, String cTask, String cDate, String cTime, String cLongitude, String cLatitude, String cDescription, String cAlert, String cImg_uri) {
        id = cId;
        username = cUsername;
        task = cTask;
        date = cDate;
        time = cTime;
        longitude = cLongitude;
        latitude = cLatitude;
        description = cDescription;
        alert = cAlert;
        img_uri = cImg_uri;
    }

    public String getLongitude() {
        return longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getDescription() {
        return description;
    }
    public String getAlert() {
        return alert;
    }
    public String getImg_uri() {
        return img_uri;
    }
    public String getId() {
        return id;
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
