package com.example.newproject;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class t1 extends AppCompatActivity {
    public SQLdataaaa DH2=null;
    public SQLiteDatabase db2;
    private String[] tobuy = new String[] {"豆腐","蔥花","蝦仁"};
    private boolean[] bechecked=new boolean[tobuy.length];
    int sum_id2;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t1);
        DH2=new SQLdataaaa(this);
        db2=DH2.getWritableDatabase();

        FloatingActionButton fab1 = findViewById(R.id.fab);
        FloatingActionButton fabv = findViewById(R.id.fabvideo);
        FloatingActionButton fabp=findViewById(R.id.fabplus);
        fabp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(t1.this).setTitle("購物清單")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setMultiChoiceItems(tobuy, bechecked, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                // TODO Auto-generated method stub
                                bechecked[which] = isChecked;
                            }
                        }).setPositiveButton("加入購物車", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                /*String[] endtobuy={};
                                int j=0;
                                for(int i=0;i<tobuy.length;i++)
                                {
                                    if(Boolean.valueOf(tobuy[i])==true)
                                    {
                                        endtobuy[j]=tobuy[i];
                                        j++;
                                    }
                                }*/
                        String s=" ";
                        for(int i=0;i< tobuy.length;i++){
                            if(bechecked[i]==true){
                                s = s+tobuy[i]+" ";
                               add(tobuy[i]);
                            }
                        }
                        Toast.makeText(t1.this, "你已將" +s+"加入購物車了", Toast.LENGTH_SHORT).show();
                    }
                }).show();/*setPositiveButton("加入購物車",null).show();*/
            }
        });
        fabv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentvt = new Intent();
                intentvt.setClass(t1.this,vt1.class);
                startActivity(intentvt);
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent();
                intent2.setClass(t1.this,payyyyyyy.class);
                startActivity(intent2);
            }
        });
    }
    private void add(String toString) {
        ContentValues contentValues=new ContentValues();
        contentValues.put("_id",String.valueOf(sum_id2+1));
        contentValues.put("_pay",toString.toString());
        db2.insert("TB2019",null,contentValues);
    }
    private void select()
    {
        Cursor cursor=db2.query("TB2019",new String[]{"_id","_pay"},null,null,null,null,null);
        List<Map<String,Object>> items= new ArrayList<Map<String,Object>>();
        cursor.moveToFirst();
        sum_id2=cursor.getCount();
        for(int i=0;i<sum_id2;i++)
        {
            Map<String,Object>item=new HashMap<String,Object>();
            item.put("_id",cursor.getString(0));
            item.put("_pay",cursor.getString(1));
            items.add(item);
            cursor.moveToNext();
        }
    }
}
