# aaaa

> 📘 **Documentación completa** (flujo, diagrama, clases comentadas, layouts, manifest, Gradle, bugs): [aaaa](../../../04-proyectos-profesor/aaaa.md) · [Mapa de proyectos](../../../04-proyectos-profesor/00-mapa-de-proyectos.md)

## Descripción
Plantilla *Responsive Views Activity* de Android Studio sin modificar: 4 secciones con Navigation Component, barra inferior en móvil y menú lateral en tablet, `RecyclerView` con `ListAdapter`, `ViewModel` + `LiveData` y View Binding.

## Qué aprende el alumno
- View Binding
- Navigation Component
- ViewModel + LiveData
- RecyclerView + ListAdapter + DiffUtil
- Layouts por tamaño de pantalla (`layout-w600dp`)

## Requisitos
- Android Studio reciente (el proyecto usa **AGP 9.3.x**, **Gradle 9.5.0** y `compileSdk 37`).
- JDK 11 o superior (lo trae Android Studio).
- Emulador o móvil con **Android 7.0 (API 24)** o superior.
- Opcional: emulador de tablet para ver el menú lateral.

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
| `MainActivity.java` | Binding + navegación |
| `ui/transform/` | RecyclerView + ViewModel |
| `ui/reflow`, `ui/slideshow`, `ui/settings` | Fragmentos simples |
| `res/navigation/mobile_navigation.xml` | Grafo |
| `res/menu/` | Menús de navegación |
| `res/layout-w600dp/`, `layout-w1240dp/` | Variantes por ancho |

## Explicación de clases
| Clase | Resumen |
|---|---|
| `MainActivity` | Conecta Toolbar, drawer y barra inferior al `NavController` |
| `TransformFragment` | `RecyclerView` + `ListAdapter` |
| `*ViewModel` | Datos con `MutableLiveData` |

## Explicación de layouts
| Layout | Pantalla / contenido |
|---|---|
| `activity_main.xml` | Drawer (+ NavigationView en tablet) |
| `app_bar_main.xml` | Toolbar + contenido + FAB |
| `content_main.xml` | NavHostFragment + BottomNavigationView |
| `item_transform.xml` | Avatar + texto |

## Métodos importantes
| Método | Clase | Cuándo se ejecuta | Qué hace |
|---|---|---|---|
| `onCreate` | `MainActivity` | Al abrir | Binding + NavigationUI |
| `onSupportNavigateUp` | `MainActivity` | Flecha ← | `navigateUp` |
| `onCreateViewHolder` / `onBindViewHolder` | `TransformAdapter` | RecyclerView | Crear / rellenar filas |
| `onDestroyView` | Fragmentos | Al destruir la vista | `binding = null` |

## Fragmentos reutilizables
- ✅ RecyclerView → [código](../../../06-codigo-reutilizable/06-listas-y-adaptadores.md)

## Posibles mejoras
- Traducir los textos de la plantilla
- Quitar `assert`

## Ejercicios recomendados
1. Sección nueva "Acerca de"
2. View Binding en DisenyoPesos

Más ejercicios con solución en la [documentación completa](../../../04-proyectos-profesor/aaaa.md) y en el [plan de estudio](../../../01-guias/04-plan-de-estudio.md).
