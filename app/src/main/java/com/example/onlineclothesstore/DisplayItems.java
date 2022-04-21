package com.example.onlineclothesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class DisplayItems extends AppCompatActivity implements View.OnClickListener {


    private ArrayList<StockItems> list = new ArrayList<>();
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    RecyclerView mRecyclerView;
    public static RecyclerAdapter myAdapter;
    EditText editText;
    private Button sort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_items);

        sort = (Button) findViewById(R.id.sort);
        sort.setOnClickListener(this);

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
        myAdapter = new RecyclerAdapter(DisplayItems.this, list);


        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        progressDialog = new ProgressDialog(DisplayItems.this);

        progressDialog.setMessage("Loading Data from Firebase Database");

        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("stock_items");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    StockItems stockItems = dataSnapshot.getValue(StockItems.class);

                    list.add(stockItems);


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
        ArrayList<StockItems> filteredList = new ArrayList<>();

        for (StockItems stockItems : list) {
            if (stockItems.getCategory().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(stockItems);
            }
            else if (stockItems.getManufacturer().equalsIgnoreCase(text)) {
                filteredList.add(stockItems);
            }
            else if (stockItems.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(stockItems);
            }
        }

        myAdapter.filterList(filteredList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sort:
                showUpdateDialog();
                break;
        }
    }

    private void showUpdateDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.dialog_options, null);

        dialogBuilder.setView(dialogView);

        final Button buttonTitle = (Button) dialogView.findViewById(R.id.title);
        final Button buttonManufacturer = (Button) dialogView.findViewById(R.id.manufacturer);
        final Button buttonPrice = (Button) dialogView.findViewById(R.id.price);
        final Button buttonCategory = (Button) dialogView.findViewById(R.id.category);

        dialogBuilder.setTitle("Sort stock items in ascending order by title, manufacturer, price or category");

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<StockItems> ascendingList = new ArrayList<>();
                TitleComparator titleComparator = new TitleComparator();
                Collections.sort(list, titleComparator);
                for (StockItems stockItems : list) {
                    ascendingList.add(stockItems);
                }
                myAdapter.ascendingList(ascendingList);
                alertDialog.dismiss();
            }
        });

        buttonManufacturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<StockItems> ascendingList = new ArrayList<>();
                ManufacturerComparator manufacturerComparator = new ManufacturerComparator();
                Collections.sort(list, manufacturerComparator);
                for (StockItems stockItems : list) {
                    ascendingList.add(stockItems);
                }
                myAdapter.ascendingList(ascendingList);
                alertDialog.dismiss();
            }
        });

        buttonPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<StockItems> ascendingList = new ArrayList<>();
                PriceComparator priceComparator = new PriceComparator();
                Collections.sort(list, priceComparator);
                for (StockItems stockItems : list) {
                    ascendingList.add(stockItems);
                }
                myAdapter.ascendingList(ascendingList);
                alertDialog.dismiss();
            }
        });

        buttonCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<StockItems> ascendingList = new ArrayList<>();
                CategoryComparator categoryComparator = new CategoryComparator();
                Collections.sort(list, categoryComparator);
                for (StockItems stockItems : list) {
                    ascendingList.add(stockItems);
                }
                myAdapter.ascendingList(ascendingList);
                alertDialog.dismiss();
            }
        });

/*
        buttonTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<StockItems> ascendingList = new ArrayList<>();
                Collections.sort(list);
                for (StockItems stockItems : list) {
                    ascendingList.add(stockItems);
                }
                myAdapter.ascendingList(ascendingList);
                alertDialog.dismiss();
            }
        });

 */

    }


}