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
import androidx.room.RoomDatabase;

import com.example.ejerciciodialogopokemon.bbdd.AppDatabase;
import com.example.ejerciciodialogopokemon.bbdd.AppExecutors;
import com.example.ejerciciodialogopokemon.model.Pokemon;

// Pantalla para crear un Pokemon nuevo: 3 campos de texto (nombre, tipo, URL de imagen)
// y un boton que lo guarda en la base de datos.
public class agregarActivity extends AppCompatActivity {

    private Button btnAgregar;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mDb = AppDatabase.getInstance(getApplicationContext());

        btnAgregar = findViewById(R.id.btnAgregarPokemon);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se leen los 3 campos justo al pulsar el boton (no antes), para coger siempre
                // lo ultimo que haya escrito el usuario.
                EditText etNombre = findViewById(R.id.etNombre);
                EditText etTipo = findViewById(R.id.etTipo);
                EditText etImagen = findViewById(R.id.etURLImagen);
                Pokemon pokemon = new Pokemon(
                        etNombre.getText().toString(),
                        etTipo.getText().toString(),
                        etImagen.getText().toString()
                );
                // Insertar en la BD tiene que hacerse en un hilo secundario (getDiskIO), nunca en
                // el principal, o la app se congelaria (ver conceptos/17-room-base-de-datos.md).
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.pokemonDao().insertPokemon(pokemon);
                    }
                });
                // OJO: no hay ningun aviso (Toast) ni se vuelve a la pantalla anterior tras guardar,
                // asi que aunque el guardado funcione, en pantalla no se nota nada.
            }
        });
    }
}