package com.example.myapplication.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class AdaptadorInicio extends ArrayAdapter<String> {

    private Context mContext;
    private String[] cursos;

    // CONSTRUCTOR: el contexto, la vista de cada fila y el array de cursos
    public AdaptadorInicio(@NonNull Context context, int resource, String[] cursos) {
        super(context, resource, cursos);
        this.mContext = context;
        this.cursos = cursos;
    }

    // Numero de filas (SIEMPRE LA LONGITUD DEL ARRAY)
    @Override
    public int getCount() {
        return cursos.length;
    }

    // Lo llama la lista para pintar cada fila
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return vistaCursos(position, convertView, parent);
    }

    // Metodo para cargar los datos de una fila
    private View vistaCursos(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // CREAR EL CARGADOR DE LAYOUT
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        // CARGAR LA VISTA DE LA FILA
        View fila = inflater.inflate(R.layout.item_curso, parent, false);
        // CARGAR EL VALOR
        TextView tvCurso = fila.findViewById(R.id.tvCurso);
        tvCurso.setText(cursos[position]);
        // RETORNAMOS LA VISTA
        return fila;
    }
}