package com.example.newproject;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import static com.example.newproject.App.CHANNEL_1_ID;
public class TimeService extends Service {
    public int settingTime = 5000;
    private NotificationManagerCompat notificationManager;
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int starId){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                showTimeUp();
            }
        },settingTime);
        return super.onStartCommand(intent,flags,starId);
    }
    public void showTimeUp(){
        notificationManager = NotificationManagerCompat.from(this);
        sendOnChannel1(TimeService.this);
    }
    public void sendOnChannel1(TimeService v){

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle("溫馨提醒：")
                .setContentText("您已經12小時沒出門了!!!!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }
}
