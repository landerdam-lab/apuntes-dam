package com.example.fragmentosnombres.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmentosnombres.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoAbajo#newInstance} factory method to
 * create an instance of this fragment.
 */
// INCOMPLETO: esto es literalmente la plantilla que genera Android Studio al crear un
// "Blank Fragment", sin editar. Su layout solo tiene un GridView sin id y sin adaptador —
// no muestra ni hace nada todavía (ver el proyecto documentado FragmentosNombres.md para
// una propuesta de cómo completarlo).
public class FragmentoAbajo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoAbajo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoAbajo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoAbajo newInstance(String param1, String param2) {
        FragmentoAbajo fragment = new FragmentoAbajo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragmento_abajo, container, false);
    }
}