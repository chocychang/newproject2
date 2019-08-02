package com.example.newproject;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlagFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    public SQLdataaaa DH=null;
    public SQLiteDatabase db;
    public ListView LV1;
    Button b1,payy;
    EditText t2;
    int sum_id;
    String wow;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_flag, container, false);

        DH=new SQLdataaaa(getActivity());
        db=DH.getWritableDatabase();
        LV1=(ListView)rootView.findViewById(R.id.LV1);
        select();
        payy=(Button)rootView.findViewById(R.id.payy);
        payy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete("TB2019", "1", null);
                new AlertDialog.Builder(getActivity())
                        .setMessage("您已成功結帳!!")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setPositiveButton("我知道了", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        });
        Spinner spinner3 = (Spinner)rootView.findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> spi = ArrayAdapter.createFromResource(
                getActivity(), R.array.spinner3, R.layout.support_simple_spinner_dropdown_item);
        spi.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner3.setAdapter(spi);
        spinner3.setOnItemSelectedListener(this);
        return rootView;
    }
    private void select()
    {
        Cursor cursor=db.query("TB2019",new String[]{"_id","_pay"},null,null,null,null,null);
        List<Map<String,Object>> items= new ArrayList<Map<String,Object>>();
        cursor.moveToFirst();
        sum_id=cursor.getCount();
        for(int i=0;i<sum_id;i++)
        {
            Map<String,Object>item=new HashMap<String,Object>();
            item.put("_id",cursor.getString(0));
            item.put("_pay",cursor.getString(1));
            items.add(item);
            cursor.moveToNext();
        }
        SimpleAdapter SA=new SimpleAdapter(getActivity(),items,R.layout.sdata2,new String[]{"_id","_pay"},new int[]{R.id.textView,R.id.textView2});
        LV1.setAdapter(SA);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        wow=parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
