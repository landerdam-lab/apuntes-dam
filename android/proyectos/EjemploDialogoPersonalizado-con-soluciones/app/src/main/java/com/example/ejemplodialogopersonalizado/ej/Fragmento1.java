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

public class Fragmento1 extends Fragment {

    private IControlFichas activity;

    public Fragmento1() {
        super();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IControlFichas) {
            activity = (IControlFichas) context;
        } else {
            throw new RuntimeException(context + " debe implementar IControlFichas");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ej_fragmento1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText etNombre = view.findViewById(R.id.etNombre);
        Button btnCrear = view.findViewById(R.id.btnCrear);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                if (nombre.isEmpty()) {
                    etNombre.setError("Introduce un nombre");
                    return;
                }
                activity.onNombreCompletado(nombre);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;     // liberamos la referencia (evita fugas de memoria)
    }
}
