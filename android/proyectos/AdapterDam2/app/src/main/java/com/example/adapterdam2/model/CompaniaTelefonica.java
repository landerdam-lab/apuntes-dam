package com.example.adapterdam2.model;

// Clase MODELO (ver 00-programacion-basica.md §5): describe qué datos tiene una compañía
// telefónica, sin ninguna lógica de pantalla — solo atributos + constructor + getters/setters.
public class CompaniaTelefonica {

    private String nombre;

    private int logo; // id de un recurso R.drawable local (no una URL)

    private float precio;

    public CompaniaTelefonica(String nombre,float precio, int logo) {
        this.nombre = nombre;
        this.logo = logo;
        this.precio = precio;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
