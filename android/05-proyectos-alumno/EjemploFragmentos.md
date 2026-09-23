---
tags:
  - android
  - proyecto
  - estado/completo
---

# Proyecto: `EjemploFragmentos`

## Qué es

El ejemplo guía del PDF [8-Fragmentos.pdf](../adjuntos/pdfs/8-Fragmentos.pdf): dos fragmentos, uno arriba con un campo de texto y un botón "Enviar", otro abajo con un `TextView` que muestra ese texto — y además un menú contextual (⋮) con dos opciones ("Rojo"/"Verde") que cambian el color del texto de abajo. Es la implementación **de referencia** para entender el patrón completo de comunicación entre fragmentos explicado en [16-fragmentos](../02-conceptos/16-fragmentos.md); casi todos los demás proyectos de fragmentos (`EjercicioFragmentos`, `FragmentosNombres`) son variaciones de esta misma estructura.

## Estructura

```
app/src/main/java/com/example/ejemplofragmentos/
├── MainActivity.java              → arranca los 2 fragmentos, implementa IControlFragmentos, gestiona el menú
├── IControlFragmentos.java        → interfaz con 2 métodos: cambiarColor, cambiarTexto
└── fragmentos/
    ├── FragmentoArriba.java       → EditText + botón "Enviar"
    └── FragmentoAbajo.java        → TextView que refleja el texto/color recibidos
```

Respecto al enunciado del PDF, aquí `IControlFragmentos` vive directamente en el paquete raíz (`com.example.ejemplofragmentos`) en vez de en un subpaquete `InterfacesDam` — una simplificación sin ningún efecto funcional.

## `activity_main.xml` — dos contenedores + toolbar

```xml
<com.google.android.material.appbar.MaterialToolbar
    android:id="@+id/toolbar"
    android:layout_width="match_parent"
    android:layout_height="?attr/actionBarSize"
    app:title="Fragmentos" .../>

<androidx.fragment.app.FragmentContainerView
    android:id="@+id/contenedor1"
    android:layout_height="0dp"
    android:layout_weight="1" />

<androidx.fragment.app.FragmentContainerView
    android:id="@+id/contenedor2"
    android:layout_height="0dp"
    android:layout_weight="1" />
```
Usa `FragmentContainerView` (la forma moderna, ver [16-fragmentos](../02-conceptos/16-fragmentos.md) §3) y reparte la pantalla al 50%/50% entre los dos fragmentos con `layout_weight` (ver [02-diseno-basado-en-pesos](../02-conceptos/02-diseno-basado-en-pesos.md)). La `MaterialToolbar` es la que permite que aparezca el menú de los 3 puntos.

## `IControlFragmentos.java` — el contrato

```java
public interface IControlFragmentos {
    void cambiarColor(int color);
    void cambiarTexto(String texto);
}
```
Una **interfaz** (ver [00-programacion-basica](../02-conceptos/00-programacion-basica.md) §7 si no sabes qué es) es un contrato: obliga a cualquier clase que la implemente (aquí, `MainActivity`) a tener estos métodos, sin decir cómo funcionan por dentro. Dos métodos porque hay dos formas de disparar un cambio en `FragmentoAbajo`: escribiendo texto (desde `FragmentoArriba`) o eligiendo un color (desde el menú de `MainActivity`). Ver la explicación general del patrón en [16-fragmentos](../02-conceptos/16-fragmentos.md) §5.

## `FragmentoArriba.java` — envía el texto

```java
public class FragmentoArriba extends Fragment {

    private IControlFragmentos activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragmento_arriba, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etTexto = view.findViewById(R.id.etTexto);
        Button btnEnviar = view.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(v -> activity.cambiarTexto(etTexto.getText().toString()));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (IControlFragmentos) context;
    }
}
```
`extends Fragment` significa que `FragmentoArriba` **hereda** de la clase `Fragment` (ver [00-programacion-basica](../02-conceptos/00-programacion-basica.md) §6) todo el comportamiento ya construido de un fragmento, y solo añade lo específico de esta pantalla. `@Override` en cada método avisa de que se está reemplazando uno que ya existía en `Fragment` (mismo documento, mismo apartado). Nótese que aquí `onCreateView` **devuelve directamente** el resultado de `inflate(...)`, sin pasar por una variable intermedia `View vista = ...; return vista;` como hacen otros proyectos — es exactamente equivalente, solo una línea menos. `etTexto`/`btnEnviar` se buscan como variables **locales** dentro de `onViewCreated` (no como campos de la clase) porque no se necesitan en ningún otro método del fragmento.

## `FragmentoAbajo.java` — recibe y muestra

```java
public static FragmentoAbajo newInstance(Bundle bundle) {
    FragmentoAbajo fragmentoAbajo = new FragmentoAbajo();
    if (bundle != null) {
        fragmentoAbajo.setArguments(bundle);
    }
    return fragmentoAbajo;
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    TextView tvAbajo = view.findViewById(R.id.tvFragmentoAbajo);
    Bundle bundle = getArguments();
    if (bundle != null) {
        if (bundle.containsKey("saludo")) {
            tvAbajo.setText(bundle.getString("saludo"));
        }
        if (bundle.containsKey("color")) {
            tvAbajo.setTextColor(bundle.getInt("color"));
        }
    }
}
```
El patrón `newInstance(Bundle)` + `containsKey(...)` está explicado en detalle en [16-fragmentos](../02-conceptos/16-fragmentos.md) §6. Aquí se ve claramente **por qué** hace falta el `containsKey`: cuando llega solo `"saludo"` (desde `cambiarTexto`) no se toca el color, y viceversa cuando llega solo `"color"` (desde el menú) — si se leyera directamente sin comprobar, uno de los dos "borraría" el efecto del otro con un valor por defecto.

## `MainActivity.java` — orquesta todo

```java
public class MainActivity extends AppCompatActivity implements IControlFragmentos {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().add(R.id.contenedor1, new FragmentoArriba()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor2, new FragmentoAbajo()).commit();
    }

    @Override
    public void cambiarColor(int color) {
        Bundle bundle = new Bundle();
        bundle.putInt("color", color);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor2, FragmentoAbajo.newInstance(bundle))
                .commit();
    }

    @Override
    public void cambiarTexto(String texto) {
        Bundle bundle = new Bundle();
        bundle.putString("saludo", texto);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor2, FragmentoAbajo.newInstance(bundle))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.rojo) { cambiarColor(Color.RED); return true; }
        else if (id == R.id.verde) { cambiarColor(Color.GREEN); return true; }
        return super.onOptionsItemSelected(item);
    }
}
```
Cada vez que `cambiarColor`/`cambiarTexto` se ejecutan, se hace `.replace(...)` (no `.add(...)`) para sustituir `FragmentoAbajo` por una instancia nueva con el `Bundle` actualizado — el patrón de "refrescar" un fragmento descrito en [16-fragmentos](../02-conceptos/16-fragmentos.md) §4. `onCreateOptionsMenu`/`onOptionsItemSelected` son el mecanismo estándar de Android para inflar (`R.menu.menu_main`) y reaccionar al menú de los 3 puntos de la `Toolbar`.

## Diferencia con el PDF del curso (nota menor)

El PDF, en su primera versión del código (antes de añadir el menú), carga los fragmentos con una comprobación `if (savedInstanceState == null) { ... }` para no duplicarlos al rotar la pantalla (explicado en [16-fragmentos](../02-conceptos/16-fragmentos.md) §4). El código final de este proyecto **no** tiene esa comprobación — en la práctica, con una pantalla tan simple, rotar el dispositivo duplicaría visualmente ambos fragmentos. Es la única diferencia relevante entre el PDF y el proyecto real.

## Ver también
- [16-fragmentos](../02-conceptos/16-fragmentos.md) — todo el detalle técnico del patrón usado aquí.
- [EjercicioFragmentos](EjercicioFragmentos.md) — la versión "ampliada" de este mismo patrón, con 3 fragmentos y datos complejos (`Serializable`).
- [FragmentosNombres](FragmentosNombres.md) — otra variación del mismo patrón, con dos bugs reales instructivos.
