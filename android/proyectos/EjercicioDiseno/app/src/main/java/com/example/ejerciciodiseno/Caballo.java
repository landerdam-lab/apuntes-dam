package com.example.ejerciciodiseno;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Pantalla de la animación del caballo: muestra una barra de progreso mientras
// va cambiando la imagen central (efecto "caballo andando"), usando una AsyncTask
// en segundo plano (ver conceptos/05-asynctask-e-hilos.md).
public class Caballo extends AppCompatActivity {

    // Atributos con getters/setters públicos (ver 00-programacion-basica.md §9) para que
    // la tarea de fondo ProgresoCaballo pueda leer y actualizar estas vistas desde fuera.
    private TypedArray imagenes = null; // los "fotogramas" del caballo

    private ProgressBar barra = null; // la barra de progreso (0 a 100)

    private ImageView imageCentral = null; // dónde se dibuja cada fotograma

    private TextView texto = null; // el texto "0%".."99%"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_caballo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imagenes = getResources().obtainTypedArray((R.array.imagenes));
        barra = findViewById(R.id.progressBar);
        imageCentral = findViewById(R.id.imageView2);
        texto = findViewById(R.id.textView);

        barra.setMax(100);
        barra.setProgress(0);
        barra.setBackgroundColor(Color.GRAY);
        // Se crea la tarea en segundo plano pasándole "this" (esta misma Activity, ver 00-programacion-basica.md §5
        // sobre "this"), para que luego pueda llamar a getBarra()/getImageCentral()/getTexto() y actualizarlas.
        ProgresoCaballo hilo = new ProgresoCaballo(this);
        hilo.execute(); // arranca la tarea: empieza a ejecutarse doInBackground() en otro hilo
    }

    public TextView getTexto() {
        return texto;
    }

    public void setTexto(TextView texto) {
        this.texto = texto;
    }

    public ImageView getImageCentral() {
        return imageCentral;
    }

    public void setImageCentral(ImageView imageCentral) {
        this.imageCentral = imageCentral;
    }

    public ProgressBar getBarra() {
        return barra;
    }

    public void setBarra(ProgressBar barra) {
        this.barra = barra;
    }

    public TypedArray getImagenes() {
        return imagenes;
    }

    public void setImagenes(TypedArray imagenes) {
        this.imagenes = imagenes;
    }
}