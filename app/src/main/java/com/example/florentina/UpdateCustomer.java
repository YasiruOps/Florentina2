package com.example.florentina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class UpdateCustomer extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "TAG";

    EditText varusername, varname, varphone, varaddress, varemail, varpassword;
    Button updatebtn;

    Customer customer;

    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatecustomer);

        customer = (Customer) getIntent().getSerializableExtra("customer");
        fStore = FirebaseFirestore.getInstance();

        updatebtn = findViewById(R.id.cusupdatebtn);

        varusername = findViewById(R.id.enterusername);
        varname = findViewById(R.id.entername);
        varphone = findViewById(R.id.enterphone);
        varaddress = findViewById(R.id.enteraddress);
        varemail = findViewById(R.id.enteremail);
        varpassword = findViewById(R.id.enterpassword);

        varusername.setText(customer.getUserName());
        varname.setText(customer.getName());
        varphone.setText(customer.getPhone());
        varaddress.setText(customer.getAddress());
        varemail.setText(customer.getEmail());
        varpassword.setText(customer.getPassword());


        ///BUTTTON CLICK LISTNER
        updatebtn.setOnClickListener(this);

    }

    private void updateCustomer() {
        String username = varusername.getText().toString().trim();
        String name = varname.getText().toString().trim();
        String phone = varphone.getText().toString().trim();
        String address = varaddress.getText().toString().trim();
        String email = varemail.getText().toString().trim();
        String password = varpassword.getText().toString().trim();

        Customer cus = new Customer(username, name, phone, address, email, password);


        ///ID EKA AWLAK THIYEEE RETREVE
        //|||||||||||||||||||||||||||||||\\

        fStore.collection("users").document(customer.getId())
                .set(cus)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateCustomer.this, "Customer Details Updated", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cusupdatebtn:
                updateCustomer();
                break;
        }

    }
}