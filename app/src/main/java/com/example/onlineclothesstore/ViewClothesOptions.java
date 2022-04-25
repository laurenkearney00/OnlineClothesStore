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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewClothesOptions extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY1 = "itemID";
    private DatabaseReference reference;
    private DatabaseReference fireDB;
    private String userID;
    private Button addItem;
    private TextView banner;
    private int position;
    private String timestamp;
    private Button rateItem;
    private String itemID;
    private String title;
    private String manufacturer;
    private String category;
    private String price;
    private String quantity;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clothes_options);

        addItem = (Button) findViewById(R.id.addItem);
        addItem.setOnClickListener(this);


        rateItem = (Button) findViewById(R.id.rateItem);
        rateItem.setOnClickListener(this);

        Intent intent= getIntent();

        position = intent.getIntExtra(ClothesAdapter.MESSAGE_KEY2,0);

        title = intent.getStringExtra(ClothesAdapter.MESSAGE_KEY1);
        manufacturer = intent.getStringExtra(ClothesAdapter.MESSAGE_KEY3);
        category = intent.getStringExtra(ClothesAdapter.MESSAGE_KEY4);
        image = intent.getStringExtra(ClothesAdapter.MESSAGE_KEY5);
        itemID = intent.getStringExtra(ClothesAdapter.MESSAGE_KEY6);
        price = intent.getStringExtra(ClothesAdapter.MESSAGE_KEY7);
        quantity = intent.getStringExtra(ClothesAdapter.MESSAGE_KEY8);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        TextView titleText = (TextView) findViewById(R.id.titleTxt);
        TextView manufacturerText = (TextView)  findViewById(R.id.manufacturerTxt);
        TextView categoryText = (TextView)  findViewById(R.id.categoryTxt);
        TextView priceText = (TextView)  findViewById(R.id.priceTxt);
        TextView quantityText = (TextView)  findViewById(R.id.quantityTxt);
        titleText.setText(title);
        manufacturerText.setText(manufacturer);
        categoryText.setText(category);
        priceText.setText(price);
        quantityText.setText(quantity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addItem:
                addItem();
                break;
            case R.id.rateItem:
                rateItem();
                break;
        }
    }

    private void addItem() {

        Intent intent = getIntent();
        String itemID = intent.getStringExtra(ClothesAdapter.MESSAGE_KEY6);
        TextView titleText = (TextView)  findViewById(R.id.titleTxt);
        TextView manufacturerText = (TextView)  findViewById(R.id.manufacturerTxt);
        TextView categoryText = (TextView)  findViewById(R.id.categoryTxt);
        TextView priceText = (TextView)  findViewById(R.id.priceTxt);
        TextView quantityText = (TextView) findViewById(R.id.quantityTxt);

        String titleString = titleText.getText().toString();
        String manufacturerString = manufacturerText.getText().toString();
        String categoryString = categoryText.getText().toString();
        String priceString = priceText.getText().toString();
        String quantityString = quantityText.getText().toString();

        fireDB = FirebaseDatabase.getInstance().getReference().child("ShoppingCartClothes");
        String shoppingCartItemID = fireDB.push().getKey();

        ShoppingCartItems shoppingCartItems = new ShoppingCartItems();
        shoppingCartItems.setTitle(titleString);
        shoppingCartItems.setManufacturer(manufacturerString);
        shoppingCartItems.setCategory(categoryString);
        shoppingCartItems.setPrice(priceString);
        shoppingCartItems.setQuantity(quantityString);
        shoppingCartItems.setImage(image);
        shoppingCartItems.setShoppingCartItemID(shoppingCartItemID);
        shoppingCartItems.setItemID(itemID);

        FirebaseDatabase.getInstance().getReference().child("Users: Customers").child(userID).child("ShoppingCartClothes")
                .child(shoppingCartItemID)
                .setValue(shoppingCartItems).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(ViewClothesOptions.this, "Added to shopping cart", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ViewClothesOptions.this, ShoppingCart.class);
                    startActivity(i);
                } else {
                    Toast.makeText(ViewClothesOptions.this, "Failed to add to shopping cart! Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void rateItem()  {
        Intent i = new Intent(ViewClothesOptions.this, RateItems.class);
        i.putExtra(KEY1, itemID);
        startActivity(i);
    }
}

