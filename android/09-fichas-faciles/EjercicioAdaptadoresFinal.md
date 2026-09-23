# 📱 EjercicioAdaptadoresFinal

## ¿Qué hace la app en la vida real?
Es como una mini ficha de la NBA: ves una lista de equipos, tocas uno y ves sus jugadores en una rejilla con sus fotos (descargadas de internet), y si tocas un jugador ves su ficha completa a pantalla grande, con un efecto de deslizamiento.

## A. Mapa de Pantallas (Lo que ve el usuario)
- **Pantalla principal (MainActivity):** una lista con el nombre, ciudad, logo y precio de cada equipo.
- **Pantalla de jugadores:** al tocar un equipo, aparece una rejilla con las fotos de sus jugadores.
- **Ficha del jugador:** al tocar un jugador, se abre su ficha a pantalla completa con una animación de deslizar desde abajo.

## B. Cómo funcionan las piezas por dentro (con analogías sencillas)
- **Los datos (Modelos):** hay tres tipos de fichas anidadas, como muñecas rusas: una Liga contiene Equipos, y cada Equipo contiene sus Jugadores.
- **El conector (Adaptadores):** el adaptador es como un camarero que coge cada ficha de equipo o de jugador y la coloca, ya emplatada, en su sitio de la lista o de la rejilla — y en este proyecto además va a buscar las fotos a internet (usando la URL guardada en cada ficha) antes de servirlas en el plato.

## C. El recorrido del usuario (paso a paso)
1. El usuario abre la app y ve la lista de equipos.
2. Toca un equipo — la app le pasa "esta es la ficha del equipo que elegiste" a la siguiente pantalla, como entregar una carpeta con todos sus datos.
3. Ve la rejilla de jugadores de ese equipo.
4. Toca un jugador — la app le pasa la ficha de ese jugador concreto a la pantalla de detalle, que se desliza hacia arriba mostrando su foto y sus datos.

## D. 💡 Glosario rápido de este proyecto
- **Picasso** = una herramienta que descarga una imagen de internet (a partir de su dirección web) y la coloca en pantalla.
- **Serializable** = una etiqueta que le dice a Android "esta ficha se puede meter dentro de la carpeta (Bundle) para pasarla de una pantalla a otra".
- **Transición** = el efecto de movimiento (deslizar, difuminar...) al cambiar de pantalla.
- **Permiso de Internet** = el aviso que la app necesita dar para poder descargar fotos de la red; sin él, las fotos no cargarían.
