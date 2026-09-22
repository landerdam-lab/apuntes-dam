---
tags:
  - android
  - concepto
---

# Drawables: formas (`<shape>`), degradados y selectores de estado (`<selector>`)

Este documento cubre un tema que se usa a fondo en [EjercicioDiseno](../proyectos/EjercicioDiseno.md) pero que no había quedado documentado: cómo se construyen fondos, marcos y estados de botón **sin usar ninguna imagen**, solo con XML.

## 1. `<shape>`: dibujar formas geométricas con XML

Un fichero en `res/drawable/nombre.xml` que empiece por `<shape>` no es una imagen — es una **receta** que Android dibuja en el momento, con la forma, colores y bordes que le indiques. Se referencia exactamente igual que una imagen normal: `android:background="@drawable/nombre"`.

### 1.1. Fondo con degradado (`fondo.xml`, real de `EjercicioDiseno`)

```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <gradient
        android:startColor="#00bfff"
        android:endColor="#000bff"
        android:centerColor="#e0f8f7"
        android:angle="270" />
</shape>
```
- `android:shape="rectangle"` — la forma base (también existen `oval`, `line`, `ring`).
- `<gradient>` — en vez de un color plano, un **degradado** entre `startColor` → `centerColor` → `endColor`, en la dirección que marca `angle` (en grados, múltiplos de 45: `0` = izquierda→derecha, `90` = abajo→arriba, `270` = arriba→abajo...).

Se aplica poniéndolo como fondo del contenedor raíz:
```xml
<LinearLayout ... android:background="@drawable/fondo">
```

> Nota real: en el proyecto `EjercicioDiseno` actual, `fondo.xml` usa los tres colores del degradado iguales (`#AAC1E2` en los tres), lo que en la práctica produce un **color plano** (sin degradado visible) — sigue siendo un `<shape>` válido, solo que con `startColor`/`centerColor`/`endColor` coincidentes no se aprecia transición de color. Para ver un degradado real hace falta que esos tres colores sean distintos, como en el ejemplo de arriba.

### 1.2. Marco/borde (`cabecera.xml`, real de `EjercicioDiseno`)

```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@android:color/transparent" />
    <padding android:left="8dp" android:right="8dp" android:top="2dp" android:bottom="2dp" />
    <corners android:radius="48dp" />
    <stroke android:color="@color/blanco" android:width="2dp" />
</shape>
```
- `<solid>` — el color de **relleno** de la forma. Aquí se pone deliberadamente `transparent`: si no lo haces explícito, en algunas versiones de Android el relleno por defecto es blanco opaco, lo que taparía cualquier contenido que hubiera detrás del marco (por ejemplo, el fondo degradado de toda la pantalla).
- `<padding>` — espacio interno entre el borde de la forma y lo que se ponga dentro (funciona igual que el `android:padding` de un `ViewGroup` normal).
- `<corners android:radius="48dp">` — redondea las cuatro esquinas con ese radio. Un valor grande (como `48dp`) sobre un rectángulo pequeño produce una forma tipo "píldora" (bordes totalmente redondeados).
- `<stroke>` — el **borde/contorno** de la forma: color y grosor.

El resultado es un "marco" transparente por dentro, con solo un borde blanco redondeado — perfecto para usar como fondo de la cabecera del menú y que se vea "flotando" sobre el degradado de fondo de toda la pantalla.

## 2. `<selector>`: drawables que cambian según el estado de la vista

```xml
<!-- res/drawable/boton_pulsado_nuevo.xml -->
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- PULSADO -->
    <item
        android:drawable="@drawable/nuevo_b"
        android:state_pressed="true" />

    <!-- NO PULSADO (por defecto) -->
    <item
        android:drawable="@drawable/nuevo"
        android:state_pressed="false" />
</selector>
```

Un `<selector>` es una **lista de "si pasa esto, muestra esta imagen"**, evaluada de arriba a abajo: Android recorre los `<item>` en orden y usa el primero cuyas condiciones (`state_xxx`) coincidan con el estado actual de la vista. Aquí solo hay dos estados (`state_pressed="true"`/`false`), pero existen muchos más: `state_focused`, `state_selected`, `state_enabled`, etc.

**El orden importa**: el estado "por defecto" (sin condiciones, o con la condición más genérica) debe ir **al final** — si pusieras `state_pressed="false"` primero, ese `<item>` coincidiría siempre (incluida la mayoría del tiempo en que el botón no está pulsado) y el `<item>` de `state_pressed="true"` nunca llegaría a evaluarse cuando sí tocara.

### Cómo se usa

```xml
<ImageButton
    android:id="@+id/btnNuevo"
    android:background="@drawable/boton_pulsado_nuevo" />
```
Con esto, el propio sistema (sin necesidad de código Java) cambia automáticamente la imagen del botón mientras el usuario lo mantiene pulsado, y la devuelve a la normal al soltar — es la técnica estándar para dar "feedback visual táctil" a botones con imagen personalizada, en lugar del efecto "ripple" gris por defecto de Android.

## 3. Estilos y sombra de texto (`titulo`, `titulo.sombreado`)

Ampliando lo visto en [02-diseno-basado-en-pesos](02-diseno-basado-en-pesos.md) §4, el proyecto `EjercicioDiseno` define, en `res/values/themes.xml`:

```xml
<style name="titulo">
    <item name="android:gravity">center</item>
    <item name="android:textStyle">bold</item>
    <item name="android:textColor">@color/white</item>
    <item name="android:textSize">@dimen/tamanoTitulo</item>
</style>
<style name="titulo.subTitulo">
    <item name="android:textSize">@dimen/tamanoSubTitulo</item>
</style>
<style name="titulo.sombreado">
    <item name="android:shadowColor">#000000</item>
    <item name="android:shadowDx">3</item>
    <item name="android:shadowDy">2</item>
    <item name="android:shadowRadius">1.8</item>
</style>
```

- `titulo.subTitulo` **hereda** de `titulo` (el punto en el nombre indica herencia) y solo cambia el tamaño de letra — reutiliza el resto (negrita, color blanco, centrado) sin repetirlo.
- `titulo.sombreado` también hereda de `titulo`, y añade una **sombra de texto**:
  - `shadowColor` — color de la sombra.
  - `shadowDx` / `shadowDy` — desplazamiento horizontal/vertical de la sombra respecto al texto (en píxeles), simulando de dónde viene la luz.
  - `shadowRadius` — el desenfoque (blur) de la sombra: cuanto mayor, más difuminada se ve.

Este estilo es el que da a las etiquetas bajo los botones del menú (`TOAST`, `ASYNCTASK`, etc.) ese efecto de texto "elevado" con sombra suave, legible incluso sobre el fondo degradado.

## 4. `res/values/dimens.xml` — tamaños centralizados

```xml
<resources>
    <dimen name="tamanoTitulo">20sp</dimen>
    <dimen name="tamanoSubTitulo">10sp</dimen>
</resources>
```
Igual que `strings.xml` centraliza los textos y `colors.xml` los colores, `dimens.xml` centraliza los **tamaños y distancias** (`sp` para texto, `dp` para el resto, ver [09-instalacion-estructura-proyecto](09-instalacion-estructura-proyecto.md) §3). Se referencia con `@dimen/nombre`, tanto desde XML (`android:textSize="@dimen/tamanoTitulo"`) como desde Java (`getResources().getDimension(R.dimen.tamanoTitulo)`).

## 5. Pantalla completa: dos técnicas distintas en tus proyectos

- **`EdgeToEdge.enable(this)` + listener de insets** (ver [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) §5) — la técnica moderna: el contenido se dibuja por detrás de las barras del sistema, pero deja hueco para ellas mediante padding. Es la que usan casi todos tus proyectos.
- **`android:windowFullscreen` en el tema** (usada en `EjercicioDiseno/res/values/themes.xml`):
  ```xml
  <style name="Theme.EjercicioDiseno" parent="Base.Theme.EjercicioDiseno">
      <item name="android:windowFullscreen">true</item>
  </style>
  ```
  Esta es la técnica **clásica**: oculta completamente la barra de estado (y con el tema `NoActionBar` ya se elimina también la barra de título/acción). A diferencia de `EdgeToEdge`, aquí simplemente no hay barra que mostrar — no hace falta gestionar ningún padding de insets para compensarla.

Ambas técnicas persiguen el mismo objetivo visual (aprovechar toda la pantalla), pero mediante mecanismos distintos; no es necesario combinarlas.

## Ver también
- [02-diseno-basado-en-pesos](02-diseno-basado-en-pesos.md) — el uso general de estilos (`style="@style/..."`).
- [../proyectos/EjercicioDiseno](../proyectos/EjercicioDiseno.md) — dónde se usan estos drawables y estilos en la práctica.

## 🏋️ Practica esto

- [Nivel 2 — ejercicio 2.4](../ejercicios/02-nivel-2-disenos-xml.md) (botón con forma y estado pulsado)
