---
tags:
  - android
  - pdf
  - bloque/1
  - tema/fragmentos
aliases:
  - 8-Fragmentos
---

# PDF 8 — Fragmentos

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/8-Fragmentos.pdf` · **13 páginas** · **Bloque 1**
> **Proyectos:** [EjemploFragmentos2](../04-proyectos-profesor/EjemploFragmentos2.md) (el ejemplo guía, casi idéntico) · [EjercicioFragmentos](../04-proyectos-profesor/EjercicioFragmentos.md) (ejercicio)
> **Conceptos:** [16 — Fragmentos](../02-conceptos/16-fragmentos.md) · [26 — Menús y Toolbar](../02-conceptos/26-menus-y-toolbar.md) · **Fácil:** [ficha](../09-fichas-faciles/8-Fragmentos.md)

## Qué explica

1. **Qué es un fragmento** y su **ciclo de vida**: `onAttach` → `onCreateView` → `onViewCreated` → … → `onDetach`, con plantillas de cada método.
2. `onCreateView` **infla** el layout del fragmento; en `onViewCreated` se buscan las vistas **con `view.findViewById`**.
3. Layout de la Activity con dos **`FragmentContainerView`** y cargar los fragmentos con `getSupportFragmentManager().beginTransaction().add(...).commit()`.
4. **Comunicación Fragment → Activity:** paquete `InterfacesDam` con la interfaz `IControlFragmentos`; la Activity la implementa y el fragmento la obtiene en `onAttach` con `(IControlFragmentos) context`.
5. **Activity → Fragment:** `newInstance(Bundle)` + `setArguments` / `getArguments` y `replace(...)`.
6. **Menú contextual:** `MaterialToolbar` en el layout + `setSupportActionBar`, `res/menu/menu_main.xml`, `onCreateOptionsMenu` y `onOptionsItemSelected` para cambiar el color (rojo/verde).

## Dónde se aplica

| Punto del PDF | Proyecto |
|---|---|
| Todo el ejemplo | EjemploFragmentos2 (paquete `com.example.ejemplofragmentos`) |
| Interfaz en el paquete `InterfacesDam` | En EjemploFragmentos2 está en el paquete raíz; en EjercicioFragmentos, en `interfaces/` |
| 3 fragmentos + lista | EjercicioFragmentos |

> [!note] Lo que el proyecto usa y el PDF no explica
> Pasar objetos con `putSerializable` (EjercicioFragmentos) y un `BaseAdapter` dentro de un fragmento.

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/8-Fragmentos.pdf)
