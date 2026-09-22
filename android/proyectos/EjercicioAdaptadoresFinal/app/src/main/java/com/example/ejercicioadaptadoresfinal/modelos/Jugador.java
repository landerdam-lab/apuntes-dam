package com.example.ejercicioadaptadoresfinal.modelos;

import java.io.Serializable;

// Modelo de datos de un jugador: nombre, dorsal (número de camiseta), posición y foto (URL).
public class Jugador implements Serializable {

    private String nombre;
    private int dorsal;
    private String posicion;
    private String foto;

    // Constructor: rellena los 4 atributos al crear un Jugador nuevo.
    public Jugador(String nombre, int dorsal, String posicion, String foto) {
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.posicion = posicion;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
