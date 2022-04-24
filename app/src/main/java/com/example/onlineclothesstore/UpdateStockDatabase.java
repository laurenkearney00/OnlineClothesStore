package com.example.onlineclothesstore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateStockDatabase extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference reference;
    private DatabaseReference fireDB;
    private String userID;
    private Button updateDetails;
    private TextView banner;
    private int position;
    private String timestamp;
    private Button removeItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock_database);

        updateDetails = (Button) findViewById(R.id.updateButton);
        updateDetails.setOnClickListener(this);


        removeItem = (Button) findViewById(R.id.remove);
        removeItem.setOnClickListener(this);

        Intent intent= getIntent();

        position = intent.getIntExtra(RecyclerAdapter.MESSAGE_KEY2,0);

        String title = intent.getStringExtra(RecyclerAdapter.MESSAGE_KEY1);
        String manufacturer = intent.getStringExtra(RecyclerAdapter.MESSAGE_KEY3);
        String category = intent.getStringExtra(RecyclerAdapter.MESSAGE_KEY4);
        //String image = intent.getStringExtra(RecyclerAdapter.MESSAGE_KEY5);
        String itemID = intent.getStringExtra(RecyclerAdapter.MESSAGE_KEY6);
        String price = intent.getStringExtra(RecyclerAdapter.MESSAGE_KEY7);
        String quantity = intent.getStringExtra(RecyclerAdapter.MESSAGE_KEY8);

        EditText titleEditText = (EditText) findViewById(R.id.titleTxt);
        EditText manufacturerEditText = (EditText) findViewById(R.id.manufacturerTxt);
        EditText categoryEditText = (EditText) findViewById(R.id.categoryTxt);
        EditText priceEditText = (EditText) findViewById(R.id.priceTxt);
        EditText quantityEditText = (EditText) findViewById(R.id.quantityTxt);
        titleEditText.setText(title);
        manufacturerEditText.setText(manufacturer);
        categoryEditText.setText(category);
        priceEditText.setText(price);
        quantityEditText.setText(quantity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateButton:
                updateDetails();
                break;
            case R.id.remove:
                removeValue();
                break;
        }
    }

    private void removeValue() {
        Intent intent = getIntent();
        String itemID = intent.getStringExtra(RecyclerAdapter.MESSAGE_KEY6);
        fireDB = FirebaseDatabase.getInstance().getReference("stock_items");
        fireDB.child(itemID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {      // Write was successful!
                Toast.makeText(UpdateStockDatabase.this, "Removal successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(UpdateStockDatabase.this, AdminOptions.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {// Write failed
                Toast.makeText(UpdateStockDatabase.this, "Removal failed",
                        Toast.LENGTH_LONG).show();
            }
        });

    }


    private void updateDetails()  {
        Intent intent = getIntent();
        String itemID = intent.getStringExtra(RecyclerAdapter.MESSAGE_KEY6);
        EditText titleEditText = (EditText) findViewById(R.id.titleTxt);
        EditText manufacturerEditText = (EditText) findViewById(R.id.manufacturerTxt);
        EditText categoryEditText = (EditText) findViewById(R.id.categoryTxt);
        EditText priceEditText = (EditText) findViewById(R.id.priceTxt);
        EditText quantityEditText = (EditText) findViewById(R.id.quantityTxt);

        String titleString = titleEditText.getText().toString();
        String manufacturerString = manufacturerEditText.getText().toString();
        String categoryString = categoryEditText.getText().toString();
        String priceString = priceEditText.getText().toString();
        String quantityString = quantityEditText.getText().toString();

        StockItems stockItems = new StockItems();
        stockItems.setTitle(titleString);
        stockItems.setManufacturer(manufacturerString);
        stockItems.setCategory(categoryString);
        stockItems.setPrice(priceString);
        stockItems.setQuantity(quantityString);

        DisplayItems.myAdapter.updateStockItems(position, stockItems);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fireDB = FirebaseDatabase.getInstance().getReference("stock_items").child(itemID);
        if (titleString.isEmpty()) {
            titleEditText.setError("Title is required!");
            titleEditText.requestFocus();
            return;
        }
        else {
            DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("stock_items").child(itemID);
            fireDB.child("title").setValue(titleString).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {      // Write was successful
                    Toast.makeText(UpdateStockDatabase.this, "Update for title is successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {// Write failed
                    Toast.makeText(UpdateStockDatabase.this, "Update failed",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
        if (manufacturerString.isEmpty()) {
            manufacturerEditText.setError("Manufacturer is required!");
            manufacturerEditText.requestFocus();
            return;
        }
        else {
            DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("stock_items").child(itemID);
            fireDB.child("manufacturer").setValue(manufacturerString).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {      // Write was successful!
                    Toast.makeText(UpdateStockDatabase.this, "Update for manufacturer is successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {// Write failed
                    Toast.makeText(UpdateStockDatabase.this, "Update failed",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

        if (categoryString.isEmpty()) {
            categoryEditText.setError("Category is required!");
            categoryEditText.requestFocus();
            return;
        }

        else {
            DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("stock_items").child(itemID);
            fireDB.child("category").setValue(categoryString).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {      // Write was successful!
                    Toast.makeText(UpdateStockDatabase.this, "Update for category is successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {// Write failed
                    Toast.makeText(UpdateStockDatabase.this, "Update failed",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

        if (priceString.isEmpty()) {
            priceEditText.setError("Price is required!");
            priceEditText.requestFocus();
            return;
        }

        else {
            DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("stock_items").child(itemID);
            fireDB.child("price").setValue(priceString).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {      // Write was successful!
                    Toast.makeText(UpdateStockDatabase.this, "Update for price is successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {// Write failed
                    Toast.makeText(UpdateStockDatabase.this, "Update failed",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

        if (quantityString.isEmpty()) {
            quantityEditText.setError("Quantity is required!");
            quantityEditText.requestFocus();
            return;
        }

        else {
            DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("stock_items").child(itemID);
            fireDB.child("quantity").setValue(quantityString).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {      // Write was successful!
                    Toast.makeText(UpdateStockDatabase.this, "Update for quantity is successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {// Write failed
                    Toast.makeText(UpdateStockDatabase.this, "Update failed",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}

