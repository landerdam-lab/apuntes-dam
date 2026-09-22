package com.example.equiposfutbol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.equiposfutbol.adaptadores.EquiposAdapter;
import com.example.equiposfutbol.model.Equipos;
import com.example.equiposfutbol.model.Jugador;

import java.util.ArrayList;

// Pantalla principal: lista de equipos de fútbol. Este proyecto está A MEDIO TERMINAR
// (ver documentacion/proyectos/equiposFutbol.md para el detalle completo de qué falta).
public class MainActivity extends AppCompatActivity {
    // "final" aquí significa que la propia VARIABLE nunca va a apuntar a otra lista distinta
    // (pero sí se le pueden seguir añadiendo elementos dentro con .add(...)).
    private final ArrayList<Equipos> equipos = new ArrayList<>();
    private final ArrayList<Jugador> jugadores = new ArrayList<>();   // se declara pero nunca se rellena (ver rellenarJugadores más abajo)
    private ListView listaEquipos;
    private int ver = 0;   // declarado pero sin usar en este fichero

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rellenarEquipos();     // esta sí rellena datos de ejemplo
        rellenarJugadores();   // esta está vacía (ver más abajo) — no hace nada todavía
        EquiposAdapter adapter = new EquiposAdapter(this, equipos);
        listaEquipos = findViewById(R.id.lvEquipo);
        listaEquipos.setAdapter(adapter);

        // Se crea UN Intent aquí fuera, con la intención de reutilizarlo dentro del listener de abajo...
        final Intent intent=new Intent(this, MainActivity.class);

        listaEquipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Equipos seleccionarEquipo = equipos.get(position);
                // ...aquí se le añade el nombre del equipo al Intent de FUERA...
                intent.putExtra("equipo", seleccionarEquipo.getNombre());
                // BUG REAL: esta línea declara una variable NUEVA también llamada "intent", con el mismo
                // nombre que la de fuera — esto se llama "shadowing" (ver 00-programacion-basica.md §2 y
                // documentacion/proyectos/equiposFutbol.md para el detalle). A partir de aquí, dentro de
                // este bloque, "intent" ya NO es el de fuera (el que lleva el nombre del equipo) sino
                // este nuevo, que va completamente vacío.
                Intent intent = new Intent(MainActivity.this, GridViewActivity.class);
                // Por eso aquí se lanza la pantalla SIN el dato del equipo seleccionado — el "equipo" nunca
                // llega a GridViewActivity, aunque un poco más arriba pareciera que sí se había guardado.
                startActivity(intent);
            }
        });
    }

    // Rellena "a mano" tres equipos de ejemplo (datos que en una app real vendrían de una base de datos).
    private void rellenarEquipos(){
        equipos.add((new Equipos("atleticomadrid", 80,R.drawable.atleticomadrid)));
        equipos.add((new Equipos("barca", 80,R.drawable.barca)));
        equipos.add((new Equipos("real madrid", 80,R.drawable.realmadrid)));

    }

    // OJO: método vacío — está pensado para rellenar la lista "jugadores" (arriba), pero todavía
    // no tiene ningún código dentro, así que esa lista se queda vacía toda la ejecución.
    private void rellenarJugadores(){

    }


}