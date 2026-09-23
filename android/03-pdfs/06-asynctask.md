---
tags:
  - android
  - pdf
  - bloque/1
  - tema/hilos
aliases:
  - 4-asyncTask
---

# PDF 4 — AsyncTask (hilos)

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/4-asyncTask.pdf` · **7 páginas** · **Bloque 1**
> **Proyectos:** [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md) (`EjemploAsynctask` + `ProgressAndando`) · [EjercicioAsyncTaskAlumnos](../04-proyectos-profesor/EjercicioAsyncTaskAlumnos.md)
> **Conceptos:** [05 — AsyncTask e hilos](../02-conceptos/05-asynctask-e-hilos.md) · [12 — Hilos a fondo](../02-conceptos/12-hilos-en-profundidad.md) · **Fácil:** [ficha](../09-fichas-faciles/4-asyncTask.md)

## Qué explica

1. Crear la Activity `EjemploAsyncTask` con un `TextView` de porcentaje, una **`ProgressBar` horizontal** y un `ImageView`.
2. Declarar las imágenes del muñeco en un `string-array` y leerlas con **`obtainTypedArray`** (un `TypedArray` de recursos, no de textos).
3. Configurar la barra: `setMax(100)`, `setProgress(0)`, color de fondo.
4. Teoría de **`AsyncTask`** y sus cuatro métodos: `onPreExecute`, `doInBackground`, `onProgressUpdate` y `onPostExecute`.
5. **Pero el código del PDF usa otra técnica:** `ProgressAndando implements Runnable`, lanzado con un `ThreadExecutor` propio (`new Thread(command).start()`), que actualiza barra, imagen y texto **desde el hilo secundario** y muestra el `Toast` "Tarea finalizada" al acabar.
6. Abrir la Activity desde `MainActivity` y **pasar un texto con `Bundle`** a una Activity vacía (`EmptyActivity`).

> [!warning] ⚠️ Cuidado: el código del PDF toca la interfaz desde otro hilo
> `foto.setImageResource(...)`, `texto.setText(...)` y `Toast.makeText(...)` dentro de `run()` de un `Thread` provocan `CalledFromWrongThreadException` / `Can't toast on a thread that has not called Looper.prepare()`. **El proyecto del profesor NO copia esa versión:** `ProgressAndando` sí hereda de `AsyncTask` y pinta en `onProgressUpdate` (hilo principal). Estudia la del proyecto. La explicación completa de por qué la versión `Runnable` falla está en [05 — AsyncTask e hilos](../02-conceptos/05-asynctask-e-hilos.md).

## Dónde se aplica

| Punto del PDF | Proyecto / archivo |
|---|---|
| `TypedArray` de imágenes | DisenyoPesos → `values/frames.xml` + `EjemploAsynctask.onCreate` |
| Barra de progreso | `activity_ejemplo_asynctask.xml` (`pbAnimacion`) |
| Hilo con progreso | `ProgressAndando extends AsyncTask` (DisenyoPesos) · `AsyntaskDamCaballo` (EjercicioAsyncTaskAlumnos) |
| Toast al terminar | Solo EjercicioAsyncTaskAlumnos ("Adios") |
| Pasar datos a otra Activity | No aparece en estos proyectos → [Pasar datos](../06-codigo-reutilizable/03-pasar-datos.md) |

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/4-asyncTask.pdf)
