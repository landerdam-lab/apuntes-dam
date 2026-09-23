package com.example.ejemplodialogopersonalizado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.fragmentos.LoginDialogFrag;

/*
 * PANTALLA PRINCIPAL (la primera que se abre, definida como LAUNCHER en el AndroidManifest).
 * Tiene dos botones: Acceder (abre el dialogo de login) y Registro (abre RegisterActivity).
 */
public class MainActivity extends AppCompatActivity {

    private Button btnAcceder, btnRegistro;
    private LoginDialogFrag dialog;

    //onCreate se ejecuta al abrirse la pantalla: aqui se prepara todo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Dibuja la app ocupando toda la pantalla (tambien bajo la barra de estado)
        EdgeToEdge.enable(this);
        //Carga el diseno activity_main.xml
        setContentView(R.layout.activity_main);
        //Anade un margen para que el contenido no quede tapado por las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //IMPLEMENTACION DE LA APLICACION
        //Boton ACCEDER: abre el dialogo de login
        btnAcceder = findViewById(R.id.btnAcceder);
        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new LoginDialogFrag();
                //show(gestor de fragmentos, etiqueta) muestra el dialogo
                dialog.show(getSupportFragmentManager(), "Login");
            }
        });

        //Boton REGISTRO: abre la pantalla de registro
        btnRegistro = findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent = "intencion" de ir de una pantalla a otra
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
