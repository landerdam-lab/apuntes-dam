package com.example.ejemplodialogopersonalizado.ej;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;

public class Ej41ToastPerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_toast_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnToast2s = findViewById(R.id.btnToast2s);
        Button btnToastTocar = findViewById(R.id.btnToastTocar);
        Button btnCarga3s = findViewById(R.id.btnCarga3s);

        // (1) Se cierra solo a los 2 segundos
        btnToast2s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogo = crearDialogo("Almi");
                dialogo.show();

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogo.dismiss();
                    }
                }, 2000);
            }
        });

        // (2) Se cierra cuando el usuario toca la imagen
        btnToastTocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogo = crearDialogo("Tocame");
                ImageView imagen = dialogo.findViewById(R.id.ivToast);
                imagen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });
                dialogo.show();
            }
        });

        // (3) Enunciado de clase: "animacion de carga de 3 segundos; al terminar, una nueva activity vacia"
        btnCarga3s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogo = crearDialogo("Cargando...");
                dialogo.show();

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), EjVaciaActivity.class);
                        startActivity(intent);
                        dialogo.dismiss();
                    }
                }, 3000);
            }
        });
    }

    // Crea el "toast": infla el layout SIN padre (null), lo rellena y lo mete en un Dialog sin fondo
    private Dialog crearDialogo(String textoMostrar) {
        View vista = getLayoutInflater().inflate(R.layout.ej_toast_per, null);

        ImageView imagen = vista.findViewById(R.id.ivToast);
        imagen.setImageResource(R.drawable.ej_andando3);
        TextView texto = vista.findViewById(R.id.tvToast);
        texto.setText(textoMostrar);

        final Dialog dialogo = new Dialog(Ej41ToastPerActivity.this);
        dialogo.setContentView(vista);
        if (dialogo.getWindow() != null) {
            dialogo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        return dialogo;
    }
}
