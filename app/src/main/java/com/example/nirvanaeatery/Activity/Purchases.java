package com.example.nirvanaeatery.Activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nirvanaeatery.Adapter.PurchasesAdapter;
import com.example.nirvanaeatery.Domain.FoodDomain;
import com.example.nirvanaeatery.Domain.OrderDatabaseDomain;
import com.example.nirvanaeatery.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Purchases extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";
    private String phoneNumber;
    RecyclerView orderHistoryRecyclerView;
    private RecyclerView.Adapter adapter;
    ImageView Back;
    TextView emptyText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_purchases);


        // Get Phone Number from Shared Preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        phoneNumber = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");
        orderHistoryRecyclerView = findViewById(R.id.purchases_view);
        Back = findViewById(R.id.backbtn);
        emptyText = findViewById(R.id.emptyTxt2);

        getOrderHistory(phoneNumber);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }


    private void getOrderHistory(String phoneNumber) {
        // Reference the "Orders" node in the Firebase Realtime Database
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");

        // Query the database to get all orders with the specified phone number
        Query phoneNumberQuery = ordersRef.orderByChild("phoneNumber").equalTo(phoneNumber);
        phoneNumberQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If there are orders with the specified phone number, iterate through the snapshot and get the order details
                if (dataSnapshot.exists()) {
                    emptyText.setVisibility(View.GONE);

                    ArrayList<OrderDatabaseDomain> orderHistory = new ArrayList<>();

                    for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {

                            String totalAmount = orderSnapshot.child("totalAmount").getValue().toString();
                            String orderId = orderSnapshot.child("orderId").getValue().toString();

                        if ( orderId != null){

                            String name = orderSnapshot.child("name").getValue().toString();
                            String phoneNumber = orderSnapshot.child("phoneNumber").getValue().toString();
                            String mpesaId = orderSnapshot.child("mpesaID").getValue().toString();
                            String date = orderSnapshot.child("date").getValue().toString();
                            String TimeofOrder = orderSnapshot.child("TimeofOrder").getValue().toString();
                            String paymentTime  = orderSnapshot.child("PaymentTime").getValue().toString();
                            String cookingTime = orderSnapshot.child("cookingTime").getValue().toString();
                            String processingTime = orderSnapshot.child("ProcessingTime").getValue().toString();
                            String pickUpTime = orderSnapshot.child("PickUptime").getValue().toString();

                            ArrayList<FoodDomain> cartItems = (ArrayList<FoodDomain>) orderSnapshot.child("cartItems").getValue();

                            String status = orderSnapshot.child("status").getValue().toString();

                            OrderDatabaseDomain order = new OrderDatabaseDomain(name, phoneNumber, orderId, mpesaId, date, totalAmount, TimeofOrder, paymentTime, processingTime, pickUpTime, cookingTime, status, cartItems);
                            order.setStatus(status);


                            orderHistory.add(order );
                        }else {
                            Toast.makeText(Purchases.this, "ID is Null", Toast.LENGTH_SHORT).show();

                        }



                    }
                    displayOrderHistory(orderHistory);
                } else {
                    Toast.makeText(Purchases.this, "You have no previous orders", Toast.LENGTH_SHORT).show();
                    emptyText.setVisibility(View.VISIBLE);
                    NoitemPrompt();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", databaseError.getMessage());
            }
        });
    }


    private void displayOrderHistory(ArrayList<OrderDatabaseDomain> orderHistory) {

            // Create a new adapter for the RecyclerView
            PurchasesAdapter adapter = new PurchasesAdapter(this, orderHistory);


            // Set the adapter for the RecyclerView
            orderHistoryRecyclerView.setAdapter(adapter);

            // Set the layout manager for the RecyclerView
            orderHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

    private void NoitemPrompt(){
        new android.app.AlertDialog.Builder(this)
                .setTitle("No Item has been Purchased")
                .setMessage("Shop with Nirvana Eatery. Continue Shopping.")
                .setPositiveButton("Main Menu",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        finish();
                    }
                })
                .setNegativeButton("Later",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                })
                .show();
    }

}




