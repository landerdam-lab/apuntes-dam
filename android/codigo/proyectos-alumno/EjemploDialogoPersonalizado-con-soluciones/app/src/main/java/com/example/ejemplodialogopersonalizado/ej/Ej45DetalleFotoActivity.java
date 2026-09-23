package com.example.ejemplodialogopersonalizado.ej;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.ejemplodialogopersonalizado.R;

public class Ej45DetalleFotoActivity extends AppCompatActivity {

    // La "clave" de la transicion: el mismo nombre en el origen y en el destino
    public static final String VIEW_NAME_HEADER_IMAGE = "imagenCabecera";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_detalle_foto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int pos = getIntent().getIntExtra("idFoto", 0);

        TypedArray paisajes = getResources().obtainTypedArray(R.array.ej_paisajes);
        int idImagen = paisajes.getResourceId(pos, -1);
        paisajes.recycle();

        // La imagen destino recibe el mismo nombre de transicion que la de origen
        ImageView ivImagen = findViewById(R.id.ivDetalles);
        ViewCompat.setTransitionName(ivImagen, VIEW_NAME_HEADER_IMAGE);
        Glide.with(getApplicationContext())
                .load(idImagen)
                .into(ivImagen);

        TextView tvTitulo = findViewById(R.id.tvDetallesTitulo);
        tvTitulo.setText("Paisaje " + (pos + 1));

        TextView tvDescripcion = findViewById(R.id.tvDetallesDescripcion);
        tvDescripcion.setText("Imagen numero " + pos + " de la galeria.");
    }
}
