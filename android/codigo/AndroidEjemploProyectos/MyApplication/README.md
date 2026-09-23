# MyApplication

> 📘 **Documentación completa** (flujo, diagrama, clases comentadas, layouts, manifest, Gradle, bugs): [MyApplication](../../../04-proyectos-profesor/MyApplication.md) · [Mapa de proyectos](../../../04-proyectos-profesor/00-mapa-de-proyectos.md)

## Descripción
Proyecto vacío creado con la plantilla *Empty Views Activity*: una pantalla con "Hello World!". Es la base de la que parten todos los demás proyectos.

## Qué aprende el alumno
- Estructura de un proyecto Android
- `onCreate` y `setContentView`
- Bloque EdgeToEdge + `WindowInsets`
- Manifest y Activity LAUNCHER
- Gradle y *version catalog*

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
| `MainActivity.java` | Única pantalla |
| `res/layout/activity_main.xml` | `ConstraintLayout` + `TextView` |
| `AndroidManifest.xml` | Declara `MainActivity` como LAUNCHER |
| `app/build.gradle.kts` | SDK 37/24, dependencias de la plantilla |

## Explicación de clases
| Clase | Resumen |
|---|---|
| `MainActivity` | Carga el layout y aplica los márgenes de las barras del sistema |

## Explicación de layouts
| Layout | Pantalla / contenido |
|---|---|
| `activity_main.xml` | Texto centrado con 4 restricciones |

## Métodos importantes
| Método | Clase | Cuándo se ejecuta | Qué hace |
|---|---|---|---|
| `onCreate(Bundle)` | `MainActivity` | Android, al crear la pantalla | EdgeToEdge + `setContentView` + insets |

## Fragmentos reutilizables
- ✅ Esqueleto de `onCreate` → [código](../../../06-codigo-reutilizable/02-cambiar-de-pantalla.md)

## Posibles mejoras
- Mover "Hello World!" a `strings.xml`

## Ejercicios recomendados
1. Añadir un botón con Toast
2. Crear una segunda Activity y abrirla
3. Pasar un texto a la segunda pantalla

Más ejercicios con solución en la [documentación completa](../../../04-proyectos-profesor/MyApplication.md) y en el [plan de estudio](../../../01-guias/04-plan-de-estudio.md).
