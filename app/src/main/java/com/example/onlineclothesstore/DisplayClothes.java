package com.example.onlineclothesstore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class DisplayClothes extends AppCompatActivity implements View.OnClickListener {


    private ArrayList<StockItems> list = new ArrayList<>();
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    RecyclerView mRecyclerView;
    public static ClothesAdapter myAdapter;
    EditText editText;
    private Button sortAscending;
    private Button sortDescending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_clothes);

        sortAscending = (Button) findViewById(R.id.sortAscending);
        sortAscending.setOnClickListener(this);

        sortDescending = (Button) findViewById(R.id.sortDescending);
        sortDescending.setOnClickListener(this);

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
        myAdapter = new ClothesAdapter(DisplayClothes.this, list);


        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        progressDialog = new ProgressDialog(DisplayClothes.this);

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
            case R.id.sortAscending:
                sortAscending();
                break;
            case R.id.sortDescending:
                sortDescending();
                break;
        }
    }

    private void sortAscending() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.dialog_sort_ascending, null);

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
                TitleComparatorAscending titleComparatorAscending = new TitleComparatorAscending();
                Collections.sort(list, titleComparatorAscending);
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
                ManufacturerComparatorAscending manufacturerComparatorAscending = new ManufacturerComparatorAscending();
                Collections.sort(list, manufacturerComparatorAscending);
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
                PriceComparatorAscending priceComparatorAscending = new PriceComparatorAscending();
                Collections.sort(list, priceComparatorAscending);
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
                CategoryComparatorAscending categoryComparatorAscending = new CategoryComparatorAscending();
                Collections.sort(list, categoryComparatorAscending);
                for (StockItems stockItems : list) {
                    ascendingList.add(stockItems);
                }
                myAdapter.ascendingList(ascendingList);
                alertDialog.dismiss();
            }
        });
    }

    private void sortDescending() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.dialog_sort_descending, null);

        dialogBuilder.setView(dialogView);

        final Button buttonTitle1 = (Button) dialogView.findViewById(R.id.title1);
        final Button buttonManufacturer1 = (Button) dialogView.findViewById(R.id.manufacturer1);
        final Button buttonPrice1 = (Button) dialogView.findViewById(R.id.price1);
        final Button buttonCategory1 = (Button) dialogView.findViewById(R.id.category1);

        dialogBuilder.setTitle("Sort stock items in ascending order by title, manufacturer, price or category");

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonTitle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<StockItems> descendingList = new ArrayList<>();
                TitleComparatorDescending titleComparatorDescending = new TitleComparatorDescending();
                Collections.sort(list, titleComparatorDescending);
                for (StockItems stockItems : list) {
                    descendingList.add(stockItems);
                }
                myAdapter.descendingList(descendingList);
                alertDialog.dismiss();
            }
        });

        buttonManufacturer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<StockItems> descendingList = new ArrayList<>();
                ManufacturerComparatorDescending manufacturerComparatorDescending = new ManufacturerComparatorDescending();
                Collections.sort(list, manufacturerComparatorDescending);
                for (StockItems stockItems : list) {
                    descendingList.add(stockItems);
                }
                myAdapter.descendingList(descendingList);
                alertDialog.dismiss();
            }
        });

        buttonPrice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<StockItems> descendingList = new ArrayList<>();
                PriceComparatorDescending priceComparatorDescending = new PriceComparatorDescending();
                Collections.sort(list, priceComparatorDescending);
                for (StockItems stockItems : list) {
                    descendingList.add(stockItems);
                }
                myAdapter.descendingList(descendingList);
                alertDialog.dismiss();
            }
        });

        buttonCategory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<StockItems> descendingList = new ArrayList<>();
                CategoryComparatorDescending categoryComparatorDescending = new CategoryComparatorDescending();
                Collections.sort(list, categoryComparatorDescending);
                for (StockItems stockItems : list) {
                    descendingList.add(stockItems);
                }
                myAdapter.descendingList(descendingList);
                alertDialog.dismiss();
            }
        });
    }
}