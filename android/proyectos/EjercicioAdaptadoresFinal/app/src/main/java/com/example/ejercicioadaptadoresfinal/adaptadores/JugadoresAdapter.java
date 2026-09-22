package com.example.ejercicioadaptadoresfinal.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ejercicioadaptadoresfinal.R;
import com.example.ejercicioadaptadoresfinal.modelos.Jugador;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// Igual que EquiposAdapter, pero para dibujar la rejilla (GridView) de jugadores de un equipo
// (ver conceptos/07-adaptadores.md).
public class JugadoresAdapter extends BaseAdapter {

    private ArrayList<Jugador> jugadores;
    private Context context;

    // Constructor: guarda los datos y el contexto que hacen falta para construir cada celda.
    public JugadoresAdapter(ArrayList<Jugador> jugadores, Context context) {
        this.jugadores = jugadores;
        this.context = context;
    }

    @Override
    public int getCount() {
        return jugadores.size();
    }

    @Override
    public Object getItem(int position) {
        return jugadores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Construye la celda visual de UN jugador concreto de la rejilla.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View fila = inflater.inflate(R.layout.jugador_item_view, parent, false);
        ImageView ivFotoJugador = fila.findViewById(R.id.ivPerfilJugador);
        TextView tvNombreJugador = fila.findViewById(R.id.tvNombreJugador);
        TextView tvAltura = fila.findViewById(R.id.tvAlturaJugador);

        Picasso.get().load(jugadores.get(position).getFoto()).into(ivFotoJugador);
        tvNombreJugador.setText(jugadores.get(position).getNombre());
        tvAltura.setText((jugadores.get(position).getPosicion()));
        return fila;
    }
}
