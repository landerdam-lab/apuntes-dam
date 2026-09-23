package com.example.ejerciciopokemon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import com.example.ejerciciopokemon.adapter.PokemonAdapter;
import com.example.ejerciciopokemon.bbdd.AppDatabase;
import com.example.ejerciciopokemon.bbdd.AppExecutors;
import com.example.ejerciciopokemon.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnAnadir;
    private Button btnAgua;
    private Button btnPlanta;
    private Button btnFuego;
    private AppDatabase mydb;
    private ListView lvPokemons;
    private PokemonAdapter adapter;
    private float tiempoUltimoClick = 0;
    private int posicionClickado = -1;

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

        btnAnadir = findViewById(R.id.btnAnadirPokemon);
        btnAgua = findViewById(R.id.btnAgua);
        btnPlanta = findViewById(R.id.btnPlanta);
        btnFuego = findViewById(R.id.btnFuego);
        lvPokemons = findViewById(R.id.lvPokemons);

        lvPokemons.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Pokemon pokemon = (Pokemon) adapter.getItem(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("pokemon",pokemon);

                Intent intent = new Intent(getApplicationContext(), UpdatePokemon.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
        });

        lvPokemons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                float ahora = System.currentTimeMillis();
                if (position == posicionClickado && tiempoUltimoClick - ahora < 300) {
                    AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mydb.pokemonDao().delete((Pokemon) adapter.getItem(position));
                        }
                    });
                    //Toast.makeText(MainActivity.this, "Doble click", Toast.LENGTH_SHORT).show();
                }
                posicionClickado = position;
                tiempoUltimoClick = ahora;
            }
        });

        mydb = AppDatabase.getInstance(getApplicationContext());

        btnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterPokemon.class);
                startActivity(intent);
            }
        });

        btnAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargaPokemonsTipo("Agua");
            }
        });
        btnPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargaPokemonsTipo("Planta");
            }
        });
        btnFuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargaPokemonsTipo("Fuego");
            }
        });

    }
    private void cargaPokemonsTipo(String tipo) {
        mydb.pokemonDao().loadPokemonByType(tipo).observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                adapter = new PokemonAdapter(pokemons,getApplicationContext());
                lvPokemons.setAdapter(adapter);
                Log.d("prueba",pokemons.size()+"");
            }
        });
    }
}