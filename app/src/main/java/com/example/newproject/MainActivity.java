package com.example.newproject;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    ImageButton frien,eat,exercise;
    TextView second;
    String secs,mins,s1,s3,s4,s2;
    long min1,sec1,min,sec;
    FloatingActionButton chatbot;
    Button button,button3,button4;
    
    private BluetoothAdapter mBluetoothAdapter;
    private List<String> bluetoothdeviceslist = new ArrayList<String>();
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private String ID_target = "BR517487";
    double dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.head_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("個人健康助理");
        button4=(Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this,payyyyyyy.class);
        startActivity(intent);
    }
    });
        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBluetoothPermission();
        SearchBluetooth();
        
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        eat=(ImageButton) findViewById(R.id.eat);
        exercise=(ImageButton) findViewById(R.id.exercise);
        frien=(ImageButton) findViewById(R.id.frien);
        second=(TextView)findViewById(R.id.timer);
        chatbot=(FloatingActionButton)findViewById(R.id.chatbot);
        button=(Button)findViewById(R.id.button);
        button3=(Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBluetoothAdapter.isDiscovering()){
                    mBluetoothAdapter.cancelDiscovery();
                }
                mBluetoothAdapter.startDiscovery();
            }

        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        .setContentTitle("今天打球!!") // title for notification
                        .setContentText("晚上19:00 交大籃球場揪3打3")// message for notification
                        .setAutoCancel(true)
                        .setVibrate(vibrate_effect); // clear notification after click
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(pi);
                mNotificationManager.notify(0, mBuilder.build());
            }
        });
        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("jp.naver.line.android");
                if( intent != null )
                {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity( intent );
                }
            }
        });
        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,food.class);
                startActivity(intent);
            }
        });
        exercise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent2 = new Intent();
                intent2.setClass(MainActivity.this, gym.class);
                Bundle bundle = new Bundle();
                bundle.putString("inputs1", s1);
                intent2.putExtras(bundle);
                startActivityForResult(intent2,5);
            }
        });
       /* frien.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,frien.class);
                startActivity(intent);
            }
        });*/
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
                        Intent intent2=new Intent();
                        intent2.setClass(MainActivity.this,reservation.class);
                        startActivity(intent2);
                    }
                }
                catch(Exception e){
                }
            }
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.homepage){
            Toast.makeText(this,"首頁",Toast.LENGTH_LONG).show();
        }
        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==5&&resultCode==RESULT_OK) {
            s1=data.getExtras().getString("outputs1");
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,fund.class);
            Bundle bundle = new Bundle();
            bundle.putString("inputs2", s2);
            intent.putExtras(bundle);
            startActivityForResult(intent,6);
        }
        else if(requestCode==6&&resultCode==RESULT_OK) {//-1
            s2=data.getExtras().getString("outputs2");
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,abdomen.class);
            Bundle bundle = new Bundle();
            bundle.putString("inputs3", s3);
            intent.putExtras(bundle);
            startActivityForResult(intent,7);
        }
        /*else if(requestCode==6&&resultCode==RESULT_FIRST_USER) {//1
            s3=data.getExtras().getString("outputs4");
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,leglayout.class);
            Bundle bundle = new Bundle();
            bundle.putString("inputs3", s4);
            intent.putExtras(bundle);
            startActivityForResult(intent,7);
        }
        else if(requestCode==6&&resultCode==RESULT_CANCELED) {//0
            s3=data.getExtras().getString("outputs4");
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,corelayout.class);
            Bundle bundle = new Bundle();
            bundle.putString("inputs3", s4);
            intent.putExtras(bundle);
            startActivityForResult(intent,7);
        }
        else if(requestCode==6&&resultCode==BIND_ALLOW_OOM_MANAGEMENT) {//10
            s3=data.getExtras().getString("outputs4");
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,shoulderlayout.class);
            Bundle bundle = new Bundle();
            bundle.putString("inputs3", s4);
            intent.putExtras(bundle);
            startActivityForResult(intent,7);
        }
        else if(requestCode==6&&resultCode==CONTEXT_RESTRICTED) {//4
            s3=data.getExtras().getString("outputs4");
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,breastlayout.class);
            Bundle bundle = new Bundle();
            bundle.putString("inputs3", s4);
            intent.putExtras(bundle);
            startActivityForResult(intent,7);
        }
        else if(requestCode==6&&resultCode==DEFAULT_KEYS_SEARCH_LOCAL) {//3
            s3=data.getExtras().getString("outputs4");
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,backlayout.class);
            Bundle bundle = new Bundle();
            bundle.putString("inputs3", s4);
            intent.putExtras(bundle);
            startActivityForResult(intent,7);
        }
        else if(requestCode==6&&resultCode==TRIM_MEMORY_MODERATE) {//60
            s3=data.getExtras().getString("outputs4");
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,abdolayout.class);
            Bundle bundle = new Bundle();
            bundle.putString("inputs3", s4);
            intent.putExtras(bundle);
            startActivityForResult(intent,7);
        }
        else if(requestCode==6&&resultCode==DEFAULT_KEYS_SHORTCUT) {//2
            s3=data.getExtras().getString("outputs4");
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,armlayout.class);
            Bundle bundle = new Bundle();
            bundle.putString("inputs3", s4);
            intent.putExtras(bundle);
            startActivityForResult(intent,7);
        }
        else if(requestCode==6&&resultCode==TRIM_MEMORY_RUNNING_CRITICAL) {//15
            s3=data.getExtras().getString("outputs4");
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,legtwolayout.class);
            Bundle bundle = new Bundle();
            bundle.putString("inputs3", s4);
            intent.putExtras(bundle);
            startActivityForResult(intent,7);
        }
        else if(requestCode==6&&resultCode==TRIM_MEMORY_BACKGROUND) {//40
            s3=data.getExtras().getString("outputs4");
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,backtwolayout.class);
            Bundle bundle = new Bundle();
            bundle.putString("inputs3", s4);
            intent.putExtras(bundle);
            startActivityForResult(intent,7);
        }*/
        else if(requestCode==7&&resultCode==RESULT_OK)
        {
            mins=data.getExtras().getString("inputs1");
            secs=data.getExtras().getString("inputs2");
            min1=Long.parseLong(mins);
            sec1=Long.parseLong(secs);
            min=min+min1;
            sec=sec+sec1;
            if (min<10&&sec<10)
                second.setText("0"+min+":0"+sec);
            else if (min<10&&sec>10)
                second.setText("0"+min+":"+sec);
            else if (min>10&&sec<10)
                second.setText(min+":0"+sec);
            else
                second.setText(min+":"+sec);
        }
    }
}
