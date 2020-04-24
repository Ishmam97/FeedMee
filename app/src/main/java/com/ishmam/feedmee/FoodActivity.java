package com.ishmam.feedmee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ishmam.feedmee.Model.Food;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FoodActivity extends AppCompatActivity {
    private static FirebaseUser user;
    private RecyclerView recV;
    private ArrayList<Food> arr;
    private FirebaseRecyclerOptions<Food> options;
    private FirebaseRecyclerAdapter<Food , FoodViewHolder> adapter;
    private DatabaseReference db;
    DatabaseReference cartListRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        cartListRef=FirebaseDatabase.getInstance().getReference("Cart");

        recV = findViewById(R.id.recyclerView);
        recV.setHasFixedSize(true);
        recV.setLayoutManager(new LinearLayoutManager(this));

        arr = new ArrayList<Food>();

        Intent intent = getIntent();
        final String dbRef = intent.getStringExtra("type");
        db = FirebaseDatabase.getInstance().getReference().child("categories").child(dbRef);

        options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(db, Food.class).build();

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FoodViewHolder holder, int i, @NonNull Food food) {
               holder.fproductID  = food.getId();
                holder.name.setText(food.getName());
                holder.description.setText(food.getDescription());
                holder.priceV.setText(food.getPrice());
                Picasso.get().load(food.getImgUrl()).centerCrop().resize(215 , 215).into(holder.pic);
            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FoodViewHolder(LayoutInflater.from(FoodActivity.this).inflate(R.layout.food_item , parent , false));
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
