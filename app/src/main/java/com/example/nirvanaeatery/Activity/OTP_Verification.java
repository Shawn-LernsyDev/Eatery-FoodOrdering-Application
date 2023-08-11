package com.example.nirvanaeatery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nirvanaeatery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP_Verification extends AppCompatActivity {

    //Declaring Variable
    private EditText input_code1, input_code2, input_code3, input_code4, input_code5, input_code6;
    private TextView Phone_no;
    private Button Verify;
    ProgressBar AccessBar;

    String VerificationId, Code;
    private FirebaseAuth mAuth;
    String phone_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

//Instantiating Variables
        AccessBar = findViewById(R.id.access_bar);
        Verify = findViewById(R.id.Verify);
        Phone_no = findViewById(R.id.phone_no);

//Instantiating Firebase Authorization variable
        mAuth = FirebaseAuth.getInstance();

//Fetching data from Intent in the getIntentBundle and setting it as Phone Number Text
        getIntentBundle();
        Phone_no.setText(phone_no);

//The Method sendVerificationCode is to be executed on the whatever is being hold on the Phone_no variable ie phone number
        sendVerificationCode(phone_no);

//The Method to input the code
        setupOTPinputs();


//Set OnclickListener on the Verify Button
        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If statement to check if Verified is pressed while the inputs are empty
                if (input_code1.getText().toString().trim().isEmpty() ||
                        input_code2.getText().toString().trim().isEmpty() ||
                        input_code3.getText().toString().trim().isEmpty() ||
                        input_code4.getText().toString().trim().isEmpty() ||
                        input_code5.getText().toString().trim().isEmpty() ||
                        input_code6.getText().toString().trim().isEmpty()) {
                    // Function of the If statement being a Pop Up Message if the Logic statement is true
                    Toast.makeText(OTP_Verification.this, "Enter valid OTP", Toast.LENGTH_SHORT).show();
                }
//If inputs slots aren't empty then get its input
                Code = input_code1.getText().toString() +
                        input_code2.getText().toString() +
                        input_code3.getText().toString() +
                        input_code4.getText().toString() +
                        input_code5.getText().toString() +
                        input_code6.getText().toString();
//Access bar should disappear and Verify Button Appear
                AccessBar.setVisibility(View.GONE);
                Verify.setVisibility(View.VISIBLE);

//The Method VerifyCode should be executed on the variable Code that carries all OTP Verification Codes
                VerifyCode(Code);

            }

        });
    }

    //Method for getting Intent PutExtra data
    private void getIntentBundle() {
        phone_no = getIntent().getStringExtra("Mobile");

    }

    //Method to send verification code that has a constructor of String Type
    //Method to send verification code that has a constructor of String Type
    private void sendVerificationCode(String number) {
        //Connecting phone number to receive verification code
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number) // Phone number to verify
                        .setTimeout(5L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)
                        .setCallbacks(mcallBacks)// Activity (for callback binding)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallBacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                // OnVerificationStateChangedCallbacks
                @Override
                public void onCodeSent(@NonNull String verificationId,
                                       @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(verificationId, forceResendingToken);
                    VerificationId = verificationId;

                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    Code = phoneAuthCredential.getSmsCode();
                    if (VerificationId != null) {
                        AccessBar.setVisibility(View.VISIBLE);
                        Verify.setVisibility(View.GONE);
                        VerifyCode(Code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                    AccessBar.setVisibility(View.GONE);
                    Verify.setVisibility(View.VISIBLE);
                    //Toast.makeText(OTP_Verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(OTP_Verification.this, "Try Again, Check if internet connection is on", Toast.LENGTH_SHORT).show();

                }
            };

    private void VerifyCode(String code){
        PhoneAuthCredential phoneAuthCredential =
                PhoneAuthProvider.getCredential(VerificationId, code);
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Intent the button
                            Intent intent = new Intent(getApplicationContext(), Complete_SigningUp.class);
                            intent.putExtra("PhoneNo", phone_no);
                            startActivity(intent);
                        }else {
                            Toast.makeText(OTP_Verification.this,
                                    "Invalid OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    //Method to setup the Input Slots
    //Method to setup the Input Slots
    private void setupOTPinputs(){

        //Declaring variables

        input_code1 = findViewById(R.id.input_code1);
        input_code2 = findViewById(R.id.input_code2);
        input_code3 = findViewById(R.id.input_code3);
        input_code4 = findViewById(R.id.input_code4);
        input_code5 = findViewById(R.id.input_code5);
        input_code6 = findViewById(R.id.input_code6);


        input_code1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    input_code1.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    input_code2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input_code2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    input_code3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input_code3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    input_code4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input_code4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    input_code5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input_code5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    input_code6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input_code6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    input_code6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().trim().isEmpty()){
                    Verify.requestFocus();
                }
            }
        });


    }

}