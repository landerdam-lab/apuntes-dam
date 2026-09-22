package com.example.ejemplodialogopersonalizado.ej;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Nota") //Consulta en la tabla Nota
public class Nota {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titulo;

    public Nota() {
    }

    @Ignore //Para que Room no use este constructor (le falta un parametro)
    public Nota(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
