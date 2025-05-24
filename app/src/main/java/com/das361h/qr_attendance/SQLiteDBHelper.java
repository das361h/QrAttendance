package com.das361h.qr_attendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    public  static final String DB_NAME = "attendance.db";
    public  static final String T_NAME = "roster";

    public SQLiteDBHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    //using loop to make db creation query-
    // CREATE TABLE roster (
    // SID VARCHAR(8) PRIMARY KEY,
    // SNAME TEXT,
    // WEEK1 VARCHAR(1),
    // WEEK2 VARCHAR(1), until
    // WEEK52 VARCHAR(1)
    // );
    @Override
    public void onCreate(SQLiteDatabase SQLdb) {
        StringBuilder stb = new StringBuilder();
        stb.append("CREATE TABLE ").append(T_NAME).append(" (SID VARCHAR(8) PRIMARY KEY, SNAME TEXT");
        for (int i = 1; i <= 52; i++) {
            stb.append(", WEEK").append(i).append(" VARCHAR(1)");
        }
        stb.append(");");
        SQLdb.execSQL(stb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase SQLdb, int oldVersion, int newVersion) {
        SQLdb.execSQL("DROP TABLE IF EXISTS " + T_NAME);
        onCreate(SQLdb);
    }

    public void insertorupdate(String sid, String sname, String week, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + T_NAME + " WHERE SID = ?", new String[]{sid});
        ContentValues cv = new ContentValues();
        cv.put("SNAME", sname);
        cv.put(week, status);
        //if sid already exists, update otherwise insert it
        if (c.moveToFirst()) {
            cv.put("WEEK" + week, status);
            db.update(T_NAME, cv, "SID = ?", new String[]{sid});
        }
        else {
            cv.put("SID", sid);
            db.insert(T_NAME, null, cv);
        }
        c.close();
    }
}
