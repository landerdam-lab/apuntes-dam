# 📄 8-Fragmentos

## En resumen
Enseña a construir una pantalla a partir de "piezas" independientes (fragmentos) que se pueden combinar, reemplazar y hacer que se comuniquen entre sí — por ejemplo, un trozo de arriba donde escribes un texto y un trozo de abajo que lo muestra.

## Puntos clave explicados fácil
- Un fragmento es como una pieza de puzle: un trozo de pantalla con su propio diseño y su propia lógica, que se puede combinar con otras piezas dentro de una misma pantalla.
- Cada fragmento tiene varios "momentos de vida" (se crea, se le prepara la vista, se muestra, se destruye...) parecidos a los de una pantalla completa, pero a menor escala.
- Para que un fragmento pueda "avisar" a la pantalla principal de algo (por ejemplo, "el usuario ha escrito este texto"), se usa un contrato (una interfaz) que la pantalla principal se compromete a cumplir.
- Para pasarle datos a un fragmento se usa un "paquete" (Bundle) a través de un método especial (`newInstance`), en vez de dárselos directamente al crearlo.
- También se explica cómo añadir un menú contextual (los tres puntos de arriba) para que, al elegir una opción, se dispare una acción sobre uno de los fragmentos (por ejemplo, cambiar el color del texto).

## ¿Con qué parte de la app se relaciona?
Son instrucciones de tarea que enseñan el patrón de fragmentos usado en los tres proyectos de fragmentos del curso (`EjemploFragmentos`, `EjercicioFragmentos`, `FragmentosNombres`).
