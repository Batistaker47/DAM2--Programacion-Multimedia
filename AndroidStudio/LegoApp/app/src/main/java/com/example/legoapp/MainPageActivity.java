package com.example.legoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
/**
 * The main activity of the application, displaying new and retired LEGO sets.
 * Implements `NavigationView.OnNavigationItemSelectedListener` to handle navigation menu clicks.
 */
public class MainPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // STORE THE CURRENT USER RETRIEVED FROM SINGLETON
    private String currentUser;

    // LINEARLAYOUTS TO HOLD NEW AND RETIRED LEGO SETS
    private LinearLayout legoContainerNew;
    private LinearLayout legoContainerRetired;

    // REFERENCE TO THE DRAWER LAYOUT AND NAVIGATION VIEW FOR NAVIGATION MENU
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page);

        // GET THE CURRENT USER FROM SINGLETON
        currentUser = Singleton.getInstance().getCurrentUser();

        // REFERENCES TO NAVIGATION VIEW, TOOLBAR, AND DRAWER LAYOUT
        NavigationView navigationView;
        Toolbar tool_bar;

        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.navigationView);
        tool_bar = findViewById(R.id.tool_bar);

        // SET THE TOOLBAR AS THE ACTION BAR FOR THIS ACTIVITY
        setSupportActionBar(tool_bar);

        // NAVIGATION MENU CONFIGURATION
        // HIDE THE "REGISTER" MENU ITEM AS IT'S NOT RELEVANT ON THE MAIN PAGE
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_register).setVisible(false);

        // BRING THE NAVIGATION VIEW TO FRONT FOR PROPER DISPLAY
        navigationView.bringToFront();

        // SET UP THE HAMBURGER MENU TOGGLE FOR NAVIGATION DRAWER
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, tool_bar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);

        // SYNCHRONIZE THE TOGGLE STATE
        toogle.syncState();

        // SET THIS ACTIVITY AS THE LISTENER FOR NAVIGATION MENU ITEM SELECTIONS
        navigationView.setNavigationItemSelectedListener(this);

        // REFERENCES TO THE LINEARLAYOUTS FOR DISPLAYING SETS
        legoContainerNew = findViewById(R.id.legoContainerNew);
        legoContainerRetired = findViewById(R.id.legoContainerRetired);

        // FIREBASESTORESTORE INSTANCE FOR FETCHING DATA
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        // LOAD NEW LEGO SETS FROM "STAR WARS" CATEGORY
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

        // LOAD RETIRED LEGO SETS FROM RETIRED CATEGORY
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
    /**
     * Changes the current activity to the login page.
     *
     * @param view The View that triggered this method (e.g., a button click)
     */
    public void changeLogInView(View view) {
        startActivity(new Intent(MainPageActivity.this, LogInActivity.class));
    }
    /**
     * Loads a list of new LEGO sets into the horizontal scroll view .
     *
     * @param products The list of LEGO set products to display.
     */
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
    /**
     * Loads a list of retired LEGO sets into the horizontal scroll view .
     *
     * @param products The list of LEGO set products to display.
     */
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

    /**
     * Handles clicks on the "View More New Sets" button.
     * Starts the InventoryActivity with an extra indicating to load new sets.
     *
     * @param view The View that triggered this method (e.g., a button click)
     */
    public void viewMoreNewSets(View view) {
        Intent intent = new Intent(MainPageActivity.this, InventoryActivity.class);
        Intent id = intent.putExtra("viewMoreNewSetsId", 1);
        startActivity(intent);
    }
    /**
     * Handles clicks on the "View More Retired Sets" button.
     * Starts the InventoryActivity with an extra indicating to load retired sets.
     *
     * @param view The View that triggered this method (e.g., a button click)
     */
    public void viewMoreRetiredSets(View view) {
        Intent intent = new Intent(MainPageActivity.this, InventoryActivity.class);
        Intent id = intent.putExtra("viewMoreRetiredSetsId", 2);
        startActivity(intent);
    }

    /**
     * Handles clicks on navigation menu items.
     *
     * @param menuItem The selected menu item.
     * @return true to consume the navigation event, false otherwise.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        if (itemId == R.id.nav_home) {
            drawerLayout.closeDrawer(GravityCompat.START);

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
        else if(itemId == R.id.nav_prueba) {
            String url = "https://www.lego.com/es-es";
            Uri link = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW,link);
            startActivity(intent);
        }

        return true;
    }
}