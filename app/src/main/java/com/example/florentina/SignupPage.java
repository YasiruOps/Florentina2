package com.example.florentina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignupPage extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button loginlinkbutton, signinbutton;
    EditText enterusername, entername, enteraddress, entermail, enterpassword, enterphone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppage);



        enterusername = findViewById(R.id.enterusername);
        entername = findViewById(R.id.entername);
        enterphone = findViewById(R.id.enterphone);
        enteraddress = findViewById(R.id.enteraddress);
        entermail = findViewById(R.id.enteremail);
        enterpassword = findViewById(R.id.enterpassword);

        signinbutton = findViewById(R.id.signinbutton) ;
        loginlinkbutton =(Button) findViewById(R.id.loginlinkbtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

//        //Check if user already loggedd in or signed up
//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), LoginPage.class));
//            finish();
//        }

        //SIGN IN
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String username = enterusername.getText().toString();
                String name = entername.getText().toString();
                String phone = enterphone.getText().toString();
                String address = enteraddress.getText().toString();
                String email = entermail.getText().toString();
                String password = enterpassword.getText().toString();

                if(username.isEmpty() || name.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignupPage.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }else if(!(username.isEmpty() || name.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty())){

                            //FIRE BASE REGISTRATION

                            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignupPage.this, "User Registerd", Toast.LENGTH_SHORT).show();

                                        //Adding user data to firebase
                                        userID = fAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = fStore.collection("users").document(userID);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("UserName", username);
                                        user.put("Name",name);
                                        user.put("Phone",phone);
                                        user.put("Address", address);
                                        user.put("Email", email);
                                        user.put("Password",password);

                                        documentReference.set(user).addOnSuccessListener((OnSuccessListener) (aVoid) ->{
                                                Log.d(TAG, "onSuccess: user Profile was created for "+ userID);
                                        }).addOnFailureListener (new OnFailureListener(){
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure "+ e.toString());
                                            }
                                        });
                                        startActivity(new Intent(getApplicationContext(), ProfilePage.class));
                                    } else{
                                        Toast.makeText(SignupPage.this, "Error" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });



    //LOGIN PAGE LINKING
        loginlinkbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            openloginpage();
              }
        });
    }
    public void openloginpage(){
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
}