package com.example.ejemplofragmentos.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ejemplofragmentos.R;


// Muestra un TextView grande con el texto/color que le mande MainActivity. No tiene botones:
// solo "refleja" lo que le llega a través de un Bundle (ver newInstance más abajo).
public class FragmentoAbajo extends Fragment {

    public FragmentoAbajo(){
        super();
    }

    // MÉTODO ESTÁTICO (ver 00-programacion-basica §10) de "fábrica": construye un FragmentoAbajo
    // nuevo y le engancha un Bundle de datos ANTES de que tenga vista, con setArguments(...).
    // Es la única forma correcta de pasarle datos a un fragmento (ver 16-fragmentos §6) —
    // nunca un constructor con parámetros, porque Android puede recrear el fragmento llamando
    // solo al constructor vacío (por ejemplo al girar la pantalla) y esos datos se perderían.
    public static FragmentoAbajo newInstance(Bundle bundle){
        FragmentoAbajo fragmentoAbajo= new FragmentoAbajo();
        if (bundle != null){
            fragmentoAbajo.setArguments(bundle);
        }
        return fragmentoAbajo;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragmento_abajo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvAbajo = view.findViewById(R.id.tvFragmentoAbajo);

        // getArguments() recupera el Bundle que se enganchó en newInstance(...) más arriba.
        Bundle bundle = getArguments();

        if (bundle != null) {
            // containsKey(...) comprueba si esa "etiqueta" viene en el Bundle antes de leerla —
            // así, si solo llega "color" (desde el menú) no se borra el texto por accidente,
            // y viceversa (ver 16-fragmentos §6).
            if (bundle.containsKey("saludo")) {
                tvAbajo.setText(bundle.getString("saludo"));
            }
            if (bundle.containsKey("color")){
                tvAbajo.setTextColor(bundle.getInt("color"));
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}