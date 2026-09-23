package com.example.ejerciciopokemon;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejerciciopokemon.bbdd.AppDatabase;
import com.example.ejerciciopokemon.bbdd.AppExecutors;
import com.example.ejerciciopokemon.model.Pokemon;

public class UpdatePokemon extends AppCompatActivity {

    private Pokemon pokemon;
    private AppDatabase mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_pokemon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mydb = AppDatabase.getInstance(getApplicationContext());
        Button btnActualizar = findViewById(R.id.btnUpdatePokemon);

        pokemon = (Pokemon) getIntent().getExtras().getSerializable("pokemon");

        EditText etNombre = findViewById(R.id.etUpdateNombre);
        EditText etTipo = findViewById(R.id.etUpdateTipo);
        EditText etPokedex = findViewById(R.id.etUpdatePokedex);
        EditText etFoto = findViewById(R.id.etUpdateFoto);

        etNombre.setText(pokemon.getNombre());
        etTipo.setText(pokemon.getTipo());
        etPokedex.setText(pokemon.getPokedex() + "");
        etFoto.setText(pokemon.getFoto());

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokemon.setNombre(etNombre.getText().toString());
                pokemon.setTipo(etTipo.getText().toString());
                pokemon.setPokedex(Integer.parseInt(etPokedex.getText().toString()));
                pokemon.setFoto(etFoto.getText().toString());

                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mydb.pokemonDao().updatePokemon(pokemon);
                    }
                });
            }
        });
    }
}