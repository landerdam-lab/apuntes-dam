---
tags:
  - android
  - proyecto
  - estado/incompleto
---

# Proyecto: `FragmentosNombres`

## Qué es

Otra variación del ejercicio de fragmentos del PDF [8-Fragmentos.pdf](../../pdfs/8-Fragmentos.pdf): **tres** fragmentos apilados verticalmente — `FragmentoArriba` (un campo de texto + botón "Enviar"), `FragmentoMedio` (un mini-formulario de nombre/apellido/fecha) y `FragmentoAbajo` (un `GridView` vacío, sin implementar). La idea es que el texto escrito en `FragmentoArriba` se envíe y aparezca reflejado en el campo "Nombre" de `FragmentoMedio`.

Este documento es interesante sobre todo porque el proyecto **tenía dos bugs reales** (ya corregidos en el código actual) que son un ejemplo perfecto de los errores típicos descritos en [16-fragmentos](../conceptos/16-fragmentos.md) §8 — se explican aquí con el antes/después real.

## Estructura

```
app/src/main/java/com/example/fragmentosnombres/
├── MainActivity.java                    → arranca los 3 fragmentos, implementa IControlFragmentos
├── Interfaces/
│   └── IControlFragmentos.java           → interfaz con 1 método: cambiarTexto
└── fragmentos/
    ├── FragmentoArriba.java              → EditText + botón "Enviar" (envía el texto)
    ├── FragmentoMedio.java                → formulario nombre/apellido/fecha (recibe el texto)
    └── FragmentoAbajo.java                → GridView vacío, sin adaptador — sin terminar
```

## `activity_main.xml` — tres contenedores, forma "clásica"

```xml
<LinearLayout android:id="@+id/contenedor1" android:layout_height="0dp" android:layout_weight="1" .../>
<LinearLayout android:id="@+id/contenedor2" android:layout_height="0dp" android:layout_weight="1" .../>
<LinearLayout android:id="@+id/contenedor3" android:layout_height="0dp" android:layout_weight="1" .../>
```
A diferencia de [EjemploFragmentos](EjemploFragmentos.md)/[EjercicioFragmentos](EjercicioFragmentos.md), que usan `androidx.fragment.app.FragmentContainerView`, aquí los contenedores son `LinearLayout` normales vacíos. Ambas formas funcionan igual de bien como contenedor de fragmentos — ver [16-fragmentos](../conceptos/16-fragmentos.md) §3 para la diferencia exacta entre ambas.

## `IControlFragmentos.java`

```java
public interface IControlFragmentos {
    void  cambiarTexto(String texto);
}
```
Es una **interfaz** (ver [00-programacion-basica](../conceptos/00-programacion-basica.md) §7): un contrato que obliga a `MainActivity` a tener este método. Un único método (a diferencia de los otros dos proyectos, que tienen 2), porque aquí solo hay un evento posible: el texto enviado desde `FragmentoArriba`.

## `MainActivity.java` — arranque y reenvío

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    ...
    getSupportFragmentManager().beginTransaction().add(R.id.contenedor1, new FragmentoArriba()).commit();
    getSupportFragmentManager().beginTransaction().add(R.id.contenedor2, new FragmentoMedio()).commit();
    getSupportFragmentManager().beginTransaction().add(R.id.contenedor3, new FragmentoAbajo()).commit();
}

@Override
public void cambiarTexto(String texto) {
    Bundle bundle = new Bundle();
    bundle.putString("nombre", texto);
    getSupportFragmentManager().beginTransaction()
            .replace(R.id.contenedor2, FragmentoMedio.newInstance(bundle))
            .commit();
}
```
Tres transacciones independientes en `onCreate` (una por fragmento) — funcionalmente correcto, aunque podrían agruparse en una sola transacción con tres `.add(...)` encadenados antes de un único `.commit()` final, evitando así tres operaciones de `FragmentManager` separadas para lo que en realidad es "un único estado inicial de pantalla". No es un bug, solo una posible micro-mejora de estilo.

## Bug 1 (ya corregido): `findViewById` apuntando a un id que no existía

**Antes:**
```java
// FragmentoArriba.java
private EditText etNombre;   // ← campo mal nombrado

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    ...
    etNombre = view.findViewById(R.id.etNombre);   // ← R.id.etNombre NO EXISTE en este layout
    ...
    btnEnviar.setOnClickListener(v -> mainActivity.cambiarTexto(etNombre.getText().toString()));
}
```
El layout `fragment_fragmento_arriba.xml` solo define `android:id="@+id/etTexto"` — no existe ningún `etNombre` en ese fichero. `findViewById` con un id que no existe en el layout inflado **no lanza ningún error en el momento**: simplemente devuelve `null`. El fallo aparecía después, al pulsar "Enviar", con un `NullPointerException` en `etNombre.getText()`.

**Después (código actual):**
```java
private EditText etTexto;
...
etTexto = view.findViewById(R.id.etTexto);
...
btnEnviar.setOnClickListener(v -> mainActivity.cambiarTexto(etTexto.getText().toString()));
```
Se renombró el campo y se apuntó al id correcto del layout (`R.id.etTexto`). Este es exactamente el error típico #1 descrito en [16-fragmentos](../conceptos/16-fragmentos.md) §8: **siempre** comprobar que el id buscado en Java existe tal cual en el XML inflado por ese fragmento concreto.

## Bug 2 (ya corregido): clave del `Bundle` vacía, no coincidía con la que se leía

**Antes:**
```java
// MainActivity.cambiarTexto(...)
Bundle bundle = new Bundle();
bundle.putString("", texto);   // ← clave vacía ""
```
```java
// FragmentoMedio.onViewCreated(...)
Bundle argumentos = getArguments();
// (no se leía nada del bundle en absoluto en la primera versión)
```
Dos problemas encadenados: primero, `FragmentoMedio` obtenía el `Bundle` pero no llegaba a usarlo para nada (el `EditText` de nombre nunca se actualizaba). Al corregir eso, apareció el segundo problema: `MainActivity` guardaba el texto con una clave vacía (`""`) en vez de una clave con nombre.

**Después (código actual):**
```java
// MainActivity.cambiarTexto(...)
Bundle bundle = new Bundle();
bundle.putString("nombre", texto);
```
```java
// FragmentoMedio.onViewCreated(...)
Bundle argumentos = getArguments();
if (argumentos != null) {
    etNombre.setText(argumentos.getString("nombre"));
}
```
Ahora la clave `"nombre"` coincide en los dos lados y `FragmentoMedio` sí actualiza su `EditText` de nombre con el texto recibido. Este es el error típico #2 de [16-fragmentos](../conceptos/16-fragmentos.md) §8: una clave de `Bundle` que no coincide entre quien escribe y quien lee no lanza ningún error — simplemente el dato nunca llega (`getString(...)` devuelve `null` en silencio).

## `FragmentoMedio.java` — formulario que recibe el texto

```java
public class FragmentoMedio extends Fragment {
    private EditText etNombre, etApellido, etFecha;

    public static FragmentoMedio newInstance(Bundle argumentos) {
        FragmentoMedio fragmentoMedio = new FragmentoMedio();
        if (argumentos != null) fragmentoMedio.setArguments(argumentos);
        return fragmentoMedio;
    }

    @Override
    public View onCreateView(...) {
        View vista = inflater.inflate(R.layout.fragment_fragmento_medio, container, false);
        etNombre = vista.findViewById(R.id.etNombre);
        etApellido = vista.findViewById(R.id.etApellido);
        etFecha = vista.findViewById(R.id.etFecha);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle argumentos = getArguments();
        if (argumentos != null) {
            etNombre.setText(argumentos.getString("nombre"));
        }
    }
}
```
`newInstance(...)` es un **método estático** (ver [00-programacion-basica](../conceptos/00-programacion-basica.md) §10): se llama sobre la clase (`FragmentoMedio.newInstance(bundle)`), sin necesitar un objeto ya creado — construye uno nuevo y se lo devuelve. Nótese que aquí los `findViewById` se hacen dentro de `onCreateView` (no en `onViewCreated`, como en el resto de fragmentos del curso) — funciona igual, porque en ese punto la vista ya está inflada (`vista.findViewById(...)`, sobre la variable local, no sobre el parámetro `view` de `onViewCreated`), es solo una elección de estilo distinta. `etApellido`/`etFecha` no reciben ningún dato del `Bundle` — el formulario permite escribirlos a mano, pero no hay ningún fragmento ni botón en este proyecto que los lea o los envíe a ningún sitio.

## `FragmentoAbajo.java` — plantilla sin completar

```java
public class FragmentoAbajo extends Fragment {
    // TODO: Rename parameter arguments...
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ...
    public static FragmentoAbajo newInstance(String param1, String param2) { ... }

    @Override
    public View onCreateView(...) {
        return inflater.inflate(R.layout.fragment_fragmento_abajo, container, false);
    }
}
```
Es literalmente la plantilla que genera Android Studio al crear un "Blank Fragment" (`new -> Fragment -> Fragment (Blank)`), sin editar: los comentarios `// TODO: Rename...` y los parámetros genéricos `param1`/`param2` son el código de plantilla, no algo escrito a propósito para este ejercicio. `fragment_fragmento_abajo.xml` solo contiene un `GridView` sin `id` y sin `numColumns`, así que tampoco hay forma de buscarlo desde Java ni de asignarle un adaptador. Es el "hueco" que falta rellenar para que el ejercicio use sus tres fragmentos con sentido — por ejemplo, mostrando ahí una rejilla acumulada de fichas, al estilo de `Fragmento3` en [EjercicioFragmentos](EjercicioFragmentos.md), que es el patrón ya resuelto a seguir.

## Resumen de lo que falta para terminar el ejercicio

1. Darle uso real a `FragmentoAbajo`: ponerle un `id` al `GridView` del layout, crear un adaptador (siguiendo el patrón de `FichaAdapter` en [EjercicioFragmentos](EjercicioFragmentos.md)) y conectarlo desde `onViewCreated`.
2. Decidir qué hacer con `etApellido`/`etFecha` de `FragmentoMedio`: si el objetivo final es acumular fichas completas (nombre+apellido+fecha) en `FragmentoAbajo`, haría falta un botón en `FragmentoMedio` que envíe esos tres campos a `MainActivity` (con un segundo método en `IControlFragmentos`, similar a `onApellidoYFechaCompletado` de `EjercicioFragmentos`).
3. Renombrar `FragmentoAbajo.newInstance(String, String)` y sus `ARG_PARAM1`/`ARG_PARAM2` de plantilla a nombres con sentido para este ejercicio, una vez decidido qué datos va a recibir de verdad.

## Ver también
- [16-fragmentos](../conceptos/16-fragmentos.md) — explicación general de todas las técnicas usadas aquí, incluyendo los dos errores típicos que este proyecto tenía.
- [EjercicioFragmentos](EjercicioFragmentos.md) — versión ya terminada del patrón "formulario multi-fragmento + rejilla acumulada" que le falta a `FragmentoAbajo` aquí.
- [EjemploFragmentos](EjemploFragmentos.md) — la versión de 2 fragmentos más simple, útil para comparar el patrón `IControlFragmentos` mínimo.
