package com.example.ejemplodialogopersonalizado.adaptadores;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ejemplodialogopersonalizado.R;

import java.io.File;
import java.util.List;

public class FotosGridViewAdapter extends BaseAdapter
{
    private final Context context;
    private final List<Uri> fotos;

    public FotosGridViewAdapter(Context context, List<Uri> fotos)
    {
        this.context = context;
        this.fotos = fotos;
    }

    @Override
    public int getCount()
    {
        return fotos.size();
    }

    @Override
    public Object getItem(int position)
    {
        return fotos.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View fila = inflater.inflate(R.layout.item_foto, parent, false);

        ImageView imageView = fila.findViewById(R.id.ivItem);
        Glide.with(context)
                .load(fotos.get(position))
                .into(imageView);
        return fila;
    }
}
