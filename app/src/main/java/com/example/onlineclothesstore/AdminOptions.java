package com.example.onlineclothesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminOptions extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        logout = (Button) findViewById(R.id.signOut);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminOptions.this, MainActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users: Administrators");
        userID = user.getUid();

        final TextView greetingTextView = (TextView) findViewById(R.id.greeting);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserCustomer userProfile = snapshot.getValue(UserCustomer.class);

                if(userProfile != null) {
                    String name = userProfile.fullName;
                    greetingTextView.setText("Welcome " + name + " to the online clothes store!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminOptions.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.administrator_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_AddStockItems) {
            //do suitable action such as call to start other activity //and return true;
            Intent i= new Intent(AdminOptions.this, AddItems.class);
            startActivity(i);
        }
        if (id == R.id.action_DisplayStockItems) {
            //do suitable action such as call to start other activity //and return true;
            Intent i= new Intent(AdminOptions.this, DisplayItems.class);
            startActivity(i);
        }
        if (id == R.id.action_viewCustomerDetails) {
            //do suitable action such as call to start other activity //and return true;
            Intent i= new Intent(AdminOptions.this, ViewCustomerDetails.class);
            startActivity(i);
        }
        return true;
    }


}