package com.example.ejercicioadaptadoresfinal.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Equipo implements Serializable {
    private String nombre;
    private String ciudad;
    private String logo;
    private float precio;
    private ArrayList<Jugador> jugadores;

    public Equipo(String nombre, String ciudad, String logo, float precio, ArrayList<Jugador> jugadores) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.logo = logo;
        this.precio = precio;
        this.jugadores = jugadores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
}
