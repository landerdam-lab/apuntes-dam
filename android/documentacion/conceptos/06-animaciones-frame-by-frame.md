---
tags:
  - android
  - concepto
---

# Animaciones frame-by-frame (fotograma a fotograma)

## 1. La idea

Una animación "frame by frame" (fotograma a fotograma) es la técnica más antigua y sencilla de animación: en vez de calcular movimiento matemáticamente (como hace un `ObjectAnimator` moviendo `translationX`, por ejemplo), simplemente **enseñas una secuencia de imágenes distintas muy rápido**, una detrás de otra, como un dibujo animado clásico o un GIF. El "caballo andando" o el "Pikachu" de tus proyectos son animaciones frame-by-frame: son en realidad 2-3 imágenes fijas (`caballo1`, `caballo2`, `caballo3`, por ejemplo) que se muestran en bucle.

## 2. `TypedArray` y `obtainTypedArray`: el "álbum" de fotogramas

Primero se declara en XML un array de referencias a drawables, en `res/values/arrays.xml` (o `frames.xml` en tus proyectos):

```xml
<array name="imagenes">
    <item>@drawable/caballo1</item>
    <item>@drawable/caballo2</item>
    <item>@drawable/caballo3</item>
</array>
```

Y en Java se recupera como un `TypedArray`:

```java
private TypedArray imagenes = null;
...
imagenes = getResources().obtainTypedArray(R.array.imagenes);
```

Un `TypedArray` es una colección de recursos "sin resolver todavía" — no son objetos `Bitmap` cargados en memoria, son **referencias** (ids de recurso) a los drawables. Para obtener el id real de un elemento concreto:

```java
int idDrawable = imagenes.getResourceId(posicion, -1);
//                                        ^ índice     ^ valor por defecto si no existe
```

> **Importante — `recycle()`**: un `TypedArray` obtenido con `obtainTypedArray` **debería liberarse** llamando a `imagenes.recycle()` cuando ya no se necesita, para devolver la memoria interna que reserva Android. En `AdapterDam2/DetalleActivity.java` sí se hace correctamente (`paisajes.recycle()`); en los ejemplos de animación de `EjercicioDiseno`/`Diseobesos` no se llama nunca — no rompe la app en estos ejercicios pequeños, pero es una fuga de recursos menor a tener en cuenta si el patrón se reutiliza en un proyecto más grande.

## 3. El bucle que crea la ilusión de movimiento

Este es el núcleo de la animación (ver también [05-asynctask-e-hilos](05-asynctask-e-hilos.md) para el detalle de `AsyncTask`):

```java
int foto = 0;
for (int i = 0; i < 100; i++) {
    foto++;
    if (foto >= 3) {       // solo hay 3 fotogramas: 0, 1, 2
        foto = 0;           // al llegar a 3, se vuelve a empezar
    }
    Thread.sleep(200);       // espera 200 milisegundos
    publishProgress(foto, i); // "foto" le dice a onProgressUpdate qué imagen toca mostrar
}
```

La variable `foto` va recorriendo `1, 2, 0, 1, 2, 0, 1, 2...` (nunca llega a mostrar el 0 en el primer ciclo porque empieza incrementando antes de comprobar, un pequeño detalle de la lógica) y cada vez que cambia, en el hilo principal se ejecuta lo siguiente (explicado con más detalle en [05-asynctask-e-hilos](05-asynctask-e-hilos.md)):

```java
activity.getImageCentral().setImageResource(
    activity.getImagenes().getResourceId(values[0], -1)
);
```

Es decir: **cada 200ms, se cambia la imagen del `ImageView` por la siguiente del ciclo**. A ojo humano, un cambio de imagen cada 200ms (5 imágenes por segundo, alternando solo 3 dibujos) ya da sensación de movimiento repetitivo, como un GIF corto en bucle.

La duración total de la animación es `100 pasos × 200ms = 20000ms = 20 segundos`, tras los cuales `onPostExecute` cierra la Activity (`activity.finish()`), volviendo automáticamente a la pantalla anterior.

## 4. La técnica "oficial" de Android: `AnimationDrawable` (módulo "Frame By Frame" del curso)

Es importante distinguir dos cosas que a primera vista parecen la misma animación pero son **técnicas de dos módulos distintos del curso**:

- Lo visto arriba (§1-3: `TypedArray` + `AsyncTask` cambiando `setImageResource` a mano) es en realidad la técnica del módulo **"AsyncTask"** — se reutiliza para dar sensación de animación, pero el objetivo real de ese ejercicio era aprender hilos, no animación en sí.
- El módulo **"Frame By Frame"** del curso enseña la forma **nativa** de Android de animar fotograma a fotograma, sin ningún hilo propio: `AnimationDrawable`. Ninguno de los proyectos de `AndroidStudioProjects/` llega a implementar este ejercicio concreto — se documenta aquí igualmente, por completitud, tal como lo plantea el material del curso.

### 4.1. El `animation-list`

```xml
<!-- res/drawable/framebyframe.xml -->
<animation-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@drawable/andando1" android:duration="50" />
    <item android:drawable="@drawable/andando2" android:duration="50" />
    <item android:drawable="@drawable/andando3" android:duration="50" />
    <item android:drawable="@drawable/andando4" android:duration="50" />
</animation-list>
```
Cada `<item>` es un fotograma con su propia duración en milisegundos (aquí, 50ms cada uno → 20 fotogramas por segundo). A diferencia del ejemplo simplificado más abajo, **no hace falta** `android:oneshot="false"` si se quiere controlar manualmente cuándo empieza/para (ver §4.2) — `oneshot` solo importa si quieres que se reproduzca una sola vez (`true`) en vez de repetirse sola en bucle (`false`, el valor por defecto).

### 4.2. El layout: un `ImageView` vacío + tres botones (Play/Stop/Back)

```xml
<LinearLayout android:orientation="vertical" ...>
    <ImageView android:id="@+id/imagenFrame" android:layout_width="match_parent"
        android:layout_height="0dp" android:layout_weight="1" />
    <!-- (sin android:src: el ImageView empieza vacío, la imagen se asigna por código) -->

    <LinearLayout android:orientation="horizontal" ...>
        <Button android:id="@+id/btnPlay" android:text="Play" .../>
        <Button android:id="@+id/btnStop" android:text="Stop" .../>
        <Button android:id="@+id/btnBack" android:text="Back" .../>
    </LinearLayout>
</LinearLayout>
```

### 4.3. El código Java: `AnimationDrawable` como control remoto de la animación

```java
public class EjemploFrameByFrame extends AppCompatActivity {
    private ImageView imagen;
    private AnimationDrawable frame;

    protected void onCreate(Bundle savedInstanceState) {
        ...
        imagen = findViewById(R.id.imagenFrame);

        // 1. Se asigna el animation-list como FONDO del ImageView...
        imagen.setBackgroundResource(R.drawable.framebyframe);
        // 2. ...y se recupera ese mismo fondo ya "tipado" como AnimationDrawable
        frame = (AnimationDrawable) imagen.getBackground();

        findViewById(R.id.btnPlay).setOnClickListener(v -> {
            if (!frame.isRunning()) frame.start();   // arranca (o reanuda) la animación
        });
        findViewById(R.id.btnStop).setOnClickListener(v -> {
            if (frame.isRunning()) frame.stop();      // la congela en el fotograma actual
        });
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
```

Puntos clave:
- `setBackgroundResource(R.drawable.framebyframe)` — el `animation-list` se asigna como **fondo** del `ImageView` (no como `src`), y eso es justo lo que permite recuperarlo después con `getBackground()` y controlarlo.
- `(AnimationDrawable) imagen.getBackground()` — el casteo funciona porque, al ser un `animation-list`, Android internamente crea un objeto `AnimationDrawable` para representarlo; `getBackground()` devuelve el `Drawable` genérico que hay que "bajar de tipo" (cast) a `AnimationDrawable` para acceder a sus métodos propios (`start()`, `stop()`, `isRunning()`).
- `frame.start()` / `frame.stop()` — a diferencia de la técnica manual con `AsyncTask` (§1-3), aquí **no hay ningún hilo que gestionar tú mismo**: el sistema se encarga de todo el temporizado internamente. Solo controlas cuándo arranca y cuándo se detiene.
- El `ImageView` se deja **vacío** en el XML (sin `android:src`) porque la imagen que se ve en cada instante la controla por completo el `AnimationDrawable` una vez arrancado.

### 4.4. Comparación de las dos técnicas

| | Técnica "AsyncTask" (la que usan tus proyectos) | Técnica "Frame By Frame" (`AnimationDrawable`) |
|---|---|---|
| Necesita un hilo propio | Sí (`AsyncTask.doInBackground`) | No, lo gestiona Android internamente |
| Se puede pausar/reanudar a mitad | No fácilmente (el bucle corre solo hasta el final) | Sí (`start()`/`stop()` en cualquier momento) |
| Controla además progreso/duración total | Sí (barra de progreso, cierre automático) | No — solo reproduce fotogramas en bucle |
| Complejidad | Mayor (hay que escribir la clase `AsyncTask` entera) | Menor (solo un XML + dos llamadas) |

Para una animación **puramente visual y controlable por el usuario** (como este ejemplo con Play/Stop), `AnimationDrawable` es la herramienta correcta. Para una animación que además debe **coordinarse con una tarea de fondo real** (como avanzar una barra de progreso mientras se "simula" una carga), tiene más sentido la técnica basada en `AsyncTask` que usan tus proyectos — de ahí que el curso enseñe ambas por separado, en dos módulos distintos.

## Ver también
- [05-asynctask-e-hilos](05-asynctask-e-hilos.md) — el mecanismo de hilos que hace posible la animación.
- [04-toast-personalizado](04-toast-personalizado.md) — otro ejemplo de "algo temporal que se muestra y desaparece", pero sin animación de fotogramas.
