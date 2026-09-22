# 📱 equiposFutbol

## ¿Qué hace la app en la vida real?
Es el principio de una app de equipos de fútbol: la lista de equipos (con su logo, nombre y valor) ya funciona, pero la pantalla que debería mostrar los jugadores de cada equipo todavía está vacía, sin terminar.

## A. Mapa de Pantallas (Lo que ve el usuario)
- **Pantalla principal (MainActivity):** una lista con el logo, nombre y valor de cada equipo de fútbol — esta parte **sí funciona**.
- **Pantalla de jugadores:** debería mostrar la rejilla de jugadores del equipo elegido, pero de momento se abre completamente vacía, sin nada dentro.

## B. Cómo funcionan las piezas por dentro (con analogías sencillas)
- **Los datos (Modelos):** cada equipo guarda su nombre, su logo y su valor, como una ficha de equipo; cada jugador tendría su propia ficha con nombre, posición, altura y foto, pero esas fichas de jugador todavía no se llegan a usar.
- **El conector (Adaptadores):** el adaptador de equipos es el camarero que sí funciona: coge cada ficha de equipo y la coloca en la lista. El adaptador de jugadores existe, pero está "vacío por dentro" — sería como un camarero que coge el plato pero no le pone nada encima.

## C. El recorrido del usuario (paso a paso)
1. El usuario abre la app y ve la lista de equipos, que funciona bien.
2. Toca un equipo... pero la app tiene un fallo interno al preparar "la carta de instrucciones" para la siguiente pantalla, así que esa información no llega.
3. Se abre la pantalla de jugadores, pero como no ha recibido los datos y tampoco tiene su rejilla montada, aparece vacía.

## D. 💡 Glosario rápido de este proyecto
- **Intent (la "carta de instrucciones")** = el mensaje que una pantalla manda para abrir otra, y que puede llevar datos dentro; aquí hay un error donde se escribe la carta pero luego se envía otra distinta vacía, así que el dato nunca llega.
- **Adapter (el "camarero")** = el encargado de mostrar cada ficha de datos como una fila o casilla en pantalla; aquí el de jugadores está sin terminar.
- **Proyecto a medias** = este ejercicio sirve para ver tanto lo que funciona bien (la lista de equipos) como lo típico que falla cuando algo queda sin terminar.
