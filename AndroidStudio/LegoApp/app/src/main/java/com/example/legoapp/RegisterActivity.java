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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

    //ADD TO FIREBASE DDBB
    public void signUp(View view) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Map<String, Object> values = new HashMap<>();
        TextView nickname = findViewById(R.id.editTextNicknameReg);
        TextView email = findViewById(R.id.editTextEmailRegister);
        TextView password = findViewById(R.id.editTextPasswordReg);
        TextView confPassword = findViewById(R.id.editTextConfPasswordReg);


        // CONTROL DE ERRORES DE CAMPOS VACÍOS
        if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || nickname.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Complete all the fields", Toast.LENGTH_LONG).show();

            // CONTROL DE ERRORES DE CONTRASEÑAS DISTINTAS
        } else if (!password.getText().toString().equals(confPassword.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_LONG).show();

            //SI VA BIEN -->
        } else {
            values.put("nickname",nickname.getText().toString());
            values.put("email",email.getText().toString());
            values.put("password",password.getText().toString());

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
                                    if (doc.getId().equals(nickname.getText().toString())) {
                                        // Si existe
                                        userCheck = true;
                                        Toast.makeText(RegisterActivity.this,"User already exist",Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                }

                                if (!userCheck) {
                                        Toast.makeText(RegisterActivity.this,"User Created",Toast.LENGTH_LONG).show();
                                        database.collection("users").document(nickname.getText().toString()).set(values);
                                        startActivity(new Intent(RegisterActivity.this, MainPageActivity.class));
                                }
                            } else {
                                //No va el internet/bbdd caída (no me traigo datos)
                            }
                        }
                    });
/*
                    //.set(values)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(RegisterActivity.this,"New user created",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, MainPageActivity.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "ERROR", Toast.LENGTH_LONG).show();

                        }
                    });*/
        }
    }

}