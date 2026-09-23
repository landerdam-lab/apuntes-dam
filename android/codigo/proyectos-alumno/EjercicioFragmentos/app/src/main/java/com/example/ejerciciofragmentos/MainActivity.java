package com.example.ejerciciofragmentos;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejerciciofragmentos.fragmentos.Fragmento1;
import com.example.ejerciciofragmentos.fragmentos.Fragmento2;
import com.example.ejerciciofragmentos.fragmentos.Fragmento3;
import com.example.ejerciciofragmentos.modelos.Persona;

import java.util.ArrayList;

// Orquesta un formulario de 3 pasos hecho con Fragment (ver 16-fragmentos): Fragmento1 pide el
// nombre, Fragmento2 pide apellido+fecha, y Fragmento3 va acumulando cada ficha completa en una
// rejilla. MainActivity implementa IControlFragmentos para que los fragmentos puedan avisarle
// cuando el usuario termina cada paso, sin conocerla directamente (ver 00-programacion-basica §7).
public class MainActivity extends AppCompatActivity implements IControlFragmentos{

    // ArrayList (ver 00-programacion-basica §8): una lista que va creciendo con cada ficha
    // (Persona) que el usuario completa. Vive aquí, como atributo de la Activity, para que
    // sobreviva mientras la pantalla esté abierta.
    private ArrayList<Persona> personas = new ArrayList<>();

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

        // Se colocan los 3 fragmentos, uno por contenedor (ver 16-fragmentos §4). Fragmento3 se
        // crea con un Bundle vacío la primera vez (todavía no hay ninguna ficha completada).
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor1, new Fragmento1()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor2, new Fragmento2()).commit();

        Bundle bundle = new Bundle();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor3, Fragmento3.newInstance(bundle)).commit();
    }

    // Método de la interfaz: lo llama Fragmento1 cuando el usuario ya escribió su nombre.
    @Override
    public void onNombreCompletado(String nombre) {
        Bundle bundle = new Bundle();
        bundle.putString("nombre", nombre);
        // Se reemplaza Fragmento2 por uno nuevo, con el nombre ya precargado en su Bundle.
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor2, Fragmento2.newInstance(bundle)).commit(); //New Fragmento2(bundle)
    }

    // Método de la interfaz: lo llama Fragmento2 cuando el usuario completó apellido y fecha.
    @Override
    public void onApellidoYFechaCompletado(String nombre, String apellido, String fecha) {
        Bundle bundle = new Bundle();
        // Se crea el objeto Persona (ver 00-programacion-basica §5) con los 3 datos ya reunidos...
        Persona persona = new Persona(nombre, apellido, fecha);
        // ...y se añade a la lista acumulada (nunca se borran las fichas anteriores).
        personas.add(persona);
        // putSerializable mete la LISTA COMPLETA en el Bundle (ver 16-fragmentos §7) — hace falta
        // que Persona implemente Serializable para que esto compile.
        bundle.putSerializable("personas", personas);
        // Se reemplaza Fragmento3 por uno nuevo con la lista actualizada; al no tener memoria
        // propia, Fragmento3 necesita recibir siempre la lista entera, no solo la ficha nueva.
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor3, Fragmento3.newInstance(bundle))
                .commit();
    }
}