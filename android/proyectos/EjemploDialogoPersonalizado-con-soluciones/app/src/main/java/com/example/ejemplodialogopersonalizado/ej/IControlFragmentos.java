package com.example.ejemplodialogopersonalizado.ej;

// El "contrato": cualquier Activity que use estos fragmentos debe implementarlo
public interface IControlFragmentos {

    void cambiarColor(int color);

    void cambiarTexto(String texto);
}
