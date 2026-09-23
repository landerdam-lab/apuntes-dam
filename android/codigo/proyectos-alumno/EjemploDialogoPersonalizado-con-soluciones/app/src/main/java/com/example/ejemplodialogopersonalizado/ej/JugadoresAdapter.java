package com.example.ejemplodialogopersonalizado.ej;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ejemplodialogopersonalizado.R;

import java.util.ArrayList;

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
        LayoutInflater inflater = LayoutInflater.from(context);
        View celda = inflater.inflate(R.layout.ej_grid_jugador, parent, false);

        ImageView foto = celda.findViewById(R.id.ivJugadorFoto);
        TextView tvNombre = celda.findViewById(R.id.tvJugadorNombre);
        TextView tvDorsal = celda.findViewById(R.id.tvJugadorDorsal);

        Jugador jugador = jugadores.get(position);
        foto.setImageResource(jugador.getFoto());
        tvNombre.setText(jugador.getNombre());
        tvDorsal.setText("Dorsal " + jugador.getDorsal());
        return celda;
    }
}
