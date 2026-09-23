package com.example.ejerciciopokemon.model;

import java.io.Serializable;

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
