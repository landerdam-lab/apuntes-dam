package com.example.ejemplodialogopersonalizado.ej;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplodialogopersonalizado.R;
import com.example.ejemplodialogopersonalizado.bbdd.AppExecutors;

import java.util.List;

public class Ej63NotasActivity extends AppCompatActivity {

    private ListView lvNotas;
    private Button btnGuardar, btnActualizar, btnBuscar;
    private EditText etTitulo;
    private NotasDb mDb;
    private NotasAdapter notasAdapter;
    private int idNota = -1;   // -1 = ninguna nota seleccionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_notas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Instanciamos la BD
        mDb = NotasDb.getInstance(getApplicationContext());

        lvNotas = findViewById(R.id.lvNotas);
        etTitulo = findViewById(R.id.etTitulo);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnBuscar = findViewById(R.id.btnBuscar);

        //Creamos el adaptador y se lo asignamos al ListView
        this.notasAdapter = new NotasAdapter(this, 1);
        lvNotas.setAdapter(notasAdapter);

        //CLIC CORTO: carga la nota en el campo para poder editarla
        lvNotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Nota nota = notasAdapter.getItem(position);
                idNota = nota.getId();
                etTitulo.setText(nota.getTitulo());
            }
        });

        //CLIC LARGO: AlertDialog para confirmar el borrado
        lvNotas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int idEliminar = notasAdapter.getItem(position).getId();
                AlertDialog.Builder alerta = new AlertDialog.Builder(Ej63NotasActivity.this);
                alerta.setTitle("Advertencia");
                alerta.setMessage("¿Seguro que quieres eliminar esta nota?");
                alerta.setPositiveButton("si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminar(idEliminar);
                    }
                });
                alerta.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Ej63NotasActivity.this, "NO SE ELIMINO", Toast.LENGTH_SHORT).show();
                    }
                });
                alerta.show();
                return true;   //"ya he gestionado el clic largo"
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNota(etTitulo.getText().toString());
                etTitulo.setText("");   //vaciamos el campo para escribir la siguiente
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idNota == -1) {
                    Toast.makeText(Ej63NotasActivity.this, "Pulsa antes una nota de la lista", Toast.LENGTH_SHORT).show();
                    return;
                }
                actualizar(idNota, etTitulo.getText().toString());
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar(etTitulo.getText().toString());
            }
        });

        consultarNotas();
    }

    //Se suscribe UNA vez: cada cambio en la tabla llama a onChanged con la lista nueva
    private void consultarNotas() {
        mDb.notasDao().loadAllNotas().observe(this, new Observer<List<Nota>>() {
            @Override
            public void onChanged(List<Nota> notas) {
                notasAdapter.setmNotaList(notas);
            }
        });
    }

    //Guarda en la BD (en un hilo secundario)
    private void guardarNota(String titulo) {
        final Nota nota = new Nota(titulo);
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.notasDao().insertNota(nota);
            }
        });
    }

    private void actualizar(final int id, final String titulo) {
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                Nota nota = mDb.notasDao().loadNotaById(id);
                if (nota != null) {
                    nota.setTitulo(titulo);
                    mDb.notasDao().updateNota(nota);
                }
            }
        });
    }

    private void eliminar(final int idEliminar) {
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                //Primero la buscamos, porque @Delete necesita el objeto completo
                Nota nota = mDb.notasDao().loadNotaById(idEliminar);
                if (nota != null) {
                    mDb.notasDao().deleteNota(nota);
                }
            }
        });
    }

    //Patron del login del PDF: consulta en segundo plano (getDiskIO) y vuelta al hilo principal (getMainThread)
    private void buscar(final String titulo) {
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                final Nota encontrada = mDb.notasDao().loadNotaByTitulo(titulo);

                AppExecutors.getInstance().getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (encontrada != null) {
                            Toast.makeText(Ej63NotasActivity.this, "Existe", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Ej63NotasActivity.this, "No existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
