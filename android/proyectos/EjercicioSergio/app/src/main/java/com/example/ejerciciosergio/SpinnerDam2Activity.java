package com.example.ejerciciosergio;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Segunda pantalla del proyecto. Por el nombre, estaba pensada para mostrar
// un Spinner (un desplegable de opciones), pero todavía no tiene esa lógica programada.
public class SpinnerDam2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spinner_dam2); // carga el diseño visual propio de esta pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Aquí faltaría: buscar el Spinner con findViewById, crearle un adaptador
        // con los datos a mostrar, y asignárselo. Todavía no está hecho.
    }
}