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

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    DatabaseReference cartListRef;
    private TextView totalPrice;
    private Button checkoutBtn;
    FirebaseAuth auth;
    FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter;
    FirebaseRecyclerOptions<Cart> options;

    private int overallTotalPrice=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart");
        cartListRef.keepSynced(true);

        recyclerView=findViewById(R.id.cart_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        totalPrice=findViewById(R.id.total_price);
        checkoutBtn=findViewById(R.id.checkoutBtn);


        String pid=cartListRef.push().getKey();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        final String domain = email .substring(0,email .indexOf("."));



        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CartActivity.this,ConfirmOrderActivity.class);
                intent.putExtra("Total price",String.valueOf(overallTotalPrice));
                startActivity(intent);
                finish();
                Toast.makeText(CartActivity.this, "Order Confirmed", Toast.LENGTH_SHORT).show();
            }
        });


        options=new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("User View")
                .child(domain).child("Products"), Cart.class).build();

        adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CartViewHolder cartViewHolder, int i, final @NonNull Cart cart) {
                cartViewHolder.txtproductName.setText(cart.getPname());
                cartViewHolder.txtproductPrice.setText("Price: "+cart.getPrice());
                cartViewHolder.txtproductQuantity.setText("Quantity: "+cart.getQuantity());


                final String name=cart.getPname();
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("categories");


                String p = cart.getPrice();
                String price = p.substring(p.indexOf(".")+1);
                int oneTypeProductTPrice=((Integer.valueOf(price)))*((Integer.valueOf(cart.getQuantity())));
                overallTotalPrice=overallTotalPrice+oneTypeProductTPrice;
                totalPrice.setText("Total Price= "+String.valueOf(overallTotalPrice));

                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]=new CharSequence[]
                                {
                                        //"Edit",
                                        "Remove"

                                };
                        AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Options: ");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                if(i==0){

                                    String p = cart.getPrice();
                                    String price = p.substring(p.indexOf(".")+1);
                                    int oneTypeProductTPrice=((Integer.valueOf(price)))*((Integer.valueOf(cart.getQuantity())));
                                    overallTotalPrice=overallTotalPrice-oneTypeProductTPrice;
                                    totalPrice.setText("Total Price= "+String.valueOf(overallTotalPrice));
                                    cartListRef.child("User View").child(domain).child("Products").child(cart.getPid())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Item Deleted", Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                        builder.show();}
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder=new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
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
        overallTotalPrice = 0;
        adapter.stopListening();

    }
}
