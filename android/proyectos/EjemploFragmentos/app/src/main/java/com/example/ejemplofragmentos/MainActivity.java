package com.example.ejemplofragmentos;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplofragmentos.fragmentos.FragmentoAbajo;
import com.example.ejemplofragmentos.fragmentos.FragmentoArriba;
import com.google.android.material.appbar.MaterialToolbar;

// La pantalla principal (una Activity, ver 01-fundamentos-bundle-intent-ciclo-vida). No dibuja
// casi nada por sí misma: solo aloja dos Fragment (trozos de interfaz reutilizables, ver 16-fragmentos)
// y hace de "puente" entre ellos implementando IControlFragmentos (una interfaz, ver 00-programacion-basica §7).
public class MainActivity extends AppCompatActivity implements IControlFragmentos{

    // Se ejecuta UNA vez, al crear la pantalla. Aquí se monta todo lo inicial.
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
        // La barra superior (Toolbar) hace también de barra de menú, para que aparezcan
        // las opciones "Rojo"/"Verde" en el menú de los 3 puntos.
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Cargamos FragmentoArriba
        // .add(contenedor, fragmento) coloca el Fragment en ese hueco del layout (ver 16-fragmentos §4).
        getSupportFragmentManager().beginTransaction()
                .add( R.id.contenedor1, new FragmentoArriba())
                .commit();

        //Cargamos FragmentoAbajo
        getSupportFragmentManager().beginTransaction()
                .add( R.id.contenedor2, new FragmentoAbajo())
                .commit();
    }

    // Método de la interfaz IControlFragmentos: lo llama el menú (Rojo/Verde) para pedir
    // que se cambie el color del texto que muestra FragmentoAbajo.
    @Override
    public void cambiarColor(int color) {
        // Un Bundle es una "caja con etiquetas" para llevar datos (ver 00-programacion-basica §2
        // y 01-fundamentos-bundle-intent-ciclo-vida §2). Aquí solo lleva un número (el color).
        Bundle bundle = new Bundle();
        bundle.putInt("color",color);
        // .replace(...) quita el FragmentoAbajo actual y pone uno NUEVO con el color ya incluido
        // (no existe un método para "actualizar" un fragmento ya puesto, ver 16-fragmentos §4).
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor2, FragmentoAbajo.newInstance(bundle))
                .commit();
    }

    // Método de la interfaz IControlFragmentos: lo llama FragmentoArriba cuando se pulsa "Enviar".
    @Override
    public void cambiarTexto(String texto) {
        Bundle bundle = new Bundle();
        bundle.putString("saludo",texto);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor2, FragmentoAbajo.newInstance(bundle))
                .commit();
    }

    // Se llama al construirse el menú de los 3 puntos: infla (convierte de XML a objetos reales)
    // el fichero menu_main.xml, que define las opciones "Rojo" y "Verde".
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Se llama cada vez que el usuario toca una opción del menú.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.rojo){
            cambiarColor(Color.RED);
            return true;
        } else if (id == R.id.verde) {
            cambiarColor(Color.GREEN);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}