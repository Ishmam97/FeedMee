package com.ishmam.feedmee;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ishmam.feedmee.Interface.ItemClickListener;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtproductName,txtproductPrice,txtproductDescription;
    public ImageView imageview;
    public ItemClickListener listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageview=(ImageView)itemView.findViewById(R.id.product_image);
        txtproductName=(TextView)itemView.findViewById(R.id.product_name);
        txtproductPrice=(TextView)itemView.findViewById(R.id.product_price);
        //txtproductDescription=(TextView)itemView.findViewById(R.id.product_description);
    }

    public void setItemClickListener(ItemClickListener listener){
           this.listener=listener;
    }

    @Override
    public void onClick(View v) {

        listener.onClick(v, getAdapterPosition(),false);

    }
}
