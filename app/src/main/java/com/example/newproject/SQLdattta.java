package com.example.newproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLdattta extends SQLiteOpenHelper {
    private final static String DB = "DB123";
    private final static String TB2 = "TB123";
    private final static int vs = 2;

    public SQLdattta(Context context) {
        super(context, DB, null, vs);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL2 = "CREATE TABLE IF NOT EXISTS " + TB2 + "(_id INTEGER,_restitle VARCHAR(20),_phone VARCHAR(20),_address VARCHAR(100),_name VARCHAR(20),_pay VARCHAR(20),_number VARCHAR(20),_month VARCHAR(20),_day VARCHAR(20),_hour VARCHAR(20),_minute VARCHAR(20))";
        db.execSQL(SQL2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQ2L = "DROP TABLE " + TB2;
        db.execSQL(SQ2L);
    }
}
