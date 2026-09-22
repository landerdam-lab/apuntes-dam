package com.example.adapterdam2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

// Pantalla que simplemente muestra el curso elegido en el Spinner de la pantalla anterior.
public class DetalleSpinnerDam2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_spinner_dam2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Recupera el texto del curso desde el Intent que lanzó esta pantalla (ver
        // conceptos/01-fundamentos-bundle-intent-ciclo-vida.md §2.2) y lo muestra en un TextView.
        String saludo = getIntent().getStringExtra("curso");
        ((TextView)findViewById(R.id.tvDetalleSpinner)).setText(saludo);
    }
}