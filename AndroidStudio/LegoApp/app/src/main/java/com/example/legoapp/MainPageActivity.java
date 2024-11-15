package com.example.legoapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout legoContainerNew;
    private LinearLayout legoContainerRetired;

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;
    private Toolbar tool_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page);

        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.navigationView);
        tool_bar = findViewById(R.id.tool_bar);

        // TOOL BAR SETTINGS
        setSupportActionBar(tool_bar);

        // NAV MENU
        // HIDE ELEMENTS IN THE NAVIGATION MENU
        //Menu menu = navigationView.getMenu();
        //menu.findItem(R.id.nav_profile).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, tool_bar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        legoContainerNew = findViewById(R.id.legoContainerNew);
        legoContainerRetired = findViewById(R.id.legoContainerRetired);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("sets/categories/Star Wars")
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
                        loadNewSets(sets);
                    }

                });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        database.collection("sets/categories/descatalogados")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        List<Product> retiredSets = new ArrayList<>();
                        for (DocumentSnapshot document : documents) {
                            Product set = document.toObject(Product.class);
                            if (set != null) {
                                retiredSets.add(set);
                            }
                        }
                        loadRetiredSets(retiredSets);
                    }

                });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void changeLogInView(View view) {
        startActivity(new Intent(MainPageActivity.this, LogInActivity.class));
    }

    @SuppressLint("DefaultLocale")
    public void loadNewSets(List<Product> products) {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Product product : products) {
            View sets_card = inflater.inflate(R.layout.sets_card, legoContainerNew, false);
            ImageView setImage = sets_card.findViewById(R.id.set_image);
            TextView setName = sets_card.findViewById(R.id.set_name);
            TextView setPrice = sets_card.findViewById(R.id.set_prize);
            TextView setPieces = sets_card.findViewById(R.id.set_pieces);

            Glide.with(this)
                    .load(product.getImage())
                    .into(setImage);

            setName.setText(product.getName());
            setPrice.setText(String.format("%.2f €", product.getPrize()));
            setPieces.setText(String.format(product.getPieces() + ""));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int margin = getResources().getDimensionPixelSize(R.dimen.product_card_margin);
            params.setMargins(margin, margin, margin, margin);
            sets_card.setLayoutParams(params);

            legoContainerNew.addView(sets_card);
            sets_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainPageActivity.this, SetDetailActivity.class);
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

    @SuppressLint("DefaultLocale")
    public void loadRetiredSets(List<Product> products) {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Product product : products) {
            View sets_card = inflater.inflate(R.layout.sets_card, legoContainerRetired, false);

            ImageView setImage = sets_card.findViewById(R.id.set_image);
            TextView setName = sets_card.findViewById(R.id.set_name);
            TextView setPrice = sets_card.findViewById(R.id.set_prize);
            TextView setPieces = sets_card.findViewById(R.id.set_pieces);

            // A partir de aqui se hacen los setters en el horizontal layout
            Glide.with(this)
                    .load(product.getImage())
                    .into(setImage);

            setName.setText(product.getName());
            setPrice.setText(String.format("%.2f €", product.getPrize()));
            setPieces.setText(String.format(product.getPieces() + ""));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int margin = getResources().getDimensionPixelSize(R.dimen.product_card_margin);
            params.setMargins(margin, margin, margin, margin);
            sets_card.setLayoutParams(params);

            legoContainerRetired.addView(sets_card);
            sets_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainPageActivity.this, SetDetailActivity.class);
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

    public void viewMoreNewSets(View view) {
        Intent intent = new Intent(MainPageActivity.this, InventoryActivity.class);
        Intent id = intent.putExtra("viewMoreNewSetsId", 1);
        startActivity(intent);
    }
    public void viewMoreRetiredSets(View view) {
        Intent intent = new Intent(MainPageActivity.this, InventoryActivity.class);
        Intent id = intent.putExtra("viewMoreRetiredSetsId", 2);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        if (itemId == R.id.nav_home) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else if(itemId == R.id.nav_sets) {
            viewMoreNewSets(MainPageActivity.this.getCurrentFocus());

        } else if(itemId == R.id.nav_profile) {
            Intent intent = new Intent(MainPageActivity.this, ProfileActivity.class);
            startActivity(intent);

        } else if(itemId == R.id.nav_login) {
            Intent intent = new Intent(MainPageActivity.this, LogInActivity.class);
            startActivity(intent);
        }

        else if(itemId == R.id.nav_register) {
            Intent intent = new Intent(MainPageActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

        return true;
    }
}