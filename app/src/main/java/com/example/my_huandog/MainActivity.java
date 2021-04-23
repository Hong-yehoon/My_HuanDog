package com.example.my_huandog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    Fragment fragmentHome;
    Fragment fragmentFriends;
    Fragment fragmentMyPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentHome = new HomeFragment();
        fragmentFriends = new FriendsFragment();
        fragmentMyPage = new MyPageFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_activity_main,fragmentHome).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.navigation_home:
                        fragmentHome = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_activity_main,fragmentHome).commit();
                        return true;

                    case R.id.navigation_friends:
                        fragmentFriends = new FriendsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_activity_main,fragmentFriends).commit();
                        return true;

                    case R.id.navigation_myPage:
                        fragmentMyPage = new MyPageFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_activity_main,fragmentMyPage).commit();
                        return true;
                }

                return false;
            }
        });



    }



}