package com.example.florentina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Subscription_Main extends AppCompatActivity {

    Button varaddsubpagebtn;

    RecyclerView recyclerView;
    DatabaseReference database;
    SubscriptionAdapter subscriptionAdapter;
    ArrayList<Subscription> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_main);

        recyclerView = findViewById(R.id.rc_subscription);
        database = FirebaseDatabase.getInstance().getReference("Subscription2");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list =new ArrayList<>();
        subscriptionAdapter = new SubscriptionAdapter(this,list);
        recyclerView.setAdapter(subscriptionAdapter);

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
                subscriptionAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        varaddsubpagebtn = findViewById(R.id.addsubscriptionpagebtn);
        varaddsubpagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Subscription_add.class));
            }
        });
    }
}