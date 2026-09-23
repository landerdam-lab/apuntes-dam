package com.example.ejerciciodiseno;

import android.os.AsyncTask;

// Igual que ProgresoCaballo.java (misma técnica AsyncTask, ver conceptos/05-asynctask-e-hilos.md),
// pero sin barra de progreso: solo cambia la imagen de Pikachu cada 200ms durante 20 segundos.
public class AnimacionPikachu extends AsyncTask<Void, Integer, Void> {

    private Pikachu activity;

    public AnimacionPikachu(Pikachu activity) {

        this.activity = activity;
    }

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
                Thread.sleep(200);
                publishProgress(foto,i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    //ESTE METODO SE DESENCADENA CUANDO TERMINA LA EJECUCION DEL HILO (RETURN)
    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        activity.finish();
    }

    //ANTES DE LA EJECUCION
    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    //LOS VALORES QUE NOS PASA EL HILO PRINCIPAL
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        activity.getImageCentral().setImageResource(
                activity.getImagenes().getResourceId(values[0], -1)
        );

    }

}
