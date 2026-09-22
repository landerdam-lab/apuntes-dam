# 📱 EjemploFragmentos

## ¿Qué hace la app en la vida real?
La pantalla está dividida en dos mitades: arriba escribes un texto y lo envías, y abajo aparece reflejado ese mismo texto. Además, desde un menú de tres puntos puedes cambiar el color del texto de abajo a rojo o verde.

## A. Mapa de Pantallas (Lo que ve el usuario)
- **Pantalla principal (MainActivity):** una única pantalla dividida en dos mitades (arriba y abajo), con un menú de tres puntos arriba a la derecha.
- **Mitad de arriba:** un campo de texto y un botón "Enviar".
- **Mitad de abajo:** un texto que cambia según lo que se haya enviado desde arriba, o según el color elegido en el menú.

## B. Cómo funcionan las piezas por dentro (con analogías sencillas)
- **Los datos (Modelos):** aquí no hay fichas de datos complejas — solo se pasa un texto suelto y un color de una mitad a la otra.
- **El conector (Adaptadores):** este proyecto no usa listas, así que no necesita camarero/adaptador. En su lugar, las dos mitades se comunican mediante un "mensajero" (la propia pantalla principal), que recoge lo que pasa arriba y se lo entrega a la mitad de abajo.

## C. El recorrido del usuario (paso a paso)
1. El usuario abre la app y ve las dos mitades, con la de abajo vacía.
2. Escribe algo arriba y toca "Enviar".
3. El texto aparece reflejado abajo, como si arriba mandara una nota y abajo la leyera.
4. Si en vez de eso abre el menú de tres puntos y elige "Rojo" o "Verde", el texto de abajo cambia de color.

## D. 💡 Glosario rápido de este proyecto
- **Fragmento** = un "trozo" de pantalla independiente, como si la pantalla completa se dividiera en piezas de puzle que se pueden cambiar por separado.
- **Interfaz (el "contrato")** = una lista de promesas que la pantalla principal se compromete a cumplir, para que los fragmentos sepan cómo pedirle cosas.
- **Bundle** = un "paquete" con datos sueltos (como el texto o el color) que viaja de una pieza a otra.
