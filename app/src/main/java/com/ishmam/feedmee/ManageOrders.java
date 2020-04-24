package com.ishmam.feedmee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ishmam.feedmee.Model.Cart;
import com.ishmam.feedmee.Model.Food;
import com.ishmam.feedmee.Model.Orders;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ManageOrders extends AppCompatActivity {
    private static FirebaseUser user;
    private RecyclerView recV;
    private ArrayList<Food> arr;
    private FirebaseRecyclerOptions<Orders> options;
    private FirebaseRecyclerAdapter<Orders , FoodViewHolder> adapter;
    private DatabaseReference db;
    DatabaseReference cartListRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);



        recV = findViewById(R.id.recyclerView);
        recV.setHasFixedSize(true);
        recV.setLayoutManager(new LinearLayoutManager(this));

        arr = new ArrayList<Food>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        db = FirebaseDatabase.getInstance().getReference().child("Orders").child("test@test");

        options = new FirebaseRecyclerOptions.Builder<Orders>().setQuery(db, Orders.class).build();

        adapter = new FirebaseRecyclerAdapter<Orders, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FoodViewHolder holder, int i, @NonNull Orders food) {
                holder.fproductID  = food.getPid();
                holder.name.setText(food.getName());
                holder.description.setText(food.getAddress());
                holder.priceV.setText(food.getPrice());
                holder.addBtn.setText("Remove Order");
                holder.addBtn.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View view) {
                                                         db.child(holder.fproductID).removeValue();
                                                         Toast.makeText(ManageOrders.this , "Removed", Toast.LENGTH_SHORT).show();
                                                     }
                                                 }
                );
                holder.quantityBtn.setVisibility(View.GONE);
            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FoodViewHolder(LayoutInflater.from(ManageOrders.this).inflate(R.layout.food_item , parent , false));
            }
        };
        recV.setAdapter(adapter);
        adapter.startListening();


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}