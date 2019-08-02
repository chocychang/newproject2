package com.example.newproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.newproject.ui.twoi.TwoiFragment;

public class twoi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twoi_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TwoiFragment.newInstance())
                    .commitNow();
        }
    }
}
