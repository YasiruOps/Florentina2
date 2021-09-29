package com.example.florentina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class UpdateUser extends AppCompatActivity {

    public static final String TAG = "TAG";

    TextView username, name, phone, address, email, password;

    Button confirm;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    private String userid;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);


        confirm = findViewById(R.id.submituserdata);

        username = findViewById(R.id.enterusername);
        name = findViewById(R.id.entername);
        phone =findViewById(R.id.enterphone);
        address =findViewById(R.id.enteraddress);
        email =findViewById(R.id.enteremail);
        password = findViewById(R.id.enterpassword);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        user = fAuth.getCurrentUser();

        userid = fAuth.getCurrentUser().getUid();


        DocumentReference documentReference = fStore.collection("users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                username.setText(documentSnapshot.getString("UserName"));
                name.setText(documentSnapshot.getString("Name"));
                phone.setText(documentSnapshot.getString("Phone"));
                address.setText(documentSnapshot.getString("Address"));
                email.setText(documentSnapshot.getString("Email"));
                password.setText(documentSnapshot.getString("Password"));
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = fStore.getInstance().collection("users").document(userid);
                Map<String,Object> user = new HashMap<>();
                String varemail = email.getText().toString();
                String varname = name.getText().toString();
                String varusername = username.getText().toString();
                String varphone = phone.getText().toString();
                String varaddress = address.getText().toString();
                String varpassword = password.getText().toString();
                user.put("UserName",varusername);
                user.put("Name",varname);
                user.put("Phone",varphone);
                user.put("Address",varaddress);
                user.put("Password",varpassword);
                user.put("Email",varemail);

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "onSuccess: User Updated");
                                Intent intent =new Intent(UpdateUser.this,ProfilePage.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: err ",e);
                            }
                        });
            }
        });
    }
}