package com.example.ejercicioadaptadoresfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejercicioadaptadoresfinal.adaptadores.EquiposAdapter;
import com.example.ejercicioadaptadoresfinal.modelos.Equipo;
import com.example.ejercicioadaptadoresfinal.modelos.Jugador;
import com.example.ejercicioadaptadoresfinal.modelos.Liga;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvEquipos;
    private Liga nba;



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

        rellenarEquipos();
        lvEquipos = findViewById(R.id.lvEquipos);
        lvEquipos.setAdapter(new EquiposAdapter(nba.getEquipos(),this));

        lvEquipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Equipo equipo = nba.getEquipos().get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("equipoSeleccionado", equipo);
                Intent intent = new Intent(getApplicationContext(), jugadores_Activity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void rellenarEquipos(){
        nba = new Liga();
        Equipo lakers;
        Equipo bulls;
        Equipo celtics;

        ArrayList<Jugador> jugadores = new ArrayList<>();

        jugadores.add(new Jugador("lebron",23,"Alero","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzdAqiwoRffTILgGBnuOR27MWiChkS0bSW-NilVssb_g&s"));
        jugadores.add(new Jugador("lebron",23,"Alero","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzdAqiwoRffTILgGBnuOR27MWiChkS0bSW-NilVssb_g&s"));
        jugadores.add(new Jugador("lebron",23,"Alero","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzdAqiwoRffTILgGBnuOR27MWiChkS0bSW-NilVssb_g&s"));

        lakers=new Equipo("Lakers","Angeles","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT6t7aHHe0sA1wpoIvwoBL1zrNfsTQg-FWbhm6-trY8sg&s=10",1,jugadores);
        bulls=new Equipo("Lakers","Angeles","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT6t7aHHe0sA1wpoIvwoBL1zrNfsTQg-FWbhm6-trY8sg&s=10",1,jugadores);
        celtics=new Equipo("Lakers","Angeles","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT6t7aHHe0sA1wpoIvwoBL1zrNfsTQg-FWbhm6-trY8sg&s=10",1,jugadores);

        ArrayList<Equipo> equipos = new ArrayList<Equipo>();

        equipos.add(lakers);
        equipos.add(bulls);
        equipos.add(celtics);

        nba.setEquipos(equipos);
    }
}