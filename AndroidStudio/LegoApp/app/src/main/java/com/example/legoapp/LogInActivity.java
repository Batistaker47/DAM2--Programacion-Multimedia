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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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

    public void logIn(View view) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Map<String, Object> values = new HashMap<>();
        TextView nickname = findViewById(R.id.editTextNicknameSignIn);
        TextView password = findViewById(R.id.editTextPasswordSignIn);

        if (password.getText().toString().isEmpty() || nickname.getText().toString().isEmpty()) {
            Toast.makeText(LogInActivity.this, "Complete all the fields", Toast.LENGTH_LONG).show();
        } else {

            database.collection("users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                // Me traigo bien los datos, va bien el internet
                                // Por cada documento en el task (todos los datos del documento)
                                boolean userCheck = false;
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    // && database.collection("users").document("password").toString().equals(password.getText().toString())
                                    if (doc.getId().equals(nickname.getText().toString()) && doc.get("password").toString().equals(password.getText().toString())) {
                                        // Si existe y la contraseña coincide
                                        userCheck = true;
                                        Toast.makeText(LogInActivity.this, "Welcome back, " + nickname.getText().toString() + "!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(LogInActivity.this, MainPageActivity.class));
                                        break;
                                    }
                                }

                                if (!userCheck) {
                                    Toast.makeText(LogInActivity.this, "Wrong user or password", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                //No va el internet/bbdd caída (no me traigo datos)
                            }
                        }
                    });
            }
    }
}