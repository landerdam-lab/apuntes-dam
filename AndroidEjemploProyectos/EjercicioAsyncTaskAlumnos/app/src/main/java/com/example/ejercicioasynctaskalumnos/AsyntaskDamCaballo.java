package com.example.ejercicioasynctaskalumnos;

import android.os.AsyncTask;
import android.widget.Toast;

public class AsyntaskDamCaballo extends AsyncTask<Void,Integer,Void> {
    AsyntaskDamActivity activity;
    public AsyntaskDamCaballo(AsyntaskDamActivity asyntaskDamActivity) {
        this.activity=asyntaskDamActivity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int numFoto=0;
        for (int i = 0; i < 100; i++) {
            numFoto++;
            if (numFoto >= 7) {
                numFoto=0;
            }
            try {
                Thread.sleep(50);
                publishProgress(numFoto,i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        activity.getPbCaballo().setProgress(values[1]);
        activity.getTvCaballo().setText(values[1]+"%");
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        activity.finish();
        Toast.makeText(activity.getApplicationContext(), "Adios", Toast.LENGTH_SHORT).show();
    }
}
