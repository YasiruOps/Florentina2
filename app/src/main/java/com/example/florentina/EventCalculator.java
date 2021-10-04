package com.example.florentina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class
EventCalculator extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner mySpinner;
    private String valueFromSpinner;
    private Integer sum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button calcbtn;
        EditText flower_road, float_lamp, car_deco, bouqeue;
        TextView totale;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calculator);

        totale = findViewById(R.id.display_total);
        calcbtn = findViewById(R.id.calculatebtn);
        flower_road = findViewById(R.id.flower_road);
        float_lamp = findViewById(R.id.float_lamp);
        car_deco = findViewById(R.id.cardeco);
        bouqeue = findViewById(R.id.bouque);

        String flower = flower_road.getText().toString();
        String lamp = float_lamp.getText().toString();
        String carDeco = car_deco.getText().toString();
        String varbouque = bouqeue.getText().toString();

        mySpinner = findViewById(R.id.spinner1);

        String[] eventTypes = getResources().getStringArray(R.array.event_names);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, eventTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);

        calcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flower=="yes"){
                    sum+=10000;
                }
                if(lamp=="yes"){
                    sum+=10000;
                }
                if(carDeco== "yes"){
                    sum+=10000;
                }
                if(varbouque=="yes"){
                    sum+=10000;
                }

                Toast.makeText(EventCalculator.this, "sum ="+sum, Toast.LENGTH_SHORT).show();
                totale.setText(Integer.toString(sum));
            }
        });


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.spinner1) {
            valueFromSpinner = parent.getItemAtPosition(position).toString();
            if(valueFromSpinner == "Wedding"){
                sum = 150000;
            }
            else if(valueFromSpinner=="Birthdays"){
                sum= 80000;
            }
            else if(valueFromSpinner == "Baby Shower"){
                sum= 50000;
            }
            else if(valueFromSpinner == "Gender Reveal"){
                sum= 25000;
            }
            else if(valueFromSpinner == "Bridal Showers"){
                sum= 60000;
            }
            else if(valueFromSpinner == "Baby Shower"){
                sum= 70000;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}