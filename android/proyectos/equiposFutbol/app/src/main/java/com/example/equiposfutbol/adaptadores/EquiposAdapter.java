package com.example.equiposfutbol.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.equiposfutbol.R;
import com.example.equiposfutbol.model.Equipos;

import java.util.ArrayList;

// Adapter que traduce cada objeto Equipos en una fila visual del ListView de la pantalla principal
// (ver conceptos/07-adaptadores.md). Este sí funciona correctamente.
public class EquiposAdapter extends BaseAdapter {
    private Context context;

    private ArrayList<Equipos> equipos;

    // Constructor: guarda el contexto (necesario para inflar layouts) y los datos a mostrar.
    public EquiposAdapter(Context context, ArrayList<Equipos> equipos) {
        this.context = context;
        this.equipos = equipos;
    }


    // ¿Cuántas filas hay que dibujar?
    @Override
    public int getCount() {
        return this.equipos.size();
    }

    // El objeto Equipos que corresponde a una posición concreta de la lista.
    @Override
    public Equipos getItem(int position) {
        return equipos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // Construye la fila visual de un equipo concreto: infla el layout "molde" y lo rellena con los
    // datos de ese equipo (ver conceptos/07-adaptadores.md §2).
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View fila = inflater.inflate(R.layout.equipos_list,parent,false);

        Equipos equipo = this.equipos.get(position);
        ImageView ivLogo = fila.findViewById(R.id.iconoEquipo);
        TextView tvTelegono = fila.findViewById(R.id.tvNombreEquipo);   // nombre de variable con errata (debería ser algo como tvNombreEquipo/tvTelefono), sin efecto real
        TextView tvPrecio = fila.findViewById(R.id.tvValorEquipo);

        // Aquí el logo SÍ es una imagen guardada dentro del propio proyecto (un id de R.drawable),
        // por eso se usa setImageResource en vez de Picasso/Glide (comparar con EjercicioAdaptadoresFinal,
        // donde el logo era una URL).
        ivLogo.setImageResource(equipo.getLogo());
        tvTelegono.setText(equipo.getNombre());
        tvPrecio.setText(equipo.getValorEquipo()+"");

        return fila;
    }
}
