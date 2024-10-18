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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void changeLogInView(View view) {
        startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
    }

    public void addToFirebase_DB(View view) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Map<String, Object> values = new HashMap<>();
        TextView email = findViewById(R.id.editTextEmailRegister);
        TextView password = findViewById(R.id.editTextPasswordReg);
        TextView nickname = findViewById(R.id.editTextNickname);

        values.put("email",email.getText().toString());
        values.put("password",password.getText().toString());

        // Añadir nuevo documento (habría que hacer un control de errores para comporbar si ya existe)
        database.collection("users").document(nickname.getText().toString())
                .set(values)
                //
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RegisterActivity.this,"New User Registered!",Toast.LENGTH_LONG).show();
                        changeMainView();
                    }

                    private void changeMainView() {
                        startActivity(new Intent(RegisterActivity.this, MainPageActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this,"ERROR",Toast.LENGTH_LONG).show();

                    }
                });
    }

}