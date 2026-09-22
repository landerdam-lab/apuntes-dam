---
tags:
  - android
  - proyecto
  - estado/incompleto
---

# Proyecto: `equiposFutbol`

## Qué es

Un ejercicio de lista + rejilla de equipos de fútbol, **a medio terminar**: la lista de equipos funciona, pero la navegación al detalle (`GridViewActivity`, para ver los jugadores de un equipo) está vacía y el adaptador de jugadores no está implementado. Es un buen ejemplo para estudiar tanto lo que SÍ funciona como los errores típicos de un ejercicio incompleto.

## Estructura

```
app/src/main/java/com/example/equiposfutbol/
├── MainActivity.java              → lista de equipos (ListView) — FUNCIONA
├── GridViewActivity.java          → pantalla de jugadores del equipo — VACÍA, sin terminar
├── adaptadores/
│   ├── EquiposAdapter.java        → adaptador de la lista de equipos — FUNCIONA
│   └── ImageAdapter.java          → adaptador de jugadores en rejilla — SIN IMPLEMENTAR
└── model/
    ├── Equipos.java                → modelo de datos de un equipo
    └── Jugador.java                → modelo de datos de un jugador
```

> Si no sabes qué es una clase, un atributo, un constructor o qué significa `extends`/`implements`, lee primero [00-programacion-basica](../conceptos/00-programacion-basica.md) — el resto de este documento da esos términos por sabidos.

## `model/Equipos.java` y `model/Jugador.java`

Dos clases modelo sencillas, sin lógica, solo campos + constructor + getters/setters (el patrón "POJO", Plain Old Java Object):

```java
public class Equipos {
    private String nombre;
    private int logo;            // id de recurso drawable local (R.drawable.xxx)
    private float valorEquipo;

    public Equipos(String nombre, float valorEquipo, int logo) {
        this.nombre = nombre;
        this.logo = logo;
        this.valorEquipo = valorEquipo;
    }
    // getters/setters...
}
```
```java
public class Jugador {
    private String nombre;
    private String posicion;
    private float altura;
    private int foto;
    ...
}
```

**A diferencia de** [EjercicioAdaptadoresFinal](EjercicioAdaptadoresFinal.md), aquí `logo`/`foto` son `int` (un id de `R.drawable`, imagen guardada dentro del propio proyecto) en vez de `String` con una URL — por eso este adaptador usa `setImageResource(...)` en lugar de Picasso/Glide (ver [07-adaptadores](../conceptos/07-adaptadores.md) §2). Y ninguna de las dos clases implementa `Serializable`, porque en este proyecto **no se llega a pasar un objeto completo entre Activities** (ver el bug más abajo).

## `adaptadores/EquiposAdapter.java` — este sí funciona

`EquiposAdapter extends BaseAdapter` — la clase **hereda** de `BaseAdapter` (ver [00-programacion-basica](../conceptos/00-programacion-basica.md) §6 si no sabes qué significa `extends`) y solo rellena los 4 métodos que le faltan.

```java
@Override
public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = LayoutInflater.from(this.context);
    View fila = inflater.inflate(R.layout.equipos_list, parent, false);

    Equipos equipo = this.equipos.get(position);
    ImageView ivLogo = fila.findViewById(R.id.iconoEquipo);
    TextView tvTelegono = fila.findViewById(R.id.tvNombreEquipo);
    TextView tvPrecio = fila.findViewById(R.id.tvValorEquipo);

    ivLogo.setImageResource(equipo.getLogo());
    tvTelegono.setText(equipo.getNombre());
    tvPrecio.setText(equipo.getValorEquipo()+"");

    return fila;
}
```
Sigue exactamente el patrón `BaseAdapter` explicado en [07-adaptadores](../conceptos/07-adaptadores.md) §2: infla `equipos_list.xml`, busca sus vistas, las rellena con los datos del `Equipos` en esa posición, devuelve la fila.

(Nota: la variable se llama `tvTelegono` — con errata, debería ser algo como `tvNombreEquipo`/`tvTelefono` — un típico "nombre mal escrito pero sin efecto funcional", ya que solo es el nombre de una variable local.)

## `adaptadores/ImageAdapter.java` — sin implementar

```java
public class ImageAdapter extends BaseAdapter {
    private ArrayList<Jugador> jugadores;
    private final Context contexto;

    @Override
    public int getCount() { return jugadores.size(); }

    @Override
    public Object getItem(int position) { return null; }     // ← no devuelve el jugador real

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { return null; }  // ← no construye ninguna vista
}
```
Este adaptador compila (implementa todos los métodos abstractos de `BaseAdapter` — "abstracto" significa que `BaseAdapter` OBLIGA a cualquier clase que herede de él a escribir estos 4 métodos, ver [00-programacion-basica](../conceptos/00-programacion-basica.md) §6-7 para herencia e interfaces —, así que Java no se queja), pero **no hace nada útil**: `getItem` siempre devuelve `null` y `getView` siempre devuelve `null` — si se llegara a usar (`gridView.setAdapter(new ImageAdapter(...))` y luego se mostrara en pantalla), la app **crashearía** al intentar dibujar cualquier fila, porque `GridView` espera que `getView` le devuelva una `View` real, nunca `null`.

**Para completarlo**, siguiendo el patrón de [EjercicioAdaptadoresFinal](EjercicioAdaptadoresFinal.md) (`JugadoresAdapter`, que es el equivalente ya terminado):
```java
@Override
public Object getItem(int position) {
    return jugadores.get(position);
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = LayoutInflater.from(contexto);
    View fila = inflater.inflate(R.layout.grid_item, parent, false);   // layout de rejilla ya existe en el proyecto
    ImageView iv = fila.findViewById(R.id.ivJugadorFoto);              // ajustar al id real del layout
    iv.setImageResource(jugadores.get(position).getFoto());
    return fila;
}
```

## `MainActivity.java` — funciona, pero con un bug de variable "sombra" (shadowing)

```java
final Intent intent = new Intent(this, MainActivity.class);   // (A) se crea UN intent aquí fuera...

listaEquipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Equipos seleccionarEquipo = equipos.get(position);
        intent.putExtra("equipo", seleccionarEquipo.getNombre());   // (B) ...se le añade el nombre del equipo a ESE intent...
        Intent intent = new Intent(MainActivity.this, GridViewActivity.class);  // (C) ...pero aquí se declara OTRA variable "intent" NUEVA, con el mismo nombre
        startActivity(intent);                                       // (D) se lanza la NUEVA (C), no la (A) que llevaba el extra
    }
});
```

Esto es un bug real y bastante instructivo: en la línea **(C)** se declara una variable local `intent` **dentro** del método `onItemClick`, con el mismo nombre que la variable `intent` de fuera (A). En Java esto se llama **"shadowing"** (la variable local "tapa" a la de fuera dentro de ese bloque) — compila sin ningún error ni aviso grave, pero el resultado es que:
- El `Intent` de la línea (A), al que sí se le añadió el nombre del equipo (B), **nunca se usa para lanzar nada**.
- El `Intent` que realmente se lanza (D) es el nuevo de la línea (C), que **no lleva ningún extra** — apunta a `GridViewActivity` pero sin decirle qué equipo se seleccionó.

Además, `GridViewActivity` (ver más abajo) ni siquiera intenta leer ese extra, así que el bug no se nota "a simple vista" en este estado del proyecto — pero es exactamente el tipo de error que causaría un `NullPointerException` en cuanto `GridViewActivity` intentara hacer `getIntent().getStringExtra("equipo")` esperando un valor que nunca llegó.

**Cómo se arreglaría** (siguiendo el patrón correcto explicado en [01-fundamentos-bundle-intent-ciclo-vida](../conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) §3): eliminar la declaración duplicada y reutilizar una sola variable:
```java
listaEquipos.setOnItemClickListener((parent, view, position, id) -> {
    Equipos seleccionarEquipo = equipos.get(position);
    Intent intent = new Intent(MainActivity.this, GridViewActivity.class);
    intent.putExtra("equipo", seleccionarEquipo.getNombre());
    startActivity(intent);
});
```

## `GridViewActivity.java` — vacía

```java
public class GridViewActivity extends AppCompatActivity {
    private GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grid_view);
        ...
    }
}
```
Declara un campo `GridView grid`, pero nunca hace `findViewById`, nunca crea un `ImageAdapter`, y nunca lee el `Intent` que la lanzó. Es el "hueco" que falta rellenar para que el ejercicio quede completo — el patrón exacto a seguir está ya resuelto en [EjercicioAdaptadoresFinal](EjercicioAdaptadoresFinal.md) (`jugadores_Activity.java`), que es esencialmente el mismo ejercicio (equipo → lista de jugadores) pero terminado.

## `rellenarJugadores()` — vacío

```java
private void rellenarJugadores(){

}
```
Se llama desde `onCreate` pero no rellena el `ArrayList<Jugador> jugadores`, que se queda vacío durante toda la ejecución.

## Resumen de lo que falta para terminar el ejercicio

1. Rellenar `rellenarJugadores()` con datos de ejemplo (comparar con `rellenarEquipos()`, que sí está completo).
2. Arreglar el bug de la variable `intent` duplicada en `MainActivity`.
3. Implementar `ImageAdapter.getItem` y `getView` de verdad.
4. En `GridViewActivity`: leer el extra `"equipo"` del `Intent`, hacer `findViewById` del `GridView`, y conectarlo con `new ImageAdapter(...)`.
