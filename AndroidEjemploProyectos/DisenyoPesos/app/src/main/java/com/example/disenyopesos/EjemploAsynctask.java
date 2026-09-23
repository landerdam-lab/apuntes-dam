package com.example.disenyopesos;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EjemploAsynctask extends AppCompatActivity
{
    private TypedArray imagenes = null;
    private ProgressBar barra = null;
    private ImageView imagenCentral = null;
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

        imagenes = getResources().obtainTypedArray(R.array.imagenes);
        Log.d("dam", imagenes.getString(0) + "-------------");
        barra = findViewById(R.id.pbAnimacion);
        imagenCentral = findViewById(R.id.ivAnimacion);
        texto = findViewById(R.id.tvAnimacion);

        barra.setMax(100);
        barra.setProgress(0);
        barra.setBackgroundColor(Color.GRAY);

        ProgressAndando hilo = new ProgressAndando(this);
        hilo.execute();



    }

    public void finalizar()
    {
        this.finish();
    }

    public TypedArray getImagenes()
    {
        return this.imagenes;
    }

    public void setImagenes(TypedArray imagenes)
    {
        this.imagenes = imagenes;
    }

    public ImageView getImagenCentral()
    {
        return imagenCentral;
    }

    public void setImagenCentral(ImageView imagenCentral)
    {
        this.imagenCentral = imagenCentral;
    }

    public TextView getTexto()
    {
        return texto;
    }

    public void setTexto(TextView texto)
    {
        this.texto = texto;
    }

    public ProgressBar getBarra()
    {
        return barra;
    }

    public void setBarra(ProgressBar barra)
    {
        this.barra = barra;
    }
}