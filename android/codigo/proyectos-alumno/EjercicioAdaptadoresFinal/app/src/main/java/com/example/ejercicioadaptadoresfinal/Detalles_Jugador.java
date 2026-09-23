package com.example.ejercicioadaptadoresfinal;

import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejercicioadaptadoresfinal.modelos.Equipo;
import com.example.ejercicioadaptadoresfinal.modelos.Jugador;
import com.squareup.picasso.Picasso;

// Tercera y última pantalla del flujo: la ficha de un jugador concreto.
public class Detalles_Jugador extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Se configura una animación de entrada "slide" (deslizar) PROPIA de esta pantalla, distinta
        // de la del resto de la app. Tiene que ir ANTES de setContentView() para funcionar bien
        // (ver conceptos/08-transiciones.md).
        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);

        getWindow().setEnterTransition(slide);

        setContentView(R.layout.activity_detalles_jugador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Se recupera el Jugador que viajó en el Intent desde jugadores_Activity.
        Jugador jugador = (Jugador)getIntent().getSerializableExtra("jugadorSeleccionado");
        // OJO: igual que en jugadores_Activity, a este Toast le falta ".show()", así que no llega a verse.
        Toast.makeText(getApplicationContext(), jugador.getNombre(), Toast.LENGTH_SHORT);
        TextView tvNombre = findViewById(R.id.tvNombreJ);
        TextView tvDorsal = findViewById(R.id.tvDorsalJ);
        TextView tvPosicion = findViewById(R.id.tvPosicionJ);
        ImageView ivPerfilEquipo = findViewById(R.id.ivFotoJugador);
        tvNombre.setText(jugador.getNombre());
        tvDorsal.setText(jugador.getDorsal()+"");
        tvPosicion.setText(jugador.getPosicion());
        Picasso.get().load(jugador.getFoto()).into(ivPerfilEquipo);
    }
}