package com.ishmam.feedmee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ishmam.feedmee.Model.Food;

public class AdminWork extends AppCompatActivity {
    private Button AddtoDataBaseBtn,AdminSignoutBtn;
    private EditText foodname_input,foodprice_input,foodquantity_input,
            fooddescription_input,foodimg_input;
    Spinner foodtype_input;

    DatabaseReference databaseFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_work);

        databaseFood= FirebaseDatabase.getInstance().getReference("categories");

        AddtoDataBaseBtn=(Button)findViewById(R.id.admininput_btn);
        AdminSignoutBtn=(Button)findViewById(R.id.adminSignout_btn);

        foodname_input=(EditText) findViewById(R.id.foodname_input);
        foodprice_input=(EditText)findViewById(R.id.foodprice_input);

        fooddescription_input=(EditText)findViewById(R.id.fooddescription_input);
        foodimg_input=(EditText)findViewById(R.id.foodimg_input);
        foodtype_input=(Spinner)findViewById(R.id.foodtype_input);

        AddtoDataBaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFood();
            }
        });
        Button order = findViewById(R.id.mngCart);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminWork.this , ManageOrders.class);
                startActivity(intent);
            }
        });
        AdminSignoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminWork.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void AddFood(){
        String name = foodname_input.getText().toString();
        String price = foodprice_input.getText().toString();
        String quantity = foodquantity_input.getText().toString();
        String type = foodtype_input.getSelectedItem().toString();
        String description = fooddescription_input.getText().toString();
        String img = foodimg_input.getText().toString();

        if((!TextUtils.isEmpty(name))||(!TextUtils.isEmpty(price))||(!TextUtils.isEmpty(quantity))
                ||(!TextUtils.isEmpty(type))){

            int quantityInt=Integer.parseInt(quantity);
            DatabaseReference fRef = databaseFood.child(type);

            String id=fRef.push().getKey();

            Food foodProduct=new Food(name,price,description, img, id);
            fRef.child(id).setValue(foodProduct);

            Toast.makeText(AdminWork.this, "Food product added.",
                    Toast.LENGTH_SHORT).show();
            foodname_input.setText("");
            foodprice_input.setText("");
            fooddescription_input.setText("");
            foodquantity_input.setText("");


        }
        else{
            Toast.makeText(AdminWork.this, "Empty field/fields.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}