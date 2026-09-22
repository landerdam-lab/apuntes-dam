package com.example.ejemplofragmentos;

// Una INTERFAZ (ver 00-programacion-basica §7): un "contrato" que dice qué métodos tiene que
// tener cualquier clase que la implemente, sin decir cómo. MainActivity la implementa (más abajo
// escribe el código de verdad); los fragmentos solo conocen esta interfaz, nunca a MainActivity
// directamente, para no depender de una Activity concreta (ver 16-fragmentos §5).
public interface IControlFragmentos {
    // Se llama cuando hay que cambiar el color del texto de FragmentoAbajo.
    void cambiarColor(int color);
    // Se llama cuando FragmentoArriba envía un texto nuevo.
     void cambiarTexto(String texto);
}
