package com.example.ejerciciodialogopokemon.model;

import java.io.Serializable;

// MODELO auxiliar: no se usa en ningun sitio del proyecto actualmente (no hay tabla @Entity
// para Tipo ni ninguna pantalla que lo instancie), parece pensado para una version futura
// donde cada tipo tuviera tambien una descripcion.
public class Tipo implements Serializable {

    private String tipo;
    private String desc;

    public Tipo(String tipo, String desc) {
        this.tipo = tipo;
        this.desc = desc;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
