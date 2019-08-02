package com.example.newproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class diy extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    List<String> my_list;
    CustomerAdapter mCustomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy);
        searchView = (SearchView)findViewById(R.id.search);
        listView = (ListView)findViewById(R.id.list);
        searchView.setIconifiedByDefault(false);// 關閉icon切換
        searchView.setFocusable(false); // 不要進畫面就跳出輸入鍵盤
        searchView.requestFocusFromTouch();
        searchView.setQueryHint("輸入你的食材");

        String[] food={"豆腐野菜多士杯","迷迭香烤薯\n佐優格香蔥醬","香蒜辣椒蛤蜊義大利麵","氣炸咖哩南瓜包","希臘風香草優格雞胸","沙沙醬涼拌豆腐","日式奶油咖哩烏冬麵","野澤菜香腸義大利麵",
                "養生地瓜咖哩雞","鮭魚蘆筍沙拉\n佐香菜優格醬","鮪魚玉米檸檬義大利麵","鮮蝦玉子豆腐","藍莓柳橙優格甜點杯","四物咖哩雞湯","肉末清蒸豆腐","鮮蝦蘆筍義大利麵"};
        setSearch_function();
        my_list = new ArrayList<>();
        // 給List增加測試數據
        for(int i=0;i<16;i++){
            my_list.add(food[i]);
        }
        mCustomAdapter = new CustomerAdapter(this,my_list);
        listView.setAdapter(mCustomAdapter);
        // 給listview 設置過濾器
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String s=searchView.getQuery().toString();
                if (position==0){
                    Intent intent=new Intent();
                    intent.setClass(diy.this,tofu.class);
                    startActivity(intent);
                }
                if (position==2){
                    Intent intent=new Intent();
                    intent.setClass(diy.this,t1.class);
                    startActivity(intent);
                }
            }
        });
        ImageButton b1=(ImageButton)findViewById(R.id.ar);
        ImageButton b2=(ImageButton)findViewById(R.id.say);
        ImageButton b3=(ImageButton)findViewById(R.id.menu);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentar = getPackageManager().getLaunchIntentForPackage("com.ar.home");
                startActivity(intentar);
                searchView.setQuery("義大利麵", false);
                searchView.clearFocus();
                /*Thread t1 = new Thread(r1);
                try {
                    t1.sleep(4000);
                } catch (InterruptedException e) {

                }
                t1.start();*/
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentsay = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intentsay.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                //設定辨識語言(這邊設定的是繁體中文)
                intentsay.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW");
                //設定語音辨識視窗的內容
                intentsay.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...");
                startActivityForResult(intentsay, 2);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentmenu = new Intent();
                intentmenu.setClass(diy.this,menu.class);
                startActivityForResult(intentmenu,3);
            }
        });
    }
    private void setSearch_function() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    // Clear the text filter.
                    listView.clearTextFilter();
                } else {
                    // Sets the initial value for the text filter.
                    listView.setFilterText(newText);
                }
                return false;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            /*@Override
            public boolean onQueryTextChange(String newText) {
                mCustomAdapter.getFilter().filter(newText);

                return true;
            }*/
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        //用來儲存最後的辨識結果
        String firstMatch;
        if (requestCode == 2 && resultCode == RESULT_OK) {
            //取出多個辨識結果並儲存在String的ArrayList中
            final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            firstMatch = result.get(0);
            if (firstMatch.equals("豆腐")) {
                new AlertDialog.Builder(diy.this).setTitle("語音辨識結果")
                        .setIcon(android.R.drawable.ic_menu_search)
                        .setMessage(firstMatch)
                        .setPositiveButton("看食譜", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                searchView.setQuery("豆腐", false);
                                searchView.clearFocus();
                            }
                        }).show();
            }
            else if(firstMatch.equals("義大利麵"))
            {
                new AlertDialog.Builder(diy.this).setTitle("語音辨識結果")
                        .setIcon(android.R.drawable.ic_menu_search)
                        .setMessage(firstMatch)
                        .setPositiveButton("看食譜", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                searchView.setQuery("義大利麵", false);
                                searchView.clearFocus();
                            }
                        }).show();
            }
            else if(firstMatch.equals("咖哩"))
            {
                new AlertDialog.Builder(diy.this).setTitle("語音辨識結果")
                        .setIcon(android.R.drawable.ic_menu_search)
                        .setMessage(firstMatch)
                        .setPositiveButton("看食譜", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                searchView.setQuery("咖哩", false);
                                searchView.clearFocus();
                            }
                        }).show();
            }
            else if(firstMatch.equals("優格"))
            {
                new AlertDialog.Builder(diy.this).setTitle("語音辨識結果")
                        .setIcon(android.R.drawable.ic_menu_search)
                        .setMessage(firstMatch)
                        .setPositiveButton("看食譜", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                searchView.setQuery("優格", false);
                                searchView.clearFocus();
                            }
                        }).show();
            }
            else {
                firstMatch = "無法辨識";
                new AlertDialog.Builder(diy.this).setTitle("語音辨識結果")
                        .setIcon(android.R.drawable.ic_menu_search)
                        .setMessage(firstMatch)
                        .setPositiveButton("OK", null).show();
            }
        }
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == RESULT_OK){
            String s=data.getExtras().getString("food");
            searchView.setQuery(s, false);
            searchView.clearFocus();
        }

        /*//android.defaultConfig.vectorDrawables.useSupportLibrary = true;
        Button b2=(Button)findViewById(R.id.say);
        Button b3 = (Button)findViewById(R.id.list);
        *//*b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentar = new Intent();
                intentar.setClass(diy.this,ar.class);
                startActivityForResult(intentar,1);
            }
        });*//*
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentsay = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intentsay.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//設定辨識語言(這邊設定的是繁體中文)
                intentsay.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW");
//設定語音辨識視窗的內容
                intentsay.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...");
                startActivityForResult(intentsay, 2);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentfoodlist = new Intent();
                intentfoodlist.setClass(diy.this,list.class);
                startActivityForResult(intentfoodlist,3);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
//用來儲存最後的辨識結果
        String firstMatch;
        if (requestCode == 2 && resultCode == RESULT_OK) {
//取出多個辨識結果並儲存在String的ArrayList中
            final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            firstMatch = (String) result.get(0);
            if(firstMatch.equals("豆腐"))
            {
                new AlertDialog.Builder(diy.this).setTitle("語音辨識結果")
                        .setIcon(android.R.drawable.ic_menu_search)
                        .setMessage(firstMatch.toString())
                        .setPositiveButton("看食譜", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Intent intenttofu = new Intent();
                                intenttofu.setClass(diy.this,tofu.class);
                                startActivity(intenttofu);
                            }
                        }).show();
            }
            *//*else if(firstMatch.equals("義大利麵"))
            {
                new AlertDialog.Builder(diy.this).setTitle("語音辨識結果")
                        .setIcon(android.R.drawable.ic_menu_search)
                        .setMessage(firstMatch.toString())
                        .setPositiveButton("看食譜", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Intent intentnoodle = new Intent();
                                intentnoodle.setClass(diy.this,noodle.class);
                                startActivity(intentnoodle);
                            }
                        }).show();
            }
            else if(firstMatch.equals("咖哩塊"))
            {
                new AlertDialog.Builder(diy.this).setTitle("語音辨識結果")
                        .setIcon(android.R.drawable.ic_menu_search)
                        .setMessage(firstMatch.toString())
                        .setPositiveButton("看食譜", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Intent intentcurry = new Intent();
                                intentcurry.setClass(diy.this,curry.class);
                                startActivity(intentcurry);
                            }
                        }).show();
            }
            else if(firstMatch.equals("優格"))
            {
                new AlertDialog.Builder(diy.this).setTitle("語音辨識結果")
                        .setIcon(android.R.drawable.ic_menu_search)
                        .setMessage(firstMatch.toString())
                        .setPositiveButton("看食譜", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Intent intentyogurt = new Intent();
                                intentyogurt.setClass(diy.this,yogurt.class);
                                startActivity(intentyogurt);
                            }
                        }).show();
            }*//*
            else {
                firstMatch = "無法辨識";
                new AlertDialog.Builder(diy.this).setTitle("語音辨識結果")
                        .setIcon(android.R.drawable.ic_menu_search)
                        .setMessage(firstMatch.toString())
                        .setPositiveButton("OK",null).show();
            }
        }
        //setBechecked(bechecked);
        FloatingActionButton fab = findViewById(R.id.fab);
        *//*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(diy.this).setTitle("確認清單")
                        .setIcon(android.R.drawable.ic_menu_manage)
                        .setMultiChoiceItems(finalbuy, bechecked, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                // TODO Auto-generated method stub
                                bechecked[which] = isChecked;
                            }
                        }).setPositiveButton("結帳去", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s=" ";
                        for(int i=0;i< finalbuy.length;i++){
                            if(bechecked[i]==true){
                                s = s+finalbuy[i]+" ";
                            }
                        }
                        Toast.makeText(diy.this, "你已將" +s+"移除購物車了", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });*/
    }
}
