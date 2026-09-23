package com.example.adapterdam2.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.adapterdam2.R;

// ArrayAdapter<String>: un Adapter simplificado para cuando los datos son solo texto
// (ver conceptos/07-adaptadores.md §6). Personaliza cómo se ve cada fila del Spinner.
public class SpinnerDam2Adapter extends ArrayAdapter<String> {
    private Context mContext;
    private String[] datos;
    // CONSTRUCTOR: "super(...)" llama al constructor de la clase padre (ArrayAdapter) para que
    // también quede correctamente inicializada con esos mismos datos.
    public SpinnerDam2Adapter(@NonNull Context context, int resource, String[] datos) {
        super(context, resource, datos);
        this.mContext=context;
        this.datos=datos;
    }

    // Truco del "+1": se añade una fila extra (la de aviso "Seleccione una Opción") por delante
    // de los datos reales, así que hay que avisar que hay uno más de los que trae el array.
    //Numero de vistas(SIEMPRE LA LONGITUD DEL ARRAY)
    @Override
    public int getCount() {
        return datos.length + 1;
    }

    // Fila para cuando el desplegable está ABIERTO (cada opción de la lista)
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return vistaPersonalizada(position, convertView, parent);
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return datos[position];
    }

    // Fila para cuando el Spinner está CERRADO (lo que se ve siempre)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return vistaPersonalizada(position, convertView, parent);
    }

    // Método privado compartido por getView/getDropDownView (ambos necesitan construir
    // exactamente la misma fila, así que el código común se centraliza aquí).
    //Metodo para cargar los datos
    private View vistaPersonalizada (int position, @Nullable View convertView, @NonNull ViewGroup parent){
        //CREAR EL CARGADOR DE LAYOUT
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        //CARGAR LA VISTA DEL ADAPTADOR
        View fila = inflater.inflate(R.layout.spinner_per, parent,false);
        //CARGAR LOS DATOS
        TextView tvSpinner = fila.findViewById(R.id.tvSpinner);
        if (position == 0) {
            // La fila "falsa" de aviso (posición 0), no es un dato real
            tvSpinner.setText("Selecione una Opcion");
            fila.setBackgroundColor(Color.BLUE);
        }else {
            //CARGAR EL VALOR — se resta 1 a la posición para compensar el "+1" de getCount()
            tvSpinner.setText(datos[position-1]);
            if(position%2==0){
                fila.setBackgroundColor(Color.GRAY);
            }
        }

        //RETORNAMOS LA VISTA
        return fila;
    }
}
