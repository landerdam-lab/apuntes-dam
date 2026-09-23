package com.example.ejemplodialogopersonalizado.ej;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ejemplodialogopersonalizado.R;

public class ImageAdapter extends BaseAdapter {

    private final Context contexto;
    private final TypedArray imagenes;

    public ImageAdapter(Context contexto, TypedArray imagenes) {
        this.contexto = contexto;
        this.imagenes = imagenes;
    }

    @Override
    public int getCount() {
        return imagenes.length();
    }

    @Override
    public Object getItem(int position) {
        return imagenes.getResourceId(position, -1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View vistaImagen = inflater.inflate(R.layout.ej_grid_item_view, parent, false);

        ImageView imagen = vistaImagen.findViewById(R.id.imagenGridView);

        // Glide carga la imagen de forma asincrona (y controla el tamano del buffer)
        Glide.with(contexto)
                .load(imagenes.getResourceId(position, -1))
                .into(imagen);

        return vistaImagen;
    }
}
