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

// Adapter (el "molde + repetición + relleno" que convierte una lista de datos en filas
// visibles, ver conceptos/07-adaptadores.md) para el ListView de compañías telefónicas.
// "extends BaseAdapter" (herencia, 00-programacion-basica.md §6): obliga a implementar 4 métodos.
public class CompaniasAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CompaniaTelefonica> companiaTelefonicas;

    // CONSTRUCTOR: guarda el contexto (necesario para inflar layouts) y la lista de datos a mostrar.
    public CompaniasAdapter(Context context,ArrayList<CompaniaTelefonica> companiaTelefonicas) {
        this.context = context;
        this.companiaTelefonicas = companiaTelefonicas;
    }

    // ¿Cuántos elementos hay? -> determina cuántas filas se dibujan.
    @Override
    public int getCount() {
        return this.companiaTelefonicas.size();
    }

    // Devuelve el objeto de datos que hay en una posición concreta.
    @Override
    public CompaniaTelefonica getItem(int position) {
        return companiaTelefonicas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // EL MÉTODO MÁS IMPORTANTE: construye y devuelve la vista (la fila) de UNA compañía concreta.
    // Se llama una vez por cada fila visible en pantalla.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        // Convierte el XML "molde" (compania_telefonica_list.xml) en una View real.
        // El "false" final significa "no la añadas tú al parent todavía, ya lo hará el ListView".
        View fila = inflater.inflate(R.layout.compania_telefonica_list,parent, false);

        CompaniaTelefonica compania = this.companiaTelefonicas.get(position);
        ImageView ivLogo = fila.findViewById(R.id.iconoTelefono);
        TextView tvTelegono = fila.findViewById(R.id.tvNombreTelefono);
        TextView tvPrecio = fila.findViewById(R.id.tvPrecioTelefono);

        ivLogo.setImageResource(compania.getLogo());
        tvTelegono.setText(compania.getNombre());
        tvPrecio.setText(compania.getPrecio()+"");

        // Colores alternos según la posición sea par o impar (efecto "cebra", para leer mejor la lista).
        if (position%2==0){
            fila.setBackgroundColor(Color.GRAY);
        }else {
            fila.setBackgroundColor(Color.LTGRAY);
        }
        return fila;
    }
}
