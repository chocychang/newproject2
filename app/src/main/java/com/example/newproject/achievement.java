package com.example.newproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class achievement extends AppCompatActivity {
    TextView timer;
    Long minute,second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        Bundle bundle = getIntent().getExtras();
        String min=bundle.getString("inputs1");
        String sec=bundle.getString("inputs2");
        Long min1=Long.parseLong(min);
        Long sec1=Long.parseLong(sec);
        minute=minute+min1;
        second=second+sec1;
        timer=(TextView)findViewById(R.id.timer);
        if (minute<10&&second<10)
            timer.setText("0"+min+":0"+sec);
        else if (minute<10&&second>10)
            timer.setText("0"+min+":"+sec);
        else if (minute>10&&second<10)
            timer.setText(min+":0"+sec);
        else
            timer.setText(min+":"+sec);
    }
}
