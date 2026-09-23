package com.example.disenyopesos;

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

        ImageButton btnAsyncTask = findViewById(R.id.btnAsynctask);
        btnAsyncTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), EjemploAsynctask.class);
                startActivity(intent);
            }
        });
        ImageButton btnToast = findViewById(R.id.btnToast);
        btnToast.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                View vista = getLayoutInflater().inflate(R.layout.toast_per, null);
                ImageView ivToast = vista.findViewById(R.id.ivToast);
                ivToast.setImageResource(R.drawable.andando1);
                TextView tvToast = vista.findViewById(R.id.tvToast);
                tvToast.setText("DAM ---- 2");

                final Dialog dialogo = new Dialog(MainActivity.this);
                dialogo.setContentView(vista);
                if(dialogo.getWindow() != null)
                {
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
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        dialogo.dismiss();
                    }
                }, 2000);
                */
            }
        });
    }
}