package com.example.florentina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Event_Add extends AppCompatActivity {

    EditText varname, vardesc, varsPrice, varexpDate, varurl;
    Button varsubmitevent;
    Event event;

    DatabaseReference fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        varname = findViewById(R.id.event_name);
        vardesc = findViewById(R.id.event_name);
        varsPrice = findViewById(R.id.event_sprice);
        varexpDate = findViewById(R.id.event_expdate);
        varurl = findViewById(R.id.event_url);

        varsubmitevent = findViewById(R.id.addbtn);

        event = new Event();

        //database connetion
        fStore = FirebaseDatabase.getInstance().getReference().child("Events2");

        varsubmitevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float price = Float.parseFloat(varsPrice.getText().toString().trim());
                event.setName(varname.getText().toString());
                event.setDesc(vardesc.getText().toString());
                event.setPrice(price);
                event.setExpdate(varexpDate.getText().toString());
                event.setImgurl(varurl.getText().toString());

                //send data to database
                fStore.push().setValue(event);

                Toast.makeText(Event_Add.this, "Data inserted sucessfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),
                        Event_Main.class));
            }
        });
    }
}