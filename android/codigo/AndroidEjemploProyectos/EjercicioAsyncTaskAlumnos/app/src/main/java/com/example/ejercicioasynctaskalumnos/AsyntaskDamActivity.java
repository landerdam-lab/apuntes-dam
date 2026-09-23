package com.example.ejercicioasynctaskalumnos;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AsyntaskDamActivity extends AppCompatActivity {

    private ImageView ivCaballo;
    private ProgressBar pbCaballo;
    private TextView tvCaballo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_asyntask_dam);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Instanciar
        tvCaballo=findViewById(R.id.tvCaballo);
        pbCaballo=findViewById(R.id.pbCaballo);
        ivCaballo=findViewById(R.id.ivCaballo);

        //Crear asyntask
        AsyntaskDamCaballo hilo=new AsyntaskDamCaballo(this);
        hilo.execute();

    }

    public ImageView getIvCaballo() {
        return ivCaballo;
    }

    public ProgressBar getPbCaballo() {
        return pbCaballo;
    }

    public TextView getTvCaballo() {
        return tvCaballo;
    }
}