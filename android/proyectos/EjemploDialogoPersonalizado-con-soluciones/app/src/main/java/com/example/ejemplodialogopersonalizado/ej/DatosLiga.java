package com.example.ejemplodialogopersonalizado.ej;

import com.example.ejemplodialogopersonalizado.R;

import java.util.ArrayList;

// Datos de ejemplo "quemados" en el codigo (en una app real vendrian de una BD o una API)
public class DatosLiga {

    public static ArrayList<Equipo> rellenarEquipos() {
        ArrayList<Jugador> jugadoresLakers = new ArrayList<>();
        jugadoresLakers.add(new Jugador("Lebron", 23, "Alero", R.drawable.ej_foto1));
        jugadoresLakers.add(new Jugador("Davis", 3, "Pivot", R.drawable.ej_foto2));
        jugadoresLakers.add(new Jugador("Reaves", 15, "Escolta", R.drawable.ej_foto3));
        jugadoresLakers.add(new Jugador("Russell", 1, "Base", R.drawable.ej_foto4));

        // Cada equipo con SU PROPIA lista (no compartir la misma referencia)
        ArrayList<Jugador> jugadoresBulls = new ArrayList<>();
        jugadoresBulls.add(new Jugador("LaVine", 8, "Escolta", R.drawable.ej_foto2));
        jugadoresBulls.add(new Jugador("Vucevic", 9, "Pivot", R.drawable.ej_foto4));
        jugadoresBulls.add(new Jugador("White", 0, "Base", R.drawable.ej_foto1));

        ArrayList<Jugador> jugadoresCeltics = new ArrayList<>();
        jugadoresCeltics.add(new Jugador("Tatum", 0, "Alero", R.drawable.ej_foto3));
        jugadoresCeltics.add(new Jugador("Brown", 7, "Escolta", R.drawable.ej_foto1));

        ArrayList<Equipo> equipos = new ArrayList<>();
        equipos.add(new Equipo("Lakers", "Los Angeles", R.drawable.ej_logo1, jugadoresLakers));
        equipos.add(new Equipo("Bulls", "Chicago", R.drawable.ej_logo2, jugadoresBulls));
        equipos.add(new Equipo("Celtics", "Boston", R.drawable.ej_logo3, jugadoresCeltics));
        return equipos;
    }
}
