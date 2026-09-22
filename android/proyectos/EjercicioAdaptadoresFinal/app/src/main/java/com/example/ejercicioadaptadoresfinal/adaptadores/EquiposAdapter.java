package com.example.ejercicioadaptadoresfinal.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ejercicioadaptadoresfinal.R;
import com.example.ejercicioadaptadoresfinal.modelos.Equipo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// Un Adapter es el "traductor" entre una lista de datos (ArrayList<Equipo>) y las filas visuales que
// se ven de verdad en pantalla (ver conceptos/07-adaptadores.md). "extends BaseAdapter" hereda el
// comportamiento base de cualquier adaptador; aquí solo se rellenan los 4 métodos que faltan.
public class EquiposAdapter extends BaseAdapter {

    private ArrayList<Equipo> equipos;   // los datos: todos los equipos a mostrar
    private Context context;             // el "entorno" de Android necesario para inflar layouts (ver 00-programacion-basica.md / conceptos/10)

    // Constructor: rellena los atributos de arriba al crear el adaptador (ver 00-programacion-basica.md §5).
    public EquiposAdapter(Context context, ArrayList<Equipo> equipos) {
        this.context = context;
        this.equipos = equipos;
    }

    // ¿Cuántas filas hay que dibujar en total?
    @Override
    public int getCount() {
        return equipos.size();
    }

    // Dame el objeto de datos que corresponde a una posición concreta de la lista.
    @Override
    public Object getItem(int position) {
        return equipos.get(position);
    }

    // Un identificador único por fila (aquí, simplemente se usa la propia posición).
    @Override
    public long getItemId(int position) {
        return position;
    }

    // EL MÉTODO MÁS IMPORTANTE: construye la vista (la fila) de UN equipo concreto.
    // Se llama una vez por cada fila que hay que dibujar en pantalla.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        // "inflar" un layout = convertir el XML item_equipo.xml en un objeto View real en memoria.
        View fila = inflater.inflate(R.layout.item_equipo, parent, false);
        ImageView logo = fila.findViewById(R.id.ivEquipoLogo);
        TextView tvNombre = fila.findViewById(R.id.tvEquipoNombre);
        TextView tvCiudad = fila.findViewById(R.id.tvEquipoCiudad);
        TextView tvPrecio = fila.findViewById(R.id.tvEquipoPrecio);

        // El logo es una URL (texto), no una imagen guardada en el proyecto — Picasso se encarga de
        // descargarla de internet y ponerla en el ImageView (ver conceptos/07-adaptadores.md §2).
        Picasso.get().load(equipos.get(position).getLogo()).into(logo);
        tvNombre.setText(equipos.get(position).getNombre());
        tvCiudad.setText(equipos.get(position).getCiudad());
        tvPrecio.setText(equipos.get(position).getPrecio()+"");

        return fila;
    }
}
