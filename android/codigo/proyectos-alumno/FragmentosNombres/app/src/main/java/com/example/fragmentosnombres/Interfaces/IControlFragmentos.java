package com.example.fragmentosnombres.Interfaces;

// El "contrato" entre FragmentoArriba y MainActivity (ver 00-programacion-basica §7 y
// 16-fragmentos §5): quien implemente esta interfaz promete tener este método.
public interface IControlFragmentos {
    void  cambiarTexto(String texto);
}
