# 📄 4-asyncTask

## En resumen
Enseña a hacer que la app "haga varias cosas a la vez" sin quedarse congelada: mientras una barra de progreso avanza y una imagen va cambiando (dando sensación de movimiento), el usuario puede seguir viendo la pantalla funcionar con normalidad.

## Puntos clave explicados fácil
- Si se hiciera todo el trabajo de la animación directamente en la pantalla principal, la app se quedaría "pillada" un rato, sin responder al tacto.
- Por eso se usa un "ayudante en segundo plano": una tarea aparte que se va encargando de cambiar la imagen y avanzar la barra, mientras la pantalla sigue funcionando con normalidad.
- Ese ayudante avisa a la pantalla en cada paso ("llevo el 43%, cambia a esta imagen") para que se vaya actualizando lo que se ve.
- Cuando termina, avisa con un mensaje ("Tarea finalizada") y cierra la pantalla solo.
- También se explica cómo llevar un mensaje de texto de una pantalla a otra al abrir una nueva ventana (como pasar una nota de una habitación a otra).

## ¿Con qué parte de la app se relaciona?
Son instrucciones de tarea que enseñan el patrón de "trabajo en segundo plano" usado en las animaciones de varios proyectos (`Diseobesos`, `EjercicioDiseno`).
