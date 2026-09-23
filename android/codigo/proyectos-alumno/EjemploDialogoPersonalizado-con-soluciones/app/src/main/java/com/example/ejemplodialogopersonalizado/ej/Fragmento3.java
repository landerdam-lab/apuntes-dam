package com.example.ejemplodialogopersonalizado.ej;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ejemplodialogopersonalizado.R;

import java.util.ArrayList;

public class Fragmento3 extends Fragment {

    private Context mContext;

    public Fragmento3() {
        super();
    }

    public static Fragmento3 newInstance(Bundle bundle) {
        Fragmento3 fragmento3 = new Fragmento3();
        if (bundle != null) {
            fragmento3.setArguments(bundle);
        }
        return fragmento3;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ej_fragmento3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView gridFicha = view.findViewById(R.id.gridFicha);
        Bundle argumentos = getArguments();
        if (argumentos != null && argumentos.containsKey("personas")) {
            // Casting: getSerializable solo devuelve un tipo general
            ArrayList<Persona> personas = (ArrayList<Persona>) argumentos.getSerializable("personas");
            gridFicha.setAdapter(new FichaAdapter(mContext, personas));
        }
    }
}
