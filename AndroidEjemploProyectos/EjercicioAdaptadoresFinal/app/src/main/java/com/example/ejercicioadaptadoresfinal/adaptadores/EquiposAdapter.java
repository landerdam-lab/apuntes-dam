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

public class EquiposAdapter extends BaseAdapter {
    private ArrayList<Equipo> equipo;
    private Context context;

    public EquiposAdapter(ArrayList<Equipo> equipo, Context context) {
        this.equipo = equipo;
        this.context = context;
    }

    @Override
    public int getCount() {
        return equipo.size();
    }

    @Override
    public Object getItem(int position) {
        return equipo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View fila = inflater.inflate(R.layout.item_equipo,parent,false);
        ImageView logo = fila.findViewById(R.id.ivEquipoLogo);
        TextView tvNombre = fila.findViewById(R.id.tvEquipoNombre);
        TextView tvCiudad = fila.findViewById(R.id.tvEquipoCiudad);
        TextView tvPrecio = fila.findViewById(R.id.tvEquipoPrecio);

        Picasso.get().load(equipo.get(position).getLogo()).into(logo);
        tvNombre.setText(equipo.get(position).getNombre());
        tvCiudad.setText(equipo.get(position).getCiudad());
        tvPrecio.setText(equipo.get(position).getPrecio()+"");


        return fila;
    }

}
