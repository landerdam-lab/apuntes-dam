# AdapterDam2

> 📘 **Documentación completa** (flujo, diagrama, clases comentadas, layouts, manifest, Gradle, bugs): [AdapterDam2](../../../04-proyectos-profesor/AdapterDam2.md) · [Mapa de proyectos](../../../04-proyectos-profesor/00-mapa-de-proyectos.md)

## Descripción
Menú con tres botones que abren un `ListView` de compañías telefónicas, un `Spinner` de cursos y un `GridView` de paisajes, cada uno con su adaptador. Incluye transiciones por tema, por código, con `res/anim` y de elemento compartido (la foto que "vuela" al detalle).

## Qué aprende el alumno
- `BaseAdapter` y `ArrayAdapter`
- `getView` y `getDropDownView`
- Modelo de datos y arrays de recursos
- Glide
- Transiciones y *shared element*

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
| `MainActivity.java` | Menú con 3 técnicas de transición |
| `ListDam2Activity.java` + `CompaniasAdapter` | Lista en cebra |
| `SpinnerDam2Activity.java` + `SpinnerDam2Adapter` | Spinner con fila de aviso |
| `GridViewDam2Activity.java` + `ImageAdapter` | Rejilla con Glide |
| `DetalleActivity.java` | Destino del *shared element* |
| `DetalleSpinnerDam2Activity.java` | Muestra el curso |
| `model/CompaniaTelefonica.java` | Modelo |
| `res/transition`, `res/anim`, `values/themes.xml` | Transiciones |

## Explicación de clases
| Clase | Resumen |
|---|---|
| `MainActivity` | Abre cada pantalla |
| `CompaniasAdapter` | Filas logo + nombre + precio en cebra |
| `SpinnerDam2Adapter` | Fila 0 de aviso; resto `datos[pos-1]` |
| `ImageAdapter` | Celdas con Glide desde un `TypedArray` |
| `DetalleActivity` | Recibe `idFoto`, pone el *transition name* y carga la foto |

## Explicación de layouts
| Layout | Pantalla / contenido |
|---|---|
| `activity_main.xml` | 3 botones de 1/3 de pantalla |
| `compania_telefonica_list.xml` | Fila imagen + 2 textos |
| `spinner_per.xml` | Fila de Spinner |
| `grid_item_view.xml` | Celda 100dp |
| `activity_detalle.xml` | Imagen 300dp + ScrollView |

## Métodos importantes
| Método | Clase | Cuándo se ejecuta | Qué hace |
|---|---|---|---|
| `getView` | Adaptadores | Por cada fila visible | Infla y rellena la fila |
| `getDropDownView` | `SpinnerDam2Adapter` | Al desplegar | Filas de la lista |
| `onItemSelected` | `SpinnerDam2Activity` | Al elegir | Navega (salvo fila 0 / 1.ª vez) |
| `onItemClick` | `GridViewDam2Activity` | Al tocar una foto | `Pair` + `makeSceneTransitionAnimation` |

## Fragmentos reutilizables
- ✅ BaseAdapter con ViewHolder → [código](../../../06-codigo-reutilizable/06-listas-y-adaptadores.md)
- ✅ Spinner limpio → [código](../../../06-codigo-reutilizable/06-listas-y-adaptadores.md)
- ✅ Shared element → [código](../../../06-codigo-reutilizable/02-cambiar-de-pantalla.md)

## Posibles mejoras
- `salida.xml` con `alpha` 1 → 0
- Reciclar `convertView`
- Quitar Picasso (no se usa)
- Textos a `strings.xml`

## Ejercicios recomendados
1. Añadir una compañía
2. Toast al tocar una compañía
3. Pasar el ListView a RecyclerView

Más ejercicios con solución en la [documentación completa](../../../04-proyectos-profesor/AdapterDam2.md) y en el [plan de estudio](../../../01-guias/04-plan-de-estudio.md).
