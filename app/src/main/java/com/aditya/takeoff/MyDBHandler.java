package com.aditya.takeoff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
                    COLUMN_JOBS_TIMESTAMP + " TIMESTAMP " + ", " +
                    COLUMN_JOBS_LONGITUTE + " DECIMAL(12,9) " + ", " +
                    COLUMN_JOBS_LATITUDE + " DECIMAL(12,9) " + ", " +
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

//    public String databaseToString() {
//        String dbString = "";
//        SQLiteDatabase db = getWritableDatabase();
//        String query = "SELECT * FROM " + TABLE_TASKS + ";";
//        Cursor c = db.rawQuery(query, null);
//        c.moveToFirst();
//        while(!c.isAfterLast()) {
//            dbString += c.getString(c.getColumnIndex(COLUMN_TASKS_ID));
//            dbString += "\n";
//            dbString += c.getString(c.getColumnIndex(COLUMN_TASKS_PARTNAME));
//            dbString += "\n";
//            dbString += c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK0));
//            dbString += "\n";
//            dbString += c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK1));
//            dbString += "\n";
//            dbString += c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK2));
//            dbString += "\n";
//            dbString += c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK3));
//            dbString += "\n";
//            dbString += c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK4));
//            dbString += "\n";
//            dbString += c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK5));
//            dbString += "\n";
//            dbString += c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK6));
//            dbString += "\n";
//            dbString += c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK7));
//            dbString += "\n";
//            dbString += c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK8));
//            dbString += "\n";
//            dbString += c.getString(c.getColumnIndex(COLUMN_TASKS_CHECK9));
//            dbString += "\n";
//            dbString += "\n";
//            dbString += "\n";
//            c.moveToNext();
//        }
//        db.close();
//        return dbString;
//    }
}
