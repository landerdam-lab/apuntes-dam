package com.example.diseobesos;

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

// Pantalla que muestra una animación (una imagen que va cambiando) con una barra de progreso,
// mientras un AsyncTask (ver ProgressAndando.java) va avanzando en segundo plano.
public class EjemploAsynctask extends AppCompatActivity {

    // ATRIBUTOS: las vistas de esta pantalla, guardadas como campos de la clase
    // (en vez de variables locales) para que ProgressAndando pueda acceder a ellas
    // desde fuera a través de los métodos "getter" de más abajo.
    private TypedArray imagenes = null;
    private ProgressBar barra = null;
    private ImageView imageCentral = null;
    private TextView texto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ejemplo_asynctask);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Carga el conjunto de imágenes (fotogramas) declarado en res/values como <array name="imagenes">
        imagenes = getResources().obtainTypedArray((R.array.imagenes));
        barra = findViewById(R.id.pbAnimacion);
        imageCentral = findViewById(R.id.ivAnimacion);
        texto = findViewById(R.id.tvAnimacion);

        barra.setMax(100); // la barra de progreso va de 0 a 100
        barra.setProgress(0); // empieza en 0
        barra.setBackgroundColor(Color.GRAY);
        // Se crea la tarea en segundo plano (le pasamos "this", esta misma Activity,
        // para que luego pueda actualizar estas vistas) y se lanza con .execute()
        ProgressAndando hilo = new ProgressAndando(this);
        hilo.execute();

    }

    // Método auxiliar para cerrar esta pantalla. No se llama desde ningún sitio
    // (ProgressAndando cierra la Activity llamando directamente a finish()).
    public void finalizar(){
        this.finish();
    }

    // A partir de aquí: "getters" y "setters", métodos que sirven para LEER (get)
    // o MODIFICAR (set) los atributos privados de esta clase desde fuera de ella.
    public TypedArray getImagenes(){
        return  this.imagenes;
    }

    public void setImagenes(TypedArray imagenes){
        this.imagenes = imagenes;
    }

    public ImageView getImageCentral() {
        return imageCentral;
    }

    public void setImageCentral(ImageView imageCentral) {
        this.imageCentral = imageCentral;
    }

    public TextView getTexto() {
        return texto;
    }

    public void setTexto(TextView texto) {
        this.texto = texto;
    }

    public ProgressBar getBarra() {
        return barra;
    }

    public void setBarra(ProgressBar barra) {
        this.barra = barra;
    }
}