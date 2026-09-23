# 📱 Diseobesos

## ¿Qué hace la app en la vida real?
Es un menú con dos botones curiosos: uno abre una animación de una imagen "andando" mientras se llena una barra de progreso, y el otro hace aparecer un aviso flotante en pantalla (como una notificación) que se cierra solo a los dos segundos.

## A. Mapa de Pantallas (Lo que ve el usuario)
- **Pantalla principal (MainActivity):** dos botones — uno de animación, otro de aviso ("toast personalizado").
- **Pantalla de animación:** una imagen que va cambiando rápido (dando la sensación de movimiento) mientras una barra de progreso avanza del 0% al 100%.
- **Aviso flotante (Dialog):** una ventanita pequeña, sin fondo, con una imagen y un texto, que aparece sobre la pantalla actual y desaparece sola pasados 2 segundos.

## B. Cómo funcionan las piezas por dentro (con analogías sencillas)
- **Los datos (Modelos):** aquí no hay "fichas" de datos como en otros proyectos — lo que se guarda es simplemente una colección de imágenes (los fotogramas de la animación) y el número de la barra de progreso.
- **El conector (Adaptadores):** este proyecto no usa listas, así que no hace falta camarero/adaptador; en su lugar hay un "ayudante en segundo plano" que se encarga de ir cambiando la imagen y avanzando la barra sin congelar la pantalla mientras lo hace.

## C. El recorrido del usuario (paso a paso)
1. El usuario abre la app y ve el menú con 2 botones.
2. Si toca el botón de animación, se abre una pantalla nueva donde una imagen "camina" mientras la barra de progreso sube, como una barra de descarga.
3. Cuando la animación termina, esa pantalla se cierra sola y vuelve al menú.
4. Si en vez de eso toca el botón de aviso, aparece una ventanita flotante con una imagen y un texto que se queda 2 segundos y se cierra sola.

## D. 💡 Glosario rápido de este proyecto
- **AsyncTask (el "ayudante en segundo plano")** = una tarea que trabaja aparte de la pantalla principal, para que la app no se quede "congelada" mientras hace algo que tarda (como ir cambiando fotogramas cada poco tiempo).
- **Dialog** = una ventanita que aparece encima de la pantalla actual, sin taparla del todo.
- **Toast personalizado** = un aviso hecho a medida (con imagen y texto propios) en vez del aviso gris simple que trae Android por defecto.
