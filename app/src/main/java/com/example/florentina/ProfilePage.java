package com.example.florentina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

//import android.support.design.internal.BottomNavigationItemView;
//import android.support.design.internal.BottomNavigationMenuView;
//import android.support.design.widget.BottomNavigationView;

public class ProfilePage extends AppCompatActivity {

    TextView username, name, address, phone, email;
    Button varlogoutbtn, varupdatebtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);

        username = findViewById(R.id.displayuname);
        name = findViewById(R.id.displayname);
        address = findViewById(R.id.displayadd);
        phone = findViewById(R.id.displayphone);
        email = findViewById(R.id.displayemail);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        varlogoutbtn = findViewById(R.id.logoutbtn);
        varupdatebtn = findViewById(R.id.updatebtn);


        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                username.setText(documentSnapshot.getString("UserName"));
                name.setText(documentSnapshot.getString("Name"));
                phone.setText(documentSnapshot.getString("Phone"));
                address.setText(documentSnapshot.getString("Address"));
                email.setText(documentSnapshot.getString("Email"));
            }
        });

        //Log out button setup
        varlogoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
                finish();
            }
        });

        //Update Page link
        varupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UpdateUser.class));
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
                        Intent intent1 = new Intent(ProfilePage.this, HomePage.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_shop:
                        Intent intent2 = new Intent(ProfilePage.this, ShopPage.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_event:
                        Intent intent3 = new Intent(ProfilePage.this, EventPage.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_userprofile:
                        Intent intent5 = new Intent(ProfilePage.this, ProfilePage.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });
    }
}