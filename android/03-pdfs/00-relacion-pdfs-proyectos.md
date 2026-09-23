---
tags:
  - android
  - pdf
  - indice
aliases:
  - Relación entre PDFs y proyectos
  - Teoría y práctica
---

# Relación entre los PDFs (teoría) y los proyectos (práctica)

## Los PDFs, en orden de curso

El curso tiene **dos bloques**, y la numeración de los PDFs vuelve a empezar en el segundo:

| Orden | Nota | Bloque | Proyecto del profesor |
|---|---|---|---|
| 1 | [0 — Instalación](01-instalacion-android.md) | Diseño de interfaces | [MyApplication](../04-proyectos-profesor/MyApplication.md) |
| 2 | [1 — Diseño por pesos](02-diseno-basado-en-pesos.md) | Diseño de interfaces | [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md) |
| 3 | [1b — Ejercicio de diseño](03-ejercicio-diseno.md) | Enunciado | (tu [EjercicioDiseno](../05-proyectos-alumno/EjercicioDiseno.md)) |
| 4 | [2 — Constraint](04-constraint.md) | Diseño de interfaces | [DisenyoConstraint](../04-proyectos-profesor/DisenyoConstraint.md) |
| 5 | [3 — Toast personalizado](05-toast-personalizado.md) | Diseño de interfaces | [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md) |
| 6 | [4 — AsyncTask](06-asynctask.md) | Diseño de interfaces | [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md), [EjercicioAsyncTaskAlumnos](../04-proyectos-profesor/EjercicioAsyncTaskAlumnos.md) |
| 7 | [5 — Frame by frame](07-frame-by-frame.md) | Diseño de interfaces | — |
| 8 | [Ejercicio animaciones](08-ejercicio-animaciones.md) | Enunciado | — |
| 9 | [6 — Adaptadores](09-adaptadores.md) | Diseño de interfaces | [AdapterDam2](../04-proyectos-profesor/AdapterDam2.md), [EjercicioSpinner](../04-proyectos-profesor/EjercicioSpinner.md), [EjercicioAdaptadoresFinal](../04-proyectos-profesor/EjercicioAdaptadoresFinal.md) |
| 10 | [7 — Transiciones](10-transiciones.md) | Diseño de interfaces | [AdapterDam2](../04-proyectos-profesor/AdapterDam2.md) |
| 11 | [8 — Fragmentos](11-fragmentos.md) | Diseño de interfaces | [EjemploFragmentos2](../04-proyectos-profesor/EjemploFragmentos2.md), [EjercicioFragmentos](../04-proyectos-profesor/EjercicioFragmentos.md) |
| 12 | [1 — Diálogo + SQLite](12-dialogo-sqlite-personalizado.md) | Programación móvil | [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md), [EjercicioPokemon](../04-proyectos-profesor/EjercicioPokemon.md) |
| 13 | [2 — Sensores](13-sensores.md) | Programación móvil | [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) |
| 14 | [3 — Cámara y almacenamiento](14-camara-y-almacenamiento.md) | Programación móvil | [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) |
| 15 | [4 — Voz](15-voz.md) | Programación móvil | [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) |

## Tabla teoría → práctica

| Tema del PDF | Proyecto | Archivo / clase | Explicación |
|---|---|---|---|
| Estructura de proyecto, SDK, emulador | MyApplication | Todo el proyecto | La plantilla que crea Android Studio |
| `layout_weight` + `0dp` | DisenyoPesos | `activity_main.xml` | Cabecera 2 / cuerpo 7 / pie 1 y rejilla 2×3 |
| `dimens.xml` + estilos heredados | DisenyoPesos | `dimens.xml`, `themes.xml` (`cursoTitulo.subtitulo`) | Mismos valores que el PDF |
| Degradado, borde y selector | DisenyoPesos | `fondo.xml`, `cabecera.xml`, `boton_pulsado.xml` | `<gradient>`, `<corners>`/`<stroke>`, `state_pressed` |
| Restricciones y *bias* | DisenyoConstraint | `activity_main.xml` | Solo boceto (4 TextView) |
| Toast con `Dialog` transparente | DisenyoPesos | `MainActivity` (btnToast), `toast_per.xml` | El `Handler` está comentado |
| `TypedArray` de imágenes | DisenyoPesos | `frames.xml`, `EjemploAsynctask` | `obtainTypedArray(R.array.imagenes)` |
| Hilo con progreso | DisenyoPesos · EjercicioAsyncTaskAlumnos | `ProgressAndando` · `AsyntaskDamCaballo` | ⚠️ Los proyectos usan `AsyncTask` real; el PDF, un `Runnable` que toca vistas desde otro hilo |
| `AnimationDrawable` | — | — | ⚠️ No está en los proyectos del profesor |
| `ArrayAdapter` / `BaseAdapter` / `Spinner` | AdapterDam2 · EjercicioSpinner | `SpinnerDam2Adapter`, `CompaniasAdapter`, `EjercicioSpinerAdapter` | `getView` / `getDropDownView` |
| `GridView` + Glide | AdapterDam2 | `ImageAdapter`, `GridViewDam2Activity` | `Glide.with().load().into()` |
| `RecyclerView` | aaaa (no el del PDF) | `TransformFragment` | ⚠️ `GridRecyclerDam` del PDF no está en AdapterDam2 |
| Transición por tema y por código | AdapterDam2 | `themes.xml`, `ListDam2Activity` | `setEnterTransition` antes de `setContentView` |
| *Shared element* | AdapterDam2 | `GridViewDam2Activity` → `DetalleActivity` | `Pair` + `setTransitionName` |
| Ciclo de vida del Fragment, `FragmentContainerView` | EjemploFragmentos2 | `FragmentoArriba/Abajo`, `activity_main.xml` | Idéntico al PDF |
| Interfaz Fragment → Activity | EjemploFragmentos2 · EjercicioFragmentos | `IControlFragmentos` | `onAttach` + casteo |
| `newInstance(Bundle)` | EjemploFragmentos2 · EjercicioFragmentos | `FragmentoAbajo` | `setArguments` / `getArguments` |
| Toolbar + menú de opciones | EjemploFragmentos2 | `MainActivity`, `menu_main.xml` | Rojo / Verde |
| `DialogFragment` | EjemploDialogoPersonalizado | `LoginDialogFrag`, `dialog_personalizado.xml` | Login |
| Room (entidad, DAO, Database) | EjemploDialogoPersonalizado · EjercicioPokemon | `bbdd/*`, `model/*` | CRUD |
| `AppExecutors` | EjemploDialogoPersonalizado · EjercicioPokemon | `bbdd/AppExecutors` | ⚠️ Parámetros cruzados (desde el PDF) |
| `LiveData` + `observe` | EjemploDialogoPersonalizado · EjercicioPokemon | `consultarUsuarios`, `cargaPokemonsTipo` | Lista que se refresca sola |
| `AlertDialog` + pulsación larga | EjemploDialogoPersonalizado | `RegisterActivity` | Confirmar borrado |
| `TextWatcher` | EjemploDialogoPersonalizado | `RegisterActivity` | Validar contraseñas |
| `SensorManager`, proximidad, giroscopio | EjemploDialogoPersonalizado | `SensoresActivity` | `onResume` / `onPause` |
| `uses-feature` | EjemploDialogoPersonalizado | `AndroidManifest.xml` | `required="false"` |
| Activity Result API (`TakePicture`, `RequestPermission`) | EjemploDialogoPersonalizado | `CameraActivity` | Registrar en `onCreate` |
| `FileProvider` + `file_paths.xml` | EjemploDialogoPersonalizado | Manifest, `res/xml/file_paths.xml` | `content://` para la cámara |
| `MediaStore` (insert/query, `IS_PENDING`) | EjemploDialogoPersonalizado | `CameraActivity.guardarEnGaleria/cargarImagenes` | Galería del sistema |
| Permiso con `maxSdkVersion` | EjemploDialogoPersonalizado | Manifest + `abrirCamara` | Solo Android ≤ 9 |
| `RecognizerIntent` | EjemploDialogoPersonalizado | `VozActivity` | `StartActivityForResult` |
| `ACTION_VIEW` + `URLEncoder` | EjemploDialogoPersonalizado | `VozActivity.realizarBusqueda` | Intent implícito |

## Lo que explican los PDFs y NO está en los proyectos del profesor

| Tema | PDF | Dónde practicarlo |
|---|---|---|
| Animación frame by frame (`AnimationDrawable`) | [5](07-frame-by-frame.md) | Tu [EjercicioDiseno](../05-proyectos-alumno/EjercicioDiseno.md), [06](../02-conceptos/06-animaciones-frame-by-frame.md) |
| Menú completo con `ConstraintLayout` | [2](04-constraint.md) | Ejercicio en [DisenyoConstraint](../04-proyectos-profesor/DisenyoConstraint.md) |
| `RecyclerView` del PDF de adaptadores (`GridRecyclerDam`) | [6](09-adaptadores.md) | [14 — RecyclerView](../02-conceptos/14-recyclerview.md) |
| Pasar un texto con `Bundle` a otra Activity (final del PDF de AsyncTask) | [4](06-asynctask.md) | [Pasar datos](../06-codigo-reutilizable/03-pasar-datos.md) |
| `RELATIVE_PATH` en `MediaStore` | [3 bloque 2](14-camara-y-almacenamiento.md) | Ejercicio en [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) |
| Pantalla completa (sin barra de estado) | [1](02-diseno-basado-en-pesos.md), [1b](03-ejercicio-diseno.md) | [28 — Recursos](../02-conceptos/28-recursos-values.md) |

## Lo que usan los proyectos y NO está bien explicado en los PDFs

| Tema | Dónde aparece | Explicación |
|---|---|---|
| Picasso | EjercicioAdaptadoresFinal, EjercicioPokemon | [Utilidades](../06-codigo-reutilizable/09-utilidades.md) |
| Permiso `INTERNET` | EjercicioAdaptadoresFinal, EjercicioPokemon | [24 — Manifest y permisos](../02-conceptos/24-manifest-y-permisos.md) |
| `Serializable` / `putSerializable` | EjercicioAdaptadoresFinal, EjercicioFragmentos, EjercicioPokemon | [Pasar datos](../06-codigo-reutilizable/03-pasar-datos.md) |
| `overridePendingTransition` + `res/anim` | AdapterDam2 | [08 — Transiciones](../02-conceptos/08-transiciones.md) |
| Doble toque y pulsación larga en una lista | EjercicioPokemon | [19](../02-conceptos/19-textwatcher-y-eventos-de-lista.md), [Utilidades](../06-codigo-reutilizable/09-utilidades.md) |
| EdgeToEdge y `WindowInsets` | Todos | [01 — Fundamentos](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) |
| Gradle con Kotlin DSL y *version catalog* | Todos | [25 — Gradle](../02-conceptos/25-gradle-y-librerias.md) |
| View Binding, Navigation, ViewModel, `RecyclerView` + `ListAdapter` | aaaa | [27](../02-conceptos/27-navigation-viewbinding-viewmodel.md) |
