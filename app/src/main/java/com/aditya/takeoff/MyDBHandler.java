package com.aditya.takeoff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// All database code from buckyroberts on github, source code available at https://github.com/buckyroberts/Source-Code-from-Tutorials/blob/master/Android_Beginners/049-054%20SQLite/49%20to%2054%20SQLite.txt

public class MyDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "takeoff.db";

    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_TASKS_ID = "id";
    private static final String COLUMN_TASKS_PARTNAME = "partname";
    private static final String COLUMN_TASKS_CHECK0 = "check0";
    private static final String COLUMN_TASKS_CHECK1 = "check1";
    private static final String COLUMN_TASKS_CHECK2 = "check2";
    private static final String COLUMN_TASKS_CHECK3 = "check3";
    private static final String COLUMN_TASKS_CHECK4 = "check4";
    private static final String COLUMN_TASKS_CHECK5 = "check5";
    private static final String COLUMN_TASKS_CHECK6 = "check6";
    private static final String COLUMN_TASKS_CHECK7 = "check7";
    private static final String COLUMN_TASKS_CHECK8 = "check8";
    private static final String COLUMN_TASKS_CHECK9 = "check9";

    private static final String TABLE_JOBS = "jobs";
    private static final String COLUMN_JOBS_ID = "id";
    private static final String COLUMN_JOBS_USERNAME = "username";
    private static final String COLUMN_JOBS_TASKS_ID = "tasks_id";
    private static final String COLUMN_JOBS_TIMESTAMP = "timestamp";
    private static final String COLUMN_JOBS_LONGITUTE = "longitude";
    private static final String COLUMN_JOBS_LATITUDE = "latitude";
    private static final String COLUMN_JOBS_DESCRIPTION = "description";
    private static final String COLUMN_JOBS_ALERT = "alert";
    private static final String COLUMN_JOBS_IMAGEURI = "img_uri";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "DROP TABLE IF EXISTS " + TABLE_TASKS;
        db.execSQL(query);
        query = "CREATE TABLE " + TABLE_TASKS + " (" +
                    COLUMN_TASKS_ID + " INTEGER PRIMARY KEY " + ", " +
                    COLUMN_TASKS_PARTNAME + " TEXT " + ", " +
                    COLUMN_TASKS_CHECK0 + " TEXT " + ", " +
                    COLUMN_TASKS_CHECK1 + " TEXT " + ", " +
                    COLUMN_TASKS_CHECK2 + " TEXT " + ", " +
                    COLUMN_TASKS_CHECK3 + " TEXT " + ", " +
                    COLUMN_TASKS_CHECK4 + " TEXT " + ", " +
                    COLUMN_TASKS_CHECK5 + " TEXT " + ", " +
                    COLUMN_TASKS_CHECK6 + " TEXT " + ", " +
                    COLUMN_TASKS_CHECK7 + " TEXT " + ", " +
                    COLUMN_TASKS_CHECK8 + " TEXT " + ", " +
                    COLUMN_TASKS_CHECK9 + " TEXT " +
                ");";
        db.execSQL(query);

        query = "DROP TABLE IF EXISTS " + TABLE_JOBS;
        db.execSQL(query);
        query = "CREATE TABLE " + TABLE_JOBS + " (" +
                    COLUMN_JOBS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                    COLUMN_JOBS_USERNAME + " TEXT " + ", " +
                    COLUMN_JOBS_TASKS_ID + " INTEGER " + ", " +
                    COLUMN_JOBS_TIMESTAMP + " DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')) " + ", " +
                    COLUMN_JOBS_LONGITUTE + " TEXT " + ", " +
                    COLUMN_JOBS_LATITUDE + " TEXT " + ", " +
                    COLUMN_JOBS_DESCRIPTION + " TEXT " + ", " +
                    COLUMN_JOBS_ALERT + " TEXT " + ", " +
                    COLUMN_JOBS_IMAGEURI + " TEXT " + ", " +
                    "FOREIGN KEY (" + COLUMN_JOBS_TASKS_ID + ") REFERENCES " + TABLE_TASKS + "(" + COLUMN_TASKS_ID + ")" +
                ");";
        db.execSQL(query);

        query = "INSERT INTO " + TABLE_TASKS + " VALUES ( 1, \"Flight Deck\", \"Emergency Exit\", \"Equipment\", \"Manuals\", \"Radio navigation charts\", \"Weight and balance sheet\", \"Hand fire extinguishers\", \"Oxygen Supply\", \"Flight controls\", \"Wheel well\", \"Doors and hatches\");";
        db.execSQL(query);

        query = "INSERT INTO " + TABLE_TASKS + " VALUES ( 2, \"Nose Gear\", \"Emergency Exit\", \"Equipment\", \"Manuals\", \"Radio navigation charts\", \"Weight and balance sheet\", \"Hand fire extinguishers\", \"Oxygen Supply\", \"Flight controls\", \"Wheel well\", \"Doors and hatches\");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public String[] getCheckList(String nfc_id) {
        String[] checks = new String[11];
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TASKS +
                            " WHERE " + COLUMN_TASKS_ID + " = " + nfc_id + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        checks[0] = c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK0));
        checks[1] = c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK1));
        checks[2] = c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK2));
        checks[3] = c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK3));
        checks[4] = c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK4));
        checks[5] = c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK5));
        checks[6] = c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK6));
        checks[7] = c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK7));
        checks[8] = c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK8));
        checks[9] = c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK9));
        checks[10] = c.getString(c.getColumnIndex(COLUMN_TASKS_PARTNAME));
        return checks;
    }

    public void submitJob(String username, String nfcId, String longitude, String latitude, String description, String alert, String imgUri) {
        ContentValues job = new ContentValues();
        job.put(COLUMN_JOBS_USERNAME, username);
        job.put(COLUMN_JOBS_TASKS_ID, nfcId);
        job.put(COLUMN_JOBS_LONGITUTE, longitude);
        job.put(COLUMN_JOBS_LATITUDE, latitude);
        job.put(COLUMN_JOBS_DESCRIPTION, description);
        job.put(COLUMN_JOBS_ALERT, alert);
        job.put(COLUMN_JOBS_IMAGEURI, imgUri);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_JOBS, null, job);
        db.close();
    }

    public Cursor getListContents(Boolean order) {
        // Code from Mitch Tabian (mitchtabian on GitHub), source code available at https://github.com/mitchtabian/ListAdapter
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " +
                            TABLE_JOBS + "." + COLUMN_JOBS_ID + " AS id, " +
                            TABLE_JOBS + "." + COLUMN_JOBS_USERNAME + " AS username, " +
                            TABLE_TASKS + "." + COLUMN_TASKS_PARTNAME + " AS task, " +
                            "DATE(" + TABLE_JOBS + "." + COLUMN_JOBS_TIMESTAMP + ") AS date, " +
                            "TIME(" + TABLE_JOBS + "." + COLUMN_JOBS_TIMESTAMP + ") AS time, " +
                            TABLE_JOBS + "." + COLUMN_JOBS_ALERT + " AS alert, " +
                            TABLE_JOBS + "." + COLUMN_JOBS_TIMESTAMP + " AS timestamp " +
                        "FROM " +
                            TABLE_JOBS +
                        " INNER JOIN " +
                            TABLE_TASKS +
                            " ON " + TABLE_TASKS + "." + COLUMN_TASKS_ID + " = " +  TABLE_JOBS + "." + COLUMN_JOBS_TASKS_ID;
        if (!order){
            query += " ORDER BY timestamp DESC;";
        }
        else {
            query += " ORDER BY timestamp ASC;";
        }



        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Job getJobDetails(String selectedJobId) {
        // Code from Mitch Tabian (mitchtabian on GitHub), source code available at https://github.com/mitchtabian/ListAdapter
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " +
                            TABLE_JOBS + "." + COLUMN_JOBS_ID + " AS id, " +
                            TABLE_JOBS + "." + COLUMN_JOBS_USERNAME + " AS username, " +
                            TABLE_TASKS + "." + COLUMN_TASKS_PARTNAME + " AS task, " +
                            "DATE(" + TABLE_JOBS + "." + COLUMN_JOBS_TIMESTAMP + ") AS date, " +
                            "TIME(" + TABLE_JOBS + "." + COLUMN_JOBS_TIMESTAMP + ") AS time, " +
                            TABLE_JOBS + "." + COLUMN_JOBS_LONGITUTE + " AS longitude, " +
                            TABLE_JOBS + "." + COLUMN_JOBS_LATITUDE + " AS latitude, " +
                            TABLE_JOBS + "." + COLUMN_JOBS_DESCRIPTION + " AS description, " +
                            TABLE_JOBS + "." + COLUMN_JOBS_ALERT + " AS alert, " +
                            TABLE_JOBS + "." + COLUMN_JOBS_IMAGEURI + " AS img_uri " +
                        "FROM " +
                            TABLE_JOBS +
                        " INNER JOIN " +
                            TABLE_TASKS +
                            " ON " + TABLE_TASKS + "." + COLUMN_TASKS_ID + " = " +  TABLE_JOBS + "." + COLUMN_JOBS_TASKS_ID +
                        " WHERE " +
                            TABLE_JOBS + "." + COLUMN_JOBS_ID + " = " + selectedJobId;
        Cursor jobDetails = db.rawQuery(query, null);
        jobDetails.moveToFirst();
        Job selectedJob = new Job(
                jobDetails.getString(jobDetails.getColumnIndex("id")),
                jobDetails.getString(jobDetails.getColumnIndex("username")),
                jobDetails.getString(jobDetails.getColumnIndex("task")),
                jobDetails.getString(jobDetails.getColumnIndex("date")),
                jobDetails.getString(jobDetails.getColumnIndex("time")),
                jobDetails.getString(jobDetails.getColumnIndex("longitude")),
                jobDetails.getString(jobDetails.getColumnIndex("latitude")),
                jobDetails.getString(jobDetails.getColumnIndex("description")),
                jobDetails.getString(jobDetails.getColumnIndex("alert")),
                jobDetails.getString(jobDetails.getColumnIndex("img_uri"))
        );
        return selectedJob;
    }
    public void deleteJob(String id) {
        String query = "DELETE FROM " + TABLE_JOBS + " WHERE " + TABLE_JOBS + "." + COLUMN_JOBS_ID + " = " + id + ";";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }
    public void updateDescription(String id, String description) {
        String query = "UPDATE " + TABLE_JOBS + " SET " + COLUMN_JOBS_DESCRIPTION  + " = \"" + description + "\" WHERE " + COLUMN_JOBS_ID + " = " + id + ";";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }
    public void updateAlert(String id, String alert) {
        String query = "UPDATE " + TABLE_JOBS + " SET " + COLUMN_JOBS_ALERT  + " = \"" + alert + "\" WHERE " + COLUMN_JOBS_ID + " = " + id + ";";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }
}
