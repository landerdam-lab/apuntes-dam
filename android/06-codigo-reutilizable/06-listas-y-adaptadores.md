---
tags:
  - android
  - reutilizable
aliases:
  - Listas
  - Adaptadores
  - ViewHolder
---

# 06 — Listas y adaptadores

> Teoría: [07 — Adaptadores](../02-conceptos/07-adaptadores.md) · [14 — RecyclerView](../02-conceptos/14-recyclerview.md)

## 1. Modelo de datos ✅ Reutilizable
```java
public class Elemento implements Serializable {       // Serializable si lo vas a pasar con putExtra
    private String titulo;
    private String subtitulo;
    private int imagen;                               // R.drawable.xxx; usa String si es una URL
    public Elemento(String titulo, String subtitulo, int imagen) {
        this.titulo = titulo; this.subtitulo = subtitulo; this.imagen = imagen;
    }
    public String getTitulo() { return titulo; }
    public String getSubtitulo() { return subtitulo; }
    public int getImagen() { return imagen; }
}
```
Truco: en Android Studio, *Alt+Insert → Constructor / Getter and Setter* los genera.

## 2. `BaseAdapter` con ViewHolder (versión mejorada del de los proyectos) ✅ Reutilizable
**Aparece (sin ViewHolder) en:** `CompaniasAdapter`, `EquiposAdapter`, `PokemonAdapter`, `AdaptadorPersona`.
```java
public class ElementosAdapter extends BaseAdapter {
    private final Context context;
    private List<Elemento> datos;

    public ElementosAdapter(Context context, List<Elemento> datos) {
        this.context = context; this.datos = datos;
    }

    @Override public int getCount() { return datos.size(); }
    @Override public Elemento getItem(int position) { return datos.get(position); }
    @Override public long getItemId(int position) { return position; }

    // Guarda las vistas de una fila para no buscarlas cada vez
    private static class ViewHolder { ImageView iv; TextView tvTitulo, tvSub; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView == null) {                              // solo se infla si no hay fila reciclable
            convertView = LayoutInflater.from(context).inflate(R.layout.item_elemento, parent, false);
            h = new ViewHolder();
            h.iv = convertView.findViewById(R.id.ivIcono);
            h.tvTitulo = convertView.findViewById(R.id.tvTitulo);
            h.tvSub = convertView.findViewById(R.id.tvSubtitulo);
            convertView.setTag(h);                              // se guarda en la propia fila
        } else {
            h = (ViewHolder) convertView.getTag();
        }
        Elemento e = datos.get(position);
        h.iv.setImageResource(e.getImagen());
        h.tvTitulo.setText(e.getTitulo());
        h.tvSub.setText(e.getSubtitulo());
        return convertView;
    }

    public void actualizar(List<Elemento> nuevos) {             // para usar con LiveData
        this.datos = nuevos;
        notifyDataSetChanged();
    }
}
```
**Uso:**
```java
ListView lv = findViewById(R.id.lvElementos);          // o GridView (+ android:numColumns en el XML)
ElementosAdapter adapter = new ElementosAdapter(this, lista);
lv.setAdapter(adapter);
lv.setOnItemClickListener((p, v, pos, id) -> abrirDetalle(adapter.getItem(pos)));
```

## 3. Spinner con adaptador propio y fila de aviso ✅ Reutilizable
**Aparece en:** [AdapterDam2](../04-proyectos-profesor/AdapterDam2.md), [EjercicioSpinner](../04-proyectos-profesor/EjercicioSpinner.md). Versión limpia **sin el truco del +1**: el aviso va dentro de los datos.
```java
List<String> opciones = new ArrayList<>();
opciones.add("Selecciona una opción");                 // posición 0 = aviso
opciones.addAll(Arrays.asList(getResources().getStringArray(R.array.cursos)));

ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.spinner_per, R.id.tvSpinner, opciones) {
    @Override
    public View getDropDownView(int pos, View cv, @NonNull ViewGroup parent) {
        View v = super.getDropDownView(pos, cv, parent);       // reutiliza la fila y pone el texto
        v.setBackgroundColor(pos == 0 ? Color.LTGRAY : Color.TRANSPARENT);
        return v;
    }
};
spinner.setAdapter(ad);
spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
        if (pos == 0) return;                               // ignora el aviso (y la selección inicial)
        abrirDetalle(opciones.get(pos));
        spinner.setSelection(0);                            // vuelve al aviso: se puede elegir lo mismo otra vez
    }
    @Override public void onNothingSelected(AdapterView<?> p) { }
});
```
Con esto sobra la variable `ver` de los proyectos: la posición 0 **es** el aviso.

Si solo necesitas textos, sin diseño propio:
```java
spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opciones));
```

## 4. `RecyclerView` ✅ Reutilizable
**Aparece en:** [aaaa](../04-proyectos-profesor/aaaa.md) (`ListAdapter`). Dependencia: `implementation("androidx.recyclerview:recyclerview:1.3.2")`.
```java
public class ElementosRVAdapter extends RecyclerView.Adapter<ElementosRVAdapter.Holder> {
    public interface OnClick { void onClick(Elemento e); }
    private final List<Elemento> datos;
    private final OnClick onClick;

    public ElementosRVAdapter(List<Elemento> datos, OnClick onClick) { this.datos = datos; this.onClick = onClick; }

    static class Holder extends RecyclerView.ViewHolder {
        final ImageView iv; final TextView tvTitulo;
        Holder(View v) { super(v); iv = v.findViewById(R.id.ivIcono); tvTitulo = v.findViewById(R.id.tvTitulo); }
    }

    @NonNull @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {      // pocas veces
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_elemento, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int position) {                    // en cada scroll
        Elemento e = datos.get(position);
        h.iv.setImageResource(e.getImagen());
        h.tvTitulo.setText(e.getTitulo());
        h.itemView.setOnClickListener(v -> onClick.onClick(e));     // RecyclerView NO tiene setOnItemClickListener
    }

    @Override public int getItemCount() { return datos.size(); }
}

// En la Activity:
RecyclerView rv = findViewById(R.id.rvElementos);
rv.setLayoutManager(new LinearLayoutManager(this));      // lista · GridLayoutManager(this, 3) = rejilla
rv.setAdapter(new ElementosRVAdapter(lista, e -> abrirDetalle(e)));
```

## 5. Filas en cebra ✅ Reutilizable
**Aparece en:** `CompaniasAdapter`.
```java
fila.setBackgroundColor(position % 2 == 0 ? Color.GRAY : Color.LTGRAY);
```

## Qué cambiar
`Elemento` → tu modelo · `item_elemento` y sus ids → tu layout de fila · `R.array.cursos` → tu array.
