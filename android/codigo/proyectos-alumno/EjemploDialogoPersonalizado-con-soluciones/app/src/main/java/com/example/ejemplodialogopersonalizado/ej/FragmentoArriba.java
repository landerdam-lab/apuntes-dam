package com.example.ejemplodialogopersonalizado.ej;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ejemplodialogopersonalizado.R;

public class FragmentoArriba extends Fragment {

    private Button btnEnviar;
    private EditText etTexto;
    private IControlFragmentos mainActivity;

    // Constructor (publico y sin parametros)
    public FragmentoArriba() {
        super();
    }

    // Al engancharse a la actividad, se guarda su referencia como IControlFragmentos
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mainActivity = (IControlFragmentos) context;
    }

    // Metodo para cargar el layout asociado
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.ej_fragment_arriba, container, false);
        return vista;
    }

    // Se ejecuta al cargar la vista del fragmento
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnEnviar = view.findViewById(R.id.btnEnviar);
        etTexto = view.findViewById(R.id.etTexto);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.cambiarTexto(etTexto.getText().toString());
            }
        });
    }
}
