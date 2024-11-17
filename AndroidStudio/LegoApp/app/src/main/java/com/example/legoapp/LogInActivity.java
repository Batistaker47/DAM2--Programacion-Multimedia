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
                                    if (doc.getId().equals(nickname.getText().toString()) && doc.get("password").toString().equals(password.getText().toString())) {
                                        // Si existe y la contraseña coincide
                                        Singleton.getInstance().setCurrentUser(doc.getId());
                                        userCheck = true;

                                        Toast.makeText(LogInActivity.this, "Welcome, " + nickname.getText().toString() + "!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(LogInActivity.this, MainPageActivity.class));
                                        break;
                                    }
                                }

                                if (!userCheck) {
                                    Toast.makeText(LogInActivity.this, "Wrong user or password", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
            }
    }

   /* public void forgotPassword(View view) {
        EditText userName = findViewById(R.id.editTextNicknameSignIn);
        // Consulta en Firebase para obtener el documento del usuario por su correo electrónico
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String email = "";
                            boolean userCheck = false;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.getId().equals(userName.getText().toString())) {
                                        email = document.getString("email");
                                        Log.d("email",email);
                                            // Usuario encontrado y el correo electrónico coincide
                                            userCheck = true;
                                            Toast.makeText(LogInActivity.this, "Correo enviado", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            if (userCheck) {

                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                auth.sendPasswordResetEmail(email)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(LogInActivity.this, "REVISA TU CORREO PARA CAMBIAR LA CONTRASEÑA", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(LogInActivity.this, "ERROOOOOOOOOOOR", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            }
                        } else {
                            // Error en la consulta
                        }
                    }
                });
    }*/
}