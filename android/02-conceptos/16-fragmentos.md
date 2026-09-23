---
tags:
  - android
  - concepto
---

# Fragmentos

## 1. Qué es un `Fragment` y por qué se usa

Un `Fragment` es un **trozo reutilizable de interfaz y de lógica** que vive dentro de una `Activity`. En vez de construir una pantalla entera en una sola `Activity`, la divides en piezas independientes (una cabecera, un formulario, una lista...) que se pueden combinar, sustituir o reutilizar en distintas pantallas sin duplicar código.

La ventaja frente a tener varias `Activity` (ver [10-activity-en-profundidad](10-activity-en-profundidad.md)) es que cambiar de un fragmento a otro **no crea ni destruye una `Activity` nueva** — es mucho más barato, y permite, por ejemplo, mostrar dos o tres fragmentos a la vez en la misma pantalla (algo que con `Activity` no es posible).

En los proyectos del curso, `MainActivity` normalmente no dibuja nada por sí misma: solo define "huecos" (contenedores) en su layout, y cada fragmento se encarga de rellenar uno de esos huecos con su propia interfaz y su propio comportamiento.

## 2. El ciclo de vida de un `Fragment`

Un `Fragment` **no tiene** `onCreate`→`onStart`→`onResume` como único ciclo — tiene un ciclo de vida propio, más largo que el de una `Activity`, porque además de "existir" tiene que "engancharse" a una Activity y "construir" su vista por separado. Este es el esqueleto que generan todos los proyectos (`EjemploFragmentos/fragmentos/FragmentoArriba.java`, `EjercicioFragmentos/fragmentos/Fragmento1.java`, `FragmentosNombres/fragmentos/FragmentoArriba.java`...):

```java
public class FragmentoArriba extends Fragment {

    public FragmentoArriba() { super(); }              // (1) constructor vacío obligatorio

    @Override
    public void onAttach(@NonNull Context context) {    // (2) se "engancha" a la Activity
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {  // (3) se crea (sin vista todavía)
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {   // (4) infla su layout
        return inflater.inflate(R.layout.fragment_fragmento_arriba, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { // (5) vista ya lista
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) { // (6) se vuelve a mostrar
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDetach() {   // (7) se elimina de la Activity
        super.onDetach();
    }
}
```

| # | Método | Cuándo se ejecuta | Qué se hace normalmente aquí |
|---|---|---|---|
| 1 | Constructor vacío | Al instanciar la clase (`new FragmentoArriba()`) | **Nada** — nunca se le pasan parámetros al constructor (ver §5) |
| 2 | `onAttach(Context)` | Al asociarse a su `Activity` contenedora | Guardar la referencia a la Activity como una interfaz propia (ver §5) |
| 3 | `onCreate(Bundle)` | Al crearse el fragmento, **antes** de tener vista | Inicializar datos que no dependan de la interfaz |
| 4 | `onCreateView(...)` | Cuando toca construir su interfaz | `inflater.inflate(R.layout.xxx, container, false)` — devuelve la `View` raíz |
| 5 | `onViewCreated(View, Bundle)` | Justo después de (4), con la vista ya construida | `findViewById`, listeners, leer `getArguments()` — **aquí es donde se conecta todo** |
| 6 | `onViewStateRestored(Bundle)` | Cuando el fragmento se vuelve a mostrar tras estar oculto | Poco usado en estos proyectos; casi siempre solo `super.onViewStateRestored(...)` |
| 7 | `onDetach()` | Al quitar el fragmento de la Activity | Liberar la referencia a la Activity (`activity = null;`) para evitar fugas de memoria |

**Detalle importante de (4)**: el tercer parámetro de `inflate(...)` es siempre `false`. Significa "no añadas automáticamente esta vista al `container`" — es el propio sistema de fragmentos (`FragmentManager`) quien se encarga de insertarla en el contenedor correcto; si se pasara `true` se duplicaría la vista o lanzaría una excepción. Ver [13-view-viewgroup](13-view-viewgroup.md) §3 para la explicación general de `inflate()` y su parámetro `parent`.

## 3. Dónde vive el fragmento: `FragmentContainerView`

El layout de la `Activity` no contiene el fragmento directamente — contiene un **contenedor vacío** donde el `FragmentManager` coloca la vista del fragmento en tiempo de ejecución. Hay dos formas, ambas presentes en los proyectos del curso:

```xml
<!-- Forma moderna: EjemploFragmentos, EjercicioFragmentos -->
<androidx.fragment.app.FragmentContainerView
    android:id="@+id/contenedor1"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:layout_weight="1" />
```
```xml
<!-- Forma "clásica": FragmentosNombres -->
<LinearLayout
    android:id="@+id/contenedor1"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:layout_weight="1"
    android:orientation="vertical"></LinearLayout>
```

`FragmentContainerView` es la vista **pensada específicamente** para alojar fragmentos (optimiza las animaciones de transición internas del `FragmentManager`); un `LinearLayout` vacío también funciona como contenedor genérico (el `FragmentManager` puede insertar la vista del fragmento dentro de cualquier `ViewGroup`), pero es la forma antigua, mantenida por compatibilidad. Si empiezas un proyecto nuevo, usa `FragmentContainerView`.

Todos los contenedores se reparten el espacio con `layout_weight="1"` — ver [02-diseno-basado-en-pesos](02-diseno-basado-en-pesos.md) para el porqué de `layout_height="0dp"` + `layout_weight`.

## 4. `FragmentManager`: añadir y reemplazar fragmentos

Todo fragmento se coloca (o se quita) mediante una **transacción**, siempre con la misma estructura de tres pasos:

```java
getSupportFragmentManager()
        .beginTransaction()                              // (1) abrir transacción
        .add(R.id.contenedor1, new FragmentoArriba())     // (2) operación: add / replace / remove...
        .commit();                                        // (3) confirmar
```

Las dos operaciones que usan estos proyectos:

- **`.add(id, fragmento)`** — coloca el fragmento en el contenedor, **sin quitar** lo que ya hubiera. Se usa siempre en `onCreate()`, para la primera carga de cada fragmento.
- **`.replace(id, fragmento)`** — quita lo que hubiera en ese contenedor y pone el fragmento nuevo en su lugar. Se usa para **actualizar** un fragmento que ya está en pantalla con datos nuevos (por ejemplo, cuando `FragmentoArriba` envía un texto y hay que "refrescar" `FragmentoMedio`/`FragmentoAbajo` con ese texto — no existe un método `fragmento.actualizar(datos)`, la única forma de "refrescar" el contenido de un fragmento con `newInstance` es crear una instancia nueva y reemplazar la vieja).

```java
// MainActivity.java (FragmentosNombres) — reemplaza el fragmento del medio cada vez que llega texto nuevo
@Override
public void cambiarTexto(String texto) {
    Bundle bundle = new Bundle();
    bundle.putString("nombre", texto);
    getSupportFragmentManager().beginTransaction()
            .replace(R.id.contenedor2, FragmentoMedio.newInstance(bundle))
            .commit();
}
```

### Evitar duplicar fragmentos al rotar la pantalla

`EjemploFragmentos`/`EjercicioFragmentos` cargan los fragmentos directamente en `onCreate()` sin condición, pero el patrón correcto — que sí usa el PDF del curso en su versión final — es comprobar `savedInstanceState`:

```java
if (savedInstanceState == null) {
    getSupportFragmentManager().beginTransaction()
            .add(R.id.contenedor1, new FragmentoArriba())
            .commit();
    ...
}
```

**Por qué**: cuando Android recrea una `Activity` (por ejemplo al rotar la pantalla), el `FragmentManager` **ya restaura automáticamente** los fragmentos que había antes. Si el código de `onCreate()` vuelve a hacer `.add(...)` sin comprobar esto, se añade un **segundo** fragmento encima del que el sistema ya restauró — duplicando la interfaz. Ver [10-activity-en-profundidad](10-activity-en-profundidad.md) §3 para el porqué de `savedInstanceState` a nivel general.

## 5. Comunicación Fragment → Activity: el patrón de interfaz

Un fragmento **no debería** llamar directamente a métodos de `MainActivity` (eso lo acoplaría a una Activity concreta, rompiendo la idea de "pieza reutilizable"). El patrón que usan todos los proyectos es:

**Paso 1 — declarar una interfaz** con los métodos que el fragmento necesita disparar en quien lo contenga:
```java
// IControlFragmentos.java
public interface IControlFragmentos {
    void cambiarTexto(String texto);
}
```

**Paso 2 — la `Activity` implementa esa interfaz:**
```java
public class MainActivity extends AppCompatActivity implements IControlFragmentos {
    @Override
    public void cambiarTexto(String texto) { ... }
}
```

**Paso 3 — el fragmento, en `onAttach()`, guarda el `Context` que recibe convertido a esa interfaz:**
```java
private IControlFragmentos mainActivity;

@Override
public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    this.mainActivity = (IControlFragmentos) context;
}
```
`onAttach(Context context)` recibe la `Activity` que está alojando el fragmento (una `Activity` **es** un `Context`). El `(IControlFragmentos) context` es un **cast**: le dice a Java "trata este objeto como si solo tuviera los métodos de `IControlFragmentos`", aunque en realidad sea una `MainActivity` completa. Así el fragmento nunca depende de la clase concreta `MainActivity`, solo del contrato `IControlFragmentos` — podría alojarse en cualquier otra `Activity` que implemente esa interfaz.

**Paso 4 — usarlo**, típicamente en el listener de un botón:
```java
btnEnviar.setOnClickListener(v -> mainActivity.cambiarTexto(etTexto.getText().toString()));
```

### Variante más segura: comprobar el cast con `instanceof`

`EjercicioFragmentos/fragmentos/Fragmento1.java` añade una comprobación que los demás proyectos no tienen:
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
```
Sin esta comprobación, si el fragmento se alojara alguna vez en una `Activity` que **no** implementa `IControlFragmentos`, el cast directo (`(IControlFragmentos) context`) lanzaría un `ClassCastException` con un mensaje genérico y poco claro. Con el `instanceof`, el fallo se detecta igual (la app sigue crasheando, a propósito: sin esa Activity, el fragmento no puede funcionar), pero con un mensaje de error que dice exactamente qué falta implementar — mucho más fácil de depurar. Es una buena práctica a copiar en cualquier fragmento nuevo.

### Liberar la referencia en `onDetach()`

```java
@Override
public void onDetach() {
    super.onDetach();
    activity = null;
}
```
(`Fragmento1`/`Fragmento2` de `EjercicioFragmentos` lo hacen; `FragmentoArriba` de `FragmentosNombres`/`EjemploFragmentos` no.) Poner la referencia a `null` evita que el fragmento se quede "agarrado" a una `Activity` que ya no existe (fuga de memoria) si, por lo que sea, algo mantiene vivo el fragmento más tiempo del debido tras separarse de su Activity.

## 6. Comunicación Activity → Fragment: `Bundle` + `newInstance(...)`

Para pasarle datos a un fragmento **no se usa un constructor con parámetros**. Se usa siempre el mismo patrón, con dos piezas:

**Un `Bundle`** (el mismo tipo que ya se usa para pasar datos entre `Activity` vía `Intent` — ver [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) §2): un mapa clave→valor.

**Un método estático `newInstance(Bundle)`**, siempre con la misma forma:
```java
public static FragmentoMedio newInstance(Bundle argumentos) {
    FragmentoMedio fragmento = new FragmentoMedio();
    if (argumentos != null) {
        fragmento.setArguments(argumentos);
    }
    return fragmento;
}
```
`setArguments(Bundle)` es el método que ofrece la clase `Fragment` para adjuntarle un `Bundle` **antes** de que el fragmento tenga vista. Luego, dentro del fragmento (normalmente en `onViewCreated`), se recupera con `getArguments()`:
```java
@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Bundle argumentos = getArguments();
    if (argumentos != null && argumentos.containsKey("nombre")) {
        etNombre.setText(argumentos.getString("nombre"));
    }
}
```

**Por qué no un constructor con parámetros** (`new FragmentoMedio(texto)`): cuando Android recrea un fragmento automáticamente (por ejemplo al rotar la pantalla), lo hace llamando a su **constructor vacío** — cualquier dato que solo estuviera guardado en un campo puesto por un constructor personalizado **se perdería** en ese proceso. Los datos guardados vía `setArguments(Bundle)` sí sobreviven a esa recreación automática, porque el propio sistema los guarda y se los vuelve a pasar. Por eso el constructor de todo fragmento debe quedarse vacío (`super();`) y el paso de datos siempre pasa por `newInstance(Bundle)`.

### `containsKey(...)` antes de leer

```java
if (argumentos.containsKey("saludo")) {
    tvTexto.setText(argumentos.getString("saludo"));
}
if (argumentos.containsKey("color")) {
    tvTexto.setTextColor(argumentos.getInt("color"));
}
```
*(`EjemploFragmentos/fragmentos/FragmentoAbajo.java`)*

Este patrón permite que un mismo fragmento reciba **distintos subconjuntos de datos** según qué evento lo disparó (por ejemplo, `cambiarColor(...)` solo manda `"color"`, `cambiarTexto(...)` solo manda `"saludo"`) sin sobrescribir por accidente el otro valor con uno vacío. Si se hiciera `argumentos.getString("saludo")` directamente sin comprobar `containsKey`, cuando el `Bundle` no traiga esa clave se devuelve `null` en vez de lanzar una excepción (a diferencia de un `Map.get` que puede comportarse distinto) — pero `containsKey` deja la intención explícita: "solo actualizo esto si de verdad me lo han mandado".

## 7. Pasar objetos complejos: `Serializable`

Para pasar algo más que texto/números sueltos —una lista de objetos propios, por ejemplo— el `Bundle` acepta cualquier objeto que implemente `Serializable`:

```java
// modelos/Persona.java (EjercicioFragmentos)
public class Persona implements Serializable {
    private String nombre, apellido, fecha;
    ...
}
```
```java
// MainActivity.java
ArrayList<Persona> personas = new ArrayList<>();
...
personas.add(new Persona(nombre, apellido, fecha));
Bundle bundle = new Bundle();
bundle.putSerializable("personas", personas);
getSupportFragmentManager().beginTransaction()
        .replace(R.id.contenedor3, Fragmento3.newInstance(bundle))
        .commit();
```
```java
// Fragmento3.java — se recupera con un cast, ya que getSerializable(String) devuelve Object
FichaAdapter adapter = new FichaAdapter(this.mContext, (ArrayList<Persona>) getArguments().getSerializable("personas"));
gridFicha.setAdapter(adapter);
```
`ArrayList` ya implementa `Serializable` por sí misma, así que basta con que los objetos que contiene (`Persona`) también lo implementen para poder meter la lista entera en el `Bundle`. Ver [07-adaptadores](07-adaptadores.md) para el patrón `BaseAdapter`/`GridView` que consume esa lista una vez recuperada.

## 8. Errores típicos (aprendidos depurando estos proyectos)

1. **`findViewById` apuntando a un id que no existe en ese layout.** En `FragmentosNombres/fragmentos/FragmentoArriba.java` el código original hacía `view.findViewById(R.id.etNombre)`, pero el layout `fragment_fragmento_arriba.xml` solo define `etTexto` — `findViewById` no lanza error en el momento, simplemente devuelve `null`, y la app crashea con `NullPointerException` en el primer uso real (`etNombre.getText()`, al pulsar el botón). Comprueba siempre que el id que buscas en Java existe tal cual en el XML que se ha inflado.
2. **La clave del `Bundle` no coincide entre quien la escribe y quien la lee.** Mismo proyecto: `MainActivity.cambiarTexto(...)` guardaba el texto con `bundle.putString("", texto)` (clave vacía), mientras que `FragmentoMedio` leía `argumentos.getString("nombre")`. Como las claves no coincidían, `getString("nombre")` devolvía siempre `null` — no crashea, pero el dato nunca llega. Usa una constante o una cadena consistente en los dos lados.
3. **Cast a la interfaz sin comprobar `instanceof`** (ver §5) — falla igual, pero con un mensaje de error mucho menos claro.
4. **Olvidar `if (savedInstanceState == null)`** al añadir los fragmentos iniciales (ver §4) — duplica la interfaz al rotar la pantalla.
5. **Leer `getArguments()` sin comprobar `null`.** `Fragmento3.java` (`EjercicioFragmentos`) hace `getArguments().containsKey("personas")` directamente, sin el `if (argumentos != null)` que sí usan `Fragmento1`/`Fragmento2` del mismo proyecto. En la práctica no falla porque `MainActivity` siempre llama a `Fragmento3.newInstance(bundle)` con un `Bundle` (nunca a `new Fragmento3()` a secas), pero es un punto frágil: si algún día se crea el fragmento sin pasar por `newInstance(...)`, `getArguments()` devuelve `null` y esa línea lanza `NullPointerException`.

## 9. Resumen: qué proyecto usa qué técnica

| Proyecto | Contenedor | `newInstance` | Interfaz Activity↔Fragment | Dato complejo (`Serializable`) | Estado |
|---|---|---|---|---|---|
| [EjemploFragmentos](../05-proyectos-alumno/EjemploFragmentos.md) | `FragmentContainerView` | ✅ (`FragmentoAbajo`) | ✅ `IControlFragmentos` (2 métodos) | ❌ | ✅ Completo, sigue el PDF del curso casi al 100% |
| [EjercicioFragmentos](../05-proyectos-alumno/EjercicioFragmentos.md) | `FragmentContainerView` | ✅ (`Fragmento2`, `Fragmento3`) | ✅ `IControlFragmentos` + `instanceof` | ✅ `ArrayList<Persona>` | ✅ Completo, el más avanzado de los tres |
| [FragmentosNombres](../05-proyectos-alumno/FragmentosNombres.md) | `LinearLayout` (forma clásica) | ✅ (`FragmentoMedio`) | ✅ `IControlFragmentos` (1 método) | ❌ | ⚠️ Tenía dos bugs reales de los descritos en §8 (ya corregidos) |

## Ver también
- [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) — el `Bundle` como mecanismo general de paso de datos, ya usado entre `Activity` antes de verlo aquí entre fragmentos.
- [02-diseno-basado-en-pesos](02-diseno-basado-en-pesos.md) — por qué los contenedores de fragmentos usan `layout_height="0dp"` + `layout_weight="1"`.
- [07-adaptadores](07-adaptadores.md) — el `BaseAdapter`/`GridView` que usa `Fragmento3` de `EjercicioFragmentos` para mostrar la lista de `Persona`.
- [10-activity-en-profundidad](10-activity-en-profundidad.md) — ciclo de vida de la `Activity` que aloja los fragmentos, y el porqué de `savedInstanceState`.
- [13-view-viewgroup](13-view-viewgroup.md) — qué hace realmente `inflater.inflate(layout, container, false)`.

## 🏋️ Practica esto

- [Nivel 5 — ejercicios 5.1 a 5.4](../07-ejercicios/05-nivel-5-fragmentos.md) (fragmento + interfaz, menú y formulario de tres fragmentos) y [Simulacro D](../07-ejercicios/08-simulacros-de-examen.md)
