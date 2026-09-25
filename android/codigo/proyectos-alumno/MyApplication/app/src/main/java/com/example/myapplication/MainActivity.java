package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.fragmentos.GaleriaFragment;
import com.example.myapplication.fragmentos.InicioFragment;
import com.example.myapplication.fragmentos.MapaFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

// Pantalla principal: tiene la Toolbar morada, el hueco donde se cargan los fragmentos
// y el menu lateral (drawer) con la cabecera de la foto
public class MainActivity extends AppCompatActivity {

    // Se declaran aqui fuera para poder usarlas en todos los metodos de la clase
    private DrawerLayout desplegable;      // la "caja" que sabe abrir y cerrar el menu lateral
    private NavigationView navView;        // el menu lateral en si (Inicio, Mapa, Galeria)
    private MaterialToolbar toolBar;       // la barra morada de arriba

    // Android llama a este metodo solo, al crear la pantalla
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // La app se dibuja en toda la pantalla, tambien detras de la hora y la bateria
        EdgeToEdge.enable(this);
        // Carga el diseño de activity_main.xml (a partir de aqui ya se puede usar findViewById)
        setContentView(R.layout.activity_main);
        // Mete padding en "main" para que el contenido no quede debajo de las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            // Se devuelve insets (y no CONSUMED) para que el menu lateral tambien los reciba
            return insets;
        });

        // Buscar en el XML cada vista por su id y guardarla en su variable
        desplegable = findViewById(R.id.desplegable);
        navView = findViewById(R.id.nav_view);
        toolBar = findViewById(R.id.toolBar);

        // Usar la Toolbar como barra de arriba de la app
        setSupportActionBar(toolBar);

        // El boton de las 3 rayas de la Toolbar: abre y cierra el menu lateral
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, desplegable, toolBar,
                R.string.abrir_menu, R.string.cerrar_menu);
        desplegable.addDrawerListener(toggle);
        toggle.syncState();
        // Pintar las 3 rayas de blanco para que se vean sobre el morado
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.white));

        // Al arrancar se mete el fragmento de Inicio en el FrameLayout "contenedor"
        getSupportFragmentManager().beginTransaction()
                .add(R.id.contenedor, new InicioFragment())
                .commit();
        // Titulo de la Toolbar al empezar (setTitle de la Activity cambia el de la Toolbar)
        setTitle(R.string.menu_Inicio);

        // Que hacer cuando se pulsa una opcion del menu lateral
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Id de la opcion pulsada (son los ids de res/menu/bottom_navigation.xml)
                int id = item.getItemId();
                // Segun la opcion, se carga un fragmento u otro
                if (id == R.id.nav_Inicio) {
                    cambiarVentana(new InicioFragment());
                } else if (id == R.id.nav_Mapa) {
                    cambiarVentana(new MapaFragment());
                } else if (id == R.id.nav_Galeria) {
                    cambiarVentana(new GaleriaFragment());
                }
                // El titulo de la Toolbar pasa a ser el de la opcion elegida
                setTitle(item.getTitle());
                // Despues de elegir, se cierra el menu
                cerrarMenu();
                // true = "he gestionado el clic", y la opcion queda marcada en el menu
                return true;
            }
        });
    }

    // Quita el fragmento que haya en "contenedor" y pone el que le pasamos
    private void cambiarVentana(Fragment fragmento) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor, fragmento)
                .commit();
    }

    // Cierra el menu lateral. START = el lado izquierdo (layout_gravity="start" en el XML)
    private void cerrarMenu() {
        desplegable.closeDrawer(GravityCompat.START);
    }
}
