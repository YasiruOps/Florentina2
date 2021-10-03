package com.example.florentina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class productUser extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Product> list;
    ProductUserAdapter productUserAdapter;
    DatabaseReference database;
    Button varbtn;

//    private Integer counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_user);

        recyclerView = findViewById(R.id.user_rv_product);
        database = FirebaseDatabase.getInstance().getReference("Products");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        ImageView plus, minus;
//        TextView total, pcount;
//
//        plus = findViewById(R.id.plus_sign);
//        minus = findViewById(R.id.minus_sign);
//
//        total = findViewById(R.id.totalproducts);
//        pcount = findViewById(R.id.product_count);
//
//        plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                counter++;
//            }
//        });
//
//        minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                counter--;
//            }
//        });
//
//        pcount.setText(Integer.toString(counter));






        list =new ArrayList<>();
        productUserAdapter = new ProductUserAdapter(this,list);
        recyclerView.setAdapter(productUserAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);

                    //sets key to be used by update
                    product.setProductId(dataSnapshot.getKey());
                    list.add(product);
                }
                productUserAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}