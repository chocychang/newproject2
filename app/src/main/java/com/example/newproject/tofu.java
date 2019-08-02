package com.example.newproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class tofu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tofu);

        ImageButton t1=(ImageButton)findViewById(R.id.t1);
        ImageButton t2=(ImageButton)findViewById(R.id.t2);
        ImageButton t3=(ImageButton)findViewById(R.id.t3);
        ImageButton t4=(ImageButton)findViewById(R.id.t4);
        t1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentt1 = new Intent();
                intentt1.setClass(tofu.this,t1.class);
                startActivity(intentt1);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentt2 = new Intent();
                intentt2.setClass(tofu.this,t2.class);
                startActivity(intentt2);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentt3 = new Intent();
                intentt3.setClass(tofu.this,t3.class);
                startActivity(intentt3);
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentt4 = new Intent();
                intentt4.setClass(tofu.this,t4.class);
                startActivity(intentt4);
            }
        });
    }
}
