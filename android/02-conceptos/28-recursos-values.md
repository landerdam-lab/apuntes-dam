---
tags:
  - android
  - concepto
  - tema/recursos
aliases:
  - Recursos
  - strings.xml
  - colors.xml
  - themes.xml
  - estilos
---

# 28 — Recursos: strings, colors, dimens, arrays, estilos y temas

> PDFs: [Diseño por pesos](../03-pdfs/02-diseno-basado-en-pesos.md), [Ejercicio de diseño](../03-pdfs/03-ejercicio-diseno.md) · Proyecto de referencia: [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md)
> Drawables (`shape`, `selector`) → [15](15-drawables-formas-y-selectores.md) · Estructura de `res/` → [09](09-instalacion-estructura-proyecto.md)

## Concepto: recurso 📌 Importante

### Qué es
Todo lo que **no es código**: textos, colores, tamaños, imágenes, layouts… Vive en `res/`. Android le asigna un id en la clase **`R`** (`R.string.titulo1`, `R.color.rojo`…) y en XML se usa con **`@tipo/nombre`** (`@string/titulo1`).

### Para qué sirve
- **Cambiar en un sitio** y que se actualice en toda la app.
- **Traducir** (`values-en/strings.xml`), **modo oscuro** (`values-night/`) y **tamaños de pantalla** (`values-w600dp/`) sin tocar el código.

### Tipos que aparecen en los proyectos

| Archivo (`res/values/`) | Etiqueta | En XML | En Java | Ejemplo real |
|---|---|---|---|---|
| `strings.xml` | `<string>` | `@string/pie` | `getString(R.string.pie)` | `pie` = "www.almi.eus" (DisenyoPesos) |
| `colors.xml` | `<color>` | `@color/rojo` | `ContextCompat.getColor(this, R.color.rojo)` | `rojo #de070a` (DisenyoPesos) |
| `dimens.xml` | `<dimen>` | `@dimen/tamanoTitulo` | `getResources().getDimension(...)` | `tamanoTitulo 20sp` |
| arrays (`array.xml`, `frames.xml`, `recursos.xml`) | `<string-array>` | `@array/cursos` | `getResources().getStringArray(R.array.cursos)` · `obtainTypedArray(...)` para drawables | `cursos`, `paisajes`, `imagenes` |
| `themes.xml` | `<style>` | `style="@style/cursoTitulo"` · `android:theme=` | — | `cursoTitulo`, `Theme.AdapterDam2` |

> Nombre del archivo: da igual (`frames.xml`, `recursos.xml`…); lo que importa es la **etiqueta** de dentro. Solo tiene que estar en `res/values/`.

### Estilos: agrupar atributos que se repiten ✅ Reutilizable
```xml
<!-- res/values/themes.xml (o styles.xml) de DisenyoPesos -->
<style name="cursoTitulo">
    <item name="android:gravity">center</item>
    <item name="android:textStyle">bold</item>
    <item name="android:textColor">@color/white</item>
    <item name="android:textSize">@dimen/tamanoTitulo</item>
    <item name="android:padding">@dimen/espacioTitulo</item>
</style>
<!-- "cursoTitulo.subtitulo" HEREDA de cursoTitulo (por el punto) y solo cambia el tamaño -->
<style name="cursoTitulo.subtitulo">
    <item name="android:textSize">@dimen/tamanoSubtitulo</item>
</style>
```
```xml
<TextView … style="@style/cursoTitulo" android:text="@string/titulo1"/>
```
**Estilo con sombra** (lo pide el [Ejercicio de diseño](../03-pdfs/03-ejercicio-diseno.md)):
```xml
<style name="etiquetaBoton">
    <item name="android:textColor">@color/white</item>
    <item name="android:shadowColor">#80000000</item>   <!-- negro al 50 % -->
    <item name="android:shadowDx">2</item>
    <item name="android:shadowDy">2</item>
    <item name="android:shadowRadius">3</item>
</style>
```

### Estilo frente a tema
| | Estilo | Tema |
|---|---|---|
| Se aplica a | **Una vista** (`style=`) | **Toda la app o una Activity** (`android:theme=` en el manifest) |
| Ejemplo | `cursoTitulo` | `Theme.Material3.DayNight.NoActionBar` |
| Afecta a | Esa vista | Colores base, barras, transiciones (`windowEnterTransition` en AdapterDam2)… |

### Pantalla completa (lo piden los PDFs de diseño)
```xml
<style name="Theme.MiApp" parent="Theme.Material3.DayNight.NoActionBar">   <!-- sin barra de título -->
    <item name="android:windowFullscreen">true</item>                        <!-- sin barra de estado -->
</style>
```
> [!note] Con `EdgeToEdge.enable(this)` (todos los proyectos), la app ya dibuja detrás de la barra de estado. `windowFullscreen` la **oculta** del todo.

### `dp` frente a `sp`
- **`dp`** (*density-independent pixels*): para tamaños y márgenes. Miden lo mismo en todas las pantallas.
- **`sp`** (*scale-independent pixels*): **solo para textos**. Como `dp`, pero además respeta el tamaño de letra que el usuario elige en Ajustes.

### Errores comunes
| Error | Causa | Solución |
|---|---|---|
| `Resources$NotFoundException: String resource ID #0x50` | `setText(80)` con un `int`: Android lo toma como id de recurso | `setText(String.valueOf(80))` o `80 + ""` |
| Aviso amarillo *Hardcoded string* | Texto escrito en el XML | Alt+Intro → *Extract string resource* |
| `error: resource string/xxx not found` | El nombre no existe o tiene un error | Revisar `strings.xml` |
| Nombres de recurso con mayúsculas o espacios | `res/` solo admite `a-z`, `0-9` y `_` | `logo_almi.jpg`, no `Logo Almi.JPG` |
| El color no cambia en modo oscuro | Está en `colors.xml` sin versión `-night` | Crear `values-night/colors.xml` |

## Relacionado
- [15 — Drawables](15-drawables-formas-y-selectores.md) · [02 — Pesos](02-diseno-basado-en-pesos.md) · [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md)
