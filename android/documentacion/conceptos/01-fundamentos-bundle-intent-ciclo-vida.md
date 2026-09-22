---
tags:
  - android
  - concepto
---

# Fundamentos: `Bundle`, `Intent` y el ciclo de vida de la `Activity`

Este documento cubre lo que aparece en **absolutamente todos** los proyectos: el bloque de código que se repite en cada `onCreate()`, cómo se pasan datos entre pantallas y por qué existen ciertas piezas que a primera vista parecen "magia".

---

## 1. ¿Qué es una `Activity`?

Una `Activity` es **una pantalla** de la aplicación. Cada pantalla de tus apps (la lista de equipos, el detalle de un jugador, el spinner de cursos...) es una clase Java que hereda de `AppCompatActivity`:

```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...
    }
}
```

Android no crea la `Activity` con un constructor normal (`new MainActivity()`) — la crea el **sistema operativo** cuando la lanzas (con un `Intent`, ver más abajo) y va llamando a una serie de métodos llamados **ciclo de vida**.

### El ciclo de vida (resumen práctico)

```
onCreate()   → se ejecuta UNA VEZ al crear la pantalla. Aquí haces setContentView(),
               findViewById(), configuras adaptadores, listeners, etc.
onStart()    → la Activity se está haciendo visible
onResume()   → la Activity está en primer plano y el usuario puede interactuar
   ...       (el usuario usa la app)
onPause()    → la Activity deja de estar en primer plano (p. ej. abres otra Activity encima)
onStop()     → la Activity ya no es visible
onDestroy()  → la Activity se destruye (se cierra o el sistema libera memoria)
```

En **todos** tus proyectos solo se sobreescribe `onCreate()`, que es el método más importante: es donde se "monta" la pantalla.

---

## 2. `Bundle`: una caja de pares clave-valor

Un `Bundle` es, literalmente, **una caja donde metes datos con una etiqueta (clave) para poder sacarlos luego por esa misma etiqueta**. Es como un `HashMap<String, Object>` pero optimizado para que Android lo pueda serializar y pasar entre procesos/pantallas.

### 2.1. `Bundle` en `onCreate(Bundle savedInstanceState)`

```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
```

Este `Bundle` que llega como parámetro **NO** es para pasar datos entre pantallas. Sirve para **restaurar el estado** de la Activity cuando el sistema la ha destruido y la vuelve a crear (por ejemplo, al girar la pantalla, o si Android mata el proceso en segundo plano para liberar memoria y luego el usuario vuelve a la app). Si quisieras guardar el texto de un `EditText` para que sobreviva a un giro de pantalla, lo guardarías en `onSaveInstanceState(Bundle outState)` y lo recuperarías aquí. En tus proyectos actuales no se usa para esto (ninguno sobreescribe `onSaveInstanceState`), pero **siempre hay que pasarlo a `super.onCreate()`** — si no, la Activity no se inicializa correctamente y la app crashea.

### 2.2. `Bundle` para pasar datos entre Activities (el uso real en tus proyectos)

Este es el uso que preguntabas: **meter datos en la caja, viajar con ellos hasta otra pantalla, y sacarlos allí**.

Ejemplo real de `jugadores_Activity.java` (proyecto `EjercicioAdaptadoresFinal`):

```java
Jugador jugador = equipo.getJugadores().get(position);

Bundle bundle = new Bundle();
bundle.putSerializable("jugadorSeleccionado", jugador);   // 1. metemos el dato en la caja

Intent intent = new Intent(getApplicationContext(), Detalles_Jugador.class);
intent.putExtras(bundle);                                  // 2. la caja viaja "pegada" al Intent
startActivity(intent);                                      // 3. lanzamos la siguiente pantalla
```

Y en `Detalles_Jugador.java`, al otro lado:

```java
Jugador jugador = (Jugador) getIntent().getSerializableExtra("jugadorSeleccionado"); // 4. sacamos el dato de la caja
```

Fíjate en el patrón:
1. `bundle.putXxx("clave", valor)` — metes el dato con una clave de texto.
2. `getIntent().getXxxExtra("clave")` — lo sacas usando **la misma clave exacta** (si te equivocas al escribirla, o pones un tipo distinto, te devuelve `null` o crashea).

`Bundle` tiene un método `putXxx`/`getXxx` para cada tipo de dato: `putString`/`getStringExtra`, `putInt`/`getIntExtra`, `putBoolean`/`getBooleanExtra`, `putSerializable`/`getSerializableExtra`, etc. El nombre cambia un poco según si lo llamas sobre el `Bundle` directamente o sobre el `Intent` (que internamente delega en su propio `Bundle` de "extras"), pero es el mismo concepto.

> **Atajo habitual**: muchas veces ni siquiera hace falta crear el `Bundle` explícitamente — puedes usar `intent.putExtra("clave", valor)` directamente sobre el `Intent`, que hace lo mismo por debajo. Por ejemplo, en `AdapterDam2/GridViewDam2Activity.java`:
> ```java
> Intent intent = new Intent(GridViewDam2Activity.this, DetalleActivity.class);
> intent.putExtra("idFoto", position);   // sin crear un Bundle a mano
> ```
> Y se recupera con `getIntent().getIntExtra("idFoto", 0)` (el `0` es el valor por defecto si la clave no existiera).

### 2.3. ¿Por qué `putSerializable` y no `putString`/`putInt`?

Porque `Jugador` es un **objeto complejo** (tiene varios campos: nombre, dorsal, posición, foto), no un dato primitivo. Para poder meter un objeto entero dentro de un `Bundle`, la clase de ese objeto tiene que decirle a Android "yo sé convertirme en una secuencia de bytes y reconstruirme luego" — eso es lo que significa implementar `Serializable`:

```java
public class Jugador implements Serializable {
    private String nombre;
    private int dorsal;
    ...
}
```

Sin `implements Serializable`, la línea `bundle.putSerializable("jugadorSeleccionado", jugador)` ni siquiera compilaría (el método exige un `Serializable` como parámetro).

`Serializable` es una interfaz de Java "de marcado" (no tiene métodos que implementar) — solo le dice a la máquina virtual "convierte automáticamente todos mis campos a bytes y viceversa". Es cómoda pero más lenta que la alternativa moderna de Android (`Parcelable`), que en apps pequeñas como estas no supone ninguna diferencia notable.

---

## 3. `Intent`: la orden de "abre esta pantalla" (o "haz esta acción")

Un `Intent` es un objeto que representa una **intención**: "quiero abrir esta Activity" (Intent explícito) o "quiero que alguna app haga esto" (Intent implícito, p. ej. abrir el navegador). En tus proyectos solo se usan **Intents explícitos**, que indican exactamente qué clase abrir:

```java
Intent intent = new Intent(getApplicationContext(), Detalles_Jugador.class);
//                          ^ contexto desde el que se lanza   ^ Activity destino
startActivity(intent);
```

- El primer parámetro es un `Context` (normalmente `getApplicationContext()`, `MainActivity.this`, o simplemente `this` dentro de la propia Activity).
- El segundo es la clase Java de la Activity que quieres abrir — Android busca esa clase (que debe estar declarada en `AndroidManifest.xml`, ver más abajo) y crea una nueva instancia de ella, llamando a su `onCreate()`.

### 3.1. Pasar datos "colgados" del Intent

Como viste arriba, el `Intent` lleva un `Bundle` interno de "extras" al que puedes añadir cosas directamente:

```java
intent.putExtra("idFoto", position);      // un int
intent.putExtras(bundle);                  // o un Bundle entero ya construido
```

### 3.2. Lanzar la Activity con animación (`ActivityOptions`)

Muchas de tus llamadas a `startActivity` van acompañadas de un segundo parámetro:

```java
ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
startActivity(intent, options.toBundle());
```

Esto **no es parte del sistema de Bundle de datos** — es un `Bundle` distinto que lleva instrucciones de animación para el sistema de ventanas. Se explica a fondo en [08-transiciones](08-transiciones.md).

---

## 4. `AndroidManifest.xml`: el "índice" de la app

Toda Activity que quieras poder abrir con un `Intent` **tiene que estar declarada** en `AndroidManifest.xml`, si no, la app crashea con `ActivityNotFoundException` al intentar lanzarla:

```xml
<activity
    android:name=".jugadores_Activity"
    android:exported="false"
    android:windowSoftInputMode="adjustResize" />
```

- `android:name` — la clase Java (el `.` inicial es un atajo para `com.example.ejercicioadaptadoresfinal.jugadores_Activity`).
- `android:exported` — si `true`, otras apps del sistema pueden lanzar esta Activity directamente; `false` la deja solo accesible desde dentro de tu propia app. Desde Android 12 es **obligatorio** declarar este atributo explícitamente en las Activities con `<intent-filter>`.
- La Activity "de arranque" (la que se abre al tocar el icono de la app) lleva además:
  ```xml
  <intent-filter>
      <action android:name="android.intent.action.MAIN" />
      <category android:name="android.intent.category.LAUNCHER" />
  </intent-filter>
  ```

---

## 5. El bloque que se repite en TODOS los `onCreate()`

Este fragmento aparece literalmente en cada Activity de cada proyecto:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        return insets;
    });
    ...
}
```

Vamos línea a línea:

- **`super.onCreate(savedInstanceState)`** — obligatorio siempre, deja que `AppCompatActivity` haga su inicialización interna antes de que tú sigas añadiendo la tuya.

- **`EdgeToEdge.enable(this)`** — esto es una plantilla que genera Android Studio por defecto en proyectos nuevos (usa la librería `androidx.activity:activity`). Le dice al sistema: "quiero que mi contenido se dibuje **por detrás** de la barra de estado y la barra de navegación" (pantalla completa "de borde a borde"), en vez de que el sistema reserve automáticamente ese espacio. Es el comportamiento recomendado/forzado en las versiones más recientes de Android.

- **`setContentView(R.layout.activity_main)`** — "infla" (convierte de XML a objetos `View` reales en memoria) el layout indicado y lo pone como contenido de la pantalla. `R.layout.activity_main` es una referencia autogenerada al fichero `res/layout/activity_main.xml`.

- **El bloque `ViewCompat.setOnApplyWindowInsetsListener(...)`** — es la contrapartida obligatoria de `EdgeToEdge.enable()`. Como el contenido ahora se dibuja debajo de la barra de estado/navegación, sin este código tus botones o textos superiores quedarían tapados por la barra de estado. Este listener:
  1. Detecta cuánto espacio ocupan las barras del sistema (`systemBars`) en cada lado.
  2. Aplica ese espacio como **padding** (relleno interno) a la vista raíz (`R.id.main`), para que el contenido "se aparte" visualmente de las barras sin dejar de dibujarse por detrás de ellas.

  `findViewById(R.id.main)` busca la vista raíz del layout — por eso en **todos** los layouts raíz verás `android:id="@+id/main"`.

Este bloque es puro "boilerplate" (código repetitivo de plantilla) y normalmente no hace falta tocarlo nunca — solo hay que saber que existe y por qué, para no borrarlo por accidente pensando que "no hace nada".

---

## 6. `findViewById`

```java
TextView tvNombre = findViewById(R.id.tvNombreEquipo);
```

Busca, dentro del árbol de vistas ya inflado por `setContentView`, el `View` cuyo `android:id="@+id/tvNombreEquipo"` coincide, y te lo devuelve casteado automáticamente al tipo que hayas puesto a la izquierda (gracias a la inferencia de tipos de Java moderno — en versiones antiguas había que escribir `(TextView) findViewById(...)`, y en algún sitio de tus proyectos [`DetalleSpinnerDam2Activity`] todavía se ve ese casteo explícito porque es un estilo válido igualmente).

Es importante llamarlo **después** de `setContentView()` — si lo llamas antes, la vista aún no existe en memoria y te devuelve `null`, provocando un `NullPointerException` en cuanto intentes usarla.

---

## Ver también
- [10-activity-en-profundidad](10-activity-en-profundidad.md) — el ciclo de vida completo (no solo `onCreate`), tareas/pila de retroceso y qué es exactamente un `Context`.
- [11-intent-en-profundidad](11-intent-en-profundidad.md) — Intents explícitos vs implícitos, todas las partes de un `Intent` y cómo lo resuelve el sistema.
- [07-adaptadores](07-adaptadores.md) — cómo se usa `Bundle`/`Intent` dentro de los `onItemClick` de listas y grids.
- [08-transiciones](08-transiciones.md) — el segundo `Bundle` (de animación) que se pasa a `startActivity`.

## 🏋️ Practica esto

- [Nivel 1 — ejercicios 1.2 a 1.5](../ejercicios/01-nivel-1-java-y-primera-app.md) (botón, `EditText`, `Toast`, `Intent`, `putExtra`)
- [Guía: ejecutar la app en emulador o móvil](../guias/01-ejecutar-la-app.md)
