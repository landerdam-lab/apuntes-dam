# DisenyoConstraint

> 📘 **Documentación completa** (flujo, diagrama, clases comentadas, layouts, manifest, Gradle, bugs): [DisenyoConstraint](../../../04-proyectos-profesor/DisenyoConstraint.md) · [Mapa de proyectos](../../../04-proyectos-profesor/00-mapa-de-proyectos.md)

## Descripción
Boceto de prácticas de `ConstraintLayout`: cuatro `TextView` colocados con distintas restricciones (uno sin ninguna, a propósito).

## Qué aprende el alumno
- Restricciones hacia `parent` y hacia otras vistas
- Centrar con Start/End
- `layout_constraintVertical_bias`
- Qué pasa con una vista sin restricciones

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
| `res/layout/activity_main.xml` | Todo el contenido del ejercicio |
| `MainActivity.java` | Plantilla |

## Explicación de clases
| Clase | Resumen |
|---|---|
| `MainActivity` | Igual que la plantilla |

## Explicación de layouts
| Layout | Pantalla / contenido |
|---|---|
| `activity_main.xml` | `textView` arriba centrado; `textView2` y `textView3` debajo; `textView4` sin restricciones |

## Métodos importantes
| Método | Clase | Cuándo se ejecuta | Qué hace |
|---|---|---|---|
| `onCreate(Bundle)` | `MainActivity` | Al abrir | Carga el layout |

## Fragmentos reutilizables
- ✅ Dos vistas en fila bajo un título (cadena) → ver la [documentación completa](../../../04-proyectos-profesor/DisenyoConstraint.md)

## Posibles mejoras
- Dar restricciones a `textView4`
- Sustituir tamaños fijos por `wrap_content`/`0dp`

## Ejercicios recomendados
1. Rehacer la cabecera de DisenyoPesos con constraint
2. Rehacer la rejilla 2×3 con cadenas

Más ejercicios con solución en la [documentación completa](../../../04-proyectos-profesor/DisenyoConstraint.md) y en el [plan de estudio](../../../01-guias/04-plan-de-estudio.md).
