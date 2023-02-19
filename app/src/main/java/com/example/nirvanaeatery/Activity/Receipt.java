package com.example.nirvanaeatery.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nirvanaeatery.Adapter.BoughtgoodsAdapter;
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

public class Receipt extends AppCompatActivity {

    private static final String MESSAGE_ID = "phoneno_prefs";
    private String phoneNumber;
    TextView OrderId, OrderDate, Mpesa, OrderStatus, amount;
    TextView Subtotal, DeliveryFee, taxFee, Total, Message;
    RecyclerView CartItemsView;
    RecyclerView.Adapter adapter;



    private OrderDatabaseDomain object;
    private String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        object = (OrderDatabaseDomain) getIntent().getSerializableExtra("object");

        // Get Phone Number from Shared Preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        phoneNumber = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");
        
        
        inviews();
        getOrderHistory(object);

        Button btnButton = findViewById(R.id.backbutton);
        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void inviews() {
        OrderId = findViewById(R.id.text_order_id);
        OrderDate = findViewById(R.id.text_order_date);
        OrderStatus = findViewById(R.id.text_order_status);
        Mpesa = findViewById(R.id.mpesaid);
        amount = findViewById(R.id.GrandTotal);
        CartItemsView = findViewById(R.id.cart_items);

        Subtotal = findViewById(R.id.TotalFeeText);
        DeliveryFee = findViewById(R.id.DeliveryFeeText);
        taxFee = findViewById(R.id.taxFeeText);
        Total = findViewById(R.id.text_total_fee);
        Message = findViewById(R.id.message);


    }


    private void getOrderHistory(OrderDatabaseDomain order) {



        OrderId.setText("Order ID: " + object.getOrderId());
        OrderDate.setText(order.getDate());
        OrderStatus.setText(order.getStatus());
        Mpesa.setText("Transaction Code: " + order.getMpesaId());



        // Reference the "Orders" node in the Firebase Realtime Database
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");


        // Query the database to get all orders with the specified phone number
        Query orderIDQuery = ordersRef.orderByChild("orderId").equalTo(object.getOrderId());
        orderIDQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If there are orders with the specified phone number, iterate through the snapshot and get the order details
                if (dataSnapshot.exists()) {

                    for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {

                        ArrayList<FoodDomain> cartItems = new ArrayList<>();
                            for (DataSnapshot foodSnapshot : orderSnapshot.child("cartItems").getChildren()) {

//A for loop is used to iterate over each FoodDomain item in the cartItems list
                                FoodDomain food = foodSnapshot.getValue(FoodDomain.class);
                                cartItems.add(food);
                                double total = 0;
                                double Deliveryfee = 100.0;
                                double ServiceFee = 0;
                                double FEE = 0;

                                for (FoodDomain item : cartItems) {
                                    double amount = item.getFee() * item.getNumberInCart();
                                    total += amount;
                                    ServiceFee += 0.10 * amount;
                                    FEE = total + Deliveryfee + ServiceFee;

                                }
                                Subtotal.setText("KES " + total);
                                DeliveryFee.setText("KES " + Deliveryfee);
                                taxFee.setText("KES " + ServiceFee);
                                Total.setText("KES " + FEE);

                                amount.setText("Paid: " + "KES" + FEE);

                                Message.setText("Thanks for ordering, " + object.getName());
                            }

                        displayOrderHistory(cartItems);
                            }

                    }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", databaseError.getMessage());
            }
        });
    }


    private void displayOrderHistory(ArrayList<FoodDomain> receiptOrder) {

        // Create a new adapter for the RecyclerView
        BoughtgoodsAdapter adapter = new BoughtgoodsAdapter(this, receiptOrder);


        // Set the adapter for the RecyclerView
        CartItemsView.setAdapter(adapter);

        // Set the layout manager for the RecyclerView
        CartItemsView.setLayoutManager(new LinearLayoutManager(this));
    }
}