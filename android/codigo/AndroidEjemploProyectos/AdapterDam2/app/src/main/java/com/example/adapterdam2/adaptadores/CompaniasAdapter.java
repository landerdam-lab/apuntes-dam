package com.example.adapterdam2.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adapterdam2.R;
import com.example.adapterdam2.model.CompaniaTelefonica;

import java.util.ArrayList;

public class CompaniasAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CompaniaTelefonica> companiaTelefonicas;

    public CompaniasAdapter(Context context, ArrayList<CompaniaTelefonica> companiaTelefonicas) {
        this.context = context;
        this.companiaTelefonicas = companiaTelefonicas;
    }

    @Override
    public int getCount() {
        return this.companiaTelefonicas.size();
    }

    @Override
    public CompaniaTelefonica getItem(int position) {
        return companiaTelefonicas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View fila = inflater.inflate(R.layout.compania_telefonica_list,parent,false);

        CompaniaTelefonica compania = this.companiaTelefonicas.get(position);
        ImageView ivLogo = fila.findViewById(R.id.iconoTelefono);
        TextView tvTelefono = fila.findViewById(R.id.tvNombreTelefono);
        TextView tvPrecio = fila.findViewById(R.id.tvPrecioTelefono);

        ivLogo.setImageResource(compania.getLogo());
        tvTelefono.setText(compania.getNombre());
        tvPrecio.setText(compania.getPrecio() + "");

        if(position % 2 == 0)
        {
            fila.setBackgroundColor(Color.GRAY);
        } else
        {
            fila.setBackgroundColor(Color.LTGRAY);
        }

        return fila;
    }
}
