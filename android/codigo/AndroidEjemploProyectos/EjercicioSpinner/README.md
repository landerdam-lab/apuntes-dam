# EjercicioSpinner

> 📘 **Documentación completa** (flujo, diagrama, clases comentadas, layouts, manifest, Gradle, bugs): [EjercicioSpinner](../../../04-proyectos-profesor/EjercicioSpinner.md) · [Mapa de proyectos](../../../04-proyectos-profesor/00-mapa-de-proyectos.md)

## Descripción
Spinner con 6 jugadores de los Lakers ("posición - nombre"). Al elegir uno se abre una pantalla con su nombre y un botón Volver.

## Qué aprende el alumno
- Adaptador propio para Spinner
- Bandera para ignorar la primera selección
- `Bundle` y `getStringExtra`
- `finish()`

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
| `MainActivity.java` | Datos + Spinner |
| `EjercicioSpinerAdapter.java` | Adaptador |
| `DetalleEjercicioSpinnerActivity.java` | Detalle |
| `res/layout/item_spinner.xml` | Fila número + nombre |

## Explicación de clases
| Clase | Resumen |
|---|---|
| `MainActivity` | Rellena datos, conecta el adaptador y navega |
| `EjercicioSpinerAdapter` | Pinta cada fila (genérico mal declarado) |
| `DetalleEjercicioSpinnerActivity` | Muestra el jugador y vuelve |

## Explicación de layouts
| Layout | Pantalla / contenido |
|---|---|
| `activity_main.xml` | Título "Lakers" + Spinner |
| `item_spinner.xml` | 2 textos |
| `activity_detalle_ejercicio_spinner.xml` | Nombre 48sp + Volver |

## Métodos importantes
| Método | Clase | Cuándo se ejecuta | Qué hace |
|---|---|---|---|
| `onItemSelected` | `MainActivity` | Al elegir | Bundle + startActivity |
| `vistaPersonalizada` | Adaptador | getView/getDropDownView | Infla `item_spinner` |

## Fragmentos reutilizables
- ✅ Spinner con fila de aviso → [código](../../../06-codigo-reutilizable/06-listas-y-adaptadores.md)

## Posibles mejoras
- `ArrayAdapter<String>`
- Fila de aviso para poder elegir el primero
- `setSelection(0)` al volver

## Ejercicios recomendados
1. Modelo `Jugador` con dorsal
2. Pasar el dorsal al detalle

Más ejercicios con solución en la [documentación completa](../../../04-proyectos-profesor/EjercicioSpinner.md) y en el [plan de estudio](../../../01-guias/04-plan-de-estudio.md).
