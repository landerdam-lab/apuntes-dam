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

// Pantalla de detalle de una imagen del GridView, destino de la transición de "elemento
// compartido" (la imagen "viaja" físicamente de una pantalla a otra, ver conceptos/08-transiciones.md §3).
public class DetalleActivity extends AppCompatActivity {

    // public static final (00-programacion-basica.md §9-10): una CONSTANTE de texto, compartida
    // entre esta clase y quien la lanza (GridViewDam2Activity), que sirve de "etiqueta" para que
    // el sistema sepa qué vista de cada pantalla es "la misma imagen" a animar.
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

        // Recupera de qué posición del grid venía (ver conceptos/01-fundamentos-bundle-intent-ciclo-vida.md §2.2)
        int pos = getIntent().getIntExtra("idFoto",0);
        TypedArray paisajes = getResources().obtainTypedArray(R.array.pikachus);
        int idImagen = paisajes.getResourceId(pos,-1);
        paisajes.recycle(); // libera el TypedArray una vez leído (buena práctica, ver conceptos/06-animaciones-frame-by-frame.md §2)

        ImageView ivImagen = findViewById(R.id.ivDetalles);
        // Marca ESTA ImageView como el "destino" de la transición de elemento compartido,
        // usando la misma etiqueta (VIEW_NAME_HEADER_IMAGE) que se usó en el origen.
        ViewCompat.setTransitionName(ivImagen, VIEW_NAME_HEADER_IMAGE);
        Glide.with(getApplicationContext())
                .load(idImagen)
                .into(ivImagen);

        TextView tvTitulo = findViewById(R.id.tvDetallesTitulo);
        tvTitulo.setText("paisaje"+(pos+1));
        TextView tvDescripcion = findViewById(R.id.tvDetallesDescripcion);
        tvDescripcion.setText("imagen numero"+(pos+1)+"De la galeria");

    }
}