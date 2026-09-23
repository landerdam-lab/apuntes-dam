# 📄 6-Adaptadores

## En resumen
Enseña a mostrar colecciones de datos en pantalla de tres formas: una lista vertical (ListView), un desplegable (Spinner) y una rejilla de fotos (GridView) — y, para cada una, cómo hacer que cada elemento de datos se convierta en una fila o casilla con su propio diseño.

## Puntos clave explicados fácil
- Un "adaptador" es como un camarero: coge cada dato de la "cocina" (la lista de información) y lo sirve, ya emplatado con su diseño, en la mesa (la lista, el desplegable o la rejilla).
- Primero se usa un adaptador "de fábrica" muy sencillo para el desplegable, y luego se enseña a construir uno propio a mano, para controlar exactamente cómo se ve cada fila.
- Cada compañía telefónica de la lista lleva su logo, su nombre y su precio, y las filas alternan de color (gris claro / gris oscuro) para que se lean mejor.
- Al cargar muchas fotos grandes en la rejilla, la app puede ir lenta; para solucionarlo se usa una herramienta (Glide) que carga las imágenes de forma más eficiente.
- Al final se explica una versión "profesional" más moderna de las listas (RecyclerView), que hace lo mismo pero reutilizando las filas de forma más inteligente para que la app vaya más fluida.

## ¿Con qué parte de la app se relaciona?
Son instrucciones de tarea que enseñan la técnica de los adaptadores, usada en casi todos los proyectos con listas o rejillas (`AdapterDam2`, `EjercicioAdaptadoresFinal`, `equiposFutbol`).
