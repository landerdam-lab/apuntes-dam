package com.example.ejerciciodialogopokemon.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

// MODELO / ENTIDAD: representa un Pokemon. @Entity hace que Room cree una TABLA donde cada
// atributo es una columna y cada objeto Pokemon una fila (ver conceptos/17-room-base-de-datos.md).
// implements Serializable: permite meter el objeto ENTERO en un Bundle para pasarlo de una
// pantalla a otra (ver MainActivity.editarPokemon y UpdateActivity).
@Entity(tableName = "Pokemon")
public class Pokemon implements Serializable
{
    // Clave primaria autogenerada: Room le asigna el numero solo (1, 2, 3...) al insertar
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String tipo;
    private String URLimagen;

    // Constructor vacio: Room lo necesita para crear objetos Pokemon al leer de la BD
    public Pokemon()
    {

    }

    // Constructor que usamos nosotros para crear un Pokemon nuevo (sin id, lo pone Room).
    // @Ignore le dice a Room "no uses este constructor", porque solo puede quedarse con uno.
    @Ignore
    public Pokemon(String nombre, String tipo, String URLimagen)
    {
        this.nombre = nombre;
        this.tipo = tipo;
        this.URLimagen = URLimagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getURLimagen() {
        return URLimagen;
    }

    public void setURLimagen(String URLimagen) {
        this.URLimagen = URLimagen;
    }
}
