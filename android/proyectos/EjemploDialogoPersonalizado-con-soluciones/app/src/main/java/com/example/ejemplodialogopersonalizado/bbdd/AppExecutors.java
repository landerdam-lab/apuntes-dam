package com.example.ejemplodialogopersonalizado.bbdd;

import android.os.Looper;
import android.os.Handler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 * EJECUTORES DE HILOS
 * Android NO deja acceder a la base de datos desde el hilo principal (el de la pantalla),
 * porque la app se quedaria congelada. Esta clase nos da "hilos" (trabajadores en segundo plano)
 * donde ejecutar esas tareas. Ejemplo de uso:
 *     AppExecutors.getInstance().getDiskIO().execute(() -> { ...consulta a la BD... });
 */
public class AppExecutors
{
    private static final Object LOCK = new Object();
    //Unica instancia (patron SINGLETON, igual que en AppDatabase)
    private static AppExecutors sInstance;
    private final Executor diskIO;      //Para tareas de disco / base de datos
    private final Executor mainThread;  //Para ejecutar cosas en el hilo principal (la pantalla)
    private final Executor networkIO;   //Para tareas de red

    //Ejecutor que lanza el codigo en el hilo principal (el unico que puede tocar la interfaz)
    private static class MainThreadExecutor implements Executor
    {
        //Handler asociado al hilo principal
        private final Handler mainTreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            //post = "manda esta tarea al hilo principal"
            mainTreadHandler.post(command);
        }
    }

    //Constructor privado: nadie puede hacer "new AppExecutors()", solo usar getInstance()
    private AppExecutors(Executor diskIO, Executor mainThread, Executor networkIO)
    {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    //Devuelve la unica instancia, creandola la primera vez
    public static AppExecutors getInstance()
    {
        if(sInstance == null)
        {
            synchronized (LOCK)
            {
                //diskIO: un solo hilo (las operaciones de BD se hacen de una en una, en orden)
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(), new MainThreadExecutor(), Executors.newFixedThreadPool(3));
            }
        }
        return sInstance;
    }

    public Executor getDiskIO()
    {
        return this.diskIO;
    }

    public Executor getMainThread()
    {
        return this.mainThread;
    }

    public Executor getNetworkIO()
    {
        return this.networkIO;
    }
}
