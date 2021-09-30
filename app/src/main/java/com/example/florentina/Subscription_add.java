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


public class Subscription_add extends AppCompatActivity {

    EditText txtname, txtdescription, txtprice, txturl;
    Button submitbtn;
    DatabaseReference db;
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_add);

        txtname = findViewById(R.id.subscriptionname);
        txtdescription =findViewById(R.id.subscriptiondescription);
        txtprice= findViewById(R.id.subscriptionprice);
        txturl = findViewById(R.id.subscriptionpictureurl);
        submitbtn = findViewById(R.id.addsubscriptionbtn);

        subscription = new Subscription();
        db = FirebaseDatabase.getInstance().getReference().child("Subscription2");

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtname.getText().toString().trim();
                String description = txtdescription.getText().toString().trim();
                String url = txturl.getText().toString().trim();
                Float price = Float.parseFloat(txtprice.getText().toString().trim());

                subscription.setName(name);
                subscription.setDesription(description);
                subscription.setPrice(price);
                subscription.setImageURL(url);

                db.push().setValue(subscription);
                Toast.makeText(Subscription_add.this, "data inserted sucessfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),
                        Subscription_Main.class));
            }
        });
    }
}