package com.example.newproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLdata2 extends SQLiteOpenHelper {
    private final static String DB2 = "MyDB2";
    private final static String TB2 = "MyTB2";
    private final static int vs = 2;

    public SQLdata2(Context context) {
        super(context, DB2, null, vs);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL = "CREATE TABLE IF NOT EXISTS " + TB2 + "(_id INTEGER,_pay VARCHAR(20))";
        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String SQL = "DROP TABLE " + TB2;
        sqLiteDatabase.execSQL(SQL);
    }
}
