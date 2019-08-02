package com.example.newproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class menu extends AppCompatActivity {
    private ListAdapter listAdapter;
    private ListView listView;
    final String[] foodmenu={"豆腐","義大利麵","咖哩","優格","玉米","雞胸肉","牛肉","豬肉","杏鮑菇","'小黃瓜","雞蛋","馬鈴薯","地瓜","南瓜"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodmenu);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("food",foodmenu[position]);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                menu.this.finish();
            }
        });
    }
}
