package com.example.ejerciciopokemon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ejerciciopokemon.R;
import com.example.ejerciciopokemon.model.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PokemonAdapter extends BaseAdapter {

    private List<Pokemon> listaPokemons;
    private Context context;

    public PokemonAdapter(List<Pokemon> listaPokemons, Context context) {
        this.listaPokemons = listaPokemons;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaPokemons.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPokemons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.item_pokemon,parent,false);

        TextView tvNombre = vista.findViewById(R.id.tvNombre);
        TextView tvTipo = vista.findViewById(R.id.tvTipo);
        TextView tvPokedex = vista.findViewById(R.id.tvPokedex);
        ImageView ivPokemon = vista.findViewById(R.id.ivPokemon);

        tvNombre.setText(listaPokemons.get(position).getNombre());
        tvTipo.setText(listaPokemons.get(position).getTipo());
        tvPokedex.setText(listaPokemons.get(position).getPokedex() + "");
        Picasso.get()
                .load(listaPokemons.get(position).getFoto())
                .fit()
                .centerCrop()
                .into(ivPokemon);

        return vista;
    }

    public List<Pokemon> getListaPokemons() {
        return listaPokemons;
    }

    public void setListaPokemons(List<Pokemon> listaPokemons) {
        this.listaPokemons = listaPokemons;
        notifyDataSetChanged();
    }
}
