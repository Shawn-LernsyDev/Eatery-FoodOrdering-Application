package com.example.nirvanaeatery.Activity.SubCategories;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nirvanaeatery.Activity.Account;
import com.example.nirvanaeatery.Activity.Cart;
import com.example.nirvanaeatery.Activity.Cartegories.Drinks;
import com.example.nirvanaeatery.Activity.Cartegories.Heavy_Meals;
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
    ImageView Back;

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
        Back = findViewById(R.id.backbtn);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Drinks.class);
                intent.putExtra("PhoneNo", Id);
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