package com.example.adapterdam2;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Pantalla de menú: 3 botones, cada uno abre una pantalla distinta con una técnica
// de transición distinta (ver conceptos/08-transiciones.md).
public class MainActivity extends AppCompatActivity {

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

        //Botones
        // Botón "ListView": lanza la Activity con las OPCIONES de animación de contenido
        // (ActivityOptionsCompat), que activará la transición "slide" que ListDam2Activity configura.
        // findViewById(...) devuelve un View genérico; (Button) delante es un CASTING (00-programacion-basica.md §12)
        // que le dice a Java "trátalo como un Button" para poder llamar a setOnClickListener.
        ((Button)findViewById(R.id.btnListView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                startActivity(new Intent(getApplicationContext(),ListDam2Activity.class), options.toBundle());
                //overridePendingTransition(R.anim.entrada,R.anim.salida);
            }
        });

        // Botón "Spinner": técnica de transición más simple, con animaciones XML predefinidas
        // (res/anim/entrada.xml y salida.xml).
        ((Button)findViewById(R.id.btnSpinner)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SpinnerDam2Activity.class));
                overridePendingTransition(R.anim.entrada,R.anim.salida);
            }
        });
        // Botón "GridView": misma técnica simple que el de arriba.
        ((Button)findViewById(R.id.btnGridView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GridViewDam2Activity.class));
                overridePendingTransition(R.anim.entrada,R.anim.salida);
            }
        });
    }
}