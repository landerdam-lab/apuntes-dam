package com.example.ejemplodialogopersonalizado.ej;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;

// Menu para abrir cada solucion de los ejercicios con un toque
public class EjMenuActivity extends AppCompatActivity {

    private static final String[] TITULOS = {
            "1.2  Contador con boton",
            "1.3  EditText + Toast",
            "1.4  Ir a otra pantalla con un Bundle",
            "2.1  Tres columnas con pesos 1:2:1",
            "2.2  Rejilla 2x2",
            "2.3  ConstraintLayout (ficha)",
            "2.4  Menu de diseno (EjercicioDiseno)",
            "3.1-3.3  Equipos > Jugadores > Detalle",
            "3.4  Spinner personalizado",
            "3.5  GridView + Glide (+ elemento compartido)",
            "3.6  RecyclerView en cuadricula",
            "3.7  Picasso (imagen de internet)",
            "4.1  Toast personalizado",
            "4.2  AsyncTask (barra de progreso)",
            "4.3  Frame a frame (Play/Stop)",
            "4.4  Transiciones",
            "5.1-5.3  FragmentoArriba / FragmentoAbajo",
            "5.4  Formulario de 3 fragmentos",
            "6.1  AlertDialog",
            "6.2  DialogFragment (login)",
            "6.3  Room: notas (CRUD)",
            "Simulacro A  Menu de adaptadores",
            "Simulacro B  Menu de diseno + toast + animaciones"
    };

    private static final Class<?>[] PANTALLAS = {
            Ej12ContadorActivity.class,
            Ej13ToastActivity.class,
            Ej14OrigenActivity.class,
            EjLayoutActivity.class,
            EjLayoutActivity.class,
            EjLayoutActivity.class,
            EjLayoutActivity.class,
            Ej31EquiposActivity.class,
            Ej34SpinnerActivity.class,
            Ej35GridActivity.class,
            Ej36RecyclerActivity.class,
            Ej37PicassoActivity.class,
            Ej41ToastPerActivity.class,
            Ej42AsyncActivity.class,
            Ej43FrameActivity.class,
            Ej44TransicionesActivity.class,
            Ej51FragmentosActivity.class,
            Ej54FichasActivity.class,
            Ej61DialogosActivity.class,
            Ej62LoginActivity.class,
            Ej63NotasActivity.class,
            SimulacroAActivity.class,
            SimulacroBActivity.class
    };

    // Nombre del layout para las entradas que usan EjLayoutActivity (null en el resto)
    private static final String[] LAYOUTS = {
            null, null, null,
            "ej_pesos_fila", "ej_rejilla", "ej_ficha", "ej_menu_diseno",
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lvMenu = findViewById(R.id.lvMenu);
        lvMenu.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TITULOS));

        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EjMenuActivity.this, PANTALLAS[position]);
                if (LAYOUTS[position] != null) {
                    intent.putExtra("layout", LAYOUTS[position]);
                }
                startActivity(intent);
            }
        });
    }
}
