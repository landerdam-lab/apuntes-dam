package com.example.ejemplodialogopersonalizado.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ejemplodialogopersonalizado.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuariosAdapter extends ArrayAdapter<Usuario>
{
    private final Context context;
    private List<Usuario> mUsuarioList;

    public UsuariosAdapter(@NonNull Context context, int resource)
    {
        super(context, resource);
        this.context = context;
        this.mUsuarioList = new ArrayList<>();
    }

    @Override
    public int getCount()
    {
        return mUsuarioList.size();
    }

    @Nullable
    @Override
    public Usuario getItem(int position)
    {
        return mUsuarioList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        TextView tvUsuario = view.findViewById(android.R.id.text1);
        tvUsuario.setText(mUsuarioList.get(position).getUsuario());

        return view;
    }

    public List<Usuario> getmUsuarioList()
    {
        return mUsuarioList;
    }

    public void setmUsuarioList(List<Usuario> mUsuarioList)
    {
        this.mUsuarioList = mUsuarioList;
        notifyDataSetChanged();
    }
}
