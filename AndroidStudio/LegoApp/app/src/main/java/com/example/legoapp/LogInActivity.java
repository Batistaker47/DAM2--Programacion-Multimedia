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
    /**
     * Changes the current activity to the register page.
     *
     * @param view The View that triggered this method (e.g., a button click)
     */
    public void changeToRegisterView(View view) {
        startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
    }
    /**
     * Changes the current activity to the forgot password page.
     *
     * @param view The View that triggered this method (e.g., a button click)
     */
    public void changeToForgotPasswordView(View view) {
        startActivity(new Intent(LogInActivity.this, ForgotPasswordActivity.class));
    }
    /**
     * Log the user in the main application
     *
     * @param view The View that triggered this method (e.g., a button click)
     */
    public void logIn(View view) {
        // GET AN INSTANCE OF THE FIREBASE FIRESTORE DATABASE
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        // GET REFERENCES TO THE EDIT TEXTS IN THE LAYOUT
        TextView nickname = findViewById(R.id.editTextNicknameSignIn);
        TextView password = findViewById(R.id.editTextPasswordSignIn);

        // CHECK IF ANY FIELD IS EMPTY
        if (password.getText().toString().isEmpty() || nickname.getText().toString().isEmpty()) {
            Toast.makeText(LogInActivity.this, "Complete all the fields", Toast.LENGTH_LONG).show();
        } else {

            // GET ALL USERS FROM THE "USERS" COLLECTION
            database.collection("users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                // SUCCESSFUL DATA RETRIEVAL
                                // FLAG TO CHECK IF USER IS FOUND
                                boolean userCheck = false;
                                // LOOP THROUGH EACH USER DOCUMENT
                                for (QueryDocumentSnapshot doc : task.getResult()) {

                                    if (    // CHECK IF NICKNAME AND PASSWORD MATCH
                                            doc.getId().equals(nickname.getText().toString()) &&
                                            doc.get("password").toString().equals(password.getText().toString()))
                                    {

                                        // GET THE CURRENT USER WITH SINGLETON INSTANCE
                                        Singleton.getInstance().setCurrentUser(doc.getId());
                                        userCheck = true;

                                        Toast.makeText(LogInActivity.this, "Welcome, " + nickname.getText().toString() + "!", Toast.LENGTH_LONG).show();

                                        // CHANGE TO MAINPAGE
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
}