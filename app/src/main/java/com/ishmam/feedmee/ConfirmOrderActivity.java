package com.ishmam.feedmee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ishmam.feedmee.Model.Orders;

public class ConfirmOrderActivity extends AppCompatActivity {

    private EditText nameEditText,phoneEditText,addressEditText;
    private Button confirmOrderBtn;
    private TextView totalPrice;
    private String totalAmount="";
    DatabaseReference Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        Ref= FirebaseDatabase.getInstance().getReference("Orders");

        nameEditText=findViewById(R.id.shipment_name);
        phoneEditText=findViewById(R.id.shipment_phone);
        addressEditText=findViewById(R.id.shipment_address);
        totalPrice=findViewById(R.id.totPrice);
        confirmOrderBtn=findViewById(R.id.confirmOrder_btn);

        totalAmount=getIntent().getStringExtra("Total price");
        totalPrice.setText("Total price: Tk."+totalAmount);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String address= addressEditText.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter your name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter your phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(address)) {
                    Toast.makeText(getApplicationContext(), "Enter your address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    confirmOrder();
                }

            }
        });
    }

    public void confirmOrder(){

        String pid=Ref.push().getKey();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        final String domain = email .substring(0,email .indexOf("."));

        String pname=nameEditText.getText().toString();
        String phone=phoneEditText.getText().toString();
        String address=addressEditText.getText().toString();
        String price=totalAmount;

        final Orders orders=new Orders(pid,pname,phone,address,price);
        Ref.child(domain).child(pid).setValue(orders);

        nameEditText.setText("");
        phoneEditText.setText("");
        addressEditText.setText("");

        FirebaseDatabase.getInstance().getReference()
                .child("Cart").child("User View").child(domain).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Order Confirmed", Toast.LENGTH_SHORT)
                                    .show();
                            Intent intent = new Intent(ConfirmOrderActivity.this, MainMenuActivity.class);
                            startActivity(intent);

                        }

                    }
                });


    }

}
