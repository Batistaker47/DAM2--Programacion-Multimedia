package com.example.a01primeraapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {
    //Creamos una variable DataBaseAux, y la inicializamos más abajo
    DataBaseAux dbAux;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Aqui inicializamos la base de datos
        dbAux = new DataBaseAux(this);
        // ESTABLECER UN CALLBACK DESDE CÓDIGO
        Button changeToImage = findViewById(R.id.button_homeToActivityImage);

        // EL evento onClickListener nos vale para decir qué evento va a ocurrir cuando pulsemos el botón

        changeToImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sacamos un mensaje pop-up por pantalla con Toast.makeText
                Toast.makeText(MainActivity.this, "Pulsado!", Toast.LENGTH_SHORT).show();
            Log.d("debug","OKEY");
            startActivity(new Intent(MainActivity.this, imageActivity.class));
            }
        });
    }
    // Funcion para añadir datos a una base de datos relacional
    public void addToSQL_DB(View view) {
        //Accedemos al elemento Edit Text con un TextView, en la que accedemos al id del recuadro de texto
        TextView nameTextView = findViewById(R.id.nameTextView);

        // Guardamos en una variable el texto introducido en el recuadro
        String nameString = nameTextView.getText().toString();

        // Esto nos permite entrar a la bbdd creada en el databaseaux para poder escribir en ella
        SQLiteDatabase db = dbAux.getWritableDatabase();

        //Nos creamos un content values que le pasaremos despues a la db.insert
        ContentValues values = new ContentValues();
        values.put("name", nameString);

        // Hacemos un insert con los datos pasandole la varible value que tenemos completada arriba
        long result = db.insert("personas", null, values);

        /*Sacamos un mensaje pop-up por pantalla con Toast.makeText para decirle al usuario si se ha realizado la inserción con éxito
        En esta funcion le pasamos en qué layout estamos, el mensaje que queremos que se muestre y el tiempo que queremos que esté el mensaje en pantalla*/
        //Meteremos un control de errores

        if (result >= 0) {
            Toast.makeText(MainActivity.this, "El nombre " + nameString + " ha sido añadido a la BBDD!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "ERROR AL INSERTAR", Toast.LENGTH_LONG).show();

        }
    }
    // Este método nos permite leer información contenida en la BBDD de nuestro emulador
    public void readFromSQL (View view) {
        SQLiteDatabase db = dbAux.getReadableDatabase();
        // Para hacer un SELECT, se utiliza el método rawQuery, al que le pasamos la query a pelo
        Cursor cursor = db.rawQuery("SELECT * FROM personas WHERE id = 1", null);
        // Si puedo mover el cursor al principio significa que no ha habido errores, es decir, que ha encontrado datos
        if (cursor.moveToFirst()) {
            // Esto significa que no ha saltado ningún error, por lo que entramos en el if y guardamos en una variable int el índice de la columna que queramos sacar la info, en este caso del nombre
            int columnIndex = cursor.getColumnIndex("name");
            // Para sacar el texto contenido en dicha columna, utilizamos la función getString, a la que le pasamos el índice
            String rowName = cursor.getString(columnIndex);
            // Mostramos el String leído con un toast
            Toast.makeText(MainActivity.this, "El nombre de la fila 1 es: " + rowName, Toast.LENGTH_LONG).show();
        }

    }

    public void changeToInfoView(View view) {
        startActivity(new Intent (MainActivity.this, ShowDatabase.class));
    }

    public void addToFirebase_DB(View view) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Map<String, Object> values = new HashMap<>();
        TextView username = findViewById(R.id.nameTextView);
        TextView email = findViewById(R.id.mailTextView);
        TextView password = findViewById(R.id.passwordTextView);
        TextView nickname = findViewById(R.id.nicknameTextView);

        values.put("userame",username.getText().toString());
        values.put("email",email.getText().toString());
        values.put("password",password.getText().toString());

        // Añadir nuevo documento (habría que hacer un control de errores para comporbar si ya existe)
        database.collection("users").document(nickname.getText().toString())
                .set(values)
                //
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this,"Todo Ok",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"ERROR",Toast.LENGTH_LONG).show();

                    }
                });
    }
}