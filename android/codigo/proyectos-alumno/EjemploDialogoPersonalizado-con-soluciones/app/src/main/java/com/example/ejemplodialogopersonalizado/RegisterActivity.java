package com.example.ejemplodialogopersonalizado;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

/*
 * PANTALLA DE REGISTRO (CRUD de usuarios)
 * Permite: crear usuarios (Nuevo), verlos en una lista, editarlos (Actualizar)
 * y borrarlos (pulsacion larga sobre la lista).
 */
public class RegisterActivity extends AppCompatActivity {

    private ListView lvUsers;                                  //Lista que muestra los usuarios
    private Button btnRegistrarNuevo, btnUpdateUsuario;        //Botones Nuevo y Actualizar
    private EditText etNombre, etPassword, etRePassword;       //Campos de texto
    private AppDatabase mDb;                                   //Base de datos
    private UsuariosAdapter usuariosAdapter;                   //Adaptador de la lista
    private int idUsuario = -1;                                //Id del usuario seleccionado (-1 = ninguno)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //Carga el diseno activity_register.xml
        setContentView(R.layout.activity_register);
        //Margen para que no lo tapen las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Instanciamos la BD
        mDb = AppDatabase.getInstance(getApplicationContext());
        //Creamos una notificacion para comprobar la creacion de la BD
        Toast.makeText(this, "Base de datos preparada", Toast.LENGTH_SHORT).show();
        //Instanciamos todos los componentes de mi vista (TextView, EditText, Button...)
        lvUsers = findViewById(R.id.lvUsers);

        //CLIC CORTO en un usuario de la lista: lo carga en los campos de texto para poder editarlo
        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuario = usuariosAdapter.getItem(position);
                //Guardamos su id para saber a quien actualizar despues
                idUsuario = usuario.getId();
                etNombre.setText(usuario.getUsuario());
                etPassword.setText(usuario.getPassword());
                etRePassword.setText(usuario.getPassword());
            }
        });

        //CLIC LARGO en un usuario: muestra un AlertDialog preguntando si se quiere eliminar
        lvUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //"final" porque se usa dentro de la clase anonima de mas abajo
                final int idEliminar = usuariosAdapter.getItem(position).getId();
                //AlertDialog.Builder = constructor de dialogos de alerta (titulo, botones...)
                AlertDialog.Builder alerta = new AlertDialog.Builder(RegisterActivity.this);
                alerta.setTitle("Advertencia");
                alerta.setMessage("¿Seguro que quieres eliminar este usuario?");
                //Boton SI: borra el usuario
                alerta.setPositiveButton("si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminar(idEliminar);
                    }
                });

                //Boton NO: solo avisa de que no se elimino nada
                alerta.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RegisterActivity.this, "NO SE ELIMINO", Toast.LENGTH_SHORT).show();
                    }
                });
                //Muestra el dialogo en pantalla
                alerta.show();
                //true = "ya he gestionado el clic largo" (asi no se ejecuta tambien el clic corto)
                return true;
            }
        });

        etNombre = findViewById(R.id.etRegistroUser);
        etPassword = findViewById(R.id.etRegistroPassword);
        etRePassword = findViewById(R.id.etRegistroRePassword);
        btnRegistrarNuevo = findViewById(R.id.btnNuevoUsuario);
        //Creamos el adaptador y se lo asignamos al ListView
        this.usuariosAdapter = new UsuariosAdapter(this, 1 );
        lvUsers.setAdapter(usuariosAdapter);

        //Boton NUEVO: coge lo escrito y lo guarda como usuario nuevo
        btnRegistrarNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etNombre.getText().toString();
                String password = etPassword.getText().toString();
                guardarUsuario(user, password);
            }
        });

        //Boton ACTUALIZAR: modifica el usuario que se selecciono en la lista
        btnUpdateUsuario = findViewById(R.id.btnUpdateUsuario);
        btnUpdateUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idUsuario == -1) {
                    Toast.makeText(RegisterActivity.this, "Pulsa antes un usuario de la lista", Toast.LENGTH_SHORT).show();
                    return;
                }
                String user = etNombre.getText().toString();
                String password = etPassword.getText().toString();
                actualizar(idUsuario, user , password);
            }
        });

        //TextWatcher = "vigilante" que se entera cada vez que cambia el texto de un campo
        //UN solo vigilante compartido, puesto en los TRES campos (usuario, password y re-password)
        TextWatcher vigilante = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                validarFormulario();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        etNombre.addTextChangedListener(vigilante);
        etPassword.addTextChangedListener(vigilante);
        etRePassword.addTextChangedListener(vigilante);
        validarFormulario();   //estado inicial correcto: con todo vacio, Nuevo empieza desactivado
        //Empezamos a observar la BD para que la lista se cargue
        consultarUsuarios();
    }

    //Activa "Nuevo" solo si hay usuario, hay password y las dos passwords coinciden
    private void validarFormulario() {
        String user = etNombre.getText().toString().trim();
        String password = etPassword.getText().toString();
        String repassword = etRePassword.getText().toString();

        boolean coinciden = password.equals(repassword);
        boolean completo = !user.isEmpty() && !password.isEmpty();

        btnRegistrarNuevo.setEnabled(coinciden && completo);
        if (coinciden) {
            etRePassword.setBackgroundColor(Color.WHITE);
        } else {
            etRePassword.setBackgroundColor(Color.RED);
        }
    }

    //Modifica en la BD el usuario con ese id
    private void actualizar(int idUsuario, String user, String password){
        //Las operaciones de BD van en un hilo secundario (diskIO), nunca en el principal
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                //Buscamos el usuario por id
                Usuario usu = mDb.usuariosDao().loadUsuarioById(idUsuario);
                //Si existe, le cambiamos los datos y lo guardamos
                if (usu != null){
                    usu.setUsuario(user);
                    usu.setPassword(password);
                    mDb.usuariosDao().updateUsuario(usu);
                }
            }
        });
    }

    //Rellena el array de usuarios y los carga en el adaptador
    private void consultarUsuarios() {
        //observe: cada vez que la tabla Usuario cambia (insertar, borrar, actualizar),
        //se ejecuta onChanged con la lista nueva y la lista de pantalla se refresca sola
        mDb.usuariosDao().loadAllUsusarios().observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                usuariosAdapter.setmUsuarioList(usuarios);
            }
        });
    }

    //Guarda el nombre y la password en la base de datos
    private void guardarUsuario(String user, String password) {
        final Usuario usuario = new Usuario(user.trim(), password);
        //Insertamos en un hilo secundario
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                //Primero comprobamos (en el hilo de fondo) si ya existe un usuario con ese nombre
                if (mDb.usuariosDao().loadUsuarioByName(usuario.getUsuario()) != null) {
                    //El Toast toca la pantalla: hay que volver al hilo principal
                    AppExecutors.getInstance().getMainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "Ese usuario ya existe", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                mDb.usuariosDao().insertUsuario(usuario);
            }
        });
    }

    //Borra de la BD el usuario con ese id
    private void eliminar(final int idEliminar){
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                //Primero lo buscamos, porque @Delete necesita el objeto completo
                Usuario usu = mDb.usuariosDao().loadUsuarioById(idEliminar);
                if(usu!=null){
                    mDb.usuariosDao().deleteUsuario(usu);
                }
            }
        });
    }
}
