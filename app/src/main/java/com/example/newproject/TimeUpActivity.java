package com.example.newproject;
import static com.example.newproject.App.CHANNEL_1_ID;
import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TimeUpActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_up);
        notificationManager = NotificationManagerCompat.from(this);
        sendOnChannel1(this);
    }
    public void sendOnChannel1(TimeUpActivity v){

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
