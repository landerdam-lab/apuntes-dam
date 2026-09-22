package com.example.ejerciciofragmentos.modelos;

import java.io.Serializable;

// Un "modelo" (POJO, ver 00-programacion-basica §5): solo guarda datos (nombre, apellido, fecha)
// y no tiene ninguna lógica de interfaz. "implements Serializable" (ver 00-programacion-basica §7
// y 16-fragmentos §7) es necesario para poder meter objetos Persona dentro de un Bundle.
public class Persona implements Serializable {

    // ATRIBUTOS (ver 00-programacion-basica §5): los datos que cada Persona guarda.
    private String nombre;
    private String apellido;
    private String fecha;

    public Persona() {
    }

    // CONSTRUCTOR (ver 00-programacion-basica §5): rellena los 3 atributos al crear el objeto.
    // "this.nombre = nombre" significa "el atributo nombre DE ESTE OBJETO = el parámetro recibido".
    public Persona(String nombre, String apellido, String fecha) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
