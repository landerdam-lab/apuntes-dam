package com.example.ejemplodialogopersonalizado.ej;

import android.os.AsyncTask;

// AsyncTask<Params, Progress, Result>: aqui no hay parametros (Void), el progreso son Integer y no devuelve nada (Void)
public class ProgressAndando extends AsyncTask<Void, Integer, Void> {

    private Ej42AsyncActivity activity;

    public ProgressAndando(Ej42AsyncActivity activity) {
        this.activity = activity;
    }

    // Se ejecuta en un HILO SECUNDARIO (aqui NO se tocan las vistas)
    @Override
    protected Void doInBackground(Void... voids) {
        int foto = 0;
        for (int i = 0; i < 40; i++) {
            foto++;
            if (foto >= 4) {
                foto = 0;           // hay 4 fotogramas (0,1,2,3): se reinicia el ciclo
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(foto, i);   // avisa al hilo principal: (fotograma, porcentaje)
        }
        return null;
    }

    // Se ejecuta en el HILO PRINCIPAL cada vez que se llama a publishProgress: aqui SI se tocan las vistas
    @Override
    protected void onProgressUpdate(Integer... values) {
        activity.getImageCentral().setImageResource(activity.getImagenes().getResourceId(values[0], -1));
        activity.getBarra().setProgress(values[1] * 100 / 40);
        activity.getTexto().setText(values[1] * 100 / 40 + "%");
    }

    // Se ejecuta en el HILO PRINCIPAL cuando doInBackground termina
    @Override
    protected void onPostExecute(Void unused) {
        activity.finish();
    }
}
