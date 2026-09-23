package com.example.equiposfutbol;

import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Pantalla pensada para mostrar los jugadores del equipo elegido, en una rejilla — pero está SIN
// TERMINAR: nunca lee el equipo que se supone que le manda MainActivity, nunca busca el GridView
// con findViewById, y nunca le asigna ningún Adapter. Solo hace el arranque estándar de cualquier
// pantalla (ver conceptos/01-fundamentos-bundle-intent-ciclo-vida.md §5).
public class GridViewActivity extends AppCompatActivity {
    private GridView grid;   // declarado pero nunca conectado con findViewById (sigue valiendo null)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grid_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Aquí faltaría: leer el Intent con el nombre del equipo, buscar "grid" con findViewById,
        // y crear+asignar un ImageAdapter con los jugadores de ese equipo.
    }
}