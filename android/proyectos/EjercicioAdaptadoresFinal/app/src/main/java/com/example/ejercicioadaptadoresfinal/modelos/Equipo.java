package com.example.ejercicioadaptadoresfinal.modelos;

import java.io.Serializable;
import java.util.ArrayList;

// Modelo de datos de un equipo (nombre, ciudad, logo, precio y su lista de jugadores).
// Serializable: si los datos son primitivos o serializables se pone para poder pasar los datos de una
// activity a otra (comentario original del autor del proyecto).
public class Equipo implements Serializable {

    // Todos private (ver 00-programacion-basica.md §9): solo se leen/modifican mediante los
    // getters/setters de más abajo, nunca directamente desde fuera de esta clase.
    private String nombre;
    private String ciudad;
    private String logo;      // aquí el logo es una URL (String), no una imagen local — ver EquiposAdapter
    private float precio;
    private ArrayList<Jugador> jugadores;

    // Constructor: rellena los 5 atributos al crear un Equipo nuevo.
    public Equipo(String nombre, String ciudad, String logo, ArrayList<Jugador> jugadores, float precio) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.logo = logo;
        this.jugadores = jugadores;
        this.precio = precio;
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
