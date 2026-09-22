package com.example.ejerciciofragmentos.fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.ejerciciofragmentos.R;
import com.example.ejerciciofragmentos.adaptadores.FichaAdapter;
import com.example.ejerciciofragmentos.modelos.Persona;

import java.util.ArrayList;


// Tercer y último paso: no pide nada, solo MUESTRA en una rejilla (GridView) todas las fichas
// de Persona que se han ido completando. Cada vez que hay una ficha nueva, MainActivity
// reemplaza este fragmento entero con la lista actualizada (ver 16-fragmentos §4 y §7).
public class Fragmento3 extends Fragment {

    private Context mContext;

    public Fragmento3() {
        super();
    }

    public static Fragmento3 newInstance(Bundle bundle) {
        Fragmento3 fragmento3 = new Fragmento3();
        if(bundle != null) {
            fragmento3.setArguments(bundle);
        }
        return fragmento3;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragmento3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView gridFicha = view.findViewById(R.id.gridFicha);

        // NOTA: aquí no se comprueba si getArguments() es null (a diferencia de Fragmento1/
        // Fragmento2). En la práctica no falla porque MainActivity siempre crea este fragmento
        // con Fragmento3.newInstance(bundle), nunca con "new Fragmento3()" a secas — pero es un
        // punto frágil (ver 16-fragmentos §8, punto 5).
        if(getArguments().containsKey("personas")) {
            // getSerializable(...) devuelve un Object genérico; el cast "(ArrayList<Persona>)"
            // (ver 00-programacion-basica §12) le dice a Java el tipo real que hay dentro.
            FichaAdapter adapter = new FichaAdapter(this.mContext, (ArrayList<Persona>) getArguments().getSerializable("personas"));
            gridFicha.setAdapter(adapter);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
}