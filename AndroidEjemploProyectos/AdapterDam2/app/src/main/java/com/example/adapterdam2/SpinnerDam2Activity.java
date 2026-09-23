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

public class SpinnerDam2Activity extends AppCompatActivity {
    private Spinner cbCursos;
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
        cbCursos=(Spinner) findViewById(R.id.cbCursos);
        cbCursos.setAdapter(new SpinnerDam2Adapter(this,//pasamos el contexto
                R.layout.spinner_per,//la vista que vamos a cargar
                getResources().getStringArray(R.array.cursos)));//pasamos el array de string

        //creamos la conexion con la siguiente pantalla
        final Intent intent=new Intent(this, DetalleSpinnerDam2Activity.class);

        cbCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (ver == 1 && position>0){
                    /*Toast.makeText(SpinnerDam2Activity.this,
                            "Has seleccionado: "+ parent.getItemAtPosition(position-1),
                            Toast.LENGTH_SHORT).show();*/
                    //Creamos la caja
                    Bundle bundle=new Bundle();
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