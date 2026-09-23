package com.example.ejerciciofragmentos.fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.ejerciciofragmentos.R;
import com.example.ejerciciofragmentos.adaptadores.AdaptadorPersona;
import com.example.ejerciciofragmentos.modelo.Persona;

import java.util.ArrayList;

public class FragmentoAbajo extends Fragment {

    private Context mContext;

    public FragmentoAbajo() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Bundle bundle) {
        FragmentoAbajo fragmentoAbajo = new FragmentoAbajo();
        if (bundle != null) {
            fragmentoAbajo.setArguments(bundle);
        }
        return fragmentoAbajo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragmento_abajo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView gv = view.findViewById(R.id.gvLista);

        assert getArguments() != null;
        if (getArguments().containsKey("personas")) {
            gv.setAdapter(new AdaptadorPersona(
                    (ArrayList<Persona>) getArguments().getSerializable("personas"),
                    this.mContext));
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}