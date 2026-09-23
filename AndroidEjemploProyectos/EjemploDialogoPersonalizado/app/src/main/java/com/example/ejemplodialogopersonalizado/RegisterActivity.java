package com.example.ejemplodialogopersonalizado;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import com.example.ejemplodialogopersonalizado.adaptadores.UsuariosAdapter;
import com.example.ejemplodialogopersonalizado.bbdd.AppDatabase;
import com.example.ejemplodialogopersonalizado.bbdd.AppExecutors;
import com.example.ejemplodialogopersonalizado.model.Usuario;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private ListView lvUsers;
    private Button btnRegistrarNuevo, btnUpdateUsuario;
    private EditText etNombre;
    private EditText etPassword;
    private EditText etRePassword;

    private AppDatabase mDb;
    private UsuariosAdapter usuariosAdapter;
    private int idUsuario = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //instaciamos la BD
        mDb = AppDatabase.getInstance(getApplicationContext());

        //creamos una notificacion para comprobar la creacion de la BD
        Toast.makeText(this, "Base de datos Preparada", Toast.LENGTH_SHORT).show();

        //Instanciamos todas los componentes de mi vista (TextViev,EditText,Button,tec...)
        lvUsers = findViewById(R.id.lvUsers);
        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Usuario usuario = usuariosAdapter.getItem(position);
                idUsuario = usuario.getId();
                etNombre.setText(usuario.getUsuario());
                etPassword.setText(usuario.getPassword());
                etRePassword.setText(usuario.getPassword());
            }
        });

        lvUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                final int idEliminar = usuariosAdapter.getItem(position).getId();
                AlertDialog.Builder alerta = new AlertDialog.Builder(RegisterActivity.this);
                alerta.setTitle("Advertencia");
                alerta.setMessage("¿Estás seguro de que deseas eliminar el usuario?");
                alerta.setPositiveButton("si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        eliminar(idEliminar);
                    }
                });

                alerta.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(getApplicationContext(), "No se ha eliminado", Toast.LENGTH_SHORT).show();
                    }
                });

                alerta.show();
                return true;
            }
        });

        etNombre = findViewById(R.id.etRegistroUser);
        etPassword = findViewById(R.id.etRegistroPassword);
        etRePassword = findViewById(R.id.etRegistroRePassword);
        btnRegistrarNuevo = findViewById(R.id.btnNuevoUsuario);

        //Creamos el adptador y se lo asignamos al listview
        this.usuariosAdapter = new UsuariosAdapter(getApplicationContext(),1);
        lvUsers.setAdapter(this.usuariosAdapter);

        //creamos el boton registrar nuevo usuario
        btnRegistrarNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String user = etNombre.getText().toString();
                String password = etPassword.getText().toString();
                guardarUsuario(user,password);
            }
        });

        btnUpdateUsuario = findViewById(R.id.btnUpdateUsuario);
        btnUpdateUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String user = etNombre.getText().toString();
                String password = etPassword.getText().toString();
                actualizar(idUsuario, user, password);
            }
        });

        etRePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s)
            {
                String password = etPassword.getText().toString();
                String repassword = etRePassword.getText().toString();
                if(!password.equals(repassword))
                {
                    btnRegistrarNuevo.setEnabled(false);
                    etRePassword.setBackgroundColor(Color.RED);
                } else
                {
                    btnRegistrarNuevo.setEnabled(true);
                    etRePassword.setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        consultarUsuarios();
    }

    private void actualizar(int idUsuario, String user, String password)
    {
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run()
            {
                Usuario usu = mDb.usuariosDao().loadUsuarioById(idUsuario);
                if(usu != null)
                {
                    usu.setUsuario(user);
                    usu.setPassword(password);
                    mDb.usuariosDao().updateUsuario(usu);
                }
            }
        });
    }

    //colsultar usuarios rellena el array de usuarios y los carga en el adaptador
    private void consultarUsuarios() {
        mDb.usuariosDao().loadAllUsuarios().observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios)
            {
                usuariosAdapter.setmUsuarioList(usuarios);
            }
        });
    }

    //guardar usuario guarda el nombre y las password en la base de datos
    private void guardarUsuario(String user, String password)
    {
        final Usuario usuario= new Usuario(user,password);
        //guardamos el nuevo Usuario
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run()
            {
                Log.d("ander", "llega al run");
                mDb.usuariosDao().insertUsuario(usuario);
            }
        });
    }

    private void eliminar(final int idEliminar)
    {
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run()
            {
                Usuario usu = mDb.usuariosDao().loadUsuarioById(idEliminar);
                if(usu != null)
                {
                    mDb.usuariosDao().delete(usu);
                }
            }
        });
    }
}










