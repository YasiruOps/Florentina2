package com.example.florentina;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



    ///NAVIGATION BAR
    BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.ic_home:
                    Intent intent1 = new Intent(HomePage.this, HomePage.class);
                    startActivity(intent1);
                    break;
                case R.id.ic_shop:
                    Intent intent2 = new Intent(HomePage.this, ShopPage.class);
                    startActivity(intent2);
                    break;
                case R.id.ic_event:
                    Intent intent3 = new Intent(HomePage.this, Event_Main.class);
                    startActivity(intent3);
                    break;
                case R.id.ic_userprofile:
                    Intent intent5 = new Intent(HomePage.this, ProfilePage.class);
                    startActivity(intent5);
                    break;
            }
            return false;
        }
        });
    }
}