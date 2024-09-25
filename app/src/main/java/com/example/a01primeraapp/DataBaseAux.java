package com.example.a01primeraapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/*La clase SQLiteOpenHelper es una clase abstracta, por lo que nos pedirá importar sus métodos
* Tambien creamos el constructor, que es el que lleva una variable version.*/
public class DataBaseAux extends SQLiteOpenHelper {
    //Creamos un atributo constante con el nombre de la BBDD,
    static final String DB_NAME = "Personas";
    static final int DB_VERSION = 1;

    // El constructor lo maquetamos de la siguiente manera
    public DataBaseAux(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Este método crea una BBDD del tirón
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Función para ejecutar SQL
        db.execSQL("CREATE TABLE personas (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
