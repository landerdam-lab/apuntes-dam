---
tags:
  - android
  - pdf
  - bloque/1
  - tema/layouts
aliases:
  - 1-DiseñoBasadoEnPesos
---

# PDF 1 — Diseño basado en pesos

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/1-DiseñoBasadoEnPesos.pdf` · **18 páginas** · **Bloque 1**
> **Proyecto:** [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md) (`activity_main.xml`, drawables y estilos)
> **Conceptos:** [02 — Pesos](../02-conceptos/02-diseno-basado-en-pesos.md) · [15 — Drawables](../02-conceptos/15-drawables-formas-y-selectores.md) · [28 — Recursos](../02-conceptos/28-recursos-values.md) · **Fácil:** [ficha](../09-fichas-faciles/1-DiseñoBasadoEnPesos.md)

## Qué explica

1. **Estructura de `res/`**: `layout/`, `drawable/`, `values/` (`strings.xml`, `colors.xml`, `themes.xml`) y la creación de **`dimens.xml`**.
2. **`LinearLayout` + `layout_weight`**: dividir la pantalla en **cabecera / cuerpo / pie**. La dimensión que se reparte debe valer **`0dp`**.
3. Una **rejilla de 6 botones** (3 filas × 2 columnas) anidando `LinearLayout` horizontales dentro de uno vertical. Cada celda tiene un `ImageButton` y un `TextView`.
4. **Estilos**: crear `cursoTitulo` y heredar `cursoTitulo.subtitulo`, con tamaños sacados de `dimens.xml` (`tamanoTitulo 20sp`, `tamanoSubtitulo 10sp`, `espacioTitulo 7dp`).
5. **Drawables XML**: fondo degradado (`<gradient>`), cabecera con bordes redondeados (`<corners>`, `<stroke>`) y **selector** que cambia la imagen del botón al pulsarlo (`state_pressed`).
6. **Pantalla completa**: quitar la barra de título y la de notificaciones desde el tema.

## Dónde se aplica

| Punto del PDF | Archivo en DisenyoPesos |
|---|---|
| Cabecera/cuerpo/pie 2/7/1 | `res/layout/activity_main.xml` |
| `dimens.xml` | `res/values/dimens.xml` (mismos valores) |
| Estilos | `res/values/themes.xml` → `cursoTitulo`, `cursoTitulo.subtitulo` |
| Degradado / borde / selector | `drawable/fondo.xml`, `cabecera.xml`, `boton_pulsado.xml` |

> [!note] Diferencia con el proyecto
> El PDF termina con la app a pantalla completa. El proyecto del profesor usa el tema `NoActionBar` (sin barra de título) pero mantiene la barra de estado, con el bloque EdgeToEdge.

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/1-Dise%C3%B1oBasadoEnPesos.pdf)
