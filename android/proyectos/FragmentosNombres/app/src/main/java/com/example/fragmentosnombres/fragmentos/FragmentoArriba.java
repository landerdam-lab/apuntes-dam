package com.example.fragmentosnombres.fragmentos;

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

import com.example.fragmentosnombres.Interfaces.IControlFragmentos;
import com.example.fragmentosnombres.R;

// Muestra un campo de texto y un botón "Enviar"; al pulsarlo, avisa a MainActivity (a través
// de IControlFragmentos) de qué texto se escribió (ver 16-fragmentos).
public class FragmentoArriba extends Fragment {

    private Button btnEnviar;
    private EditText etTexto;
    // Se guarda la Activity SOLO como la interfaz, nunca como MainActivity directamente.
    private IControlFragmentos mainActivity;

    //constructor
    public FragmentoArriba() {
        super();
    }

    //Metodo para interactuar con la actividad principal
    // Se llama al "engancharse" a la Activity. El cast "(IControlFragmentos) context" le dice a
    // Java "trata esto como si solo tuviera los métodos de la interfaz" (ver
    // 00-programacion-basica §12 y 16-fragmentos §5).
    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.mainActivity = (IControlFragmentos) context;
    }

    //Se ejecuta cuando se crea el fragmento, no necesariamente si se muestra
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //Metodo para cargar el layout asociado
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_fragmento_arriba,container,false);
        return vista;
    }

    //Se ejecuta al cargar la vista del fragmento
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        btnEnviar = view.findViewById(R.id.btnEnviar);
        etTexto = view.findViewById(R.id.etTexto);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al pulsar, se llama al método de la interfaz — es MainActivity quien decide
                // qué hacer de verdad con el texto (crear el Bundle y refrescar FragmentoMedio).
                mainActivity.cambiarTexto(etTexto.getText().toString());
            }
        });
    }
    // Se ejecuta cuando el fragmento se vuelve a poner visible
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
    }

    //Método llamado al eliminar el fragmento de la actividad
    @Override
    public void onDetach()
    {
        super.onDetach();
    }


}