---
tags:
  - android
  - concepto
---

# `AsyncTask` e hilos en Android

## 1. Por qué hace falta esto: el hilo de interfaz (UI thread)

Toda app Android tiene **un hilo principal** ("UI thread" o "main thread") que es el único que puede tocar las vistas (`TextView.setText`, `ImageView.setImageResource`, etc.). Ese mismo hilo es el que dibuja la pantalla y responde a los toques. Si metes ahí una tarea que tarda (una espera, una descarga, un bucle largo), **la pantalla se queda congelada** mientras dure, y si tarda más de ~5 segundos el sistema mata la app con un error `ANR` (Application Not Responding).

`AsyncTask` es una clase de Android (hoy día **deprecada**, ver §5) pensada para resolver justo esto: ejecutar algo largo **en otro hilo**, y avisar al hilo principal cuando haya resultados parciales o el resultado final, para que sea él quien actualice la pantalla de forma segura.

## 2. Anatomía de `AsyncTask<Params, Progress, Result>`

```java
public class ProgresoCaballo extends AsyncTask<Void, Integer, Void> {
```

Los tres tipos genéricos son:
- **`Params`** — el tipo de los argumentos que recibe `execute(...)`. Aquí `Void` porque no se le pasa nada.
- **`Progress`** — el tipo de los valores de progreso intermedios que se van publicando (aquí `Integer`).
- **`Result`** — el tipo del resultado final que devuelve la tarea (aquí `Void`, no devuelve nada útil).

Y los cuatro métodos que se sobreescriben, **en el orden en que se ejecutan**:

```java
@Override
protected void onPreExecute() {
    // 1. Se ejecuta en el HILO PRINCIPAL, justo antes de arrancar el hilo de fondo.
    //    Aquí se prepararía la UI (p. ej. mostrar una barra de carga).
}

@Override
protected Void doInBackground(Void... voids) {
    // 2. Se ejecuta en un HILO SECUNDARIO (nunca toques vistas aquí directamente).
    //    Es el único método obligatorio.
    ...
    return null; // el resultado final (tipo Result)
}

@Override
protected void onProgressUpdate(Integer... values) {
    // 3. Se ejecuta en el HILO PRINCIPAL, cada vez que el hilo de fondo llama a publishProgress(...).
    //    Aquí SÍ es seguro tocar vistas.
}

@Override
protected void onPostExecute(Void unused) {
    // 4. Se ejecuta en el HILO PRINCIPAL, cuando doInBackground() termina y devuelve su resultado.
}
```

## 3. Ejemplo real completo: la "animación" del caballo (`EjercicioDiseno`)

Este patrón se repite **casi idéntico** en tres sitios de tus proyectos: `ProgresoCaballo`, `AnimacionPikachu` (`EjercicioDiseno`) y `ProgressAndando` (`Diseobesos`). Lo analizamos entero:

### 3.1. La Activity lanza la tarea (`Caballo.java`)

```java
public class Caballo extends AppCompatActivity {
    private TypedArray imagenes = null;
    private ProgressBar barra = null;
    private ImageView imageCentral = null;
    private TextView texto = null;

    protected void onCreate(Bundle savedInstanceState) {
        ...
        imagenes = getResources().obtainTypedArray(R.array.imagenes); // el conjunto de fotogramas, ver 06
        barra = findViewById(R.id.progressBar);
        imageCentral = findViewById(R.id.imageView2);
        texto = findViewById(R.id.textView);

        barra.setMax(100);
        barra.setProgress(0);

        ProgresoCaballo hilo = new ProgresoCaballo(this);  // 1. se crea la tarea, pasándole "this" (la Activity)
        hilo.execute();                                     // 2. se lanza: arranca doInBackground() en otro hilo
    }

    // getters/setters públicos para que la tarea pueda acceder a estas vistas desde fuera
    public ImageView getImageCentral() { return imageCentral; }
    public TextView getTexto() { return texto; }
    public ProgressBar getBarra() { return barra; }
    public TypedArray getImagenes() { return imagenes; }
}
```

**¿Por qué le pasa `this` al constructor?** Porque el `AsyncTask` necesita, en sus métodos que corren en el hilo principal (`onProgressUpdate`, `onPostExecute`), acceder a las vistas de la Activity para actualizarlas. Guardando una referencia a la Activity (`this.activity = activity`), la tarea puede llamar a `activity.getBarra()`, `activity.getImageCentral()`, etc.

> **Nota de diseño**: esta forma de comunicación (getters/setters públicos + guardar la Activity entera) es sencilla de entender pero acopla mucho la tarea a esa Activity concreta, y puede provocar fugas de memoria si la tarea sigue viva después de que la Activity se destruya. En apps más grandes se prefieren interfaces "callback" o (hoy en día) corrutinas/`ViewModel`. Para el nivel de estos ejercicios, el patrón usado es perfectamente válido y fácil de seguir.

### 3.2. La tarea en sí (`ProgresoCaballo.java`)

```java
public class ProgresoCaballo extends AsyncTask<Void, Integer, Void> {
    private Caballo activity;

    public ProgresoCaballo(Caballo activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int foto = 0;
        for (int i = 0; i < 100; i++) {          // 100 pasos → de 0% a 100%
            foto++;
            if (foto >= 3) {                      // solo hay 3 fotogramas (índices 0,1,2)
                foto = 0;                          // así que se reinicia el ciclo → efecto "animado"
            }
            try {
                Thread.sleep(200);                 // pausa de 200ms → 100 pasos * 200ms = 20 segundos totales
                publishProgress(foto, i);           // envía (fotograma actual, porcentaje actual) al hilo principal
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // values[0] = número de fotograma (0,1,2) ; values[1] = porcentaje (0-99)
        activity.getImageCentral().setImageResource(
            activity.getImagenes().getResourceId(values[0], -1)   // cambia la imagen mostrada
        );
        activity.getBarra().setProgress(values[1]);                // avanza la barra de progreso
        activity.getTexto().setText(values[1] + "%");               // actualiza el texto "0%".."99%"
    }

    @Override
    protected void onPostExecute(Void unused) {
        activity.finish();   // al terminar el bucle, se cierra la Activity automáticamente
    }
}
```

**El truco de la animación**: `doInBackground` no hace ningún cálculo complicado — solo cuenta de 0 a 99 muy despacio (una vuelta cada 200ms) y, en cada vuelta, hace avanzar un índice de fotograma que da vueltas entre 0, 1 y 2. Al llamar a `publishProgress(foto, i)`, Android encola una llamada a `onProgressUpdate` **en el hilo principal**, que es quien realmente cambia la imagen en pantalla — el bucle en sí nunca toca la UI directamente (si lo hiciera, lanzaría una excepción, porque `doInBackground` corre en un hilo distinto al de la interfaz).

`publishProgress(Integer... values)` acepta un número variable de argumentos del tipo `Progress` declarado en la clase (`Integer` aquí) — por eso se le pueden pasar dos valores (`foto` e `i`) aunque el genérico diga `Integer` en singular: es un *varargs* (`Integer...`).

### 3.3. `.execute()` sin argumentos

```java
hilo.execute();
```
`execute()` acepta los `Params` declarados en el genérico (`Void` aquí, así que se llama sin argumentos). Si la tarea necesitara datos de entrada (por ejemplo una URL para descargar), sería `AsyncTask<String, Integer, Bitmap>` y se llamaría `hilo.execute("https://...")`.

## 4. `Thread.sleep()` dentro de `doInBackground`

```java
Thread.sleep(200);
```
Pausa el hilo actual (el hilo de fondo de la tarea) 200 milisegundos. Está permitido aquí porque **no es el hilo principal** — bloquear un hilo secundario no congela la interfaz. Bloquear el hilo principal con `Thread.sleep()`, en cambio, sí congelaría la app (nunca se debe hacer).

`Thread.sleep()` lanza `InterruptedException` (una excepción comprobada), por eso siempre va envuelta en un `try/catch`.

## 5. La otra variante que enseña el curso: `Runnable` + `Executor` a mano (y por qué es delicada)

El material del curso muestra, para este mismo ejercicio, una **segunda forma** de conseguir hilos en segundo plano, sin usar la clase `AsyncTask` en absoluto — usando las piezas de Java "puro" (`Runnable`, `Thread`, `Executor`):

```java
public class ProgressAndando implements Runnable {
    private EjemploAsyncTask actividad;
    public ProgressAndando(EjemploAsyncTask actividad) { this.actividad = actividad; }

    @Override
    public void run() {
        ProgressBar barra = actividad.getBarra();
        ImageView foto = actividad.getImagenCentral();
        TextView texto = actividad.getTexto();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            // ... calcular numFoto ...
            barra.setProgress(i);                                       // ⚠️ tocando la UI...
            foto.setImageResource(imagenes.getResourceId(numFoto, -1));   // ⚠️ ...desde un hilo NO principal
            texto.setText(i + "%");
        }
    }
}
```
```java
public class ThreadExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();   // crea y arranca un hilo nuevo de verdad
    }
}
```
```java
ProgressAndando hilo = new ProgressAndando(this);
ThreadExecutor executor = new ThreadExecutor();
executor.execute(hilo);   // el run() de "hilo" se ejecuta DENTRO del hilo nuevo que crea el executor
```

Aquí `Executor` es una interfaz estándar de Java (no de Android) con un único método (`execute(Runnable)`) — `ThreadExecutor` es una implementación mínima que simplemente crea un `Thread` de verdad y lo arranca (`new Thread(command).start()`) para ejecutar ese `Runnable`.

### ⚠️ El problema de esta variante

Fíjate en las líneas marcadas dentro de `run()`: **están actualizando vistas (`ProgressBar`, `ImageView`, `TextView`) directamente desde dentro del método `run()`**, que se ejecuta en el hilo secundario creado por `ThreadExecutor` — **no en el hilo principal**. Esto viola directamente la regla explicada en [12-hilos-en-profundidad](12-hilos-en-profundidad.md) §2: *"solo el hilo principal puede tocar las vistas"*. En determinadas versiones/configuraciones de Android esto puede funcionar por pura casualidad (sobre todo en emuladores o compilaciones antiguas menos estrictas), pero **no es código correcto** — el comportamiento garantizado por la documentación oficial de Android es lanzar `CalledFromWrongThreadException` y crashear.

Esta es exactamente la razón por la que los proyectos reales de `AndroidStudioProjects/` (`ProgresoCaballo`, `AnimacionPikachu`, `ProgressAndando` del proyecto `Diseobesos`) **no usan esta variante** — usan la clase `AsyncTask` real (extendiendo `android.os.AsyncTask`, ver §1-4 arriba), donde `onProgressUpdate` garantiza ejecutarse en el hilo principal, precisamente para evitar este problema. Si ves este patrón de `Runnable`+`Executor` manual en algún apunte o ejemplo, es útil para entender *qué es* un hilo a nivel más básico de Java, pero para tocar vistas de Android hay que usar siempre un mecanismo que garantice el salto de vuelta al hilo principal (`AsyncTask`, `Handler`, `runOnUiThread`, etc. — ver [12-hilos-en-profundidad](12-hilos-en-profundidad.md)).

## 6. Variante para leer un `Bundle`: `getIntent().getExtras()`

En [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) §2.2 se vio `getIntent().getStringExtra("clave")` directamente. El material del curso muestra una forma equivalente, obteniendo primero el `Bundle` completo:
```java
Bundle bundleRecibido = (Bundle) getIntent().getExtras();
if (!bundleRecibido.isEmpty()) {
    Toast.makeText(getApplicationContext(), bundleRecibido.getString("saludo"), Toast.LENGTH_SHORT).show();
}
```
`getIntent().getExtras()` devuelve el `Bundle` interno completo del `Intent` (o `null` si no se le pasó ninguno), y a partir de ahí se puede llamar a `.getString("clave")`, `.getInt("clave")`, etc. sobre ese `Bundle` — el mismo resultado que `getIntent().getStringExtra("clave")`, solo que en dos pasos en vez de uno. `.isEmpty()` es una comprobación de seguridad para no intentar leer de un `Bundle` que no contenga ningún extra.

## 7. `AsyncTask` está deprecado — ¿qué se usa hoy?

Desde Android 11 (API 30), Google marcó `AsyncTask` como obsoleto porque tiene varios problemas conocidos (fugas de memoria si la Activity se destruye mientras la tarea sigue viva, comportamiento confuso en paralelo, API incómoda). Como referencia, las alternativas modernas son:
- **Kotlin Coroutines** (`viewModelScope.launch { ... }`) — el estándar actual en apps Kotlin.
- **`Executor` + `Handler`** — la forma "manual" en Java puro, sin librerías extra.
- **`WorkManager`** — para tareas en segundo plano que deben sobrevivir aunque se cierre la app.

Para el propósito de estos ejercicios (aprender el concepto de hilo de fondo + hilo de UI), `AsyncTask` sigue siendo perfectamente válido y mucho más simple de leer que las alternativas — solo conviene saber que en un proyecto profesional actual no se usaría.

## Ver también
- [12-hilos-en-profundidad](12-hilos-en-profundidad.md) — el mecanismo interno completo: `Looper`, `MessageQueue`, `Handler`, y qué es un ANR.
- [06-animaciones-frame-by-frame](06-animaciones-frame-by-frame.md) — el uso concreto que se le da aquí a `AsyncTask` (animar cambiando de imagen).
- [04-toast-personalizado](04-toast-personalizado.md) — la alternativa `Handler.postDelayed` para esperas simples sin necesidad de un hilo de fondo real.

## 🏋️ Practica esto

- [Nivel 4 — ejercicio 4.2](../ejercicios/04-nivel-4-toast-asynctask-animaciones-transiciones.md): barra de progreso con `AsyncTask`
- [Simulacro B](../ejercicios/08-simulacros-de-examen.md): menú de diseño con toast, `AsyncTask` y frames
