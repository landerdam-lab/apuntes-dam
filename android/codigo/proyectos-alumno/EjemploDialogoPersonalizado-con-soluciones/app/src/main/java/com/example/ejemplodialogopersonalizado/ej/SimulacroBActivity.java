package com.example.ejemplodialogopersonalizado.ej;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;

public class SimulacroBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_menu_diseno);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Boton "Nuevo": va a una nueva actividad
        ImageButton btnNuevo = findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(v -> {
            Intent intent = new Intent(SimulacroBActivity.this, EjVaciaActivity.class);
            startActivity(intent);
        });

        // Boton "Toast": animacion de carga de 3 segundos y, al terminar, una nueva actividad vacia
        ImageButton btnToast = findViewById(R.id.btnToast);
        btnToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View vista = getLayoutInflater().inflate(R.layout.ej_toast_per, null);
                ImageView imagen = vista.findViewById(R.id.ivToast);
                imagen.setImageResource(R.drawable.ej_andando3);
                TextView texto = vista.findViewById(R.id.tvToast);
                texto.setText("Cargando...");

                final Dialog dialogo = new Dialog(SimulacroBActivity.this);
                dialogo.setContentView(vista);
                if (dialogo.getWindow() != null) {
                    dialogo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }
                dialogo.show();

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), EjVaciaActivity.class));
                        dialogo.dismiss();
                    }
                }, 3000);
            }
        });

        // Boton "Animacion": la barra de progreso con AsyncTask
        ImageButton btnAsync = findViewById(R.id.btnAsync);
        btnAsync.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Ej42AsyncActivity.class)));

        // Boton "Frames": la animacion frame a frame
        ImageButton btnFrames = findViewById(R.id.btnFrames);
        btnFrames.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Ej43FrameActivity.class)));
    }
}
