---
tags:
  - android
  - pdf
  - bloque/1
  - tema/animaciones
aliases:
  - 5-frameByFrame
---

# PDF 5 — Animación frame by frame

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/5-frameByFrame.pdf` · **4 páginas** · **Bloque 1**
> **Proyecto del profesor:** ⚠️ **ninguno** (el botón FRAME BY FRAME de [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md) está sin programar)
> **Tu versión:** [EjercicioDiseno](../05-proyectos-alumno/EjercicioDiseno.md) · **Concepto:** [06 — Frame by frame](../02-conceptos/06-animaciones-frame-by-frame.md) · **Fácil:** [ficha](../09-fichas-faciles/5-frameByFrame.md)

## Qué explica

1. Nueva Activity **`EjemploFrameByFrame`** con un `ImageView` **vacío** y tres botones: **Play**, **Stop** y **Back**.
2. Un drawable `framebyframe.xml` con un **`<animation-list>`**: cada `<item>` es un fotograma con su `android:duration`.
3. En Java: poner la animación como **fondo** del `ImageView` y obtenerla como **`AnimationDrawable`**.
4. **Play** → `start()` si no está en marcha · **Stop** → `stop()` si lo está · **Back** → `finish()`.
5. Dar id al botón del menú y abrir la Activity con un `Intent`.

## Diferencia con el PDF de AsyncTask

Aquí **Android** pasa los fotogramas solo: no hace falta ningún hilo propio, ni `TypedArray`, ni `sleep`.

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/5-frameByFrame.pdf)
