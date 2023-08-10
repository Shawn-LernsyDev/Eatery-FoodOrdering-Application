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
import com.example.nirvanaeatery.Activity.MainMenu;
import com.example.nirvanaeatery.Activity.Purchases;
import com.example.nirvanaeatery.Activity.Support;
import com.example.nirvanaeatery.Adapter.MenuAdapter;
import com.example.nirvanaeatery.Domain.FoodDomain;
import com.example.nirvanaeatery.R;

import java.util.ArrayList;

public class NonAlcoholicDrinks extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";
    RecyclerView.Adapter adapter;
    RecyclerView recyclerViewNonAlcoholic;
    private String Id;
    ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_non_alcoholic_drinks);

        getIncomingIntent();
        recyclerViewNonAlcoholic();
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


    private void recyclerViewNonAlcoholic() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewNonAlcoholic = findViewById(R.id.nonalcoholic_side_view);
        recyclerViewNonAlcoholic.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> NonAlcoholic = new ArrayList<>();
        NonAlcoholic.add(new FoodDomain("Maziwa Mala", "maziwa_mala", "Refresh yourself with a glass of Maziwa Mala, a traditional Kenyan beverage made from sour milk. It's a perfect side drink to any meal.", 50.0, 4.5, 2, 150.0));
        NonAlcoholic.add(new FoodDomain("Mango Juice", "mango_juice", "Treat yourself to a glass of our delicious Mango Juice, made from fresh and sweet mangoes.", 70.0, 4.8, 5, 200.0));
        NonAlcoholic.add(new FoodDomain("Orange Juice", "orange_juice", "Enjoy a glass of fresh Orange Juice, perfect for any time of the day.", 60.0, 4.6, 2, 150.0));


        adapter=new MenuAdapter(NonAlcoholic);
        recyclerViewNonAlcoholic.setAdapter(adapter);
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