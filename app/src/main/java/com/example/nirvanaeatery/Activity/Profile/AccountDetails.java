package com.example.nirvanaeatery.Activity.Profile;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nirvanaeatery.Activity.Account;
import com.example.nirvanaeatery.Domain.UserDomain;
import com.example.nirvanaeatery.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class AccountDetails extends AppCompatActivity {

    //Declaring Variable
    SharedPreferences sharedPreferences;
    private static final int GALLERY_CODE = 1;
    ImageView ProfilePic, Back;
    TextView firstName;
    TextView lastName;
    TextView mail;
    TextView PhoneNumber;
    String PhoneNo;
    Button update;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_account_details);

//Instantiate the variables
        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        mail = findViewById(R.id.mail);
        Back = findViewById(R.id.backbtn);
        update = findViewById(R.id.Update);
        PhoneNumber = findViewById(R.id.PhoneNo);
        ProfilePic = findViewById(R.id.profilepic);
        progressBar = findViewById(R.id.progressBar);

//Get intentPutExtra from previous page and set it as the Phone Number
        PhoneNo = getIntent().getStringExtra("PhoneNumber");
        PhoneNumber.setText(PhoneNo);



//Calling the method getAccountDetails to be operational
        getAccountDetails();


//Setting OnClick Listeners for the buttons

        ProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProfilePicUpdate();
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountDetails.this, Account.class);
                intent.putExtra("PhoneNo", PhoneNo);
                startActivity(intent);
            }
        });


    }

    //Creating Method for getting account details from the Firebase Real-time
    private void getAccountDetails() {

        //Instantiate the Firebase Class that was declared as a variable
        database = FirebaseDatabase.getInstance();

        //Getting Reference of the Table User that exist in the database
        databaseReference = database.getReference("Users");

        //Add value event listener is used to add or update things on the firebase database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Creating a for loop to the database over the children of the Parent Table
                for (DataSnapshot Ds : dataSnapshot.getChildren()) {
                    //If statement to retrieve data from the the table if the phone number matches the one in the table
                    if (Ds.child("phoneNumber").getValue().equals(PhoneNo)) {
                        firstName.setText(Ds.child("firstName").getValue(String.class));
                        lastName.setText(Ds.child("lastName").getValue(String.class));
                        mail.setText(Ds.child("email").getValue(String.class));

                        //Converting image Url String to be understood by Java as an Image by using Picasso
                        final String imageUrl = Ds.child("imageUrl").getValue(String.class);
                        if (Ds.child("phoneNumber").getValue().equals(PhoneNo)
                                && imageUrl != null && !imageUrl.isEmpty()) {
                            Picasso.get().load(imageUrl).into(ProfilePic);
                            progressBar.setVisibility(View.GONE);

                        }


                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }

        });
    }


    //Method to pick image from gallery
    public void pickImage() {

//Intent Profile pic to be picked from phone storage
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, GALLERY_CODE);


    }

    //Converting the URi for the image in the phone to appear in the app after selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();//ImageUri is retrieved
                ProfilePic.setImageURI(imageUri);//ImageUri is set on the app

                Toast.makeText(this, "Profile Set", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ProfilePicUpdate() {

//Storing it in Firebase Storage, this will be the filepath

        final String FirstName = firstName.getText().toString().trim();
        final String LastName = lastName.getText().toString().trim();
        final String Email = mail.getText().toString().trim();
        final String Number = PhoneNumber.getText().toString().trim();


        String Name = FirstName + " " + LastName;

        final StorageReference filepath = FirebaseStorage.getInstance().getReference("Profiles")
                .child(Name + ": " + Timestamp.now().getSeconds());


        progressBar.setVisibility(View.VISIBLE);


//If Statement - If Account Details is not empty then Profile Pic should be Stored

        if (!TextUtils.isEmpty(FirstName) &&
                !TextUtils.isEmpty(LastName) && !TextUtils.isEmpty(Email) &&
                !TextUtils.isEmpty(Number)
                && imageUri != null) {

            Toast.makeText(AccountDetails.this, "Profile Updating...", Toast.LENGTH_SHORT).show();


            //Storing Profile Pic URL from FirebaseStorage into a Table in FirebaseDatabase (USER)
            filepath.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //Storing Profile Pic URL from FirebaseStorage into a Table in FirebaseDatabase (USER)
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    //Setting ImageURi from Storage into an empty variable

                                    String imageUrl = uri.toString();

                                    if (imageUrl != null) {

                                        //Using UserDomain class to create different objects in the Database

                                        UserDomain Profile = new UserDomain();
                                        Profile.setFirstName(FirstName);
                                        Profile.setLastName(LastName);
                                        Profile.setImageUrl(imageUrl);
                                        Profile.setPhoneNumber(Number);
                                        Profile.setEmail(Email);

                                        //When User Details are stored take us back to Profile Activity

                                        Intent intent = new Intent(AccountDetails.this, Account.class);
                                        intent.putExtra("PhoneNo", PhoneNo);
                                        Toast.makeText(AccountDetails.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);

                                        databaseReference.child(Name + " - " + Number).setValue(Profile)
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d(TAG, "onFailure: " + e.getMessage());

                                                    }
                                                });
                                    }

                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    });


        } else {

            ProfileUpdateDB();
        }


        }


    private void ProfileUpdateDB() {

        progressBar.setVisibility(View.VISIBLE);

        //Instantiate used variables

        final String FirstName = firstName.getText().toString().trim();
        final String LastName = lastName.getText().toString().trim();
        final String Email = mail.getText().toString().trim();
        final String Number = PhoneNo;
        final String[] imageUrl = {""};


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String ImageUrl = snapshot.child("imageUrl").getValue(String.class);

                for (DataSnapshot DS : snapshot.getChildren()) {
                    if (DS.child("phoneNumber").getValue().equals(PhoneNo)){

                        imageUrl[0] = DS.child("imageUrl").getValue(String.class);
                            //Using UserDomain class to create different objects in the Database

                            UserDomain Profile = new UserDomain();
                            Profile.setFirstName(FirstName);
                            Profile.setLastName(LastName);
                            Profile.setImageUrl(imageUrl[0]);
                            Profile.setPhoneNumber(Number);
                            Profile.setEmail(Email);

                            //When User Details are stored take us back to Profile Activity

                            Intent intent = new Intent(AccountDetails.this, Account.class);
                            intent.putExtra("PhoneNo", PhoneNo);
                            Toast.makeText(AccountDetails.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                            databaseReference.child(Number).setValue(Profile)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: " + e.getMessage());

                                        }
                                    });

                        }


                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        progressBar.setVisibility(View.INVISIBLE);

    }



    }


