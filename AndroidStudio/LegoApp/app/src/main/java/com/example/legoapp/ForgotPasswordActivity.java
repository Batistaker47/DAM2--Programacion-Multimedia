package com.example.legoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void changePassword(View view) {

        // GET AN INSTANCE OF THE FIREBASE FIRESTORE DATABASE
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        // CREATE A MAP TO STORE USER DATA
        Map<String, Object> values = new HashMap<>();

        // GET REFERENCES TO THE EDIT TEXTS IN THE LAYOUT
        TextView nickname = findViewById(R.id.editTextNicknameFP);
        TextView oldPassword = findViewById(R.id.editTextOldPassword);
        TextView newPassword = findViewById(R.id.editTextNewPasswordFP);
        TextView email = findViewById(R.id.editTextEmailFP);

        // CHECK IF ANY FIELD IS EMPTY
        if (
                email.getText().toString().isEmpty() ||
                oldPassword.getText().toString().isEmpty() ||
                newPassword.getText().toString().isEmpty() ||
                nickname.getText().toString().isEmpty())
        {
            Toast.makeText(ForgotPasswordActivity.this, "Complete all the fields", Toast.LENGTH_LONG).show();

        }  else {
            // ALL FIELDS ARE FILLED
            // ADD USER DATA TO THE MAP
            values.put("nickname",nickname.getText().toString());
            values.put("password",newPassword.getText().toString());
            values.put("email",email.getText().toString());

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
                                    if (
                                        // CHECK IF NICKNAME AND OLDPASSWORD MATCH
                                            doc.getId().equals(nickname.getText().toString()) &&
                                                    doc.get("password").toString().equals(oldPassword.getText().toString())) {
                                        // CHANGE FLAG UPDATE USER DATA WITH NEW PASSWORD
                                        userCheck = true;
                                        database.collection("users").document(nickname.getText().toString()).set(values);
                                        Toast.makeText(ForgotPasswordActivity.this, "Password successfully changed!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(ForgotPasswordActivity.this, LogInActivity.class));
                                        break;
                                    }
                                }

                                if (!userCheck) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Wrong user or password", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                // HANDLE ERRORS DURING DATA RETRIEVAL
                                Log.w("ForgotPassword", "ERROR GETTING DOCUMENTS.", task.getException());
                            }
                        }
                    });
        }
    }
}