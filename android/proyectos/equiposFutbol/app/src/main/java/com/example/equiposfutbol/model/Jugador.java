package com.example.equiposfutbol.model;

// Clase "modelo" de un jugador: nombre, posición, altura y foto (id local de imagen).
// A diferencia de los modelos de EjercicioAdaptadoresFinal, esta clase NO implementa Serializable
// porque en este proyecto nunca se llega a pasar un objeto Jugador completo de una pantalla a otra.
public class Jugador {
    private String nombre;
    private String posicion;
    private float altura;
    private int foto;


    // Constructor: rellena los 4 atributos al crear un Jugador nuevo.
    public Jugador(String nombre, String posicion, float altura,int foto) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.altura = altura;
        this.foto = foto;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }
}
