# 📄 5-frameByFrame

## En resumen
Enseña otra forma de hacer una animación de imágenes: en vez de ir controlando el cambio de imagen "a mano" desde el código, se prepara de antemano una lista de fotogramas que Android va pasando solo, como una tira de dibujos animados, y se controla con botones de Play, Stop y Back.

## Puntos clave explicados fácil
- Se guarda una lista con cada imagen de la animación y cuánto tiempo debe verse cada una (por ejemplo, 50 milisegundos cada fotograma).
- Esa lista se pone como "fondo" de una imagen en blanco, y Android se encarga solo de ir pasando los fotogramas uno detrás de otro, como una peonza girando.
- El botón "Play" arranca la animación si no está ya en marcha.
- El botón "Stop" la detiene si está en marcha.
- El botón "Back" cierra la pantalla y vuelve a la anterior.

## ¿Con qué parte de la app se relaciona?
Son instrucciones de tarea que enseñan una segunda técnica de animación por fotogramas (más sencilla que la del documento del "ayudante en segundo plano"), usada en proyectos como `EjercicioDiseno`.
