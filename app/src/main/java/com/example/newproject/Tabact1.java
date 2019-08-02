package com.example.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Tabact1 extends Fragment {
    Button button;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View rootView = inflater.inflate(R.layout.fragment_actone, container, false);
        button=(Button)rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),bigreeny.class);
                startActivity(intent);
            }
        });
        return rootView;
        //return inflater.inflate(R.layout.fragment_one, container, false);
    }
}
