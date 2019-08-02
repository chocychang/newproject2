package com.example.newproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class food extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Button diy=(Button)findViewById(R.id.diy);
        Button go=(Button)findViewById(R.id.go);
        diy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setClass(food.this,diy.class);
                startActivity(intent1);
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setClass(food.this,go.class);
                startActivity(intent1);
            }
        });
        toolbar = findViewById(R.id.head_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("側邊欄");

        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.homepage){
            Toast.makeText(this,"首頁",Toast.LENGTH_LONG).show();
        }
        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
