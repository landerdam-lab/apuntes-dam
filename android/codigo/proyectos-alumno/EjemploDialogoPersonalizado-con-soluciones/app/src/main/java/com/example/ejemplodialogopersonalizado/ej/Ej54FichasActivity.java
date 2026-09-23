package com.example.ejemplodialogopersonalizado.ej;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;

import java.util.ArrayList;

public class Ej54FichasActivity extends AppCompatActivity implements IControlFichas {

    // Vive mientras la Activity no se destruya y CRECE con cada ficha nueva
    private ArrayList<Persona> personas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_fichas_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.contenedor1, new Fragmento1()).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.contenedor2, Fragmento2.newInstance(new Bundle())).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.contenedor3, Fragmento3.newInstance(new Bundle())).commit();
        }
    }

    // El Fragmento1 nos avisa: recargamos el Fragmento2 con el nombre ya escrito
    @Override
    public void onNombreCompletado(String nombre) {
        Bundle bundle = new Bundle();
        bundle.putString("nombre", nombre);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor2, Fragmento2.newInstance(bundle))
                .commit();
    }

    // El Fragmento2 nos avisa: creamos la Persona, la acumulamos y recargamos el Fragmento3
    @Override
    public void onApellidoYFechaCompletado(String nombre, String apellido, String fecha) {
        Bundle bundle = new Bundle();
        Persona persona = new Persona(nombre, apellido, fecha);
        personas.add(persona);                          // se acumula, no se reemplaza
        bundle.putSerializable("personas", personas);   // se manda la LISTA COMPLETA cada vez
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor3, Fragmento3.newInstance(bundle))
                .commit();
    }
}
