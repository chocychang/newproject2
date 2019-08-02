package com.example.newproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class go extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);
        Button b1=(Button)findViewById(R.id.con);
        Button b2 = (Button)findViewById(R.id.rest);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentcon = getPackageManager().getLaunchIntentForPackage("com.AR.ar");
                startActivity(intentcon);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(go.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
