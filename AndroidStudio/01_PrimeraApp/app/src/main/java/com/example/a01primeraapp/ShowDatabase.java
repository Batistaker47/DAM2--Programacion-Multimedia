package com.example.a01primeraapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.util.Map;

public class ShowDatabase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_showdatabase);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Llamamos al método de llenar el scrollView con los datos de la BBDD local
        //fillScrollView();
        fillScrollViewFB();
    }

    private void fillScrollViewFB() {
        LinearLayout ly = findViewById(R.id.LinearLayoutshowDB);
        ly.removeAllViews();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Accedemos a la BBBDD
        DocumentReference df = db.collection("users").document("batistaker47");
        Source src = Source.SERVER;
        //Para eliminar
        // df.delete();

        df.get(src).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot result = task.getResult();
                Map<String, Object> info = result.getData();
                Toast.makeText(ShowDatabase.this, "TODO OK", Toast.LENGTH_LONG).show();
                TextView infoView = new TextView(ShowDatabase.this);
                infoView.setText(info.get("username").toString());
                ly.addView(infoView);

            }
        });
    }


    private void fillScrollView() {
        LinearLayout layout = findViewById(R.id.LinearLayoutshowDB);
        DataBaseAux dbAux = new DataBaseAux(this);
        SQLiteDatabase database = dbAux.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM personas", null);
        // Vaciamos el layout para cuando borremos un registro
        layout.removeAllViews();

        // Si puedo mover el cursor al principio significa que no ha habido errores, es decir, que ha encontrado datos
        if (cursor.moveToFirst()) {
            // Hacemos un do/while en el cual decimos que realice todos los procesos pertinentes mientras que el cursor pueda seguir moviendose por los registros
            do {
                int idIndex = cursor.getColumnIndex("id");
                int id = cursor.getInt(idIndex);

                int nameIndex = cursor.getColumnIndex("name");
                String name = cursor.getString(nameIndex);

                TextView infoView = new TextView(this);
                infoView.setText("id: " + id + " Name: " + name);

                Button deleteButton = new Button(this);
                deleteButton.setText("Delete register " + id);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int rowsAfected = database.delete("personas", "id = " + id, null);
                        if (rowsAfected > 0) {
                            fillScrollView();
                        }
                    }
                });
                layout.addView(infoView);
                layout.addView(deleteButton);

            } while(cursor.moveToNext());
        }

    }
    public void changetoMain(View view) {
        startActivity(new Intent(ShowDatabase.this, MainActivity.class));
    }
}