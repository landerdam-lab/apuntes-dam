package com.example.ejerciciospinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int ver=0;
    private ArrayList<String> datos=new ArrayList<>();
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

        rellenarDatos();
        ((Spinner)findViewById(R.id.spEjercicio)).setAdapter(new EjercicioSpinerAdapter(this,-1,datos));
        final Intent intent=new Intent(this, DetalleEjercicioSpinnerActivity.class);
        ((Spinner)findViewById(R.id.spEjercicio)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (ver == 1) {
                    Bundle bundle=new Bundle();
                    bundle.putString("jugador",datos.get(position));
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

    private void rellenarDatos(){
        datos.add("Luka Doncic");
        datos.add("Austin Reaves");
        datos.add("Rui Hachimura");
        datos.add("Bronny James");
        datos.add("Gave Vicent");
        datos.add("Luka Doncic jr");
    }

}