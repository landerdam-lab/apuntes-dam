package com.example.ejemplodialogopersonalizado.ej;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;

public class Ej35GridActivity extends AppCompatActivity {

    private GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_grid_imagenes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        grid = findViewById(R.id.gridImagenes);
        grid.setAdapter(new ImageAdapter(getApplicationContext(),
                getResources().obtainTypedArray(R.array.ej_paisajes)));

        // Al pulsar una foto: transicion de elemento compartido hacia el detalle (ejercicio 4.5)
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Ej35GridActivity.this, Ej45DetalleFotoActivity.class);
                intent.putExtra("idFoto", position);

                // La imagen de la celda pulsada es el elemento compartido
                View imagenPulsada = view.findViewById(R.id.imagenGridView);
                ActivityOptionsCompat opciones = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        Ej35GridActivity.this,
                        new Pair<>(imagenPulsada, Ej45DetalleFotoActivity.VIEW_NAME_HEADER_IMAGE));

                ActivityCompat.startActivity(Ej35GridActivity.this, intent, opciones.toBundle());
            }
        });
    }
}
