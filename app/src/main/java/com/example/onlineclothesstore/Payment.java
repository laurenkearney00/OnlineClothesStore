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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Payment extends AppCompatActivity implements View.OnClickListener{
    private Button confirmPayment;
    private DatabaseReference fireDB, databaseReference, dataRef;
    private String userID;
    private String shoppingCartItemID;
    private String itemID;
    private String quantity;
    private String purchaseID;
    private String customerID;
    private String price;
    private String title;
    private String manufacturer;
    private String category;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        confirmPayment = (Button) findViewById(R.id.confirmPayment);
        confirmPayment.setOnClickListener(this);

        Intent i = getIntent();
        itemID = i.getStringExtra(ShoppingCart.KEY1);
        shoppingCartItemID = i.getStringExtra(ShoppingCart.KEY2);
        price = i.getStringExtra(ShoppingCart.KEY3);
        title = i.getStringExtra(ShoppingCart.KEY4);
        quantity = i.getStringExtra(ShoppingCart.KEY5);
        manufacturer = i.getStringExtra(ShoppingCart.KEY6);
        category = i.getStringExtra(ShoppingCart.KEY7);
        image = i.getStringExtra(ShoppingCart.KEY8);
        TextView nameOfItems = findViewById(R.id.nameOfItems);
        TextView totalamount = findViewById(R.id.value);
        nameOfItems.setText(title);
        totalamount.setText(price);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmPayment:
                confirmPayment();
                break;
        }
    }

    private void confirmPayment() {
        EditText cardNumber = (EditText) findViewById(R.id.cardNumber);
        String card = cardNumber.getText().toString();
        EditText expiryDate = (EditText) findViewById(R.id.expiryDate);
        String date = expiryDate.getText().toString();
        EditText cvcText = (EditText) findViewById(R.id.cvc);
        String cvc = cvcText.getText().toString();

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        if (card.isEmpty()) {
            cardNumber.setError("Card Number is required!");
            cardNumber.requestFocus();
            return;
        }
        else if (!card.matches("[0-9]{16}")) {
            cardNumber.requestFocus();
            cardNumber.setError("Correct format for card number is: xxxx xxxx xxxx xxxx");
            return;
        }
        else if (date.isEmpty()) {
            expiryDate.setError("Expiry Date is required!");
            expiryDate.requestFocus();
            return;
        }
        else if (!date.matches("[0-9]{2}[/][0-9]{2}[/][0-9]{4}")) {
            expiryDate.setError("Valid card expiration must have format 22/07/2022!");
            expiryDate.requestFocus();
            return;
        }
        else if (cvc.isEmpty()) {
            cvcText.setError("CVC is required!");
            cvcText.requestFocus();
            return;
        }
        else if (!cvc.matches("[0-9]{3}")) {
            cvcText.setError("Valid cvc must have three digits!");
            cvcText.requestFocus();
            return;
        }
        else {
            int stockAmount = Integer.parseInt(quantity);
            int stockLeft = stockAmount - 1;
            String currentQuantity = String.valueOf(stockLeft);
            Toast.makeText(this, "Successful payment has been made!", Toast.LENGTH_LONG).show();
            fireDB = FirebaseDatabase.getInstance().getReference().child("Users: Customers").child(userID).child("ShoppingCartClothes");
            fireDB.child(shoppingCartItemID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {      // Write was successful!
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {// Write failed
                }
            });
            databaseReference = FirebaseDatabase.getInstance().getReference("stock_items").child(itemID);
            databaseReference.child("quantity").setValue(currentQuantity).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {      // Write was successful!
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {// Write failed
                }
            });
            dataRef = FirebaseDatabase.getInstance().getReference().child("Purchases");
            purchaseID = fireDB.push().getKey();
            customerID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            Intent intent = getIntent();
            itemID = intent.getStringExtra(ShoppingCart.KEY1);
            price = intent.getStringExtra(ShoppingCart.KEY3);
            title = intent.getStringExtra(ShoppingCart.KEY4);
            manufacturer = intent.getStringExtra(ShoppingCart.KEY6);
            category = intent.getStringExtra(ShoppingCart.KEY7);
            image = intent.getStringExtra(ShoppingCart.KEY8);

            CustomerPurchases customerPurchases = new CustomerPurchases();
            customerPurchases.setItemID(itemID);
            customerPurchases.setPrice(price);
            customerPurchases.setTitle(title);
            customerPurchases.setManufacturer(manufacturer);
            customerPurchases.setCategory(category);
            customerPurchases.setImage(image);


            FirebaseDatabase.getInstance().getReference().child("Users: Customers").child(customerID).child("Purchases")
                    .child(purchaseID)
                    .setValue(customerPurchases).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                    } else {
                    }
                }
            });

            Intent i = new Intent(Payment.this, CustomerOptions.class);
            startActivity(i);
        }
    }



}
