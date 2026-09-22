package com.example.adapterdam2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapterdam2.adaptadores.SpinnerDam2Adapter;

// Pantalla con un Spinner (el desplegable "selecciona una opción") de cursos.
// Ver conceptos/07-adaptadores.md §6 para el patrón ArrayAdapter que usa.
public class SpinnerDam2Activity extends AppCompatActivity {

    private Spinner cbCursos;

    // Bandera (0/1) para distinguir la selección automática inicial del Spinner
    // de una selección real hecha por el usuario (ver más abajo).
    private int ver = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spinner_dam2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cbCursos=findViewById(R.id.cbCursos);
        cbCursos.setAdapter(new SpinnerDam2Adapter(this,//PASAMOS EL CONTEXTO
                R.layout.spinner_per,//LA VISTA QUE VAMOS A CARGAR
                getResources().getStringArray(R.array.cursos)));//PASAMOS EL ARRAY DE STRING

        //creamos la conexion con la siguiente pantalla
        // El Intent se crea UNA SOLA VEZ aquí fuera, y se reutiliza dentro del listener
        // (a diferencia del bug visto en el proyecto equiposFutbol, donde se creaba mal dentro).
        final Intent intent=new Intent(this, DetalleSpinnerDam2Activity.class);

        // Este listener se dispara TAMBIÉN la primera vez que se monta el Spinner, aunque el
        // usuario no haya tocado nada todavía — por eso existe la bandera "ver": la ignora la
        // primera llamada (automática) y solo actúa a partir de la segunda (el usuario ya interactuó).
        cbCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(ver == 1 && position>0){
                    /*Toast.makeText(SpinnerDam2Activity.this,
                            "Has seleccionado"+parent.getItemAtPosition(position-1),
                            Toast.LENGTH_SHORT).show();*/
                    //CREAMOS LA CAJA
                    Bundle bundle = new Bundle();
                    bundle.putString("curso",parent.getItemAtPosition(position-1)+"");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                ver=1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}