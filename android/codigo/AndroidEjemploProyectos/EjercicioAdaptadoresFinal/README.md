# EjercicioAdaptadoresFinal

> 📘 **Documentación completa** (flujo, diagrama, clases comentadas, layouts, manifest, Gradle, bugs): [EjercicioAdaptadoresFinal](../../../04-proyectos-profesor/EjercicioAdaptadoresFinal.md) · [Mapa de proyectos](../../../04-proyectos-profesor/00-mapa-de-proyectos.md)

## Descripción
Tres niveles de navegación: `ListView` de equipos → `GridView` de jugadores → detalle del jugador con un popup. Pasa objetos `Serializable` entre pantallas y carga imágenes de internet con Picasso.

## Qué aprende el alumno
- Modelos anidados `Serializable`
- `putSerializable` / `getSerializableExtra`
- Picasso + permiso `INTERNET`
- `Dialog` reutilizando un layout

## Requisitos
- Android Studio reciente (el proyecto usa **AGP 9.3.x**, **Gradle 9.5.0** y `compileSdk 37`).
- JDK 11 o superior (lo trae Android Studio).
- Emulador o móvil con **Android 7.0 (API 24)** o superior.
- Conexión a internet en el emulador (las imágenes son URLs).

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
| `MainActivity.java` | Lista de equipos |
| `jugadores_Activity.java` | Rejilla de jugadores |
| `JugadorActivity.java` | Detalle + popup |
| `adaptadores/` | `EquiposAdapter`, `JugadoresAdapter` |
| `modelos/` | `Liga`, `Equipo`, `Jugador` |

## Explicación de clases
| Clase | Resumen |
|---|---|
| `MainActivity` | Crea la liga y abre el equipo elegido |
| `jugadores_Activity` | Recibe el `Equipo` y muestra sus jugadores |
| `JugadorActivity` | Recibe el `Jugador`; popup al tocar la foto |
| `EquiposAdapter` / `JugadoresAdapter` | BaseAdapter + Picasso |

## Explicación de layouts
| Layout | Pantalla / contenido |
|---|---|
| `activity_main.xml` / `item_equipo.xml` | Lista y fila de equipo |
| `activity_jugadores.xml` / `jugador_item_view.xml` | Rejilla y celda de jugador |
| `activity_jugador.xml` | Detalle (también usado en el popup) |

## Métodos importantes
| Método | Clase | Cuándo se ejecuta | Qué hace |
|---|---|---|---|
| `rellenarEquipos` | `MainActivity` | En `onCreate` | Datos de prueba |
| `onItemClick` | Main / jugadores | Al tocar | `putSerializable` + startActivity |
| `getView` | Adaptadores | Por fila | Picasso + textos |

## Fragmentos reutilizables
- ✅ Pasar objetos → [código](../../../06-codigo-reutilizable/03-pasar-datos.md)
- ✅ Picasso/Glide → [código](../../../06-codigo-reutilizable/09-utilidades.md)

## Posibles mejoras
- Datos distintos por equipo
- `placeholder`/`error` en Picasso
- Aplicar (o quitar) la transición `opacidad`
- Renombrar `jugadores_Activity`

## Ejercicios recomendados
1. 3 equipos reales
2. Mostrar el dorsal
3. Animar el popup

Más ejercicios con solución en la [documentación completa](../../../04-proyectos-profesor/EjercicioAdaptadoresFinal.md) y en el [plan de estudio](../../../01-guias/04-plan-de-estudio.md).
