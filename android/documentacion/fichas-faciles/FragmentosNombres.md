# 📱 FragmentosNombres

## ¿Qué hace la app en la vida real?
Es otra variación del ejercicio de "pantalla dividida en trozos": arriba escribes un texto y lo envías, en el medio aparece un formulario de nombre/apellido/fecha (con el nombre ya rellenado solo), y abajo debería haber una rejilla que todavía no se ha terminado de montar.

## A. Mapa de Pantallas (Lo que ve el usuario)
- **Pantalla principal (MainActivity):** una única pantalla dividida en tres zonas, una encima de otra.
- **Zona de arriba:** un campo de texto y un botón "Enviar".
- **Zona del medio:** un mini-formulario con nombre (ya rellenado solo con lo enviado arriba), apellido y fecha.
- **Zona de abajo:** debería mostrar una rejilla, pero de momento está vacía, sin terminar.

## B. Cómo funcionan las piezas por dentro (con analogías sencillas)
- **Los datos (Modelos):** aquí no hay fichas complejas — solo se pasa un texto suelto de una zona a otra.
- **El conector (Adaptadores):** la zona de abajo todavía no tiene su camarero/adaptador montado, así que no hay nada que mostrar ahí todavía.

## C. El recorrido del usuario (paso a paso)
1. El usuario escribe algo arriba y toca "Enviar".
2. Ese texto aparece automáticamente en la casilla "nombre" de la zona del medio.
3. Puede rellenar también apellido y fecha a mano, aunque de momento no hay ningún botón que haga algo con esos dos datos.
4. La zona de abajo se queda vacía, a la espera de que se termine de construir.

## D. 💡 Glosario rápido de este proyecto
- **Fragmento** = un "trozo" de pantalla independiente, como una pieza de puzle.
- **Bundle (el "paquete")** = el sobre con datos sueltos que viaja de una zona a otra; este proyecto tuvo, en una versión anterior, dos fallos típicos ya corregidos: buscar un dato con el nombre equivocado, y guardar un dato con una "etiqueta" vacía en el paquete — en ambos casos el dato simplemente no llegaba, sin avisar de ningún error.
- **Plantilla sin terminar** = cuando se crea una pieza nueva y se deja tal cual la generó Android Studio, sin rellenarla todavía con contenido propio (es lo que le pasa a la zona de abajo).
