package com.example.ejemplodialogopersonalizado.ej;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;

public class Ej34SpinnerActivity extends AppCompatActivity {

    private int ver = 0;   // "bandera": el Spinner dispara onItemSelected al montarse

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_spinner);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Los datos vienen de res/values (un TypedArray/array de recursos)
        String[] cursos = getResources().getStringArray(R.array.ej_cursos);

        Spinner cbCursos = findViewById(R.id.cbCursos);
        cbCursos.setAdapter(new SpinnerCursosAdapter(this, R.layout.ej_spinner_fila, cursos));

        // El Intent se crea UNA vez, fuera del listener
        final Intent intent = new Intent(this, Ej34DetalleSpinnerActivity.class);
        cbCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (ver == 1 && position > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("curso", parent.getItemAtPosition(position - 1) + "");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                ver = 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // obligatorio implementarlo, aqui no hace falta nada
            }
        });
    }
}
