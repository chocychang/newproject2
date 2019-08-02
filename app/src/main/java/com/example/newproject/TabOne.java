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

public class TabOne extends Fragment {
    String s2;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_one, container, false);
        Button abdomenbutton = (Button) rootView.findViewById(R.id.backtwobutton);
        FloatingActionButton chat=(FloatingActionButton)rootView.findViewById(R.id.chatbutton);
        abdomenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("outputs2", s2);
                intent.putExtras(bundle);
                getActivity().setResult(getActivity().RESULT_OK,intent);
                getActivity().finish();
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),chat.class);
                getActivity().startActivity(intent);
            }
        });
        return rootView;
        //return inflater.inflate(R.layout.fragment_one, container, false);
    }
}
