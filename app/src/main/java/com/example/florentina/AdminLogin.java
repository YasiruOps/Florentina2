package com.example.florentina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class AdminLogin extends AppCompatActivity {

    EditText varemail, varpass;
    Button varloginbtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        varemail = findViewById(R.id.emailadmin);
        varpass = findViewById(R.id.passadmin);
        varloginbtn = findViewById(R.id.loginbtn);
        fAuth = FirebaseAuth.getInstance();

        //LOGIN BTN SETUP
        varloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = varemail.getText().toString();
                String password = varpass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    varemail.setError("Email Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    varpass.setError("Email Required");
                    return;
                }
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminLogin.this, "Logged in Sucessfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminLogin.this, AdminPortal.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AdminLogin.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}


