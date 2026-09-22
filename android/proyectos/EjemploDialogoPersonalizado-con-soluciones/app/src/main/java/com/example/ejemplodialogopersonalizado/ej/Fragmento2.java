package com.example.ejemplodialogopersonalizado.ej;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ejemplodialogopersonalizado.R;

public class Fragmento2 extends Fragment {

    private IControlFichas activity;

    public Fragmento2() {
        super();
    }

    public static Fragmento2 newInstance(Bundle bundle) {
        Fragmento2 fragmento2 = new Fragmento2();
        if (bundle != null) {
            fragmento2.setArguments(bundle);
        }
        return fragmento2;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IControlFichas) {
            activity = (IControlFichas) context;
        } else {
            throw new RuntimeException(context + " debe implementar IControlFichas");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ej_fragmento2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText etNombre = view.findViewById(R.id.etNombre);
        final EditText etApellido = view.findViewById(R.id.etApellido);
        final EditText etFecha = view.findViewById(R.id.etFecha);
        Button btnFicha = view.findViewById(R.id.btnFicha);

        // El nombre llega precargado desde el Fragmento1 (sigue siendo editable)
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("nombre")) {
            etNombre.setText(bundle.getString("nombre"));
        }

        btnFicha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String apellido = etApellido.getText().toString().trim();
                String fecha = etFecha.getText().toString().trim();
                if (apellido.isEmpty() || fecha.isEmpty()) {
                    Toast.makeText(getContext(), "Completa apellido y fecha", Toast.LENGTH_SHORT).show();
                    return;
                }
                activity.onApellidoYFechaCompletado(nombre, apellido, fecha);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
}
