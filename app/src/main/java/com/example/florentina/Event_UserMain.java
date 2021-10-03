package com.example.florentina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class Event_UserMain extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    Event_UserAdapter eventUserAdapter;
    ArrayList<Event> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_main);

        TextView eventcount;
        eventcount = findViewById(R.id.event_count);


        recyclerView = findViewById(R.id.rv_userevent);
        database = FirebaseDatabase.getInstance().getReference("Events2");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        eventUserAdapter = new Event_UserAdapter(this, list);
        recyclerView.setAdapter(eventUserAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Event event = dataSnapshot.getValue(Event.class);

                    //sets postion to be used in event adapter
                    event.setEventId(dataSnapshot.getKey());
                    list.add(event);
                }

                eventUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //FLOATING CALCULATOR
        FloatingActionButton calcbtn = findViewById(R.id.floating_cal);

        calcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Event_UserMain.this, EventCalculator.class));
            }
        });

    }
}