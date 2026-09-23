---
tags:
  - android
  - indice
aliases:
  - Android — Inicio
  - Índice de Android
---

# 📱 Android — Inicio

Apuntes completos de **Programación Multimedia y Dispositivos Móviles / Diseño de Interfaces (DAM 2)**: los 15 PDFs del curso, los 12 proyectos del profesor (`AndroidEjemploProyectos`) y tus 12 proyectos, explicados paso a paso, enlazados entre sí y con código listo para reutilizar.

> [!tip] 🚀 ¿Por dónde empiezo?
> 1. [Guía 01 — Cómo abrir y ejecutar una app](01-guias/01-ejecutar-la-app.md)
> 2. [Plan de estudio paso a paso](01-guias/04-plan-de-estudio.md), que te dice qué PDF, proyecto y ejercicio toca en cada momento
> 3. [Mapa de proyectos del profesor](04-proyectos-profesor/00-mapa-de-proyectos.md)
> 4. Si nunca has programado: [00 — Programación básica](02-conceptos/00-programacion-basica.md)

## 🗂️ Cómo está organizado

```
android/
├── INICIO.md                 ← estás aquí
├── INVENTARIO.md             ← lista de TODO: PDFs, clases, layouts, manifest, Gradle, librerías
├── 01-guias/                 ← ejecutar la app, glosario, errores, plan de estudio, malas prácticas
├── 02-conceptos/             ← un documento por tema (00–28)
├── 03-pdfs/                  ← resumen de cada PDF + relación teoría ↔ práctica
├── 04-proyectos-profesor/    ← los 12 proyectos de AndroidEjemploProyectos, a fondo
├── 05-proyectos-alumno/      ← tus 12 proyectos, fragmento a fragmento
├── 06-codigo-reutilizable/   ← fragmentos listos para copiar, por categorías
├── 07-ejercicios/            ← niveles 1–7 con solución + simulacros de examen
├── 08-repaso-examen/         ← preguntas tipo examen con respuesta
├── 09-fichas-faciles/        ← versión sin tecnicismos de cada proyecto y PDF
├── adjuntos/                 ← img/ (capturas) y pdfs/ (los PDFs originales)
└── codigo/                   ← el código: AndroidEjemploProyectos/ (profesor), proyectos-alumno/, proyectos-zip/, recursos/
```

> [!info] Leyenda de marcas
> 📌 **Importante** para estudiar · ✅ **Reutilizable** (se puede copiar) · ⚠️ **Cuidado** (errores, bugs, código antiguo) · 💡 **Recomendación** moderna. En Obsidian salen como *callouts* de colores; los que tienen `-` en el título se pliegan (pulsa para abrir).

## 🛠️ Guías

| Guía | De qué trata |
|---|---|
| [01 — Ejecutar la app](01-guias/01-ejecutar-la-app.md) | Abrir un proyecto, Gradle, el error del AGP incompatible, emulador, móvil real, Logcat, Database Inspector |
| [02 — Glosario A–Z](01-guias/02-glosario.md) | 130 términos en tablas: explicación sencilla + ejemplo |
| [03 — Errores comunes](01-guias/03-errores-comunes.md) | Errores de compilación, de ejecución y "raros", con causa y arreglo |
| [04 — Plan de estudio](01-guias/04-plan-de-estudio.md) | Orden recomendado + 11 ejercicios con pista y solución + errores para provocar |
| [05 — Código antiguo y malas prácticas](01-guias/05-codigo-antiguo-y-malas-practicas.md) | Los 16 bugs reales de los proyectos, lo obsoleto y cómo se hace hoy |

## 📘 Conceptos

| # | Concepto | # | Concepto |
|---|---|---|---|
| 00 | [Programación básica (Java)](02-conceptos/00-programacion-basica.md) | 15 | [Drawables: formas y selectores](02-conceptos/15-drawables-formas-y-selectores.md) |
| 01 | [Fundamentos: Bundle, Intent, ciclo de vida](02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) | 16 | [Fragmentos](02-conceptos/16-fragmentos.md) |
| 02 | [Diseño basado en pesos](02-conceptos/02-diseno-basado-en-pesos.md) | 17 | [Room: base de datos](02-conceptos/17-room-base-de-datos.md) |
| 03 | [ConstraintLayout](02-conceptos/03-constraintlayout.md) | 18 | [Diálogos](02-conceptos/18-dialogos.md) |
| 04 | [Toast personalizado](02-conceptos/04-toast-personalizado.md) | 19 | [TextWatcher y eventos de lista](02-conceptos/19-textwatcher-y-eventos-de-lista.md) |
| 05 | [AsyncTask e hilos](02-conceptos/05-asynctask-e-hilos.md) | 20 | [Executors y LiveData](02-conceptos/20-executors-y-livedata.md) |
| 06 | [Animaciones frame by frame](02-conceptos/06-animaciones-frame-by-frame.md) | 21 | 🆕 [Sensores](02-conceptos/21-sensores.md) |
| 07 | [Adaptadores](02-conceptos/07-adaptadores.md) | 22 | 🆕 [Cámara, almacenamiento y Activity Result API](02-conceptos/22-camara-y-almacenamiento.md) |
| 08 | [Transiciones](02-conceptos/08-transiciones.md) | 23 | 🆕 [Voz e intents implícitos](02-conceptos/23-voz-e-intents-implicitos.md) |
| 09 | [Instalación y estructura de proyecto](02-conceptos/09-instalacion-estructura-proyecto.md) | 24 | 🆕 [Manifest y permisos](02-conceptos/24-manifest-y-permisos.md) |
| 10 | [Activity a fondo](02-conceptos/10-activity-en-profundidad.md) | 25 | 🆕 [Gradle y librerías](02-conceptos/25-gradle-y-librerias.md) |
| 11 | [Intent a fondo](02-conceptos/11-intent-en-profundidad.md) | 26 | 🆕 [Menús y Toolbar](02-conceptos/26-menus-y-toolbar.md) |
| 12 | [Hilos a fondo](02-conceptos/12-hilos-en-profundidad.md) | 27 | 🆕 [View Binding, ViewModel y Navigation](02-conceptos/27-navigation-viewbinding-viewmodel.md) |
| 13 | [View y ViewGroup](02-conceptos/13-view-viewgroup.md) | 28 | 🆕 [Recursos: strings, colors, dimens, estilos, temas](02-conceptos/28-recursos-values.md) |
| 14 | [RecyclerView](02-conceptos/14-recyclerview.md) | | |

## 📄 PDFs del curso

Empieza por [Relación PDFs ↔ proyectos](03-pdfs/00-relacion-pdfs-proyectos.md) (qué teoría se aplica dónde, y qué falta en cada lado).

| Bloque 1 — Diseño de interfaces | Bloque 2 — Programación móvil |
|---|---|
| [0 — Instalación](03-pdfs/01-instalacion-android.md) | [1 — Diálogo personalizado + SQLite](03-pdfs/12-dialogo-sqlite-personalizado.md) |
| [1 — Diseño por pesos](03-pdfs/02-diseno-basado-en-pesos.md) | 🆕 [2 — Sensores](03-pdfs/13-sensores.md) |
| [1b — Ejercicio de diseño](03-pdfs/03-ejercicio-diseno.md) | 🆕 [3 — Cámara y almacenamiento](03-pdfs/14-camara-y-almacenamiento.md) |
| [2 — Constraint](03-pdfs/04-constraint.md) | 🆕 [4 — Voz](03-pdfs/15-voz.md) |
| [3 — Toast personalizado](03-pdfs/05-toast-personalizado.md) | |
| [4 — AsyncTask](03-pdfs/06-asynctask.md) | |
| [5 — Frame by frame](03-pdfs/07-frame-by-frame.md) · [Ejercicio animaciones](03-pdfs/08-ejercicio-animaciones.md) | |
| [6 — Adaptadores](03-pdfs/09-adaptadores.md) | |
| [7 — Transiciones](03-pdfs/10-transiciones.md) | |
| [8 — Fragmentos](03-pdfs/11-fragmentos.md) | |

## 👨‍🏫 Proyectos del profesor (`codigo/AndroidEjemploProyectos/`)

| Proyecto | Qué enseña | Nivel |
|---|---|---|
| [MyApplication](04-proyectos-profesor/MyApplication.md) | La plantilla: estructura, `onCreate`, manifest, Gradle | ⭐ |
| [DisenyoConstraint](04-proyectos-profesor/DisenyoConstraint.md) | Restricciones de ConstraintLayout | ⭐ |
| [DisenyoPesos](04-proyectos-profesor/DisenyoPesos.md) | Pesos, drawables, estilos, toast con Dialog, AsyncTask | ⭐⭐ |
| [EjercicioAsyncTaskAlumnos](04-proyectos-profesor/EjercicioAsyncTaskAlumnos.md) | AsyncTask (caballo) | ⭐⭐ |
| [AdapterDam2](04-proyectos-profesor/AdapterDam2.md) | ListView, Spinner, GridView, Glide, transiciones | ⭐⭐⭐ |
| [EjercicioSpinner](04-proyectos-profesor/EjercicioSpinner.md) | Spinner personalizado | ⭐⭐⭐ |
| [EjercicioAdaptadoresFinal](04-proyectos-profesor/EjercicioAdaptadoresFinal.md) | 3 niveles, Serializable, Picasso | ⭐⭐⭐ |
| [EjemploFragmentos2](04-proyectos-profesor/EjemploFragmentos2.md) | Fragmentos, interfaz, menú | ⭐⭐⭐⭐ |
| [EjercicioFragmentos](04-proyectos-profesor/EjercicioFragmentos.md) | 3 fragmentos encadenados | ⭐⭐⭐⭐ |
| [EjercicioPokemon](04-proyectos-profesor/EjercicioPokemon.md) | CRUD con Room y LiveData | ⭐⭐⭐⭐⭐ |
| [EjemploDialogoPersonalizado](04-proyectos-profesor/EjemploDialogoPersonalizado.md) | Login, Room, **sensores, cámara, voz** | ⭐⭐⭐⭐⭐ |
| [aaaa](04-proyectos-profesor/aaaa.md) | Android moderno: Navigation, View Binding, ViewModel | ⭐⭐⭐⭐⭐⭐ |

Resumen, orden recomendado y comparación con tus proyectos: [Mapa de proyectos](04-proyectos-profesor/00-mapa-de-proyectos.md). Cada carpeta de proyecto tiene además su propio `README.md`.

## 🧑‍🎓 Tus proyectos (`codigo/proyectos-alumno/`)

| Proyecto | Estado | De qué trata |
|---|---|---|
| [EjercicioAdaptadoresFinal](05-proyectos-alumno/EjercicioAdaptadoresFinal.md) | ✅ | Liga → Equipos → Jugadores, con un caso real de depuración de una transición |
| [AdapterDam2](05-proyectos-alumno/AdapterDam2.md) | ✅ | ListView + Spinner + GridView y *shared element* |
| [EjercicioDiseno](05-proyectos-alumno/EjercicioDiseno.md) | ✅ | Menú por pesos, 2 animaciones frame by frame, toasts |
| [Diseobesos](05-proyectos-alumno/Diseobesos.md) | ✅ | AsyncTask + toast personalizado |
| [EjercicioFragmentos](05-proyectos-alumno/EjercicioFragmentos.md) | ✅ | 3 fragmentos, `Serializable` en un GridView |
| [EjemploFragmentos](05-proyectos-alumno/EjemploFragmentos.md) | ✅ | Ejemplo guía de fragmentos + menú |
| [EjemploDialogoPersonalizado](05-proyectos-alumno/EjemploDialogoPersonalizado.md) | ✅ | Login + registro con Room, explicado línea a línea |
| [equiposFutbol](05-proyectos-alumno/equiposFutbol.md) | ⚠️ | Lista de equipos; bug de `Intent` duplicado explicado |
| [FragmentosNombres](05-proyectos-alumno/FragmentosNombres.md) | ⚠️ | 3 fragmentos; dos bugs reales explicados |
| [anclados](05-proyectos-alumno/anclados.md) · [EjercicioSergio](05-proyectos-alumno/EjercicioSergio.md) · [Pokemons](05-proyectos-alumno/Pokemons.md) | 🌱 | Esqueletos |

> `codigo/proyectos-alumno/EjemploDialogoPersonalizado-con-soluciones` contiene las soluciones de los ejercicios (menú "Ejercicios").

## ✅ Código reutilizable

[Índice](06-codigo-reutilizable/00-indice.md): [botones y eventos](06-codigo-reutilizable/01-botones-y-eventos.md) · [cambiar de pantalla](06-codigo-reutilizable/02-cambiar-de-pantalla.md) · [pasar datos](06-codigo-reutilizable/03-pasar-datos.md) · [mensajes y diálogos](06-codigo-reutilizable/04-mensajes-toast-dialogos.md) · [formularios](06-codigo-reutilizable/05-formularios.md) · [listas](06-codigo-reutilizable/06-listas-y-adaptadores.md) · [Room](06-codigo-reutilizable/07-base-de-datos-room.md) · [preferencias](06-codigo-reutilizable/08-preferencias.md) · [utilidades](06-codigo-reutilizable/09-utilidades.md) · [sensores, cámara y voz](06-codigo-reutilizable/10-multimedia-sensores-camara-voz.md)

## 🏋️ Ejercicios y examen

| | Documento |
|---|---|
| Cómo usarlos | [00 — Cómo usar los ejercicios](07-ejercicios/00-como-usar.md) |
| ⭐ → ⭐⭐⭐⭐⭐ | [Nivel 1](07-ejercicios/01-nivel-1-java-y-primera-app.md) · [2](07-ejercicios/02-nivel-2-disenos-xml.md) · [3](07-ejercicios/03-nivel-3-adaptadores.md) · [4](07-ejercicios/04-nivel-4-toast-asynctask-animaciones-transiciones.md) · [5](07-ejercicios/05-nivel-5-fragmentos.md) · [6](07-ejercicios/06-nivel-6-dialogos-y-room.md) |
| 🔧 Mejoras del proyecto | [Nivel 7](07-ejercicios/07-nivel-7-mejoras-del-proyecto.md) |
| 🎯 Examen | [Simulacros](07-ejercicios/08-simulacros-de-examen.md) · [Preguntas tipo examen (45, con respuesta)](08-repaso-examen/01-preguntas-tipo-examen.md) |
| 🧩 Sobre los proyectos del profesor | Ejercicios A–K del [plan de estudio](01-guias/04-plan-de-estudio.md) y al final de cada nota de proyecto |

## 🧸 Fichas fáciles

Versión con analogías y sin código de cada proyecto y PDF: [índice de fichas](09-fichas-faciles/INDICE.md).

## 💡 Trucos de Obsidian para este vault
- **Ctrl+O**: abrir una nota por nombre (escribe "Room", "Spinner", "Pokemon"…).
- **Ctrl+Shift+F**: buscar un texto en todos los apuntes (por ejemplo, `findViewById`).
- **Vista de grafo**: los colores separan conceptos (azul), PDFs (naranja), proyectos del profesor (verde), tus proyectos (turquesa), código reutilizable (morado), ejercicios (rojo) y fichas (amarillo).
- **Etiquetas**: `#tema/room`, `#tema/fragmentos`, `#nivel/3`… en el panel de etiquetas para filtrar.
- Los PDFs se pueden leer **dentro** de Obsidian: al final de cada nota de `03-pdfs` hay un desplegable "Ver el PDF original".
