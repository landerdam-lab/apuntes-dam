---
tags:
  - android
  - pdf
  - bloque/2
  - tema/dialogos
  - tema/room
aliases:
  - 1-DialogSQLitePersonalizado
---

# PDF 1 (bloque 2) — Diálogo personalizado y SQLite (Room)

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/1-DialogSQLitePersonalizado.pdf` · **43 páginas** (el más largo) · **Bloque 2** (Programación móvil)
> **Proyectos:** [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) (ejemplo guía) · [EjercicioPokemon](../04-proyectos-profesor/EjercicioPokemon.md) (ejercicio)
> **Conceptos:** [18 — Diálogos](../02-conceptos/18-dialogos.md) · [17 — Room](../02-conceptos/17-room-base-de-datos.md) · [20 — Executors y LiveData](../02-conceptos/20-executors-y-livedata.md) · [19 — TextWatcher](../02-conceptos/19-textwatcher-y-eventos-de-lista.md) · **Fácil:** [ficha](../09-fichas-faciles/1-DialogSQLitePersonalizado.md)

## Qué explica (en orden)

1. **Login con `DialogFragment`:** layout `dialog_personalizado.xml` (logo, usuario, contraseña, Aceptar/Cancelar) y la clase `LoginDialogFrag` (`onCreateDialog`, `onCreateView`, `onViewCreated`, `dismiss`). Se muestra desde `MainActivity` con `show(getSupportFragmentManager(), "Login")`.
2. **Room:** dependencias `room-runtime` + `room-compiler` (`annotationProcessor`), la entidad `Usuario` (`@Entity`, `@PrimaryKey(autoGenerate)`, `@Ignore`), `UsuariosDao` (`@Insert`, `@Update`, `@Delete`, `@Query`) y `AppDatabase` (`@Database`, Singleton con `synchronized`).
3. **Hilos para la BD:** `AppExecutors` con `diskIO`, `mainThread` (`MainThreadExecutor` con `Handler(Looper.getMainLooper())`) y `networkIO`.
4. **Registro:** `RegisterActivity` con formulario y `ListView`, y `UsuariosAdapter extends ArrayAdapter<Usuario>`.
5. **`LiveData` + `observe`** para que la lista se refresque sola.
6. **Editar** (clic en la fila), **borrar** (pulsación larga + `AlertDialog`) y **validar contraseñas** (`TextWatcher`).
7. **Login real:** consulta `WHERE usuario = :usu AND password = :pass` y, si existe, abrir `CentralActivity` (que ya incluye un botón **Volley** para un tema posterior).

## Dónde se aplica

Todo en [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md). El mismo patrón (entidad + DAO + Database + Executors + LiveData) se repite en [EjercicioPokemon](../04-proyectos-profesor/EjercicioPokemon.md).

> [!warning] ⚠️ Cuidado: el orden de parámetros de `AppExecutors` ya viene mal en el PDF
> El constructor es `AppExecutors(diskIO, mainThread, networkIO)`, pero **el propio PDF** lo llama con `(newSingleThreadExecutor(), newFixedThreadPool(3), new MainThreadExecutor())`. Es decir, `mainThread` recibe un *pool* de hilos. Los dos proyectos copian el error. Consecuencias y arreglo en [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) (sección de bugs) y [20 — Executors y LiveData](../02-conceptos/20-executors-y-livedata.md). Por la misma razón, el `Toast "Bienvenido"` que el PDF muestra tras el login no funcionaría: en el proyecto no está.

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/1-DialogSQLitePersonalizado.pdf)
