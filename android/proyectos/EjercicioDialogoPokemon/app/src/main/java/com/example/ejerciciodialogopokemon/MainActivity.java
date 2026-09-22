package com.example.ejerciciodialogopokemon;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import com.example.ejerciciodialogopokemon.adaptadores.PokemonAdapter;
import com.example.ejerciciodialogopokemon.bbdd.AppDatabase;
import com.example.ejerciciodialogopokemon.bbdd.AppExecutors;
import com.example.ejerciciodialogopokemon.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

// Pantalla principal: lista de Pokemon filtrable por tipo (3 botones), boton para agregar
// uno nuevo (abre agregarActivity) y gestos sobre la lista para editar/eliminar.
// El ListView de Android no soporta "doble clic" de forma nativa, asi que hay que
// detectarlo a mano comparando la hora y la posicion del clic anterior (ver mas abajo).
public class MainActivity extends AppCompatActivity {

    private ListView lvPokemon;
    private Button btnPlanta, btnAgua, btnFuego, btnAgregar;
    private AppDatabase mDb;
    private PokemonAdapter pokemonAdapter;
    private int idPokemon = -1;   // declarado pero sin usar en este fichero

    // Datos para detectar el doble clic "a mano": guardamos cuando y donde fue el ultimo clic
    // simple, y si llega un segundo clic en la misma fila antes de DOBLE_CLICK_DELAY ms, se
    // considera doble clic.
    private static final long DOBLE_CLICK_DELAY = 300; // milisegundos
    private long ultimoClickTiempo = 0;
    private int ultimaPosicionClick = -1;
    // Handler = "mensajero" que ejecuta codigo en el hilo principal despues de un retraso
    // (ver conceptos/12-hilos-en-profundidad.md). Se usa para esperar a ver si el clic simple
    // se convierte en doble antes de actuar.
    private final Handler clickHandler = new Handler(Looper.getMainLooper());
    private Runnable clickSimpleRunnable;   // la accion de "editar" pendiente de ejecutarse

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
        mDb = AppDatabase.getInstance(getApplicationContext());

        Toast.makeText(this, "Base de datos preprada", Toast.LENGTH_SHORT).show();
        lvPokemon = findViewById(R.id.lvPokemon);
        // Adaptador arrancado con una lista vacia: hasta que se pulse un boton de tipo no hay
        // ningun Pokemon cargado en pantalla (evita un NullPointerException si se toca la lista
        // vacia antes de elegir un tipo).
        pokemonAdapter = new PokemonAdapter(new ArrayList<Pokemon>(), getApplicationContext(), 1);
        lvPokemon.setAdapter(pokemonAdapter);
        // Clic LARGO (mantener pulsado) = otra forma de pedir eliminar, ademas del doble clic.
        lvPokemon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                confirmarEliminar(position);
                return true;   // true = "ya he gestionado este evento", no hace falta nada mas
            }
        });
        // Clic simple = editar; doble clic = eliminar (con confirmacion).
        // Un clic simple espera DOBLE_CLICK_DELAY antes de actuar, por si llega un segundo clic.
        lvPokemon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                long tiempoActual = System.currentTimeMillis();

                if (position == ultimaPosicionClick &&
                        (tiempoActual - ultimoClickTiempo) < DOBLE_CLICK_DELAY) {

                    // ¡Doble clic detectado! Cancelamos la edicion pendiente del primer clic.
                    clickHandler.removeCallbacks(clickSimpleRunnable);
                    confirmarEliminar(position);

                    // Reseteamos para evitar detectar un triple clic como otro doble clic
                    ultimoClickTiempo = 0;
                    ultimaPosicionClick = -1;
                } else {
                    // Es un clic simple (o el primero de un posible doble clic):
                    // esperamos a ver si llega un segundo clic antes de editar.
                    ultimoClickTiempo = tiempoActual;
                    ultimaPosicionClick = position;
                    clickSimpleRunnable = new Runnable() {
                        @Override
                        public void run() {
                            editarPokemon(position);
                        }
                    };
                    clickHandler.postDelayed(clickSimpleRunnable, DOBLE_CLICK_DELAY);
                }
            }
        });
        btnAgregar = findViewById(R.id.btnAgregar);
        btnPlanta = findViewById(R.id.btnPlanta);
        btnAgua = findViewById(R.id.btnAgua);
        btnFuego = findViewById(R.id.btnFuego);
        mDb = AppDatabase.getInstance(getApplicationContext());   // llamada repetida, ya se hizo arriba

        // Boton AGREGAR: abre la pantalla para crear un Pokemon nuevo.
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), agregarActivity.class);
                startActivity(intent);
            }
        });

        // Cada boton de tipo consulta la BD filtrando por ese tipo concreto (ver CargarPokemonTipo).
        btnPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarPokemonTipo("Planta");
            }
        });

        btnAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarPokemonTipo("Agua");
            }
        });

        btnFuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarPokemonTipo("Fuego");
            }
        });

    }

    // Navega a UpdateActivity pasandole el Pokemon completo dentro de un Bundle (Pokemon
    // implementa Serializable para poder viajar entero de una pantalla a otra, ver Pokemon.java).
    private void editarPokemon(int position) {
        Pokemon pokemon = pokemonAdapter.getItem(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable("pokemon", pokemon);

        Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // Pide confirmacion antes de borrar (para no eliminar por error con un clic accidental).
    private void confirmarEliminar(int position) {
        final int idEliminar = pokemonAdapter.getItem(position).getId();
        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
        alerta.setTitle("Advertencia");
        alerta.setMessage("¿Estás seguro de que deseas eliminar el Pokemon?");
        alerta.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminar(idEliminar);
            }
        });
        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "No se ha eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        alerta.show();
    }

    // Borra el Pokemon de la base de datos. Se hace en un hilo secundario (getDiskIO) porque
    // Room no permite tocar la BD desde el hilo principal (ver conceptos/17-room-base-de-datos.md).
    private void eliminar(final int idEliminar) {
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                // @Delete necesita el objeto Pokemon completo, no solo el id, por eso primero se busca
                Pokemon usu = mDb.pokemonDao().loadPokemonById(idEliminar);
                if (usu !=null)
                {
                    mDb.pokemonDao().deletePokemon(usu);
                }
            }
        });
    }

    // Consulta los Pokemon de un tipo concreto. loadPokemonByTipo devuelve un LiveData: al
    // observarlo, cada vez que la tabla cambia (insertar/editar/borrar) esta funcion se vuelve a
    // ejecutar sola y la lista de pantalla se actualiza sin tener que recargar nada a mano.
    private void CargarPokemonTipo (String tipo) {
        mDb.pokemonDao().loadPokemonByTipo(tipo).observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                pokemonAdapter = new PokemonAdapter(pokemons, getApplicationContext(), 1);
                lvPokemon.setAdapter(pokemonAdapter);

            }
        });
    }
}