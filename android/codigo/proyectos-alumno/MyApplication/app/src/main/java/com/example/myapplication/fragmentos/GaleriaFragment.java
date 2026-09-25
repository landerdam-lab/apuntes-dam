package com.example.myapplication.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

// Fragmento de la ventana de Galeria. MainActivity lo mete en el FrameLayout "contenedor"
public class GaleriaFragment extends Fragment {

    // Constructor vacio: Android lo necesita para volver a crear el fragmento (por ejemplo al girar el movil)
    public GaleriaFragment() {
        super();
    }

    // Android lo llama solo para crear la vista del fragmento
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Convierte fragment_galeria.xml en vistas reales y las devuelve.
        // false = no pegarlo aun al contenedor, eso ya lo hace el FragmentManager
        return inflater.inflate(R.layout.fragment_galeria, container, false);
    }
}
