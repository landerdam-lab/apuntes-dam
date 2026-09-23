# 📄 3-ToasPersonalizado

## En resumen
Enseña a mostrar avisos flotantes en pantalla: primero el aviso "normal" que trae Android por defecto (un texto que aparece unos segundos y desaparece), y después una versión "a medida" con logo e imagen propia, que se cierra sola pasado un tiempo.

## Puntos clave explicados fácil
- Un aviso normal se lanza con muy poco código: solo hace falta el texto y cuánto tiempo debe verse.
- Para hacer un aviso "a medida" (con imagen y texto propios), se diseña primero su aspecto en un archivo aparte, y luego se "infla" (se convierte ese diseño en algo que se puede mostrar) dentro de una ventanita flotante sin bordes.
- Es importante quitar el fondo blanco de esa ventanita para que parezca realmente un aviso flotante y no un recuadro extraño.
- Un "reloj" en segundo plano (Handler) es el encargado de cerrar la ventanita automáticamente pasados unos segundos, imitando a un aviso normal.

## ¿Con qué parte de la app se relaciona?
Son instrucciones de tarea paso a paso que enseñan la técnica del "toast personalizado", usada más adelante en varios proyectos (`Diseobesos`, `EjercicioDiseno`) para mostrar avisos con su propio diseño.
