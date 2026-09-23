---
tags:
  - android
  - pdf
  - bloque/1
  - tema/adaptadores
aliases:
  - 6-Adaptadores
---

# PDF 6 — Adaptadores

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/6-Adaptadores.pdf` · **27 páginas** · **Bloque 1**
> **Proyectos:** [AdapterDam2](../04-proyectos-profesor/AdapterDam2.md) (versión del profesor del proyecto `AdapterDam` del PDF) · [EjercicioSpinner](../04-proyectos-profesor/EjercicioSpinner.md) · [EjercicioAdaptadoresFinal](../04-proyectos-profesor/EjercicioAdaptadoresFinal.md)
> **Conceptos:** [07 — Adaptadores](../02-conceptos/07-adaptadores.md) · [14 — RecyclerView](../02-conceptos/14-recyclerview.md) · **Fácil:** [ficha](../09-fichas-faciles/6-Adaptadores.md)

## Qué explica

El PDF construye el proyecto **`AdapterDam`** con estas Activities:

| Activity del PDF | Qué enseña | En AdapterDam2 del profesor |
|---|---|---|
| `SpinnerDam` | `Spinner` con `ArrayAdapter` de fábrica y luego `SpinnerDamAdapter` propio (`getView` + `getDropDownView`) | `SpinnerDam2Activity` + `SpinnerDam2Adapter` |
| `ListDam` | Modelo `CompaniaTelefonica` + `CompaniasAdapter extends BaseAdapter` (logo, nombre, precio, filas alternas) | `ListDam2Activity` + `CompaniasAdapter` |
| `GridDam` | `GridView` de imágenes con `ImageAdapter` y `TypedArray`; el problema de memoria con imágenes grandes → **Glide** | `GridViewDam2Activity` + `ImageAdapter` |
| `GridRecyclerDam` | La misma rejilla con **`RecyclerView`** (`ImageRecyclerAdapter` + `ImagenViewHolder`) y la dependencia `androidx.recyclerview:recyclerview:1.3.2` | ⚠️ **No está** en AdapterDam2 |

Puntos clave: los 4 métodos de `BaseAdapter` (`getCount`, `getItem`, `getItemId`, `getView`), inflar la fila con `LayoutInflater`, la diferencia entre la fila cerrada y la desplegada en el Spinner, y Glide para no saturar la memoria.

> [!note] Lo que el PDF explica y el proyecto no tiene
> La parte de **RecyclerView** (`GridRecyclerDam`). La tienes explicada en [14 — RecyclerView](../02-conceptos/14-recyclerview.md) y en [aaaa](../04-proyectos-profesor/aaaa.md) (con `ListAdapter`).

> [!note] Lo que los proyectos usan y el PDF no explica
> **Picasso** (EjercicioAdaptadoresFinal, EjercicioPokemon), el permiso **`INTERNET`** y pasar objetos con **`Serializable`**.

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/6-Adaptadores.pdf)
