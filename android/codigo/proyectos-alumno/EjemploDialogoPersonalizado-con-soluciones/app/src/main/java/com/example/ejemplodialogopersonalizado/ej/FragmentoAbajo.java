package com.example.ejemplodialogopersonalizado.ej;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ejemplodialogopersonalizado.R;

public class FragmentoAbajo extends Fragment {

    private TextView tvTexto;

    public FragmentoAbajo() {
        super();
    }

    // Paso de parametros: crea la instancia y le fija los argumentos (Bundle)
    public static FragmentoAbajo newInstance(Bundle argumentos) {
        FragmentoAbajo fragmentoAbajo = new FragmentoAbajo();

        if (argumentos != null) {
            fragmentoAbajo.setArguments(argumentos);
        }

        return fragmentoAbajo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.ej_fragment_abajo, container, false);
        tvTexto = vista.findViewById(R.id.tvAbajo);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle argumentos = getArguments();
        if (argumentos != null) {
            if (argumentos.containsKey("saludo")) {
                tvTexto.setText(argumentos.getString("saludo"));
            }

            if (argumentos.containsKey("color")) {
                tvTexto.setTextColor(argumentos.getInt("color"));
            }
        }
    }
}
