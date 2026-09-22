package com.example.ejerciciodiseno;

import android.os.AsyncTask;

// AsyncTask: una CLASE especial de Android (ver conceptos/05-asynctask-e-hilos.md) que permite
// ejecutar algo que tarda (aquí, un bucle de 20 segundos) SIN congelar la pantalla, y avisar
// al hilo principal cada vez que hay que actualizar algo visible.
// "extends AsyncTask<Void, Integer, Void>" (herencia, ver 00-programacion-basica.md §6): Void = no recibe
// datos de entrada, Integer = el tipo del progreso que va publicando, Void = no devuelve nada al terminar.
public class ProgresoCaballo extends AsyncTask <Void, Integer, Void> {
    private Caballo activity; // referencia a la pantalla que lanzó esta tarea, para poder actualizar sus vistas

    // CONSTRUCTOR (ver 00-programacion-basica.md §5): guarda la Activity que se le pasa al crear la tarea.
    public ProgresoCaballo(Caballo activity) {
        this.activity = activity;
    }

    // Se ejecuta en un HILO SECUNDARIO (nunca en el de la pantalla) — aquí SÍ se puede "esperar" sin
    // congelar nada. Cuenta 100 pasos, uno cada 200ms, cambiando el número de fotograma (0,1,2 en bucle).
    //CONTROL DE LOS TIEMPOS (EL HILO)
    @Override
    protected Void doInBackground(Void... voids) {
        int foto = 0;
        for (int i = 0; i < 100; i++) {
            foto++;
            if(foto >= 3){
                foto=0;
            }
            try {
                Thread.sleep(200); // pausa 200 milisegundos (permitido aquí porque NO es el hilo principal)
                publishProgress(foto,i); // avisa al hilo principal: "actualiza la pantalla con estos valores"
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    // Se ejecuta en el HILO PRINCIPAL cuando doInBackground() termina del todo (tras los 100 pasos).
    //ESTE METODO SE DESENCADENA CUANDO TERMINA LA EJECUCION DEL HILO (RETURN)
    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        activity.finish(); // cierra la pantalla del caballo, volviendo automáticamente al menú
    }

    // Se ejecuta en el HILO PRINCIPAL justo antes de arrancar doInBackground(). Aquí no hace nada,
    // pero es el sitio donde se prepararía la interfaz si hiciera falta.
    //ANTES DE LA EJECUCION
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // Se ejecuta en el HILO PRINCIPAL cada vez que doInBackground() llama a publishProgress(...).
    // Aquí SÍ es seguro tocar vistas: cambia la imagen, avanza la barra y actualiza el texto "%".
    //LOS VALORES QUE NOS PASA EL HILO PRINCIPAL
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        activity.getImageCentral().setImageResource(
                activity.getImagenes().getResourceId(values[0], -1)
        );
        activity.getBarra().setProgress(values[1]);
        activity.getTexto().setText(values[1]+"%");
    }
}
