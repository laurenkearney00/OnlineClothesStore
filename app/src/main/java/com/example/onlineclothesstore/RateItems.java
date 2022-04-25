package com.example.onlineclothesstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RateItems extends AppCompatActivity implements View.OnClickListener {

    private TextView ratingDisplayTextView;
    private RatingBar ratingRatingBar;
    String itemID, title;
    DatabaseReference reference, databaseReference;
    String starID, reviewID;
    String userID;
    private Button submitButton;
    private Button addButton;
    private EditText editTextReview;
    private String commentID, clothesID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_items);

        editTextReview = (EditText) findViewById(R.id.review);

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        TextView titleOfItemTv = (TextView) findViewById(R.id.titleOfItem);
        ratingDisplayTextView = (TextView) findViewById(R.id.rating_display_text_View);
        ratingRatingBar = (RatingBar) findViewById(R.id.rating_bar);

        Intent intent = getIntent();
        itemID = intent.getStringExtra(ViewClothesOptions.KEY1);

        reference = FirebaseDatabase.getInstance().getReference("stock_items");
        reference.child(itemID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { //every time change data the event listener will execute ondatachangemethod
                StockItems stockItems = snapshot.getValue(StockItems.class);

                if (stockItems != null) {
                    title = stockItems.title;
                    titleOfItemTv.setText(title);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RateItems.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("stock_items").child(itemID).child("Ratings");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Rating aRating = dataSnapshot.getValue(Rating.class);
                    if (aRating.getItemID().equals(itemID) && aRating.getUserID().equals(userID)) {
                        String rating = aRating.getRating();
                        starID = aRating.getRatingID();
                        submitButton.setText("Submitted Rating");
                        ratingDisplayTextView.setText("Rating given to item is " + rating);
                        ratingRatingBar.setRating(Float.parseFloat(rating));
                        changeRating();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference("stock_items").child(itemID).child("Reviews");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Reviews aReviews = dataSnapshot.getValue(Reviews.class);
                    if (aReviews.getItemID().equals(itemID) && aReviews.getUserID().equals(userID)) {
                        String review = aReviews.getReview();
                        commentID = aReviews.getReviewID();
                        clothesID = aReviews.getItemID();
                        addButton.setText("Update Review");
                        editTextReview.setText(review);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void changeRating() {
        ratingRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromTouch) {
                String rate = String.valueOf(rating);
                Rating aRating = new Rating(rate, userID, itemID, starID);
                FirebaseDatabase.getInstance().getReference().child("stock_items").child(itemID).child("Ratings")
                        .child(starID)
                        .setValue(aRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RateItems.this, "Updating rating", Toast.LENGTH_SHORT).show();

                        } else
                            Toast.makeText(RateItems.this, "Failed to save rating! Try again!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                addButton();
                break;
            case R.id.submit_button:
                submitButton();
                break;
        }
    }

    private void submitButton() {
        if (submitButton.getText().equals("Submitted Rating")) {
            Toast.makeText(RateItems.this, "Rating has already been created for item", Toast.LENGTH_LONG).show();
        } else {
            DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference().child("Ratings");
            starID = fireDB.push().getKey();
            if (ratingDisplayTextView.getText().toString().equals("Your rating will appear here")) {
                String rate = String.valueOf(ratingRatingBar.getRating());
                ratingDisplayTextView.setText("Rating given to item is " + rate);
                Rating aRating = new Rating(rate, userID, itemID, starID);

                FirebaseDatabase.getInstance().getReference().child("stock_items").child(itemID).child("Ratings")
                        .child(starID)
                        .setValue(aRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RateItems.this, "Rating saved", Toast.LENGTH_LONG).show();
                            submitButton.setText("Submitted Rating");

                        } else
                            Toast.makeText(RateItems.this, "Failed to save rating! Try again!", Toast.LENGTH_LONG).show();
                    }

                });
            } else {
                Toast.makeText(RateItems.this, "Rating is already saved", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void addButton() {
        if (addButton.getText().equals("Update Review")) {
            Toast.makeText(RateItems.this, "Updating Review", Toast.LENGTH_LONG).show();
            update();
        } else {
            DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference().child("Reviews");
            reviewID = fireDB.push().getKey();
            String comment = editTextReview.getText().toString().trim();
            Reviews review = new Reviews(comment, userID, itemID, reviewID);
            FirebaseDatabase.getInstance().getReference().child("stock_items").child(itemID).child("Reviews")
                    .child(reviewID)
                    .setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(RateItems.this, "Review has been added", Toast.LENGTH_LONG).show();
                        addButton.setText("Update Review");

                    } else {
                        Toast.makeText(RateItems.this, "Failed to add review! Try again!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private void update() {
        String comment = editTextReview.getText().toString().trim();

        Reviews reviews = new Reviews();
        reviews.setReview(comment);
        reviews.setReviewID(commentID);
        reviews.setUserID(userID);
        reviews.setItemID(clothesID);

        DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("stock_items").child(itemID).child("Reviews").child(commentID);
        fireDB.child("review").setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {      // Write was successful
                Toast.makeText(RateItems.this, "Update for review is successful", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {// Write failed
            }
        });
    }

}
