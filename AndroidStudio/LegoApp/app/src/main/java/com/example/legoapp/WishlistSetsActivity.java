package com.example.legoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class WishlistSetsActivity extends AppCompatActivity {

    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wishlist_sets);

        currentUser = Singleton.getInstance().getCurrentUser();

        loadWishlist();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void loadWishlist() {
        LinearLayout ly = findViewById(R.id.LinearLayoutshowDBWish);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users/" + currentUser + "/wishlistSets")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        for (DocumentSnapshot document : documents) {
                            TextView infoView = new TextView(WishlistSetsActivity.this);
                            infoView.setTextSize(25);
                            infoView.setText(document.get("name").toString());
                            ly.addView(infoView);
                        }
                    }
                });
    }
    public void changeProfileView(View view) {
        startActivity(new Intent(WishlistSetsActivity.this, ProfileActivity.class));

    }
}