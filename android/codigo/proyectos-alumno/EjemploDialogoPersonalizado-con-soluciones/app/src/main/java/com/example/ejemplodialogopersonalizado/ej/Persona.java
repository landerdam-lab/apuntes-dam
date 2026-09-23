package com.example.ejemplodialogopersonalizado.ej;

import java.io.Serializable;

// POJO Serializable: solo guarda datos
public class Persona implements Serializable {

    private String nombre, apellido, fecha;

    public Persona(String nombre, String apellido, String fecha) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = fecha;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getFecha() { return fecha; }
}
