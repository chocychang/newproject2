package com.example.newproject;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class reservation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public SQLdata DH=null;
    public SQLiteDatabase db;
    public ListView LV1;
    Button b1,b2;
    EditText t2;
    int sum_id,key=0,a=0;
    TextView tv;
    String wow,wow2;
    String running;
    public static final String KEY_ID = "_id";
    private CountDownTimer mCountDownTimer;
    private TextView mTextViewCountDown;
    private boolean mTimerRunning;

    private long mTimeLeftInMillis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        mTextViewCountDown=(TextView)findViewById(R.id.textView21);
        tv=(TextView)findViewById(R.id.textView19);
        DH=new SQLdata(this);
        db=DH.getWritableDatabase();
        LV1=(ListView)findViewById(R.id.LV);
        select();
        String a=String.valueOf(sum_id);
        tv.setText(a);
        Spinner spnSex = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapSexList = ArrayAdapter.createFromResource(
                this, R.array.spinner, R.layout.support_simple_spinner_dropdown_item);
        adapSexList.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnSex.setAdapter(adapSexList);
        spnSex.setOnItemSelectedListener(this);

        Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> spi = ArrayAdapter.createFromResource(
                this, R.array.spinner2, R.layout.support_simple_spinner_dropdown_item);
        spi.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(spi);
        spinner2.setOnItemSelectedListener(this);

        t2=(EditText)findViewById(R.id.editText2);
       // t3=(EditText)findViewById(R.id.editText3);
        //t4=(EditText)findViewById(R.id.editText4);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (key == 0)
                {
                    add(t2.getText().toString(),wow,wow2);
                    int time=Integer.parseInt(wow);
                    int time2=Integer.parseInt(wow2);
                    mTimeLeftInMillis=(time*60+time2)*1000;
                    if (mTimerRunning) {
                    } else {
                        startTimer();
                    }
                    select();
                    key=1;
                }
                else
                    Toast.makeText(reservation.this, "還有人在用!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void add(String toString, String toString1, String toString2) {
        ContentValues contentValues=new ContentValues();
        contentValues.put("_id",String.valueOf(sum_id+1));
        contentValues.put("_equipment",toString.toString());
        contentValues.put("_minute",toString1.toString());
        contentValues.put("_second",toString2.toString());
        db.insert("MyTB",null,contentValues);
    }
    private void select()
    {
        Cursor cursor=db.query("MyTB",new String[]{"_id","_equipment","_minute","_second"},null,null,null,null,null);
        List<Map<String,Object>> items= new ArrayList<Map<String,Object>>();
        cursor.moveToFirst();
        sum_id=cursor.getCount();
        for(int i=0;i<sum_id;i++)
        {
            Map<String,Object>item=new HashMap<String,Object>();
            item.put("_id",cursor.getString(0));
            item.put("_equipment",cursor.getString(1));
            item.put("_minute",cursor.getString(2));
            item.put("_second",cursor.getString(3));
            items.add(item);
            cursor.moveToNext();
        }
        SimpleAdapter SA=new SimpleAdapter(this,items,R.layout.sdata,new String[]{"_id","_equipment","_minute","_second"},new int[]{R.id.textView5,R.id.textView6,R.id.textView7,R.id.textView8});
        LV1.setAdapter(SA);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            wow = parent.getItemAtPosition(position).toString();
            wow2=parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                long[] vibrate_effect =
                        {1000, 500, 1000, 400, 1000, 300, 1000, 200, 1000, 100};
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                            "YOUR_CHANNEL_NAME",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
                    mNotificationManager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
                        .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                        .setContentTitle("跑步機的使用者已使用完成!!") // title for notification
                        .setAutoCancel(true)
                        .setVibrate(vibrate_effect); // clear notification after click
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(reservation.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(pi);
                mNotificationManager.notify(0, mBuilder.build());
                Toast.makeText(reservation.this, "結束!!!!!", Toast.LENGTH_SHORT).show();

               new AlertDialog.Builder(reservation.this)
                        .setMessage("器材使用時間已到唷!!")
                        .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getApplicationContext(), R.string.gogo, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                key=0;
                fresh();
                select();
            }
        }.start();

        mTimerRunning = true;
    }
    private void fresh() {
        String id=String.valueOf(sum_id);
        db.delete("MyTB", "_id=?", new String[] { id});
    }
    private void update(String toString, String toString1)
    {
        String id=String.valueOf(sum_id);
        ContentValues contentValues=new ContentValues();
        contentValues.put("_minute", toString.toString());
        contentValues.put("_second", toString1.toString());
        db.update("MyTB", contentValues, "_id=?", new String[] {id});
        select();
    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String yo=String.valueOf(minutes);
        String yo2=String.valueOf(seconds);
        update(yo,yo2);
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}
