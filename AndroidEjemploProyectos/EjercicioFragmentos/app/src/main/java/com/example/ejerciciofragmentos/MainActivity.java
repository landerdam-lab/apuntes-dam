package com.example.ejerciciofragmentos;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejerciciofragmentos.fragmentos.FragmentoAbajo;
import com.example.ejerciciofragmentos.fragmentos.FragmentoArriba;
import com.example.ejerciciofragmentos.fragmentos.FragmentoMedio;
import com.example.ejerciciofragmentos.interfaces.IControlFragmentos;
import com.example.ejerciciofragmentos.modelo.Persona;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IControlFragmentos {

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
        //CARGAMOS FRAGMENTO ARRIBA
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedorArriba,new FragmentoArriba()).commit();
        //CARGAMOS FRAGMENTO MEDIO
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedorMedio,new FragmentoMedio()).commit();
        //CARGAMOS FRAGMENTO ABAJO
        Bundle bundle = new Bundle();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedorAbajo,FragmentoAbajo.newInstance(bundle)).commit();
    }

    @Override
    public void pasarTexto(String texto) {
        Bundle bundle = new Bundle();
        bundle.putString("nombre",texto);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorMedio, new FragmentoMedio(bundle)).commit();
    }

    @Override
    public void pasarTextos(String nombre, String apellido, String fecha) {
        Bundle bundle = new Bundle();
        Persona persona = new Persona(nombre, apellido, fecha);
        personas.add(persona);
        bundle.putSerializable("personas",personas);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorAbajo, FragmentoAbajo.newInstance(bundle)).commit();
    }
}