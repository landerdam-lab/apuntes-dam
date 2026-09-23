---
tags:
  - android
  - pdf
  - bloque/1
  - enunciado
aliases:
  - 1-EjercicioDiseno
---

# PDF 1b — Ejercicio de diseño (enunciado)

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/1-EjercicioDiseno.pdf` · **1 página** · **Enunciado** · **Bloque 1**
> **Proyecto del profesor:** ninguno · **Tu solución:** [EjercicioDiseno](../05-proyectos-alumno/EjercicioDiseno.md)
> **Fácil:** [ficha](../09-fichas-faciles/1-EjercicioDiseno.md)

## Qué pide

1. Reproducir un menú tipo "agenda" a partir de una imagen, con **fondo azul claro**.
2. **Sombra** en los `TextView` bajo cada botón (`shadowColor`, `shadowDx`, `shadowDy`, `shadowRadius`) y **estilos reutilizables** para títulos, pies y etiquetas.
3. **Botones con dos imágenes** (normal / pulsado) a partir de las 8 imágenes de `recursos_ejercicio` → `codigo/recursos/RECURSOS_EJERCCIO_DISENO.rar`.
4. **Sin barra de título ni de notificaciones.**
5. El botón **Nuevo** abre una Activity nueva.
6. Diseño **adaptable** a distintos tamaños (pesos, no medidas fijas).

## Cómo se resuelve con lo visto

| Requisito | Técnica | Dónde verla |
|---|---|---|
| Rejilla adaptable | Pesos | [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md) |
| Estilos con sombra | `<style>` con `android:shadow*` | [28 — Recursos](../02-conceptos/28-recursos-values.md) |
| Botón pulsado | `<selector>` | `boton_pulsado.xml` de DisenyoPesos |
| Nueva pantalla | `Intent` + declararla en el manifest | [Cambiar de pantalla](../06-codigo-reutilizable/02-cambiar-de-pantalla.md) |

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/1-EjercicioDiseno.pdf)
