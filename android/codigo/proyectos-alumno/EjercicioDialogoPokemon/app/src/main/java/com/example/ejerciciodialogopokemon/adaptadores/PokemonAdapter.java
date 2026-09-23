package com.example.ejerciciodialogopokemon.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ejerciciodialogopokemon.R;
import com.example.ejerciciodialogopokemon.model.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

// Adapter (ver conceptos/07-adaptadores.md): traduce la lista de objetos Pokemon en filas
// visuales dentro del ListView, cargando ademas la imagen de cada uno con Picasso.
public class PokemonAdapter extends ArrayAdapter<Pokemon> {

    private final Context context;
    private List<Pokemon> mPokemonList;
    public PokemonAdapter(@NonNull List<Pokemon> pokemonList, @NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.mPokemonList = pokemonList;
    }

    @Override
    public int getCount() {
        return mPokemonList.size();
    }

    @Nullable
    @Override
    public Pokemon getItem(int position) {
        return mPokemonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Se llama una vez por cada fila visible: construye como se ve un Pokemon concreto.
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // "Infla" (convierte) el XML item_pokemon.xml en una fila real
        View view = inflater.inflate(R.layout.item_pokemon, parent, false);
        TextView tvNombre = view.findViewById((R.id.tvNombre));
        TextView tvTipo = view.findViewById((R.id.tvTipo));
        ImageView ivImagen = view.findViewById(R.id.ivPokemon);
        tvNombre.setText(mPokemonList.get(position).getNombre());
        tvTipo.setText(mPokemonList.get(position).getTipo());
        String urlImagen = mPokemonList.get(position).getURLimagen();
        // Comprobacion importante: Picasso lanza un error si le pasas una URL vacia, por eso
        // solo se le pide cargar la imagen cuando de verdad hay una URL guardada.
        if (urlImagen != null && !urlImagen.isEmpty()) {
            Picasso.get().load(urlImagen).into(ivImagen);
        } else {
            ivImagen.setImageDrawable(null);
        }
        return view;
    }

    public List<Pokemon> getmPokemonList()
    {
        return mPokemonList;
    }

    public void setmPokemonList(List<Pokemon> mPokemonList) {
        this.mPokemonList = mPokemonList;
        notifyDataSetChanged();
    }
}
