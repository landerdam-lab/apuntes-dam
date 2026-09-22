package com.example.ejemplodialogopersonalizado.ej;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;

import java.util.ArrayList;

public class Ej31EquiposActivity extends AppCompatActivity {

    private ArrayList<Equipo> equipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_lista_equipos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Los datos
        equipos = DatosLiga.rellenarEquipos();

        // El adaptador propio + conectarlo a la lista
        EquiposAdapter adapter = new EquiposAdapter(this, equipos);
        ListView lvEquipos = findViewById(R.id.lvEquipos);
        lvEquipos.setAdapter(adapter);

        // Al pulsar una fila: abrimos los jugadores de ese equipo (ejercicio 3.3)
        lvEquipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Equipo equipo = equipos.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("equipoSeleccionado", equipo);
                Intent intent = new Intent(getApplicationContext(), Ej32JugadoresActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
