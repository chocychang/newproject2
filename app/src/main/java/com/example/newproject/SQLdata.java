package com.example.newproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLdata extends SQLiteOpenHelper {
    private final static String DB = "MyDB";
    private final static String TB = "MyTB";
    private final static int vs = 2;

    public SQLdata(Context context) {
        super(context, DB, null, vs);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL = "CREATE TABLE IF NOT EXISTS " + TB + "(_id INTEGER,_equipment VARCHAR(50),_minute VARCHAR(20),_second VARCHAR(20))";
        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String SQL = "DROP TABLE " + TB;
        sqLiteDatabase.execSQL(SQL);
    }
}
