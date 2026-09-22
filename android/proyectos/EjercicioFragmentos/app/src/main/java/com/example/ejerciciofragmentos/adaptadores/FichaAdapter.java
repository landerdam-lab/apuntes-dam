package com.example.ejerciciofragmentos.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ejerciciofragmentos.R;
import com.example.ejerciciofragmentos.modelos.Persona;

import java.util.ArrayList;
import java.util.List;

// Un Adapter (ver 07-adaptadores) es el "molde + repetición + relleno" que convierte la lista de
// Persona en filas reales dentro del GridView de Fragmento3. BaseAdapter es la clase padre
// (ver 00-programacion-basica §6, "extends") que obliga a implementar estos 4 métodos.
public class FichaAdapter extends BaseAdapter {

    private ArrayList<Persona> listaPersonas;
    private LayoutInflater inflater;

    public FichaAdapter(Context context, ArrayList<Persona> listaPersonas) {
        this.listaPersonas = listaPersonas;
        this.inflater = LayoutInflater.from(context);
    }

    // Cuántos elementos hay → cuántas celdas dibuja el GridView.
    @Override
    public int getCount() {
        return listaPersonas.size();
    }

    // El objeto de datos (una Persona) que corresponde a esa posición.
    @Override
    public Object getItem(int position) {
        return listaPersonas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // El método más importante: construye (o reutiliza) la vista de UNA celda concreta y la
    // rellena con los datos de esa Persona.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;
        if (vista == null) {
            // "inflar" = convertir el XML grid_item_view.xml en objetos View reales en memoria.
            vista = inflater.inflate(R.layout.grid_item_view, parent, false);
        }

        TextView tvNombre = vista.findViewById(R.id.tvNombre);
        TextView tvApellido = vista.findViewById(R.id.tvApellido);
        TextView tvFecha = vista.findViewById(R.id.tvFecha);

        Persona persona = listaPersonas.get(position);
        tvNombre.setText(persona.getNombre());
        tvApellido.setText(persona.getApellido());
        tvFecha.setText(persona.getFecha());

        return vista;
    }
}
