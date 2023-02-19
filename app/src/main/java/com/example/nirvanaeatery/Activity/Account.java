package com.example.nirvanaeatery.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.nirvanaeatery.Activity.Profile.AccountDetails;
import com.example.nirvanaeatery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Account extends AppCompatActivity {
    private static final String PHONE_ID = "phoneno_prefs";

    CardView Profile, Wallet, Orders, Logout;
    TextView ProfileName, ProfileName2;
    ImageView ProfilePicture;
    ProgressBar progressBar;

    SharedPreferences sharedPreferences;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String Id;
    private String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sharedPreferences = getSharedPreferences(PHONE_ID, MODE_PRIVATE);

        progressBar = findViewById(R.id.progressBar2);
        Profile = findViewById(R.id.ProfilePage);
        Wallet = findViewById(R.id.Wallet);
        Orders = findViewById(R.id.OrderPage);
        Logout = findViewById(R.id.logout);
        ProfileName = findViewById(R.id.ProfileName);
        ProfileName2 = findViewById(R.id.profileName2);
        ProfilePicture = findViewById(R.id.ProfilePicture);

        getIncomingIntent();
        bottomNavigationView();
        getAccountDetails();
        UiResponse();
    }

    private void getIncomingIntent(){

        Id = getIntent().getStringExtra("PhoneNo");
//Get Phone Number from Shared Preferences only our application will have access to this shared preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(PHONE_ID, MODE_PRIVATE);
        Id = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");

    }

    private void getAccountDetails(){

//Retrieving data from the Profile Database to set them as Account Details

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("User");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Ds : dataSnapshot.getChildren()) {

                    phoneNumber = Ds.child("phoneNumber").getValue((String.class));

                    if (Ds.child("phoneNumber").getValue().equals(Id)) {
                        ProfileName.setText(Ds.child("firstName").getValue(String.class));
                        ProfileName2.setText(Ds.child("lastName").getValue(String.class));

                        if(Ds.child("imageUrl").getValue().equals("")) {
                            progressBar.setVisibility(View.VISIBLE);
                            ProfilePicPrompt();

                        }else {

            //Converting image Url String to be understood by Java as an Image by using Picasso
                            final String imageUrl = Ds.child("imageUrl").getValue(String.class);
                            Picasso.get()
                                    .load(imageUrl)
                                    .into(ProfilePicture);

                            progressBar.setVisibility(View.GONE);
                        }

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });


    }

    private void UiResponse() {

        Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, Purchases.class);
                intent.putExtra("PhoneNumber",Id);
                startActivity(intent);
            }
        });


        Wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Account.this, com.example.nirvanaeatery.Activity.Profile.Wallet.class);
                intent.putExtra("PhoneNumber",Id);
                startActivity(intent);
            }
        });


        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, AccountDetails.class);
                intent.putExtra("PhoneNumber",Id);
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOutPrompt();
            }
        });

    }

    private void bottomNavigationView() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout ProfileBtn = findViewById(R.id.ProfileBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout SupportBtn = findViewById(R.id.SupportBtn);
        LinearLayout TrackOrderBtn = findViewById(R.id.TrackOrderBtn);




//Button Response for MainMenu button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, MainMenu.class);
                intent.putExtra("Phone",Id);
                startActivity(intent);
            }
        });

//Button Response for Cart Button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, Cart.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Support Button
        SupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, Support.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for TrackOrder Button
        TrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, Purchases.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });



    }


//Alert Dialogue Box for Logging Out, It will be executed on Logout Btn press
    private void LogOutPrompt(){

        SharedPreferences.Editor editor = sharedPreferences.edit();

        new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("You are going to be logged out and all you User data will be deleted")
                .setPositiveButton("Yes, Delete Account",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which){
                FirebaseAuth.getInstance().signOut();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(Account.this,Get_Started.class);
                startActivity(intent);
                finish();

            }
        })
                .setNegativeButton("No, Stay Logged In",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        })
                .show();
    }

    private void ProfilePicPrompt(){
        new AlertDialog.Builder(this)
                .setTitle("Set Profile")
                .setMessage("Go to Account Details to Set your Profile ")
                .setPositiveButton("Set Profile",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Intent intent = new Intent(Account.this,AccountDetails.class);
                        intent.putExtra("PhoneNumber",Id);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Later",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                    }
                })
                .show();
    }


}