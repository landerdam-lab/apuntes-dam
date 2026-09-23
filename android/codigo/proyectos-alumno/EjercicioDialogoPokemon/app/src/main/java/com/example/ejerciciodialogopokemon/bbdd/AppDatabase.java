package com.example.ejerciciodialogopokemon.bbdd;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ejerciciodialogopokemon.model.Pokemon;

// BASE DE DATOS: clase principal de Room (ver conceptos/17-room-base-de-datos.md).
// entities = {Pokemon.class} -> hay una sola tabla, la de Pokemon.
@Database(entities = {Pokemon.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase   // abstract: Room genera la implementacion real
{
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = AppDatabase.class.getSimpleName();
    // Candado para que dos hilos no puedan crear la BD a la vez
    private static final Object LOCK = new Object();
    // Unica instancia de la BD en toda la app (patron SINGLETON)
    private static AppDatabase sInstance;

    // Punto de entrada para conseguir la BD desde cualquier Activity: AppDatabase.getInstance(context)
    public static AppDatabase getInstance(Context context)
    {
        if(sInstance == null)
        {
            synchronized (LOCK)   // solo un hilo a la vez puede entrar aqui
            {
                Log.d(LOG_TAG, "Creando la base de datos");
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    // Metodo abstracto: Room lo implementa solo y nos da el DAO para hacer consultas.
    public abstract PokemonDao pokemonDao();
}
