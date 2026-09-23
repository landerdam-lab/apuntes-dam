package com.example.adapterdam2.model;

public class CompaniaTelefonica {

    private String nombre;
    private int logo;
    private float precio;

    public CompaniaTelefonica(String nombre, float precio, int logo) {
        this.nombre = nombre;
        this.precio = precio;
        this.logo = logo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
