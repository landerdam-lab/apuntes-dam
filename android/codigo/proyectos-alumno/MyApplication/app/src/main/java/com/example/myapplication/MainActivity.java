package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.fragmentos.GaleriaFragment;
import com.example.myapplication.fragmentos.InicioFragment;
import com.example.myapplication.fragmentos.MapaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private FloatingActionButton fabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        bottomNav = findViewById(R.id.bottom_nav_view);
        fabMenu = findViewById(R.id.fab_menu);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contenedor, new InicioFragment())
                .commit();

        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomNav.getVisibility() == View.VISIBLE) {
                    cerrarMenu();
                } else {
                    abrirMenu();
                }
            }
        });

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_Inicio) {
                    cambiarVentana(new InicioFragment());
                } else if (id == R.id.nav_Mapa) {
                    cambiarVentana(new MapaFragment());
                } else if (id == R.id.nav_Galeria) {
                    cambiarVentana(new GaleriaFragment());
                }
                cerrarMenu();
                return true;
            }
        });
    }

    private void cambiarVentana(Fragment fragmento) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor, fragmento)
                .commit();
    }

    private void abrirMenu() {
        bottomNav.setVisibility(View.VISIBLE);
        fabMenu.setImageResource(R.drawable.ic_close_black_24dp);   // la burbuja pasa a ser una X
    }

    private void cerrarMenu() {
        bottomNav.setVisibility(View.GONE);
        fabMenu.setImageResource(R.drawable.ic_menu_black_24dp);    // vuelve el icono de menu
    }
}
