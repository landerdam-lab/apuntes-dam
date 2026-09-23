package com.example.ejemplodialogopersonalizado.ej;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;
import com.google.android.material.appbar.MaterialToolbar;

public class Ej51FragmentosActivity extends AppCompatActivity implements IControlFragmentos {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_fragmentos_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // La barra superior hace de ActionBar (para que aparezca el menu de los 3 puntos)
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Se cargan los fragmentos solo la primera vez (evita duplicarlos al recrear la actividad)
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contenedor1, new FragmentoArriba())
                    .commit();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contenedor2, FragmentoAbajo.newInstance(new Bundle()))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ej_menu_colores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.rojo) {
            cambiarColor(Color.RED);
            return true;
        } else if (id == R.id.verde) {
            cambiarColor(Color.GREEN);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void cambiarColor(int color) {
        Bundle bundle = new Bundle();
        bundle.putInt("color", color);

        // Se recarga el fragmento de abajo con el nuevo color
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor2, FragmentoAbajo.newInstance(bundle))
                .commit();
    }

    @Override
    public void cambiarTexto(String texto) {
        Bundle bundle = new Bundle();
        bundle.putString("saludo", texto);

        // Se recarga el fragmento de abajo con el nuevo texto
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor2, FragmentoAbajo.newInstance(bundle))
                .commit();
    }
}
