package com.example.florentina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CustomerPage extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Customer> customerArrayList;
    CustomerAdapter customerAdapter;
    FirebaseFirestore fStore;

    Button varaddcusbtn, deletebtn;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_page);


        //for nice user experience
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data.....");
        progressDialog.show();

        recyclerView = findViewById(R.id.rc_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customerAdapter);

        fStore = FirebaseFirestore.getInstance();
        customerArrayList = new ArrayList<Customer>();
        customerAdapter = new CustomerAdapter(CustomerPage.this, customerArrayList);



        EventChangeListner();

        fStore.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:list){
                        Customer cus =d.toObject(Customer.class);
                        cus.setId(d.getId());
                    }
                    customerAdapter.notifyDataSetChanged();
                }
            }
        });


    }


    private void EventChangeListner() {
        fStore.collection("users").orderBy("Name",Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    Log.e("Firestore error", error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()) {

                    if (dc.getType() == DocumentChange.Type.ADDED) {

                        customerArrayList.add(dc.getDocument().toObject(Customer.class));
                    }

                    customerAdapter.notifyDataSetChanged();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }
        });


        ///Button to cusotmer add page
        varaddcusbtn = findViewById(R.id.linktoaddcustomerpage);
        varaddcusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerPage.this, AddCustomer.class));
            }
        });
    }
}