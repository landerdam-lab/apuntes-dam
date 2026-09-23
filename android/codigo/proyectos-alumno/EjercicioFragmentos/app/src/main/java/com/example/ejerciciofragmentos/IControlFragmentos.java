package com.example.ejerciciofragmentos;

// El contrato entre los fragmentos y MainActivity (ver 00-programacion-basica §7 y
// 16-fragmentos §5). Cada método representa un "paso" del formulario completado.
public interface IControlFragmentos {
    // Se llama cuando Fragmento1 termina de pedir el nombre.
    void onNombreCompletado(String nombre);
    // Se llama cuando Fragmento2 termina de pedir apellido y fecha.
    void onApellidoYFechaCompletado(String nombre, String apellido, String fecha);
}
