package com.example.legoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {

    private LinearLayout legoContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
         setContentView(R.layout.activity_main_page);

        legoContainer = findViewById(R.id.legoContainer);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("sets/categories/Star Wars")
                .get()
                .addOnCompleteListener(task ->  {
                        if(task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            List<Product> sets = new ArrayList<>();
                            for (DocumentSnapshot document : documents) {
                                Product set = document.toObject(Product.class);
                                if (set != null) {
                                    //Log.d("Prize set", set.getName());
                                    sets.add(set);
                                }
                            }
                            loadNewSets(sets);
                        }

                });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    public void changeProfileView(View view) {
        startActivity(new Intent(MainPageActivity.this, ProfileActivity.class));
    }

    public void changeLogInView(View view) {
        startActivity(new Intent(MainPageActivity.this, LogInActivity.class));
    }

    @SuppressLint("DefaultLocale")
    public void loadNewSets(List<Product> products) {
        LayoutInflater inflater = LayoutInflater.from(this);

        for(Product product : products) {
            View sets_card = inflater.inflate(R.layout.sets_card, legoContainer, false );

            ImageView setImage = sets_card.findViewById(R.id.setImage);
            TextView setName = sets_card.findViewById(R.id.tvSetName);
            TextView setPrice = sets_card.findViewById(R.id.tvSetPrice);

            Glide.with(this)
                    .load(product.getImage())
                    .into(setImage);

            setName.setText(product.getName());
            setPrice.setText(String.format("%.2f €" , product.getPrize()));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int margin = getResources().getDimensionPixelSize(R.dimen.product_card_margin);
            params.setMargins(margin,margin,margin,margin);
            sets_card.setLayoutParams(params);

            legoContainer.addView(sets_card);


        }
    }
}