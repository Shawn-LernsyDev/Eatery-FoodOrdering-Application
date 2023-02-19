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

public class ChapatiSideDish extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";
    RecyclerView.Adapter adapter;
    RecyclerView recyclerViewChapatiMeals;
    private String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_chapati_side_dish);

        getIncomingIntent();
        recyclerViewChapatiMeals();
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
                Intent intent = new Intent(ChapatiSideDish.this, MainMenu.class);
                intent.putExtra("Phone",Id);
                startActivity(intent);
            }
        });

//Button Response for Profile button
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChapatiSideDish.this, Account.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Cart Button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChapatiSideDish.this, Cart.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Support Button
        SupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChapatiSideDish.this, Support.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for TrackOrder Button
        TrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChapatiSideDish.this, Purchases.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });



    }

    private void recyclerViewChapatiMeals() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewChapatiMeals = findViewById(R.id.chapati_side_view);
        recyclerViewChapatiMeals.setLayoutManager(linearLayoutManager);

        ArrayList <FoodDomain> Chapatimeals = new ArrayList<>();
        Chapatimeals.add(new FoodDomain("Chapati and Minji", "chapati_minji", "Try our delicious combination of Chapati and Minji. Minji is a popular Kenyan dish made with ground meat and spices, it's a great source of protein and it will be served with Chapati.", 250.0, 4.8, 20, 600.0));
        Chapatimeals.add(new FoodDomain("Chapati and Beans", "chapati_beans", "Indulge in the flavors of East Africa with our Chapati and Beans. Our beans are cooked with a blend of spices and served with Chapati, a traditional Kenyan bread.", 250.0, 4.7, 25, 600.0));
        Chapatimeals.add(new FoodDomain("Chapati and Green grams", "chapati_ndengu", "Experience the taste of chapati and green grams, a traditional Kenyan dish made with green grams and served with chapati, a traditional Kenyan bread.", 200.0, 4.6, 20, 500.0));
        Chapatimeals.add(new FoodDomain("Chapati and Beef", "chapati_beef", "Try our delicious combination of Chapati and Beef. Our beef is cooked with a blend of spices and served with chapati, a traditional Kenyan bread.", 250.0, 4.8, 20, 600.0));

        adapter=new MenuAdapter(Chapatimeals);
        recyclerViewChapatiMeals.setAdapter(adapter);
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