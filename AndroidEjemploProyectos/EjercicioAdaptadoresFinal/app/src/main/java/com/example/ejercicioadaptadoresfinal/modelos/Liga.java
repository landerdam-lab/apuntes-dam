package com.example.ejercicioadaptadoresfinal.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Liga implements Serializable {

    private ArrayList<Equipo> equipos;

    public Liga() {
        equipos = new ArrayList<Equipo>();

    }

    public ArrayList<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(ArrayList<Equipo> equipos) {
        this.equipos = equipos;
    }
}
