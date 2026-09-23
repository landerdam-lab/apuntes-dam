package com.example.ejerciciofragmentos.adaptadores;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ejerciciofragmentos.R;
import com.example.ejerciciofragmentos.modelo.Persona;

import java.util.ArrayList;

public class AdaptadorPersona extends BaseAdapter {
    private ArrayList<Persona> personas = new ArrayList<>();
    private Context context;

    public AdaptadorPersona(ArrayList<Persona> personas, Context context) {
        this.personas = personas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return personas.size();
    }

    @Override
    public Object getItem(int position) {
        return personas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View fila = inflater.inflate(R.layout.item_persona, parent, false);
        TextView tvNombre = fila.findViewById(R.id.itNombre);
        TextView tvApellido = fila.findViewById(R.id.itApellido);
        TextView tvFecha = fila.findViewById(R.id.itFecha);

        tvNombre.setText(personas.get(position).getNombre());
        tvApellido.setText(personas.get(position).getApellido());
        tvFecha.setText(personas.get(position).getFecha());

        return fila;
    }
}
