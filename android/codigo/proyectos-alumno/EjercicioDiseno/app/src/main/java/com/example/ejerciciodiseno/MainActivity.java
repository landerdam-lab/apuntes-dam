package com.example.ejerciciodiseno;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// CLASE MainActivity: es la pantalla de menú principal de la app.
// "extends AppCompatActivity" significa que esta clase HEREDA de AppCompatActivity (ver 00-programacion-basica.md §6):
// ya viene con todo lo necesario para ser "una pantalla", y aquí solo añadimos el comportamiento propio del menú.
public class MainActivity extends AppCompatActivity {

    // ATRIBUTOS (datos que pertenecen a esta pantalla, ver 00-programacion-basica.md §5):
    // se guardan aquí (en vez de como variables locales) para que la tarea en segundo plano
    // AnimacionPikachu pueda leerlos/escribirlos a través de los métodos getXxx/setXxx de más abajo.
    private TypedArray pikachu = null; // el conjunto de imágenes ("fotogramas") de la animación de Pikachu

    private ImageView imageCentral = null; // el ImageView donde se muestra cada fotograma



    // onCreate: el MÉTODO que Android llama automáticamente al crear esta pantalla.
    // Aquí se prepara todo lo que se ve y se conecta cada botón con lo que debe pasar al pulsarlo.
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
        // Carga desde res/values el array de imágenes de Pikachu (ver conceptos/06-animaciones-frame-by-frame.md)
        pikachu = getResources().obtainTypedArray((R.array.pikachu));
        ImageButton btnCaballo = findViewById(R.id.botonCaballo);




        //SI ES BOTON NORMAL PARA CASTEAR Y SI USAS IMAGE BUTTON PUEDES IGUALEMNTE
        /*((Button)findViewById(R.id.botonCaballo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        // Botón "Caballo": al pulsarlo, se crea un Intent (la "orden" de abrir otra pantalla,
        // ver conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) y se lanza la Activity Caballo.
        btnCaballo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Caballo.class);
                startActivity(intent);
            }
        });

        ImageButton btnPikachu = findViewById(R.id.btnPikachu);

        // Botón "Pikachu": en vez de ir directo a la Activity Pikachu, primero se muestra
        // un aviso (un Dialog sin fondo, el mismo truco de "toast personalizado" de conceptos/04-toast-personalizado.md)
        // durante 3 segundos, y SOLO ENTONCES se navega de verdad a Pikachu.
        btnPikachu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se infla (convierte de XML a objetos View reales) el propio layout de Pikachu
                // y se reutiliza como contenido del diálogo de aviso.
                View vista = getLayoutInflater().inflate(R.layout.activity_pikachu, null);
                imageCentral = findViewById(R.id.imagePikachu);
                ImageView ivToast = vista.findViewById(R.id.imagePikachu);


                final Dialog dialogo = new Dialog(MainActivity.this);
                dialogo.setContentView(vista);
                if(dialogo.getWindow() != null){
                    dialogo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }
                dialogo.show();

                // Handler + postDelayed: "dentro de 3000 milisegundos (3 segundos), ejecuta este código"
                // (ver conceptos/12-hilos-en-profundidad.md). NO congela la pantalla mientras espera.
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), Pikachu.class);
                        startActivity(intent);
                        dialogo.dismiss(); // cierra el diálogo de aviso justo al lanzar la nueva pantalla
                    }
                },3000);
            }
        });



        // Botón "Nuevo": navegación simple a MainActivity2 (una pantalla vacía),
        // escrito con una EXPRESIÓN LAMBDA (v -> { ... }) en vez de una clase anónima completa
        // — ambas formas hacen exactamente lo mismo, la lambda es solo más corta de escribir.
        ImageButton btnNuevo = findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });

        // Botón "Toast": el "toast personalizado" más elaborado del proyecto, con un mini-formulario
        // (usuario/contraseña) dentro del propio diálogo — ver conceptos/04-toast-personalizado.md.
        ImageButton btnToast = findViewById(R.id.btnToast);
        btnToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View vista = getLayoutInflater().inflate(R.layout.nueva_main, null);
                ImageView ivToast = vista.findViewById(R.id.ivToast);
                ivToast.setImageResource(R.drawable.users);
                TextView tvToast = vista.findViewById(R.id.tvToast);
                tvToast.setText("USUARIO:");
                EditText txtNombre = vista.findViewById(R.id.txtNombre);
                TextView tvToast2 = vista.findViewById(R.id.tvPassword);
                tvToast2.setText("PASSWORD:");
                EditText txtPassword = vista.findViewById(R.id.txtPassword);


                final Dialog dialogo = new Dialog(MainActivity.this);
                dialogo.setContentView(vista);
                if(dialogo.getWindow() != null){
                    dialogo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }
                dialogo.show();
                ivToast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });
                /*
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogo.dismiss();
                    }
                },20000);*/
            }
        });



    }

    // GETTERS/SETTERS (ver 00-programacion-basica.md §9): métodos públicos para leer/escribir
    // los atributos privados de arriba desde fuera de esta clase.
    public TypedArray getPikachu() {
        return pikachu;
    }

    public void setPikachu(TypedArray pikachu) {
        this.pikachu = pikachu;
    }

    public ImageView getImageCentral() {
        return imageCentral;
    }

    public void setImageCentral(ImageView imageCentral) {
        this.imageCentral = imageCentral;
    }
}