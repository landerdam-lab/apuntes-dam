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

public class MainActivity extends AppCompatActivity implements IControlFragmentos
{

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

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //CARGAMOS FRAGMENTO ARRIBA
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor1, new FragmentoArriba())
                .commit();

        //CARGAMOS FRAGMENTO ABAJO
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor2, new FragmentoAbajo())
                .commit();
    }

    @Override
    public void cambiarColor(int color)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("color", color);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor2, FragmentoAbajo.newInstance(bundle))
                .commit();
    }

    @Override
    public void cambiarTexto(String texto)
    {
        Bundle bundle = new Bundle();
        bundle.putString("saludo", texto);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor2, FragmentoAbajo.newInstance(bundle))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.rojo)
        {
            cambiarColor(Color.RED);
            return true;
        } else if(id == R.id.verde)
        {
            cambiarColor(Color.GREEN);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}