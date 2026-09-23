package com.example.ejemplodialogopersonalizado.ej;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;

public class Ej32JugadoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_jugadores);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recogemos el equipo que nos mandan (casting: getSerializableExtra devuelve un tipo general)
        final Equipo equipo = (Equipo) getIntent().getSerializableExtra("equipoSeleccionado");

        TextView tvTituloEquipo = findViewById(R.id.tvTituloEquipo);
        tvTituloEquipo.setText(equipo.getNombre());

        GridView gridView = findViewById(R.id.gvJugadores);
        JugadoresAdapter jugadoresAdapter = new JugadoresAdapter(equipo.getJugadores(), getApplicationContext());
        gridView.setAdapter(jugadoresAdapter);

        // Al pulsar un jugador: su ficha (ejercicio 3.3)
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jugador jugador = equipo.getJugadores().get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("jugadorSeleccionado", jugador);
                Intent intent = new Intent(getApplicationContext(), Ej33DetalleJugadorActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
