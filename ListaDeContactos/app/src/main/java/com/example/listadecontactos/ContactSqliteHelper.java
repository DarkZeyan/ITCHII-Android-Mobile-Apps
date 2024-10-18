package com.example.listadecontactos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactSqliteHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    public ContactSqliteHelper(Context context){
        // Brindamos el contexto unicamente al constructor de la clase, los otros datos los pasamos por constantes
        // Para no ser hardcodeados en el codigo fuente
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Metodo onCreate para crear las tabla
    @Override
    public void onCreate(SQLiteDatabase db){
        // Crear la tabla de contactos
        String sql = "CREATE TABLE contacts(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "last_name TEXT," +
                "phone TEXT)";
        db.execSQL(sql);
    }

    // Metodo onUpgrade para actualizar la base de datos

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
     // Manejar la actualizacion de la base de datos
        if(oldVersion < newVersion){
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }
}
