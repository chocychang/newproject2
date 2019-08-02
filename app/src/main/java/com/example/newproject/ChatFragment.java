package com.example.newproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import nctu.fintech.appmate.Table;
import nctu.fintech.appmate.Tuple;

public class ChatFragment extends Fragment {
    Button b1;
    EditText et1;
    TextView tv1;
    Table mTable;
    String word2send;
    String s1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        b1 = (Button)rootView.findViewById(R.id.chatbutton);
        et1 = (EditText)rootView.findViewById(R.id.editText);
        tv1 = (TextView)rootView.findViewById(R.id.textView);
        mTable = new Table("http://140.113.73.128:8000/api", "chat", "panda", "apclab0939825885");

        Timer timer01 = new Timer();
        timer01.schedule(task, 0, 3000);

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                word2send = et1.getText().toString();
                et1.setText("");
                Thread t1 = new Thread(r1);
                t1.start();
            }
        });
        return rootView;
    }
    private Runnable r1 = new Runnable(){
        public void run()
        {
            Tuple tuple_add = new Tuple();
            String id="Shawn Mendes";
            tuple_add.put("message", word2send);
            tuple_add.put("UserId", id);
            try {
                mTable.add(tuple_add);
            }catch (IOException e) {
                Log.e("Error", "Fail to put");
            }
        }
    };
    private TimerTask task = new TimerTask(){
        public void run() {
            Thread t2 = new Thread(r2);
            t2.start();
        }
    };
    private Runnable r2 = new Runnable(){
        public void run()
        {
            try {
                Tuple tuple_get[] = mTable.get();
                s1 = "";
                for(int i=0; i<tuple_get.length; i++){
                    String tempString = tuple_get[i].get("message");
                    String name = tuple_get[i].get("UserId");
                    if(tempString!=null && !tempString.equals("")) {
                        s1 = name+":"+tempString  + "\n" + s1;
                    }
                }

                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }catch (IOException e){
                Log.e("Net", "Fail to get");
            }
        }
    };
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    tv1.setText(s1);
                    break;
            }
        }
    };
}
