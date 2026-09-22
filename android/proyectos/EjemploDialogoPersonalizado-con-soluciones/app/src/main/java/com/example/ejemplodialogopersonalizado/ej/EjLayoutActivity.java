package com.example.ejemplodialogopersonalizado.ej;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Muestra cualquier layout indicandolo por nombre en un extra (para los ejercicios que son solo XML)
public class EjLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        String nombre = getIntent().getStringExtra("layout");
        setContentView(getResources().getIdentifier(nombre, "layout", getPackageName()));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(com.example.ejemplodialogopersonalizado.R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
