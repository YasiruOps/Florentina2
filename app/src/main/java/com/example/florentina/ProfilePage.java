package com.example.florentina;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

//import android.support.design.internal.BottomNavigationItemView;
//import android.support.design.internal.BottomNavigationMenuView;
//import android.support.design.widget.BottomNavigationView;

public class ProfilePage extends AppCompatActivity {

    TextView username, name, address, phone, email;
    Button varlogoutbtn, varupdatebtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private CircleImageView profilepic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        username = findViewById(R.id.displayuname);
        name = findViewById(R.id.displayname);
        address = findViewById(R.id.displayadd);
        phone = findViewById(R.id.displayphone);
        email = findViewById(R.id.displayemail);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        varlogoutbtn = findViewById(R.id.logoutbtn);
        varupdatebtn = findViewById(R.id.updatebtn);

        profilepic = findViewById(R.id.profilepic);

        StorageReference profileRef = storageReference.child("creatorpics/"+userId+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilepic);
            }
        });


        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                username.setText(documentSnapshot.getString("UserName"));
                name.setText(documentSnapshot.getString("Name"));
                phone.setText(documentSnapshot.getString("Phone"));
                address.setText(documentSnapshot.getString("Address"));
                email.setText(documentSnapshot.getString("Email"));
            }
        });

        //Log out button setup
        varlogoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
                finish();
            }
        });

        //Update Page link
        varupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UpdateUser.class));
            }
        });

        ////////////////////////////////PROFILE PIC ADD//////////////////////////////////////////////
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startCropActivity();
                choosePicture();
            }
        });


    ///NAVIGATION BAR
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent intent1 = new Intent(ProfilePage.this, HomePage.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_shop:
                        Intent intent2 = new Intent(ProfilePage.this, ShopPage.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_event:
                        Intent intent3 = new Intent(ProfilePage.this, Event_Main.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_userprofile:
                        Intent intent5 = new Intent(ProfilePage.this, ProfilePage.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });
    }

    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            //creatorPic.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture(){
        final ProgressDialog pd = new ProgressDialog(ProfilePage.this);
        pd.setTitle("Uploading image.....");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference creatorpropicRef = storageReference.child("creatorpics/" + userId + "/profile.jpg");

        creatorpropicRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded. ", Snackbar.LENGTH_LONG).show();
                        creatorpropicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(profilepic);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed Upload", Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Percentage: " + (int) progressPercent + "%");
            }
        });

//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                        double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                        pd.setMessage("Percentage: " + (int) progressPercent + "%");
//                    }
//                });
    }
}