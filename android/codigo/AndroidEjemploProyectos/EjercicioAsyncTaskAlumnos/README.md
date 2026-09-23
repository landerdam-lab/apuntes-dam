# EjercicioAsyncTaskAlumnos

> 📘 **Documentación completa** (flujo, diagrama, clases comentadas, layouts, manifest, Gradle, bugs): [EjercicioAsyncTaskAlumnos](../../../04-proyectos-profesor/EjercicioAsyncTaskAlumnos.md) · [Mapa de proyectos](../../../04-proyectos-profesor/00-mapa-de-proyectos.md)

## Descripción
Solución del ejercicio de AsyncTask: un botón abre una pantalla con un caballo, una barra y un porcentaje. La barra avanza 100 pasos de 50 ms y al terminar se cierra con un Toast "Adios". La animación del caballo está sin terminar.

## Qué aprende el alumno
- Repetir el patrón Activity + AsyncTask con *getters*
- Toast al terminar una tarea
- Casteo + listener en una línea

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
| `MainActivity.java` | Botón que abre la animación |
| `AsyntaskDamActivity.java` | Pantalla del caballo |
| `AsyntaskDamCaballo.java` | `AsyncTask` |
| `res/drawable/caballo1..8.png` | Fotogramas |

## Explicación de clases
| Clase | Resumen |
|---|---|
| `MainActivity` | Abre `AsyntaskDamActivity` |
| `AsyntaskDamActivity` | Busca las vistas y lanza el hilo |
| `AsyntaskDamCaballo` | Avanza barra y %; al final `finish()` + Toast |

## Explicación de layouts
| Layout | Pantalla / contenido |
|---|---|
| `activity_main.xml` | Un botón centrado |
| `activity_asyntask_dam.xml` | Caballo + barra + % |

## Métodos importantes
| Método | Clase | Cuándo se ejecuta | Qué hace |
|---|---|---|---|
| `doInBackground` | `AsyntaskDamCaballo` | Segundo plano | 100 × (sleep 50 ms + publishProgress) |
| `onProgressUpdate` | `AsyntaskDamCaballo` | Hilo principal | Barra y texto |
| `onPostExecute` | `AsyntaskDamCaballo` | Al terminar | `finish()` + Toast |

## Fragmentos reutilizables
- ✅ Tarea con progreso → [código](../../../06-codigo-reutilizable/09-utilidades.md)

## Posibles mejoras
- Animar el caballo con un array de drawables
- Recorrer las 8 imágenes (`% 8`)
- Cancelar al salir

## Ejercicios recomendados
1. Terminar la animación
2. Mover el caballo con `setTranslationX`
3. Botón Cancelar

Más ejercicios con solución en la [documentación completa](../../../04-proyectos-profesor/EjercicioAsyncTaskAlumnos.md) y en el [plan de estudio](../../../01-guias/04-plan-de-estudio.md).
