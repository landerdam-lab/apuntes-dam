# 📱 EjercicioDiseno

## ¿Qué hace la app en la vida real?
Es un menú de aspecto muy cuidado (fondo azul, botones con sombra, imágenes que cambian al pulsarlas) con cuatro opciones: dos animaciones (un caballo andando y un Pikachu), un aviso emergente con un formulario de usuario/contraseña, y un botón que lleva a una pantalla nueva vacía.

## A. Mapa de Pantallas (Lo que ve el usuario)
- **Pantalla principal (MainActivity):** una cabecera, cuatro botones grandes en forma de rejilla 2x2 (que cambian de imagen al pulsarlos) y un pie de página.
- **Animación del caballo:** una imagen que "anda" mientras una barra de progreso avanza hasta el 100%.
- **Animación de Pikachu:** primero aparece un aviso de "cargando" 3 segundos, y después arranca la animación de Pikachu moviéndose.
- **Aviso con formulario:** una ventanita flotante con casillas para escribir un usuario y una contraseña.
- **Pantalla nueva:** una pantalla vacía, a la que se llega desde el botón "Nuevo".

## B. Cómo funcionan las piezas por dentro (con analogías sencillas)
- **Los datos (Modelos):** aquí no hay fichas de datos guardadas — lo que se gestiona son colecciones de imágenes (los fotogramas de cada animación).
- **El conector (Adaptadores):** este proyecto no usa listas ni adaptadores; en su lugar tiene "ayudantes en segundo plano" que van cambiando la imagen mostrada cada poco tiempo sin congelar la pantalla.

## C. El recorrido del usuario (paso a paso)
1. El usuario abre la app y ve el menú de 4 botones con su fondo azul y sus sombras.
2. Si toca el botón del caballo, ve la animación con su barra de progreso.
3. Si toca el botón de Pikachu, primero ve un aviso de "cargando" 3 segundos y luego la animación arranca sola.
4. Si toca el botón de "toast", aparece una ventanita flotante pidiéndole usuario y contraseña.
5. Si toca "Nuevo", pasa a una pantalla completamente vacía.

## D. 💡 Glosario rápido de este proyecto
- **Selector de imagen** = un truco de diseño para que un botón cambie de imagen automáticamente mientras está siendo pulsado.
- **Estilo reutilizable** = una "plantilla" de apariencia (por ejemplo, un tipo de sombra de texto) que se puede aplicar a varios textos sin repetir el mismo código una y otra vez.
- **AsyncTask (el "ayudante en segundo plano")** = una tarea que va cambiando la imagen de la animación sin bloquear el resto de la pantalla.
