package com.example.ejerciciosergio;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Esta clase es UNA PANTALLA de la app (una "Activity").
// "extends AppCompatActivity" = hereda todo lo ya construido en AppCompatActivity.
public class MainActivity extends AppCompatActivity {

    // Se ejecuta automáticamente UNA VEZ, al crearse esta pantalla.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // inicialización obligatoria de la clase padre
        EdgeToEdge.enable(this); // pantalla completa, dibujando por detrás de las barras del sistema
        setContentView(R.layout.activity_main); // carga el diseño visual (XML) de esta pantalla
        // Añade un margen para que el contenido no quede tapado por las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Esta pantalla todavía no tiene ningún botón ni lógica propia añadida,
        // solo el arranque estándar que genera Android Studio por defecto.
    }
}