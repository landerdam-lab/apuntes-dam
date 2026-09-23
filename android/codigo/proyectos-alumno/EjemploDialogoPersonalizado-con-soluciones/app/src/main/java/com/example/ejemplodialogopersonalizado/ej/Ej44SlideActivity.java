package com.example.ejemplodialogopersonalizado.ej;

import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;

public class Ej44SlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Transicion de entrada propia (slide). Va ANTES de setContentView
        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.ej_slide);
        getWindow().setEnterTransition(slide);

        setContentView(R.layout.ej_vacia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
