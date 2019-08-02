package com.example.newproject;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class clock extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private TextView mPercentage;

    private int progress = 0;//變化參數
    private int hours, mins,key=0;
    private Context mContext;
    private TextView mShow;
    private CountDownTimer countDownTimer;
    private Button show_picker;
    private Button BT_Search;
    private TextView TV1;
    private BluetoothAdapter mBluetoothAdapter;
    private List<String> bluetoothdeviceslist = new ArrayList<String>();
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private String ID_target = "BR517487";
    double dis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        BT_Search = (Button) findViewById(R.id.button2);
        TV1 = (TextView) findViewById(R.id.textView2);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBluetoothPermission();
        SearchBluetooth();
        BT_Search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mBluetoothAdapter.isDiscovering()){
                    mBluetoothAdapter.cancelDiscovery();
                }
                mBluetoothAdapter.startDiscovery();
            }
        });

        mContext = this;
        initView();
    }

    private void checkBluetoothPermission() {
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
            // Android M Permission check
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }
    }
    public void SearchBluetooth(){
        if(mBluetoothAdapter == null){
            Toast.makeText(this,"not find the bluetooth",Toast.LENGTH_SHORT).show();
            finish();
        }
        if(!mBluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,1);
            Set<BluetoothDevice> myDevices = mBluetoothAdapter.getBondedDevices();
            if(myDevices.size() > 0) {
                for(BluetoothDevice device : myDevices)
                    bluetoothdeviceslist.add(device.getName()+":"+device.getAddress()+"\n");
            }
        }
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(myreceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(myreceiver, filter);
    }
    private final BroadcastReceiver myreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //收到的廣播類型
            String action = intent.getAction();
            //發現設備的廣播
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //從intent中獲取設備
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                double txPower = -59;
                double ratio = rssi*1.0/txPower;
                if (ratio < 1.0) {
                    dis = Math.pow(ratio,10);
                }
                else {
                    dis =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
                }

                try{
                    if(device.getName().equals("BR522962"))
                    {
                        TV1.setText(device.getName().toString()+"   "+Double.toString(dis));
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
                                .setContentTitle("剩餘時間") // title for notification
                                .setContentText("此健身器材還剩25分鐘歐")// message for notification
                                .setAutoCancel(true)
                                .setVibrate(vibrate_effect); // clear notification after click

                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        PendingIntent pi = PendingIntent.getActivity(clock.this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                        mBuilder.setContentIntent(pi);
                        mNotificationManager.notify(0, mBuilder.build());
                    }
                }
                catch(Exception e){
                }
            }
        }
    };


    private void initView() {
        show_picker = (Button) findViewById(R.id.show_picker);
        mShow = (TextView) findViewById(R.id.show);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mPercentage = (TextView) findViewById(R.id.progress_tv);
    }
    //設定時間的按鈕
    public void timePickerBtn(View view) {
        //防呆
        show_picker.setClickable(true);
//        //假如重複選擇時間，暫停上一個計時器
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
        }
        TimePickerDialog timepicker = new TimePickerDialog(mContext, onTimeSetListener, hours, mins, true);
        timepicker.show();
    }

    //取消倒數
    public void canelTimeBtn(View view) {
        show_picker.setClickable(true);
        mPercentage.setText("0%");
        mProgressBar.setProgress(0);
        progress = 0;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
        }
    }

    //TimePicker監聽
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //只format hours and mins
            SimpleDateFormat sdf = new SimpleDateFormat("HHmm");

            //抓取現在的時間
            Calendar calendar = Calendar.getInstance();
            int nowHours = calendar.get(Calendar.HOUR_OF_DAY);
            int nowMins = calendar.get(Calendar.MINUTE);

            Long time = null;
            //確認格式是我們在SimpleDateFormat內指定的樣子"HHmm"
            Log.d("checkpoint", " 確認選取的時間 = " + format(hourOfDay) + format(minute));
            Log.d("checkpoint", "卻線線在的時間 = " + format(nowHours) + format(nowMins));

            try {
                //選取時間 - 現在時間
                Date pickDate = sdf.parse(format(hourOfDay) + format(minute));
                Date nowDate = sdf.parse(format(nowHours) + format(nowMins));
                Long pickLongDate = pickDate.getTime();
                Long nowLongDate = nowDate.getTime();
                time = pickLongDate - nowLongDate;
                //設定ProgressBar
                mProgressBar.setMax(toIntExact(time));
                Log.d("checkpoint", "checkpoint = " + time);
            } catch (ParseException e) {
                e.printStackTrace();
                Log.d("checkpoint", "error - " + e);
            }

            if (time != null) {
                //time為毫秒 帶入倒數計時器CountDownTimer
                final Long finalTime = time;
                countDownTimer = new CountDownTimer(finalTime, 500) {
                    @Override
                    public void onFinish() {
                        mShow.setText("Done!");
                        if (progress != 0) {
                            mPercentage.setText("100%");
                            mProgressBar.setProgress(toIntExact(finalTime));
                        } else {
                            mPercentage.setText("0%");
                        }
                        show_picker.setClickable(true);
                    }

                    @Override
                    public void onTick(long millisUntilFinished) {
                        //這方法中間如果遇到個位數字，前方自動補0 "08"
                        NumberFormat f = new DecimalFormat("00");
                        long hour = (millisUntilFinished / 3600000) % 24;
                        long min = (millisUntilFinished / 60000) % 60;
                        long sec = (millisUntilFinished / 1000) % 60;

                        //換算必須用Double，如果使用int不管怎麼除都會是0
                        progress = (int) ((Double.valueOf(finalTime - millisUntilFinished) / Double.valueOf(finalTime)) * 100);
                        //在計時器內對ProgrssBar做每秒的更新
                        mProgressBar.setProgress(toIntExact(finalTime - millisUntilFinished));
                        //設定旁邊的文字%
                        mPercentage.setText(progress + "%");
                        //設定倒數計時
                        mShow.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                        if(Integer.valueOf(f.format(hour))<=5&&key==0)
                        {
                            long[] vibrate_effect =
                                    {1000, 500, 1000, 400, 1000, 300, 1000, 200, 1000, 100};
                            key=1;
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
                                    .setContentTitle("剩餘時間") // title for notification
                                    .setContentText("此健身器材還剩5分鐘歐")// message for notification
                                    .setAutoCancel(true)
                                    .setVibrate(vibrate_effect); // clear notification after click

                            Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                            PendingIntent pi = PendingIntent.getActivity(clock.this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                            mBuilder.setContentIntent(pi);
                            mNotificationManager.notify(0, mBuilder.build());
                        }
                    }
                }.start();
            }
        }
    };


    //重新把long變成String，中間如果遇到個位數字，前方自動補0 "08"
    private String format(long value) {
        String valueTwo = stringValue(value);
        if (valueTwo.length() == 1) {
            return "0" + valueTwo;
        }
        return valueTwo;
    }

    //轉換器long convert to String
    private String stringValue(long value) {
        return String.valueOf(value);
    }

    //轉換器 Long convert to int
    public static int toIntExact(long value) {
        if ((int) value != value) {
            throw new ArithmeticException("integer overflow");
        }
        return (int) value;
    }
}
