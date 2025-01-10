package com.example.a01primeraapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class FirstScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lmfirst_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

            Switch sw = findViewById(R.id.swDarkMode);
            if (sw != null) {
                // Use the switch here
            } else {
                // Handle the case where the view is not found
                // For example, log an error or show a toast to the user
                Log.e("MyActivity", "SwitchMaterial with id R.id.swDarkMode not found");
            }

            sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    enableDarkMode();
                } else {
                    disableDarkMode();
                }
            });
        });
    }
    private void enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        getDelegate().applyDayNight();
    }

    private void disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getDelegate().applyDayNight();
    }
}