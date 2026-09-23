---
tags:
  - android
  - pdf
  - bloque/1
  - tema/layouts
aliases:
  - 2-constraint
---

# PDF 2 — ConstraintLayout

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/2-constraint.pdf` · **2 páginas** · **Bloque 1**
> **Proyecto:** [DisenyoConstraint](../04-proyectos-profesor/DisenyoConstraint.md) (solo un boceto)
> **Concepto:** [03 — ConstraintLayout](../02-conceptos/03-constraintlayout.md) · **Fácil:** [ficha](../09-fichas-faciles/2-constraint.md)

## Qué explica

1. Reutilizar los recursos (`res/`) del ejercicio de pesos y **vaciar** `activity_main.xml`.
2. En la pestaña **Design → Component Tree**, convertir la raíz en `ConstraintLayout`.
3. Idea clave: cada componente se **enlaza** a los bordes o a otros componentes, y esos enlaces fijan sus márgenes y su posición.
4. Usar la vista **Blueprint**: arrastrar un `TextView` (título), fijar sus restricciones en el panel *Attributes* y poner `wrap_content`.
5. Arrastrar los `ImageButton` y poner la imagen en **`background`**, no en `src`, para que se vea bien.
6. Construir el resto de la interfaz poco a poco, anidando elementos.

## Dónde se aplica

[DisenyoConstraint](../04-proyectos-profesor/DisenyoConstraint.md) solo practica restricciones sueltas (4 `TextView`). **El menú completo con constraint que pide el PDF no está en ningún proyecto del profesor.** Es un buen ejercicio (ver el final de la nota del proyecto).

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/2-constraint.pdf)
