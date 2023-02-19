package com.example.nirvanaeatery.Activity.Permissions;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.nirvanaeatery.Activity.TrackOrder;
import com.example.nirvanaeatery.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class LocationPermission extends AppCompatActivity {

//Declaring Variable

    Button Allow;

    private Context context;

    SharedPreferences sharedPreferences;
    private static final String MESSAGE_ID = "phoneno_prefs";

    private String Id;
    double total;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_permission);

//Instantiating the variable
        Allow =findViewById(R.id.Allow);

        getIncomingIntent();
        Permission();
        Grant();
    }


    private void getIncomingIntent() {
//Get Phone Number from Shared Preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        Id = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");

// Get intent from Cart to pass amount to Track Order
        total = getIntent().getDoubleExtra("total_amount", 0);
        time = getIntent().getIntExtra("total_time",0);
    }

//Location Permission Method
//Going to the Track Activity if Location access is granted

     public void Permission(){
        if(ContextCompat.checkSelfPermission(LocationPermission.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Intent intent  = new Intent(LocationPermission.this, TrackOrder.class);
            intent.putExtra("total_amount", total);
            intent.putExtra("total_time", time);
            startActivity(intent);
            finish();
            return;

        }

    }

//OnClickListener For Granting Location Permission using the Dexter Dependencies.

    public void Grant(){
        Allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(LocationPermission.this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent  = new Intent(LocationPermission.this, TrackOrder.class);
                                intent.putExtra("PhoneNo",Id);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            //Alert Dialogue if permission is denied
                                if(permissionDeniedResponse.isPermanentlyDenied()){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LocationPermission.this);
                                    builder.setTitle("Permission Denied")
                                           .setMessage("Permission to access device location is permanently denied. Change it in the App Management Settings")
                                           .setNegativeButton("Cancel",null)
                                           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialogInterface, int i) {
                                               Intent intent = new Intent();
                                               intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                               intent.setData(Uri.fromParts("package",getPackageName(), null));


                                               }
                                           })
                                           .show();

                                }else {
                                    Toast.makeText(LocationPermission.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }

                        })
                        .check();

            }
        });

    }

}