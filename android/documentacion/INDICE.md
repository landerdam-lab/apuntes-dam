---
tags:
  - android
  - indice
---

# Documentación Android — Índice

Documentación completa de los conceptos de Android usados a lo largo de los proyectos de `C:\Users\Dam2\AndroidStudioProjects\`, explicados paso a paso con ejemplos reales sacados directamente de ese código.

Está organizada en cuatro bloques (más la carpeta `img/` con las capturas de pantalla):

- **`guias/`** — cómo abrir y ejecutar una app (emulador y móvil real), leer errores, y un glosario. **Empieza por aquí si es tu primera vez.**
- **`conceptos/`** — un documento por cada tema general (qué es, cómo funciona, ejemplos). Empieza por aquí si quieres entender una técnica en general.
- **`proyectos/`** — un documento por cada proyecto, explicando **cada fichero fragmento a fragmento**, remitiendo a los conceptos cuando corresponde. Empieza por aquí si quieres entender un proyecto concreto línea a línea.
- **`ejercicios/`** — ejercicios **de más fácil a más difícil**, con solución paso a paso (verificada en un emulador), y **simulacros de examen**.

Los documentos están enlazados entre sí (enlaces markdown relativos `[texto](ruta.md)`) — puedes navegar saltando de un concepto a otro o de un proyecto a los conceptos que usa.

## 🚀 ¿Primera vez con Android (o con programación)? Empieza aquí

Este orden es el más fácil de seguir si nunca has visto código o nunca has tocado Android:

0. [Guía 01 — Cómo abrir, ejecutar y probar una app](guias/01-ejecutar-la-app.md): abrir un proyecto en Android Studio, crear un emulador, ejecutar en tu móvil y leer los errores. **Hazlo primero: así podrás ver cada ejemplo funcionando.**
1. [00 — Conceptos básicos de programación](conceptos/00-programacion-basica.md): qué es una variable, una clase, un método, un bucle... el vocabulario mínimo de Java que se usa en todos los demás documentos. **Si nunca has programado, no te saltes este.**
2. [09 — Instalación y estructura de proyecto](conceptos/09-instalacion-estructura-proyecto.md): qué es Android Studio, qué carpetas tiene un proyecto y cómo se compila/instala una app. El contexto de "dónde está cada cosa" antes de ver código.
3. [01 — Fundamentos: Bundle, Intent y ciclo de vida](conceptos/01-fundamentos-bundle-intent-ciclo-vida.md): qué es una `Activity` (una pantalla), cómo se pasan datos entre pantallas, y el bloque de código que se repite en todos los proyectos — la base de la que dependen casi todos los demás documentos.
4. A partir de ahí, elige: o sigues la tabla de "Conceptos generales" en orden (02→20), o saltas directamente a un proyecto en la tabla de abajo y vuelves aquí cada vez que un término te suene a chino (todo enlaza al concepto que lo explica).

Además, dentro de cada proyecto de `AndroidStudioProjects\`, los ficheros `.java` tienen ahora comentarios en español explicando qué hace cada parte — puedes leer el código real a la vez que la documentación.

## 📘 Conceptos generales

| # | Documento | De qué trata |
|---|---|---|
| 00 | [Conceptos básicos de programación](conceptos/00-programacion-basica.md) | Qué es una variable, una clase/objeto, un método, `if`/`for`, herencia, interfaces, `null`, comentarios — el vocabulario de Java que se usa en todo lo demás |
| 01 | [Fundamentos: Bundle, Intent y ciclo de vida](conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) | `Bundle`, `Intent`, `onCreate`, `findViewById`, `AndroidManifest`, el bloque EdgeToEdge que se repite en todos lados |
| 02 | [Diseño basado en pesos](conceptos/02-diseno-basado-en-pesos.md) | `LinearLayout` + `layout_weight`, rejillas proporcionales, estilos reutilizables |
| 03 | [ConstraintLayout](conceptos/03-constraintlayout.md) | Restricciones, bias, cadenas entre vistas, `tools:` |
| 04 | [Toast personalizado](conceptos/04-toast-personalizado.md) | Por qué se usa un `Dialog` sin fondo en vez de `Toast`, `Handler.postDelayed` |
| 05 | [AsyncTask e hilos](conceptos/05-asynctask-e-hilos.md) | Hilo de UI vs hilo de fondo, `doInBackground`/`onProgressUpdate`/`onPostExecute`, alternativas modernas |
| 06 | [Animaciones frame-by-frame](conceptos/06-animaciones-frame-by-frame.md) | `TypedArray`, `obtainTypedArray`, el bucle que crea la ilusión de movimiento, `AnimationDrawable` |
| 07 | [Adaptadores](conceptos/07-adaptadores.md) | `BaseAdapter`, `ArrayAdapter`, `ListView`/`GridView`/`Spinner`, Picasso/Glide, `convertView` |
| 08 | [Transiciones entre Activities](conceptos/08-transiciones.md) | `overridePendingTransition`, transiciones de contenido, shared element transitions, `VIEW_NAME_HEADER_IMAGE`, errores típicos |
| 09 | [Instalación y estructura de proyecto](conceptos/09-instalacion-estructura-proyecto.md) | Android Studio/SDK/Gradle, `res/`, la clase `R`, `adb`/`logcat`, cómo se compila |
| 10 | [La clase Activity, a fondo](conceptos/10-activity-en-profundidad.md) | Qué es realmente una Activity, ciclo de vida completo, tareas y pila de retroceso, `Context` |
| 11 | [Intent, a fondo](conceptos/11-intent-en-profundidad.md) | Explícito vs implícito, todas las partes de un Intent, cómo lo resuelve el sistema |
| 12 | [Hilos, a fondo](conceptos/12-hilos-en-profundidad.md) | Proceso vs hilo, `Looper`/`MessageQueue`/`Handler`, cómo funciona `AsyncTask` por dentro, ANR |
| 13 | [View y ViewGroup, a fondo](conceptos/13-view-viewgroup.md) | Qué son `View`/`ViewGroup`, el parámetro `parent` de `inflate()`, el ciclo measure→layout→draw |
| 14 | [RecyclerView](conceptos/14-recyclerview.md) | La evolución moderna de `ListView`/`GridView`: `ViewHolder`, `LayoutManager`, comparado línea a línea con `CompaniasAdapter` |
| 15 | [Drawables: formas y selectores](conceptos/15-drawables-formas-y-selectores.md) | `<shape>` (degradados, marcos con `stroke`/`corners`), `<selector>` de estado (`state_pressed`), `dimens.xml`, pantalla completa |
| 16 | [Fragmentos](conceptos/16-fragmentos.md) | Ciclo de vida de `Fragment`, `FragmentContainerView`, `FragmentManager` (`add`/`replace`), comunicación Fragment↔Activity con interfaz, `newInstance(Bundle)`, paso de objetos `Serializable`, errores típicos |
| 17 | [Room: base de datos local](conceptos/17-room-base-de-datos.md) | SQLite con Room: `@Entity`, `@PrimaryKey`, `@Dao` (`@Insert`/`@Update`/`@Delete`/`@Query`), `@Database`, patrón Singleton, la regla "nada de BD en el hilo principal" |
| 18 | [Diálogos](conceptos/18-dialogos.md) | `AlertDialog` (patrón Builder, botones sí/no) frente a `DialogFragment` personalizado (`onCreateDialog`/`onCreateView`/`onViewCreated`, `show`/`dismiss`), evolución del login |
| 19 | [TextWatcher y eventos de lista](conceptos/19-textwatcher-y-eventos-de-lista.md) | Listeners y clases anónimas, `OnItemClick` vs `OnItemLongClick` (el `return true`), `TextWatcher` para validar contraseñas, limitaciones del ejemplo |
| 20 | [Executors y LiveData](conceptos/20-executors-y-livedata.md) | `AppExecutors` (`getDiskIO`/`getMainThread`), volver al hilo principal, `LiveData` + `observe` para listas que se refrescan solas, aviso del orden de parámetros |

## 🛠️ Guías

| Guía | De qué trata |
|---|---|
| [01 — Ejecutar la app](guias/01-ejecutar-la-app.md) | Abrir un proyecto, sincronizar Gradle, **el error del AGP incompatible**, emulador, móvil real (USB y Wi-Fi), Logcat, Database Inspector, recorrido con capturas de `EjemploDialogoPersonalizado` |
| [02 — Glosario A–Z](guias/02-glosario.md) | 75 términos explicados en una o dos frases, enlazados a su documento |
| [03 — Errores comunes](guias/03-errores-comunes.md) | Tabla de errores de compilación, de ejecución y "raros", con causa y arreglo; método para depurar |

## 🏋️ Ejercicios y examen

Empieza por [00 — Cómo usar los ejercicios](ejercicios/00-como-usar.md).

| Nivel | Documento | Contenido |
|---|---|---|
| ⭐ 1 | [Java y primera app](ejercicios/01-nivel-1-java-y-primera-app.md) | Métodos, `for`, `ArrayList`; botón, `EditText`, `Toast`, `Intent`, `Bundle` |
| ⭐⭐ 2 | [Diseños XML](ejercicios/02-nivel-2-disenos-xml.md) | Pesos, rejilla 2×2, `ConstraintLayout`, formas y selectores, cálculo de pesos |
| ⭐⭐⭐ 3 | [Adaptadores](ejercicios/03-nivel-3-adaptadores.md) | `ListView`/`GridView` con `BaseAdapter`, `Spinner` personalizado, Glide, `RecyclerView`, Picasso |
| ⭐⭐⭐⭐ 4 | [Toast, AsyncTask, animaciones y transiciones](ejercicios/04-nivel-4-toast-asynctask-animaciones-transiciones.md) | Toast con `Dialog`, `AsyncTask`, frame a frame, transiciones y elemento compartido |
| ⭐⭐⭐⭐ 5 | [Fragmentos](ejercicios/05-nivel-5-fragmentos.md) | Fragmento + `newInstance`, interfaz con la Activity, menú, formulario de 3 fragmentos |
| ⭐⭐⭐⭐⭐ 6 | [Diálogos y Room](ejercicios/06-nivel-6-dialogos-y-room.md) | `AlertDialog`, `DialogFragment`, app de notas con Room, `AppExecutors` y `LiveData` |
| 🔧 7 | [Mejoras del proyecto real](ejercicios/07-nivel-7-mejoras-del-proyecto.md) | 5 arreglos sobre `EjemploDialogoPersonalizado` (+ 2 extras opcionales) |
| 🎯 8 | [Simulacros de examen](ejercicios/08-simulacros-de-examen.md) | 4 exámenes de tus proyectos de clase (con rúbrica) + test de teoría de 30 preguntas |

Todo el código de las soluciones está en `Desktop\android\proyectos-zip\EjemploDialogoPersonalizado-con-soluciones.zip` (se abre con Android Studio y trae un menú "Ejercicios").

## 📂 Proyectos

| Proyecto | Estado | De qué trata |
|---|---|---|
| [EjercicioAdaptadoresFinal](proyectos/EjercicioAdaptadoresFinal.md) | ✅ Completo | Liga→Equipos→Jugadores, adaptadores con Picasso, navegación en 3 niveles, **caso real de depuración de una transición** |
| [AdapterDam2](proyectos/AdapterDam2.md) | ✅ Completo | El más variado: `ListView` + `Spinner` + `GridView`, cada uno con su adaptador, y la única transición de elemento compartido del curso |
| [EjercicioDiseno](proyectos/EjercicioDiseno.md) | ✅ Completo | Menú con diseño por pesos, dos animaciones frame-by-frame, varios "toasts" personalizados |
| [Diseobesos](proyectos/Diseobesos.md) | ✅ Completo | Ejercicio enfocado en `AsyncTask` + toast personalizado |
| [EjercicioFragmentos](proyectos/EjercicioFragmentos.md) | ✅ Completo | El más avanzado de fragmentos: 3 fragmentos encadenados, formulario multi-paso, objetos `Serializable` acumulados en un `GridView` |
| [EjemploFragmentos](proyectos/EjemploFragmentos.md) | ✅ Completo | El ejemplo guía del PDF de Fragmentos: 2 fragmentos + menú contextual que cambia color/texto |
| [equiposFutbol](proyectos/equiposFutbol.md) | ⚠️ Incompleto | Lista de equipos funcional; rejilla de jugadores y navegación sin terminar — incluye un **bug real explicado** (variable `Intent` duplicada) |
| [FragmentosNombres](proyectos/FragmentosNombres.md) | ⚠️ Incompleto | 3 fragmentos; **dos bugs reales explicados** (`findViewById` a un id inexistente, clave de `Bundle` vacía) ya corregidos, y un fragmento sin terminar |
| [anclados](proyectos/anclados.md) | 🌱 Esqueleto | Solo el arranque estándar, sin funcionalidad añadida |
| [EjercicioSergio](proyectos/EjercicioSergio.md) | 🌱 Esqueleto | Solo el arranque estándar, pensado para un ejercicio de `Spinner` sin empezar |
| [Pokemons](proyectos/Pokemons.md) | 🌱 Esqueleto | Proyecto recién creado con la plantilla ("Hello World!"), sin código propio todavía |
| [EjemploDialogoPersonalizado](proyectos/EjemploDialogoPersonalizado.md) | ✅ Completo | Login con `DialogFragment` personalizado + registro de usuarios (CRUD) con base de datos Room, `LiveData`, `AlertDialog` y `TextWatcher`. Incluye orden para construirlo y bugs reales explicados |

## Fuentes

Además del código real de los 12 proyectos, esta documentación se ha contrastado con los PDFs del curso (`C:\Users\Dam2\Desktop\android\pdfs\*.pdf`: instalación, diálogo personalizado y SQLite, diseño por pesos, constraint, toast personalizado, asyncTask, frame by frame, adaptadores, transiciones, fragmentos, y los enunciados de ejercicio). Los conceptos 17–20 y el proyecto `EjemploDialogoPersonalizado` salen del PDF `1-DialogSQLitePersonalizado.pdf` (43 páginas). Donde el código de un proyecto y el material del curso difieren (por ejemplo, una técnica alternativa que el curso enseña pero el proyecto no llegó a usar, o un enunciado que el código no cumple al 100%), el documento correspondiente lo señala explícitamente en vez de ocultarlo.

**Nota:** `EjercicioNombres` (en `AndroidStudioProjects\`) no aparece en esta tabla porque solo contiene metadatos de IDE (`.idea\`), sin ningún código fuente — no es un proyecto real que documentar.

## 🗂️ Cómo está organizada la carpeta `Desktop\android`

```
Desktop\android\
├── documentacion\        ← estás aquí
│   ├── guias\            ← ejecutar la app, glosario, errores comunes
│   ├── conceptos\        ← un documento por tema (00–20)
│   ├── proyectos\        ← un documento por proyecto (12)
│   ├── ejercicios\       ← niveles 1–7 con soluciones y simulacros de examen
│   ├── img\              ← capturas de pantalla reales de las apps y ejercicios
│   └── INDICE.md
├── pdfs\                 ← los PDFs originales del curso
├── proyectos-zip\        ← export en .zip de 11 proyectos (código fuente, sin build/.gradle/.idea) + EjemploDialogoPersonalizado-con-soluciones.zip
└── recursos\             ← material suelto del curso que no es ninguno de tus proyectos: exports antiguos/duplicados, plantillas de ejercicio (.rar) e imágenes sueltas
```

## Cómo usar esta documentación

- Si es tu primera vez, empieza por la [Guía 01](guias/01-ejecutar-la-app.md) y sigue el orden de "Empieza aquí".
- Si un término no lo conoces, búscalo en el [Glosario](guias/02-glosario.md).
- Si algo no compila o la app se cierra, mira [Errores comunes](guias/03-errores-comunes.md).
- Para el examen: sigue los [ejercicios](ejercicios/00-como-usar.md) por niveles y termina con los simulacros.
- Si vienes de un proyecto y no entiendes una línea de código, busca el concepto correspondiente en la tabla de arriba.
- Si quieres repasar un proyecto entero, ábrelo directamente — cada explicación enlaza al concepto general la primera vez que aparece una técnica.
- Los proyectos marcados "⚠️ Incompleto" o "🌱 Esqueleto" son útiles para ver **qué falta** y cómo completarlo siguiendo el patrón de un proyecto ya terminado (siempre indicado en el propio documento).
