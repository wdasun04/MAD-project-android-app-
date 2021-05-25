package com.example.firebasetest;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AdminUpload extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button selectImage;
    private Button button3;
    private Button button2;
    private EditText itemCode,itemName,price,sizes,colours,description;
    private ImageView imageView;
    private ProgressBar progressBar2;
    private Uri imageUri;

    private StorageReference storageRef;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_upload);

        selectImage = findViewById(R.id.selectImage);
        button3 = findViewById(R.id.button3);
        button2 = findViewById(R.id.button2);
        itemCode = findViewById(R.id.itemCode);
        itemName = findViewById(R.id.itemName);
        price = findViewById(R.id.price);
        sizes = findViewById(R.id.sizes);
        colours = findViewById(R.id.colours);
        description = findViewById(R.id.description);
        imageView = findViewById(R.id.imageView);
        progressBar2 = findViewById(R.id.progressBar2);

        storageRef = FirebaseStorage.getInstance().getReference("uploads");
        databaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        //upload button
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
        //view
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
        private void openFileChooser(){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();

            Picasso.with(this).load(imageUri).into(imageView);

        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void uploadFile(){
        if(imageUri != null){
            StorageReference fileReference = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri) );

                fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            }
                        },5000);
                        Toast.makeText(AdminUpload.this, "Upload Sucessfull", Toast.LENGTH_SHORT).show();

                        Upload upload = new Upload(itemCode.getText().toString().trim(),itemName.getText().toString().trim(),price.getText().toString().trim(),sizes.getText().toString().trim(),colours.getText().toString().trim(),
                                description.getText().toString().trim(), taskSnapshot.getMetadata().getReference().getDownloadUrl().toString() );
                        String uploadId = databaseRef.push().getKey();
                        databaseRef.child(uploadId).setValue(upload);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminUpload.this, e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        FileDownloadTask.TaskSnapshot taskSnapshot = null;
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressBar2.setProgress((int) progress);

                    }
                });
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }else
            {
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
            }
    }
}

/*
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.MalformedInputException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminUpload extends AppCompatActivity {
    private StorageReference mStorageRef;

    Button btnPickImage, btnUpload;
    ImageView imgSource, imgDestination;
    LinearLayout lv;
    Uri selectedImage;
   // ProgressBar pbbar;
    DatabaseReference databaseReference, childReference;
    private static final int REQUEST_TAKE_GALLERY_PHOTO = 2;
    StorageReference fireRef;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String imageURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

      //  pbbar = findViewById(R.id.pbbar);
      //  pbbar.setVisibility(View.GONE);

        lv = findViewById(R.id.lv);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnPickImage = findViewById(R.id.btnPickImage);
        btnUpload = findViewById(R.id.btnUpload);

        imgSource = findViewById(R.id.imgSource);
        imgDestination = findViewById(R.id.img);
        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)
                        && !Environment.getExternalStorageState().equals(
                        Environment.MEDIA_CHECKING)) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_TAKE_GALLERY_PHOTO);

                } else
                    Toast.makeText(MainActivity.this, "No gallery found.", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImages();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_PHOTO) {
                Bitmap originBitmap = null;
                selectedImage = data.getData();
                InputStream imageStream;
                try {
                    pbbar.setVisibility(View.VISIBLE);
                    imageStream = getContentResolver().openInputStream(
                            selectedImage);
                    originBitmap = BitmapFactory.decodeStream(imageStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (originBitmap != null) {
                    {
                        this.imgSource.setImageBitmap(originBitmap);
                        pbbar.setVisibility(View.GONE);
                        imgSource.setVisibility(View.VISIBLE);
                    }
                } else
                    selectedImage = null;
            }

        }
    }

    public String GetDate() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentdate = df.format(Calendar.getInstance().getTime());
        return currentdate;
    }

    public void UploadImages() {
        try {
           // pbbar.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);

            String strFileName = GetDate() + "img.jpg";

            Uri file = selectedImage;

            fireRef = mStorageRef.child("images/" + currentUser.getUid().toString() + "/" + strFileName);

            UploadTask uploadTask = fireRef.putFile(file);
            Log.e("Fire Path", fireRef.toString());
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fireRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Log.e("Image URL", downloadUri.toString());

                        pbbar.setVisibility(View.GONE);
                        selectedImage = null;
                        imageURL = downloadUri.toString();
                    } else {
                        Toast.makeText(AdminUpload.this, "Image upload unsuccessful. Please try again."
                                , Toast.LENGTH_LONG).show();
                    }
                    pbbar.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);

                    DownloadImageFromURL downloadImageFromURL = new DownloadImageFromURL();
                    downloadImageFromURL.execute("");
                }
            });
        } catch (Exception ex) {
            Toast.makeText(AdminUpload.this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private class DownloadImageFromURL extends AsyncTask<String, Void, String> {
        Bitmap bitmap = null;

        @Override
        protected void onPreExecute() {

        }

        protected String doInBackground(String... urls) {
            try {
                Log.e("imageURL is ", imageURL);
                InputStream in = new java.net.URL(imageURL).openStream();
                if (in != null) {
                    bitmap = BitmapFactory.decodeStream(in);
                } else
                    Log.e("Empty InputStream", "InputStream is empty.");
            } catch (MalformedInputException e) {
                Log.e("Error URL", e.getMessage().toString());
            } catch (Exception ex) {
                Log.e("Input stream error", "Input stream error");
            }
            return "";
        }

        protected void onPostExecute(String result) {
            if (bitmap != null) {
                imgDestination.setImageBitmap(bitmap);
            } else
                Log.e("Empty Bitmap", "Bitmap is empty.");
        }
    }

}
*/