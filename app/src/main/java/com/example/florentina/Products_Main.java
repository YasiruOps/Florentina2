    package com.example.florentina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Products_Main extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Product> list;
    ProductAdapter productAdapter;
    DatabaseReference database;

    Button varbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_main);

        recyclerView = findViewById(R.id.rc_products);
        database = FirebaseDatabase.getInstance().getReference("Products");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list =new ArrayList<>();
        productAdapter = new ProductAdapter(this,list);
        recyclerView.setAdapter(productAdapter);

        database.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       list.clear();
                       for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                           Product product = dataSnapshot.getValue(Product.class);
                           list.add(product);
                       }
                       productAdapter.notifyDataSetChanged();
                   }


                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }

               });





        varbtn = findViewById(R.id.linktoaddproductpage);
        varbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), Products_Add.class));
            }
        });

    }
}