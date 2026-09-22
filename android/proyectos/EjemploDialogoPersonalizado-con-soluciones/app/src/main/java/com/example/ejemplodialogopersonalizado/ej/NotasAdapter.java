package com.example.ejemplodialogopersonalizado.ej;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NotasAdapter extends ArrayAdapter<Nota> {

    private final Context context;
    private List<Nota> mNotaList;

    public NotasAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.mNotaList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mNotaList.size();
    }

    @Nullable
    @Override
    public Nota getItem(int position) {
        return mNotaList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView tvNota = view.findViewById(android.R.id.text1);
        tvNota.setText(mNotaList.get(position).getTitulo());
        return view;
    }

    //Se llama cuando la BD cambia: guarda la lista nueva y avisa al ListView para que se redibuje
    public void setmNotaList(List<Nota> mNotaList) {
        this.mNotaList = mNotaList;
        notifyDataSetChanged();
    }
}
