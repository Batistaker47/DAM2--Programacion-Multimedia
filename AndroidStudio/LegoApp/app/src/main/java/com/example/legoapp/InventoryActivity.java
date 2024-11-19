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

public class InventoryActivity extends AppCompatActivity {

    // STORE THE CURRENT USER
    private String currentUser;

    // LINEAR LAYOUT TO HOLD LEGO SETS
    private LinearLayout legoLinearLayout;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventory);

        // GET THE CURRENT USER FROM SINGLETON CLASS
        currentUser = Singleton.getInstance().getCurrentUser();

        // GET THE INTENT THAT STARTED THIS ACTIVITY AND THE LAYOUT FILE BY THE ID
        Intent intent = getIntent();
        legoLinearLayout = findViewById(R.id.linearLayoutInventory);


        // HANDLE VIEW MORE NEW SETS INTENT
        if (intent.getIntExtra("viewMoreNewSetsId", 0) == 1) {
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection("sets/categories/Star Wars")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // SUCCESSFUL DATA RETRIEVAL
                            // CREATE A DOCUMENT SNAPSHOT LIST TO STORE ALL THE SET DOCUMENTS
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();

                            //CREATE A LIST TO ADD SETS
                            List<Product> sets = new ArrayList<>();

                            // LOOP THROUGH EACH SET DOCUMENT
                            for (DocumentSnapshot document : documents) {

                                // FORMAT EACH DOCUMENT TO OUR PRODUCT CLASS
                                Product set = document.toObject(Product.class);

                                // IF SET FOUNDED IS NOT NULL, ADD THE SET TO THE SET LIST
                                if (set != null) {
                                    sets.add(set);
                                }
                            }
                            // METHOD CALL
                            loadNewSets(sets);
                        }

                    });
        } else if (intent.getIntExtra("viewMoreRetiredSetsId",0) == 2) {
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                database.collection("sets/categories/descatalogados")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                List<Product> sets = new ArrayList<>();
                                for (DocumentSnapshot document : documents) {
                                    Product set = document.toObject(Product.class);
                                    if (set != null) {
                                        sets.add(set);
                                    }
                                }
                                loadRetiredSets(sets);
                            }

                        });

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }

    /**
     * Loads a list of new LEGO sets into the inventory layout.
     *
     * @param products The list of LEGO set products to display.
     */
    @SuppressLint("DefaultLocale")
    public void loadNewSets(List<Product> products) {
        // GET A LAYOUTINFLATER INSTANCE TO INFLATE VIEWS
        LayoutInflater inflater = LayoutInflater.from(this);

        // ITERATE THROUGH EACH PRODUCT IN THE LIST
        for (Product product : products) {
            // INFLATE THE INVENTORY CARD LAYOUT
            View sets_card = inflater.inflate(R.layout.inventory_card, legoLinearLayout, false);

            // GET REFERENCES TO THE VIEWS WITHIN THE CARD
            ImageView setImage = sets_card.findViewById(R.id.set_image);
            TextView setName = sets_card.findViewById(R.id.set_name);
            TextView setPrice = sets_card.findViewById(R.id.set_prize);
            TextView setPieces = sets_card.findViewById(R.id.set_pieces);

            // LOAD THE PRODUCT IMAGE USING GLIDE
            Glide.with(this)
                    .load(product.getImage())
                    .into(setImage);

            // SET THE TEXT FOR THE SET NAME, PRICE, AND PIECE COUNT
            setName.setText(product.getName());
            setPrice.setText(String.format("%.2f €", product.getPrize()));
            setPieces.setText(String.format(product.getPieces() + " pieces"));

            // SET LAYOUT PARAMETERS FOR THE CARD WITH MARGINS
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int margin = getResources().getDimensionPixelSize(R.dimen.product_card_margin);
            params.setMargins(margin, margin, margin, margin);
            sets_card.setLayoutParams(params);

            // ADD THE CARD TO THE LINEAR LAYOUT
            legoLinearLayout.addView(sets_card);

            // SET AN ONCLICKLISTENER FOR THE CARD TO OPEN THE SetDetailActivity
            sets_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // SAVE ALL THE DATA TO USED IT IN ANOTHER ACTIVITY
                    Intent intent = new Intent(InventoryActivity.this, SetDetailActivity.class);
                    Intent name = intent.putExtra("setName", product.getName());
                    Intent prize = intent.putExtra("setPrize", product.getPrize());
                    Intent pieces = intent.putExtra("setPieces", product.getPieces());
                    Intent image = intent.putExtra("setImage", product.getImage());
                    Intent description = intent.putExtra("setDescription", product.getDescription());
                    startActivity(intent);
                }
            });
        }
    }
    /**
     * Loads a list of retired LEGO sets into the inventory layout.
     *
     * @param products The list of LEGO set products to display.
     */
    @SuppressLint("DefaultLocale")
    public void loadRetiredSets(List<Product> products) {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Product product : products) {
            View sets_card = inflater.inflate(R.layout.inventory_card, legoLinearLayout, false);

            ImageView setImage = sets_card.findViewById(R.id.set_image);
            TextView setName = sets_card.findViewById(R.id.set_name);
            TextView setPrice = sets_card.findViewById(R.id.set_prize);
            TextView setPieces = sets_card.findViewById(R.id.set_pieces);

            Glide.with(this)
                    .load(product.getImage())
                    .into(setImage);

            setName.setText(product.getName());
            setPrice.setText(String.format("%.2f €", product.getPrize()));
            setPieces.setText(String.format(product.getPieces() + " pieces"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int margin = getResources().getDimensionPixelSize(R.dimen.product_card_margin);
            params.setMargins(margin, margin, margin, margin);
            sets_card.setLayoutParams(params);

            legoLinearLayout.addView(sets_card);
            sets_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InventoryActivity.this, SetDetailActivity.class);
                    Intent name = intent.putExtra("setName", product.getName());
                    Intent prize = intent.putExtra("setPrize", product.getPrize());
                    Intent pieces = intent.putExtra("setPieces", product.getPieces());
                    Intent image = intent.putExtra("setImage", product.getImage());
                    Intent description = intent.putExtra("setDescription", product.getDescription());
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * Changes the current activity to the main page.
     *
     * @param view The View that triggered this method (e.g., a button click)
     */
    public  void changeToMainFromAllSets(View view) {
        Intent intent = new Intent(InventoryActivity.this, MainPageActivity.class);
        startActivity(intent);
    }
}
