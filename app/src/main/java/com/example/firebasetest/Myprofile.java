 package com.example.firebasetest;

 import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.annotation.Nullable;

 public class Myprofile extends AppCompatActivity {

     private Uri imageUri;
     private static final int GALLERY_INTENT_CODE = 1023 ;
     private static final int PICK_IMAGE_REQUEST = 1;
     TextView fullName,email,phone,verifyMsg;
     FirebaseAuth fAuth;
     FirebaseFirestore fStore;
     String userId;
     Button resetPassLocal,changeProfileImage,btnEdit;
     FirebaseUser user;
     ImageView profileImage;
     StorageReference storageReference;


     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_myprofile);
         phone = findViewById(R.id.phone);
         fullName = findViewById(R.id.name);
         email    = findViewById(R.id.email);
         resetPassLocal = findViewById(R.id.resetPasswordLocal);
         btnEdit  = findViewById(R.id.butEdit);
         profileImage = findViewById(R.id.profileImage);
         changeProfileImage = findViewById(R.id.changeProfile);


         fAuth = FirebaseAuth.getInstance();
         fStore = FirebaseFirestore.getInstance();
         storageReference = FirebaseStorage.getInstance().getReference();

         StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");





         userId = fAuth.getCurrentUser().getUid();
         user = fAuth.getCurrentUser();



         DocumentReference documentReference = fStore.collection("users").document(userId);
         documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
             @Override
             public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                 if(documentSnapshot.exists()){
                     phone.setText(documentSnapshot.getString("phone"));
                     fullName.setText(documentSnapshot.getString("fName"));
                     email.setText(documentSnapshot.getString("email"));

                 }else {
                     Log.d("tag", "onEvent: Document do not exists");
                 }
             }
         });


         resetPassLocal.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 final EditText resetPassword = new EditText(v.getContext());

                 final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                 passwordResetDialog.setTitle("Reset Password ?");
                 passwordResetDialog.setMessage("Enter New Password > 6 Characters long.");
                 passwordResetDialog.setView(resetPassword);

                 passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         // extract the email and send reset link
                         String newPassword = resetPassword.getText().toString();
                         user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {
                                 Toast.makeText(Myprofile.this, "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Toast.makeText(Myprofile.this, "Password Reset Failed.", Toast.LENGTH_SHORT).show();
                             }
                         });
                     }
                 });

                 passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         // close
                     }
                 });

                 passwordResetDialog.create().show();

             }
         });

         changeProfileImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 openFileChooser();
             }


             private void openFileChooser() {
                 Intent intent = new Intent();
                 intent.setType("image/*");
                 intent.setAction(Intent.ACTION_GET_CONTENT);
                 startActivityForResult(intent, PICK_IMAGE_REQUEST);
             }


         });


         changeProfileImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 // open gallery
                 Intent i = new Intent(v.getContext(),EditProfile.class);
                 i.putExtra("fullName",fullName.getText().toString());
                 i.putExtra("email",email.getText().toString());
                 i.putExtra("phone",phone.getText().toString());
                 startActivity(i);
//
             }
         });

         btnEdit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(Myprofile.this, EditProfile.class));
             }
         });

     }

     public void logout(View view) {
         FirebaseAuth.getInstance().signOut();//logout
         startActivity(new Intent(getApplicationContext(),loginpage.class));
         finish();
     }


 }
/*
 import android.os.Bundle;
import android.widget.TextView;

 import androidx.annotation.Nullable;
 import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
 import com.google.firebase.firestore.DocumentSnapshot;
 import com.google.firebase.firestore.EventListener;
 import com.google.firebase.firestore.FirebaseFirestore;
 import com.google.firebase.firestore.FirebaseFirestoreException;

 public class Myprofile extends AppCompatActivity {
    TextView Name,Email,Phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Phone = findViewById(R.id.phone);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
       documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
           @Override
           public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

               Name.setText(documentSnapshot.getString("fName"));
               Email.setText(documentSnapshot.getString("email"));
               Phone.setText(documentSnapshot.getString("phone"));
           }
       });

    }
}

 */