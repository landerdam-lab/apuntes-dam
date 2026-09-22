package com.example.ejerciciodialogopokemon.bbdd;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// Da "hilos" (trabajadores en segundo plano) listos para usar, para no bloquear la pantalla
// con operaciones de base de datos (ver conceptos/17-room-base-de-datos.md y 12-hilos-en-profundidad.md).
// Ejemplo de uso: AppExecutors.getInstance().getDiskIO().execute(() -> { ...consulta a la BD... });
public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;   // unica instancia (patron SINGLETON)
    private final Executor diskIO;      // para operaciones de disco / base de datos
    private final Executor mainThread;  // pensado para volver al hilo principal
    private final Executor networkIO;   // pensado para tareas de red

    // Ejecutor que manda el codigo al hilo principal (el unico que puede tocar la interfaz)
    private static class MainThreadExecutor implements Executor
    {
        private final Handler mainTreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainTreadHandler.post(command);
        }
    }

    // Constructor privado: nadie puede hacer "new AppExecutors()" desde fuera, solo getInstance()
    private AppExecutors(Executor diskIO, Executor mainThread, Executor networkIO)
    {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    public static AppExecutors getInstance()
    {
        if(sInstance == null)
        {
            synchronized (LOCK)
            {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), new MainThreadExecutor());
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
