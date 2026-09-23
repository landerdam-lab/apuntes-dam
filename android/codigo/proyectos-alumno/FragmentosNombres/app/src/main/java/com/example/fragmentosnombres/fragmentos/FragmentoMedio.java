package com.example.fragmentosnombres.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.fragmentosnombres.R;

// Formulario de nombre/apellido/fecha. El campo "Nombre" se rellena automáticamente con el
// texto que envía FragmentoArriba (ver 16-fragmentos); apellido y fecha se pueden escribir
// a mano, pero de momento ningún botón los envía a ningún sitio.
public class FragmentoMedio extends Fragment {

    private EditText etNombre;
    private EditText etApellido;
    private EditText etFecha;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoMedio() {
        super();
    }

    // Paso de parámetros: crea la instancia y le fija los argumentos (Bundle)
    // Método de fábrica (ver 00-programacion-basica §10): construye el fragmento y le engancha
    // el Bundle de datos ANTES de que tenga vista — nunca se usa un constructor con parámetros
    // (ver 16-fragmentos §6 sobre por qué).
    public static FragmentoMedio newInstance(Bundle argumentos)
    {
        FragmentoMedio fragmentoMedio = new FragmentoMedio();
        if (argumentos != null)
        {
            fragmentoMedio.setArguments(argumentos);
        }
        return fragmentoMedio;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle
                                     savedInstanceState)
    {
        View vista = inflater.inflate(R.layout.fragment_fragmento_medio, container,
                        false);
        etNombre = vista.findViewById(R.id.etNombre);
        etApellido = vista.findViewById(R.id.etApellido);
        etFecha = vista.findViewById(R.id.etFecha);
        return vista;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable
    Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        // getArguments() recupera el Bundle enganchado en newInstance(...). La clave "nombre"
        // tiene que coincidir con la que usa MainActivity.cambiarTexto(...) al escribirlo.
        Bundle argumentos = getArguments();
        if (argumentos != null) {
            etNombre.setText(argumentos.getString("nombre"));
        }
    }
}