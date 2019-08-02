package com.example.newproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tabact2 extends Fragment {
    public SQLdattta DH=null;
    public SQLiteDatabase db;
    public ListView LV1;
    TextView textViewid;
    int sum_id;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View rootView = inflater.inflate(R.layout.fragment_acttwo, container, false);
        DH=new SQLdattta(getActivity());
        db=DH.getWritableDatabase();
        LV1=(ListView)rootView.findViewById(R.id.LV1);
        select();
        LV1.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),join.class);
                Bundle bundle=new Bundle();
                bundle.putInt("key",position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
            return rootView;
        //return inflater.inflate(R.layout.fragment_one, container, false);
    }
    private void select()
    {
        Cursor cursor=db.query("TB123",new String[]{"_id","_restitle","_name","_pay","_month","_day","_hour","_minute"},null,null,null,null,null);
        List<Map<String,Object>> items= new ArrayList<Map<String,Object>>();
        cursor.moveToFirst();
        sum_id=cursor.getCount();
        for(int i=0;i<sum_id;i++)
        {
            Map<String,Object>item=new HashMap<String,Object>();
            item.put("_id",cursor.getString(0));
            item.put("_restitle",cursor.getString(1));
            item.put("_name",cursor.getString(2));
            item.put("_pay",cursor.getString(3));
            item.put("_month",cursor.getString(4));
            item.put("_day",cursor.getString(5));
            item.put("_hour",cursor.getString(6));
            item.put("_minute",cursor.getString(7));
            items.add(item);
            cursor.moveToNext();
        }
        SimpleAdapter SA=new SimpleAdapter(getActivity(),items,R.layout.acttwo_list,new String[]{"_restitle","_name","_pay","_month","_day","_hour","_minute"},new int[]{R.id.tv_title,R.id.tv_name,R.id.tv_pay,R.id.tv_month,R.id.tv_day,R.id.tv_hour,R.id.tv_minute});
        LV1.setAdapter(SA);
    }
}
