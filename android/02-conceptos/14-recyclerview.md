---
tags:
  - android
  - concepto
---

# `RecyclerView` (la evolución moderna de `ListView`/`GridView`)

Ninguno de tus proyectos (`codigo/proyectos-alumno/`) implementa `RecyclerView` — todos usan `ListView`/`GridView` con `BaseAdapter` (ver [07-adaptadores](07-adaptadores.md)), que es la forma "clásica" y totalmente válida de mostrar listas. El único ejemplo real de `RecyclerView` está en la plantilla del profesor [aaaa](../04-proyectos-profesor/aaaa.md) (`TransformFragment`, con `ListAdapter` + `DiffUtil` + View Binding). Pero `RecyclerView` es, desde hace años, el estándar recomendado por Google para listas y rejillas, así que conviene entender qué cambia y por qué, sobre todo porque resuelve **exactamente** el problema de rendimiento señalado en [07-adaptadores](07-adaptadores.md) §3 (el `convertView` que tus adaptadores ignoran).

> **Esto no es una extrapolación mía**: el material del curso (`6-Adaptadores.pdf`) incluye literalmente este mismo ejercicio como cuarto apartado, justo después de `ListView`/`Spinner`/`GridView` — un botón adicional "GridView (Recycler)" que abre una `GridRecyclerDam` con un `RecyclerView` + `GridLayoutManager(this, 3)`, usando la dependencia `androidx.recyclerview:recyclerview:1.3.2`. Los ejemplos de este documento siguen esa misma estructura, adaptados al `CompaniasAdapter` de tus proyectos reales para que puedas comparar línea a línea con algo que ya conoces. El propio material del curso resume la idea así: *"Los componentes ListView, GridView y Spinner siguen siendo válidos y funcionan perfectamente, pero en desarrollo profesional actual las listas y cuadrículas se construyen con RecyclerView (...) El concepto de adaptador que se ha aprendido aquí es exactamente el mismo; RecyclerView solo añade una capa de optimización y estructura."*

## 1. El problema que `RecyclerView` resuelve

Con `ListView`, el **reciclaje de vistas** (reutilizar una fila que salió de la pantalla, en vez de crear una nueva) es **opcional** y manual (el parámetro `convertView` de `getView`, que en tus adaptadores nunca se usa). Con `RecyclerView`, el reciclaje es **obligatorio y automático**: la propia clase base te obliga a estructurar el código de forma que el reciclaje SIEMPRE ocurra correctamente — de ahí el nombre, *Recycler*View.

## 2. Las tres piezas de `RecyclerView` (en vez de una sola `BaseAdapter`)

| Pieza | Rol | Equivalente en `ListView`/`GridView` |
|---|---|---|
| `RecyclerView.Adapter` | Crea y rellena las vistas | `BaseAdapter` |
| `RecyclerView.ViewHolder` | Guarda las referencias (`findViewById`) de UNA fila, para no repetir la búsqueda | No existe como clase — en tus adaptadores, `findViewById` se llama cada vez dentro de `getView` |
| `LayoutManager` | Decide CÓMO se organizan las filas: en columna (`LinearLayoutManager`), en rejilla (`GridLayoutManager`), en cascada (`StaggeredGridLayoutManager`) | Es "fijo": lo decide la clase (`ListView` = columna, `GridView` = rejilla con `numColumns`) |

## 3. Ejemplo comparado: reescribiendo `CompaniasAdapter` (de `AdapterDam2`) como `RecyclerView`

### La versión actual (`BaseAdapter`, ver `AdapterDam2/adaptadores/CompaniasAdapter.java`)

```java
public class CompaniasAdapter extends BaseAdapter {
    private ArrayList<CompaniaTelefonica> companiaTelefonicas;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View fila = inflater.inflate(R.layout.compania_telefonica_list, parent, false);

        ImageView ivLogo = fila.findViewById(R.id.iconoTelefono);      // findViewById en CADA llamada
        TextView tvTelegono = fila.findViewById(R.id.tvNombreTelefono);
        TextView tvPrecio = fila.findViewById(R.id.tvPrecioTelefono);

        CompaniaTelefonica compania = companiaTelefonicas.get(position);
        ivLogo.setImageResource(compania.getLogo());
        tvTelegono.setText(compania.getNombre());
        tvPrecio.setText(compania.getPrecio()+"");
        return fila;
    }
    // getCount(), getItem(), getItemId()...
}
```

### La misma lista con `RecyclerView.Adapter` + `ViewHolder`

```java
public class CompaniasAdapter extends RecyclerView.Adapter<CompaniasAdapter.CompaniaViewHolder> {

    private ArrayList<CompaniaTelefonica> companiaTelefonicas;

    public CompaniasAdapter(ArrayList<CompaniaTelefonica> companiaTelefonicas) {
        this.companiaTelefonicas = companiaTelefonicas;
    }

    // 1. Se llama SOLO cuando hace falta crear una fila NUEVA de verdad (no cada vez que se ve una fila)
    @NonNull
    @Override
    public CompaniaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View fila = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.compania_telefonica_list, parent, false);
        return new CompaniaViewHolder(fila);   // el ViewHolder ya hace los findViewById UNA sola vez (ver abajo)
    }

    // 2. Se llama cada vez que una fila (nueva o reciclada) necesita mostrar unos datos concretos
    @Override
    public void onBindViewHolder(@NonNull CompaniaViewHolder holder, int position) {
        CompaniaTelefonica compania = companiaTelefonicas.get(position);
        holder.ivLogo.setImageResource(compania.getLogo());
        holder.tvNombre.setText(compania.getNombre());
        holder.tvPrecio.setText(compania.getPrecio() + "");
    }

    // 3. Cuántos elementos hay (equivalente a getCount())
    @Override
    public int getItemCount() {
        return companiaTelefonicas.size();
    }

    // 4. El ViewHolder: busca las vistas de la fila UNA sola vez, en su constructor
    static class CompaniaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLogo;
        TextView tvNombre;
        TextView tvPrecio;

        public CompaniaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.iconoTelefono);      // findViewById solo AQUÍ, una vez por fila reciclada
            tvNombre = itemView.findViewById(R.id.tvNombreTelefono);
            tvPrecio = itemView.findViewById(R.id.tvPrecioTelefono);
        }
    }
}
```

### La diferencia clave, explicada

- **`getView(...)`** (una sola función que hace inflar + buscar vistas + rellenar, todo junto, cada vez) se divide en **dos** funciones separadas:
  - `onCreateViewHolder` — solo se llama cuando hace falta una fila **físicamente nueva** (las primeras que llenan la pantalla, o cuando el usuario hace scroll más rápido de lo que se pueden reciclar filas existentes). Aquí es donde se infla el XML y se buscan las vistas (`findViewById`), **una sola vez por cada fila física que llegue a existir en memoria**.
  - `onBindViewHolder` — se llama **cada vez** que una fila (nueva o reciclada) tiene que mostrar los datos de una posición concreta. Aquí solo se asignan valores (`setText`, `setImageResource`) sobre las vistas que el `ViewHolder` ya tiene guardadas — **sin volver a buscar (`findViewById`) nada**.
- El **`ViewHolder`** es literalmente lo que su nombre indica: "sostiene las vistas" de una fila, para no tener que volver a buscarlas cada vez que esa fila se reutiliza. En una lista con scroll, `RecyclerView` normalmente solo llega a crear (`onCreateViewHolder`) tantas filas físicas como caben en pantalla + un par extra de margen — el resto de veces que hace falta mostrar una fila (al seguir haciendo scroll), reutiliza (recicla) esas mismas pocas instancias, llamando solo a `onBindViewHolder` con los nuevos datos. Esto es exactamente la optimización de `convertView` que en [07-adaptadores](07-adaptadores.md) §3 se señala como **ausente** en tus adaptadores actuales — con `RecyclerView` es imposible "olvidarla", porque la propia estructura de clases te obliga a separar creación de relleno.

## 4. Cómo se conecta en la Activity (comparado con `ListView`)

### Con `ListView` (lo que hacen tus proyectos)

```java
ListView listaCompanias = findViewById(R.id.lvTelefonica);
listaCompanias.setAdapter(new CompaniasAdapter(this, companiaTelefonicas));
```

### Con `RecyclerView`

```java
RecyclerView recyclerCompanias = findViewById(R.id.rvTelefonica);
recyclerCompanias.setLayoutManager(new LinearLayoutManager(this));   // ← decide "en columna" (equivale a ListView)
recyclerCompanias.setAdapter(new CompaniasAdapter(companiaTelefonicas));
```

Para una **rejilla** (equivalente a `GridView`), solo cambia el `LayoutManager`:
```java
recyclerJugadores.setLayoutManager(new GridLayoutManager(this, 2));   // 2 columnas, como android:numColumns="2"
```

Y en el XML, en vez de `<ListView>`/`<GridView>`, se declara:
```xml
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/rvTelefonica"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
(requiere añadir la dependencia `androidx.recyclerview:recyclerview` en `build.gradle`, que no está incluida en ninguno de tus proyectos actuales).

## 5. Clic sobre un elemento — ya no hay `setOnItemClickListener`

`RecyclerView` **no tiene** un equivalente directo a `setOnItemClickListener` — hay que añadir el listener de clic **tú mismo**, sobre la vista raíz del `ViewHolder`, normalmente dentro de `onBindViewHolder` o del constructor del `ViewHolder`:

```java
@Override
public void onBindViewHolder(@NonNull CompaniaViewHolder holder, int position) {
    CompaniaTelefonica compania = companiaTelefonicas.get(position);
    ...
    holder.itemView.setOnClickListener(v -> {
        // equivalente al onItemClick(parent, view, position, id) de ListView
        Intent intent = new Intent(v.getContext(), DetalleActivity.class);
        intent.putExtra("nombre", compania.getNombre());
        v.getContext().startActivity(intent);
    });
}
```
Es un poco más de código que `setOnItemClickListener`, pero a cambio es más flexible (por ejemplo, permite tener clics distintos sobre distintas partes de la misma fila, como "un botón de borrar" dentro de cada elemento).

## 6. Actualizar los datos: `notifyDataSetChanged()` y variantes

Con `BaseAdapter`, si cambias la lista de datos (añades, quitas o modificas elementos), tienes que avisar al adaptador:
```java
adapter.notifyDataSetChanged();   // "algo cambió, vuelve a dibujar todo"
```
`RecyclerView.Adapter` hereda el mismo método, pero además ofrece versiones más específicas y eficientes, que evitan redibujar TODA la lista cuando solo cambió una parte:
```java
adapter.notifyItemInserted(position);   // se añadió un elemento en esa posición
adapter.notifyItemRemoved(position);     // se quitó un elemento de esa posición
adapter.notifyItemChanged(position);      // cambió el contenido de un elemento concreto
```
Usar las versiones específicas permite además que `RecyclerView` anime automáticamente el cambio (un elemento que aparece con un fundido, uno que desaparece deslizándose, etc.), algo que `ListView` no ofrece de fábrica.

## 7. Resumen: ¿merece la pena cambiar tus proyectos a `RecyclerView`?

Para listas cortas y estáticas como las de tus ejercicios (unos pocos equipos, compañías o jugadores, que no cambian mientras la app está abierta), `ListView`/`GridView` con `BaseAdapter` es perfectamente correcto y más simple de leer — no hay ninguna necesidad práctica de migrar. `RecyclerView` demuestra su ventaja en listas **largas y/o dinámicas** (cientos de elementos, con scroll fluido, insertados/eliminados en tiempo real) — es el estándar en apps profesionales de hoy en día, pero para el nivel y tamaño de estos ejercicios, entender bien `BaseAdapter` (como ya haces) es la base perfecta para dar el salto a `RecyclerView` el día que haga falta.

## Ver también
- [07-adaptadores](07-adaptadores.md) — el patrón `BaseAdapter`/`ArrayAdapter` usado en todos tus proyectos actuales.
- [13-view-viewgroup](13-view-viewgroup.md) — la base (`View`/`ViewGroup`, `LayoutInflater`) sobre la que se construye tanto `BaseAdapter` como `RecyclerView.Adapter`.
