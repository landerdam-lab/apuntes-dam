package com.example.equiposfutbol.model;

// Clase "modelo": solo datos, sin lógica de pantalla (ver 00-programacion-basica.md §5).
public class Equipos {
    private String nombre;
    private int logo;          // aquí "logo" es un id de imagen LOCAL del proyecto (R.drawable.xxx), no una URL
    private float valorEquipo;

    // Constructor: rellena los 3 atributos al crear un Equipos nuevo.
    // OJO al orden de los parámetros: (nombre, valorEquipo, logo) — fácil de confundir si no se mira bien.
    public Equipos(String nombre, float valorEquipo, int logo) {
        this.nombre = nombre;
        this.logo = logo;
        this.valorEquipo = valorEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public float getValorEquipo() {
        return valorEquipo;
    }

    public void setValorEquipo(float valorEquipo) {
        this.valorEquipo = valorEquipo;
    }
}
