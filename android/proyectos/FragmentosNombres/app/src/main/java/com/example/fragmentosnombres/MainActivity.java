package com.example.fragmentosnombres;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fragmentosnombres.Interfaces.IControlFragmentos;
import com.example.fragmentosnombres.fragmentos.FragmentoAbajo;
import com.example.fragmentosnombres.fragmentos.FragmentoArriba;
import com.example.fragmentosnombres.fragmentos.FragmentoMedio;

// Aloja tres Fragment apilados (ver 16-fragmentos): FragmentoArriba (envía texto),
// FragmentoMedio (lo recibe y lo muestra en un formulario) y FragmentoAbajo (sin terminar).
// implements IControlFragmentos: MainActivity cumple el "contrato" para que FragmentoArriba
// pueda avisarle sin conocerla directamente (ver 00-programacion-basica §7).
public class MainActivity extends AppCompatActivity implements IControlFragmentos {

    // Se ejecuta UNA vez, al crear la pantalla.
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
        // Se coloca cada fragmento en su contenedor (ver 16-fragmentos §4).
        getSupportFragmentManager().beginTransaction()
                .add( R.id.contenedor1, new FragmentoArriba())
                .commit();

        getSupportFragmentManager().beginTransaction()
                .add( R.id.contenedor2, new FragmentoMedio())
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add( R.id.contenedor3, new FragmentoAbajo())
                .commit();
    }

    // Método de la interfaz: lo llama FragmentoArriba cuando se pulsa "Enviar".
    @Override
    public void cambiarTexto(String texto) {
        // Bundle = "caja con etiquetas" para llevar datos (ver 00-programacion-basica §2).
        // La clave "nombre" tiene que coincidir EXACTAMENTE con la que lee FragmentoMedio,
        // si no, el dato llega como null en silencio (ver 16-fragmentos §8).
        Bundle bundle = new Bundle();
        bundle.putString("nombre",texto);

        // .replace(...) sustituye FragmentoMedio por uno nuevo, ya con el texto recibido.
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor2,FragmentoMedio.newInstance(bundle)).commit();
    }
}