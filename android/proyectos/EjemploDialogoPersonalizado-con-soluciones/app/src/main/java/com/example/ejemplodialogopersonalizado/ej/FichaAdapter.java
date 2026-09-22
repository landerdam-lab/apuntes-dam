package com.example.ejemplodialogopersonalizado.ej;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ejemplodialogopersonalizado.R;

import java.util.ArrayList;

public class FichaAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final ArrayList<Persona> listaPersonas;

    public FichaAdapter(Context context, ArrayList<Persona> listaPersonas) {
        this.inflater = LayoutInflater.from(context);
        this.listaPersonas = listaPersonas;
    }

    @Override
    public int getCount() {
        return listaPersonas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPersonas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;              // reciclaje de vistas
        if (vista == null) {
            vista = inflater.inflate(R.layout.ej_ficha_item, parent, false);
        }
        TextView tvNombre = vista.findViewById(R.id.tvNombre);
        TextView tvApellido = vista.findViewById(R.id.tvApellido);
        TextView tvFecha = vista.findViewById(R.id.tvFecha);

        Persona persona = listaPersonas.get(position);
        tvNombre.setText(persona.getNombre());
        tvApellido.setText(persona.getApellido());
        tvFecha.setText(persona.getFecha());
        return vista;
    }
}
