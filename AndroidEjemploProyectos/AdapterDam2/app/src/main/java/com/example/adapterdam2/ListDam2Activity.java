package com.example.adapterdam2;

import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapterdam2.adaptadores.CompaniasAdapter;
import com.example.adapterdam2.model.CompaniaTelefonica;

import java.util.ArrayList;

public class ListDam2Activity extends AppCompatActivity
{
    private final ArrayList<CompaniaTelefonica> companiasTelefonicas = new ArrayList<>();
    private ListView listaCompanias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
        getWindow().setEnterTransition(slide);

        setContentView(R.layout.activity_list_dam2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rellenarCompanias();
        CompaniasAdapter adapter = new CompaniasAdapter(this, companiasTelefonicas);
        listaCompanias = findViewById(R.id.lvTelefonicas);
        listaCompanias.setAdapter(adapter);

    }

    private void rellenarCompanias()
    {
        this.companiasTelefonicas.add(new CompaniaTelefonica("Movistar", 80, R.drawable.logomovistar));
        companiasTelefonicas.add(new CompaniaTelefonica("Euskaltel", 40, R.drawable.logoeuskaltel));
        companiasTelefonicas.add(new CompaniaTelefonica("Digi", 35, R.drawable.logodigi));
        companiasTelefonicas.add(new CompaniaTelefonica("Vodafone", 55, R.drawable.logovodafone));
    }
}