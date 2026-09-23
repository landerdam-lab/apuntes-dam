---
tags:
  - android
  - concepto
---

# `View` y `ViewGroup`, a fondo

Todo lo que se ve en pantalla en Android —un texto, una imagen, un botón, pero también un layout entero que los contiene— es, en el fondo, una instancia de `View` o de su subclase `ViewGroup`. Este documento explica la base sobre la que están construidos [02-diseno-basado-en-pesos](02-diseno-basado-en-pesos.md), [03-constraintlayout](03-constraintlayout.md) y [07-adaptadores](07-adaptadores.md).

## 1. `View`: la unidad básica de interfaz

Una `View` es **un rectángulo en pantalla que sabe dibujarse a sí mismo y reaccionar a eventos** (toques, foco, etc.). `TextView`, `ImageView`, `Button`, `ImageButton`, `EditText`, `ListView`, `GridView`, `ProgressBar`... **todos** son subclases de `View`. Cada una añade su propio comportamiento (un `TextView` sabe dibujar texto, un `ImageView` sabe dibujar un `Bitmap`), pero todas comparten propiedades básicas heredadas de `View`: posición, tamaño, visibilidad (`View.VISIBLE`/`INVISIBLE`/`GONE`), padding, fondo, si puede recibir clics, etc.

```java
ImageView ivLogo = fila.findViewById(R.id.ivEquipoLogo);   // ImageView ES-UN View
TextView tvNombre = fila.findViewById(R.id.tvEquipoNombre); // TextView ES-UN View
```

## 2. `ViewGroup`: una `View` que contiene otras `View`

`ViewGroup` es una subclase especial de `View` que además **puede contener y organizar otras vistas dentro de sí** — es decir, es un **contenedor**. `LinearLayout`, `ConstraintLayout`, `FrameLayout`, e incluso `ListView`/`GridView`/`RecyclerView`, son todos `ViewGroup`.

```
                    View  (clase base: dibujar, tamaño, posición, eventos...)
                     │
         ┌───────────┴───────────┐
         │                        │
   ViewGroup                (Views "hoja": no contienen otras)
   (contiene otras Views)    TextView, ImageView, Button, ProgressBar...
         │
   ┌─────┼──────────┬───────────────┐
LinearLayout  ConstraintLayout  ListView/GridView/RecyclerView  ...
```

Esto explica por qué en un layout XML puedes anidar contenedores dentro de contenedores (como los `LinearLayout` anidados de [02-diseno-basado-en-pesos](02-diseno-basado-en-pesos.md)), y por qué un `ListView` puede "contener" filas que a su vez son otro `ConstraintLayout` con varias vistas dentro — todo es la misma jerarquía de `View`/`ViewGroup`, recursivamente.

## 3. El parámetro `parent` en `inflate()` — por fin explicado del todo

Esto aparece en **todos** los adaptadores de tus proyectos y rara vez se explica bien:

```java
View fila = inflater.inflate(R.layout.item_equipo, parent, false);
```

- **`parent`** — el `ViewGroup` donde esta vista `fila` va a insertarse finalmente (en la práctica, el propio `ListView`/`GridView` que está pidiendo la fila). Se necesita, aunque no se use para insertar ahora mismo, porque **la vista raíz del layout que inflas necesita saber en qué tipo de contenedor va a vivir**, para poder interpretar correctamente sus propios `android:layout_width`/`android:layout_height` (esos atributos son en realidad instrucciones **para el padre**, no para la vista misma — ver §5).
- **`false`** — "no adjuntes automáticamente esta vista al `parent` todavía". El propio `ListView`/`GridView` se encargará de colocarla en su sitio después de que `getView()` termine de devolverla. Si pusieras `true`, la vista se añadiría dos veces (una por ti, y otra por el `ListView` al recibir el resultado), causando errores o duplicados.

## 4. La jerarquía de vistas y el "árbol" que crea `setContentView`

```java
setContentView(R.layout.activity_main);
```

Convierte el XML en un árbol de objetos en memoria:

```
ConstraintLayout (raíz, id="main")
 ├── TextView (id="textView")
 └── ListView (id="lvEquipos")
      └── (filas generadas dinámicamente por el Adapter, cada una un ConstraintLayout con sus propias vistas hijas)
```

`findViewById(R.id.xxx)` recorre este árbol completo buscando el id — por eso funciona igual de bien encontrando la raíz (`R.id.main`) que un `TextView` enterrado varios niveles dentro de contenedores anidados.

## 5. El ciclo interno de dibujado: `measure` → `layout` → `draw` (mención general)

Cuando el sistema necesita mostrar/actualizar la pantalla, cada `View` pasa (de forma recursiva, empezando por la raíz) por tres fases:

1. **`measure`** — cada `View` calcula **cuánto espacio necesita** (según su contenido y sus atributos `layout_width`/`layout_height`: `wrap_content`, `match_parent`, un valor fijo, o `0dp` + peso, ver [02-diseno-basado-en-pesos](02-diseno-basado-en-pesos.md)). Un `ViewGroup` primero pregunta a sus hijos cuánto necesitan, y así decide cuánto necesita él mismo.
2. **`layout`** — una vez que todos saben su tamaño, cada `ViewGroup` decide **dónde exactamente** coloca a cada uno de sus hijos (aquí es donde entran en juego las reglas concretas de cada tipo de contenedor: reparto por pesos en `LinearLayout`, restricciones en `ConstraintLayout`, columnas en `GridView`...).
3. **`draw`** — cada `View`, ya con tamaño y posición decididos, se dibuja a sí misma sobre el `Canvas` (lienzo) de la pantalla.

No hace falta programar nada de esto a mano en tus proyectos (Android lo hace automáticamente cada vez que cambias algo visible), pero explica, por ejemplo, por qué poner `layout_height="200dp"` en vez de `0dp` + peso "rompe" el reparto proporcional visto en [02-diseno-basado-en-pesos](02-diseno-basado-en-pesos.md) §2: en la fase `measure`, esa vista ya declara un tamaño fijo propio, así que el `ViewGroup` padre no tiene ese espacio disponible para repartir proporcionalmente entre los demás.

## 6. `View.GONE` vs `View.INVISIBLE` (mención útil aunque no se use en tus proyectos actuales)

```java
vista.setVisibility(View.GONE);        // desaparece Y deja de ocupar espacio (el layout se recalcula sin ella)
vista.setVisibility(View.INVISIBLE);   // desaparece pero SIGUE ocupando su espacio (queda un "hueco")
vista.setVisibility(View.VISIBLE);     // normal, visible
```
Un error común es esperar que `INVISIBLE` haga desaparecer el hueco que deja la vista — para eso hace falta `GONE`.

## Ver también
- [02-diseno-basado-en-pesos](02-diseno-basado-en-pesos.md) y [03-constraintlayout](03-constraintlayout.md) — dos sistemas concretos con los que un `ViewGroup` decide la fase `layout` de sus hijos.
- [07-adaptadores](07-adaptadores.md) — cómo `LayoutInflater` crea nuevas `View` en tiempo de ejecución a partir de un XML.
- [14-recyclerview](14-recyclerview.md) — la vista moderna que sustituye a `ListView`/`GridView`, construida sobre estos mismos conceptos.
