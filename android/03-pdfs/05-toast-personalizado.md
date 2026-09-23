---
tags:
  - android
  - pdf
  - bloque/1
  - tema/mensajes
aliases:
  - 3-ToasPersonalizado
---

# PDF 3 — Toast personalizado

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/3-ToasPersonalizado.pdf` · **4 páginas** · **Bloque 1**
> **Proyecto:** [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md) → botón **TOAST** + `toast_per.xml`
> **Concepto:** [04 — Toast personalizado](../02-conceptos/04-toast-personalizado.md) · **Fácil:** [ficha](../09-fichas-faciles/3-ToasPersonalizado.md)

## Qué explica

1. **Toast normal:** `Toast.makeText(contexto, "texto", Toast.LENGTH_SHORT).show()`.
2. **Toast propio:**
   1. Diseñar su aspecto en un layout aparte (`toast_per.xml`: imagen + texto).
   2. **Inflarlo** con `getLayoutInflater().inflate(...)`.
   3. Meterlo en un **`Dialog`** (`setContentView(vista)`). El PDF no lo dice, pero el motivo es que `Toast.setView()` está obsoleto y en Android 11+ ya no muestra vistas propias.
   4. Poner el fondo de la ventana **transparente**.
   5. Cerrarlo solo con un **`Handler`**: `new Handler(Looper.getMainLooper()).postDelayed(dialogo::dismiss, 2000)`.

## Dónde se aplica

`MainActivity` de [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md), botón `btnToast`.

> [!note] Diferencia con el proyecto
> En el proyecto, el `Handler` que cierra el aviso a los 2 s está **comentado**. En su lugar, el aviso se cierra al **tocar la imagen**.

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/3-ToasPersonalizado.pdf)
