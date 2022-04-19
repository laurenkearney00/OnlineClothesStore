package com.example.onlineclothesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterCustomer extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText editTextFullName, editTextDateOfBirth, editTextEmail, editTextPhoneNumber, editTextPassword;
    private EditText editTextShippingAddress, editTextPaymentMethod;
    private ProgressBar progressBar;
    private TextView registerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        mAuth = FirebaseAuth.getInstance();

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.nameOfCustomer);
        editTextDateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        editTextShippingAddress = (EditText) findViewById(R.id.shippingAddress);
        editTextPaymentMethod = (EditText) findViewById(R.id.paymentMethod);
        editTextPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String dateOfBirth = editTextDateOfBirth.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String shippingAddress = editTextShippingAddress.getText().toString().trim();
        String paymentMethod = editTextPaymentMethod.getText().toString().trim();


        if (fullName.isEmpty()) {
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }

        if (dateOfBirth.isEmpty()) {
            editTextDateOfBirth.setError("Date of Birth is required!");
            editTextDateOfBirth.requestFocus();
            return;
        }

        if (!dateOfBirth.matches("[0-9]{2}[/][0-9]{2}[/][0-9]{4}")) {
            editTextDateOfBirth.setError("Valid date of birth must have format 22/07/2022!");
            editTextDateOfBirth.requestFocus();
            return;
        }

        if (phoneNumber.isEmpty()) {
            editTextDateOfBirth.setError("Phone number is required!");
            editTextDateOfBirth.requestFocus();
            return;
        }

        if (!phoneNumber.matches("[0][0-9]{9}")) {
            editTextPhoneNumber.setError("Correct format for phone number is: 0xx xxx xxxx");
            editTextPhoneNumber.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if (shippingAddress.isEmpty()) {
            editTextShippingAddress.setError("Shipping Address is required!");
            editTextShippingAddress.requestFocus();
            return;
        }

        if (paymentMethod.isEmpty()) {
            editTextPaymentMethod.setError("Payment Method is required!");
            editTextPaymentMethod.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6 || password.contains(" ")) {
            editTextPassword.setError("Min password length should be 6 characters and contain no whitespaces!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String customerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            UserCustomer user = new UserCustomer(fullName, dateOfBirth, email, phoneNumber, shippingAddress, paymentMethod, customerID);

                            FirebaseDatabase.getInstance().getReference("Users: Customers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterCustomer.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(RegisterCustomer.this, LoginCustomer.class);
                                        startActivity(intent);

                                        // redirect to login layout!
                                    } else {
                                        Toast.makeText(RegisterCustomer.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterCustomer.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}