---
tags:
  - android
  - concepto
---

# Transiciones entre Activities

Hay **tres formas distintas** de animar el paso de una pantalla a otra en tus proyectos, de más simple a más avanzada. Es importante no mezclarlas mal, porque generan conflictos (ver §5).

## 0. La clasificación oficial (según el material del curso)

El curso distingue tres **momentos** en los que puede aplicarse una transición:
- **Entrada** — cuando una Activity aparece.
- **Salida** — cuando una Activity desaparece.
- **Elementos compartidos** — una vista concreta que existe en ambas pantallas "viaja" físicamente de una a otra (ver §3).

Para entrada/salida hay **tres efectos disponibles** como etiqueta XML dentro de `res/transition/`:
```xml
<explode />   <!-- las vistas entran/salen desde los bordes de la pantalla hacia/desde su posición -->
<slide />     <!-- las vistas entran/salen deslizándose desde un borde concreto (slideEdge) -->
<fade />      <!-- fundido de opacidad -->
```
(Tus proyectos usan `<autoTransition>`, que combina automáticamente `fade` + `changeBounds` + `fade`, y `<slide>` directamente — ver §2.3 para el detalle de ambos.)

Para elementos compartidos, las operaciones que se pueden animar son:
```
changeBounds        → cambios en el tamaño/posición del layout (la más habitual: "la imagen crece")
changeClipBounds     → cambios en los límites de recorte (qué parte de la vista se ve)
changeTransform       → cambios de escala y rotación
changeImageTransform    → cambios de tamaño/escala específicos de imágenes
```

Todo este sistema de transiciones (`android.transition.*`) requiere **API 21 (Android 5.0 Lollipop) como mínimo** — en tus proyectos no supone ninguna limitación real porque todos tienen `minSdk` muy por encima de 21 (ver [09-instalacion-estructura-proyecto](09-instalacion-estructura-proyecto.md) §2), pero es la razón histórica de que esta API exista como un sistema aparte en vez de estar simplemente integrada desde siempre.

---

## 1. `overridePendingTransition` — la forma clásica y más simple

```java
startActivity(new Intent(getApplicationContext(), SpinnerDam2Activity.class));
overridePendingTransition(R.anim.entrada, R.anim.salida);
```
*(`AdapterDam2/MainActivity.java`)*

Justo después de `startActivity(...)`, le dices al sistema qué animación de recurso usar para la Activity que **entra** y cuál para la que **sale**. `R.anim.entrada` / `R.anim.salida` son ficheros XML en `res/anim/` con animaciones simples (fade, slide, etc. definidas con `<alpha>`, `<translate>`...). Es una API antigua pero muy sencilla y fiable — no depende de temas ni de `ActivityOptions`, funciona siempre.

---

## 2. Transiciones de contenido (`Window.setEnterTransition` / tema)

Esta es la técnica moderna (API 21+, paquete `android.transition`) que anima **el contenido** de la Activity que entra/sale (fundido, cambio de posición/tamaño...), no solo un efecto de ventana genérico.

### 2.1. Declararla en el tema (afecta a TODA la app)

```xml
<!-- res/values/themes.xml -->
<style name="Base.Theme.MiApp" parent="Theme.Material3.DayNight.NoActionBar">
    <item name="android:windowActivityTransitions">true</item>
    <item name="android:windowContentTransitions">true</item>
    <item name="android:windowEnterTransition">@transition/auto</item>
    <item name="android:windowExitTransition">@transition/auto</item>
</style>
```
Con esto, **todas** las Activities de la app heredan esa transición de entrada/salida automáticamente, sin escribir nada en Java.

### 2.2. Declararla por código (afecta solo a ESA Activity)

```java
Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
getWindow().setEnterTransition(slide);
```
Debe ir **antes** de `setContentView()` en `onCreate()` para que surta efecto de forma fiable.

### 2.3. El fichero XML de la transición

```xml
<!-- res/transition/slide.xml -->
<transitionSet xmlns:android="http://schemas.android.com/apk/res/android">
    <slide android:duration="1000" android:slideEdge="bottom"/>
</transitionSet>
```
```xml
<!-- res/transition/auto.xml -->
<transitionSet xmlns:android="http://schemas.android.com/apk/res/android">
    <autoTransition android:duration="1000"/>
</transitionSet>
```
- `<slide slideEdge="bottom">` — el contenido entra deslizándose desde abajo.
- `<autoTransition>` — combina automáticamente un fundido de salida + cambio de tamaño/posición + fundido de entrada (`Fade(OUT)` + `ChangeBounds` + `Fade(IN)`).

> **Nota práctica comprobada**: el `android:duration` declarado en estos XML **no siempre se respeta al 100%** en todas las versiones de Android — en pruebas reales, una transición declarada a `1000ms` a veces se resuelve visualmente en 300-400ms. No es un fallo de tu código; si necesitas que una animación se note claramente, prueba con duraciones más largas (`2000`+) y comprueba el resultado real en el dispositivo/emulador, no solo el valor del XML.

### 2.4. Para que se dispare: `ActivityOptions.makeSceneTransitionAnimation`

Esta es la parte que **más falla** cuando algo "no hace" la transición. Configurar `getWindow().setEnterTransition(...)` (o el tema) **no es suficiente**: además hay que lanzar la Activity con las opciones adecuadas:

```java
ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
startActivity(intent, options.toBundle());
```
Si simplemente haces `startActivity(intent)` **sin** ese `Bundle` de opciones, Android usa la animación de ventana por defecto del sistema y tu transición de contenido personalizada nunca se activa, aunque esté perfectamente declarada en el tema o por código. **Esta fue exactamente la causa de un bug real** en `EjercicioAdaptadoresFinal`: `jugadores_Activity` configuraba correctamente una transición `slide` para `Detalles_Jugador`, pero la lanzaba con `startActivity(intent)` a secas — la solución fue añadir el `ActivityOptionsCompat.makeSceneTransitionAnimation(...)` que faltaba (ver [EjercicioAdaptadoresFinal](../05-proyectos-alumno/EjercicioAdaptadoresFinal.md) para el caso completo).

---

## 3. Transición de elemento compartido ("shared element" / hero animation)

Es un paso más: en vez de animar el contenido de forma genérica, animas **una vista concreta** para que parezca que "viaja" físicamente de una pantalla a otra (típicamente una imagen que se agranda al abrir el detalle).

### 3.1. Marcar la vista de origen con un nombre de transición

En la fila de la lista/grid (dentro del `Adapter`, o en el `onItemClick`):
```java
View imagenPulsada = view.findViewById(R.id.imagenGridView);
ActivityOptionsCompat opciones = ActivityOptionsCompat.makeSceneTransitionAnimation(
        GridViewDam2Activity.this,
        new Pair<>(imagenPulsada, DetalleActivity.VIEW_NAME_HEADER_IMAGE));
ActivityCompat.startActivity(GridViewDam2Activity.this, intent, opciones.toBundle());
```
*(`AdapterDam2/GridViewDam2Activity.java`)*

Aquí `makeSceneTransitionAnimation` recibe un **`Pair<View, String>`**: la vista concreta que se va a animar (`imagenPulsada`) y una **etiqueta de texto** (`VIEW_NAME_HEADER_IMAGE`) que identifica esa vista de forma única entre las dos pantallas.

### 3.2. La constante `VIEW_NAME_HEADER_IMAGE` — por qué es `public static final`

```java
public class DetalleActivity extends AppCompatActivity {
    public static final String VIEW_NAME_HEADER_IMAGE = "imagenCabecera";
    ...
}
```
Es solo un `String` fijo (`"imagenCabecera"`) que sirve de **clave compartida** entre la Activity de origen y la de destino, para que el sistema sepa "la vista que marqué con esta etiqueta en la pantalla A es la misma vista que debo animar hacia/desde la vista marcada con esa etiqueta en la pantalla B". Se declara:
- **`public`** — para que otra clase (`GridViewDam2Activity`) pueda leerla desde fuera al construir el `Pair`.
- **`static`** — porque es un valor fijo de la clase, no depende de ninguna instancia (no hace falta crear un `DetalleActivity` para conocer el nombre de la etiqueta que usa).
- **`final`** — porque nunca cambia; es una constante.

Poner el texto literal `"imagenCabecera"` repetido a mano en ambos lados también funcionaría, pero sería frágil (un error de tipeo en un lado rompe la animación sin avisar); centralizarlo en una constante evita ese error.

### 3.3. En la pantalla de destino: asignar el mismo nombre a la vista correspondiente

```java
ImageView ivImagen = findViewById(R.id.ivDetalles);
ViewCompat.setTransitionName(ivImagen, DetalleActivity.VIEW_NAME_HEADER_IMAGE);
Glide.with(getApplicationContext()).load(idImagen).into(ivImagen);
```
*(`AdapterDam2/DetalleActivity.java`)*

`ViewCompat.setTransitionName(vista, etiqueta)` es el equivalente en Java de poner `android:transitionName="imagenCabecera"` directamente en el XML — ambas formas son válidas; aquí se hace por código porque la etiqueta es la misma constante usada en el paso 3.1.

Con ambos lados usando la misma etiqueta, el sistema anima automáticamente la transformación de tamaño/posición de una imagen a la otra, dando la sensación de que es "la misma foto" que crece hasta ocupar la pantalla de detalle.

---

## 4. Resumen: ¿qué técnica usar?

| Necesitas... | Usa |
|---|---|
| Un efecto simple y fiable (fundido, deslizar) sin complicarte | `overridePendingTransition` (§1) |
| Que el contenido entero de la nueva pantalla aparezca con fundido/slide | `Window.setEnterTransition` + tema (§2) |
| Que una imagen/vista concreta "viaje" físicamente de una pantalla a otra | Shared element transition con `Pair` + `transitionName` (§3) |

## 5. Errores típicos (aprendidos depurando estos proyectos)

1. **Configurar la transición dos veces** (en el tema Y por código en la misma Activity) puede generar conflictos: el sistema intenta coordinar dos transiciones a la vez y la ventana puede quedarse "colgada" esperando a que termine una animación que nunca coincide del todo. **Elige un solo sitio** para declarar la transición de una Activity concreta.
2. **Olvidar `ActivityOptionsCompat.makeSceneTransitionAnimation(...)` al lanzar la Activity** — sin esto, ninguna transición de contenido ni de elemento compartido se dispara, por muy bien configurada que esté en el destino.
3. **Llamar a `getWindow().setEnterTransition(...)` después de `setContentView()`** — colócalo siempre antes, junto al resto de configuración inicial de la ventana.
4. **Duraciones XML que no se respetan exactamente** — no asumas que `android:duration="1000"` se traduce siempre en 1000ms reales visibles; compruébalo en pantalla.

## Ver también
- [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) — el `Intent`/`Bundle` de datos, que es un concepto totalmente distinto (aunque a veces se confunde) del `Bundle` de opciones de animación (`options.toBundle()`).
- [07-adaptadores](07-adaptadores.md) — de dónde sale normalmente la vista que se pulsa (`onItemClick`) para lanzar una transición de elemento compartido.
- [EjercicioAdaptadoresFinal](../05-proyectos-alumno/EjercicioAdaptadoresFinal.md) — el caso real, paso a paso, de un bug de transición depurado en este proyecto.
