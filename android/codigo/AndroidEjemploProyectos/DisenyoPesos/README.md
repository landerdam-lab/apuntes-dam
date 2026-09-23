# DisenyoPesos

> 📘 **Documentación completa** (flujo, diagrama, clases comentadas, layouts, manifest, Gradle, bugs): [DisenyoPesos](../../../04-proyectos-profesor/DisenyoPesos.md) · [Mapa de proyectos](../../../04-proyectos-profesor/00-mapa-de-proyectos.md)

## Descripción
Menú del Centro Almi hecho con `LinearLayout` y pesos (cabecera, rejilla de 6 botones y pie), con fondo degradado y botones con selector. El botón TOAST muestra un aviso propio con `Dialog`, y el botón ASYNCTASK abre una animación con barra de progreso movida por un `AsyncTask`.

## Qué aprende el alumno
- `layout_weight` + `0dp`
- Drawables `shape` y `selector`
- Estilos heredados y `dimens.xml`
- "Toast" personalizado con `Dialog` transparente
- `AsyncTask` (`doInBackground`, `publishProgress`, `onProgressUpdate`, `onPostExecute`)
- `TypedArray` y `ProgressBar`

## Requisitos
- Android Studio reciente (el proyecto usa **AGP 9.3.x**, **Gradle 9.5.0** y `compileSdk 37`).
- JDK 11 o superior (lo trae Android Studio).
- Emulador o móvil con **Android 7.0 (API 24)** o superior.


## Cómo abrirlo en Android Studio
1. **File → Open…** y selecciona **esta carpeta** (la que contiene `settings.gradle.kts`).
2. Espera a que termine **Gradle Sync** (barra inferior). La primera vez descarga dependencias.
3. Si aparece *"incompatible version of the Android Gradle plugin"*, actualiza Android Studio o baja `agp` en `gradle/libs.versions.toml`.

## Cómo ejecutarlo
1. Crea o elige un emulador en **Device Manager** (o conecta tu móvil con depuración USB).
2. Pulsa ▶ **Run 'app'** (Mayús+F10).
3. Si algo falla, mira **Logcat** (filtra por el nombre del paquete o por `AndroidRuntime`).

## Estructura de archivos
Rutas relativas a `app/src/main/java/…/` y `app/src/main/res/`.

| Archivo | Para qué sirve |
|---|---|
| `MainActivity.java` | Menú; botones TOAST y ASYNCTASK |
| `EjemploAsynctask.java` | Pantalla de la animación |
| `ProgressAndando.java` | `AsyncTask` que anima |
| `res/layout/activity_main.xml` | Menú por pesos 2/7/1 |
| `res/layout/toast_per.xml` | Diseño del aviso |
| `res/drawable/fondo.xml`, `cabecera.xml`, `boton_pulsado.xml` | Degradado, marco y selector |
| `res/values/frames.xml`, `dimens.xml`, `themes.xml` | Arrays, tamaños y estilos |

## Explicación de clases
| Clase | Resumen |
|---|---|
| `MainActivity` | Programa 2 de los 6 botones |
| `EjemploAsynctask` | Prepara vistas y `TypedArray`; lanza el hilo; expone *getters* |
| `ProgressAndando` | 100 pasos de 200 ms que cambian imagen, barra y % |

## Explicación de layouts
| Layout | Pantalla / contenido |
|---|---|
| `activity_main.xml` | Cabecera, cuerpo (3 filas × 2 celdas) y pie |
| `activity_ejemplo_asynctask.xml` | % + barra horizontal + muñeco |
| `toast_per.xml` | Imagen 200dp + texto 40sp |

## Métodos importantes
| Método | Clase | Cuándo se ejecuta | Qué hace |
|---|---|---|---|
| `onClick` (btnToast) | `MainActivity` | Al pulsar TOAST | Infla `toast_per` y lo muestra en un `Dialog` sin fondo |
| `doInBackground` | `ProgressAndando` | En segundo plano tras `execute()` | Bucle con `sleep` + `publishProgress` |
| `onProgressUpdate` | `ProgressAndando` | Hilo principal, tras cada `publishProgress` | Pinta imagen, barra y texto |
| `onPostExecute` | `ProgressAndando` | Al terminar | `finish()` |

## Fragmentos reutilizables
- ✅ Rejilla por pesos, selector, degradado → ver la [documentación completa](../../../04-proyectos-profesor/DisenyoPesos.md)
- ✅ Toast personalizado → [código](../../../06-codigo-reutilizable/04-mensajes-toast-dialogos.md)
- ✅ Tarea en segundo plano (versión moderna) → [código](../../../06-codigo-reutilizable/09-utilidades.md)

## Posibles mejoras
- Mostrar las 4 imágenes (`foto % 4`)
- Cerrar el aviso solo con `Handler.postDelayed`
- `recycle()` del `TypedArray`
- `contentDescription` en los `ImageButton`
- Cancelar el `AsyncTask` en `onDestroy`

## Ejercicios recomendados
1. Programar el botón FRAME BY FRAME con `AnimationDrawable`
2. Toast "Tarea finalizada" al acabar
3. Pasar un texto a `EjemploAsynctask` con `putExtra`

Más ejercicios con solución en la [documentación completa](../../../04-proyectos-profesor/DisenyoPesos.md) y en el [plan de estudio](../../../01-guias/04-plan-de-estudio.md).
