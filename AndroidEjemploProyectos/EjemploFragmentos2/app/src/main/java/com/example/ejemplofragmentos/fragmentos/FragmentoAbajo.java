package com.example.ejemplofragmentos.fragmentos;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ejemplofragmentos.R;

public class FragmentoAbajo extends Fragment {
    public FragmentoAbajo() {
        super();
    }
    //GENERAMOS UNA NUEVA INSTANCIA PORQUE ESTE FRAGMENTO RECIBE PARAMETROS
    public static FragmentoAbajo newInstance(Bundle bundle){
        FragmentoAbajo fragmentoAbajo = new FragmentoAbajo();
        if (bundle != null) {
            fragmentoAbajo.setArguments(bundle);

        }
        return fragmentoAbajo;
    }

    //CUANDO CARGAMOS LA VISTA O EL LAYOUT EN PANTALLA
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragmento_abajo,container,false);
    }

    //CUANDO ESTA CARGADA LA VISTA EN PANTALLA
    //ES DECIR EN ESTE METODO ACCEDEMOS A NUESTROS COMPONENTES TEXT VIEW etc
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvAbajo = view.findViewById(R.id.tvAbajo);

        //AQUI DAME LOS VALORES
        Bundle bundle = getArguments();

        if (bundle != null) {
            if (bundle.containsKey("saludo")) {
                tvAbajo.setText(bundle.getString("saludo"));
            }
            if (bundle.containsKey("color")) {
                tvAbajo.setTextColor(bundle.getInt("color"));
            }
        }
    }

    //CUANDO DESCONECTAMOS LA VISTA DE LA ACTIVIDAD PRINCIPAL
    @Override
    public void onDetach() {
        super.onDetach();
    }

    //CUANDO NOS CONECTAMOS A LA ACTIVIDAD PRINCIPAL Y CONTEXT ES LA ACTIVIDAD PRINCIPAL
    //CONTEXT ES LA ACTIVIDAD DONDE CARGAMOS TODOoooooooo ☻
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}