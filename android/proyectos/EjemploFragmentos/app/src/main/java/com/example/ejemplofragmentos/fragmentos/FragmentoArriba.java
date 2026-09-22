package com.example.ejemplofragmentos.fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ejemplofragmentos.IControlFragmentos;
import com.example.ejemplofragmentos.R;


// Un Fragment (ver 16-fragmentos) es un trozo de interfaz que vive dentro de una Activity.
// Este muestra un campo de texto y un botón "Enviar"; al pulsarlo, avisa a MainActivity
// (a través de la interfaz IControlFragmentos) de qué texto se escribió.
public class FragmentoArriba extends Fragment {

    // Guarda la Activity que lo contiene, pero SOLO como IControlFragmentos (la interfaz, no la
    // clase completa) — así este fragmento no depende de una Activity en concreto.
    private IControlFragmentos activity;

    // Constructor vacío: obligatorio en todo Fragment (ver 16-fragmentos §2 y §6 sobre por qué
    // nunca se le pasan parámetros aquí).
    public FragmentoArriba(){
        super();
    }

    // Se llama para construir la interfaz de este fragmento: "infla" (convierte de XML a objetos
    // reales) el layout fragment_fragmento_arriba.xml y lo devuelve.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragmento_arriba, container, false);
    }

    // Se llama justo después de onCreateView, con la vista ya lista: aquí se buscan los widgets
    // (findViewById) y se conectan los listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etTexto = view.findViewById(R.id.etTexto);
        Button btnEnviar = view.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al pulsar el botón, se llama al método de la interfaz: es MainActivity quien
                // de verdad decide qué hacer con ese texto (ver 16-fragmentos §5).
                activity.cambiarTexto(etTexto.getText().toString());
            }
        });
    }

    // Se llama cuando el fragmento se "engancha" a su Activity. El parámetro context ES la
    // Activity (una Activity es un Context). El cast "(IControlFragmentos) context" le dice a
    // Java "trátalo como si solo tuviera los métodos de esa interfaz" (ver 00-programacion-basica §12).
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (IControlFragmentos) context;
    }

    // Se llama cuando el fragmento se separa de la Activity.
    @Override
    public void onDetach() {
        super.onDetach();
    }

}