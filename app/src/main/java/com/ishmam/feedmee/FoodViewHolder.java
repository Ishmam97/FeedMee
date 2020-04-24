package com.ishmam.feedmee;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ishmam.feedmee.FoodActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ishmam.feedmee.Model.Cart;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class FoodViewHolder extends RecyclerView.ViewHolder {
    public TextView name , priceV , description;
    public ImageView pic;
    public ElegantNumberButton quantityBtn;
    public Button addBtn;
    public int qt;
    FirebaseUser user;
    DatabaseReference cartListRef;
    String fproductID="";

    public FoodViewHolder(View itemView){

        super(itemView);
        name = itemView.findViewById(R.id.foodName);
        priceV = itemView.findViewById(R.id.foodPrice);
        description = itemView.findViewById(R.id.foodDescription);
        pic = itemView.findViewById(R.id.foodPic);
        quantityBtn = (ElegantNumberButton) itemView.findViewById(R.id.quantityBtn);
        addBtn = itemView.findViewById(R.id.addToCart);
        qt = Integer.parseInt(quantityBtn.getNumber());


        quantityBtn.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                qt = Integer.parseInt(quantityBtn.getNumber());
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String toast = qt+ " "+ name.getText().toString() + "s added to cart";
                if(qt==0){
                            Toast.makeText(getApplicationContext(), "Select quantity!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            addToCart();
                        }
            }
        });
    }
    public void addToCart(){
            final TextView tv = this.name;
            String pname = name.getText().toString();
            String price = priceV.getText().toString();
            int quantity = qt;

//            Date date = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
//            String strDate = formatter.format(date);
            user = FirebaseAuth.getInstance().getCurrentUser();

            String email = user.getEmail();
            final String finalEmail = email.substring(0,email .indexOf("."));
            cartListRef = FirebaseDatabase.getInstance().getReference("Cart");


//  CALCULATE PRICE * QT
//            int price1 = (Integer.parseInt(price) * qt);
//            price = price1 + "";
        String pid=cartListRef.push().getKey();
        final Cart cartProduct=new Cart(pid, pname, price, quantity);


        cartListRef.child("User View").child(finalEmail).child("Products").child(pid).setValue(cartProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
            public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful()){
                        cartListRef.child("Admin View").child(finalEmail).child("Products").
                        child(fproductID).setValue(cartProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful()){
                                 Toast.makeText(getApplicationContext() , "Added to Cart" , Toast.LENGTH_SHORT);
                             }
                          }
                        });
                 }
            }
        });


    }
}


