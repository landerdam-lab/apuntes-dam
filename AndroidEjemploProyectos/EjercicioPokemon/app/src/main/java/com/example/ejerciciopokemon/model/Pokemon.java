package com.example.ejerciciopokemon.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "Pokemon")
public class Pokemon implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String tipo;
    private String foto;
    private int pokedex;

    public Pokemon(String nombre, String tipo, String foto, int pokedex) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.foto = foto;
        this.pokedex = pokedex;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getPokedex() {
        return pokedex;
    }

    public void setPokedex(int pokedex) {
        this.pokedex = pokedex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
