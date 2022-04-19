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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewUpdateCustomerProfile extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button updateDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_update_customer_profile);

        updateDetails = (Button) findViewById(R.id.update);
        updateDetails.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users: Customers");
        userID = user.getUid();

        EditText nameEditText = (EditText) findViewById(R.id.name);
        EditText phoneNumberEditText = (EditText) findViewById(R.id.num);
        EditText shippingAddressEditText = (EditText) findViewById(R.id.address);
        EditText paymentMethodEditText = (EditText) findViewById(R.id.payment);


        //retrieve user details and updateUI
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { //every time change data the event listener will execute ondatachangemethod
                UserCustomer userProfile = snapshot.getValue(UserCustomer.class);

                if(userProfile != null){
                    String name = userProfile.fullName;
                    String phoneNumber = userProfile.phoneNumber;
                    String shippingAddress = userProfile.shippingAddress;
                    String paymentMethod = userProfile.paymentMethod;

                    nameEditText.setText(name);
                    phoneNumberEditText.setText(phoneNumber);
                    shippingAddressEditText.setText(shippingAddress);
                    paymentMethodEditText.setText(paymentMethod);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewUpdateCustomerProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update:
                updateDetails();
                break;
        }
    }

    private void updateDetails() {
        EditText name = (EditText) findViewById(R.id.name);
        String username = name.getText().toString();
        EditText phoneNumber = (EditText) findViewById(R.id.num);
        String number = phoneNumber.getText().toString();
        EditText shippingAddress = (EditText) findViewById(R.id.address);
        String address = shippingAddress.getText().toString();
        EditText paymentMethod = (EditText) findViewById(R.id.payment);
        String payment = paymentMethod.getText().toString();

        if (username.isEmpty()) {
            name.setError("Name is required!");
            name.requestFocus();
            return;
        }
        else {
            String userID = user.getUid();
            DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("Users: Customers").child(userID);
            fireDB.child("name").setValue(username).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {      // Write was successful!
                    Toast.makeText(ViewUpdateCustomerProfile.this, "Update for name is successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {// Write failed
                    Toast.makeText(ViewUpdateCustomerProfile.this, "Update failed",
                            Toast.LENGTH_LONG).show();
                }
            });

        }
        if (number.isEmpty()) {
            phoneNumber.setError("Phone number is required!");
            phoneNumber.requestFocus();
            return;
        }

        else if (!number.matches("[0][0-9]{9}")) {
            phoneNumber.setError("Correct format for phone number is: 0xx xxx xxxx");
            phoneNumber.requestFocus();
            return;
        }
        else {
            String userID = user.getUid();
            DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("Users: Customers").child(userID);
            fireDB.child("phoneNumber").setValue(number).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {      // Write was successful!
                    Toast.makeText(ViewUpdateCustomerProfile.this, "Update for phone number is successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {// Write failed
                    Toast.makeText(ViewUpdateCustomerProfile.this, "Update failed",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
        if (address.isEmpty()) {
            shippingAddress.setError("Shipping address is required!");
            shippingAddress.requestFocus();
            return;
        }
        else {
            String userID = user.getUid();
            DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("Users: Customers").child(userID);
            fireDB.child("shippingAddress").setValue(address).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {      // Write was successful!
                    Toast.makeText(ViewUpdateCustomerProfile.this, "Update for shipping address is successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {// Write failed
                    Toast.makeText(ViewUpdateCustomerProfile.this, "Update failed",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
        if (payment.isEmpty()) {
            paymentMethod.setError("Payment method is required!");
            paymentMethod.requestFocus();
            return;
        }
        else {
            String userID = user.getUid();
            DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("Users: Customers").child(userID);
            fireDB.child("paymentMethod").setValue(payment).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {      // Write was successful!
                    Toast.makeText(ViewUpdateCustomerProfile.this, "Update for payment method is successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {// Write failed
                    Toast.makeText(ViewUpdateCustomerProfile.this, "Update failed",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}




