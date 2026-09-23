---
tags:
  - android
  - proyecto
  - estado/completo
---

# Proyecto: `AdapterDam2`

## Qué es

El proyecto de referencia para **todo lo relacionado con adaptadores y transiciones**: contiene un `ListView`, un `Spinner` y un `GridView`, cada uno con su propio `Adapter`, y además la única transición de **elemento compartido** (shared element / "hero animation") de todos tus proyectos. Es el más completo en cuanto a variedad de técnicas.

## Estructura

```
app/src/main/java/com/example/adapterdam2/
├── MainActivity.java                  → menú con 3 botones (ListView / Spinner / GridView)
├── ListDam2Activity.java               → lista de compañías telefónicas
├── DetalleActivity.java                → detalle de una imagen del grid (con shared element)
├── SpinnerDam2Activity.java            → spinner de cursos
├── DetalleSpinnerDam2Activity.java     → pantalla que muestra el curso elegido
├── GridViewDam2Activity.java           → rejilla de imágenes
├── adaptadores/
│   ├── CompaniasAdapter.java            → BaseAdapter para el ListView
│   ├── SpinnerDam2Adapter.java           → ArrayAdapter para el Spinner
│   └── ImageAdapter.java                 → BaseAdapter para el GridView
└── model/
    └── CompaniaTelefonica.java           → modelo de datos
```

## `MainActivity.java` — tres formas distintas de lanzar una Activity

```java
((Button)findViewById(R.id.btnListView)).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
        startActivity(new Intent(getApplicationContext(),ListDam2Activity.class), options.toBundle());
        //overridePendingTransition(R.anim.entrada,R.anim.salida);
    }
});

((Button)findViewById(R.id.btnSpinner)).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),SpinnerDam2Activity.class));
        overridePendingTransition(R.anim.entrada,R.anim.salida);
    }
});

((Button)findViewById(R.id.btnGridView)).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),GridViewDam2Activity.class));
        overridePendingTransition(R.anim.entrada,R.anim.salida);
    }
});
```
Este único fichero es un resumen perfecto de las **tres técnicas de transición** explicadas en [08-transiciones](../02-conceptos/08-transiciones.md):
- Botón "ListView" → `ActivityOptionsCompat.makeSceneTransitionAnimation(...)` (sin `Pair`, sin vista concreta) — activa la transición de **contenido** que `ListDam2Activity` configura en su propio `onCreate` (ver más abajo). El `overridePendingTransition` comentado justo debajo es el resto de una prueba con la técnica alternativa, dejado como referencia.
- Botones "Spinner" y "GridView" → `overridePendingTransition(R.anim.entrada, R.anim.salida)`, la técnica más simple (animación de ventana con recursos `anim/`).

## `ListDam2Activity.java` — `ListView` + `BaseAdapter` + transición `slide`

```java
Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
getWindow().setEnterTransition(slide);          // ANTES de setContentView() ✓

setContentView(R.layout.activity_list_dam2);
...
rellenarCompanias();
CompaniasAdapter adapter = new CompaniasAdapter(this, companiaTelefonicas);
listaCompanias = findViewById(R.id.lvTelefonica);
listaCompanias.setAdapter(adapter);
```
Aquí la transición `slide` se configura **correctamente** (antes de `setContentView`, ver [08-transiciones](../02-conceptos/08-transiciones.md) §2.2) y se dispara correctamente porque `MainActivity` la lanza con `ActivityOptionsCompat.makeSceneTransitionAnimation(...)` — los dos lados del contrato (declarar la transición + lanzar con opciones) están cubiertos, por eso esta es la transición de contenido "de ejemplo que sí funciona" a la que se puede comparar cualquier otra que falle.

```java
private void rellenarCompanias(){
    companiaTelefonicas.add(new CompaniaTelefonica("Movistar", 80, R.drawable.movistar_isotype_2025));
    companiaTelefonicas.add(new CompaniaTelefonica("Euskaltel", 80, R.drawable.euskaltel_la_morea_pamplona));
    ...
}
```
Datos de ejemplo "quemados" en el código (hardcodeados) — en una app real vendrían de una base de datos o una API, pero para el ejercicio se rellenan a mano.

## `model/CompaniaTelefonica.java`

```java
public class CompaniaTelefonica {
    private String nombre;
    private int logo;     // R.drawable.xxx — imagen local, no URL
    private float precio;
    public CompaniaTelefonica(String nombre, float precio, int logo) { ... }
    // getters/setters
}
```
Fíjate en el **orden de los parámetros del constructor** (el método especial que rellena el objeto al crearlo con `new`, ver [00-programacion-basica](../02-conceptos/00-programacion-basica.md) §5): `(nombre, precio, logo)`, pero al llamarlo se escribe `new CompaniaTelefonica("Movistar", 80, R.drawable.movistar...)` — es decir, el `80` es el **precio**, no un id de recurso, y el drawable va en tercera posición. Un detalle fácil de confundir si no se mira la firma del constructor con atención.

## `adaptadores/CompaniasAdapter.java`

```java
@Override
public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = LayoutInflater.from(this.context);
    View fila = inflater.inflate(R.layout.compania_telefonica_list, parent, false);

    CompaniaTelefonica compania = this.companiaTelefonicas.get(position);
    ImageView ivLogo = fila.findViewById(R.id.iconoTelefono);
    ...
    ivLogo.setImageResource(compania.getLogo());
    ...
    if (position % 2 == 0) {
        fila.setBackgroundColor(Color.GRAY);
    } else {
        fila.setBackgroundColor(Color.LTGRAY);
    }
    return fila;
}
```
El patrón `BaseAdapter` estándar (ver [07-adaptadores](../02-conceptos/07-adaptadores.md) §2), con un detalle extra: **colores de fila alternos** según si `position` es par o impar (`position % 2 == 0`) — el clásico efecto "cebra" (*zebra striping*) para facilitar la lectura de listas largas, calculado directamente dentro de `getView` en vez de definirlo en el XML.

## `SpinnerDam2Activity.java` + `adaptadores/SpinnerDam2Adapter.java`

Ya explicados fragmento a fragmento en [07-adaptadores](../02-conceptos/07-adaptadores.md) §6 — es el ejemplo canónico de `ArrayAdapter` personalizado con `getView`/`getDropDownView`, el truco del "+1" para la fila de aviso, y la bandera `ver` para ignorar la primera selección automática del `Spinner`.

```java
final Intent intent = new Intent(this, DetalleSpinnerDam2Activity.class);
cbCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (ver == 1 && position > 0) {
            Bundle bundle = new Bundle();
            bundle.putString("curso", parent.getItemAtPosition(position-1)+"");
            intent.putExtras(bundle);
            startActivity(intent);
        }
        ver = 1;
    }
    ...
});
```
El `Intent` se crea **una sola vez, fuera** del listener (a diferencia del bug visto en [equiposFutbol](equiposFutbol.md), donde se creaba mal dentro) — así se reutiliza la misma variable en cada selección válida, evitando el problema de "variable sombra" (declarar dentro de un bloque otra variable con el mismo nombre que una de fuera, "tapándola" por accidente).

## `DetalleSpinnerDam2Activity.java`

```java
String saludo = getIntent().getStringExtra("curso");
((TextView)findViewById(R.id.tvDetalleSpinner)).setText(saludo);
```
Recuperación simple de un `String` desde el `Intent` (ver [01-fundamentos-bundle-intent-ciclo-vida](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) §2.2), con **casteo** explícito de `findViewById` (ver [00-programacion-basica](../02-conceptos/00-programacion-basica.md) §12) en vez de inferencia de tipo — ambas formas son válidas y equivalentes.

## `GridViewDam2Activity.java` + `adaptadores/ImageAdapter.java` — la transición de elemento compartido

```java
grid = findViewById(R.id.gridImagenes);
grid.setAdapter(new ImageAdapter(getApplicationContext(), getResources().obtainTypedArray(R.array.pikachus)));

grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(GridViewDam2Activity.this, DetalleActivity.class);
        intent.putExtra("idFoto", position);

        View imagenPulsada = view.findViewById(R.id.imagenGridView);
        ActivityOptionsCompat opciones = ActivityOptionsCompat.
                makeSceneTransitionAnimation(GridViewDam2Activity.this,
                        new Pair<>(imagenPulsada, DetalleActivity.VIEW_NAME_HEADER_IMAGE));
        ActivityCompat.startActivity(GridViewDam2Activity.this, intent, opciones.toBundle());
    }
});
```
Este es el ejemplo real completo de la técnica explicada en [08-transiciones](../02-conceptos/08-transiciones.md) §3:
1. `intent.putExtra("idFoto", position)` — se pasa qué imagen se pulsó (un simple índice `int`, ver [01-fundamentos-bundle-intent-ciclo-vida](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md)).
2. `view.findViewById(R.id.imagenGridView)` — se obtiene la vista **exacta** de la fila pulsada (no de todo el `GridView`, sino de esa única celda).
3. `new Pair<>(imagenPulsada, DetalleActivity.VIEW_NAME_HEADER_IMAGE)` — se empareja esa vista con la etiqueta que `DetalleActivity` usará para reconocer "esta es la misma foto que debo animar".
4. `ActivityCompat.startActivity(..., opciones.toBundle())` — se lanza con las opciones de animación de elemento compartido.

```java
public class ImageAdapter extends BaseAdapter {
    private final TypedArray imagenes;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vistaImagen = inflater.inflate(R.layout.grid_item_view, parent, false);
        ImageView imageView = vistaImagen.findViewById(R.id.imagenGridView);
        Glide.with(contexto).load(imagenes.getResourceId(position,-1)).into(imageView);
        return vistaImagen;
    }
}
```
Aquí las imágenes son recursos locales (`TypedArray` de drawables, ver [06-animaciones-frame-by-frame](../02-conceptos/06-animaciones-frame-by-frame.md) §2), pero aun así se cargan con **Glide** en vez de `setImageResource` directo (una línea comentada `//imageView.setImageResource(...)` deja constancia de que ambas formas se probaron) — usar Glide para recursos locales no es estrictamente necesario, pero mantiene el mismo código de carga de imágenes uniforme en todo el proyecto.

## `DetalleActivity.java` — el destino de la transición

```java
public class DetalleActivity extends AppCompatActivity {
    public static final String VIEW_NAME_HEADER_IMAGE = "imagenCabecera";

    protected void onCreate(Bundle savedInstanceState) {
        ...
        int pos = getIntent().getIntExtra("idFoto",0);
        TypedArray paisajes = getResources().obtainTypedArray(R.array.pikachus);
        int idImagen = paisajes.getResourceId(pos,-1);
        paisajes.recycle();                                     // ← aquí SÍ se libera el TypedArray

        ImageView ivImagen = findViewById(R.id.ivDetalles);
        ViewCompat.setTransitionName(ivImagen, VIEW_NAME_HEADER_IMAGE);
        Glide.with(getApplicationContext()).load(idImagen).into(ivImagen);

        TextView tvTitulo = findViewById(R.id.tvDetallesTitulo);
        tvTitulo.setText("paisaje"+(pos+1));
    }
}
```
- `VIEW_NAME_HEADER_IMAGE` — la constante que actúa de "clave" de la transición, explicada en profundidad en [08-transiciones](../02-conceptos/08-transiciones.md) §3.2 (y que fue, de hecho, la pregunta que dio origen a esta documentación: por qué es `public static final`).
- `getIntent().getIntExtra("idFoto", 0)` — recupera el índice de imagen que se pulsó en el grid.
- `ViewCompat.setTransitionName(ivImagen, VIEW_NAME_HEADER_IMAGE)` — marca **esta** `ImageView` como el destino de la animación de elemento compartido, usando la misma etiqueta que se usó como origen.
- `paisajes.recycle()` — aquí sí se libera el `TypedArray` correctamente (comparar con la observación de buena práctica en [06-animaciones-frame-by-frame](../02-conceptos/06-animaciones-frame-by-frame.md) §2).

## Relación con otros proyectos

- Es el único proyecto con transición de **elemento compartido** (shared element) — todos los demás, cuando usan transiciones, son de contenido genérico (fundido/slide, ver [EjercicioAdaptadoresFinal](EjercicioAdaptadoresFinal.md)).
- El patrón `BaseAdapter` (`CompaniasAdapter`, `ImageAdapter`) es idéntico en estructura al de [EjercicioAdaptadoresFinal](EjercicioAdaptadoresFinal.md) y [equiposFutbol](equiposFutbol.md).
