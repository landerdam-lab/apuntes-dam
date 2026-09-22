package com.example.ejercicioadaptadoresfinal;

import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejercicioadaptadoresfinal.adaptadores.EquiposAdapter;
import com.example.ejercicioadaptadoresfinal.modelos.Equipo;
import com.example.ejercicioadaptadoresfinal.modelos.Jugador;
import com.example.ejercicioadaptadoresfinal.modelos.Liga;

import java.util.ArrayList;

// Pantalla principal (la primera que se ve al abrir la app): muestra la lista de equipos de la liga.
// "extends AppCompatActivity" significa que esta clase HEREDA todo el comportamiento de una pantalla
// Android ya construido (ver conceptos/00-programacion-basica.md §6) y aquí solo añadimos lo propio de esta pantalla.
public class MainActivity extends AppCompatActivity {

    // ATRIBUTOS de la clase (ver 00-programacion-basica.md §5): datos que esta pantalla necesita recordar
    // mientras esté abierta.
    private ListView lvEquipos;   // el widget de lista donde se ven los equipos

    private Liga nba;             // el objeto que contiene TODOS los equipos y, dentro de cada uno, sus jugadores

    // onCreate() es el MÉTODO que Android llama automáticamente al crear esta pantalla (una única vez).
    // Aquí se "monta" todo lo que hay en la pantalla: el layout, los datos, los listeners de los botones, etc.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);   // carga el XML de esta pantalla como interfaz visual
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rellenarEquipo();   // crea los datos de ejemplo (equipos + jugadores) a mano
        lvEquipos = findViewById(R.id.lvEquipos);
        // Un ListView no sabe por sí solo cómo dibujar cada fila: necesita un "Adapter" que traduzca
        // cada Equipo de la lista en una fila visual (ver conceptos/07-adaptadores.md).
        lvEquipos.setAdapter(new EquiposAdapter(this, nba.getEquipos()));

        // Este listener (un objeto que "escucha" el evento de tocar un elemento) se ejecuta cada vez
        // que el usuario pulsa un equipo de la lista.
        lvEquipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // "position" es la posición (0, 1, 2...) del equipo que se pulsó dentro de la lista
                Equipo equipo = nba.getEquipos().get(position);
                //Toast.makeText(getApplicationContext(), equipo.getNombre(), Toast.LENGTH_SHORT).show();
                // Se mete el equipo elegido en una "caja" (Bundle) para poder llevarlo a la siguiente pantalla.
                // Equipo puede meterse entero porque implementa Serializable (ver Equipo.java).
                Bundle bundle = new Bundle();
                bundle.putSerializable("equipoSeleccionado", equipo);
                // Opciones de animación al cambiar de pantalla (ver conceptos/08-transiciones.md).
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                // Intent = "orden" de abrir otra pantalla (jugadores_Activity), ver conceptos/01-fundamentos...
                Intent intent = new Intent(getApplicationContext(), jugadores_Activity.class);
                intent.putExtras(bundle);   // se "engancha" la caja de datos al Intent para que viaje con él
                startActivity(intent, options.toBundle());   // se abre la nueva pantalla
            }
        });
    }

    // Método auxiliar: rellena "a mano" los datos de ejemplo (en una app real vendrían de internet o una base
    // de datos). No lo llama nadie de fuera de esta clase, por eso es "private".
    private void rellenarEquipo()
    {
        nba = new Liga();
        Equipo lakers;
        Equipo bulls;
        Equipo celtics;

        ArrayList<Jugador> jugadores = new ArrayList<>();   // lista que puede crecer (ver 00-programacion-basica.md §8)

        jugadores.add(new Jugador("Lebron James", 23, "Alero", "https://cdn.nba.com/headshots/nba/latest/1040x760/2544.png"));
        jugadores.add(new Jugador("Lebron", 23, "Alero", "https://cdn.nba.com/headshots/nba/latest/1040x760/2544.png"));
        jugadores.add(new Jugador("Lebron", 23, "Alero", "https://cdn.nba.com/headshots/nba/latest/1040x760/2544.png"));
        jugadores.add(new Jugador("Lebron", 23, "Alero", "https://cdn.nba.com/headshots/nba/latest/1040x760/2544.png"));
        jugadores.add(new Jugador("Lebron", 23, "Alero", "https://cdn.nba.com/headshots/nba/latest/1040x760/2544.png"));

        // OJO: los tres equipos comparten la MISMA lista "jugadores" (se les pasa la misma variable a los tres),
        // así que en realidad los tres "ven" exactamente los mismos jugadores en memoria.
        lakers = new Equipo("Lakers", "Los Angeles", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLTApwM3p2tcjtJcoStpsQcGejPj0cMy3ykmhLXphSDbohNluIWaW0RQu1&s=10", jugadores, 1);
        bulls = new Equipo("Bulls", "Chicago", "https://static.vecteezy.com/system/resources/thumbnails/029/721/170/small_2x/chicago-bulls-logo-basketball-free-vector.jpg", jugadores, 1);
        celtics = new Equipo("Celtics", "Boston", "https://dejpknyizje2n.cloudfront.net/media/carstickers/versions/boston-celtics-nba-logo-sticker-ub684-8601-x418.png", jugadores, 1);

        ArrayList<Equipo> equipos = new ArrayList<Equipo>();

        equipos.add(lakers);
        equipos.add(bulls);
        equipos.add(celtics);

        nba.setEquipos(equipos);
    }
}