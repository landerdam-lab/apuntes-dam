---
tags:
  - android
  - concepto
---

# Adaptadores (`BaseAdapter`, `ArrayAdapter`) con `ListView`, `GridView` y `Spinner`

## 1. El problema que resuelven

Tienes una `ArrayList<Equipo>` con 20 equipos y quieres mostrarlos en pantalla como una lista de filas, cada una con logo + nombre + ciudad. No vas a crear 20 `TextView` a mano en el XML — necesitas una vista "molde" (un layout de una sola fila) que se repita tantas veces como elementos tenga tu colección, rellenando cada copia con los datos correspondientes. Ese "molde + repetición + relleno" es exactamente lo que hace un **Adapter**.

```
ArrayList<Equipo>  --[Adapter]-->  ListView / GridView / Spinner en pantalla
```

El `Adapter` es el **puente** entre tus datos (un `ArrayList`, normalmente) y una vista que muestra colecciones (`ListView`, `GridView`, `Spinner`, y en apps modernas `RecyclerView`).

## 2. `BaseAdapter`: la clase base que hay que "rellenar"

Todos tus adaptadores de lista/rejilla (`CompaniasAdapter`, `EquiposAdapter`, `JugadoresAdapter`, `ImageAdapter`...) heredan de `BaseAdapter` y sobreescriben 4 métodos:

```java
public class EquiposAdapter extends BaseAdapter {

    private ArrayList<Equipo> equipos;
    private Context context;

    public EquiposAdapter(Context context, ArrayList<Equipo> equipos) {
        this.context = context;
        this.equipos = equipos;
    }

    @Override
    public int getCount() {
        return equipos.size();            // 1. ¿Cuántos elementos hay? → cuántas filas se dibujan
    }

    @Override
    public Object getItem(int position) {
        return equipos.get(position);      // 2. Dame el objeto de datos en esa posición
    }

    @Override
    public long getItemId(int position) {
        return position;                    // 3. Un id único por fila (normalmente la propia posición)
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 4. EL MÁS IMPORTANTE: construye y devuelve la vista de UNA fila concreta
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View fila = inflater.inflate(R.layout.item_equipo, parent, false);

        ImageView logo = fila.findViewById(R.id.ivEquipoLogo);
        TextView tvNombre = fila.findViewById(R.id.tvEquipoNombre);
        TextView tvCiudad = fila.findViewById(R.id.tvEquipoCiudad);

        Picasso.get().load(equipos.get(position).getLogo()).into(logo);
        tvNombre.setText(equipos.get(position).getNombre());
        tvCiudad.setText(equipos.get(position).getCiudad());

        return fila;
    }
}
```

### `getView`, paso a paso

1. **`LayoutInflater.from(context)`** — obtiene el "inflador", el objeto que sabe convertir un XML de layout en objetos `View` reales.
2. **`inflater.inflate(R.layout.item_equipo, parent, false)`** — crea una instancia nueva del layout `item_equipo.xml` (el "molde" de una fila).
   - `parent` — el `ViewGroup` donde esa fila va a vivir (el propio `ListView`/`GridView`), necesario para que la vista se cree con el `LayoutParams` correcto (ancho/alto) del contenedor real.
   - `false` — **NO** añadas automáticamente la vista al `parent` ahora mismo; el propio `ListView` se encarga de colocarla en su sitio después. Si pusieras `true` aquí, la vista se duplicaría/rompería — es un error muy común al copiar mal este patrón.
3. **`fila.findViewById(...)`** — igual que en una Activity, pero llamando sobre la vista de la fila recién inflada, no sobre la Activity entera.
4. Rellenas los datos del elemento `equipos.get(position)` en esas vistas.
5. **`return fila`** — devuelves la fila ya montada; el `ListView`/`GridView` la coloca en pantalla.

`getView` se llama **una vez por cada fila visible en pantalla** (y se vuelve a llamar cada vez que una fila entra en pantalla al hacer scroll).

### El uso de Picasso/Glide dentro de `getView`

```java
Picasso.get().load(equipos.get(position).getLogo()).into(logo);
```
Cuando el "logo" es una URL de internet (como en `EjercicioAdaptadoresFinal`, donde `Equipo.getLogo()` es un `String` con una URL), no puedes usar `setImageResource` (eso solo sirve para recursos locales del `drawable`). Picasso (o Glide, según el proyecto) se encarga de: descargar la imagen en segundo plano, cachearla, y asignarla al `ImageView` cuando esté lista — todo con una sola línea, sin que tengas que gestionar hilos tú mismo (compara con lo manual que es [05-asynctask-e-hilos](05-asynctask-e-hilos.md)).

- **Picasso** → usado en `EjercicioAdaptadoresFinal` (`EquiposAdapter`, `JugadoresAdapter`).
- **Glide** → usado en `AdapterDam2` (`ImageAdapter`, `DetalleActivity`).

Ambas hacen esencialmente lo mismo (cargar imágenes de forma asíncrona y eficiente); cuál se use en cada proyecto depende de qué librería se añadió a `build.gradle`.

## 3. El problema del rendimiento: `convertView` (y por qué en tus proyectos no se usa)

`getView` recibe un parámetro `convertView` que, en teoría, sirve para **reciclar** una fila que ya salió de la pantalla (por scroll) en vez de inflar una nueva cada vez — es la optimización clásica de `ListView` (el patrón "ViewHolder"):

```java
@Override
public View getView(int position, View convertView, ViewGroup parent) {
    View fila = convertView;
    if (fila == null) {
        fila = LayoutInflater.from(context).inflate(R.layout.item_equipo, parent, false);
    }
    ...
    return fila;
}
```

**Ninguno de tus adaptadores usa `convertView`** — siempre inflan una vista nueva (`inflater.inflate(...)`), ignorando el parámetro. Para listas cortas como las de estos ejercicios (unos pocos equipos/jugadores) esto no se nota nada, pero en una lista de miles de elementos con scroll continuo, ignorar `convertView` hace que el scroll vaya más lento (cada fotograma tendría que volver a inflar vistas desde cero). Es una optimización a tener en cuenta si algún día un adaptador de estos se reutiliza con una fuente de datos grande.

## 4. `ListView` vs `GridView`

Son la misma idea (una vista que repite filas usando un adaptador), la diferencia es solo visual:
- **`ListView`** — una columna, una fila debajo de otra.
- **`GridView`** — varias columnas (`android:numColumns="2"` en el XML), como una rejilla de fotos.

El mismo tipo de `Adapter` (`BaseAdapter`) sirve para ambos — de hecho podrías usar el mismo adaptador con un `ListView` o un `GridView` indistintamente, cambiando solo el XML.

```java
GridView gridView = findViewById(R.id.gvJugadores);
JugadoresAdapter jugadoresAdapter = new JugadoresAdapter(equipo.getJugadores(), getApplicationContext());
gridView.setAdapter(jugadoresAdapter);
```

## 5. Reaccionar a un toque: `setOnItemClickListener`

```java
gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Jugador jugador = equipo.getJugadores().get(position);
        ...
    }
});
```
- `position` — el índice (0, 1, 2...) del elemento pulsado dentro del `ArrayList` original — así es como sabes **qué** elemento exacto se tocó.
- `view` — la vista concreta de esa fila que se pulsó (útil, por ejemplo, para transiciones con elemento compartido, ver [08-transiciones](08-transiciones.md)).

## 6. `ArrayAdapter` (para el `Spinner`)

`Spinner` (el desplegable típico "selecciona una opción") normalmente no necesita un `BaseAdapter` completo — cuando los datos son simples (un array de `String`), basta con `ArrayAdapter`, que ya trae implementados `getCount`/`getItem`/`getItemId` por ti, y solo hace falta personalizar cómo se ve cada fila:

```java
public class SpinnerDam2Adapter extends ArrayAdapter<String> {

    private String[] datos;

    public SpinnerDam2Adapter(Context context, int resource, String[] datos) {
        super(context, resource, datos);   // le pasamos los datos a la clase padre también
        this.datos = datos;
    }

    @Override
    public int getCount() {
        return datos.length + 1;    // +1: dejamos hueco para la fila "Seleccione una opción"
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return vistaPersonalizada(position, convertView, parent);   // fila cuando el spinner está CERRADO
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return vistaPersonalizada(position, convertView, parent);   // fila para cada opción del desplegable ABIERTO
    }

    private View vistaPersonalizada(int position, View convertView, ViewGroup parent) {
        View fila = LayoutInflater.from(mContext).inflate(R.layout.spinner_per, parent, false);
        TextView tvSpinner = fila.findViewById(R.id.tvSpinner);
        if (position == 0) {
            tvSpinner.setText("Selecione una Opcion");   // fila "falsa" 0, solo de aviso
            fila.setBackgroundColor(Color.BLUE);
        } else {
            tvSpinner.setText(datos[position - 1]);        // el resto son los datos reales, desplazados 1 posición
        }
        return fila;
    }
}
```

Fíjate en el "truco del +1": como se quiere que la primera fila visible sea un aviso ("Seleccione una opción") en vez de directamente el primer dato real, `getCount()` devuelve un elemento extra, y en `vistaPersonalizada` todo el acceso a `datos[]` se desplaza un índice (`datos[position - 1]`) para compensarlo.

`ArrayAdapter` tiene **dos** métodos de vista distintos porque un `Spinner` tiene dos apariencias: la fila que se ve siempre (cerrado) —`getView`— y las filas de la lista que se despliega al tocarlo —`getDropDownView`—. En este ejemplo ambas usan exactamente el mismo layout, pero podrían ser distintas.

### `setOnItemSelectedListener`

```java
cbCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (ver == 1 && position > 0) {
            Bundle bundle = new Bundle();
            bundle.putString("curso", parent.getItemAtPosition(position - 1) + "");
            intent.putExtras(bundle);
            startActivity(intent);
        }
        ver = 1;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
});
```
Este listener se dispara **también la primera vez** que se inicializa el `Spinner` (aunque el usuario no haya tocado nada todavía) — por eso hay una variable `ver` que actúa de "bandera": la primera vez que se llama a `onItemSelected` (al montar la pantalla) se ignora (`ver` pasa de 0 a 1 sin hacer nada), y solo a partir de la segunda vez (cuando `ver == 1`, es decir, cuando el usuario ya interactuó de verdad) se navega a la siguiente pantalla. Es una forma manual y algo frágil de distinguir "selección inicial automática" de "selección real del usuario" — funciona, pero hay formas más robustas (por ejemplo, comprobar si es la primera llamada con un `boolean primeraVez`).

## 6.1. El atajo para Spinners simples: `ArrayAdapter.createFromResource`

Si un `Spinner` solo necesita mostrar texto simple (sin diseño personalizado, sin colores distintos por fila, etc.), ni siquiera hace falta escribir una clase adaptador propia — Android trae un método de fábrica que construye un `ArrayAdapter` completo a partir de un `array` de recursos y una vista estándar del propio sistema:

```java
ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        this,                                          // Context
        R.array.cursos,                                 // el <string-array> de arrays.xml
        android.R.layout.simple_spinner_dropdown_item);   // layout estándar de Android (una fila de texto)
spinner.setAdapter(adapter);
```

`android.R.layout.simple_spinner_dropdown_item` es un layout que **ya viene incluido en el propio sistema Android** (por eso se referencia como `android.R....` y no `R....` — el paquete `android.R` contiene los recursos internos del framework, mientras que `R` a secas contiene los de tu propia app). Es el equivalente "sin personalizar" al `SpinnerDam2Adapter` visto en §6 — buena opción para prototipar rápido, antes de invertir tiempo en un adaptador a medida con su propio layout.

## 7. Resumen del patrón completo

```
1. Modelo de datos          → clase Java simple (Equipo, Jugador, CompaniaTelefonica...)
2. Colección de datos       → ArrayList<Modelo>
3. Layout de una fila       → item_equipo.xml / grid_item_view.xml / spinner_per.xml
4. Adapter                  → BaseAdapter (o ArrayAdapter) que convierte cada elemento en una fila
5. Vista de colección       → ListView / GridView / Spinner, con .setAdapter(miAdapter)
6. Reacción al toque        → .setOnItemClickListener (listas/grids) o .setOnItemSelectedListener (spinner)
```

## Ver también
- [13-view-viewgroup](13-view-viewgroup.md) — qué es exactamente una `View`/`ViewGroup` y el parámetro `parent` de `inflate()`.
- [14-recyclerview](14-recyclerview.md) — la evolución moderna de este mismo patrón, con reciclaje de vistas obligatorio (`ViewHolder`).
- [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) — cómo se pasa el elemento pulsado a la siguiente pantalla.
- [08-transiciones](08-transiciones.md) — animar la navegación al tocar un elemento de la lista/grid.

## 🏋️ Practica esto

- [Nivel 3 — Adaptadores](../07-ejercicios/03-nivel-3-adaptadores.md): `ListView`/`GridView` con `BaseAdapter` (3.1–3.3), `Spinner` personalizado (3.4), Glide (3.5), `RecyclerView` (3.6) y Picasso (3.7)
- [Simulacro A — Menú de adaptadores](../07-ejercicios/08-simulacros-de-examen.md)
