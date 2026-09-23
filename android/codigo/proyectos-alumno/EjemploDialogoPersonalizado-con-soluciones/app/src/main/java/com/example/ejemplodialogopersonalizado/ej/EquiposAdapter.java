package com.example.ejemplodialogopersonalizado.ej;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ejemplodialogopersonalizado.R;

import java.util.ArrayList;

public class EquiposAdapter extends BaseAdapter {

    private ArrayList<Equipo> equipos;
    private Context context;

    public EquiposAdapter(Context context, ArrayList<Equipo> equipos) {
        this.context = context;
        this.equipos = equipos;
    }

    @Override
    public int getCount() {
        return equipos.size();            // 1. Cuantas filas hay
    }

    @Override
    public Object getItem(int position) {
        return equipos.get(position);     // 2. El objeto de esa posicion
    }

    @Override
    public long getItemId(int position) {
        return position;                  // 3. Un id por fila
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 4. EL MAS IMPORTANTE: construye la vista de UNA fila
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View fila = inflater.inflate(R.layout.ej_item_equipo, parent, false);

        ImageView logo = fila.findViewById(R.id.ivEquipoLogo);
        TextView tvNombre = fila.findViewById(R.id.tvEquipoNombre);
        TextView tvCiudad = fila.findViewById(R.id.tvEquipoCiudad);

        Equipo equipo = equipos.get(position);
        logo.setImageResource(equipo.getLogo());
        tvNombre.setText(equipo.getNombre());
        tvCiudad.setText(equipo.getCiudad());

        // Colores de fila alternos (efecto "cebra")
        if (position % 2 == 0) {
            fila.setBackgroundColor(Color.LTGRAY);
        } else {
            fila.setBackgroundColor(Color.WHITE);
        }
        return fila;
    }
}
