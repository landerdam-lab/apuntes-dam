# 📱 AdapterDam2

## ¿Qué hace la app en la vida real?
Es un menú con tres botones que enseñan tres formas distintas de mostrar listas de cosas en el móvil: una lista de compañías de teléfono, un desplegable de cursos y una rejilla de fotos. Al tocar una foto de la rejilla, esta "vuela" hasta convertirse en la foto grande de la siguiente pantalla, como un efecto de cine.

## A. Mapa de Pantallas (Lo que ve el usuario)
- **Pantalla principal (MainActivity):** tres botones — "ListView", "Spinner" y "GridView" — cada uno lleva a un ejemplo distinto de lista.
- **Lista de compañías:** una lista vertical con el logo, nombre y precio de cada compañía telefónica.
- **Desplegable de cursos:** un menú desplegable (como el selector de país de un formulario) que, al elegir un curso, abre una pantalla mostrando el curso elegido.
- **Rejilla de fotos:** una cuadrícula de imágenes; al tocar una, se abre a pantalla completa con una animación de "la foto crece hasta ocupar toda la pantalla".

## B. Cómo funcionan las piezas por dentro (con analogías sencillas)
- **Los datos (Modelos):** cada compañía telefónica guarda su nombre, su logo y su precio, como una ficha de datos.
- **El conector (Adaptadores):** el adaptador es como un camarero que va cogiendo cada ficha de la "cocina" (la lista de datos) y la coloca, ya emplatada (con su diseño), en la mesa (la pantalla) — uno por cada fila o casilla de la lista/rejilla.

## C. El recorrido del usuario (paso a paso)
1. El usuario abre la app y ve el menú con 3 botones.
2. Si toca "ListView", va a la lista de compañías (cada fila viene de una ficha distinta).
3. Si toca "Spinner", ve un desplegable; al elegir un curso, la app abre otra pantalla y le muestra el nombre del curso elegido — como pasar una nota escrita de una habitación a otra.
4. Si toca "GridView", ve fotos en rejilla; al tocar una, la app le dice a la siguiente pantalla "quiero enseñar exactamente esta foto" y hace que crezca suavemente hasta llenar la pantalla nueva.

## D. 💡 Glosario rápido de este proyecto
- **Intent** = la carta de instrucciones que la app manda para abrir una nueva ventana (y, si hace falta, llevar algún dato dentro).
- **Adapter** = el "camarero" que conecta los datos con lo que se ve en pantalla.
- **Transición** = el efecto de movimiento/animación al pasar de una pantalla a otra.
- **Shared element (elemento compartido)** = cuando un elemento (como la foto) parece ser "el mismo objeto" que viaja de una pantalla a otra, en vez de desaparecer y aparecer de golpe.
