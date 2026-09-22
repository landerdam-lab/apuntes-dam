---
tags:
  - android
  - proyecto
  - estado/completo
---

# Proyecto: `EjercicioAdaptadoresFinal`

## Qué es

El ejercicio "final" que combina modelos anidados (Liga → Equipos → Jugadores), adaptadores con imágenes cargadas por URL (Picasso), navegación en tres niveles (lista de equipos → jugadores del equipo → detalle de un jugador) y transiciones de Activity — incluyendo un **bug real que se depuró en directo** durante el desarrollo de esta documentación, con evidencia capturada del emulador. Es el proyecto más representativo de "cómo se ve un ejercicio completo" de todos los de la carpeta.

## Estructura

```
app/src/main/java/com/example/ejercicioadaptadoresfinal/
├── MainActivity.java                  → lista de equipos (ListView)
├── jugadores_Activity.java             → jugadores del equipo elegido (GridView)
├── Detalles_Jugador.java                → ficha de un jugador (transición "slide")
├── DetalleActivity.java                 → Activity suelta, sin usar en el flujo actual
├── adaptadores/
│   ├── EquiposAdapter.java              → BaseAdapter para la lista de equipos
│   └── JugadoresAdapter.java             → BaseAdapter para la rejilla de jugadores
└── modelos/
    ├── Liga.java                         → contiene una lista de Equipo
    ├── Equipo.java                       → contiene una lista de Jugador
    └── Jugador.java                       → datos de un jugador
```

## Los modelos: `Liga` → `Equipo` → `Jugador` (composición + `Serializable`)

Antes de entrar en el código: si no sabes qué es una clase, un atributo, un constructor o qué significa `implements`, lee primero [00-programacion-basica](../conceptos/00-programacion-basica.md) — todo lo de aquí abajo da esos términos por sabidos.

```java
public class Liga implements Serializable {
    private ArrayList<Equipo> equipos;
    public Liga() { equipos = new ArrayList<Equipo>(); }
    ...
}

public class Equipo implements Serializable { //Si los datos son primitivos o serializables se pone para poder pasar los datos de una activity a otra
    private String nombre, ciudad, logo;
    private float precio;
    private ArrayList<Jugador> jugadores;
    ...
}

public class Jugador implements Serializable {
    private String nombre, posicion, foto;
    private int dorsal;
    ...
}
```
Los tres modelos implementan `Serializable` (`Serializable` es una **interfaz** — ver [00-programacion-basica](../conceptos/00-programacion-basica.md) §7 si no sabes qué significa eso — y su uso concreto aquí se explica en [01-fundamentos-bundle-intent-ciclo-vida](../conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) §2.3), y el propio comentario dejado en el código de `Equipo` lo explica: es un requisito para poder meter estos objetos en un `Bundle`/`Intent` al navegar entre pantallas. Fíjate en que `Liga` contiene una lista de `Equipo`, y `Equipo` contiene una lista de `Jugador` — es una jerarquía de objetos anidados, y **basta con que la clase de más "fuera" (`Liga`) sea serializable para que toda la cadena lo sea**, siempre que cada clase interna (`Equipo`, `Jugador`) también lo sea (si alguna no lo fuera, fallaría en tiempo de ejecución al intentar serializar, no en compilación).

`logo` en `Equipo` y `foto` en `Jugador` son de tipo `String` (una **URL**), no `int` (a diferencia de [equiposFutbol](equiposFutbol.md) o [AdapterDam2](AdapterDam2.md), donde eran recursos locales) — por eso el adaptador correspondiente usa Picasso en vez de `setImageResource` (ver más abajo).

## `MainActivity.java` — construcción manual de los datos + primera navegación

```java
private void rellenarEquipo() {
    nba = new Liga();
    Equipo lakers, bulls, celtics;
    ArrayList<Jugador> jugadores = new ArrayList<>();

    jugadores.add(new Jugador("Lebron James", 23, "Alero", "https://cdn.nba.com/headshots/.../2544.png"));
    jugadores.add(new Jugador("Lebron", 23, "Alero", "..."));
    // (se repite el mismo jugador 5 veces)

    lakers = new Equipo("Lakers", "Los Angeles", "https://.../lakers.svg", jugadores, 1);
    bulls = new Equipo("Bulls", "Chicago", "https://.../bulls.jpg", jugadores, 1);
    celtics = new Equipo("Celtics", "Boston", "https://.../celtics.png", jugadores, 1);

    ArrayList<Equipo> equipos = new ArrayList<Equipo>();
    equipos.add(lakers); equipos.add(bulls); equipos.add(celtics);
    nba.setEquipos(equipos);
}
```
Datos de ejemplo quemados a mano (igual que en [AdapterDam2](AdapterDam2.md)). Fíjate en que aquí se construyen los objetos con `new Equipo(...)` pasando los datos directamente al **constructor** (ver [00-programacion-basica](../conceptos/00-programacion-basica.md) §5 si no sabes qué es un constructor). Un detalle a tener en cuenta: **los tres equipos (`lakers`, `bulls`, `celtics`) comparten la misma instancia de `ArrayList<Jugador> jugadores`** — como solo se creó una lista y se le pasó la misma referencia a los tres constructores de `Equipo`, en realidad los tres equipos "apuntan" a la misma lista de jugadores en memoria (todos ven exactamente los mismos 5 "Lebron"). Si se modificara la lista de jugadores de un equipo, cambiaría también en los otros dos — funciona para el ejercicio (mostrar datos de ejemplo), pero si se quisiera dar a cada equipo su propia plantilla de jugadores distinta, habría que crear un `new ArrayList<>()` independiente para cada uno.

```java
lvEquipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Equipo equipo = nba.getEquipos().get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("equipoSeleccionado", equipo);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
        Intent intent = new Intent(getApplicationContext(), jugadores_Activity.class);
        intent.putExtras(bundle);
        startActivity(intent, options.toBundle());
    }
});
```
El patrón completo: `Bundle` con `putSerializable` (ver [01-fundamentos-bundle-intent-ciclo-vida](../conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) §2.2) + lanzamiento con `ActivityOptionsCompat.makeSceneTransitionAnimation` (sin `Pair`, así que activa la transición de **contenido**, no de elemento compartido — ver [08-transiciones](../conceptos/08-transiciones.md) §2.4). Esta llamada **sí incluye** las opciones de animación, por eso la transición de `jugadores_Activity` (ver más abajo) funciona sin problema.

## `adaptadores/EquiposAdapter.java`

```java
Picasso.get().load(equipos.get(position).getLogo()).into(logo);
tvNombre.setText(equipos.get(position).getNombre());
tvCiudad.setText(equipos.get(position).getCiudad());
tvPrecio.setText(equipos.get(position).getPrecio()+"");
```
Patrón `BaseAdapter` estándar (`EquiposAdapter extends BaseAdapter` — hereda de `BaseAdapter`, ver [00-programacion-basica](../conceptos/00-programacion-basica.md) §6 si no sabes qué significa `extends`; y ver [07-adaptadores](../conceptos/07-adaptadores.md) §2 para el patrón completo), usando **Picasso** para cargar la URL del logo (a diferencia de Glide en `AdapterDam2`) — mismo concepto, librería distinta.

## `jugadores_Activity.java` — el caso de estudio del bug de transición

Esta Activity recibe el `Equipo` elegido y muestra sus jugadores en una rejilla:

```java
Equipo equipo = (Equipo) getIntent().getSerializableExtra("equipoSeleccionado");
...
GridView gridView = findViewById(R.id.gvJugadores);
JugadoresAdapter jugadoresAdapter = new JugadoresAdapter(equipo.getJugadores(), getApplicationContext());
gridView.setAdapter(jugadoresAdapter);

gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Jugador jugador = equipo.getJugadores().get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("jugadorSeleccionado", jugador);
        Intent intent = new Intent(getApplicationContext(), Detalles_Jugador.class);
        intent.putExtras(bundle);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(jugadores_Activity.this);
        startActivity(intent, options.toBundle());
    }
});
```

### Historial real del bug (útil como caso práctico de depuración)

En una versión anterior de este fichero había, al principio de `onCreate` (antes de `setContentView`):
```java
Transition auto = TransitionInflater.from(this).inflateTransition(R.transition.auto);
getWindow().setEnterTransition(auto);
```
...mientras que el **tema** (`res/values/themes.xml`) **ya declaraba** esa misma transición para toda la app:
```xml
<item name="android:windowEnterTransition">@transition/auto</item>
<item name="android:windowExitTransition">@transition/auto</item>
```
Es decir, la transición de entrada de `jugadores_Activity` se estaba configurando **dos veces** (tema + código), lo que generaba conflicto: la ventana quedaba "pausada" esperando a que las dos transiciones terminasen de coordinarse, provocando que a veces la pantalla dejara de responder al tacto brevemente. **Solución**: eliminar la configuración duplicada en el código de `jugadores_Activity` (dejando que solo el tema la controle) — ver [08-transiciones](../conceptos/08-transiciones.md) §5, punto 1.

Después de arreglar eso, el `startActivity(intent)` hacia `Detalles_Jugador` **seguía sin animarse** con el `slide` configurado en esa Activity de destino. La causa, en este caso, era otra: faltaba pasar `ActivityOptionsCompat.makeSceneTransitionAnimation(...)` al lanzar la Activity — se estaba llamando a `startActivity(intent)` a secas. La versión final (mostrada arriba) ya incluye la corrección: crear las `options` y pasarlas como segundo argumento de `startActivity`. Este es el ejemplo real citado en [08-transiciones](../conceptos/08-transiciones.md) §2.4.

**Lección general**: cuando una transición "no hace nada", hay que comprobar **los dos lados por separado**:
1. ¿Está bien declarada la transición en el destino (tema o `getWindow().setEnterTransition`, antes de `setContentView`)?
2. ¿Se está lanzando la Activity con `ActivityOptionsCompat.makeSceneTransitionAnimation(...)` en el origen?

Si falta cualquiera de los dos, no hay animación visible, aunque el otro esté perfecto.

## `Detalles_Jugador.java`

```java
Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
getWindow().setEnterTransition(slide);          // ANTES de setContentView() ✓
setContentView(R.layout.activity_detalles_jugador);
...
Jugador jugador = (Jugador) getIntent().getSerializableExtra("jugadorSeleccionado");
Picasso.get().load(jugador.getFoto()).into(ivFotoJugador);
```
Configura una transición `slide` (deslizar desde abajo, ver [08-transiciones](../conceptos/08-transiciones.md) §2.3) **propia de esta Activity**, distinta del `auto` (fundido) que usa el resto de la app por tema — es un ejemplo de "sobreescribir" la transición por defecto solo para una pantalla concreta.

## `DetalleActivity.java` — código sin usar en el flujo actual

```java
public class DetalleActivity extends AppCompatActivity {
    public static final String VIEW_NAME_HEADER_IMAGE = "imagenCabecera";
    protected void onCreate(Bundle savedInstanceState) {
        ...
        setContentView(R.layout.activity_detalle);
        ...
    }
}
```
Esta Activity existe y está declarada en el manifiesto, pero **ninguna otra pantalla del proyecto la lanza** (no hay ningún `Intent` hacia `DetalleActivity.class` en todo el código). Probablemente es un resto de una versión anterior del ejercicio (quizás copiada como plantilla desde [AdapterDam2](AdapterDam2.md), de donde procede literalmente el mismo patrón de `VIEW_NAME_HEADER_IMAGE` para shared element transitions) que se dejó sin eliminar. No afecta al funcionamiento de la app, pero es "código muerto" a limpiar si se quisiera dejar el proyecto ordenado.

## `AndroidManifest.xml`

```xml
<uses-permission android:name="android.permission.INTERNET" />
...
<activity android:name=".MainActivity" android:exported="true" ...>
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
<activity android:name=".jugadores_Activity" android:exported="false" .../>
<activity android:name=".Detalles_Jugador" android:exported="false" .../>
<activity android:name=".DetalleActivity" android:exported="false" .../>
```
El permiso de `INTERNET` es imprescindible aquí porque Picasso necesita descargar las imágenes de las URLs de los modelos — sin este permiso, la app compilaría igual pero todas las cargas de imagen fallarían silenciosamente (ver [09-instalacion-estructura-proyecto](../conceptos/09-instalacion-estructura-proyecto.md) §5). Solo `MainActivity` lleva el `<intent-filter>` de lanzador — es la única pantalla accesible directamente desde el icono de la app; las demás solo se abren mediante `Intent` desde dentro de la propia app (`android:exported="false"`).

## Relación con otros proyectos

- Estructura de modelos (`Liga`/`Equipo`/`Jugador`) y de adaptadores: evolución directa de [equiposFutbol](equiposFutbol.md), pero completa y funcionando (incluyendo el `ImageAdapter`/`JugadoresAdapter` que en `equiposFutbol` se dejó vacío).
- Transiciones: comparte técnicas con [AdapterDam2](AdapterDam2.md) (transición de contenido con tema + `ActivityOptionsCompat`), pero sin llegar a usar shared element transitions (aunque deja el código de `VIEW_NAME_HEADER_IMAGE` preparado para ello, sin usar).
