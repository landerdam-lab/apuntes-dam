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
import android.widget.Toast;

import com.example.ejerciciofragmentos.IControlFragmentos;
import com.example.ejerciciofragmentos.R;

// Segundo paso del formulario: pide apellido y fecha, con el nombre ya precargado (llega desde
// Fragmento1 vía Bundle). Al pulsar "Ficha", avisa a MainActivity con los 3 datos juntos.
public class Fragmento2 extends Fragment {

    private IControlFragmentos activity;

    //private String nombre;

    public Fragmento2() {
        super();
    }

    // Método de fábrica (ver 00-programacion-basica §10 y 16-fragmentos §6): crea el fragmento
    // y le engancha el Bundle recibido (que trae el nombre) ANTES de que tenga vista.
    public static Fragmento2 newInstance(Bundle bundle) {
        Fragmento2 fragmento2 = new Fragmento2();
        if(bundle != null) {
            fragmento2.setArguments(bundle);
        }
        return fragmento2;
    }

    /*public Fragmento2 (Bundle bundle) {
        nombre = bundle.getString("nombre");
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragmento2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etNombre = view.findViewById(R.id.etNombre);
        EditText etApellido = view.findViewById(R.id.etApellido);
        EditText etFecha = view.findViewById(R.id.etFecha);
        Button btnFicha = view.findViewById(R.id.btnFicha);

        // getArguments() recupera el Bundle enganchado en newInstance(...) — aquí es donde se
        // precarga el nombre que ya se escribió en el paso anterior (Fragmento1).
        Bundle bundle = getArguments();
        if(bundle !=null) {
            if(bundle.containsKey("nombre")) {
                etNombre.setText(bundle.getString("nombre"));
            }
        }

        btnFicha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String apellido = etApellido.getText().toString().trim();
                String fecha = etFecha.getText().toString().trim();

                if (apellido.isEmpty() || fecha.isEmpty()) {
                    // Toast: un mensajito flotante que aparece unos segundos y desaparece solo.
                    Toast.makeText(getContext(), "Completa apellido y fecha", Toast.LENGTH_SHORT).show();
                    return;
                }

                activity.onApellidoYFechaCompletado(nombre, apellido, fecha);
            }
        });


    }

    // Se limpia la referencia a la Activity al separarse (evita fugas de memoria).
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

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