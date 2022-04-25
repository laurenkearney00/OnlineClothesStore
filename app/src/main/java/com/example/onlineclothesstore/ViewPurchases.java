package com.example.onlineclothesstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewPurchases extends AppCompatActivity {


    private ArrayList<CustomerPurchases> list = new ArrayList<>();
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    RecyclerView mRecyclerView;
    public static PurchaseAdapter myAdapter;
    EditText editText;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_purchases);

        Intent i = getIntent();
        userID = i.getStringExtra(CustomerAdapter.KEY1);

        editText = (EditText) findViewById(R.id.search);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        mRecyclerView = findViewById(R.id.my_recycler_view);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        myAdapter = new PurchaseAdapter(ViewPurchases.this, list);


        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        progressDialog = new ProgressDialog(ViewPurchases.this);

        progressDialog.setMessage("Loading Data from Firebase Database");

        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users: Customers").child(userID).child("Purchases");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    CustomerPurchases customerPurchases = dataSnapshot.getValue(CustomerPurchases.class);

                    list.add(customerPurchases);


                }


                myAdapter.notifyDataSetChanged();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });

    }
    private void filter(String text) {
        ArrayList<CustomerPurchases> filteredList = new ArrayList<>();

        for (CustomerPurchases customerPurchases : list) {
            if (customerPurchases.getCategory().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(customerPurchases);
            }
            else if (customerPurchases.getManufacturer().equalsIgnoreCase(text)) {
                filteredList.add(customerPurchases);
            }
            else if (customerPurchases.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(customerPurchases);
            }
        }

        myAdapter.filterList(filteredList);
    }




}