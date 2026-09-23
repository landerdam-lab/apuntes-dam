---
tags:
  - android
  - pdf
  - bloque/1
  - enunciado
aliases:
  - EjercicioAnimaciones
---

# Ejercicio de animaciones (enunciado)

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/EjercicioAnimaciones.pdf` · **2 páginas** · **Enunciado**
> **Proyecto:** ninguno · **Fácil:** [ficha](../09-fichas-faciles/EjercicioAnimaciones.md)

## Qué pide

- Al pulsar el botón **Opciones** del menú, mostrar una **animación de carga de 3 segundos**.
- Al terminar, abrir una **Activity vacía**.

## Cómo resolverlo con lo visto

| Opción | Técnica | Ver |
|---|---|---|
| A | `AnimationDrawable` + `Handler.postDelayed(…, 3000)` que lanza el `Intent` | [06](../02-conceptos/06-animaciones-frame-by-frame.md) · [04](../02-conceptos/04-toast-personalizado.md) |
| B | `AsyncTask` con `ProgressBar`, 30 pasos de 100 ms; `onPostExecute` → `startActivity` | [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md) |

Ejercicios parecidos (con solución) en el [Nivel 4](../07-ejercicios/04-nivel-4-toast-asynctask-animaciones-transiciones.md).

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/EjercicioAnimaciones.pdf)
