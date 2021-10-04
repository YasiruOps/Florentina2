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

public class Products_Add extends AppCompatActivity {

    EditText varname, vardesc, varprice, varurl, varquantity;
    Button varbtn;
    Product product;

    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_add);

        varname=findViewById(R.id.productname);
        vardesc=findViewById(R.id.productdesc);
        varprice=findViewById(R.id.productprice);
        varquantity = findViewById(R.id.productquantity);

        varurl = findViewById(R.id.producturl);

        product = new Product();

        varbtn=findViewById(R.id.productaddbtn);

        db = FirebaseDatabase.getInstance().getReference().child("Products");
        varbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Float price = Float.parseFloat(varprice.getText().toString().trim());

                 product.setName(varname.getText().toString());
                 product.setDescription(vardesc.getText().toString());
                 product.setPrice(price);
                 product.setImageURL(varurl.getText().toString());
                 product.setQuantity(Integer.parseInt(varquantity.getText().toString()));

                 if(product.getName().isEmpty() || product.getDescription().isEmpty() || product.getPrice().equals(null)|| product.getImageURL().isEmpty()){
                     Toast.makeText(Products_Add.this, "please enter data in all fields", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     db.push().setValue(product);
                     Toast.makeText(Products_Add.this, "data inserted sucessfully", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(getApplicationContext(), Products_Main.class));
                 }
            }
        });
    }
}