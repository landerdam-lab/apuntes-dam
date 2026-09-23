package com.example.ejemplodialogopersonalizado.ej;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;

public class Ej33DetalleJugadorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_detalle_jugador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Sacamos el objeto con LA MISMA clave con la que lo metimos
        Jugador jugador = (Jugador) getIntent().getSerializableExtra("jugadorSeleccionado");

        ImageView ivFoto = findViewById(R.id.ivFotoJ);
        TextView tvNombre = findViewById(R.id.tvNombreJ);
        TextView tvDorsal = findViewById(R.id.tvDorsalJ);
        TextView tvPosicion = findViewById(R.id.tvPosicionJ);

        ivFoto.setImageResource(jugador.getFoto());
        tvNombre.setText(jugador.getNombre());
        tvDorsal.setText("Dorsal: " + jugador.getDorsal());
        tvPosicion.setText("Posicion: " + jugador.getPosicion());
    }
}
