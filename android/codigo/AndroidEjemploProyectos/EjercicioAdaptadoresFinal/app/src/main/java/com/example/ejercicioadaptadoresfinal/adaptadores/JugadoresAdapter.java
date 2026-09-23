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
import java.util.zip.Inflater;

public class JugadoresAdapter extends BaseAdapter {
    private ArrayList<Jugador> jugadores;
    private Context context;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View fila = layoutInflater.inflate(R.layout.jugador_item_view,parent,false);
        ImageView ivFotoJugador = fila.findViewById(R.id.ivPerfilJugador);
        TextView tvNombreJugador = fila.findViewById(R.id.tvNombreJugador);
        TextView tvAlturaJugador = fila.findViewById(R.id.tvAlturaJugador);

        Picasso.get().load(jugadores.get(position).getFoto()).into(ivFotoJugador);
        tvNombreJugador.setText(jugadores.get(position).getNombre());
        tvAlturaJugador.setText(jugadores.get(position).getPosicion());

        return fila;
    }
}
