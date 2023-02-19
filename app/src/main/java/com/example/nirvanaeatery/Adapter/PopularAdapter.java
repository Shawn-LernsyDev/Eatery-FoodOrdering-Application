package com.example.nirvanaeatery.Adapter;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nirvanaeatery.Activity.FoodDetails;
import com.example.nirvanaeatery.Domain.FoodDomain;
import com.example.nirvanaeatery.Helper.ManagementCart;
import com.example.nirvanaeatery.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    ArrayList<FoodDomain> popularDomains;
    FoodDomain Object;

    public PopularAdapter(ArrayList<FoodDomain> popularDomains) {
        this.popularDomains = popularDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent,false );

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position ) {

// Adding Kenya to the local inbuilt class to get it's position, currency and time
        Locale locale=new Locale("en", "KE");
        NumberFormat numberFormat=NumberFormat.getCurrencyInstance(locale);

//Extracting the data from where it is stored (holder.variable...) is storage, (popularDomains) is data to be extracted in (ArrayList<FoodDomain> popularDomains)
        holder.title.setText(popularDomains.get(position).getTitle());
        holder.fee.setText(String.valueOf(numberFormat.format(popularDomains.get(position).getFee())));


    int drawableResourceId=holder.itemView.getContext().getResources()
            .getIdentifier(popularDomains.get(position).getPicture(),"drawable",
            holder.itemView.getContext().getPackageName());

    Glide.with(holder.itemView.getContext())
                .load(drawableResourceId).into(holder.picture);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), FoodDetails.class);
                intent.putExtra("object", popularDomains.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

    //A new instance of the "ManagementCart" class is created using the context of the itemView in the ViewHolder.
                ManagementCart managementCart = new ManagementCart(holder.itemView.getContext());
                int numberItem = 1;

    //The FoodDomain object at the specified position in the "popularDomains" list is retrieved
            //The number of items in the cart for the FoodDomain object is set to 1.
            //The insertFood method of the ManagementCart class is called, passing in the FoodDomain object, to add the item to the cart.
                FoodDomain foodItem = popularDomains.get(position);
                foodItem.setNumberInCart(numberItem);
                managementCart.insertFood(foodItem);

                Toast.makeText(holder.itemView.getContext(), "Item Added to your Cart", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
     public int getItemCount() {
        return popularDomains.size();
    }

    //Creating the variables in ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView picture;
        TextView fee;
        ImageView AddBtn;
        ConstraintLayout mainLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

    //Declaring the variables in ViewHolder
        title=itemView.findViewById(R.id.PopularName);
        picture=itemView.findViewById(R.id.PopularImage);
        fee=itemView.findViewById(R.id.PriceTag);
        AddBtn=itemView.findViewById(R.id.AddBtn);
        mainLayout=itemView.findViewById(R.id.mainlayout2);



        }
    }
}