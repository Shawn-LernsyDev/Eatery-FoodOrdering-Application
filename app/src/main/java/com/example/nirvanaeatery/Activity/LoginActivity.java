package com.example.nirvanaeatery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nirvanaeatery.R;
import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {

//Declaring variable
    private CountryCodePicker ccp;
    private EditText Enter_MobileNo;
    private Button Continue_login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//Instantiating the Variable
        final TextView ccp = findViewById(R.id.CountryCode);
        final EditText Enter_MobileNo = findViewById(R.id.Enter_MobileNo);
        Button Continue_login = findViewById(R.id.Continue_login);
        final ProgressBar progressBar = findViewById(R.id.verification_bar);



//Set OnClickListener for Continue Button
        Continue_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
      //Input of Mobile Number by taking what the user input
                String MobileNo = Enter_MobileNo.getText().toString().trim();
                String CCP = ccp.getText().toString().trim();

          //Mobile Number Format
                if (MobileNo.isEmpty() || MobileNo.length() < 9) {
                    Toast.makeText(LoginActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                } else {
            //Intent and going to the next activity
                    String number = CCP + MobileNo;

                    Intent intent = new Intent(LoginActivity.this, OTP_Verification.class);
                    intent.putExtra("Mobile", number);
                    startActivity(intent);






























                }
            }
        });
    }
}