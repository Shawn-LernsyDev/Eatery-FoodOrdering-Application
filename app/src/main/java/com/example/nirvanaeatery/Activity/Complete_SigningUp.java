package com.example.nirvanaeatery.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nirvanaeatery.Domain.UserDomain;
import com.example.nirvanaeatery.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Complete_SigningUp extends AppCompatActivity {

//Declaring variables
    EditText First_Name, Last_Name, Email, Contact;
    DatabaseReference userDatabase;
    FirebaseDatabase myDatabase;
    String PhoneNo;

//The shared preferences file name
    private static final String PHONE_ID = "phoneno_prefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_signing_up);

//Instantiate variables
        First_Name= findViewById(R.id.first_name);
        Last_Name= findViewById(R.id.last_name);
        Email = findViewById(R.id.Text_EmailAddress);
        Contact = findViewById(R.id.PhoneNo);
        Button Done = findViewById(R.id.Done);

//Calling getIntentBundle Method and setting data stored in PhoneNo variable as Contact which is the Phonenumber
        getIntentBundle();
        Contact.setText(PhoneNo);


//Set OnClickListener for the Done Button
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    //Calling the addUser method
                addUser();

            }

        });

    }

//Method for getting IntentPut Extra and Storing it on phone Memory as a SharedPrefrence
    private void getIntentBundle() {

        PhoneNo = getIntent().getStringExtra("PhoneNo");

//Shared preference helps the data to be stored and can be used sometime else on the app device
    //Saving PhoneNumber in phone memory as a shared preference only our application will have access to this shared preferences
    //Accessing the SharedPreference class and connecting it to an object and call getSharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences(PHONE_ID, MODE_PRIVATE);

    //Accessing the SharedPreference editor so as to store data in it
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PhoneNumber", PhoneNo);
        editor.apply();
    }

    private void addUser(){

//Variables will store the input on the form fields
        String firstName = First_Name.getText().toString().trim();
        String lastName = Last_Name.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String phoneNumber = Contact.getText().toString().trim();
        String imageUrl = "";

        String Name = firstName + " " + lastName;


//Saving Account Details on Firebase Real Time Database
            if( !(email.isEmpty() || lastName.isEmpty() || firstName.isEmpty())){


                //Accessing the Firebase Database Class and Instantiate by getting Reference Path
                userDatabase = myDatabase.getInstance().getReference("Users");

                // Generate a unique key for the new order
                String orderKey = userDatabase.child( Name + " - " + phoneNumber).getKey();

                // Create a map to store the order details
                Map<String, Object> orderMap = new HashMap<>();
                orderMap.put("firstName", firstName);
                orderMap.put("lastName", lastName);
                orderMap.put("email", email);
                orderMap.put("phoneNumber", phoneNumber);
                orderMap.put("imageUrl", imageUrl);

                Toast.makeText(this, "user almost added", Toast.LENGTH_SHORT).show();
                // Add the order to the "orders" node in the Firebase Realtime Database
                userDatabase.child(orderKey).setValue(orderMap);


                Intent intent= new Intent(Complete_SigningUp.this,MainMenu.class);
                intent.putExtra("Phone",PhoneNo);
        //Intent that this will be the last activity bundle and a new one will begin starting from Main Menu
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                Toast.makeText(Complete_SigningUp.this,"Welcome " + firstName,Toast.LENGTH_SHORT).show();

            }else{
                showErrorDialog();
            }
    }

//Setting an Alert Dialog For login Failure
    private void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Login Failed")
                .setMessage("Enter all your account details and try again.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}