---
tags:
  - android
  - concepto
---

# Diseño basado en pesos (`LinearLayout` + `layout_weight`)

## 1. La idea

`LinearLayout` coloca sus hijos **en una sola fila o columna** (según `android:orientation="horizontal"` o `"vertical"`), uno detrás de otro. El "diseño basado en pesos" es una técnica para repartir el espacio disponible **proporcionalmente** entre esos hijos, en vez de darles un tamaño fijo en `dp`. Es la forma clásica (pre-`ConstraintLayout`) de construir rejillas y pantallas que se adaptan a cualquier tamaño de pantalla.

## 2. La regla de oro: tamaño a `0dp` + `layout_weight`

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:layout_weight="0.5" />
```

Cuando un hijo de un `LinearLayout` tiene:
- la dimensión **que varía según el reparto** puesta a `0dp` (si el `LinearLayout` padre es vertical, es la `layout_height`; si es horizontal, la `layout_width`), y
- un `android:layout_weight` mayor que 0,

Android reparte el espacio **sobrante** del `LinearLayout` entre todos los hijos con peso, proporcionalmente al valor de su peso. Si pones la dimensión a un valor fijo (`200dp`) en vez de `0dp`, el peso deja de tener el efecto esperado, porque Android ya le ha dado un tamaño y el peso solo repartiría el espacio *extra* que sobre después de eso (un error muy común).

## 3. Ejemplo real completo: `EjercicioDiseno/activity_main.xml`

Este layout construye una pantalla de menú con cabecera, cuerpo (una rejilla 2×2 de botones) y pie, usando **solo pesos, sin `ConstraintLayout`**:

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <!-- CABECERA: ocupa 0.5 partes de 5 -->
    <LinearLayout
        android:layout_height="0dp"
        android:layout_weight="0.5" ...>
        <TextView android:text="@string/titulo" .../>
    </LinearLayout>

    <!-- CUERPO: ocupa 4 partes de 5 -->
    <LinearLayout
        android:layout_height="0dp"
        android:layout_weight="4" ...>
        ...
    </LinearLayout>

    <!-- PIE: ocupa 0.5 partes de 5 -->
    <LinearLayout
        android:layout_height="0dp"
        android:layout_weight="0.5" ...>
        ...
    </LinearLayout>

</LinearLayout>
```

**Cómo se calcula el reparto:** Android suma todos los `layout_weight` de los hijos directos: `0.5 + 4 + 0.5 = 5`. Cada hijo recibe `(su_peso / suma_total)` del espacio disponible:
- Cabecera → `0.5 / 5 = 10%` de la altura de pantalla.
- Cuerpo → `4 / 5 = 80%`.
- Pie → `0.5 / 5 = 10%`.

Los pesos **no tienen que sumar 1 ni ser proporciones "redondas"** — son solo una proporción relativa entre sí. Podrías haber usado `1`, `8`, `1` y el resultado sería idéntico (mismas proporciones: 10/80/10).

### 3.1. Anidando pesos: la rejilla 2×2 de botones

Dentro del "cuerpo" (peso 4), hay dos filas, cada una con peso `1` (mitad y mitad de esa zona):

```xml
<LinearLayout android:orientation="vertical" android:layout_weight="4" ...>

    <!-- Línea 1: ocupa la mitad del cuerpo -->
    <LinearLayout android:orientation="horizontal" android:layout_height="0dp" android:layout_weight="1">

        <!-- Celda izquierda: la mitad del ancho de la línea -->
        <LinearLayout android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1">
            <ImageButton android:layout_height="0dp" android:layout_weight="3" android:id="@+id/btnNuevo"/>
            <TextView    android:layout_height="0dp" android:layout_weight="1" android:text="@string/nuevo"/>
        </LinearLayout>

        <!-- Celda derecha: la otra mitad -->
        <LinearLayout android:orientation="vertical" android:layout_width="0dp" android:layout_weight="1">
            <ImageButton android:layout_height="0dp" android:layout_weight="3" android:id="@+id/botonCaballo"/>
            <TextView    android:layout_height="0dp" android:layout_weight="1" android:text="@string/calendario"/>
        </LinearLayout>

    </LinearLayout>

    <!-- Línea 2: la otra mitad del cuerpo (mismo patrón) -->
    ...
</LinearLayout>
```

Observa el patrón fractal: **cada nivel de anidación reparte su propio espacio entre sus propios hijos**, sin que le importe lo que pase fuera de él. Dentro de cada celda, el `ImageButton` (peso `3`) ocupa 3/4 de la altura de la celda y el `TextView` con la etiqueta (peso `1`) ocupa el 1/4 restante — así el icono es grande y el texto queda pequeño debajo, sea cual sea el tamaño real de la pantalla.

Este patrón (orientación alterna vertical→horizontal→vertical, con `0dp`+peso en cada nivel) es exactamente como se construyen tablas/rejillas responsivas con `LinearLayout`.

## 4. `android:layout_margin` vs el "hueco" entre celdas

Cada celda aquí lleva `android:padding="30dp"` (espacio *dentro* de la celda, entre su borde y su contenido) y las cajas de cabecera/cuerpo/pie llevan `android:layout_margin="5dp"` (espacio *fuera*, entre la caja y sus vecinas). Es la manera de separar visualmente los bloques sin que los pesos dejen de sumar el 100% del espacio disponible (el margen se resta del espacio que se reparte, pero de forma consistente en todos los hijos).

## 5. `style="@style/titulo"` — estilos reutilizables

```xml
<TextView android:text="@string/titulo" style="@style/titulo" />
```

En vez de repetir `android:textSize`, `android:textColor`, `android:fontFamily`, etc. en cada `TextView`, se define una vez en `res/values/styles.xml`:

```xml
<style name="titulo">
    <item name="android:textSize">32sp</item>
    <item name="android:textColor">#FFFFFF</item>
</style>
<style name="titulo.sombreado" parent="titulo">
    <item name="android:shadowColor">#000000</item>
</style>
```

`titulo.sombreado` (con un punto) es un estilo que **hereda** de `titulo` y añade/sobreescribe atributos — es el mecanismo de herencia de estilos de Android (equivalente a heredar una clase CSS). Esto es una simplificación didáctica del estilo real usado en [../proyectos/EjercicioDiseno](../05-proyectos-alumno/EjercicioDiseno.md), que además añade `shadowDx`/`shadowDy`/`shadowRadius` para controlar la dirección y el desenfoque de la sombra — ver el detalle completo en [15-drawables-formas-y-selectores](15-drawables-formas-y-selectores.md) §3.

## 6. Cuándo usar pesos vs `ConstraintLayout`

- **Pesos (`LinearLayout`)**: ideal para rejillas regulares y reparto proporcional simple (mitad/mitad, tercios, etc.), como este menú de botones.
- **`ConstraintLayout`** (ver [03-constraintlayout](03-constraintlayout.md)): ideal cuando las vistas se posicionan unas relativas a otras de forma más libre (no en fila/columna estricta), y es lo que usa Android Studio por defecto al crear una Activity nueva — por eso la mayoría de tus otras pantallas (`activity_main.xml` de `EjercicioAdaptadoresFinal`, los `item_equipo.xml`, etc.) usan `ConstraintLayout` en vez de pesos.

## 🏋️ Practica esto

- [Nivel 2 — ejercicios 2.1, 2.2 y 2.5](../07-ejercicios/02-nivel-2-disenos-xml.md) (columnas 1:2:1, rejilla 2×2, cálculo de pesos)
