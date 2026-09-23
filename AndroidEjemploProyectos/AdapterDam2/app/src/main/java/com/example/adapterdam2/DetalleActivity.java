package com.example.adapterdam2;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class DetalleActivity extends AppCompatActivity
{
    public static final String VIEW_NAME_HEADER_IMAGE = "imagenCabecera";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int pos = getIntent().getIntExtra("idFoto", 0);
        TypedArray paisajes = getResources().obtainTypedArray(R.array.paisajes);
        int idImagen = paisajes.getResourceId(pos, -1);
        paisajes.recycle();

        ImageView ivImagen = findViewById(R.id.ivDetalles);
        ViewCompat.setTransitionName(ivImagen, VIEW_NAME_HEADER_IMAGE);
        Glide.with(getApplicationContext())
                .load(idImagen)
                .into(ivImagen);

        TextView tvTitulo = findViewById(R.id.tvDetallesTitulo);
        tvTitulo.setText("Paisaje " + (pos + 1));

        TextView tvDescripcion = findViewById(R.id.tvDetallesDescripcion);
        tvDescripcion.setText("Imagen número " + (pos + 1) +  " de la galería.");
    }
}