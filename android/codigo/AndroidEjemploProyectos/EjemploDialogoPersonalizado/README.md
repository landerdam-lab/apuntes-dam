# EjemploDialogoPersonalizado

> 📘 **Documentación completa** (flujo, diagrama, clases comentadas, layouts, manifest, Gradle, bugs): [EjemploDialogoPersonalizado](../../../04-proyectos-profesor/EjemploDialogoPersonalizado.md) · [Mapa de proyectos](../../../04-proyectos-profesor/00-mapa-de-proyectos.md)

## Descripción
Login con un `DialogFragment` propio y registro de usuarios con Room (alta, edición, borrado con confirmación y validación de contraseñas). Tras el login, un menú abre tres módulos: **Sensores** (proximidad y giroscopio), **Cámara** (fotos guardadas en la galería con `MediaStore`) y **Voz** (dictado y búsqueda en Google).

## Qué aprende el alumno
- `DialogFragment` y `AlertDialog`
- Room + `LiveData` + `AppExecutors`
- `TextWatcher`
- `SensorManager` y ciclo de vida
- Activity Result API
- `FileProvider` y `MediaStore`
- Permisos por versión
- `RecognizerIntent` e intents implícitos

## Requisitos
- Android Studio reciente (el proyecto usa **AGP 9.3.x**, **Gradle 9.5.0** y `compileSdk 37`).
- JDK 11 o superior (lo trae Android Studio).
- Emulador o móvil con **Android 7.0 (API 24)** o superior.
- Emulador **con Google Play** (voz) y cámara emulada.
- Para los sensores: *Extended Controls → Virtual sensors*.

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
| `MainActivity.java` | Acceder / Registrar |
| `fragmentos/LoginDialogFrag.java` | Login |
| `RegisterActivity.java` | CRUD de usuarios |
| `CentralActivity.java` | Menú |
| `SensoresActivity.java` | Sensores |
| `CameraActivity.java` | Cámara y galería |
| `VozActivity.java` | Voz |
| `bbdd/` | Room |
| `res/xml/file_paths.xml` | FileProvider |

## Explicación de clases
| Clase | Resumen |
|---|---|
| `LoginDialogFrag` | Consulta usuario+contraseña y abre `CentralActivity` |
| `RegisterActivity` | CRUD + TextWatcher + AlertDialog |
| `SensoresActivity` | Listeners registrados en `onResume`/`onPause` |
| `CameraActivity` | `TakePicture` + copia a `MediaStore` + galería |
| `VozActivity` | Dictado + `ACTION_VIEW` |

## Explicación de layouts
| Layout | Pantalla / contenido |
|---|---|
| `dialog_personalizado.xml` | Logo + usuario + contraseña + botones |
| `activity_register.xml` | Formulario + lista |
| `activity_central.xml` | 4 botones |
| `activity_camera.xml` | Botón + foto + grid |
| `activity_sensores.xml` / `activity_voz.xml` | Textos / botón + EditText + botón |

## Métodos importantes
| Método | Clase | Cuándo se ejecuta | Qué hace |
|---|---|---|---|
| `onViewCreated` | `LoginDialogFrag` | Al mostrar | Programa Aceptar/Cancelar |
| `consultarUsuarios` | `RegisterActivity` | `onCreate` | `observe` de `LiveData` |
| `onResume` / `onPause` | `SensoresActivity` | Visible / oculta | register / unregister |
| `lanzarCamara` / `guardarEnGaleria` / `cargarImagenes` | `CameraActivity` | Botón / callback | FileProvider / MediaStore insert / query |
| `realizarBusqueda` | `VozActivity` | Web Search | URL codificada + ACTION_VIEW |

## Fragmentos reutilizables
- ✅ Diálogos → [código](../../../06-codigo-reutilizable/04-mensajes-toast-dialogos.md)
- ✅ Room → [código](../../../06-codigo-reutilizable/07-base-de-datos-room.md)
- ✅ Multimedia → [código](../../../06-codigo-reutilizable/10-multimedia-sensores-camara-voz.md)

## Posibles mejoras
- Arreglar `AppExecutors`
- Cerrar el diálogo al entrar y avisar si falla
- Validar el registro
- Copiar fotos fuera del hilo principal
- `try/catch` en voz
- `RELATIVE_PATH` como en el PDF

## Ejercicios recomendados
1. Acelerómetro
2. Saludo con el nombre en `CentralActivity`
3. Botón Volley con Toast

Más ejercicios con solución en la [documentación completa](../../../04-proyectos-profesor/EjemploDialogoPersonalizado.md) y en el [plan de estudio](../../../01-guias/04-plan-de-estudio.md).
