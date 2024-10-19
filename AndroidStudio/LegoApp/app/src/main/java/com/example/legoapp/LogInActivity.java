package com.example.legoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void changeToRegisterView(View view) {
        startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
    }

    /*public void changeToMainView(View view) {
        startActivity(new Intent(LogInActivity.this, MainPageActivity.class));
        Toast.makeText(this, "Welcome User", Toast.LENGTH_LONG).show();
    }*/

    public void logIn(View view) {
        TextView nickname = findViewById(R.id.editTextNicknameSignIn);
        TextView password = findViewById(R.id.editTextPasswordSignIn);

        if (password.getText().toString().isEmpty() || nickname.getText().toString().isEmpty()) {
            Toast.makeText(LogInActivity.this, "Complete all the fields", Toast.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(LogInActivity.this, MainPageActivity.class));
            Toast.makeText(this, "Welcome User", Toast.LENGTH_LONG).show();
        }
    }
}