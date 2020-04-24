package com.ishmam.feedmee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenuActivity extends AppCompatActivity {
    public TextView welcTv;
    public ImageButton burgerbtn,friesbtn, shakesbtn,desertbtn ,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main_menu);

        welcTv = findViewById(R.id.welcTv);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        email = "Welcome " + email +"!";
        welcTv.setText(email);

        burgerbtn = findViewById(R.id.burgerbtn);
        friesbtn = findViewById(R.id.friesbtn);
        shakesbtn = findViewById(R.id.shakesbtn);
        desertbtn = findViewById(R.id.desertbtn);

        final Intent intent = new Intent(MainMenuActivity.this, FoodActivity.class);

        burgerbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intent.putExtra("type" , "burger");
            startActivity(intent);
        }

    });

        friesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type" , "fries");
                startActivity(intent);
            }

        });
        shakesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type" , "shakes");
                startActivity(intent);
            }

        });
        desertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type" , "desert");
                startActivity(intent);
            }

        });
        ImageView cartBtn = findViewById(R.id.cartBtn);
        cartBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent cart = new Intent(MainMenuActivity.this , CartActivity.class);
                startActivity(cart);
            }
        });
        logout = findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth user = FirebaseAuth.getInstance();
                user.signOut();
                Toast.makeText(getApplicationContext() , "Logged out" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainMenuActivity.this , MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
