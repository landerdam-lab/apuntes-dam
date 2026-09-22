# 📱 EjercicioFragmentos

## ¿Qué hace la app en la vida real?
Es un mini-formulario de "ficha de persona" repartido en tres pasos: primero pides el nombre, luego el apellido y la fecha (con el nombre ya puesto de antes), y al final todas las fichas completadas se van acumulando en una rejilla, como si fueras rellenando y coleccionando tarjetas de visita.

## A. Mapa de Pantallas (Lo que ve el usuario)
- **Pantalla principal (MainActivity):** una única pantalla dividida en tres zonas, siempre visibles.
- **Zona 1 (arriba):** un campo para escribir el nombre y un botón "Crear".
- **Zona 2 (medio):** campos para apellido y fecha (el nombre ya viene rellenado solo) y un botón "Ficha".
- **Zona 3 (abajo):** una rejilla que va mostrando cada ficha de persona ya completada, sin borrar las anteriores.

## B. Cómo funcionan las piezas por dentro (con analogías sencillas)
- **Los datos (Modelos):** cada "Persona" guarda su nombre, apellido y fecha, como una tarjeta de visita completa.
- **El conector (Adaptadores):** el adaptador es como un camarero que coge cada tarjeta de persona ya completada y la coloca, emplatada, como una casilla más de la rejilla de abajo.

## C. El recorrido del usuario (paso a paso)
1. El usuario escribe un nombre arriba y toca "Crear".
2. Ese nombre aparece ya escrito en el paso del medio (como si se lo hubieran chivado).
3. Completa apellido y fecha, y toca "Ficha".
4. Se crea una ficha completa de esa persona y aparece como una nueva tarjeta en la rejilla de abajo, sumándose a las que ya había.
5. Puede repetir el proceso para ir añadiendo más fichas, que se van acumulando todas juntas.

## D. 💡 Glosario rápido de este proyecto
- **Fragmento** = un "trozo" de pantalla independiente, como una pieza de puzle.
- **Serializable** = la etiqueta que permite meter una tarjeta de persona completa dentro del "paquete" que viaja entre zonas de la pantalla.
- **GridView (rejilla)** = una cuadrícula donde se van colocando las fichas, una al lado de otra.
