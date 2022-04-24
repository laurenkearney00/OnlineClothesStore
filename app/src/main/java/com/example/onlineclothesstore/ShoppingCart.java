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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingCart extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY1 = "itemID";
    public static final String KEY2 = "shoppingCartItemID";
    public static final String KEY3 = "price";
    public static final String KEY4 = "title";
    public static final String KEY5 = "quantity";
    private ArrayList<ShoppingCartItems> list = new ArrayList<>();
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    RecyclerView mRecyclerView;
    public static ShoppingCartAdapter myAdapter;
    EditText editText;
    private String userID;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    Button shoppingCart, deleteItem, buyItem;
    private String itemID;
    private String shoppingCartItemID;
    private String price;
    private String title;
    private String quantity;
    private DatabaseReference fireDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

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
        myAdapter = new ShoppingCartAdapter(ShoppingCart.this, list);


        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        progressDialog = new ProgressDialog(ShoppingCart.this);

        progressDialog.setMessage("Loading Data from Firebase Database");

        progressDialog.show();

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users: Customers").child(userID).child("ShoppingCartClothes");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ShoppingCartItems shoppingCartItems = dataSnapshot.getValue(ShoppingCartItems.class);

                    list.add(shoppingCartItems);


                }


                myAdapter.notifyDataSetChanged();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });

        myAdapter.setOnCartClickListener(new CartClickListener() {
            @Override
            public void OnCartClick(int position, ShoppingCartItems shoppingCartItems) {
                builder = new AlertDialog.Builder(ShoppingCart.this);
                builder.setTitle("View Shopping Cart Options");
                builder.setCancelable(false);
                View view = LayoutInflater.from(ShoppingCart.this).inflate(R.layout.dialog_shopping_cart_options, null, false);
                builder.setView(view);
                dialog = builder.create();
                dialog.show();

                itemID = shoppingCartItems.getItemID();
                shoppingCartItemID = shoppingCartItems.getShoppingCartItemID();
                price = shoppingCartItems.getPrice();
                title = shoppingCartItems.getTitle();
                quantity = shoppingCartItems.getQuantity();


                buyItem = view.findViewById(R.id.buyItem);
                deleteItem = view.findViewById(R.id.deleteItem);
                shoppingCart = view.findViewById(R.id.shoppingCart);

                buyItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(ShoppingCart.this, Payment.class);
                        i.putExtra(KEY1, itemID);
                        i.putExtra(KEY2, shoppingCartItemID);
                        i.putExtra(KEY3, price);
                        i.putExtra(KEY4, title);
                        i.putExtra(KEY5, quantity);
                        startActivity(i);
                    }
                });

                deleteItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fireDB = FirebaseDatabase.getInstance().getReference().child("Users: Customers").child(userID).child("ShoppingCartClothes");
                        fireDB.child(shoppingCartItemID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {      // Write was successful!
                                Toast.makeText(ShoppingCart.this, "Removal successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ShoppingCart.this, CustomerOptions.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {// Write failed
                                Toast.makeText(ShoppingCart.this, "Removal failed",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                        Intent i = new Intent(ShoppingCart.this, CustomerOptions.class);
                        startActivity(i);
                    }
                });

                shoppingCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });
    }

    private void filter(String text) {
        ArrayList<ShoppingCartItems> filteredList = new ArrayList<>();

        for (ShoppingCartItems shoppingCartItems : list) {
            if (shoppingCartItems.getCategory().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(shoppingCartItems);
            }
            else if (shoppingCartItems.getManufacturer().equalsIgnoreCase(text)) {
                filteredList.add(shoppingCartItems);
            }
            else if (shoppingCartItems.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(shoppingCartItems);
            }
        }

        myAdapter.filterList(filteredList);

    }

    @Override
    public void onClick(View v) {

    }
}