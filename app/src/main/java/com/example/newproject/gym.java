package com.example.newproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class gym extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    TextView qrcodebutton,fundamentalbutton,equipmentbutton;
    FloatingActionButton clock;
    String s1;
    ImageButton reservation,search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);

        fundamentalbutton = (TextView) findViewById(R.id.fundamentalbutton);
        qrcodebutton = (TextView) findViewById(R.id.qrcodebutton);
        equipmentbutton = (TextView) findViewById(R.id.equipmentbutton);
        clock=(FloatingActionButton) findViewById(R.id.clock);
        reservation=(ImageButton)findViewById(R.id.reservation);
        search=(ImageButton)findViewById(R.id.search);
        toolbar = findViewById(R.id.head_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("GYM");
        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(gym.this)
                        .setMessage("這區主要是練腹部的唷!")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setPositiveButton("我知道了", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gym.this.finish();
                            }
                        }).show();
            }
        });
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(gym.this,reservation.class);
                startActivity(intent);
            }
        });
        clock.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(gym.this,clock.class);
                startActivity(intent);
            }
        });
        fundamentalbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(gym.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("outputs1", s1);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                gym.this.finish();
            }
        });
        qrcodebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // 連結ZXING的API，開啟條碼掃描器
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                // 設定參數，兩種條碼都讀
                intent.putExtra("SCAN_MODE", "SCAN_MODE");
                //只判斷QRCode
                //intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                //只判斷二維條碼
                //intent.putExtra("SCAN_MODE", "PRODUCT_MODE");

                //因為要回傳掃描結果所以要使用startActivityForResult，要求回傳1
                startActivityForResult(intent, 1);

            }
        });
        equipmentbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(gym.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("outputs1", s1);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                gym.this.finish();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //requestCode在startActivityForResult傳入參數時決定的，如果成功的話會傳回相同的值
        if (requestCode == 1) {
            //成功回傳值
            if (resultCode == RESULT_OK) {
                //ZXing回傳的內容
                String contents = intent.getStringExtra("SCAN_RESULT");
                //ZXing回傳的格式(掃到的是一維條碼/QR code)
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                //將掃描的結果顯示在textview元件
                //txt1.setText(contents);
                // Handle successful scan
                Intent intent1=new Intent();
                intent1.setClass(gym.this,runmachine.class);
                startActivity(intent1);
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }


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
}
