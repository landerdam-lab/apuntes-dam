---
tags:
  - android
  - pdf
  - bloque/1
  - tema/transiciones
aliases:
  - 7-Transiciones
---

# PDF 7 — Transiciones

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/7-Transiciones.pdf` · **16 páginas** · **Bloque 1**
> **Proyecto:** [AdapterDam2](../04-proyectos-profesor/AdapterDam2.md) (el PDF parte del proyecto de adaptadores)
> **Concepto:** [08 — Transiciones](../02-conceptos/08-transiciones.md) · **Fácil:** [ficha](../09-fichas-faciles/7-Transiciones.md)

## Qué explica

1. **Tipos:** transición de **entrada**, de **salida** y de **elementos compartidos**. Efectos de entrada/salida: **explode** (expandir), **slide** (deslizar) y **fade** (difuminar). Operaciones de elementos compartidos: `changeBounds`, `changeClipBounds`, `changeTransform` y `changeImageTransform`. Disponibles desde la API 21.
2. **Gradle:** el PDF muestra un `build.gradle` **antiguo en Groovy** (`compileSdkVersion 30`, Java 1.8, Glide 4.10.0) y pide actualizar las versiones con `Alt+Intro` y sincronizar.
3. **Recursos:** crear `res/transition/` con `explode.xml` y `slide.xml` (`slideEdge="bottom"`).
4. **Transición global por tema:** `windowActivityTransitions`, `windowEnterTransition` y `windowExitTransition` en `themes.xml`.
5. **Transición propia de una Activity:** en `ListDam`, `getWindow().setEnterTransition(slide)` **antes** de `setContentView`. Solo se ve si la Activity se lanza con `ActivityOptionsCompat.makeSceneTransitionAnimation(...)`.
6. **Elemento compartido:** `DetalleActivity` con `public static final String VIEW_NAME_HEADER_IMAGE`, `ViewCompat.setTransitionName(...)` en el destino y `new Pair<>(vista, VIEW_NAME_HEADER_IMAGE)` en el origen. Ojo: el `Pair` debe importarse de **`androidx.core.util`**.

## Dónde se aplica

| Punto del PDF | AdapterDam2 |
|---|---|
| `res/transition/explode.xml`, `slide.xml` | ✔ iguales |
| Tema con transiciones | ✔ pero con `windowExitTransition = slide` (en el PDF, `explode`) |
| `setEnterTransition(slide)` en la lista | ✔ `ListDam2Activity` |
| Lanzar con `makeSceneTransitionAnimation` | ✔ botón ListView de `MainActivity` |
| *Shared element* Grid → Detalle | ✔ `GridViewDam2Activity` → `DetalleActivity` |

> [!note] Lo que el proyecto usa y el PDF no explica
> `overridePendingTransition(R.anim.entrada, R.anim.salida)` con recursos `res/anim/` (botones Spinner y GridView de AdapterDam2). Explicado en [08 — Transiciones](../02-conceptos/08-transiciones.md).

> [!warning] ⚠️ Cuidado: el Gradle del PDF es antiguo
> Los proyectos actuales usan **Kotlin DSL** (`build.gradle.kts`) y un **catálogo de versiones** (`libs.versions.toml`). No copies el bloque Groovy del PDF en un proyecto nuevo: traduce solo la dependencia, `implementation("com.github.bumptech.glide:glide:4.16.0")`. Ver [25 — Gradle](../02-conceptos/25-gradle-y-librerias.md).

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/7-Transiciones.pdf)
