package com.example.florentina;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    private View decorView;
    ImageView buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        decorView = getWindow().getDecorView();
        buy = findViewById(R.id.buy_btn);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,productUser.class);
                startActivity(intent);
            }
        });

        ///NAVIGATION BAR
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent intent1 = new Intent(HomePage.this, HomePage.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_shop:
                        Intent intent2 = new Intent(HomePage.this, productUser.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_event:
                        Intent intent3 = new Intent(HomePage.this, Event_UserMain.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_random:
                        Intent intent4 = new Intent(HomePage.this, Subscription_UserMain.class);
                        startActivity(intent4);
                        break;
                    case R.id.ic_userprofile:
                        Intent intent5 = new Intent(HomePage.this, ProfilePage.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        decorView.setSystemUiVisibility(hideSystemBars());

    }



    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    };
}