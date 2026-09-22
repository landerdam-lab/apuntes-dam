---
tags:
  - android
  - concepto
---

# La clase `Activity`, a fondo

Este documento amplía lo visto en [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) §1: aquí se explica **qué es realmente** una Activity, cómo la gestiona el sistema operativo, el ciclo de vida completo (no solo `onCreate`), qué es una "tarea" (task) y la pila de retroceso, y qué es exactamente un `Context`.

## 1. ¿Qué es, técnicamente, una `Activity`?

Una `Activity` es una clase que representa **una pantalla con la que el usuario puede interactuar**. No es "una ventana" en el sentido de escritorio (como en Windows), es más bien un **componente gestionado por el sistema operativo Android**: tú no decides cuándo se crea ni cuándo se destruye tu `Activity` — lo decide el sistema, en respuesta a eventos (el usuario la abre, la pone en segundo plano, el sistema necesita memoria y la mata, el usuario gira la pantalla, etc.).

Esto es muy distinto a un programa de escritorio normal, donde tú controlas el flujo con un `main()`. En Android, **el sistema operativo llama a tus métodos** (`onCreate`, `onStart`, `onResume`...) cuando él lo decide — es lo que se llama **"inversión de control"**: en vez de que tu código llame a las funciones de Android, es Android quien llama a las tuyas, en el momento y orden que él determina.

### ¿Y dónde está la interfaz gráfica?

Cada `Activity` está asociada, internamente, a una `Window` (ventana del sistema), y esa `Window` contiene una jerarquía de `View`/`ViewGroup` (lo que infla `setContentView()`, ver §5). La `Activity` en sí **no dibuja nada directamente** — solo coordina el ciclo de vida y delega el dibujo a esa jerarquía de vistas.

## 2. El ciclo de vida completo

En [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) se vio un resumen. Aquí el detalle completo, con **todos** los métodos que se pueden sobreescribir (aunque en tus proyectos solo se use `onCreate`):

```
   [Activity NO existe]
          │
          │ el usuario/sistema lanza la Activity
          ▼
    onCreate()      ← se llama UNA vez. Aquí: setContentView(), findViewById(), configurar todo.
          │
          ▼
    onStart()       ← la Activity se hace VISIBLE (pero el usuario aún no puede tocarla)
          │
          ▼
    onResume()      ← la Activity pasa a PRIMER PLANO, el usuario ya puede interactuar
          │
   ══════════════   (el usuario usa la app con normalidad)
          │
          │ ocurre algo que tapa parcialmente la Activity
          │ (p. ej. se abre otra Activity ENCIMA, o llega una notificación con diálogo)
          ▼
    onPause()       ← la Activity deja de estar en primer plano, pero SIGUE VISIBLE parcialmente
          │
          │ si la Activity deja de verse del todo (otra Activity la tapa completamente)
          ▼
    onStop()        ← la Activity ya NO es visible en absoluto
          │
          ├──── el usuario vuelve a esta Activity ────► onRestart() ──► onStart() ──► onResume()  (vuelve al bucle activo)
          │
          └──── el sistema decide destruirla (memoria baja, o el usuario la cierra con "atrás")
                 ▼
            onDestroy()   ← destrucción definitiva. Aquí se liberan recursos si hiciera falta.
```

### Cuándo se llama a cada uno, con ejemplos de tus proyectos

- **`onCreate`** → se llama al abrir cualquier Activity por primera vez. Ejemplo: al tocar un equipo en `MainActivity` (proyecto `EjercicioAdaptadoresFinal`), se lanza `jugadores_Activity`, y **inmediatamente** el sistema llama a su `onCreate()`.
- **`onPause`** → se llama, por ejemplo, en `jugadores_Activity` en el instante en que se toca un jugador y empieza a abrirse `Detalles_Jugador` encima (aunque `jugadores_Activity` técnicamente sigue existiendo "debajo", detenida).
- **`onStop`** → se llama cuando `Detalles_Jugador` termina de cubrir del todo la pantalla (`jugadores_Activity` deja de ser visible).
- **`onDestroy`** → se llama, por ejemplo, cuando pulsas el botón "atrás" del sistema y la Activity se cierra definitivamente, o cuando el sistema mata el proceso en segundo plano por falta de memoria.

### ¿Por qué importa saber esto si en tus proyectos solo se usa `onCreate`?

Porque explica comportamientos que si no, parecen "raros":
- Si giras el móvil (cambio de orientación), por defecto **la Activity se destruye y se vuelve a crear entera** (`onDestroy` → `onCreate`) — es una de las razones por las que existe `savedInstanceState` en `onCreate(Bundle savedInstanceState)` (ver [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) §2.1): sin guardar el estado ahí (o en `onSaveInstanceState`), cualquier dato que no venga de tu modelo de datos original se perdería al girar la pantalla.
- Si sales de la app (botón "inicio") y Android necesita memoria, puede matar el proceso **sin llamar siquiera a `onDestroy`** — por eso nunca debes asumir que `onDestroy` se ejecutará siempre; no es fiable para "guardar cosas importantes en el último momento" (para eso hay otros mecanismos, como `onPause` o `onStop`, que sí están garantizados si la Activity llegó a estar visible).
- El `AsyncTask` explicado en [05-asynctask-e-hilos](05-asynctask-e-hilos.md) guarda una referencia a la `Activity` completa (`this.activity = activity`). Si la Activity se destruye (por ejemplo girando la pantalla) **mientras la tarea sigue corriendo en segundo plano**, esa tarea sigue teniendo una referencia a una Activity "muerta" — cuando finalmente intente actualizar sus vistas (`activity.getBarra().setProgress(...)`), estará actualizando vistas de una pantalla que ya no existe. Esto es justo el tipo de fuga de memoria/comportamiento inesperado por el que `AsyncTask` está deprecado.

## 3. Tareas (`Task`) y la pila de retroceso (`Back Stack`)

Cuando lanzas Activities unas desde otras con `startActivity(intent)` (como en toda tu navegación: `MainActivity` → `jugadores_Activity` → `Detalles_Jugador`), Android las va apilando en una estructura llamada **Task** (tarea), que funciona como una **pila** (stack, tipo "LIFO" — el último en entrar es el primero en salir):

```
Pila de la Task:
┌─────────────────────┐
│ Detalles_Jugador     │  ← arriba del todo: la que se ve ahora mismo
├─────────────────────┤
│ jugadores_Activity   │
├─────────────────────┤
│ MainActivity         │  ← la primera que se abrió
└─────────────────────┘
```

Cuando el usuario pulsa el botón **"atrás"** del sistema, Android **saca (`pop`) la Activity de arriba de la pila** y la destruye, mostrando la que queda debajo — por eso, sin escribir ni una línea de código de navegación "hacia atrás", pulsar atrás en `Detalles_Jugador` te devuelve automáticamente a `jugadores_Activity` (y de ahí, otra vez atrás, a `MainActivity`). Este comportamiento es completamente automático y es la razón por la que en ninguno de tus proyectos hay que programar manualmente "volver a la pantalla anterior".

`finish()` (usado, por ejemplo, en `ProgresoCaballo.onPostExecute` — `activity.finish()`) hace exactamente esto mismo mano: saca esa Activity concreta de la pila y la destruye, aunque el usuario no haya pulsado "atrás".

## 4. `Context` — qué es y por qué hay varias formas de conseguirlo

`Context` es una clase abstracta que representa **"el entorno en el que se está ejecutando este código"**: da acceso a recursos (`getResources()`), permite lanzar Activities (`startActivity()`), inflar layouts (`getLayoutInflater()`), etc. `Activity` **es** un `Context` (hereda de él, indirectamente, a través de `AppCompatActivity` → ... → `Context`), por eso puedes pasar `this` o `MainActivity.this` como `Context` en muchos sitios de tus proyectos.

Verás tres formas distintas de obtener un `Context` en tu código, y no son intercambiables sin más:

- **`this`** (dentro de la propia Activity, en un método normal) o **`NombreActivity.this`** (dentro de una clase anónima/lambda, donde `this` se referiría a esa clase anónima, no a la Activity) → el `Context` de **esta Activity concreta**, ligado a su ciclo de vida y a su interfaz visual.
  ```java
  final Dialog dialogo = new Dialog(MainActivity.this);   // necesita el contexto DE LA ACTIVITY, no de la app
  ```
  Un `Dialog`, por ejemplo, **necesita** el contexto de una Activity (no sirve el de la aplicación), porque un diálogo va ligado visualmente a una ventana concreta.

- **`getApplicationContext()`** → el `Context` de **toda la aplicación**, que vive mientras la app esté en memoria, independientemente de qué Activity esté abierta o si hay alguna abierta. Se usa típicamente para cosas que no dependen de una pantalla concreta:
  ```java
  Intent intent = new Intent(getApplicationContext(), Detalles_Jugador.class);
  ```
  Usar el contexto de aplicación para lanzar Intents es una práctica segura y habitual precisamente porque no depende del ciclo de vida de ninguna Activity en particular.

- **El `Context` recibido como parámetro** en un adaptador (`CompaniasAdapter(Context context, ...)`) → se guarda para poder usarlo más tarde dentro del adaptador (por ejemplo, para `LayoutInflater.from(this.context)`), normalmente pasando `this` (la Activity) o `getApplicationContext()` desde donde se crea el adaptador.

### Por qué importa la diferencia (fuga de memoria)

Si guardas una referencia **larga** (por ejemplo, en un campo `static`, o en un objeto que vive mucho más que la pantalla) al `Context` de una `Activity`, y esa Activity se destruye, pero algo sigue "agarrando" esa referencia, el recolector de basura de Java **no puede liberar la memoria de esa Activity entera** (con toda su jerarquía de vistas) — eso es una fuga de memoria (*memory leak*). Por eso, para cosas que deban sobrevivir más que una pantalla concreta, se usa `getApplicationContext()` en vez del contexto de la Activity.

## 5. `setContentView()` y la jerarquía de vistas

```java
setContentView(R.layout.activity_main);
```
Esto hace, internamente:
1. Toma el XML de `res/layout/activity_main.xml`.
2. Lo "infla" — lo convierte en un árbol de objetos `View`/`ViewGroup` reales en memoria (cada etiqueta XML como `<TextView>` o `<ConstraintLayout>` se convierte en una instancia de la clase Java correspondiente).
3. Coloca ese árbol como el contenido visual de la `Window` de esta Activity.

A partir de ahí, `findViewById(R.id.xxx)` puede recorrer ese árbol y devolverte cualquier nodo por su id.

## 6. Modos de lanzamiento (`launchMode`) — mención breve

Por defecto (`standard`, el que usan todos tus proyectos, al no declarar `android:launchMode` en el manifiesto), **cada `startActivity()` crea una instancia NUEVA** de esa Activity y la apila encima, aunque ya hubiera una instancia igual más abajo en la pila. Existen otros modos (`singleTop`, `singleTask`, `singleInstance`) que cambian este comportamiento (por ejemplo, reutilizar una instancia existente en vez de crear otra), pero no se usan en ninguno de tus proyectos actuales — se mencionan aquí solo para que sepas que existen si alguna vez ves `android:launchMode="singleTop"` en un manifiesto.

## Ver también
- [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) — el resumen práctico y el bloque de código que se repite en cada `onCreate`.
- [11-intent-en-profundidad](11-intent-en-profundidad.md) — el mecanismo que hace posible pasar de una Activity a otra.
- [12-hilos-en-profundidad](12-hilos-en-profundidad.md) — por qué la Activity tiene "su propio hilo" y qué pasa si lo bloqueas.
