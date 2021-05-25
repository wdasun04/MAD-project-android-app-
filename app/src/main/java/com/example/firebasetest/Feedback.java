package com.example.firebasetest;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Feedback extends AppCompatActivity {

    EditText txtComment, txtSuggesion;
    Button btnAdd, btnShow,btnUpdate,btnDelete;
    DatabaseReference dbRef;
    Comment  com;

    //Method to clear all user inputs
    private void clearControls(){

        txtComment.setText("");
        txtSuggesion.setText("");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        txtComment = findViewById(R.id.Comment);
        txtSuggesion = findViewById(R.id.suggesion);


        btnAdd = findViewById(R.id.btnNext);
        btnShow = findViewById(R.id.btnShow);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);


     //   btnUpdate = findViewById(R.id.btnUpdate);
     //   btnDelete = findViewById(R.id.btnDate);

        com = new Comment();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Comment").child("Comment/com1");
                dbRef.removeValue();
                Toast.makeText(getApplicationContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference();
                dbRef.child("Comment").child("com1").child("Comment").setValue(txtComment.getText().toString().trim());
                dbRef.child("Comment/com1/suggesion").setValue(txtSuggesion.getText().toString().trim());
                Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                clearControls();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Comment/com1");
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        
                       
                        if(dataSnapshot.hasChildren()){
                            txtComment.setText(dataSnapshot.child("Comment").getValue().toString());
                            txtSuggesion.setText(dataSnapshot.child("suggesion").getValue().toString());
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "cannot find Comment", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override

                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Comment");
                try{
                    if(TextUtils.isEmpty(txtComment.getText().toString()))
                        Toast.makeText(getApplicationContext(),"please enter your comment",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtSuggesion.getText().toString()))
                        Toast.makeText(getApplicationContext(),"please enter your suggesion",Toast.LENGTH_SHORT).show();

                    else{
                        //Take inputs from the user and assigning them to this instance(std) of the Student..
                        com.setComment(txtComment.getText().toString().trim());
                        com.setSuggesion(txtSuggesion.getText().toString().trim());

                        //Insert in to the database...
                        dbRef.push().setValue(com);
                        dbRef.child("com1").setValue(com);
                        //Feedback to the user via Toast...
                        Toast.makeText(getApplicationContext(),"Data Saved Successfully",Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid Contact Number",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}

/*
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {


    EditText txtComment, txtSuggesion;
    Button btnAdd, btnCancel;
    DatabaseReference dbRef;
    Comment com;

    private void clearControls() {

        txtComment.setText("");
        txtSuggesion.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        txtComment = findViewById(R.id.Ecomment);

        btnAdd = findViewById(R.id.btnNext);
        btnCancel = findViewById(R.id.btnCancel);

        com = new Comment();

        dbRef = FirebaseDatabase.getInstance().getReference().child("Comment");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Feedback.this, MainActivity.class));
                Toast.makeText(Feedback.this, "Saved", Toast.LENGTH_SHORT).show();
            }

                try {
                    if (TextUtils.isEmpty(txtComment.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter an Comment", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty((txtSuggesion.getText().toString())))
                        Toast.makeText(getApplicationContext(), "Please enter an suggesion", Toast.LENGTH_SHORT).show();
                    else {
                        com.setComment(txtComment.getText().toString().trim());
                        com.setSuggesion(txtSuggesion.getText().toString().trim());

                        dbRef.push().setValue(com);

                        Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }

                } finally {
                    (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();


                    }

                )};







                btnCancel = findViewById(R.id.btnCancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {


                                                 @Override
                                                 public void onClick(View v) {
                                                     startActivity(new Intent(Feedback.this, MainActivity.class));
                                                     Toast.makeText(Feedback.this, "canceled", Toast.LENGTH_SHORT).show();
                                                 }
                                             }

                );




    }
}
*/
    