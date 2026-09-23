package com.example.ejerciciodiseno;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Pantalla de la animación de Pikachu: igual que Caballo.java, pero más simple
// (sin barra de progreso ni texto de porcentaje, solo la imagen cambiando).
public class Pikachu extends AppCompatActivity {

    private TypedArray imagenes = null; // los "fotogramas" de Pikachu

    private ImageView imageCentral = null; // dónde se dibuja cada fotograma

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pikachu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imagenes = getResources().obtainTypedArray((R.array.pikachu));
        imageCentral = findViewById(R.id.imagePikachu);
        // Se lanza la tarea de fondo que anima Pikachu (ver conceptos/05-asynctask-e-hilos.md)
        AnimacionPikachu hilo = new AnimacionPikachu(this);
        hilo.execute();
    }

    public TypedArray getImagenes() {
        return imagenes;
    }

    public void setImagenes(TypedArray imagenes) {
        this.imagenes = imagenes;
    }

    public ImageView getImageCentral() {
        return imageCentral;
    }

    public void setImageCentral(ImageView imageCentral) {
        this.imageCentral = imageCentral;
    }
}