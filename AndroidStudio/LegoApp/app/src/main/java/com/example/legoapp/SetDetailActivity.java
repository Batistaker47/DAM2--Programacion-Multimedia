package com.example.legoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SetDetailActivity extends AppCompatActivity {

    private String currentUser;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_detail);


        // GET THE CURRENT USER FROM SINGLETON
        currentUser = Singleton.getInstance().getCurrentUser();

        // REFERENCE UI ELEMENTS FOR DISPLAYING SET DETAILS
        TextView tvSetNameDetail = findViewById(R.id.tvSetNameDetail);
        TextView tvSetPrizeDetail = findViewById(R.id.tvSetPrizeDetail);
        TextView tvSetPiecesDetail = findViewById(R.id.tvSetPiecesDetail);
        TextView tvSetDescription = findViewById(R.id.tvSetDescriptionDetail);
        ImageView imageSetDetail = findViewById(R.id.imageSetDetail);

        // EXTRACT SET DETAILS FROM THE INTENT THAT LAUNCHED THIS ACTIVITY
        Intent intent = getIntent();
        String setName = intent.getStringExtra("setName");
        Double setPrice = intent.getDoubleExtra("setPrize",0);
        Integer setPieces = intent.getIntExtra("setPieces",0);
        String setImage = intent.getStringExtra("setImage");
        String setDescription = intent.getStringExtra("setDescription");

        tvSetNameDetail.setText(setName);
        tvSetPrizeDetail.setText(setPrice + " €");
        tvSetPiecesDetail.setText(setPieces + " pieces");
        tvSetDescription.setText(setDescription);
        Glide.with(this)
                .load(setImage)
                .into(imageSetDetail);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    /**
     * Starts the MainPageActivity.
     *
     * @param view The View that triggered this method (e.g., a button click)
     */
    public void changeToMainView(View view) {
        startActivity(new Intent(SetDetailActivity.this, MainPageActivity.class));
    }

    /**
     * Adds the currently viewed set to the user's inventory in FirebaseFirestore.
     * Performs the following steps:
     * 1. Retrieves set information from the detail view elements (name, price, pieces, description).
     * 2. Creates a HashMap containing the set information.
     * 3. Queries the "mySets" subcollection within the current user's document in FirebaseFirestore.
     * 4. Checks if the set already exists in the user's inventory:
     *    - For each document in the "mySets" collection, it compares the document ID (set name) with the current set name.
     * 5. If the set doesn't exist:
     *    - Saves the set information as a new document in the "mySets" subcollection.
     *    - Displays a toast message indicating successful addition.
     * 6. If the set already exists:
     *    - Displays a toast message indicating the user already owns the set.
     * 7. Handles potential errors during data retrieval from Firestore.
     *
     * @param view The View that triggered this method (e.g., the "Add to Inventory" button)
     */
    public void addToInventory(View view) {

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Map<String, Object> values = new HashMap<>();

        TextView tvSetNameDetail = findViewById(R.id.tvSetNameDetail);
        TextView tvSetPrizeDetail = findViewById(R.id.tvSetPrizeDetail);
        TextView tvSetPiecesDetail = findViewById(R.id.tvSetPiecesDetail);
        TextView tvSetDescription = findViewById(R.id.tvSetDescriptionDetail);
        ImageView imageSetDetail = findViewById(R.id.imageSetDetail);

        values.put("name",tvSetNameDetail.getText().toString());
        values.put("prize",tvSetPrizeDetail.getText().toString());
        values.put("pieces",tvSetPiecesDetail.getText().toString());
        values.put("description",tvSetDescription.getText().toString());

        database.collection("users").document(currentUser).collection("mySets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Me traigo bien los datos, va bien el internet
                            // Por cada documento en el task (todos los datos del documento)
                            boolean flag = false;
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                if (doc.getId().equals(tvSetNameDetail.getText().toString())) {
                                    // Si ya existe el set en la colección
                                    flag = true;
                                    Toast.makeText(SetDetailActivity.this,"You already own this set!",Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }

                            if (!flag) {
                                Toast.makeText(SetDetailActivity.this,"Set added succesfully!",Toast.LENGTH_LONG).show();
                                database.collection("users").document(currentUser).collection("mySets").add(values);
                            }
                        } else {
                        }
                    }
                });

    }

    /**
     * Adds the currently viewed set to the user's wishlist in FirebaseFirestore.
     * Performs the following steps:
     * 1. Retrieves set information from the detail view elements (name, price, pieces, description).
     * 2. Creates a HashMap containing the set information.
     * 3. Queries the "wishlistSets" subcollection within the current user's document in FirebaseFirestore.
     * 4. Checks if the set already exists in the user's inventory:
     *    - For each document in the "mySets" collection, it compares the document ID (set name) with the current set name.
     * 5. If the set doesn't exist:
     *    - Saves the set information as a new document in the "wishlistSets" subcollection.
     *    - Displays a toast message indicating successful addition.
     * 6. If the set already exists:
     *    - Displays a toast message indicating the user already owns the set.
     * 7. Handles potential errors during data retrieval from Firestore.
     *
     * @param view The View that triggered this method (e.g., the "Add to Inventory" button)
     */
    public void addToWishlist(View view) {

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Map<String, Object> values = new HashMap<>();

        TextView tvSetNameDetail = findViewById(R.id.tvSetNameDetail);
        TextView tvSetPrizeDetail = findViewById(R.id.tvSetPrizeDetail);
        TextView tvSetPiecesDetail = findViewById(R.id.tvSetPiecesDetail);
        TextView tvSetDescription = findViewById(R.id.tvSetDescriptionDetail);
        ImageView imageSetDetail = findViewById(R.id.imageSetDetail);

        values.put("name",tvSetNameDetail.getText().toString());
        values.put("prize",tvSetPrizeDetail.getText());
        values.put("pieces",tvSetPiecesDetail.getText().toString());
        values.put("description",tvSetDescription.getText().toString());

        database.collection("users").document(currentUser).collection("wishlistSets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Me traigo bien los datos, va bien el internet
                            // Por cada documento en el task (todos los datos del documento)
                            boolean flag = false;
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                if (doc.getId().equals(tvSetNameDetail.getText().toString())) {
                                    // Si ya existe el set en la colección
                                    flag = true;
                                    Toast.makeText(SetDetailActivity.this,"You already add this set to the wishlist!",Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }

                            if (!flag) {
                                Toast.makeText(SetDetailActivity.this,"Set added succesfully!",Toast.LENGTH_LONG).show();
                                database.collection("users").document(currentUser).collection("wishlistSets").add(values);
                            }
                        } else {
                            //No va el internet/bbdd caída (no me traigo datos)
                        }
                    }
                });

    }

}