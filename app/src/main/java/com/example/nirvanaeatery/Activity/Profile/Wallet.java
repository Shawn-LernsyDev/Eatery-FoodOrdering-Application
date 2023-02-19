package com.example.nirvanaeatery.Activity.Profile;

import static android.provider.Telephony.BaseMmsColumns.MESSAGE_ID;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nirvanaeatery.Activity.Account;
import com.example.nirvanaeatery.R;

public class Wallet extends AppCompatActivity {
    ImageView Back;
    private String Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_wallet);

        Back = findViewById(R.id.backbtn);


        getIntentBundle();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Wallet.this, Account.class);
                intent.putExtra("PhoneNo", Id);
                startActivity(intent);
            }
        });

    }

    private void getIntentBundle() {
        Id = getIntent().getStringExtra("Phone");

//Get Phone Number from Shared Preferences only our application will have access to this shared preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        Id = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");

    }
}