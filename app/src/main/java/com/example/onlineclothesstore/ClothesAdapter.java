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

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.MyViewHolder> {
    private ArrayList<StockItems> list;

    public static final String MESSAGE_KEY1 ="title";
    public static final String MESSAGE_KEY2 ="position";
    public static final String MESSAGE_KEY3 ="manufacturer";
    public static final String MESSAGE_KEY4 ="category";
    public static final String MESSAGE_KEY5 ="image";
    public static final String MESSAGE_KEY6 ="itemID";
    public static final String MESSAGE_KEY7 ="price";
    public static final String MESSAGE_KEY8 ="quantity";

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
            String quantity = list.get(position).getQuantity();

            Intent intent= new Intent(view.getContext(), ViewClothesOptions.class);
            intent.putExtra(MESSAGE_KEY1, title);
            intent.putExtra(MESSAGE_KEY2, position);
            intent.putExtra(MESSAGE_KEY3, manufacturer);
            intent.putExtra(MESSAGE_KEY4, category);
            intent.putExtra(MESSAGE_KEY5, image);
            intent.putExtra(MESSAGE_KEY6, itemID);
            intent.putExtra(MESSAGE_KEY7, price);
            intent.putExtra(MESSAGE_KEY8, quantity);
            view.getContext().startActivity(intent);
        }

    }

    public ClothesAdapter(DisplayClothes displayClothes, ArrayList<StockItems> stockItems) {
        list = stockItems;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ClothesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

        StockItems currentStockItem = list.get(position);
        holder.textView1.setText("Title: " + currentStockItem.getTitle());
        holder.textView2.setText("Manufacturer: " + currentStockItem.getManufacturer());
        holder.textView3.setText("Category: " + currentStockItem.getCategory());
        holder.textView4.setText("Price: €" + currentStockItem.getPrice());
        holder.textView5.setText("Quantity: " + currentStockItem.getQuantity());

        Picasso.get()
                .load(currentStockItem.getImage())
                .into(holder.itemImageView);
    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return list.size();
    }


    public void filterList(ArrayList<StockItems> filteredList) {
        list = filteredList;
        notifyDataSetChanged();

    }

    public void ascendingList(ArrayList<StockItems> ascendingList) {
        list = ascendingList;
        notifyDataSetChanged();

    }

    public void descendingList(ArrayList<StockItems> descendingList) {
        list = descendingList;
        notifyDataSetChanged();

    }


}


