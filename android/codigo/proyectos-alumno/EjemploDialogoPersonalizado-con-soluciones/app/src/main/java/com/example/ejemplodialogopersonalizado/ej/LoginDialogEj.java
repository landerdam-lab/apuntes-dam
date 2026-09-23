package com.example.ejemplodialogopersonalizado.ej;

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

import com.example.ejemplodialogopersonalizado.R;

public class LoginDialogEj extends DialogFragment {

    //ATRIBUTOS DE LA CLASE
    private EditText etNombre;
    private EditText etPassword;
    private Button btnAceptar;
    private Button btnCancelar;

    // Un DialogFragment necesita constructor publico vacio
    public LoginDialogEj() {
        super();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // Crea la ventana del dialogo y le pone el titulo
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Login");
        return dialog;
    }

    // Crea el CONTENIDO: "infla" el XML del dialogo
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ej_dialog_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //INSTANCIAMOS LOS EDITTEXT Y LOS BOTONES
        etNombre = view.findViewById(R.id.etUser);
        etPassword = view.findViewById(R.id.etPassword);
        btnAceptar = view.findViewById(R.id.btnAceptar);
        btnCancelar = view.findViewById(R.id.btnCancelar);

        //PROGRAMAMOS LOS BOTONES
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String password = etPassword.getText().toString();

                // Fase 2 del PDF: el usuario esta escrito en el codigo (en el ejercicio 6.4 se comprueba en la BD)
                if (nombre.equals("Almi") && password.equals("Almi123")) {
                    Toast.makeText(getContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), EjVaciaActivity.class);
                    startActivity(intent);
                } else {
                    dismiss();
                }
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
