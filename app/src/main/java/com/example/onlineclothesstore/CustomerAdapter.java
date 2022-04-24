package com.example.onlineclothesstore;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {
    private ArrayList<UserCustomer> list;

    // Provide a reference to the views for each data item
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;

        public MyViewHolder(View itemView) {
            super(itemView); //itemView corresponds to all views defined in row layout

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int position = this.getLayoutPosition();
        }

    }

    public CustomerAdapter(ViewCustomerDetails viewCustomerDetails, ArrayList<UserCustomer> trips) {
        list = trips;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView= inflater.inflate(R.layout.item_customer, parent, false);
        //false: inflate the row
        // layout to parent and return view, if true return parent+view
        MyViewHolder viewHolder= new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // -get element from your dataset at this position
        // -replace the contents of the view with that element


        UserCustomer userCustomer = list.get(position);

        holder.textView1.setText("Customer's name: " + userCustomer.getFullName());
        holder.textView2.setText("Phone number: " + userCustomer.getPhoneNumber());
        holder.textView3.setText("Shipping address: " + userCustomer.getShippingAddress());
        holder.textView4.setText("Payment method: " + userCustomer.getPaymentMethod());
    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return list.size();
        //return mylistvalues.size();
    }




    public void filterList(ArrayList<UserCustomer> filteredList) {
        list = filteredList;
        notifyDataSetChanged();

    }
}

