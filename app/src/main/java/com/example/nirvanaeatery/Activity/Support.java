package com.example.nirvanaeatery.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.nirvanaeatery.R;

public class Support extends AppCompatActivity {

//Constant integer that you can create to store the request code for the phone call permission.
    private static final int CALL_PHONE_REQUEST = 1;

    private String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        getIntentBundle();
        bottomNavigationView();
        dialSupport();
        ChatWhatsApp();
    }


// The is the method that will invoke the dialer to call the Eatery
    private void dialSupport(){
        LinearLayout callButton = findViewById(R.id.ContactBtn);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

    //Checking Permission file if granted; it is in the Manifest. the request code for the phone call permission.
                if(ActivityCompat.checkSelfPermission(Support.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Support.this, new String[] {Manifest.permission.CALL_PHONE}, CALL_PHONE_REQUEST);
                } else {

                //Dial if Permission is granted
                    makeCall();
                }
            }
        });

    }

//..the phone number and dial action method
    private void makeCall() {
        String phoneNumber = "tel:" + "+254706046049"; // replace with the desired phone number
        Intent dial = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
        startActivity(dial);
    }


// Invoking this method takes the user to the chat with the Eatery on Whatsapp
    private void ChatWhatsApp(){
        LinearLayout chatButton = findViewById(R.id.ChatBtn);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "+254706046049"; //PhoneNumber to chat with
                String url = "https://api.whatsapp.com/send?phone=" + phoneNumber; // Passing the WhatsApp API to connect to the specified number.
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));//If the user doesn't have the App it will go to website.
                startActivity(i);
            }
        });

    }

    private void getIntentBundle(){
        Id = getIntent().getStringExtra("PhoneNo");
    }

    private void bottomNavigationView() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout ProfileBtn = findViewById(R.id.ProfileBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout TrackOrderBtn = findViewById(R.id.TrackOrderBtn);



//Button Response for MainMenu button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Support.this, MainMenu.class);
                intent.putExtra("Phone",Id);
                startActivity(intent);
            }
        });

//Button Response for Profile button
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Support.this, Account.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Cart Button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Support.this, Cart.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for TrackOrder Button
        TrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Support.this, Purchases.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });


    }
}