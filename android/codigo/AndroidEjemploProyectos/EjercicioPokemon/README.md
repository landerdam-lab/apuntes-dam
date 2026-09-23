# EjercicioPokemon

> 📘 **Documentación completa** (flujo, diagrama, clases comentadas, layouts, manifest, Gradle, bugs): [EjercicioPokemon](../../../04-proyectos-profesor/EjercicioPokemon.md) · [Mapa de proyectos](../../../04-proyectos-profesor/00-mapa-de-proyectos.md)

## Descripción
Pokédex con Room: filtros por tipo (Agua, Planta, Fuego), lista con foto de internet, alta, edición con pulsación larga y borrado con doble toque.

## Qué aprende el alumno
- Entidad, DAO y Database de Room
- `AppExecutors` para el disco
- `LiveData` + `observe`
- Clic frente a pulsación larga
- Picasso

## Requisitos
- Android Studio reciente (el proyecto usa **AGP 9.3.x**, **Gradle 9.5.0** y `compileSdk 37`).
- JDK 11 o superior (lo trae Android Studio).
- Emulador o móvil con **Android 7.0 (API 24)** o superior.
- Conexión a internet (fotos por URL).

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
| `MainActivity.java` | Filtros y lista |
| `RegisterPokemon.java` / `UpdatePokemon.java` | Alta / edición |
| `bbdd/` | `AppDatabase`, `PokemonsDao`, `AppExecutors` |
| `model/Pokemon.java` | Entidad |
| `adapter/PokemonAdapter.java` | Filas |

## Explicación de clases
| Clase | Resumen |
|---|---|
| `MainActivity` | Observa por tipo; doble toque = borrar; pulsación larga = editar |
| `RegisterPokemon` | Inserta |
| `UpdatePokemon` | Actualiza |
| `AppDatabase` | Singleton |
| `PokemonsDao` | Consultas |

## Explicación de layouts
| Layout | Pantalla / contenido |
|---|---|
| `activity_main.xml` | 3 filtros + lista + Añadir |
| `item_pokemon.xml` | Foto + 3 textos |
| `activity_register/update_pokemon.xml` | Formularios de 4 campos |

## Métodos importantes
| Método | Clase | Cuándo se ejecuta | Qué hace |
|---|---|---|---|
| `cargaPokemonsTipo` | `MainActivity` | Botón de tipo | `observe` de la consulta |
| `onItemClick` | `MainActivity` | Toque | Doble toque → delete (⚠️ bug) |
| `getInstance` | `AppDatabase` | Primer uso | Crea la BD una vez |

## Fragmentos reutilizables
- ✅ Plantilla Room → [código](../../../06-codigo-reutilizable/07-base-de-datos-room.md)
- ✅ Doble toque corregido → [código](../../../06-codigo-reutilizable/09-utilidades.md)

## Posibles mejoras
- Arreglar el doble toque (`long`, `ahora - antes`)
- Quitar observers antiguos
- `AppExecutors` con el orden correcto
- Validar y `inputType=number`
- Spinner de tipos
- Mostrar todos al inicio

## Ejercicios recomendados
1. Confirmación antes de borrar
2. Botón Todos
3. Campo nivel + migración

Más ejercicios con solución en la [documentación completa](../../../04-proyectos-profesor/EjercicioPokemon.md) y en el [plan de estudio](../../../01-guias/04-plan-de-estudio.md).
