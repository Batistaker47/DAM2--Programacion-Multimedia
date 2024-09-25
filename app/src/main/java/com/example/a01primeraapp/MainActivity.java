package com.example.a01primeraapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        db.insert("personas", null, values);
        
        /*Sacamos un mensaje pop-up por pantalla con Toast.makeText
        En esta funcion le pasamos en qué layout estamos, el mensaje que queremos que se muestre y el tiempo que queremos que esté el mensaje en pantalla*/
        Toast.makeText(MainActivity.this, "El nombre " + nameString + " ha sido añadido a la BBDD!", Toast.LENGTH_LONG).show();


    }
}