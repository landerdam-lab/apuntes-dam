package com.example.ejercicioadaptadoresfinal;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejercicioadaptadoresfinal.modelos.Jugador;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class JugadorActivity extends AppCompatActivity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jugador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Jugador jugador = (Jugador) getIntent().getSerializableExtra("jugadorSeleccionado");
        //Toast.makeText(getApplicationContext(),jugador.getNombre(),Toast.LENGTH_SHORT).show();
        ImageView imageView = findViewById(R.id.ivJugador);
        TextView tvJugador  = findViewById(R.id.tvJugador);

        //CREAMOS UN DIALOGO CON TRANSICIONES
        final Dialog dialogoLander = new Dialog(this);

        Picasso.get().load(jugador.getFoto()).into(imageView);
        tvJugador.setText(jugador.getNombre());

        //CARGAMOS LA VISTA QUE ASOCIAREMOS AL DIALOGO
        View vista = getLayoutInflater().inflate(R.layout.activity_jugador,null);
        Picasso.get().load(jugador.getFoto()).into((ImageView) vista.findViewById(R.id.ivJugador));
        TransitionInflater inflater= TransitionInflater.from(getApplicationContext());
        Transition opacidad=inflater.inflateTransition(R.transition.opacidad);
        opacidad.addTarget((ImageView) vista.findViewById(R.id.ivJugador));

        ((TextView)vista.findViewById(R.id.tvJugador)).setText("POPUP");

        //CREAMOS UN EVENTO EN LA IMAGEN PARA CERRAR EL DIALOGOLANDER
        vista.findViewById(R.id.ivJugador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoLander.dismiss();
            }
        });



        dialogoLander.setContentView(vista);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogoLander.show();
            }
        });

    }
}