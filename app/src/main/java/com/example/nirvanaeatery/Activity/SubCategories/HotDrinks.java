package com.example.nirvanaeatery.Activity.SubCategories;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nirvanaeatery.Activity.Account;
import com.example.nirvanaeatery.Activity.Cart;
import com.example.nirvanaeatery.Activity.MainMenu;
import com.example.nirvanaeatery.Activity.Purchases;
import com.example.nirvanaeatery.Activity.Support;
import com.example.nirvanaeatery.Adapter.MenuAdapter;
import com.example.nirvanaeatery.Domain.FoodDomain;
import com.example.nirvanaeatery.R;

import java.util.ArrayList;

public class HotDrinks extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";
    RecyclerView.Adapter adapter;
    RecyclerView recyclerViewHotDrinks;
    private String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_hot_drinks);
       
        getIncomingIntent();
        recyclerViewHotDrinks();
        getIntentBundle();
        bottomNavigationView();
    }

    private void getIntentBundle(){
//Get Phone Number from Shared Preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        Id = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");

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
                Intent intent = new Intent(HotDrinks.this, MainMenu.class);
                intent.putExtra("Phone",Id);
                startActivity(intent);
            }
        });

//Button Response for Profile button
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotDrinks.this, Account.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Cart Button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotDrinks.this, Cart.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Support Button
        SupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotDrinks.this, Support.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for TrackOrder Button
        TrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotDrinks.this, Purchases.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });



    }

    private void recyclerViewHotDrinks() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewHotDrinks = findViewById(R.id.hotdrinks_side_view);
        recyclerViewHotDrinks.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> HotDrink = new ArrayList<>();
        HotDrink.add(new FoodDomain("Tea", "tea", "Experience the traditional Kenyan taste of tea. Our tea is brewed with the best leaves and served with milk and sugar.", 70.0, 4.5, 5, 150.0));
        HotDrink.add(new FoodDomain("Coffee", "coffee", "Wake up to the aroma of freshly brewed coffee. Our coffee is made from the best Kenyan coffee beans, served with milk and sugar.", 80.0, 4.8, 5, 200.0));
        HotDrink.add(new FoodDomain("Milk", "milk", "Enjoy a glass of fresh milk, perfect for any time of the day.", 50.0, 4.5, 2, 150.0));

        adapter=new MenuAdapter(HotDrink);
        recyclerViewHotDrinks.setAdapter(adapter);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("title")) {
            String CategoryName = getIntent().getStringExtra("title");


            setImage(CategoryName);
        }

    }

    private void setImage(String categoryName) {
        TextView CategoryName = findViewById(R.id.CartName);
        CategoryName.setText(categoryName);
    }

}