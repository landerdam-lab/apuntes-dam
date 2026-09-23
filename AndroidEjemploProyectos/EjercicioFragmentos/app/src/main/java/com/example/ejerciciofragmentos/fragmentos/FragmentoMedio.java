package com.example.ejerciciofragmentos.fragmentos;

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
import android.widget.TextView;

import com.example.ejerciciofragmentos.R;
import com.example.ejerciciofragmentos.interfaces.IControlFragmentos;

public class FragmentoMedio extends Fragment {

    private IControlFragmentos activity;
    private String nombre;
    public FragmentoMedio(Bundle bundle) {
        nombre = bundle.getString("nombre");
    }

    public FragmentoMedio(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragmento_medio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvNombre = view.findViewById(R.id.tvMedioNombre);
        tvNombre.setText(this.nombre);

        EditText etApellido = view.findViewById(R.id.etApellido);
        EditText etFecha = view.findViewById(R.id.etFecha);

        Button btnEnviar = view.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.pasarTextos(tvNombre.getText() + "",
                        etApellido.getText() + "",
                        etFecha.getText()+"");
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (IControlFragmentos) context;
    }
}