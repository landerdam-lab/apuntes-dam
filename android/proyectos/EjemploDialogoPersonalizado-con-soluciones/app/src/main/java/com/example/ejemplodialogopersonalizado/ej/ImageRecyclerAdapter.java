package com.example.ejemplodialogopersonalizado.ej;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ejemplodialogopersonalizado.R;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImagenViewHolder> {

    private final Context contexto;
    private final TypedArray imagenes;

    public ImageRecyclerAdapter(Context contexto, TypedArray imagenes) {
        this.contexto = contexto;
        this.imagenes = imagenes;
    }

    // Se crea la vista y se guardan sus referencias en el ViewHolder (pocas veces)
    @NonNull
    @Override
    public ImagenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(contexto).inflate(R.layout.ej_grid_item_view, parent, false);
        return new ImagenViewHolder(vista);
    }

    // Se rellena una vista reutilizada con la imagen de esa posicion (al hacer scroll)
    @Override
    public void onBindViewHolder(@NonNull ImagenViewHolder holder, int position) {
        Glide.with(contexto)
                .load(imagenes.getResourceId(position, -1))
                .into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return imagenes.length();
    }

    // El ViewHolder cachea el findViewById: una sola vez por vista
    static class ImagenViewHolder extends RecyclerView.ViewHolder {
        final ImageView imagen;

        ImagenViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenGridView);
        }
    }
}
