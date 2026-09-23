package com.example.adapterdam2;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.graphics.Insets;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapterdam2.adaptadores.ImageAdapter;

// Pantalla con una rejilla (GridView) de imágenes; al pulsar una, navega a DetalleActivity
// con una transición de "elemento compartido" (la imagen pulsada "viaja" a la siguiente pantalla).
public class GridViewDam2Activity extends AppCompatActivity {

    private GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grid_view_dam2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        grid = findViewById(R.id.gridImagenes);
        // Adaptador de la rejilla (ver conceptos/07-adaptadores.md), cargado con el TypedArray
        // de imágenes "pikachus" declarado en res/values.
        grid.setAdapter(new ImageAdapter(getApplicationContext(),getResources().obtainTypedArray(R.array.pikachus)));

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GridViewDam2Activity.this,DetalleActivity.class);
                intent.putExtra("idFoto",position); // qué imagen se pulsó

                // Se obtiene la vista EXACTA (esta celda concreta) que se pulsó, y se empareja
                // con la etiqueta VIEW_NAME_HEADER_IMAGE para que el sistema sepa animarla
                // "viajando" hasta la misma vista marcada con esa etiqueta en DetalleActivity
                // (ver conceptos/08-transiciones.md §3).
                View imagenPulsada = view.findViewById(R.id.imagenGridView);
                ActivityOptionsCompat opciones = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(GridViewDam2Activity.this,
                                new Pair<>(imagenPulsada, DetalleActivity.VIEW_NAME_HEADER_IMAGE));
                ActivityCompat.startActivity(GridViewDam2Activity.this, intent, opciones.toBundle());
            }
        });
    }
}