package com.example.onlineclothesstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void signInCustomer(View v) {
        Intent intent = new Intent(getApplicationContext(), LoginCustomer.class);
        startActivity(intent);
    }

    public void signInAdministrator(View v) {
        Intent intent = new Intent(getApplicationContext(), LoginAdministrator.class);
        startActivity(intent);
    }

}