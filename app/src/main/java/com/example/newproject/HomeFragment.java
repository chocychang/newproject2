package com.example.newproject;

import android.Manifest;
import android.app.Notification;
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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static com.example.newproject.App.CHANNEL_2_ID;
public class HomeFragment extends Fragment {
    ImageButton frien,eat,exercise;
    TextView second;
    String secs,mins,s1,s3,s4,s2;
    long min1,sec1,min,sec;
    FloatingActionButton chatbot;
    Button button,button3,button4;
    int RESULT_OK=-1;
    private BluetoothAdapter mBluetoothAdapter;
    private List<String> bluetoothdeviceslist = new ArrayList<String>();
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    double dis;
    private NotificationManagerCompat notificationManager;
    //記得修改
    private String ID_target1 = "BR522975";//回到家beacon
    private String ID_target2 = "BR522983";//經過場地
    private String ID_target3 = "BR522975";//器材
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        button4=(Button)rootView.findViewById(R.id.button4);
     
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                final String deviceToken = instanceIdResult.getToken();
                Log.i("MainActivity","token "+deviceToken);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = "default_notification_channel_id";
            String channelName = "default_notification_channel_name";
            NotificationManager notificationManager =
                    getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        notificationManager = NotificationManagerCompat.from(getActivity());

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBluetoothPermission();
        SearchBluetooth();
        if(mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();

        }
        else
            mBluetoothAdapter.startDiscovery();
        eat=(ImageButton)rootView.findViewById(R.id.eat);
        exercise=(ImageButton) rootView.findViewById(R.id.exercise);
        frien=(ImageButton) rootView.findViewById(R.id.frien);
        second=(TextView)rootView.findViewById(R.id.timer);
        chatbot=(FloatingActionButton)rootView.findViewById(R.id.chatbot);
        button=(Button)rootView.findViewById(R.id.button);
        button3=(Button)rootView.findViewById(R.id.button3);
       /* button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent();
              intent.setClass(getActivity(),reservation.class);
              startActivity(intent);
            }
        });*/
        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),food.class);
                startActivity(intent);
            }
        });
        exercise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), gym.class);
                Bundle bundle = new Bundle();
                bundle.putString("inputs1", s1);
                intent2.putExtras(bundle);
                startActivityForResult(intent2,5);
            }
        });
       frien.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),invite.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
    public void sendOnChannel2(BroadcastReceiver v){

        Notification notification = new NotificationCompat.Builder(getActivity(),CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle("活動預報：")
                .setContentText("明日8時此廣場將會舉辦XXX太極拳大賽！")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(2,notification);
    }
    private void checkBluetoothPermission() {
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
            // Android M Permission check
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }
    }
    public void SearchBluetooth(){
        if(mBluetoothAdapter == null){
            Toast.makeText(getActivity(),"not find the bluetooth",Toast.LENGTH_SHORT).show();
            getActivity().finish();
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
        getActivity().registerReceiver(myreceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getActivity().registerReceiver(myreceiver, filter);
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

                    if(ID_target1.equals(device.getName()))
                    {
                        Toast.makeText(getActivity(), "回到家了~", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getActivity(),TimeService.class);
                        getActivity().startService(intent1);
                    }
                    else if(ID_target2.equals(device.getName())) {
                        sendOnChannel2(this);
                    }
                    else if(ID_target3.equals(device.getName())) {
                        Toast.makeText(getActivity(), "歡迎使用器材！", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(getActivity(),TimeService.class);
                        getActivity().startService(intent2);
                    }
                }
                catch(Exception e){
                }
            }
        }
    };
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==5&&resultCode==RESULT_OK) {
            s1=data.getExtras().getString("outputs1");
            Intent intent = new Intent();
            intent.setClass(getActivity(),fund.class);
            Bundle bundle = new Bundle();
            bundle.putString("inputs2", s2);
            intent.putExtras(bundle);
            startActivityForResult(intent,6);
        }
        else if(requestCode==6&&resultCode==RESULT_OK) {//-1
            s2=data.getExtras().getString("outputs2");
            Intent intent = new Intent();
            intent.setClass(getActivity(),abdomen.class);
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
           /*Intent intent = new Intent();
            intent.setClass(getActivity(),achievement.class);
            Bundle bundle = new Bundle();
            Bundle bundle2 = new Bundle();
            String s1=String.valueOf(min);
            String s2=Long.toString(sec);
            bundle.putString("inputs1",s1);
            bundle2.putString("inputs2",s2);
            intent.putExtras(bundle);
            intent.putExtras(bundle2);
            startActivity(intent);*/
        }
    }
}

