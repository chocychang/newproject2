package com.example.newproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class t4 extends AppCompatActivity {
    private String[] tobuy = new String[] {"豆腐","蘑菇","番薯苗","吐司","芝士"};
    private boolean[] bechecked=new boolean[tobuy.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t4);
        FloatingActionButton fab1 = findViewById(R.id.fab);
        FloatingActionButton fabv = findViewById(R.id.fabvideo);
        FloatingActionButton fabp=findViewById(R.id.fabplus);
        fabp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(t4.this).setTitle("購物清單")
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setMultiChoiceItems(tobuy, bechecked, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                // TODO Auto-generated method stub
                                bechecked[which] = isChecked;
                            }
                        }).setPositiveButton("加入購物車", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s=" ";
                        for(int i=0;i< tobuy.length;i++){
                            if(bechecked[i]==true){
                                s = s+tobuy[i]+" ";
                            }
                        }
                        Toast.makeText(t4.this, "你已將" +s+"加入購物車了", Toast.LENGTH_SHORT).show();
                    }
                }).show();
                /*for(int i=0;i< tobuy.length;i++){
                    if(bechecked[i]==true){
                        finalbuy[j]=tobuy[i];
                        j++;
                    }
                }*/
            }
        });
        fabv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentvt = new Intent();
                intentvt.setClass(t4.this,vt4.class);
                startActivity(intentvt);
            }
        });
    }
}
