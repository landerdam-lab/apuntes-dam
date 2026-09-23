package com.example.diseobesos;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Pantalla de menú: dos botones, uno para ver la animación con AsyncTask
// y otro para mostrar un "toast" (aviso temporal) personalizado.
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Botón 1: al pulsarlo, abre la pantalla EjemploAsynctask (la animación con barra de progreso)
        ImageButton btnAsyncTask = findViewById(R.id.asyntask);
        btnAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EjemploAsynctask.class);
                startActivity(intent);
            }
        });
        // Botón 2: al pulsarlo, muestra un aviso ("toast") personalizado con imagen y texto
        ImageButton btnToast = findViewById(R.id.btnToast);
        btnToast.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Se construye, a partir del XML toast_per.xml, la vista que se va a mostrar dentro del aviso
                View vista = getLayoutInflater().inflate(R.layout.toast_per, null);
                ImageView ivToast = vista.findViewById(R.id.ivToast);
                ivToast.setImageResource(R.drawable.andando1);
                TextView tvToast = vista.findViewById(R.id.tvToast);
                tvToast.setText("DAM ---- 2");

                // Se mete esa vista dentro de un Dialog (una ventana flotante) sin fondo,
                // para que parezca un aviso flotante en vez de una ventana normal
                final Dialog dialogo = new Dialog(MainActivity.this);
                dialogo.setContentView(vista);
                if(dialogo.getWindow() != null)
                {
                    dialogo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }
                dialogo.show();
                // Se programa que, 2000 milisegundos (2 segundos) después, el diálogo se cierre solo
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        dialogo.dismiss();
                    }
                }, 2000);

            }
        });
    }
}