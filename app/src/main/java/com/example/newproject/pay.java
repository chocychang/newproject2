package com.example.newproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pay extends AppCompatActivity {
    public SQLdataaaa DH=null;
    public SQLiteDatabase db;
    public ListView LV1;
    Button b1;
    EditText t2;
    int sum_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        DH=new SQLdataaaa(this);
        db=DH.getWritableDatabase();
        LV1=(ListView)findViewById(R.id.LV1);
        select();
        //t2=(EditText)findViewById(R.id.editText4);
        //b1=(Button)findViewById(R.id.button4) ;
        select();

        /*b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(t2.getText().toString());
                select();
            }
        });*/
    }
    private void add(String toString) {
        ContentValues contentValues=new ContentValues();
        contentValues.put("_id",String.valueOf(sum_id+1));
        contentValues.put("_pay",toString.toString());
        db.insert("TB2019",null,contentValues);
    }
    private void select()
    {
        Cursor cursor=db.query("TB2019",new String[]{"_id","_pay"},null,null,null,null,null);
        List<Map<String,Object>> items= new ArrayList<Map<String,Object>>();
        cursor.moveToFirst();
        sum_id=cursor.getCount();
        for(int i=0;i<sum_id;i++)
        {
            Map<String,Object>item=new HashMap<String,Object>();
            item.put("_id",cursor.getString(0));
            item.put("_pay",cursor.getString(1));
            items.add(item);
            cursor.moveToNext();
        }
        SimpleAdapter SA=new SimpleAdapter(this,items,R.layout.sdata2,new String[]{"_id","_pay"},new int[]{R.id.textView,R.id.textView2});
        LV1.setAdapter(SA);
    }


}
