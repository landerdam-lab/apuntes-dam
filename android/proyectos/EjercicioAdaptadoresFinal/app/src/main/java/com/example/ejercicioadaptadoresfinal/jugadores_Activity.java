package com.example.ejercicioadaptadoresfinal;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejercicioadaptadoresfinal.adaptadores.JugadoresAdapter;
import com.example.ejercicioadaptadoresfinal.modelos.Equipo;
import com.example.ejercicioadaptadoresfinal.modelos.Jugador;
import com.squareup.picasso.Picasso;

// Segunda pantalla: muestra en una rejilla (GridView) los jugadores del equipo que se eligió en MainActivity.
public class jugadores_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Código comentado (deshabilitado a propósito): una transición "auto" que se probó y se dejó descartada,
        // porque el tema (themes.xml) ya declara esa misma transición para toda la app — declararla dos veces
        // provocaba conflictos (ver conceptos/08-transiciones.md).
        /*Transition auto = TransitionInflater.from(this).inflateTransition(R.transition.auto);
        getWindow().setEnterTransition(auto);*/



        setContentView(R.layout.activity_jugadores);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Se recupera el Equipo que MainActivity metió en el Intent. El "(Equipo)" delante es un CASTING
        // (ver 00-programacion-basica.md §12): le decimos a Java que trate el resultado como un Equipo.
        Equipo equipo = (Equipo)getIntent().getSerializableExtra("equipoSeleccionado");
        // OJO: a este Toast le falta ".show()" al final, así que en realidad NUNCA llega a verse en pantalla
        // (se crea el objeto Toast, pero nunca se le ordena mostrarse).
        Toast.makeText(getApplicationContext(), equipo.getNombre(), Toast.LENGTH_SHORT);
        TextView tvNombre = findViewById(R.id.tvNombreEquipo);
        ImageView ivPerfilEquipo = findViewById(R.id.ivLogoEquipo);
        tvNombre.setText(equipo.getNombre());
        Picasso.get().load(equipo.getLogo()).into(ivPerfilEquipo);   // descarga la imagen del logo desde su URL
        GridView gridView = findViewById(R.id.gvJugadores);
        // Igual que con el ListView de equipos: el GridView necesita un Adapter para saber
        // cómo dibujar cada jugador como una celda de la rejilla.
        JugadoresAdapter jugadoresAdapter = new JugadoresAdapter(equipo.getJugadores(), getApplicationContext());
        gridView.setAdapter(jugadoresAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Jugador jugador = equipo.getJugadores().get(position);
                Toast.makeText(getApplicationContext(), jugador.getNombre(), Toast.LENGTH_SHORT).show();
                // Mismo patrón que antes: meter el Jugador elegido en una caja (Bundle) y viajar con él
                // a la tercera pantalla (Detalles_Jugador).
                Bundle bundle = new Bundle();
                bundle.putSerializable("jugadorSeleccionado", jugador);
                Intent intent = new Intent(getApplicationContext(), Detalles_Jugador.class);
                intent.putExtras(bundle);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(jugadores_Activity.this);
                startActivity(intent, options.toBundle());
            }
        });
    }
}