package com.example.ejemplodialogopersonalizado.ej;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;

public class SimulacroAActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_simulacro_a);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ListView: con transicion de escena
        Button btnListView = findViewById(R.id.btnListView);
        btnListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ej31EquiposActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SimulacroAActivity.this);
                startActivity(intent, options.toBundle());
            }
        });

        // Spinner: con overridePendingTransition
        Button btnSpinner = findViewById(R.id.btnSpinner);
        btnSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Ej34SpinnerActivity.class));
                overridePendingTransition(R.anim.ej_entrada, R.anim.ej_salida);
            }
        });

        // GridView: el detalle lleva la transicion de elemento compartido (se programa en el GridView)
        Button btnGridView = findViewById(R.id.btnGridView);
        btnGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Ej35GridActivity.class));
            }
        });

        // GridView con RecyclerView
        Button btnGridRecycler = findViewById(R.id.btnGridRecycler);
        btnGridRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Ej36RecyclerActivity.class));
            }
        });
    }
}
