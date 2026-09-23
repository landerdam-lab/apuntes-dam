package com.example.ejemplodialogopersonalizado.ej;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;

public class Ej42AsyncActivity extends AppCompatActivity {

    //Typed Array es un array de recursos.
    private TypedArray imagenes = null;
    private ProgressBar barra = null;
    private ImageView imageCentral = null;
    private TextView texto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_async);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Obtener los fotogramas del XML: R.array es un array de Strings que se convierte en un TypedArray
        imagenes = getResources().obtainTypedArray(R.array.ej_imagenes);

        barra = findViewById(R.id.pbAnimacion);
        imageCentral = findViewById(R.id.ivAnimacion);
        texto = findViewById(R.id.tvAnimacion);

        //Parametros para el ProgressBar: valor maximo, inicio y color de fondo
        barra.setMax(100);
        barra.setProgress(0);
        barra.setBackgroundColor(Color.GRAY);

        //Creamos la tarea, pasandole "this" (la Activity), y la lanzamos
        ProgressAndando hilo = new ProgressAndando(this);
        hilo.execute();
    }

    // getters: para que la tarea (otra clase) pueda usar estas vistas
    public TypedArray getImagenes() { return imagenes; }
    public ProgressBar getBarra() { return barra; }
    public ImageView getImageCentral() { return imageCentral; }
    public TextView getTexto() { return texto; }
}
