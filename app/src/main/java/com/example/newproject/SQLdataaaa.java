package com.example.newproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLdataaaa extends SQLiteOpenHelper {
    private final static String DB = "DB2019.db";
    private final static String TB = "TB2019";
    private final static String TB2 = "TB123";
    private final static int vs = 2;

    public SQLdataaaa(Context context) {
        super(context, DB, null, vs);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "CREATE TABLE IF NOT EXISTS " + TB + "(_id INTEGER,_pay VARCHAR(20))";
        String SQL2 = "CREATE TABLE IF NOT EXISTS " + TB2 + "(_id INTEGER,_restitle VARCHAR(20),_phone VARCHAR(20),_address VARCHAR(100),_name VARCHAR(20),_pay VARCHAR(20),_number VARCHAR(20),_month VARCHAR(20),_day VARCHAR(20),_hour VARCHAR(20),_minute VARCHAR(20))";
        db.execSQL(SQL);
        db.execSQL(SQL2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL = "DROP TABLE " + TB;
        String SQ2L = "DROP TABLE " + TB2;
        db.execSQL(SQL);
        db.execSQL(SQ2L);
    }
}
