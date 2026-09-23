package com.example.ejercicioadaptadoresfinal.modelos;

import java.io.Serializable;
import java.util.ArrayList;

// Clase "modelo": solo guarda datos (no tiene lógica de pantalla). Representa una liga entera,
// que por dentro contiene una lista de Equipo. "implements Serializable" (ver 00-programacion-basica.md §7
// y conceptos/01-fundamentos-bundle-intent-ciclo-vida.md §2.3) es la promesa de que este objeto se puede
// "empaquetar" para viajar dentro de un Bundle/Intent de una pantalla a otra.
public class Liga implements Serializable {

    private ArrayList<Equipo> equipos;   // ATRIBUTO: la lista de equipos de esta liga

    // Constructor sin parámetros: al crear una Liga nueva, empieza con la lista de equipos vacía.
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
