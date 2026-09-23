# EjemploFragmentos2

> 📘 **Documentación completa** (flujo, diagrama, clases comentadas, layouts, manifest, Gradle, bugs): [EjemploFragmentos2](../../../04-proyectos-profesor/EjemploFragmentos2.md) · [Mapa de proyectos](../../../04-proyectos-profesor/00-mapa-de-proyectos.md)

## Descripción
Pantalla con dos fragmentos: arriba un `EditText` + Enviar y abajo un texto grande. El texto enviado aparece abajo, y un menú en la Toolbar cambia su color a rojo o verde. Es el ejemplo guía del PDF de Fragmentos.

## Qué aprende el alumno
- Ciclo de vida del Fragment
- `FragmentContainerView` y transacciones
- Interfaz Fragment → Activity
- `newInstance(Bundle)`
- `MaterialToolbar` + menú de opciones

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
| `IControlFragmentos.java` | Interfaz |
| `MainActivity.java` | Aloja los fragmentos y el menú |
| `fragmentos/FragmentoArriba.java` | Formulario |
| `fragmentos/FragmentoAbajo.java` | Resultado |
| `res/menu/menu_main.xml` | Rojo / Verde |

## Explicación de clases
| Clase | Resumen |
|---|---|
| `MainActivity` | Implementa la interfaz; `replace` del fragmento de abajo |
| `FragmentoArriba` | Obtiene la Activity en `onAttach` y le avisa |
| `FragmentoAbajo` | Lee `getArguments()` |

## Explicación de layouts
| Layout | Pantalla / contenido |
|---|---|
| `activity_main.xml` | Toolbar + 2 contenedores al 50 % |
| `fragment_fragmento_arriba.xml` | EditText + botón |
| `fragment_fragmento_abajo.xml` | Texto 60sp |

## Métodos importantes
| Método | Clase | Cuándo se ejecuta | Qué hace |
|---|---|---|---|
| `onAttach` | `FragmentoArriba` | Al unirse a la Activity | Castea a la interfaz |
| `onCreateView` / `onViewCreated` | Fragmentos | Al crear la vista | Inflar / buscar vistas |
| `cambiarTexto` / `cambiarColor` | `MainActivity` | Desde el fragmento / menú | `replace` con Bundle |
| `onCreateOptionsMenu` / `onOptionsItemSelected` | `MainActivity` | Barra / selección | Menú |

## Fragmentos reutilizables
- ✅ `newInstance` e interfaz → [código](../../../06-codigo-reutilizable/03-pasar-datos.md)

## Posibles mejoras
- `if (savedInstanceState == null)` antes de `add`
- Conservar texto y color a la vez
- `activity = null` en `onDetach`

## Ejercicios recomendados
1. Opción Azul
2. Contador de mensajes en un tercer fragmento

Más ejercicios con solución en la [documentación completa](../../../04-proyectos-profesor/EjemploFragmentos2.md) y en el [plan de estudio](../../../01-guias/04-plan-de-estudio.md).
