package com.example.adapterdam2.adaptadores;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.adapterdam2.R;

// Adapter del GridView de imágenes (ver conceptos/07-adaptadores.md). Los datos aquí no son
// una lista de objetos propios, sino un TypedArray (un "álbum" de recursos drawable, ver
// conceptos/06-animaciones-frame-by-frame.md §2).
public class ImageAdapter extends BaseAdapter {

    private final Context contexto;
    private final TypedArray imagenes;

    public ImageAdapter(Context contexto, TypedArray imagenes){
        this.contexto = contexto;
        this.imagenes = imagenes;
    }
    @Override
    public int getCount() {
        return this.imagenes.length();
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
        View vistaImagen = inflater.inflate(R.layout.grid_item_view,parent,false);

        ImageView imageView = vistaImagen.findViewById(R.id.imagenGridView);
        // Aunque son recursos locales (podrían cargarse con setImageResource, línea comentada
        // debajo), aquí se usa Glide igualmente para mantener el mismo código de carga de
        // imágenes en todo el proyecto (ver conceptos/07-adaptadores.md, uso de Picasso/Glide).
        //imageView.setImageResource(imagenes.getResourceId(position,-1));
        Glide.with(contexto)
                .load(imagenes.getResourceId(position,-1))
                .into(imageView);
        return vistaImagen;
    }
}
