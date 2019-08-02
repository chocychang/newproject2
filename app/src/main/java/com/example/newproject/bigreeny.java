package com.example.newproject;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class bigreeny extends AppCompatActivity {
    Button minus2,send;
    ImageButton plus2;
    ImageView minus,plus;
    int number,sum_id;
    EditText num,title,pay,month,day,hour,minute;
    String save,count;
    public SQLdattta DH=null;
    public SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigreeny);
        minus2=(Button)findViewById(R.id.minus2);
        send=(Button)findViewById(R.id.send);
        plus2=(ImageButton)findViewById(R.id.plus2);
        minus=(ImageView)findViewById(R.id.minus);
        plus=(ImageView)findViewById(R.id.minus);

        num=(EditText)findViewById(R.id.num);
        title=(EditText)findViewById(R.id.title);
        pay=(EditText)findViewById(R.id.pay);
        month=(EditText)findViewById(R.id.month);
        day=(EditText)findViewById(R.id.day);
        hour=(EditText)findViewById(R.id.hour);
        minute=(EditText)findViewById(R.id.minute);

        save=num.getText().toString();
        number=Integer.parseInt(save);

        DH=new SQLdattta(this);
        db=DH.getWritableDatabase();

        minus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number>0)
                {
                    number--;
                    String hey=String.valueOf(number);
                    num.setText(hey);
                }
            }
        });
        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number++;
                String hey2=String.valueOf(number);
                num.setText(hey2);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay.setText("現金付款");
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay.setText("信用卡付款");
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int month1,day1,hour1,minute1;
                month1=Integer.parseInt(month.getText().toString());
                day1=Integer.parseInt(day.getText().toString());
                hour1=Integer.parseInt(hour.getText().toString());
                minute1=Integer.parseInt(minute.getText().toString());
                if(!title.getText().equals("")&&!pay.getText().equals("")&&!num.getText().equals("")&&!month.getText().equals("")&&!day.getText().equals("")&&!hour.getText().equals("")&&!minute.getText().equals(""))
                {
                    if(month1>=1&&month1<=12&&day1>=1&&day1<=31&&hour1>=1&&hour1<=24&&minute1>=0&&minute1<=59)
                    {
                        add("光一肆號","02 2362 2211","台北市大安區新生南路三段76巷2號",title.getText().toString(),pay.getText().toString(),num.getText().toString(),month.getText().toString(),day.getText().toString(),hour.getText().toString(),minute.getText().toString());
                        new AlertDialog.Builder(bigreeny.this).setTitle("您已成功揪團!!!!")
                                .setMessage("光一肆號")
                                .setIcon(android.R.drawable.ic_menu_edit)
                                .setPositiveButton("我知道了", new DialogInterface.OnClickListener()
                                {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bigreeny.this.finish();
                            }
                        }).show();
                    }
                    else
                    {
                        new AlertDialog.Builder(bigreeny.this).setTitle("錯誤訊息")
                                .setMessage("請確認時間是否正確")
                                .setIcon(android.R.drawable.ic_menu_edit)
                                .setPositiveButton("我知道了", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                    }
                }
                else
                {
                    new AlertDialog.Builder(bigreeny.this).setTitle("錯誤訊息")
                            .setMessage("任一欄不可為空白")
                            .setIcon(android.R.drawable.ic_menu_edit)
                            .setPositiveButton("我知道了", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }
            }
        });
    }
    private void add(String toString, String toString1, String toString2, String toString5, String toString6,String toString7,String toString8,String toString9,String toString10,String toString11) {
        ContentValues contentValues=new ContentValues();
        contentValues.put("_id",String.valueOf(sum_id+1));
        contentValues.put("_restitle",toString.toString());
        contentValues.put("_phone",toString1.toString());
        contentValues.put("_address",toString2.toString());
        //contentValues.put("_time",toString3.toString());
        //contentValues.put("_time2",toString4.toString());
        contentValues.put("_name",toString5.toString());
        contentValues.put("_pay",toString6.toString());
        contentValues.put("_number",toString7.toString());
        contentValues.put("_month",toString8.toString());
        contentValues.put("_day",toString9.toString());
        contentValues.put("_hour",toString10.toString());
        contentValues.put("_minute",toString11.toString());
        db.insert("TB123",null,contentValues);
    }
}
