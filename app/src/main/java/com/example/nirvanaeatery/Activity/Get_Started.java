package com.example.nirvanaeatery.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nirvanaeatery.R;

public class Get_Started extends AppCompatActivity {

// The shared preferences file name
    private static final String MESSAGE_ID = "phoneno_prefs";

// Create variables
    private Button Getstarted;
    private String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//Get Phone Number from Shared Preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        Id = getSharedPrefs.getString("PhoneNumber", null);

//Persistence Logging In, if a PhoneNumber is already registered on the device go straight to Main Activity
        if( Id != null){
            startMainActivity();
        }


        setContentView(R.layout.activity_get_started);

//Otherwise the phone will proceed with the normal program flow below

        // Instantiate the variables
        Getstarted = findViewById(R.id.Done);

        //Set on click listener
        Getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent the button to respond
                    Intent intent = new Intent(Get_Started.this, MainMenu.class);
                    startActivity(intent);

            }
        });


    }

//Start Main Menu Activity Method
    private void startMainActivity() {
        //Intent the button to respond
        Intent intent = new Intent(Get_Started.this, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}