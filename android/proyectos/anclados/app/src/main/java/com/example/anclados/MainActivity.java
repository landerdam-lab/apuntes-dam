package com.example.anclados;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Esta clase representa UNA PANTALLA de la app (una "Activity").
// "extends AppCompatActivity" significa que HEREDA todo el comportamiento ya hecho
// de la clase AppCompatActivity (gestionar la pantalla, su ciclo de vida, etc.)
// y aquí solo añadimos lo propio de esta pantalla.
public class MainActivity extends AppCompatActivity {

    // onCreate() es un MÉTODO que Android llama automáticamente UNA VEZ,
    // justo cuando esta pantalla se crea. Aquí es donde se monta todo.
    // @Override indica que estamos reemplazando un método que ya existía en la clase padre.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // deja que la clase padre haga su propia inicialización primero
        EdgeToEdge.enable(this); // hace que el contenido se dibuje a pantalla completa (por detrás de las barras del sistema)
        setContentView(R.layout.activity_main); // carga el diseño visual (el XML) que se ve en esta pantalla
        // Este bloque evita que las barras del sistema (hora, batería...) tapen el contenido de la pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}