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

import com.example.ejerciciofragmentos.IControlFragmentos;
import com.example.ejerciciofragmentos.R;

// Primer paso del formulario: solo pide el nombre. Al pulsar "Crear" (si no está vacío),
// avisa a MainActivity a través de IControlFragmentos.
public class Fragmento1 extends Fragment {

    private IControlFragmentos activity;

    public Fragmento1() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragmento1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etNombre = view.findViewById(R.id.etNombre);
        Button btnCrear = view.findViewById(R.id.btnCrear);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // .trim() quita espacios sueltos al principio/final, para que escribir solo
                // espacios no cuente como "nombre válido".
                String nombre = etNombre.getText().toString().trim();

                if (nombre.isEmpty()) {
                    // setError muestra el aviso rojo típico de Material Design junto al campo.
                    etNombre.setError("Introduce un nombre");
                    return;
                }

                activity.onNombreCompletado(nombre);
            }
        });
    }

    // Se limpia la referencia a la Activity al separarse, para evitar fugas de memoria
    // (que el fragmento quede "agarrado" a una Activity que ya no existe).
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    // "instanceof" (ver 00-programacion-basica §12) comprueba, ANTES del cast, si context de
    // verdad implementa la interfaz. Si no, se lanza un error explicando exactamente qué falta,
    // en vez de un ClassCastException genérico (ver 16-fragmentos §5).
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IControlFragmentos) {
            activity = (IControlFragmentos) context;
        } else {
            throw new RuntimeException(context + " debe implementar IControlFragmentos");
        }
    }
}