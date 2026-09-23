package com.example.ejemplodialogopersonalizado;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.ejemplodialogopersonalizado.adaptadores.FotosGridViewAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity
{
    private ImageView ivFoto;
    private File fotoActual;
    private ActivityResultLauncher<Uri> tomarFotoLauncher;
    private  ActivityResultLauncher<String> permisoLauncher;

    private GridView gvMiniaturas;
    private final List<Uri> fotos = new ArrayList<>();
    private FotosGridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ivFoto = findViewById(R.id.ivFoto);
        gvMiniaturas = findViewById(R.id.gvMiniaturas);
        adapter = new FotosGridViewAdapter(this, fotos);
        gvMiniaturas.setAdapter(adapter);

        tomarFotoLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), exito ->{

            if(Boolean.TRUE.equals(exito) && fotoActual != null)
            {
                guardarEnGaleria(fotoActual);
                Glide.with(CameraActivity.this).load(fotoActual).into(ivFoto);
                cargarImagenes();
            }
        });

        permisoLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), concedido ->{
            if(Boolean.TRUE.equals(concedido))
            {
                lanzarCamara();
            } else
            {
                Toast.makeText(this, "Sin permiso no se puede guardar en galería", Toast.LENGTH_SHORT).show();
            }
        });
        Button btnSacarFoto = findViewById(R.id.btnSacarFoto);
        btnSacarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                abrirCamara();
            }
        });
        cargarImagenes();
    }

    private void lanzarCamara()
    {
        File carpeta = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String nombre = "foto_" +
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";
        fotoActual = new File(carpeta, nombre);
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", fotoActual);
        tomarFotoLauncher.launch(uri);
    }

    private void cargarImagenes()
    {
        fotos.clear();
        Uri coleccion = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] proyeccion = {MediaStore.Images.Media._ID};
        String seleccion = MediaStore.Images.Media.DISPLAY_NAME +  " LIKE ?";
        String[] args = {"foto_%"};
        String orden = MediaStore.Images.Media.DATE_ADDED + " DESC";

        try (Cursor cursor = getContentResolver().query(coleccion, proyeccion, seleccion, args, orden)){
            if(cursor != null)
            {
                int idCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                while (cursor.moveToNext())
                {
                    long id = cursor.getLong(idCol);
                    fotos.add(ContentUris.withAppendedId(coleccion, id));
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void abrirCamara()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
        {
            permisoLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return;
        }
        lanzarCamara();
    }

    @SuppressLint("NewApi")
    private void guardarEnGaleria(File archivo)
    {
        ContentValues valores = new ContentValues();
        valores.put(MediaStore.Images.Media.DISPLAY_NAME, archivo.getName());
        valores.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            valores.put(MediaStore.Images.Media.IS_PENDING, 1);
        }

        Uri destino = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, valores);
        if(destino == null)
        {
            return;
        }

        try (InputStream in = new FileInputStream(archivo);
             OutputStream out = getContentResolver().openOutputStream(destino))
        {
            byte[] buffer = new byte[8192];
            int leidos;
            while ((leidos = in.read(buffer)) > 0)
            {
                out.write(buffer, 0, leidos);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            ContentValues v = new ContentValues();
            v.put(MediaStore.Images.Media.IS_PENDING, 0);
            getContentResolver().update(destino, v, null, null);
        }
    }
}