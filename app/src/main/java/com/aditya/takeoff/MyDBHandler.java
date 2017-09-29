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

//    private static final String TABLE_JOBS = "tasks";
//    private static final String COLUMN_JOBS_ID = "id";
//    private static final String COLUMN_JOBS_USERNAME = "username";
//    private static final String COLUMN_JOBS_TIMESTAMP = "timestamp";
//    private static final String COLUMN_JOBS_LONGITUTE = "longitude";
//    private static final String COLUMN_JOBS_LATITUDE = "latitude";
//    private static final String COLUMN_JOBS_DESCRIPTION = "description";
//    private static final String COLUMN_JOBS_ALERT = "alert";
//    private static final String COLUMN_JOBS_IMAGEURI = "img_uri";
//    private static final String COLUMN_JOBS_TASKS_ID = "tasks_id";



    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "DROP TABLE IF EXISTS " + TABLE_TASKS;
        db.execSQL(query);
        Log.d("dbdebug", "I am in on create");
        query = "CREATE TABLE " + TABLE_TASKS + " (" +
                COLUMN_TASKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                COLUMN_TASKS_PARTNAME + " TEXT " +
                            ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("dbdebug", "I am in on upgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // Add a new row to the database
    public void addTask (String task) {
        Log.d("dbdebug", "I am in addTask()");
        ContentValues values = new ContentValues();
        Log.d("dbdebug", "I have made content values");
        values.put(COLUMN_TASKS_PARTNAME, task);
        Log.d("dbdebug", "I have put stuff inside content values");
        SQLiteDatabase db = getWritableDatabase();
        Log.d("dbdebug", "I got a writable db");
        db.insert(TABLE_TASKS, null, values);
        Log.d("dbdebug", "I have just added the values");
        db.close();
        Log.d("dbdebug", "I have closed the db");
    }

    public String databaseToString() {
        Log.d("dbdebug", "I am in databasetostring()");
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TASKS + ";";

        // Cursor point to a location in your result
        Cursor c = db.rawQuery(query, null);

//        String res = DatabaseUtils.dumpCursorToString(c);


//        Move to the first row in results
        c.moveToFirst();
        while(!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_TASKS_PARTNAME)) != null) {
                dbString += c.getString(c.getColumnIndex(COLUMN_TASKS_PARTNAME));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;


    }

}
