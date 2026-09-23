package com.example.ejemplodialogopersonalizado.bbdd;

import android.content.Context;
import android.util.Log;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ejemplodialogopersonalizado.model.Usuario;

/*
 * BASE DE DATOS
 * Es la clase principal de Room. Con @Database le decimos:
 *  - entities: que tablas tiene (aqui solo Usuario)
 *  - version: version de la BD (si cambias las tablas hay que subirla)
 *  - exportSchema = false: no guardar un fichero JSON con el esquema
 */
@Database(entities = {Usuario.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase  //abstract: no se puede instanciar directamente, Room crea la implementacion
{
    //Etiqueta para los mensajes de Log (se ven en la pestana Logcat)
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    //Nombre del fichero de la base de datos en el movil
    private static final String DATABASE_NAME = AppDatabase.class.getSimpleName();
    //Objeto que usamos como "candado" para que dos hilos no creen la BD a la vez
    private static final Object LOCK = new Object();
    //La unica instancia de la BD. Patron SINGLETON: solo existe un objeto en toda la app
    private static AppDatabase sInstance;

    //Metodo para obtener la BD desde cualquier sitio: AppDatabase.getInstance(context)
    public static AppDatabase getInstance(Context context)
    {
        //Solo la creamos si todavia no existe
        if (sInstance == null)
        {
            synchronized (LOCK) //Solo un hilo a la vez puede entrar aqui dentro
            {
                Log.d(LOG_TAG, "Creando la base de datos");
                //Room.databaseBuilder construye la BD. getApplicationContext() evita fugas de memoria
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    //Metodo abstracto: Room lo implementa y nos devuelve el DAO para hacer consultas
    public abstract UsuariosDao usuariosDao();
}
