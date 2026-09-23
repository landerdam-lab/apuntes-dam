package com.example.disenyopesos;

import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProgressAndando  extends AsyncTask <Void,Integer,Void>
{
    private EjemploAsynctask activity;
    public ProgressAndando(EjemploAsynctask activity) {

       this.activity = activity;
    }
    //Control de los tiempos (El Hilo)
    @Override
    protected Void doInBackground(Void... voids) {
        int foto = 0;
        for (int i = 0; i < 100; i++) {
            foto++;
            if (foto >= 3) {
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
    // Este metodo se desencadena cuando termina la ejecucion del hilo (return).
    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        activity.finish();
    }

   // Antes de la ejecucion
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    //Los valores que nos pasa el hilo principal
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        activity.getImagenCentral().setImageResource(
                activity.getImagenes().getResourceId(values[0],-1)
        );
        activity.getBarra().setProgress(values[1]);
        activity.getTexto().setText(values[1]+"%");
    }
}
