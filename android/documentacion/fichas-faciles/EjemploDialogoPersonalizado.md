# 📱 EjemploDialogoPersonalizado

## ¿Qué hace la app en la vida real?
Es una pequeña app de acceso a un centro de estudios: te puedes registrar como usuario, y luego iniciar sesión con tu usuario y contraseña para entrar a un menú central. Guarda tus datos en el propio móvil, así que si cierras la app y la vuelves a abrir, tus usuarios siguen ahí.

## A. Mapa de Pantallas (Lo que ve el usuario)
- **Pantalla principal (MainActivity):** el nombre del centro y dos botones: "Acceder" (para iniciar sesión) y "Registrar" (para crear un usuario nuevo).
- **Ventana de acceso (diálogo de login):** una ventanita flotante donde escribes tu usuario y contraseña; si son correctos, entras al menú central.
- **Pantalla de registro:** una lista de todos los usuarios creados, con casillas para escribir uno nuevo, un botón para guardarlo, otro para actualizar uno existente y "mantener pulsado" para borrarlo (con una pregunta de confirmación antes de borrar de verdad).
- **Pantalla central:** el menú al que llegas tras acceder correctamente, con botones para temas futuros (sensores, voz, cámara...) que todavía no hacen nada.

## B. Cómo funcionan las piezas por dentro (con analogías sencillas)
- **Los datos (Modelos):** cada usuario guarda su nombre y su contraseña, como una ficha en un fichero de socios.
- **El conector (Adaptadores):** el adaptador es como un camarero que coge cada ficha de usuario guardada y la coloca, ya lista, como una fila en la lista de la pantalla de registro.
- **La base de datos:** es como un archivador dentro del propio móvil donde se guardan todas las fichas de usuario, para que no se pierdan al cerrar la app.

## C. El recorrido del usuario (paso a paso)
1. El usuario abre la app y ve el botón "Registrar".
2. Toca "Registrar", escribe un nombre y una contraseña, y pulsa "Nuevo" — la ficha se guarda en el archivador interno del móvil.
3. Vuelve a la pantalla principal y toca "Acceder".
4. Escribe el mismo usuario y contraseña en la ventanita — la app busca esa ficha en el archivador.
5. Si coincide, entra al menú central; si no coincide, la ventanita se cierra sin más aviso.
6. Desde la lista de usuarios también puede tocar uno para editarlo, o mantenerlo pulsado para borrarlo (con una pregunta de "¿seguro?" antes).

## D. 💡 Glosario rápido de este proyecto
- **Dialog / DialogFragment** = una ventanita que aparece flotando encima de la pantalla, como un cartel emergente.
- **Base de datos local (Room)** = el "archivador" dentro del móvil donde se guardan los datos aunque cierres la app.
- **CRUD** = las cuatro acciones básicas sobre datos: Crear, leer (mostrar), actualizar y borrar.
- **Hilo en segundo plano** = una tarea que ocurre "detrás" de la pantalla, para que buscar en el archivador no deje la app congelada un instante.
