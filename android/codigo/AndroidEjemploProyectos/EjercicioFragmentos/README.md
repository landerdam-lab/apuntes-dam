# EjercicioFragmentos

> 📘 **Documentación completa** (flujo, diagrama, clases comentadas, layouts, manifest, Gradle, bugs): [EjercicioFragmentos](../../../04-proyectos-profesor/EjercicioFragmentos.md) · [Mapa de proyectos](../../../04-proyectos-profesor/00-mapa-de-proyectos.md)

## Descripción
Formulario en tres fragmentos: nombre (arriba) → apellido y fecha (medio) → `GridView` que acumula las personas creadas (abajo).

## Qué aprende el alumno
- Encadenar 3 fragmentos
- `ArrayList` de objetos `Serializable` en un Bundle
- `BaseAdapter` dentro de un fragmento
- Por qué no se usa un constructor con datos en un Fragment

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
| `MainActivity.java` | Guarda `personas` y coordina |
| `interfaces/IControlFragmentos.java` | `pasarTexto`, `pasarTextos` |
| `fragmentos/` | Arriba, Medio, Abajo |
| `adaptadores/AdaptadorPersona.java` | Celdas |
| `modelo/Persona.java` | Modelo |

## Explicación de clases
| Clase | Resumen |
|---|---|
| `MainActivity` | Implementa la interfaz, acumula personas y sustituye fragmentos |
| `FragmentoMedio` | Recibe el nombre (⚠️ por constructor) |
| `FragmentoAbajo` | `newInstance` + GridView |

## Explicación de layouts
| Layout | Pantalla / contenido |
|---|---|
| `activity_main.xml` | 3 contenedores `LinearLayout` |
| `fragment_fragmento_medio.xml` | 3 filas + Enviar |
| `item_persona.xml` | 3 textos |

## Métodos importantes
| Método | Clase | Cuándo se ejecuta | Qué hace |
|---|---|---|---|
| `pasarTexto` | `MainActivity` | Crear | `replace` del medio |
| `pasarTextos` | `MainActivity` | Enviar | Añade a la lista y `replace` de abajo |

## Fragmentos reutilizables
- ✅ Fila etiqueta + campo → [código](../../../06-codigo-reutilizable/05-formularios.md)
- ✅ `newInstance` → [código](../../../06-codigo-reutilizable/03-pasar-datos.md)

## Posibles mejoras
- `FragmentoMedio.newInstance`
- Validar campos
- `hint` en vez de `text`
- `savedInstanceState == null`

## Ejercicios recomendados
1. Toast al tocar una persona
2. Limpiar el formulario tras enviar

Más ejercicios con solución en la [documentación completa](../../../04-proyectos-profesor/EjercicioFragmentos.md) y en el [plan de estudio](../../../01-guias/04-plan-de-estudio.md).
