# 📄 1-DialogSQLitePersonalizado

## En resumen
Enseña a construir una app completa de acceso con usuario y contraseña: un diálogo de login "a medida", una pantalla de registro con lista de usuarios, y una base de datos guardada dentro del propio móvil (para que los usuarios no se pierdan al cerrar la app).

## Puntos clave explicados fácil
- Un diálogo personalizado es una ventanita flotante propia (con su propio diseño) que pide al usuario que escriba algo, en vez de usar los diálogos genéricos de Android.
- Los datos de cada usuario (nombre y contraseña) se guardan en un "archivador" dentro del móvil (una base de datos local), organizado como una tabla con filas y columnas.
- Existen operaciones básicas sobre esa tabla: guardar uno nuevo, buscar uno, modificar uno y borrar uno.
- Como consultar el archivador puede tardar un poco, esas operaciones se hacen "en segundo plano" (sin congelar la pantalla), y luego se "vuelve" al primer plano para actualizar lo que se ve.
- La lista de usuarios se actualiza sola en pantalla en cuanto cambia algo en el archivador, sin tener que refrescarla a mano.
- Al final, el login de verdad busca en el archivador si existe un usuario con ese nombre y esa contraseña exactos; si lo encuentra, deja pasar a la pantalla siguiente.

## ¿Con qué parte de la app se relaciona?
Son instrucciones de tarea completas (43 páginas) que dieron origen al proyecto `EjemploDialogoPersonalizado`, y también a la lógica de guardar datos que se reutilizó después en `EjercicioDialogoPokemon`.
