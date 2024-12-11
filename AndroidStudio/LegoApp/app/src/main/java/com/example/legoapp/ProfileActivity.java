package com.example.legoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        currentUser = Singleton.getInstance().getCurrentUser();

        TextView userName = findViewById(R.id.tvProfileUserName);
        userName.setText(Singleton.getInstance().getCurrentUser());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    /**
     * Starts the LogInActivity and displays a toast message indicating successful logout.
     *
     * @param view The View that triggered this method (e.g., a button click)
     */
    public void changeLogInView(View view) {
        startActivity(new Intent(ProfileActivity.this, LogInActivity.class));
        Toast.makeText(ProfileActivity.this, "Log out succesfull!", Toast.LENGTH_LONG).show();

    }
    /**
     * Starts the MainPageActivity.
     *
     * @param view The View that triggered this method (e.g., a button click)
     */
    public void changeMainPageView(View view) {
        startActivity(new Intent(ProfileActivity.this, MainPageActivity.class));
    }
    /**
     * Starts the OwnedSetsActivity to display the user's owned sets.
     *
     * @param view The View that triggered this method (e.g., a button click)
     */
    public void showInventorySets(View view) {
        Intent intent = new Intent(this, OwnedSetsActivity.class);
        startActivity(intent);
    }
    /**
     * Starts the WishlistSetsActivity to display the user's wishlist.
     *
     * @param view The View that triggered this method (e.g., a button click)
     */
    public void showWishlistSets(View view) {
        Intent intent = new Intent(this, WishlistSetsActivity.class);
        startActivity(intent);
    }
}