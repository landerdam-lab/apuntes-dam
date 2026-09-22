package com.example.adapterdam2;

import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapterdam2.adaptadores.CompaniasAdapter;
import com.example.adapterdam2.model.CompaniaTelefonica;

import java.util.ArrayList;

// Pantalla que muestra una lista (ListView) de compañías telefónicas usando un adaptador
// (ver conceptos/07-adaptadores.md).
public class ListDam2Activity extends AppCompatActivity {

    // ArrayList<CompaniaTelefonica>: una LISTA que puede crecer (00-programacion-basica.md §8),
    // aquí guarda los datos de ejemplo que se muestran en pantalla.
    private final ArrayList<CompaniaTelefonica> companiaTelefonicas = new ArrayList<>();
    private ListView listaCompanias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Se prepara la transición de entrada "slide" ANTES de setContentView (ver conceptos/08-transiciones.md §2.2)
        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);

        getWindow().setEnterTransition(slide);

        setContentView(R.layout.activity_list_dam2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rellenarCompanias();
        // Se crea el Adapter (el "puente" entre la lista de datos y lo que se ve en pantalla,
        // ver conceptos/07-adaptadores.md) y se conecta al ListView con setAdapter.
        CompaniasAdapter adapter = new CompaniasAdapter(this, companiaTelefonicas);
        listaCompanias = findViewById(R.id.lvTelefonica);
        listaCompanias.setAdapter(adapter);
    }

    // MÉTODO PRIVADO (00-programacion-basica.md §9) que rellena la lista con datos de ejemplo
    // escritos a mano ("hardcodeados"): en una app real vendrían de una base de datos o internet.
    private void rellenarCompanias(){
        companiaTelefonicas.add((new CompaniaTelefonica("Movistar", 80,R.drawable.movistar_isotype_2025)));
        companiaTelefonicas.add((new CompaniaTelefonica("Euskaltel", 80,R.drawable.euskaltel_la_morea_pamplona)));
        companiaTelefonicas.add((new CompaniaTelefonica("Digi", 80,R.drawable.images)));
        companiaTelefonicas.add((new CompaniaTelefonica("Vodafone", 80,R.drawable.vodafone)));

    }
}