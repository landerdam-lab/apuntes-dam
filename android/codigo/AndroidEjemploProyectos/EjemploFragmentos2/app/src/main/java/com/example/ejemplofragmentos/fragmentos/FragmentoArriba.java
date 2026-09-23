package com.example.ejemplofragmentos.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ejemplofragmentos.IControlFragmentos;
import com.example.ejemplofragmentos.R;

public class FragmentoArriba extends Fragment
{
    private IControlFragmentos activity;
    public FragmentoArriba() {
        super();
    }

    //CUANDO CARGAMOS LA VISTA O EL LAYOUT EN PANTALLA
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragmento_arriba,container,false);
    }

    //CUANDO ESTA CARGADA LA VISTA EN PANTALLA
    //ES DECIR EN ESTE METODO ACCEDEMOS A NUESTROS COMPONENTES TEXT VIEW etc
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etTexto = view.findViewById(R.id.etTexto);
        Button btnEnviar = view.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                activity.cambiarTexto(etTexto.getText().toString());
            }
        });
    }

    //CUANDO DESCONECTAMOS LA VISTA DE LA ACTIVIDAD PRINCIPAL
    @Override
    public void onDetach() {
        super.onDetach();
    }

    //CUANDO NOS CONECTAMOS A LA ACTIVIDAD PRINCIPAL Y CONTEXT ES LA ACTIVIDAD PRINCIPAL
    //CONTEXT ES LA ACTIVIDAD DONDE CARGAMOS TODOoooooooo ☻
    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.activity = (IControlFragmentos) context;
    }
}