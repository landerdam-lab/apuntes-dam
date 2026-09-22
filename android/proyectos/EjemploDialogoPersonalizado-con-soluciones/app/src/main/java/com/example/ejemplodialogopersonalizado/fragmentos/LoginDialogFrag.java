package com.example.ejemplodialogopersonalizado.fragmentos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ejemplodialogopersonalizado.CentralActivity;
import com.example.ejemplodialogopersonalizado.R;
import com.example.ejemplodialogopersonalizado.bbdd.AppDatabase;
import com.example.ejemplodialogopersonalizado.bbdd.AppExecutors;
import com.example.ejemplodialogopersonalizado.model.Usuario;

/*
 * DIALOGO PERSONALIZADO DE LOGIN
 * DialogFragment = una ventanita emergente con diseno propio (dialog_per.xml)
 * que aparece encima de la pantalla actual. Se abre desde MainActivity.
 * Comprueba en la base de datos si el usuario y la password existen.
 */
public class LoginDialogFrag extends DialogFragment {

    //ATRIBUTOS DE LA CLASE (los elementos del diseno dialog_per.xml)
    private EditText etNombre;
    private EditText etPassword;
    private Button btnAceptar;
    private Button btnCancelar;
    private AppDatabase mDb;    //Base de datos donde estan los usuarios registrados

    //Un DialogFragment necesita siempre un constructor publico vacio
    public LoginDialogFrag() {
        super();
    }

    //Se llama cuando el usuario cancela el dialogo (pulsa fuera o el boton atras)
    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    //Se llama cuando el fragmento se separa de la Activity
    @Override
    public void onDetach() {
        super.onDetach();
    }

    //Crea la ventana del dialogo (el "contenedor") y le pone el titulo
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Login");
        return dialog;
    }

    //Crea el CONTENIDO del dialogo: "infla" (convierte) el XML dialog_per en objetos View
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_per, container, false);
    }

    //Se llama justo despues de crear la vista: aqui ya podemos buscar los elementos y programarlos
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Obtenemos la base de datos (la misma instancia que usa RegisterActivity)
        mDb = AppDatabase.getInstance(getContext());

        //INSTANCIAMOS LOS EDITTEXT Y LOS BOTONES (los enlazamos con los ids del XML)
        etNombre = view.findViewById(R.id.etUser);
        etPassword = view.findViewById(R.id.etPassword);
        btnAceptar = view.findViewById(R.id.btnAceptar);
        btnCancelar = view.findViewById(R.id.btnCancelar);

        //PROGRAMAMOS LOS BOTONES
        //Boton ACEPTAR: busca el usuario en la BD
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Leemos lo que ha escrito el usuario
                String nombre = etNombre.getText().toString();
                String password = etPassword.getText().toString();

                //1) La consulta a la BD se hace en un hilo secundario (diskIO)
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        //Devuelve el Usuario si nombre y password coinciden, o null si no existe
                        final Usuario usu = mDb.usuariosDao().loadUsuarioByNamePass(nombre, password);

                        //2) Con el resultado, volvemos a otro ejecutor para actuar sobre la pantalla
                        AppExecutors.getInstance().getMainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                if(usu != null){
                                    //Login correcto: abrimos CentralActivity
                                    Intent intent = new Intent(getContext(), CentralActivity.class);
                                    startActivity(intent);
                                    dismiss();   //cerramos tambien el dialogo (si no, seguiria debajo de la nueva pantalla)
                                }else {
                                    //Login incorrecto: avisamos y cerramos el dialogo
                                    Toast.makeText(getContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            }
                        });
                    }
                });

            }
        });

        //Boton CANCELAR: cierra el dialogo
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
