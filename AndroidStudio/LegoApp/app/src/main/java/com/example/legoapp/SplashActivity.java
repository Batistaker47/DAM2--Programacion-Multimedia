package com.example.legoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // DELAY THE TRANSITION TO THE LogInActivity FOR 3 SECONDS
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // CREATE AN INTENT TO START THE LogInActivity
                Intent intent = new Intent(SplashActivity.this,LogInActivity.class);

                // STAR THE LogInActivity AND FINISH THE CURRENT SPLASH ACTIVITY
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}