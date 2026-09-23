package com.example.ejerciciopokemon.bbdd;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ejerciciopokemon.model.Pokemon;


@Database(entities = {Pokemon.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context)
    {
        if(sInstance == null)
        {
            synchronized (LOCK)
            {
                Log.d(LOG_TAG, "Creando la base de datos");
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                        AppDatabase.DATABASE_NAME).build();
            }
        }

        return sInstance;
    }

    public abstract PokemonsDao pokemonDao();

}
