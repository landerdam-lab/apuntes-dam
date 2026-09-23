---
tags:
  - android
  - guia
  - malas-practicas
aliases:
  - Código antiguo y malas prácticas
  - Bugs de los proyectos del profesor
---

# 05 — Código antiguo, malas prácticas y bugs de los proyectos

Todo lo que en los PDFs o en los proyectos del profesor está **obsoleto**, es **mejorable** o directamente **falla**. Para cada punto: qué es, por qué es un problema, cómo se hace hoy y si conviene cambiarlo o **solo entenderlo para clase**.

> [!important] 📌 Para el examen
> Si el examen pide "hazlo como en clase", hazlo como en clase (por ejemplo, `AsyncTask`). Estos avisos son para que **entiendas** lo que pasa y para tus propios proyectos.

## A. Bugs reales (el código no hace lo que pretende)

| # | Dónde | Qué pasa | Arreglo | ¿Cambiar? |
|---|---|---|---|---|
| 1 | `AppExecutors` (PDF Diálogo, **EjemploDialogoPersonalizado**, **EjercicioPokemon**) | Parámetros cruzados: `getMainThread()` es un *pool* de hilos | `new AppExecutors(single, new MainThreadExecutor(), pool3)` | ✅ Sí |
| 2 | `MainActivity` de **EjercicioPokemon** | Doble toque: resta al revés (`ultimo - ahora`) y `float` → cualquier 2.º toque borra | `long` y `ahora - ultimo < 300` | ✅ Sí |
| 3 | `MainActivity` de **EjercicioPokemon** | Cada filtro añade otro observer; los viejos siguen activos | `removeObservers` o `ViewModel` + `switchMap` | ✅ Sí |
| 4 | `ProgressAndando` (**DisenyoPesos**) | `andando4` nunca se muestra (se reinicia al llegar a 3) | `foto = (foto + 1) % imagenes.length()` | ✅ Sí |
| 5 | `AsyntaskDamCaballo` (**EjercicioAsyncTaskAlumnos**) | El caballo no cambia de imagen (se calcula `numFoto` pero no se usa) | Array de caballos + `setImageResource` en `onProgressUpdate` | ✅ Sí |
| 6 | `anim/salida.xml` (**AdapterDam2**) | Igual que `entrada.xml` (0 → 1) | `fromAlpha="1" toAlpha="0"` | ✅ Sí |
| 7 | `JugadorActivity` (**EjercicioAdaptadoresFinal**) | La transición `opacidad` se crea y nunca se aplica; además es un `slide` | Aplicarla o usar `windowAnimations` | Entender |
| 8 | `rellenarEquipos` (**EjercicioAdaptadoresFinal**) | Los 3 equipos son "Lakers" con los mismos 3 "lebron" | Datos distintos | ✅ Sí |
| 9 | `EjercicioSpinerAdapter` (**EjercicioSpinner**) | `ArrayAdapter<ArrayList<String>>`: genérico equivocado | `ArrayAdapter<String>` + `super(context, 0, datos)` | ✅ Sí |
| 10 | Spinner (**EjercicioSpinner**) | No se puede elegir el primer jugador (ya está seleccionado) | Fila de aviso en la posición 0 | ✅ Sí |
| 11 | `LoginDialogFrag` (**EjemploDialogoPersonalizado**) | Login correcto: el diálogo no se cierra. Incorrecto: se cierra sin avisar | `dismiss()` tras entrar + mensaje de error | ✅ Sí |
| 12 | `RegisterPokemon` (**EjercicioPokemon**) | `Integer.getInteger(texto)` para "convertir" → siempre `null` | `Integer.parseInt` | ✅ Sí |
| 13 | `DisenyoConstraint` | `textView4` sin restricciones: en el móvil salta a (0,0) | Añadir restricciones | ✅ Sí |
| 14 | `FragmentoMedio` (**EjercicioFragmentos**) | Datos por constructor → se pierden al girar | `newInstance(Bundle)` | ✅ Sí |
| 15 | `MainActivity` con fragmentos (**EjemploFragmentos2**, **EjercicioFragmentos**) | `add()` en cada `onCreate` → fragmentos duplicados al girar | `if (savedInstanceState == null)` | ✅ Sí |
| 16 | PDF **AsyncTask** | `Runnable` + `Thread` que tocan vistas → `CalledFromWrongThreadException` | Lo que hace el proyecto: `AsyncTask` real, o `Executor` + `Handler` | Entender |

## B. Código antiguo (funciona, pero hoy se hace de otra forma)

> [!warning] ⚠️ Cuidado: `AsyncTask` (obsoleto desde API 30)
> **Dónde:** DisenyoPesos, EjercicioAsyncTaskAlumnos. **Problema:** fugas de memoria (guarda la Activity), no se cancela al salir y su comportamiento cambió entre versiones. **Hoy:** `ExecutorService` + `Handler` ([Utilidades](../06-codigo-reutilizable/09-utilidades.md)) o corrutinas (Kotlin). **Decisión:** entenderlo para clase; en proyectos propios, `Executor`.

> [!warning] ⚠️ Cuidado: `overridePendingTransition` (obsoleto en Android 14)
> **Dónde:** AdapterDam2. **Hoy:** `overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, enter, exit)` (API 34+). **Decisión:** entender.

> [!warning] ⚠️ Cuidado: `getSerializableExtra(String)` / `getSerializable(String)` (obsoletos en API 33)
> **Dónde:** EjercicioAdaptadoresFinal, EjercicioFragmentos, EjercicioPokemon. **Hoy:** `getSerializableExtra(clave, Clase.class)` o `IntentCompat`/`BundleCompat`, o `Parcelable`. **Decisión:** entender; funciona.

> [!warning] ⚠️ Cuidado: `ListView`/`GridView` + `BaseAdapter` sin reciclar `convertView`
> **Dónde:** todos los adaptadores. **Problema:** con listas largas va lento y gasta memoria. **Hoy:** `RecyclerView` ([14](../02-conceptos/14-recyclerview.md), [aaaa](../04-proyectos-profesor/aaaa.md)); como mínimo, ViewHolder en `BaseAdapter` ([Listas](../06-codigo-reutilizable/06-listas-y-adaptadores.md)). **Decisión:** los exámenes usan `BaseAdapter`, pero añade el `if (convertView == null)`.

> [!warning] ⚠️ Cuidado: `findViewById` repetido y casteos
> **Dónde:** todos. **Hoy:** View Binding ([27](../02-conceptos/27-navigation-viewbinding-viewmodel.md)). **Decisión:** entender; `findViewById` es lo que se pide en clase.

> [!warning] ⚠️ Cuidado: Gradle en Groovy con versiones de 2021
> **Dónde:** [PDF Transiciones](../03-pdfs/10-transiciones.md). **Hoy:** `build.gradle.kts` + `libs.versions.toml`, que ya usan los proyectos. **Decisión:** no copiar el bloque del PDF.

> [!warning] ⚠️ Cuidado: Picasso
> **Dónde:** EjercicioAdaptadoresFinal, EjercicioPokemon. **Problema:** casi sin mantenimiento. **Hoy:** Glide (ya en el curso) o Coil (Kotlin). **Decisión:** vale para clase.

## C. Malas prácticas de estilo y mantenimiento

| Mala práctica | Dónde | Por qué es un problema | Cómo se hace bien |
|---|---|---|---|
| Textos escritos en el XML y en Java ("Hello World!", "Lakers", "Adios"…) | Casi todos (DisenyoPesos es la excepción) | No se puede traducir; hay que cambiarlos en muchos sitios | `@string/…` y `getString(R.string…)` |
| Tamaños fijos (`409dp`, `380dp x 620dp`, `marginTop 284dp`) | EjercicioSpinner, EjercicioAdaptadoresFinal, AdapterDam2 | En móviles pequeños se sale; en grandes queda raro | `0dp` + restricciones, `wrap_content`, pesos |
| Sin validaciones de formulario | Pokemon, Register, EjercicioFragmentos | Cierres (`parseInt`), datos vacíos o duplicados | [Formularios](../06-codigo-reutilizable/05-formularios.md) |
| Contraseñas en texto plano | EjemploDialogoPersonalizado | Riesgo de seguridad | *Hash* (fuera del temario) |
| Código muerto (setters sin uso, `finalizar()`, `Tipo`, Picasso en AdapterDam2, `loadAllPokemons`) | Varios | Confunde al leer | Borrarlo |
| Nombres poco claros (`jugadores_Activity`, `aaaa`, `tvAlturaJugador` para la posición, `Asyntask`) | Varios | Cuesta encontrar las cosas | *PascalCase* para clases y nombres que digan lo que contienen |
| E/S de disco en el hilo principal | `CameraActivity` | Tirones en la pantalla | `diskIO` |
| `throw new RuntimeException(e)` en un `catch` | `CameraActivity`, `VozActivity` | Un fallo recuperable cierra la app | Log + Toast |
| `TypedArray` sin `recycle()` | DisenyoPesos, AdapterDam2 (grid) | Fuga de memoria pequeña | `recycle()` o copiar a `int[]` |
| `assert` en Android | EjercicioFragmentos, aaaa | Los `assert` están desactivados: no comprueban nada | `if (x == null) …` / `Objects.requireNonNull` |
| Contexto de aplicación para inflar | `UsuariosAdapter` | Ignora el tema (modo oscuro) | Pasar la Activity (`this`) |
| `ImageButton` sin `contentDescription` | DisenyoPesos | Accesibilidad (lectores de pantalla) | `android:contentDescription="@string/…"` |
| `app:srcCompat="@drawable/m3_split_button_chevron_avd"` | EjercicioAdaptadoresFinal | Drawable interno de Material: puede desaparecer | Tu propio *placeholder* |
| `EditText` con `android:text="Name"` | EjercicioFragmentos | Sale relleno | `android:hint` |

## D. Recomendaciones modernas 💡

| Hoy se recomienda | En vez de | Nota |
|---|---|---|
| View Binding | `findViewById` | [27](../02-conceptos/27-navigation-viewbinding-viewmodel.md) |
| `ViewModel` + `LiveData` | Datos en la Activity o comunicación con interfaces | [27](../02-conceptos/27-navigation-viewbinding-viewmodel.md) |
| `RecyclerView` + `ListAdapter` | `ListView` + `BaseAdapter` | [14](../02-conceptos/14-recyclerview.md) |
| `Executor` / corrutinas | `AsyncTask` | [Utilidades](../06-codigo-reutilizable/09-utilidades.md) |
| Navigation Component | `replace()` a mano | [aaaa](../04-proyectos-profesor/aaaa.md) |
| Activity Result API | `startActivityForResult` | Ya lo usan los proyectos (cámara, voz) ✔ |
| Kotlin + Jetpack Compose | Java + XML | No aparece en los PDFs del curso |
