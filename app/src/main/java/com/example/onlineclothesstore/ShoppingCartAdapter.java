package com.example.onlineclothesstore;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {
    private ArrayList<ShoppingCartItems> list;
    private CartClickListener cartClickListener;

    public void setOnCartClickListener(CartClickListener cartClickListener){
        this.cartClickListener = cartClickListener;
    }


    // Provide a reference to the views for each data item
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;
        public TextView textView5;
        public ImageView itemImageView;

        public MyViewHolder(View itemView) {
            super(itemView); //itemView corresponds to all views defined in row layout

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            textView5 = itemView.findViewById(R.id.quantityTextView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int position = this.getLayoutPosition();
            String title = list.get(position).getTitle();
        }

    }

    public ShoppingCartAdapter(ShoppingCart shoppingCart, ArrayList<ShoppingCartItems> shoppingCartItems) {
        list = shoppingCartItems;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShoppingCartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView= inflater.inflate(R.layout.row_model, parent, false);
        //false: inflate the row
        // layout to parent and return view, if true return parent+view
        MyViewHolder viewHolder= new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // -get element from your dataset at this position
        // -replace the contents of the view with that element

        ShoppingCartItems currentStockItem = list.get(position);
        holder.textView1.setText("Title: " + currentStockItem.getTitle());
        holder.textView2.setText("Manufacturer: " + currentStockItem.getManufacturer());
        holder.textView3.setText("Category: " + currentStockItem.getCategory());
        holder.textView4.setText("Price: €" + currentStockItem.getPrice());
        holder.textView5.setText("Quantity: " + currentStockItem.getQuantity());

        Picasso.get()
                .load(currentStockItem.getImage())
                .into(holder.itemImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickListener.OnCartClick(position, currentStockItem);
            }
        });
    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return list.size();
    }


    public void filterList(ArrayList<ShoppingCartItems> filteredList) {
        list = filteredList;
        notifyDataSetChanged();

    }

}


