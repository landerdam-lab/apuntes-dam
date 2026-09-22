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

/*
 * ADAPTADOR
 * Un ListView no sabe por si solo como mostrar objetos Usuario. El adaptador es el "traductor":
 * coge la lista de usuarios y construye una fila (View) para cada uno.
 */
public class UsuariosAdapter extends ArrayAdapter<Usuario>
{
    private final Context context;
    //Lista de usuarios que se muestra en pantalla
    private List<Usuario> mUsuarioList;

    public UsuariosAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        //Empieza vacia hasta que la BD nos de los datos
        this.mUsuarioList = new ArrayList<>();
    }

    //Le dice al ListView cuantas filas tiene que dibujar
    @Override
    public int getCount() {
        return mUsuarioList.size();
    }

    //Devuelve el usuario que esta en la posicion indicada
    @Nullable
    @Override
    public Usuario getItem(int position) {
        return mUsuarioList.get(position);
    }

    //Se llama una vez por cada fila visible: aqui construimos como se ve cada fila
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        //Usamos un diseno de fila que ya trae Android (un solo TextView)
        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView tvUsuario = view.findViewById((android.R.id.text1));
        //Ponemos el nombre del usuario en esa fila
        tvUsuario.setText(mUsuarioList.get(position).getUsuario());
        return view;
    }

    public List<Usuario> getmUsuarioList() {
        return mUsuarioList;
    }

    //Se llama cuando la BD cambia: guarda la lista nueva y avisa al ListView para que se redibuje
    public void setmUsuarioList(List<Usuario> mUsuarioList) {
        this.mUsuarioList = mUsuarioList;
        notifyDataSetChanged();
    }
}
