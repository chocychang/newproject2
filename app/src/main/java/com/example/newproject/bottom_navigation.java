package com.example.newproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class bottom_navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    FloatingActionButton chatbot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        toolbar = findViewById(R.id.head_bar);
        chatbot=(FloatingActionButton)findViewById(R.id.chatbot);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("GYM");
        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("jp.naver.line.android");
                if( intent != null )
                {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity( intent );
                }
            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment=null;
                    switch (menuItem.getItemId())
                    {
                        case R.id.nav_home:
                            selectedFragment=new HomeFragment();
                            break;
                        case R.id.nav_chat:
                            selectedFragment=new ChatFragment();
                            break;
                        case R.id.nav_inbox:
                            selectedFragment=new InboxFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment=new SearchFragment();
                            break;
                        case R.id.nav_flag:
                            selectedFragment=new FlagFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.homepage){
            Toast.makeText(this,"首頁",Toast.LENGTH_LONG).show();
        }
        else if(id==R.id.activity)
        {
            Intent intent=new Intent(bottom_navigation.this,myact.class);
            startActivity(intent);
        }
        else if(id==R.id.achievement)
        {
            Intent intent=new Intent(bottom_navigation.this,achievement.class);
            startActivity(intent);
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
