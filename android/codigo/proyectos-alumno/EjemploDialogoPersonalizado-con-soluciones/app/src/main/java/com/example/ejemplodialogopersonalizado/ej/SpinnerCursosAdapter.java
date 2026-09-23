package com.example.ejemplodialogopersonalizado.ej;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ejemplodialogopersonalizado.R;

public class SpinnerCursosAdapter extends ArrayAdapter<String> {

    private final Context contexto;
    private final String[] datos;

    public SpinnerCursosAdapter(Context context, int resource, String[] datos) {
        super(context, resource, datos);   // le pasamos los datos a la clase padre tambien
        this.contexto = context;
        this.datos = datos;
    }

    @Override
    public int getCount() {
        return datos.length + 1;    // +1: dejamos hueco para la fila "Selecciona una opcion"
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return vistaPersonalizada(position, parent);   // fila cuando el spinner esta CERRADO
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return vistaPersonalizada(position, parent);   // fila del desplegable ABIERTO
    }

    private View vistaPersonalizada(int position, ViewGroup parent) {
        View fila = LayoutInflater.from(contexto).inflate(R.layout.ej_spinner_fila, parent, false);
        TextView tvSpinner = fila.findViewById(R.id.tvSpinner);
        if (position == 0) {
            tvSpinner.setText("Selecciona una opcion");   // fila "falsa" 0, solo de aviso
            fila.setBackgroundColor(Color.LTGRAY);
        } else {
            tvSpinner.setText(datos[position - 1]);        // los datos reales, desplazados 1 posicion
        }
        return fila;
    }
}
