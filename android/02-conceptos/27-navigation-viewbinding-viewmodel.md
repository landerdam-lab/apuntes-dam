---
tags:
  - android
  - concepto
  - tema/moderno
aliases:
  - View Binding
  - ViewModel
  - Navigation Component
---

# 27 — View Binding, ViewModel y Navigation (Android moderno)

> Proyecto: [aaaa](../04-proyectos-profesor/aaaa.md). **Fuera de los PDFs.** Sirve para entender cómo se escribe hoy lo mismo que haces en clase.

## Concepto: View Binding 💡 Recomendación

### Qué es
Android genera, por cada layout, una clase con **un campo por cada vista con id**. Así ya no hace falta `findViewById` ni castear.

| Layout | Clase generada | Vista `@+id/btn_enviar` |
|---|---|---|
| `activity_main.xml` | `ActivityMainBinding` | `binding.btnEnviar` |
| `fragment_transform.xml` | `FragmentTransformBinding` | — |
| `item_transform.xml` | `ItemTransformBinding` | — |

### Activarlo (en `app/build.gradle.kts`)
```kotlin
android {
    buildFeatures { viewBinding = true }
}
```

### Antes y después ✅ Reutilizable
```java
// ANTES (todos los proyectos de clase)
setContentView(R.layout.activity_main);
Button btn = findViewById(R.id.btnEnviar);
EditText et = findViewById(R.id.etNombre);
btn.setOnClickListener(v -> saludar(et.getText().toString()));

// DESPUÉS (View Binding)
private ActivityMainBinding binding;
…
binding = ActivityMainBinding.inflate(getLayoutInflater());
setContentView(binding.getRoot());
binding.btnEnviar.setOnClickListener(v -> saludar(binding.etNombre.getText().toString()));
```
**Ventajas:** si el id no existe **no compila** (en vez de dar `NullPointerException` al ejecutar), y el tipo siempre es el correcto.

**En un Fragment:** inflar en `onCreateView` con `XxxBinding.inflate(inflater, container, false)` y poner `binding = null` en `onDestroyView` (ver `TransformFragment`).

## Concepto: ViewModel + LiveData 📌 Importante

### Qué es
- **`ViewModel`:** una clase que guarda los **datos de una pantalla** y **sobrevive al giro del móvil**, porque Android no la destruye al recrear la Activity o el Fragment.
- **`LiveData`:** un dato "observable": quien lo observa recibe el valor nuevo cada vez que cambia, solo mientras la pantalla está visible.

### Por qué hace falta
En [EjercicioFragmentos](../04-proyectos-profesor/EjercicioFragmentos.md), la lista `personas` vive en la Activity: al girar el móvil, la Activity se recrea y **la lista se pierde**. En un `ViewModel` no se perdería.

### Ejemplo reutilizable ✅ Reutilizable
```java
public class PersonasViewModel extends ViewModel {
    private final MutableLiveData<List<Persona>> personas = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Persona>> getPersonas() { return personas; }   // lectura desde fuera

    public void anadir(Persona p) {
        List<Persona> nueva = new ArrayList<>(personas.getValue());
        nueva.add(p);
        personas.setValue(nueva);            // setValue: hilo principal · postValue: desde otro hilo
    }
}

// En la Activity o Fragment:
PersonasViewModel vm = new ViewModelProvider(this).get(PersonasViewModel.class);
vm.getPersonas().observe(this, lista -> adaptador.actualizar(lista));
vm.anadir(new Persona("Ane", "Etxeberria", "01/01/2006"));
```
> Dos fragmentos pueden **compartir** un ViewModel si ambos lo piden con `new ViewModelProvider(requireActivity())`. Sustituye a la interfaz `IControlFragmentos`.

## Concepto: Navigation Component

### Qué es
Una librería que gestiona la navegación entre **fragmentos** a partir de un **grafo** en XML (`res/navigation/*.xml`). Hace por ti los `replace`, la pila de "Atrás", el título de la Toolbar y los menús (drawer o barra inferior).

### Piezas (ver aaaa)
| Pieza | Dónde | Qué hace |
|---|---|---|
| Grafo `mobile_navigation.xml` | `res/navigation/` | Lista de destinos y el inicial |
| `NavHostFragment` | `FragmentContainerView` con `android:name="androidx.navigation.fragment.NavHostFragment"` y `app:navGraph` | El "hueco" donde se cambian los fragmentos |
| `NavController` | `navHostFragment.getNavController()` | `navigate(R.id.destino)` y `navigateUp()` |
| `NavigationUI` | `setupWithNavController(menuView, navController)` | Conecta menús y Toolbar con el grafo |

> [!important] 📌 Importante
> Los `android:id` de los ítems del menú deben ser **iguales** a los `android:id` de los destinos del grafo. Así es como `NavigationUI` sabe adónde ir.

## Concepto: RecyclerView con `ListAdapter`

Explicado en [14 — RecyclerView](14-recyclerview.md). La versión de aaaa añade **`ListAdapter` + `DiffUtil`**: le das la lista nueva con `submitList(lista)` y él calcula qué filas cambiaron y solo repinta esas, con animación.

## ¿Esto va a examen?
Salvo que el profesor lo indique, **no**. Pero te sirve para:
- entender avisos de Android Studio ("considera usar View Binding"),
- escribir menos código en tus propios proyectos,
- el proyecto final o las prácticas.

## Relacionado
- [aaaa](../04-proyectos-profesor/aaaa.md) · [20 — Executors y LiveData](20-executors-y-livedata.md) · [16 — Fragmentos](16-fragmentos.md)
