package com.example.legoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class SetDetailActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_detail);

        TextView tvSetNameDetail = findViewById(R.id.tvSetNameDetail);
        TextView tvSetPrizeDetail = findViewById(R.id.tvSetPrizeDetail);
        TextView tvSetPiecesDetail = findViewById(R.id.tvSetPiecesDetail);
        TextView tvSetDescription = findViewById(R.id.tvSetDescriptionDetail);
        ImageView imageSetDetail = findViewById(R.id.imageSetDetail);

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

    public void changeToMainView(View view) {
        startActivity(new Intent(SetDetailActivity.this, MainPageActivity.class));
    }

}