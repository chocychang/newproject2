package com.example.newproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myact extends AppCompatActivity {
    public SQLdattta DH=null;
    public SQLiteDatabase db;
    public ListView LV1;
    int sum_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myact);

        DH=new SQLdattta(this);
        db=DH.getWritableDatabase();
        LV1=(ListView)findViewById(R.id.LV1);
        select();
    }
    private void select()
    {
        Cursor cursor=db.query("TB123",new String[]{"_id","_restitle","_phone","_address","_name","_pay","_number","_month","_day","_hour","_minute"},null,null,null,null,null);
        List<Map<String,Object>> items= new ArrayList<Map<String,Object>>();
        cursor.moveToFirst();
        sum_id=cursor.getCount();
        for(int i=0;i<sum_id;i++)
        {
            Map<String,Object>item=new HashMap<String,Object>();
            item.put("_id",cursor.getString(0));
            item.put("_restitle",cursor.getString(1));
            item.put("_phone",cursor.getString(2));
            item.put("_address",cursor.getString(3));
            item.put("_name",cursor.getString(4));
            item.put("_pay",cursor.getString(5));
            item.put("_number",cursor.getString(6));
            item.put("_month",cursor.getString(7));
            item.put("_day",cursor.getString(8));
            item.put("_hour",cursor.getString(9));
            item.put("_minute",cursor.getString(10));
            items.add(item);
            cursor.moveToNext();}
        SimpleAdapter SA=new SimpleAdapter(this,items,R.layout.sdata3,new String[]{"_id","_restitle","_phone","_address","_name","_pay","_number","_month","_day","_hour","_minute"},new int[]{R.id.tv_id,R.id.tv_title,R.id.tv_phone,R.id.tv_address,R.id.tv_name,R.id.tv_pay,R.id.tv_number,R.id.tv_month,R.id.tv_day,R.id.tv_hour,R.id.tv_minute});
        LV1.setAdapter(SA);
    }
}
