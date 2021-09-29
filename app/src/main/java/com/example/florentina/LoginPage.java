package com.example.florentina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    TextView varsignuplink, varadminlogin;
    EditText varemail, varpass;
    Button varloginbtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);


         varemail = findViewById(R.id.enteremail);
         varpass = findViewById(R.id.enterpassword);
         varloginbtn = findViewById(R.id.login);
         fAuth = FirebaseAuth.getInstance();

         //LOGIN BTN SETUP
         varloginbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 String email =varemail.getText().toString();
                 String password = varpass.getText().toString();

                 if(TextUtils.isEmpty(email)){
                     varemail.setError("Email Required");
                     return;
                 }
                 if(TextUtils.isEmpty(password)){
                     varpass.setError("Email Required");
                     return;
                 }
                     fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(Task<AuthResult> task) {
                             if(task.isSuccessful()){
                                 Toast.makeText(LoginPage.this, "Logged in Sucessfully", Toast.LENGTH_SHORT).show();
                                 startActivity(new Intent(getApplicationContext(), ProfilePage.class));
                             } else{
                                 Toast.makeText(LoginPage.this, "Error" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                     });

             }
         });


        //SIGN UP PAGE LINK

        varsignuplink =(TextView) findViewById(R.id.signuplink);
        varsignuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupPage.class));
            }
        });

        //ADMIN PAGE LINK
        varadminlogin =(TextView) findViewById(R.id.adminlogin);
        varadminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
            }
        });
    }
}