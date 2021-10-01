package com.example.florentina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminPortal extends AppCompatActivity {

    Button cuspagebtn, subscriptionbtn, eventbtn, packagebtn;
    TextView varlogoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminportal);

        varlogoutbtn = findViewById(R.id.logoutadminportal);
        cuspagebtn = findViewById(R.id.customerpagelink);
        subscriptionbtn = findViewById(R.id.subscriptionbtn);
        eventbtn = findViewById(R.id.eventbtn);
        packagebtn = findViewById(R.id.packagebtn);


        cuspagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerPage.class));
            }
        });
        subscriptionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Subscription_Main.class));
            }
        });
        eventbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Event_Main.class));
            }
        });
        packagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(), Products_Main.class));
            }
        });


        varlogoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
                finish();
            }
        });
    }
}