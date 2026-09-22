package com.example.ejemplodialogopersonalizado.ej;

import java.io.Serializable;
import java.util.ArrayList;

public class Equipo implements Serializable {

    private String nombre, ciudad;
    private int logo;                       // R.drawable.xxx
    private ArrayList<Jugador> jugadores;

    public Equipo(String nombre, String ciudad, int logo, ArrayList<Jugador> jugadores) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.logo = logo;
        this.jugadores = jugadores;
    }

    public String getNombre() { return nombre; }
    public String getCiudad() { return ciudad; }
    public int getLogo() { return logo; }
    public ArrayList<Jugador> getJugadores() { return jugadores; }
}
