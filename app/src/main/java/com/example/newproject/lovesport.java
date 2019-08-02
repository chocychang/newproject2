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
import android.widget.Toast;

public class lovesport extends AppCompatActivity {
    Button minus2,send;
    ImageButton plus2;
    int number,sum_id;
    EditText num,month,day,hour,minute;
    String save;
    public SQLdattta DH=null;
    public SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lovesport);

        minus2=(Button)findViewById(R.id.minus2);
        send=(Button)findViewById(R.id.send);
        plus2=(ImageButton)findViewById(R.id.plus2);

        num=(EditText)findViewById(R.id.num);
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
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!num.getText().equals("")&&!month.getText().equals("")&&day.getText().equals("")&&!hour.getText().equals("")&&minute.getText().equals(""))
                {
                    add("愛運動健身中心","03 575 3333","300新竹市東區公道五路二段469號4樓",num.getText().toString(),month.getText().toString(),day.getText().toString(),hour.getText().toString(),minute.getText().toString());
                    new AlertDialog.Builder(lovesport.this).setTitle("您已成功揪團!!!!")
                            .setMessage("愛運動健身中心")
                            .setIcon(android.R.drawable.ic_menu_edit)
                            .setPositiveButton("我知道了", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();/*setPositiveButton("加入購物車",null).show();*/
                /* Intent intent=new Intent(bigreeny.this,myact.class);
                startActivity(intent);*/}
                else
                    Toast.makeText(lovesport.this,"任一欄不可為空白!!",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void add(String toString, String toString1, String toString2,String toString7,String toString8,String toString9,String toString10,String toString11) {
        ContentValues contentValues=new ContentValues();
        contentValues.put("_id",String.valueOf(sum_id+1));
        contentValues.put("_restitle",toString.toString());
        contentValues.put("_phone",toString1.toString());
        contentValues.put("_address",toString2.toString());
        //contentValues.put("_time",toString3.toString());
        //contentValues.put("_time2",toString4.toString());
        contentValues.put("_number",toString7.toString());
        contentValues.put("_month",toString8.toString());
        contentValues.put("_day",toString9.toString());
        contentValues.put("_hour",toString10.toString());
        contentValues.put("_minute",toString11.toString());
        db.insert("TB123",null,contentValues);
    }
}
