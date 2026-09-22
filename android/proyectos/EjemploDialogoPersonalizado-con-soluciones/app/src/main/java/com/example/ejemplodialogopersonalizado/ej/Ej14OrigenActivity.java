package com.example.ejemplodialogopersonalizado.ej;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;

public class Ej14OrigenActivity extends AppCompatActivity {

    private EditText etNombre;
    private Button btnIr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_origen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNombre = findViewById(R.id.etNombre);
        btnIr = findViewById(R.id.btnIr);

        btnIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creacion de un Bundle para pasar datos a la nueva activity
                Bundle bundle = new Bundle();
                bundle.putString("saludo", etNombre.getText().toString());

                Intent intent = new Intent(getApplicationContext(), Ej14DestinoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
