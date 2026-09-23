---
tags:
  - android
  - concepto
---

# Hilos en Android, a fondo (`Thread`, `Looper`, `Handler`, `MessageQueue`, ANR)

En [05-asynctask-e-hilos](05-asynctask-e-hilos.md) se explicó `AsyncTask` de forma práctica. Aquí se explica **el mecanismo interno** en el que se apoya (y en el que se apoya también `Handler.postDelayed`, usado en [04-toast-personalizado](04-toast-personalizado.md)): qué es un proceso, qué es un hilo, cómo Android organiza el hilo principal internamente, y qué pasa exactamente si lo bloqueas.

## 1. Proceso vs hilo

- **Proceso**: cuando abres tu app, Android le asigna un **proceso** del sistema operativo (una instancia en ejecución, con su propia porción de memoria, aislada de otras apps). Cada app corre normalmente en su propio proceso (viste esto en las capturas de `logcat` durante la depuración: `Start proc 19285:com.example.ejercicioadaptadoresfinal/u0a232`).
- **Hilo (`Thread`)**: dentro de un mismo proceso, puede haber **varios hilos ejecutándose "a la vez"** (o, en una CPU de un solo núcleo, turnándose muy rápido). Todos los hilos de un mismo proceso **comparten la misma memoria** — por eso dos hilos pueden acceder a los mismos objetos Java, lo cual es potente pero también peligroso (si dos hilos modifican el mismo dato a la vez sin cuidado, se producen errores de sincronización).

Cuando tu app arranca, Android crea automáticamente **un primer hilo**, llamado el **hilo principal** (*main thread*) o **hilo de UI** (*UI thread*).

## 2. Por qué existe un único "hilo de UI" y qué reglas impone

Android decidió, por diseño, que **todas las vistas (`View`) solo pueden ser leídas/modificadas desde un único hilo**: el hilo principal. Esto no es una limitación arbitraria — es una decisión de diseño para evitar tener que sincronizar (con bloqueos, semáforos, etc.) cada acceso a cada vista desde cualquier hilo, lo cual sería muchísimo más lento y propenso a errores. La regla es simple y estricta:

> **Solo el hilo principal puede tocar las vistas. Cualquier otro hilo que lo intente, lanza una excepción (`CalledFromWrongThreadException`).**

Y la regla complementaria:

> **El hilo principal nunca debe bloquearse con trabajo largo**, porque es el mismo hilo responsable de dibujar cada fotograma de la pantalla (idealmente cada ~16ms, para mantener 60 imágenes por segundo) y de responder a los toques del usuario.

## 3. `Looper` y `MessageQueue`: cómo funciona realmente el hilo principal por dentro

El hilo principal de Android no es "un hilo cualquiera que ejecuta tu `onCreate` y ya está" — internamente ejecuta un **bucle infinito** gestionado por un objeto `Looper`, que va sacando "mensajes" de una cola (`MessageQueue`) y ejecutándolos uno detrás de otro, para siempre (mientras la app esté viva):

```
   MessageQueue (cola de tareas pendientes)
   ┌────┬────┬────┬────┬────┐
   │ M1 │ M2 │ M3 │ M4 │ ...│
   └────┴────┴────┴────┴────┘
        ▲
        │  Looper.loop() saca mensajes uno a uno y los ejecuta, SIEMPRE en este mismo hilo
        │
   [Hilo principal]
```

Cada vez que:
- se pulsa la pantalla,
- se necesita redibujar algo,
- se llama a `runOnUiThread(...)` (🧪 no visto en clase; en clase se usa `AppExecutors.getInstance().getMainThread()` o `onProgressUpdate` de `AsyncTask`),
- o un `Handler.post(...)`/`postDelayed(...)` programa algo,

...se añade un "mensaje" (o una tarea, `Runnable`) a esa cola, y el `Looper` del hilo principal lo va procesando en orden, uno cada vez, sin paralelismo dentro de ese mismo hilo. Esto explica por qué **si un mensaje tarda mucho en procesarse, todos los que vienen detrás (incluyendo redibujar la pantalla y responder a toques) se quedan esperando** — de ahí que "bloquear el hilo principal" signifique literalmente "meter una tarea larga en esa cola, que bloquea que se procesen las siguientes".

## 4. `Handler`: el objeto que te permite mandar mensajes a un hilo concreto

```java
new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
    @Override
    public void run() {
        dialogo.dismiss();
    }
}, 2000);
```
*(`Diseobesos/MainActivity.java`)*

Un `Handler` se asocia a un `Looper` concreto (aquí, `Looper.getMainLooper()`, el bucle del hilo principal) y sirve para **encolar** una tarea (`Runnable`) en la `MessageQueue` de ese hilo, opcionalmente con un retraso (`postDelayed`, en milisegundos). Cuando pasan esos 2000ms, el `Looper` del hilo principal saca ese `Runnable` de la cola y ejecuta su `run()` — **en el hilo principal**, así que es totalmente seguro tocar vistas o diálogos ahí dentro (`dialogo.dismiss()`).

Es importante notar que `postDelayed(runnable, 2000)` **no bloquea nada mientras espera** — no es un `Thread.sleep(2000)` metido en el hilo principal (eso sí congelaría la app). Es más bien "apunta esta tarea para dentro de 2 segundos y sigue haciendo tu vida mientras tanto" — el hilo principal sigue libre para responder a toques y redibujar durante esos 2 segundos; solo cuando llega el momento, se añade esa tarea a la cola para ejecutarse.

## 5. Cómo encaja `AsyncTask` en todo esto

Recordando [05-asynctask-e-hilos](05-asynctask-e-hilos.md):

```
onPreExecute()        → se ejecuta en el hilo principal (vía Handler, como el ejemplo de arriba)
doInBackground()       → Android crea/reutiliza un HILO SECUNDARIO NUEVO, fuera del Looper del hilo principal,
                          y ejecuta aquí tu código — por eso SÍ puedes hacer Thread.sleep() sin congelar la app
publishProgress()      → internamente, usa un Handler para encolar una llamada a onProgressUpdate() EN EL HILO PRINCIPAL
onProgressUpdate()     → se ejecuta en el hilo principal → aquí SÍ es seguro tocar vistas
onPostExecute()        → también se ejecuta en el hilo principal, cuando doInBackground() termina
```

Es decir: `AsyncTask` es, por dentro, una combinación de **un hilo secundario real** (para `doInBackground`) + **un `Handler` apuntando al `Looper` del hilo principal** (para todo lo demás) — exactamente los dos mecanismos explicados arriba, empaquetados en una sola clase para que no tengas que gestionarlos tú a mano.

## 6. ANR (*Application Not Responding*)

Si el hilo principal está bloqueado (por ejemplo, procesando un mensaje/tarea que tarda demasiado — un bucle largo, una operación de red hecha por error ahí, un `Thread.sleep()` puesto directamente en el hilo principal) durante varios segundos seguidos (típicamente ~5 segundos para eventos de entrada), el sistema Android detecta que la app "no responde" y muestra al usuario un diálogo de sistema ofreciendo **cerrar la app a la fuerza** — esto es un **ANR**. Es distinto de un *crash* (una excepción no capturada): en un ANR la app no ha fallado, simplemente está tan ocupada que ya no puede atender al usuario, y el sistema decide intervenir.

Esta es la razón de fondo, a nivel de sistema operativo, por la que **todo** el código de `doInBackground` en tus proyectos (los bucles con `Thread.sleep(200)` en `ProgresoCaballo`, `AnimacionPikachu`, `ProgressAndando`) se ejecuta deliberadamente **fuera** del hilo principal: si esos bucles (100 pasos × 200ms = 20 segundos) se ejecutaran directamente dentro de `onCreate()` (en el hilo principal), la app provocaría un ANR casi con toda seguridad.

## 7. Resumen mental

```
Hilo principal (UI thread)
 ├─ Es el ÚNICO que puede tocar vistas.
 ├─ Ejecuta un bucle infinito (Looper) que procesa una cola de tareas (MessageQueue), una detrás de otra.
 ├─ NUNCA debe bloquearse con trabajo largo (riesgo de ANR).
 └─ Se le pueden mandar tareas desde código con Handler (post/postDelayed) o automáticamente
    desde AsyncTask (onPreExecute/onProgressUpdate/onPostExecute).

Hilo(s) secundario(s)
 ├─ Se crean explícitamente para trabajo largo (AsyncTask.doInBackground, o un Thread manual).
 ├─ Aquí SÍ se puede usar Thread.sleep(), bucles largos, operaciones de red/disco, etc.
 └─ NUNCA deben tocar vistas directamente — deben "avisar" al hilo principal (vía Handler o
    los callbacks de AsyncTask) para que sea él quien actualice la interfaz.
```

## Ver también
- [05-asynctask-e-hilos](05-asynctask-e-hilos.md) — el uso práctico de `AsyncTask` en tus proyectos.
- [04-toast-personalizado](04-toast-personalizado.md) — el uso práctico de `Handler.postDelayed`.
- [10-activity-en-profundidad](10-activity-en-profundidad.md) — por qué guardar una referencia larga a la Activity desde un hilo de fondo es arriesgado.

## 🏋️ Practica esto

- [Nivel 4 — ejercicio 4.2](../07-ejercicios/04-nivel-4-toast-asynctask-animaciones-transiciones.md) (`AsyncTask`) y [Nivel 6 — ejercicio 6.3](../07-ejercicios/06-nivel-6-dialogos-y-room.md) (`AppExecutors`)
