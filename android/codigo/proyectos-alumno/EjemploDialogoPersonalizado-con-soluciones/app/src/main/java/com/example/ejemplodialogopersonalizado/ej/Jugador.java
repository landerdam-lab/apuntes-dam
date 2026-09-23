package com.example.ejemplodialogopersonalizado.ej;

import java.io.Serializable;

//Si los datos son primitivos o serializables se pone Serializable para poder pasar los datos de una activity a otra
public class Jugador implements Serializable {

    private String nombre, posicion;
    private int dorsal;
    private int foto;   // R.drawable.xxx (recurso local)

    public Jugador(String nombre, int dorsal, String posicion, int foto) {
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.posicion = posicion;
        this.foto = foto;
    }

    public String getNombre() { return nombre; }
    public String getPosicion() { return posicion; }
    public int getDorsal() { return dorsal; }
    public int getFoto() { return foto; }
}
