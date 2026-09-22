# 📱 EjercicioDialogoPokemon

## ¿Qué hace la app en la vida real?
Es una pequeña "Pokédex" personal: puedes añadir Pokémon con su nombre, tipo y una foto (por URL), filtrar la lista por tipo (Planta, Agua o Fuego), y tocar uno para editarlo o hacer doble clic para borrarlo, siempre con una confirmación antes de borrar de verdad.

## A. Mapa de Pantallas (Lo que ve el usuario)
- **Pantalla principal (MainActivity):** tres botones de tipo (Planta/Agua/Fuego) arriba, la lista de Pokémon en el medio (con foto, nombre y tipo) y un botón "Agregar" abajo.
- **Pantalla de agregar:** casillas para escribir el nombre, el tipo y la URL de una foto, y un botón para guardarlo.
- **Pantalla de editar:** igual que la de agregar, pero ya rellena con los datos del Pokémon que tocaste, para poder cambiarlos y guardarlos.

## B. Cómo funcionan las piezas por dentro (con analogías sencillas)
- **Los datos (Modelos):** cada Pokémon guarda su nombre, su tipo y la dirección web de su foto, como una carta de una baraja de cromos.
- **El conector (Adaptadores):** el adaptador es como un camarero que coge cada carta de Pokémon guardada, va a buscar su foto a internet, y la coloca ya emplatada como una fila de la lista.

## C. El recorrido del usuario (paso a paso)
1. El usuario abre la app y ve la lista de todos los Pokémon guardados.
2. Toca "Planta", "Agua" o "Fuego" para ver solo los de ese tipo.
3. Toca "Agregar", rellena nombre/tipo/foto y lo guarda — vuelve automáticamente a la lista, que se actualiza sola.
4. Toca un Pokémon de la lista (un solo clic) para editarlo, cambia lo que quiera y lo guarda.
5. Hace doble clic sobre un Pokémon para borrarlo — la app pregunta "¿seguro?" antes de borrarlo de verdad.

## D. 💡 Glosario rápido de este proyecto
- **Base de datos local (Room)** = el archivador dentro del móvil donde se guardan los Pokémon, aunque cierres la app.
- **Picasso** = la herramienta que descarga la foto de internet a partir de su dirección web y la muestra en pantalla.
- **Doble clic / clic largo** = dos formas distintas de tocar un elemento de la lista para que la app entienda que quieres hacer algo distinto (editar vs. borrar).
- **LiveData** = una lista "viva" que se redibuja sola en pantalla en cuanto cambian los datos guardados, sin que haya que pedírselo a mano.
