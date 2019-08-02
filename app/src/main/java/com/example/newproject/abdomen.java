package com.example.newproject;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class abdomen extends AppCompatActivity {
    private VideoView vidView;
    private MediaController vidControl;
    ImageButton startbutton,pausebutton,stopbutton;
    private long startTime;
    long pausetime,spentTime,spentime2=0;
    private Handler handler = new Handler();
    private Handler handler2 = new Handler();
    long minius,seconds,pausemin=0,pausesec=0;
    private final int STOP = -1;
    private int status = 1;
    int a=1,b=1,c=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abdomen);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        vidView = (VideoView) findViewById(R.id.myVideo);
        vidControl = new MediaController(this);
        vidView.setMediaController(vidControl);
        vidControl.setMediaPlayer(vidView);
        vidView.setVideoURI(Uri.parse("android.resource://com.example.newproject/" + R.raw.abdomen));
        vidView.requestFocus();
        vidView.start();

        startbutton=(ImageButton)findViewById(R.id.startbutton);
        pausebutton=(ImageButton)findViewById(R.id.pausebutton);
        stopbutton=(ImageButton)findViewById(R.id.stopbutton);
        startbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(status==STOP) {//第二次開始
                    status = 1;
                    a=1;
                    b=0;
                    c=0;
                    //startTime = System.currentTimeMillis();
                    //設定定時要執行的方法
                    handler.removeCallbacks(updateTimer);
                    //設定Delay的時間
                    handler.postDelayed(updateTimer, 1000);
                }
                else if(b==1)//如果是第一次
                {
                    startTime = System.currentTimeMillis();
                    //設定定時要執行的方法
                    handler.removeCallbacks(updateTimer);
                    //設定Delay的時間
                    handler.postDelayed(updateTimer, 1000);
                }
            }
        });
        pausebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(c==1) {
                    status = STOP;
                    a = 0;
                    pausetime = System.currentTimeMillis();
                    //handler2.removeCallbacks(pause);
                    handler2.postDelayed(pause, 1000);
                }
                else
                {
                    status = STOP;
                    a = 0;
                    pausetime = System.currentTimeMillis()+pausetime;
                    //handler2.removeCallbacks(pause);
                    handler2.postDelayed(pause, 0);
                }
            }
        });
        stopbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                Bundle bundle2 = new Bundle();
                String s1=String.valueOf(minius);
                String s2=Long.toString(seconds);
                bundle.putString("inputs1",s1);
                bundle2.putString("inputs2",s2);
                intent.putExtras(bundle);
                intent.putExtras(bundle2);
                setResult(RESULT_OK,intent);
                abdomen.this.finish();
            }
        });
    }
    private Runnable updateTimer = new Runnable() {
        public void run() {
            if (status != STOP)
            {
                final TextView time = (TextView) findViewById(R.id.timer);
                spentTime = System.currentTimeMillis() - startTime-spentime2-1000;
                //計算目前已過分鐘數
                minius = (spentTime/1000)/60;
                //計算目前已過秒數
                seconds = (spentTime/1000) % 60;
                if (minius<10&&seconds<10)
                    time.setText("0"+minius+":0"+seconds);
                else if (minius<10&&seconds>=10)
                    time.setText("0"+minius+":"+seconds);
                else if (minius>=10&&seconds<10)
                    time.setText(minius+":0"+seconds);
                else
                    time.setText(minius+":"+seconds);
                handler.postDelayed(this, 1000);
            }
        }
    };
    private Runnable pause = new Runnable() {
        public void run() {
            if(a==0) {
                // 判斷是否掛起
                spentime2+=1000;
                //計算目前已過分鐘數
                pausemin = (spentime2 / 1000) / 60;
                //計算目前已過秒數
                pausesec = (spentime2 / 1000) % 60;
                handler2.postDelayed(this, 1000);
            }
        }
    };
}
