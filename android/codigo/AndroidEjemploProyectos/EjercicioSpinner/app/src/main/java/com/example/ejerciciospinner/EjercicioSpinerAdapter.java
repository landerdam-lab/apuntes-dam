package com.example.ejerciciospinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EjercicioSpinerAdapter extends ArrayAdapter<ArrayList<String>> {

    private Context context;
    private ArrayList<String> datos;
    public EjercicioSpinerAdapter(Context context, int i, ArrayList<String> datos) {
        super(context,i);
        this.context=context;
        this.datos=datos;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return vistaPersonalizada(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return vistaPersonalizada(position, convertView, parent);
    }

    private View vistaPersonalizada(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater= ((Activity)this.context).getLayoutInflater();
        View fila= inflater.inflate(R.layout.item_spinner,parent,false);
        ((TextView)fila.findViewById(R.id.tvNumero)).setText(position + " - ");
        ((TextView)fila.findViewById(R.id.tvNombre)).setText(datos.get(position)+"");
        return fila;
    }

}
