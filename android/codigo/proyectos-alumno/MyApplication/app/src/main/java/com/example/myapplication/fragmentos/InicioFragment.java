package com.example.myapplication.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.widget.ListView;
import com.example.myapplication.adaptadores.AdaptadorInicio;

import com.example.myapplication.R;

// Fragmento de la ventana de Inicio. MainActivity lo mete en el FrameLayout "contenedor"
public class InicioFragment extends Fragment {

    private Context mContext;

    // Constructor vacio: Android lo necesita para volver a crear el fragmento (por ejemplo al girar el movil)
    public InicioFragment() {
        super();
    }

    // Android lo llama cuando el fragmento se engancha a la Activity: aqui se guarda el contexto
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    // Android lo llama solo para crear la vista del fragmento
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Convierte fragment_inicio.xml en vistas reales y las devuelve.
        // false = no pegarlo aun al contenedor, eso ya lo hace el FragmentManager
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Buscar la lista dentro de la vista del fragmento
        ListView lvInicio = view.findViewById(R.id.lvInicio);
        // Leer el array "cursos" de res/values/array.xml
        String[] cursos = getResources().getStringArray(R.array.cursos);
        // Darle a la lista el adaptador con el array
        lvInicio.setAdapter(new AdaptadorInicio(mContext, R.layout.item_curso, cursos));
    }
}
