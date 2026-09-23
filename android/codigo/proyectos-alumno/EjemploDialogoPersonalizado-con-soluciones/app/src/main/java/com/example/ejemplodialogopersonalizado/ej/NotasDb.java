package com.example.ejemplodialogopersonalizado.ej;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Nota.class}, version = 1, exportSchema = false)
public abstract class NotasDb extends RoomDatabase  //abstract: no se puede instanciar, solo heredar
{
    private static final String LOG_TAG = NotasDb.class.getSimpleName();
    private static final String DATABASE_NAME = NotasDb.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static NotasDb sInstance; //Patron SINGLETON: solo hay una instancia

    public static NotasDb getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) //Solo un hilo a la vez
            {
                Log.d(LOG_TAG, "Creando la base de datos");
                sInstance = Room.databaseBuilder(context.getApplicationContext(), NotasDb.class, NotasDb.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract NotasDao notasDao();
}
