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

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.MyViewHolder> {
    private ArrayList<CustomerPurchases> list;

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
            String manufacturer = list.get(position).getManufacturer();
            String category = list.get(position).getCategory();
            String image = list.get(position).getImage();
            String itemID = list.get(position).getItemID();
            String price = list.get(position).getPrice();
        }

    }

    public PurchaseAdapter(ViewPurchases viewPurchases, ArrayList<CustomerPurchases> customerPurchases) {
        list = customerPurchases;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PurchaseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView= inflater.inflate(R.layout.item_purchase, parent, false);
        //false: inflate the row
        // layout to parent and return view, if true return parent+view
        MyViewHolder viewHolder= new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // -get element from your dataset at this position
        // -replace the contents of the view with that element

        CustomerPurchases customerPurchases = list.get(position);
        holder.textView1.setText("Title: " + customerPurchases.getTitle());
        holder.textView2.setText("Manufacturer: " + customerPurchases.getManufacturer());
        holder.textView3.setText("Category: " + customerPurchases.getCategory());
        holder.textView4.setText("Price: €" + customerPurchases.getPrice());

        Picasso.get()
                .load(customerPurchases.getImage())
                .into(holder.itemImageView);
    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return list.size();
    }


    public void filterList(ArrayList<CustomerPurchases> filteredList) {
        list = filteredList;
        notifyDataSetChanged();

    }

}


