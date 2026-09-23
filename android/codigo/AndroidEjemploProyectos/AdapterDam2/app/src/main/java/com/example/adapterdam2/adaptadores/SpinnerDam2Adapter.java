package com.example.adapterdam2.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.adapterdam2.R;

public class SpinnerDam2Adapter extends ArrayAdapter<String> {
    private Context mContext;
    private String[] datos;
    public SpinnerDam2Adapter(@NonNull Context context, int resource,String[] datos) {
        super(context, resource, datos);
        this.mContext=context;
        this.datos=datos;
    }

    //numero de vistas(siempre la longitud del array)
    @Override
    public int getCount() {
        return datos.length + 1;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return vistaPersonalizada(position, convertView, parent);
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return datos[position];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return vistaPersonalizada(position, convertView, parent);
    }

    //metodo para cargar los datos
    private View vistaPersonalizada (int position, @Nullable View convertView, @NonNull ViewGroup parent){
        //Crear el cargador de layout
        LayoutInflater inflater= ((Activity)mContext).getLayoutInflater();
        //Cargar la vista del adaptador
        View fila=inflater.inflate(R.layout.spinner_per,parent,false);
        //cargar los datos
        TextView tvSpinner= fila.findViewById(R.id.tvSpinner);
        if (position == 0) {
            tvSpinner.setText("Selecione una opción:");
            fila.setBackgroundColor(Color.BLUE);
        }else {
            //cargar el valor
            tvSpinner.setText(datos[position-1]);
            if (position % 2 == 0) {
                fila.setBackgroundColor(Color.GRAY);
            }
        }
        //retornamos la vista
        return fila;
    }


}
