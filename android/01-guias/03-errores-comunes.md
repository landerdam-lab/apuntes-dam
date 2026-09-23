---
tags:
  - android
  - guia
---

# Errores comunes y cómo arreglarlos

Cuando algo falla, hay que saber **dónde mirar** y **qué significa el mensaje**. Esta tabla reúne los errores que aparecen en estos proyectos, con su causa y su arreglo. Los marcados con 📌 son **errores reales** que ya ocurrieron en tus proyectos.

## Cómo saber de qué tipo es el error

| Cuándo aparece | Tipo | Dónde se ve |
|---|---|---|
| Al **escribir** el código (subrayado rojo) | Error del editor | En el propio código |
| Al **compilar** (▶ y no llega a arrancar) | Error de compilación | Pestaña **Build** |
| Con la app **abierta** y se cierra | Error de ejecución (*crash*) | Pestaña **Logcat** (ver [Guía 01 §6](01-ejecutar-la-app.md)) |
| La app funciona pero **hace algo raro** | Error lógico | Tú: depurar con `Log.d` |

---

## A. Errores de compilación

| Mensaje | Causa | Arreglo |
|---|---|---|
| 📌 **`cannot find symbol`** con una clase, p. ej. `AlertaDialog` | Errata en el nombre (`AlertaDialog` en vez de `AlertDialog`) o falta un `import` | Corrige la errata. Pon el cursor sobre el rojo y pulsa **Alt + Intro** → *Import class* |
| `cannot find symbol … R.id.xxx` | El `android:id` no existe en el XML o está mal escrito | Comprueba el id en el layout (`@+id/xxx`) y que el layout es el que carga `setContentView` |
| `Cannot resolve symbol 'R'` | Un error en un XML impide generar la clase `R` | Arregla primero el error del XML; **Build → Rebuild Project** |
| `incompatible types: int cannot be converted to String` | Le pasas un `int` donde va texto | `String.valueOf(numero)` o `"" + numero` |
| `variable x is accessed from within inner class; needs to be declared final` | Usas una variable local dentro de una clase anónima y esa variable cambia | Hazla `final`, o cópiala a una `final` antes ([04](../02-conceptos/04-toast-personalizado.md) §4.1) |
| `X is not abstract and does not override abstract method Y` | Implementas una interfaz pero te falta un método | Alt + Intro → *Implement methods* |
| 📌 **Sync: "incompatible version of the Android Gradle plugin"** | El AGP del proyecto es mayor que el que soporta tu Android Studio | Baja `agp` en `libs.versions.toml` o actualiza Android Studio ([Guía 01 §2](01-ejecutar-la-app.md)) |
| `Manifest merger failed … android:exported needs to be explicitly specified` | Una `Activity` con `intent-filter` sin `android:exported` | Añade `android:exported="true"` (o `"false"`) |
| `Duplicate class …` | La misma librería añadida dos veces | Quita la dependencia repetida en `build.gradle.kts` |
| `Cannot find a matching … 'androidx.room:room-compiler'` / errores en `*_Impl` | Falta `annotationProcessor` de Room o las dos versiones no coinciden | Las dos líneas de Room deben llevar **el mismo número de versión** |
| `Room cannot pick a constructor` | Dos constructores sin `@Ignore` en uno | Deja el vacío y marca el otro con `@Ignore` ([17](../02-conceptos/17-room-base-de-datos.md)) |

---

## B. Errores de ejecución (la app se cierra)

En **Logcat** busca `FATAL EXCEPTION` y la línea con `Caused by:`.

| Excepción | Qué significa | Causas típicas y arreglo |
|---|---|---|
| **`NullPointerException`** | Usaste algo que vale `null` | `findViewById` con un id que no está en ese layout, o llamado **antes** de `setContentView`; `getIntent().getStringExtra("x")` con clave mal escrita. Revisa el número de línea del error ([00 §13](../02-conceptos/00-programacion-basica.md)) |
| 📌 **`ActivityNotFoundException`** | Lanzas una `Activity` que no está en el manifiesto | Añade `<activity android:name=".MiActivity" … />` a `AndroidManifest.xml` |
| **`Resources$NotFoundException: String resource ID #0x…`** | Hiciste `textView.setText(numero)` con un `int`: Android lo interpreta como id de un recurso | `textView.setText(String.valueOf(numero))` |
| **`ClassCastException`** | Un *casting* a un tipo que no es. Típico en `onAttach`: `(MiInterfaz) context` cuando la Activity **no** hace `implements` | Añade `implements MiInterfaz` a la Activity y escribe sus métodos |
| **`CalledFromWrongThreadException`** | Tocaste una vista desde un hilo secundario | Toca la vista desde `onProgressUpdate`/`onPostExecute` (`AsyncTask`) o desde `AppExecutors.getInstance().getMainThread().execute(...)` ([20](../02-conceptos/20-executors-y-livedata.md)); con un temporizador, `Handler.postDelayed` |
| **`IllegalStateException: Cannot access database on the main thread`** | Consulta de Room en el hilo principal | Ejecútala dentro de `getDiskIO().execute(...)` ([17](../02-conceptos/17-room-base-de-datos.md)) |
| **`Fragment … has no zero-argument constructor`** / `Unable to instantiate fragment` | El fragmento no tiene constructor público vacío | Añade `public MiFragmento() { }` |
| `IndexOutOfBoundsException` | Pediste la posición 5 de una lista de 3 | Recuerda que se empieza en 0 y el último es `size() - 1` |
| **`UnsupportedOperationException: addView(View, LayoutParams) is not supported in AdapterView`** | En `getView` usaste `inflate(layout, parent, true)` | Pon `false` como tercer parámetro ([07](../02-conceptos/07-adaptadores.md) §2) |
| `RuntimeException: setOnItemClickListener cannot be used with a spinner` | Usaste `setOnItemClickListener` en un `Spinner` | Usa `setOnItemSelectedListener` |
| `You need to use a Theme.AppCompat theme` | La Activity usa un tema que no es AppCompat | Revisa `android:theme` en el manifiesto |
| **Crash al abrir la app tras cambiar la entidad de Room** (`Room cannot verify the data integrity… changed schema but forgot to update the version number`) | Cambiaste las columnas sin subir `version` de la BD | En desarrollo: **desinstala la app** del dispositivo (o sube `version` y borra datos); en producción: crea una migración |

---

## C. La app funciona pero "hace algo raro"

| Síntoma | Causa probable | Arreglo |
|---|---|---|
| 📌 **El `Toast` no aparece** | Falta `.show()` | `Toast.makeText(...).show();` ([04](../02-conceptos/04-toast-personalizado.md)) |
| 📌 **`@Update`/`@Delete` no hacen nada** | El objeto tiene un `id` que no existe (p. ej. `0` o `-1`) | Comprueba que cargaste el objeto de la BD o que le pusiste el `id` correcto |
| 📌 **El botón "Actualizar" no hace nada** | `idUsuario` sigue en `-1` porque no se seleccionó nada | Comprueba `if (id == -1)` y avisa al usuario |
| 📌 **El botón "Nuevo" está activo con los campos vacíos** | `"" .equals("")` es `true`: el validador da por buenas dos contraseñas vacías | Comprueba también `isEmpty()` ([19](../02-conceptos/19-textwatcher-y-eventos-de-lista.md)) |
| La lista no se actualiza al cambiar los datos | Cambiaste la lista pero no avisaste al adaptador | `adaptador.notifyDataSetChanged()` (o usa `LiveData`) |
| El listener del `Spinner` se ejecuta nada más abrir | Es normal: se dispara una primera vez al inicializar | Ignora la primera llamada con una bandera ([07](../02-conceptos/07-adaptadores.md) §6) |
| El clic largo también dispara el clic corto | `onItemLongClick` devuelve `false` | Devuelve `true` ("ya lo gestioné") ([19](../02-conceptos/19-textwatcher-y-eventos-de-lista.md)) |
| El diálogo de login sigue debajo tras abrir otra pantalla | No se llama a `dismiss()` | Llama a `dismiss()` tras `startActivity` |
| 📌 `getMainThread()` no corre en el hilo principal | Orden de parámetros cambiado en `AppExecutors.getInstance()` (igual que en el PDF) | Pasa `new MainThreadExecutor()` en **segundo** lugar ([20](../02-conceptos/20-executors-y-livedata.md)) |
| Los botones quedan tapados por la barra de estado | Falta el bloque de `EdgeToEdge`/insets, o el layout raíz no tiene `android:id="@+id/main"` | Restaura el bloque estándar ([01](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) §5) |
| El texto no cambia de idioma / color | Está escrito "a pelo" en el XML | Muévelo a `strings.xml` / `colors.xml` ([09](../02-conceptos/09-instalacion-estructura-proyecto.md) §3) |

---

## Método para depurar cualquier error

1. **Lee el mensaje entero**, de arriba abajo, sin asustarte por lo largo que sea.
2. Localiza **el tipo** de error (primera línea) y **`Caused by`**.
3. Busca **la primera línea con tu paquete** → ese es el fichero y la línea a mirar.
4. Pregúntate: *¿qué valdría cada variable en esa línea?* Si alguna puede ser `null`, sospecha de ella.
5. Añade `Log.d("MIAPP", "x=" + x);` antes de esa línea y vuelve a ejecutar.
6. Cambia **una sola cosa** cada vez. Si lo cambias todo a la vez, no sabrás qué lo arregló.

## Ver también
- [Guía 01 — Ejecutar la app](01-ejecutar-la-app.md)
- [02 — Glosario](02-glosario.md)
