package com.example.ejerciciodialogopokemon;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejerciciodialogopokemon.bbdd.AppDatabase;
import com.example.ejerciciodialogopokemon.bbdd.AppExecutors;
import com.example.ejerciciodialogopokemon.model.Pokemon;

// Pantalla para editar un Pokemon existente: llega desde MainActivity con el Pokemon completo
// (ver MainActivity.editarPokemon), precarga sus datos en los campos, y al pulsar el boton
// guarda los cambios en la base de datos.
public class UpdateActivity extends AppCompatActivity {

    private Pokemon pokemon;   // el Pokemon que se esta editando (recibido por Intent)
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mDb = AppDatabase.getInstance(getApplicationContext());
        Button btnActualizar = findViewById(R.id.btnUpdatePokemon);

        // Se recupera el Pokemon que se guardo en el Bundle en MainActivity. Funciona porque
        // Pokemon implementa Serializable (ver model/Pokemon.java).
        pokemon = (Pokemon) getIntent().getExtras().getSerializable("pokemon");

        EditText etNombre =findViewById(R.id.etNombreU);
        EditText etTipo = findViewById(R.id.etTipoU);
        EditText etImagen = findViewById(R.id.etURLImagenU);

        // Se precargan los campos con los datos actuales del Pokemon, para que el usuario
        // solo tenga que cambiar lo que quiera editar.
        etNombre.setText(pokemon.getNombre());
        etTipo.setText(pokemon.getTipo());
        etImagen.setText(pokemon.getURLimagen());

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se actualiza el mismo objeto "pokemon" (conserva su id original) con lo
                // escrito en los campos...
                pokemon.setNombre(etNombre.getText().toString());
                pokemon.setTipo(etTipo.getText().toString());
                pokemon.setURLimagen(etImagen.getText().toString());

                // ...y se guarda en la BD en un hilo secundario, como toda operacion con Room.
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.pokemonDao().updatePokemon(pokemon);
                    }
                });
            }
        });
    }
}