---
tags:
  - android
  - proyecto
  - estado/completo
---

# Proyecto: `EjercicioFragmentos`

## Qué es

El ejercicio más completo de los tres proyectos de fragmentos: **tres** fragmentos encadenados que forman un mini-formulario de "ficha de persona" — `Fragmento1` pide un nombre, `Fragmento2` pide apellido y fecha (y ya trae el nombre precargado desde `Fragmento1`), y `Fragmento3` acumula todas las fichas completadas y las muestra en una rejilla (`GridView`). Es el único de los tres que pasa **objetos completos** (no solo texto) entre fragmentos, usando `Serializable`. Está completo y funcional — es el ejemplo a seguir si quieres ver el patrón de fragmentos "a máxima complejidad" dentro de este curso.

## Estructura

```
app/src/main/java/com/example/ejerciciofragmentos/
├── MainActivity.java              → arranca los 3 fragmentos, implementa IControlFragmentos, acumula las Persona
├── IControlFragmentos.java        → interfaz con 2 métodos: onNombreCompletado, onApellidoYFechaCompletado
├── modelos/
│   └── Persona.java                → POJO Serializable: nombre, apellido, fecha
├── adaptadores/
│   └── FichaAdapter.java           → BaseAdapter para el GridView de Fragmento3
└── fragmentos/
    ├── Fragmento1.java              → EditText nombre + botón "Crear"
    ├── Fragmento2.java              → EditText apellido/fecha + botón "Ficha" (nombre precargado)
    └── Fragmento3.java              → GridView con todas las fichas ya completadas
```

## El flujo completo

```
Fragmento1 (nombre) --onNombreCompletado--> MainActivity --replace--> Fragmento2 (con "nombre" precargado)
Fragmento2 (apellido+fecha) --onApellidoYFechaCompletado--> MainActivity --crea Persona, la añade a la lista--> Fragmento3 (replace con la lista completa)
```

`Fragmento1` y `Fragmento2` están siempre visibles a la vez (en `contenedor1` y `contenedor2`); `Fragmento3` (en `contenedor3`) va acumulando cada ficha nueva en una rejilla, sin borrar las anteriores.

## `IControlFragmentos.java`

```java
public interface IControlFragmentos {
    void onNombreCompletado(String nombre);
    void onApellidoYFechaCompletado(String nombre, String apellido, String fecha);
}
```
A diferencia de [EjemploFragmentos](EjemploFragmentos.md) (métodos genéricos `cambiarTexto`/`cambiarColor`), aquí los nombres de los métodos describen el **evento de negocio** ("se completó el nombre", "se completaron apellido y fecha") en vez de la acción técnica sobre la interfaz — un estilo más cercano al patrón *callback* típico de formularios multi-paso. Ver el patrón general de interfaz Fragment↔Activity en [16-fragmentos](../conceptos/16-fragmentos.md) §5.

## `modelos/Persona.java` — el POJO `Serializable`

```java
public class Persona implements Serializable {
    private String nombre, apellido, fecha;

    public Persona(String nombre, String apellido, String fecha) { ... }
    // getters/setters
}
```
Un **POJO** ("Plain Old Java Object") es una clase sencilla que solo guarda datos (atributos) más su constructor y sus getters/setters, sin ninguna lógica de interfaz — ver [00-programacion-basica](../conceptos/00-programacion-basica.md) §5 para qué es una clase/objeto/atributo/constructor si no lo tienes claro. Implementa `Serializable` porque es el único proyecto de fragmentos que necesita meter **objetos propios completos** dentro de un `Bundle` (no solo `String`/`int` sueltos) — ver [16-fragmentos](../conceptos/16-fragmentos.md) §7 para el porqué.

## `Fragmento1.java` — el primer paso, con validación

```java
btnCrear.setOnClickListener(v -> {
    String nombre = etNombre.getText().toString().trim();
    if (nombre.isEmpty()) {
        etNombre.setError("Introduce un nombre");
        return;
    }
    activity.onNombreCompletado(nombre);
});
```
`.trim()` quita espacios en blanco al principio/final antes de comprobar `.isEmpty()` (para que escribir solo espacios no cuente como "nombre válido"). `etNombre.setError(...)` es el método estándar de `EditText` para mostrar un mensaje de error con el icono rojo característico de Material Design, sin necesidad de un `Toast` ni un diálogo aparte.

```java
@Override
public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if (context instanceof IControlFragmentos) {
        activity = (IControlFragmentos) context;
    } else {
        throw new RuntimeException(context + " debe implementar IControlFragmentos");
    }
}

@Override
public void onDetach() {
    super.onDetach();
    activity = null;
}
```
Este proyecto es el único de los tres que hace **ambas** cosas explicadas en [16-fragmentos](../conceptos/16-fragmentos.md) §5: comprobar el cast con `instanceof` (mensaje de error claro si la Activity no implementa la interfaz) y liberar la referencia en `onDetach()` (evita fugas de memoria). `Fragmento2.java` repite exactamente el mismo patrón.

## `Fragmento2.java` — recibe el nombre, pide el resto

```java
public static Fragmento2 newInstance(Bundle bundle) {
    Fragmento2 fragmento2 = new Fragmento2();
    if (bundle != null) fragmento2.setArguments(bundle);
    return fragmento2;
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    EditText etNombre = view.findViewById(R.id.etNombre);
    ...
    Bundle bundle = getArguments();
    if (bundle != null && bundle.containsKey("nombre")) {
        etNombre.setText(bundle.getString("nombre"));
    }

    btnFicha.setOnClickListener(v -> {
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String fecha = etFecha.getText().toString().trim();
        if (apellido.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(getContext(), "Completa apellido y fecha", Toast.LENGTH_SHORT).show();
            return;
        }
        activity.onApellidoYFechaCompletado(nombre, apellido, fecha);
    });
}
```
El `EditText` de nombre en `Fragmento2` **no es el mismo objeto** que el de `Fragmento1` — es un campo distinto, en un layout distinto, que simplemente se **precarga** con el valor recibido por `Bundle` (`etNombre.setText(bundle.getString("nombre"))`) para que el usuario no tenga que volver a escribirlo, pero sigue siendo editable. Aquí la validación usa `Toast` en vez de `setError(...)` porque afecta a dos campos a la vez (apellido + fecha), no a uno solo.

Hay código comentado (`//private String nombre;` y un constructor alternativo comentado) que corresponde a un primer intento — descartado en favor del patrón `newInstance(Bundle)` correcto, tal y como se explica en [16-fragmentos](../conceptos/16-fragmentos.md) §6 sobre por qué no usar un constructor con parámetros.

## `Fragmento3.java` — acumula y muestra en rejilla

```java
@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    GridView gridFicha = view.findViewById(R.id.gridFicha);
    if (getArguments().containsKey("personas")) {
        FichaAdapter adapter = new FichaAdapter(this.mContext, (ArrayList<Persona>) getArguments().getSerializable("personas"));
        gridFicha.setAdapter(adapter);
    }
}
```
El `(ArrayList<Persona>)` delante de `getArguments().getSerializable(...)` es un **cast** (ver [00-programacion-basica](../conceptos/00-programacion-basica.md) §12): `getSerializable(...)` solo puede devolver un tipo genérico, así que hay que decirle a Java el tipo real que hay dentro.

**Punto frágil** (ver [16-fragmentos](../conceptos/16-fragmentos.md) §8, punto 5): aquí se llama a `getArguments()` **sin comprobar que no sea `null`** — a diferencia de `Fragmento1`/`Fragmento2` del mismo proyecto, que sí comprueban `bundle != null` antes de usarlo. En este proyecto no llega a fallar porque `MainActivity` **siempre** crea `Fragmento3` con `Fragmento3.newInstance(bundle)` (nunca con `new Fragmento3()` a secas, ni siquiera la primera vez — le pasa un `Bundle` vacío), así que `getArguments()` nunca es `null` en la práctica. Aun así, es una inconsistencia frente al resto del proyecto que convendría corregir añadiendo el mismo `if (getArguments() != null)` que usan los otros dos fragmentos.

## `adaptadores/FichaAdapter.java` — `BaseAdapter` clásico

```java
@Override
public View getView(int position, View convertView, ViewGroup parent) {
    View vista = convertView;
    if (vista == null) {
        vista = inflater.inflate(R.layout.grid_item_view, parent, false);
    }
    TextView tvNombre = vista.findViewById(R.id.tvNombre);
    ...
    Persona persona = listaPersonas.get(position);
    tvNombre.setText(persona.getNombre());
    ...
    return vista;
}
```
Sigue el patrón estándar `BaseAdapter` con reciclaje de vistas (`if (convertView == null)`) explicado en detalle en [07-adaptadores](../conceptos/07-adaptadores.md) — la única diferencia con los adaptadores de listas de ese documento es que aquí el `parent` es un `GridView` (`android:numColumns="2"` en `fragment_fragmento3.xml`) en vez de un `ListView`, pero el contrato de `BaseAdapter` (`getCount`/`getItem`/`getItemId`/`getView`) es idéntico en ambos casos.

## `MainActivity.java` — el pegamento

```java
private ArrayList<Persona> personas = new ArrayList<>();

@Override
protected void onCreate(Bundle savedInstanceState) {
    ...
    getSupportFragmentManager().beginTransaction().add(R.id.contenedor1, new Fragmento1()).commit();
    getSupportFragmentManager().beginTransaction().add(R.id.contenedor2, new Fragmento2()).commit();
    getSupportFragmentManager().beginTransaction().add(R.id.contenedor3, Fragmento3.newInstance(new Bundle())).commit();
}

@Override
public void onNombreCompletado(String nombre) {
    Bundle bundle = new Bundle();
    bundle.putString("nombre", nombre);
    getSupportFragmentManager().beginTransaction()
            .replace(R.id.contenedor2, Fragmento2.newInstance(bundle))
            .commit();
}

@Override
public void onApellidoYFechaCompletado(String nombre, String apellido, String fecha) {
    Bundle bundle = new Bundle();
    Persona persona = new Persona(nombre, apellido, fecha);
    personas.add(persona);                       // se acumula, no se reemplaza
    bundle.putSerializable("personas", personas); // se manda la LISTA COMPLETA cada vez
    getSupportFragmentManager().beginTransaction()
            .replace(R.id.contenedor3, Fragmento3.newInstance(bundle))
            .commit();
}
```
La lista `personas` (un `ArrayList`, ver [00-programacion-basica](../conceptos/00-programacion-basica.md) §8) vive como campo de `MainActivity` (sobrevive mientras la Activity no se destruya) y **crece** con cada ficha nueva; cada vez que se completa una ficha, se manda la lista **entera** actualizada a `Fragmento3` (no solo la ficha nueva), porque `Fragmento3` se reconstruye desde cero con `.replace(...)` y no tiene memoria propia de las fichas anteriores.

## Ver también
- [16-fragmentos](../conceptos/16-fragmentos.md) — el detalle de cada técnica usada aquí (`newInstance`, `Serializable`, `instanceof`, `onDetach`).
- [07-adaptadores](../conceptos/07-adaptadores.md) — el patrón `BaseAdapter` que usa `FichaAdapter`.
- [EjemploFragmentos](EjemploFragmentos.md) — la versión más simple (2 fragmentos, sin objetos complejos) de este mismo patrón.
