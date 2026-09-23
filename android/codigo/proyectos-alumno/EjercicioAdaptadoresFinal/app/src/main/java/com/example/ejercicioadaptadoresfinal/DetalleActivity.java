package com.example.ejercicioadaptadoresfinal;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// OJO: esta pantalla existe y está declarada en el manifiesto, pero NINGÚN otro fichero del proyecto
// la llega a abrir (no hay ningún "new Intent(..., DetalleActivity.class)" en todo el código) — es
// "código muerto", probablemente un resto copiado de otro proyecto (AdapterDam2) que no se terminó de usar.
public class DetalleActivity extends AppCompatActivity {

    // "public static final": una constante de texto que pertenece a la CLASE (static, ver
    // 00-programacion-basica.md §10), no a un objeto concreto, y que nunca cambia (final).
    // Serviría como "etiqueta" para una transición de elemento compartido (ver conceptos/08-transiciones.md §3),
    // pero al no usarse esta Activity, tampoco llega a usarse esta constante.
    public static final String VIEW_NAME_HEADER_IMAGE = "imagenCabecera";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}