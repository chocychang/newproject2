package com.example.newproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Button list1=(Button)findViewById(R.id.list1);
        list1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intenttofu = new Intent();
                intenttofu.setClass(list.this,tofu.class);
                startActivity(intenttofu);
            }
        });
    }
}
