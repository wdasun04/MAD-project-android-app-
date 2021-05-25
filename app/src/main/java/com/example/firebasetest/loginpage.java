package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginpage extends AppCompatActivity {

    EditText email,Password;
    TextView reg;
    Button buttonLog;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        reg = findViewById(R.id.textViewLog);

        email = findViewById(R.id.email);
        Password = findViewById(R.id.Password);
        buttonLog = findViewById(R.id.buttonLog);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tEmail = email.getText().toString().trim();
                String tPassword = Password.getText().toString().trim();

                if(TextUtils.isEmpty(tEmail)){
                    email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(tPassword)){
                    Password.setError("Password is Required");
                    return;
                }
                if(Password.length() < 6 ){
                    Password.setError("Password must be greater than 5 digits");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //Authenticate user
                fAuth.signInWithEmailAndPassword(tEmail,tPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(loginpage.this, "You are Login successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(loginpage.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginpage.this, RegisterPage.class));
            }
        });
    }



}