package com.example.newproject;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class payyyyyyy extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public SQLdataaaa DH=null;
    public SQLiteDatabase db;
    public ListView LV1;
    Button b1,payy;
    EditText t2;
    int sum_id;
    String wow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payyyyyyy);
        DH=new SQLdataaaa(this);
        db=DH.getWritableDatabase();
        LV1=(ListView)findViewById(R.id.LV1);
        select();

        payy=(Button)findViewById(R.id.payy);
        payy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete("TB2019", "1", null);
                new AlertDialog.Builder(payyyyyyy.this)
                        .setMessage("您已成功結帳!!")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setPositiveButton("我知道了", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                payyyyyyy.this.finish();
                            }
                        }).show();
            }
        });
        Spinner spinner3 = (Spinner)findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> spi = ArrayAdapter.createFromResource(
                this, R.array.spinner3, R.layout.support_simple_spinner_dropdown_item);
        spi.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner3.setAdapter(spi);
        spinner3.setOnItemSelectedListener(this);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        wow=parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
