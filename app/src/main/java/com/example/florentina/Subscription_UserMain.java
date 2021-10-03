package com.example.florentina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Subscription_UserMain extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    Subscription_UserAdapter subscription_userAdapters;
    ArrayList<Subscription> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_user_main);

        recyclerView = findViewById(R.id.rv_user_subscription);
        database = FirebaseDatabase.getInstance().getReference("Subscription2");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        list =new ArrayList<>();
        subscription_userAdapters = new Subscription_UserAdapter(this,list);
        recyclerView.setAdapter(subscription_userAdapters);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Subscription subscription = dataSnapshot.getValue(Subscription.class);
                    //SETS KEY WHICH IS LATER FETCHED BY DELETE AND UPDATE
                    subscription.setSubid(dataSnapshot.getKey());
                    list.add(subscription);

                }
                subscription_userAdapters.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}