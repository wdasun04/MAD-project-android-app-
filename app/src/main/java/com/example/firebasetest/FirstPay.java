//IT19151984
//M.P.N.D.Marasingha
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

public class FirstPay extends AppCompatActivity {

    EditText txtPNumber, txtPName, txtPdata, txtPCode;
    Button btnsave, btnshow, btnUpdate, btnDelete;
    DatabaseReference dbRef;
    Payment pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_pay);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Payment");


        txtPNumber = findViewById(R.id.pNumber);
        txtPName = findViewById(R.id.pName);
        txtPdata = findViewById(R.id.pTxtData);
        txtPCode = findViewById(R.id.pTxtSecurity);

        btnsave = findViewById(R.id.btnSave);
        btnshow = findViewById(R.id.btnShow);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnCancel);

        pay = new Payment();

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbRef = FirebaseDatabase.getInstance().getReference().child("Payment/");
                    dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           if(dataSnapshot.hasChild("pay1")){
                               dbRef = FirebaseDatabase.getInstance().getReference().child("Payment/pay1");
                               dbRef.removeValue();

                               Toast.makeText(getApplicationContext(), "Delete Data Successfully", Toast.LENGTH_SHORT).show();
                           }
                           Toast.makeText(getApplicationContext(), "Not data Delete", Toast.LENGTH_SHORT ).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Payment");
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("pay1")){
                            try {
                                pay.setNumber(txtPNumber.getText().toString().trim());
                                pay.setName(txtPName.getText().toString().trim());
                                pay.setDate(txtPdata.getText().toString().trim());
                                pay.setSecurity(Integer.parseInt(txtPCode.getText().toString().trim()));
                                dbRef = FirebaseDatabase.getInstance().getReference().child("Payment/pay1");
                                dbRef.setValue(pay);
                                clearControls();

                                //Feedback to the user vi a Toast...
                                Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                            catch (NumberFormatException e ){
                                Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Not Update", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Payment/pay1");
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()) {
                            txtPNumber.setText(dataSnapshot.child("number").getValue().toString());
                            txtPName.setText(dataSnapshot.child("name").getValue().toString());
                            txtPdata.setText(dataSnapshot.child("date").getValue().toString());
                            txtPCode.setText(dataSnapshot.child("security").getValue().toString());
                        }
                        else
                            Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                        }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        //btnsave.setOnClickListener(new View.OnClickListener() {
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Payment");

                try {
            if (TextUtils.isEmpty(txtPNumber.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter a date", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(txtPName.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter a date", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(txtPdata.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter a date", Toast.LENGTH_SHORT).show();

            else if (TextUtils.isEmpty(txtPCode.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter a Security Code", Toast.LENGTH_SHORT).show();

            else {
                //Take inputs form the user and assigning them to this instance (pay) of the Payment...
               pay.setNumber(txtPNumber.getText().toString().trim());
               pay.setName(txtPName.getText().toString().trim());
                pay.setDate(txtPdata.getText().toString().trim());
                pay.setSecurity(Integer.parseInt(txtPCode.getText().toString().trim()));
                //Insert in to the databse...
                dbRef.child("pay1").setValue(pay);
                //Feedback to the user via a Toast...
                Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                clearControls();

            }
        } catch (NumberFormatException e) {

            Toast.makeText(getApplicationContext(), "Invalid Security Code", Toast.LENGTH_SHORT).show();

        }

    }


    });
}
    private void clearControls() {
        txtPName.setText("");
        txtPNumber.setText("");
        txtPdata.setText("");
        txtPCode.setText("");
    } }