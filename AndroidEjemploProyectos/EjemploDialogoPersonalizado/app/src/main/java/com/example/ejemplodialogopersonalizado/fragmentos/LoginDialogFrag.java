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

public class LoginDialogFrag extends DialogFragment {

    //atributos de la clase
    private EditText etNombre;
    private EditText etPassword;
    private Button btnAceptar;
    private Button btnCancelar;
    private AppDatabase mDb;


    public LoginDialogFrag() {
        super();
    }

    public void onCancel(@NonNull DialogInterface dialog){
        super.onCancel(dialog);
    }

    public void onDetach(){
        super.onDetach();
    }
    public Dialog onCreateDialog(@Nullable Bundle savedInstance){
        Dialog dialog=super.onCreateDialog(savedInstance);
        dialog.setTitle("Login");
        return dialog;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_personalizado,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mDb = AppDatabase.getInstance(getContext());


        //INTANCIAMOS LOS EDITTEXT Y LOS BOTONES
        etNombre=view.findViewById(R.id.etUser);
        etPassword=view.findViewById(R.id.etPassword);
        btnAceptar=view.findViewById(R.id.btnAceptar);
        btnCancelar=view.findViewById(R.id.btnCancelar);
        //PROGRAMAMOS LOS BOTONES
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String nombre = etNombre.getText().toString();
                String password = etPassword.getText().toString();

                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run()
                    {
                        final Usuario usu = mDb.usuariosDao().loadUsuarioByNamePass(nombre, password);

                        AppExecutors.getInstance().getMainThread().execute(new Runnable() {
                            @Override
                            public void run()
                            {
                                if(usu != null)
                                {
                                    Intent intent = new Intent(getContext(), CentralActivity.class);
                                    startActivity(intent);
                                } else
                                {
                                    //Toast.makeText(getContext(), "Usuario o contraseña no válidas", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            }
                        });
                    }
                });


            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}












