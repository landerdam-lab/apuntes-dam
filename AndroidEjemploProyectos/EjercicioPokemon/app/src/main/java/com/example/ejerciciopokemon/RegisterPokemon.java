package com.example.ejerciciopokemon;

import android.os.Bundle;
import android.util.Log;
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

public class RegisterPokemon extends AppCompatActivity {

    private Button btnCrear;
    private AppDatabase mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_pokemon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCrear = findViewById(R.id.btnCrearPokemon);

        mydb = AppDatabase.getInstance(getApplicationContext());

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etNombre= findViewById(R.id.etNombre);
                EditText etTipo = findViewById(R.id.etTipo);
                EditText etPokedex = findViewById(R.id.etPokedex);
                EditText etFoto = findViewById(R.id.etFoto);

                int numPoke = Integer.parseInt(etPokedex.getText().toString());

                final Pokemon pokemon = new Pokemon(
                        etNombre.getText().toString(),
                        etTipo.getText().toString(),
                        etFoto.getText().toString(),
                        numPoke
                        );

                Log.d("prueba",Integer.getInteger(etPokedex.getText().toString())+"");
                Log.d("prueba",etFoto.getText().toString()+"***************************");
                Log.d("prueba",etTipo.getText().toString()+"***************************");

                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mydb.pokemonDao().insertPokemon(pokemon);
                    }
                });
            }
        });
    }
}