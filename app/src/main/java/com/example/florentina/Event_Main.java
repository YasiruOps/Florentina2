package com.example.florentina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Event_Main extends AppCompatActivity {

    Button varbtn;

    RecyclerView recyclerView;
    DatabaseReference database;
    EventAdapter eventAdapter;
    ArrayList<Event> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);

        TextView eventcount;
        eventcount = findViewById(R.id.event_count);


        recyclerView = findViewById(R.id.rc_event);
        //database cnnetion
        database = FirebaseDatabase.getInstance().getReference("Events2");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        eventAdapter = new EventAdapter(this, list);
        recyclerView.setAdapter(eventAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                int count = 0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Event event = dataSnapshot.getValue(Event.class);

                    count++;

                    //sets postion to be used in event adapter
                    event.setEventId(dataSnapshot.getKey());
                    list.add(event);
                }
                //Display number of events
                eventcount.setText(String.valueOf(count));

                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        varbtn = findViewById(R.id.addeventbtn);
        varbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Event_Add.class));
            }
        });
    }
}