package com.example.ejemplodialogopersonalizado;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.net.URLEncoder;
import java.util.ArrayList;

public class VozActivity extends AppCompatActivity {

    private EditText editTexto;
    private ActivityResultLauncher<Intent> vozLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTexto = findViewById(R.id.editTexto);

        //SE REGISTRA EL LANZADOR DEAL RECONOCIMIENTO DE VOZ (ACTIVITY RESULT API)

        vozLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                resultado -> {
            if (resultado.getResultCode() == RESULT_OK && resultado.getData() != null){
                ArrayList<String> textos = resultado.getData()
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if(textos != null && !textos.isEmpty()){
                    //SE USA EL PRIMER RESULTADO (EL MAS PROBABLE)
                    editTexto.setText(textos.get(0));
                }
            }
        });

        Button btnVoz = findViewById(R.id.btnVoz);
        btnVoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "AHORA PUEDES HABLAR .....");

                vozLauncher.launch(intent);
            }
        });

        Button btnWeb = findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarBusqueda(editTexto.getText().toString());
            }
        });


    }
    private void realizarBusqueda(String texto){
        try {
            //SE CODIFICA EL TEXTO PARA QUE SEA SEGURO DENTRO DDE UNA URL
            String consulta = URLEncoder.encode(texto, "UTF-8");
            Uri uri = Uri.parse("https://www.google.com/search?q="+consulta);

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            startActivity(intent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}