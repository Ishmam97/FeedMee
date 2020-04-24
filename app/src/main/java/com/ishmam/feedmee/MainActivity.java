package com.ishmam.feedmee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
//    private FirebaseDatabase db;
//    private DatabaseReference myRef;
//
    private Button joinNowBtn,loginBtn,adminBtn;
    private FirebaseAuth auth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinNowBtn=(Button)findViewById(R.id.main_join_now_btn);
        loginBtn=(Button)findViewById(R.id.main_login_btn);
        adminBtn=(Button)findViewById(R.id.admin_btn);



        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, MainMenuActivity.class));
            finish();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        joinNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
//
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });



    }

}
//        mAuth = FirebaseAuth.getInstance();
//        db =  FirebaseDatabase.getInstance();
//        myRef =db.getReference("User");
//                user = new User(name , email, phone , pwd);
//                myRef.push().setValue(user);
//                        User u1 = dataSnapshot.getValue(User.class);
//                        String name = u1.getName();

//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("message");
//        myRef.setValue("hello");