package com.example.ejercicioadaptadoresfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Grid;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejercicioadaptadoresfinal.adaptadores.JugadoresAdapter;
import com.example.ejercicioadaptadoresfinal.modelos.Equipo;
import com.example.ejercicioadaptadoresfinal.modelos.Jugador;
import com.squareup.picasso.Picasso;

public class jugadores_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jugadores);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Equipo equipo = (Equipo) getIntent().getSerializableExtra("equipoSeleccionado");
        TextView tvNombre = findViewById(R.id.tvEquipo);
        ImageView ivPerfilEquipo = findViewById(R.id.ivLogoEquipo);
        tvNombre.setText(equipo.getNombre());
        Picasso.get().load(equipo.getLogo()).into(ivPerfilEquipo);
        GridView gridView = findViewById(R.id.gvJugadores);
        JugadoresAdapter jugadoresAdapter = new  JugadoresAdapter(equipo.getJugadores(), getApplicationContext());
        gridView.setAdapter(jugadoresAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jugador jugador = equipo.getJugadores().get(position);
               // Toast.makeText(getApplicationContext(),jugador.getNombre(),Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putSerializable("jugadorSeleccionado",jugador);
                Intent intent = new Intent(getApplicationContext(), JugadorActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}