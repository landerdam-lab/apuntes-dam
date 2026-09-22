package com.example.diseobesos;

import android.os.AsyncTask;

// Una AsyncTask es una clase que ejecuta trabajo LARGO en un hilo aparte (en segundo
// plano), para no bloquear la pantalla mientras tanto, y avisa a la pantalla principal
// cuando hay progreso nuevo o cuando termina. "extends AsyncTask<Void,Integer,Void>"
// hereda ese comportamiento (los tres tipos entre <> son detalles de qué datos maneja).
public class ProgressAndando extends AsyncTask<Void,Integer,Void> {

    // Se guarda una referencia a la pantalla (Activity) para poder actualizar sus vistas
    private EjemploAsynctask activity;
    public ProgressAndando(EjemploAsynctask activity) {
        this.activity = activity;
    }

    //CONTROL DE LOS TIEMPOS (EL HILO)
    // Este método se ejecuta EN SEGUNDO PLANO (fuera de la pantalla principal).
    // Aquí es seguro "esperar" sin congelar la app.
    @Override
    protected Void doInBackground(Void... voids) {
        int foto = 0;
        // Bucle de 100 pasos: en cada vuelta, espera un poco y avisa del progreso
        for (int i = 0; i < 100; i++) {
            foto++;
            if(foto >= 3){
                foto=0; // solo hay 3 fotogramas (0,1,2), así que se reinicia el ciclo
            }
            try {
                Thread.sleep(200); // pausa de 200 milisegundos antes de la siguiente vuelta
                publishProgress(foto,i); // avisa a la pantalla principal: "aquí va el fotograma y el porcentaje actuales"
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    //ESTE METODO SE DESENCADENA CUANDO TERMINA LA EJECUCION DEL HILO (RETURN)
    // Se ejecuta en la pantalla principal, cuando el bucle de arriba ha terminado del todo
    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        activity.finish(); // cierra automáticamente la pantalla de la animación
    }

    //ANTES DE LA EJECUCION
    // Se ejecuta en la pantalla principal, justo antes de arrancar el trabajo en segundo plano
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //LOS VALORES QUE NOS PASA EL HILO PRINCIPAL
    // Se ejecuta en la pantalla principal cada vez que publishProgress(...) se llama arriba.
    // Aquí SÍ es seguro tocar las vistas (imagen, barra, texto).
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        // values[0] = número de fotograma a mostrar ; values[1] = porcentaje actual
        activity.getImageCentral().setImageResource(
                activity.getImagenes().getResourceId(values[0], -1)
        );
        activity.getBarra().setProgress(values[1]);
        activity.getTexto().setText(values[1]+"%");
    }
}
