---
tags:
  - android
  - guia
---

# Glosario A–Z

Los 130 términos que aparecen en la documentación, explicados en una o dos frases. Cuando hay un documento que lo explica a fondo, se enlaza.

> Si un término no está aquí, búscalo en el [INICIO](../INICIO.md). Para Java básico (variable, clase, método…) empieza por [00 — Programación básica](../02-conceptos/00-programacion-basica.md).

## A

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **Activity** | Una pantalla de la app. Es una clase Java que hereda de `AppCompatActivity`. | [10](../02-conceptos/10-activity-en-profundidad.md) |
| **Activity Result API** | Forma actual de abrir otra pantalla o app y recibir su resultado (foto, texto dictado, permiso). Sustituye a `startActivityForResult`. | `registerForActivityResult(new TakePicture(), …)` en `CameraActivity` · [22](../02-conceptos/22-camara-y-almacenamiento.md) |
| **`ActivityResultLauncher`** | El "lanzador" que devuelve `registerForActivityResult`; se usa con `.launch(entrada)`. | `tomarFotoLauncher.launch(uri)` · [22](../02-conceptos/22-camara-y-almacenamiento.md) |
| **Adaptador (Adapter)** | Pieza que convierte una lista de datos en filas visibles dentro de un `ListView`, `GridView` o `Spinner`. | [07](../02-conceptos/07-adaptadores.md) |
| **`adb`** | *Android Debug Bridge*. Programa de línea de comandos para hablar con un móvil o emulador. | [09](../02-conceptos/09-instalacion-estructura-proyecto.md) |
| **AGP** | *Android Gradle Plugin*. El plugin que sabe compilar apps Android. Su versión debe ser compatible con tu Android Studio. | [Guía 01](01-ejecutar-la-app.md) |
| **AlertDialog** | Ventanita emergente estándar con título, mensaje y botones (sí/no). | [18](../02-conceptos/18-dialogos.md) |
| **AnimationDrawable** | Objeto que reproduce una animación frame a frame (`animation-list`); se arranca con `start()`. | [06](../02-conceptos/06-animaciones-frame-by-frame.md) |
| **Anotación** | Etiqueta que empieza por `@` (`@Entity`, `@Override`…) que da instrucciones al compilador o a una librería. |  |
| **APK** | El fichero instalable de una app Android (`.apk`). |  |
| **`AppExecutors`** | Clase de los proyectos con tres `Executor` (disco, hilo principal y red) para no bloquear la pantalla con Room. | `getDiskIO().execute(…)` · [20](../02-conceptos/20-executors-y-livedata.md) |
| **`applicationId`** | Identificador único de la app en el móvil y en Google Play. Se define en `app/build.gradle.kts`. | `com.example.adapterdam2` · [25](../02-conceptos/25-gradle-y-librerias.md) |
| **AsyncTask** | Clase antigua (obsoleta) para hacer trabajo en segundo plano. | [05](../02-conceptos/05-asynctask-e-hilos.md) |
| **AVD** | *Android Virtual Device*: un móvil virtual (emulador). |  |

## B

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **BottomNavigationView** | Barra de navegación inferior con iconos (3–5 secciones). | Plantilla [aaaa](../04-proyectos-profesor/aaaa.md) |
| **Builder (patrón)** | Objeto auxiliar al que encadenas configuraciones y al final construye el resultado. Ej.: `AlertDialog.Builder`. | [18](../02-conceptos/18-dialogos.md) |
| **Bundle** | "Caja" de datos con etiqueta (clave→valor) para pasar información entre pantallas. | [01](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) |

## C

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **Callback** | Código que le das a alguien para que lo ejecute cuando pase algo (un clic, un resultado, una lectura de sensor). | La lambda de `registerForActivityResult` |
| **Casting** | Decir a Java "trata este objeto como de otro tipo": `(String) objeto`. | [00](../02-conceptos/00-programacion-basica.md) |
| **Clase** | El "plano" de un tipo de objeto: qué datos tiene y qué sabe hacer. | [00](../02-conceptos/00-programacion-basica.md) |
| **Clase anónima** | Clase sin nombre que se define y se usa en el mismo sitio, típica de los *listeners*. | [04](../02-conceptos/04-toast-personalizado.md) |
| **Compilar** | Traducir tu código a algo que el móvil pueda ejecutar. Si hay errores de sintaxis, falla. |  |
| **`compileSdk` / `minSdk` / `targetSdk`** | Versión con la que se compila / mínima donde se instala / para la que está pensada la app. | 37 / 24 / 37 en todos los proyectos · [25](../02-conceptos/25-gradle-y-librerias.md) |
| **ConstraintLayout** | Layout donde cada vista se ata a otras mediante "restricciones". | [03](../02-conceptos/03-constraintlayout.md) |
| **Constructor** | Método especial que se ejecuta al crear un objeto con `new`. | [00](../02-conceptos/00-programacion-basica.md) |
| **ContentResolver** | Objeto para consultar y modificar datos de otros "proveedores" (como la galería) con `insert`, `query`, `update` y `delete`. | `getContentResolver().insert(MediaStore…)` · [22](../02-conceptos/22-camara-y-almacenamiento.md) |
| **ContentValues** | "Fila" clave → valor que se pasa a `insert`/`update` de un `ContentResolver`. | `valores.put(DISPLAY_NAME, nombre)` |
| **Context** | Objeto que representa "el entorno de la app" (la pantalla actual o la app entera). Muchos métodos lo piden. | [10](../02-conceptos/10-activity-en-profundidad.md) |
| **CoordinatorLayout** | Layout de Material que coordina animaciones entre hijos (p. ej. el Snackbar empuja al FAB). | `app_bar_main.xml` de [aaaa](../04-proyectos-profesor/aaaa.md) |
| **CRUD** | *Create, Read, Update, Delete*: crear, leer, actualizar y borrar datos. Es lo que hace `RegisterActivity`. |  |
| **Cursor** | Resultado de una consulta: se recorre fila a fila con `moveToNext()`. | `cargarImagenes()` en `CameraActivity` |

## D

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **DAO** | *Data Access Object*: interfaz con las operaciones sobre una tabla. | [17](../02-conceptos/17-room-base-de-datos.md) |
| **DialogFragment** | Fragmento que se muestra como ventana emergente con diseño propio. | [18](../02-conceptos/18-dialogos.md) |
| **DiffUtil** | Utilidad que compara la lista vieja con la nueva para repintar solo lo que cambió en un `RecyclerView`. | `TransformAdapter` de [aaaa](../04-proyectos-profesor/aaaa.md) |
| **`dismiss()`** | Cierra un diálogo. | [18](../02-conceptos/18-dialogos.md) |
| **`dp` / `sp`** | Unidades de tamaño independientes de la pantalla. `dp` para distancias, `sp` para texto. |  |
| **DrawerLayout** | Contenedor con menú lateral deslizable ("hamburguesa"). | [aaaa](../04-proyectos-profesor/aaaa.md) (tablet) |

## E

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **EdgeToEdge** | Modo en que la app se dibuja bajo las barras del sistema (estado y navegación). | [01](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) |
| **Emulador** | Móvil virtual que se ejecuta en tu PC. | [Guía 01](01-ejecutar-la-app.md) |
| **Entidad (`@Entity`)** | Clase Java que Room convierte en una tabla. | [17](../02-conceptos/17-room-base-de-datos.md) |
| **Estilo / Tema** | Estilo: atributos agrupados para **una vista**. Tema: estilo aplicado a **toda la app o Activity**. | `cursoTitulo` / `Theme.Material3…` · [28](../02-conceptos/28-recursos-values.md) |
| **Executor** | Objeto al que le das una tarea y decide en qué hilo ejecutarla. | [20](../02-conceptos/20-executors-y-livedata.md) |
| **`exported`** | Atributo del manifest: si otras apps pueden abrir esa Activity. `true` en la de arranque y `false` en el resto. | [24](../02-conceptos/24-manifest-y-permisos.md) |
| **`extends`** | Palabra clave de herencia: "esta clase parte de esa otra". | [00](../02-conceptos/00-programacion-basica.md) |

## F

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **FAB (FloatingActionButton)** | Botón redondo flotante para la acción principal de la pantalla. | [aaaa](../04-proyectos-profesor/aaaa.md) |
| **FileProvider** | "Portero" que convierte una ruta privada en una `Uri content://` segura para compartirla con otra app (la cámara). | `<provider>` + `file_paths.xml` · [22](../02-conceptos/22-camara-y-almacenamiento.md) |
| **`final`** | Variable que no puede reasignarse. Obligatoria (o "efectivamente final") si la usa una clase anónima. | [04](../02-conceptos/04-toast-personalizado.md) |
| **`findViewById`** | Busca en el layout el componente con un `id` concreto. | [01](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) |
| **Fragment** | Trozo reutilizable de pantalla con su propio layout y ciclo de vida. | [16](../02-conceptos/16-fragmentos.md) |
| **FragmentContainerView** | Hueco en un layout donde se colocan fragmentos. | [EjemploFragmentos2](../04-proyectos-profesor/EjemploFragmentos2.md) |
| **FragmentManager** | Gestor de fragmentos de una Activity: `beginTransaction().add/replace(...).commit()`. | `getSupportFragmentManager()` · [16](../02-conceptos/16-fragmentos.md) |

## G

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **`getSystemService`** | Pide al sistema un servicio (sensores, teclado, notificaciones…). | `getSystemService(SENSOR_SERVICE)` |
| **Getter / Setter** | Métodos `getX()` / `setX()` para leer / cambiar un atributo `private`. | [00](../02-conceptos/00-programacion-basica.md) |
| **Giroscopio** | Sensor que mide la **velocidad** de giro sobre X, Y y Z (rad/s). | `SensoresActivity` · [21](../02-conceptos/21-sensores.md) |
| **Glide** | Librería para cargar imágenes en un `ImageView` de forma eficiente: `Glide.with(contexto).load(...).into(imagen)`. | [07](../02-conceptos/07-adaptadores.md) |
| **Gradle** | Sistema que compila el proyecto y descarga las librerías. | [09](../02-conceptos/09-instalacion-estructura-proyecto.md) |
| **GridView** | Rejilla de elementos (como `ListView` pero con columnas). | [07](../02-conceptos/07-adaptadores.md) |

## H

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **Handler** | Objeto que permite dejar una tarea en la cola de un hilo (por ejemplo, "en 2 segundos haz esto"). | [12](../02-conceptos/12-hilos-en-profundidad.md) |
| **Herencia** | Que una clase reutilice todo lo de otra (`extends`). | [00](../02-conceptos/00-programacion-basica.md) |
| **Hilo (thread)** | Línea de ejecución. La app tiene un **hilo principal** (interfaz) y puede crear **hilos secundarios**. | [12](../02-conceptos/12-hilos-en-profundidad.md) |

## I

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **`id` (`android:id`)** | Nombre único de un componente del layout (`@+id/btnAceptar`), para encontrarlo desde Java. |  |
| **Inflar (`inflate`)** | Convertir un fichero XML de layout en objetos `View` reales en memoria. | [13](../02-conceptos/13-view-viewgroup.md) |
| **Intent** | Objeto que representa una intención, normalmente "abre esta pantalla". | [11](../02-conceptos/11-intent-en-profundidad.md) |
| **`intent-filter`** | Declara en el manifest qué intents puede atender una Activity (`MAIN` + `LAUNCHER` = pantalla de arranque). | [24](../02-conceptos/24-manifest-y-permisos.md) |
| **Interfaz** | "Contrato" que lista métodos que una clase promete implementar (`implements`). | [00](../02-conceptos/00-programacion-basica.md) |
| **`IS_PENDING`** | Marca del `MediaStore` (Android 10+) que oculta un archivo mientras se escribe. | `guardarEnGaleria()` |

## J

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **Java** | Lenguaje de programación de todos los proyectos del curso. | [00](../02-conceptos/00-programacion-basica.md) |

## K

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **Kotlin** | El otro lenguaje oficial de Android (el que recomienda Google). Los `.gradle.kts` están escritos en Kotlin. | [25](../02-conceptos/25-gradle-y-librerias.md) |

## L

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **Layout** | Fichero XML que describe cómo se ve una pantalla. `LinearLayout`, `ConstraintLayout`… [02](../02-conceptos/02-diseno-basado-en-pesos.md) | [03](../02-conceptos/03-constraintlayout.md) |
| **`layout_weight`** | Peso que reparte el espacio sobrante dentro de un `LinearLayout`. | [02](../02-conceptos/02-diseno-basado-en-pesos.md) |
| **ListAdapter** | Adaptador de `RecyclerView` que gestiona la lista y usa `DiffUtil`; se actualiza con `submitList`. | [aaaa](../04-proyectos-profesor/aaaa.md) |
| **Listener** | Código que se ejecuta cuando ocurre un evento (clic, cambio de texto…). | [19](../02-conceptos/19-textwatcher-y-eventos-de-lista.md) |
| **ListView** | Lista vertical de filas, alimentada por un adaptador. | [07](../02-conceptos/07-adaptadores.md) |
| **LiveData** | Contenedor de datos "observable" que avisa cuando cambian. | [20](../02-conceptos/20-executors-y-livedata.md) |
| **Logcat** | Panel de Android Studio con los mensajes y errores de la app. | [Guía 01](01-ejecutar-la-app.md) |
| **Looper** | El bucle del hilo principal que va procesando mensajes y tareas; `Handler(Looper.getMainLooper())` envía trabajo a ese hilo. | [12](../02-conceptos/12-hilos-en-profundidad.md) |

## M

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **Manifest (`AndroidManifest.xml`)** | "Índice" de la app: sus pantallas, permisos y cuál se abre primero. | [01](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) |
| **MaterialToolbar / Toolbar** | Barra superior colocada en el layout; se convierte en la barra de la app con `setSupportActionBar`. | [26](../02-conceptos/26-menus-y-toolbar.md) |
| **MediaStore** | Base de datos del sistema con las fotos, vídeos y audios compartidos (la galería). | [22](../02-conceptos/22-camara-y-almacenamiento.md) |
| **Menú de opciones** | Opciones de la Toolbar (iconos o ⋮), definidas en `res/menu` y gestionadas con `onCreateOptionsMenu` / `onOptionsItemSelected`. | Rojo/Verde en EjemploFragmentos2 · [26](../02-conceptos/26-menus-y-toolbar.md) |
| **Método** | Bloque de instrucciones con nombre que se puede llamar. | [00](../02-conceptos/00-programacion-basica.md) |
| **Modal** | Ventana que exige una respuesta antes de seguir (un diálogo). |  |

## N

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **Navigation Component** | Librería que navega entre fragmentos a partir de un grafo XML (`NavController`, `NavHostFragment`). | [27](../02-conceptos/27-navigation-viewbinding-viewmodel.md) |
| **`newInstance`** | Método estático "fábrica" para crear un fragmento con datos mediante `setArguments(bundle)`. | `FragmentoAbajo.newInstance(bundle)` · [16](../02-conceptos/16-fragmentos.md) |
| **`null`** | "Ninguna cosa": una variable que no apunta a ningún objeto. Usarla como si tuviera algo provoca `NullPointerException`. | [00](../02-conceptos/00-programacion-basica.md) |

## O

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **Objeto (instancia)** | Una "cosa real" creada a partir de una clase con `new`. | [00](../02-conceptos/00-programacion-basica.md) |
| **ORM** | *Object-Relational Mapping*: traducir automáticamente entre objetos Java y filas de tablas. Room es un ORM. | [17](../02-conceptos/17-room-base-de-datos.md) |
| **`@Override`** | Anotación que dice "este método ya existía en la clase padre y lo estoy reemplazando". | [00](../02-conceptos/00-programacion-basica.md) |

## P

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **Paquete (`package`)** | Carpeta que agrupa clases relacionadas. | [00](../02-conceptos/00-programacion-basica.md) |
| **Parámetro** | Dato de entrada de un método. |  |
| **Permiso** | Autorización para usar algo protegido. **Normal** (`INTERNET`: basta con declararlo) o **peligroso** (`CAMERA`: además hay que pedirlo en tiempo de ejecución). | [24](../02-conceptos/24-manifest-y-permisos.md) |
| **Picasso** | Librería parecida a Glide, típica para imágenes de internet (necesita el permiso `INTERNET`). | [07](../02-conceptos/07-adaptadores.md) |
| **Primary key (clave primaria)** | Columna que identifica de forma única cada fila (`id`). | [17](../02-conceptos/17-room-base-de-datos.md) |
| **ProgressBar** | Barra (o ruedita) de progreso; horizontal con `style="?android:attr/progressBarStyleHorizontal"`. | [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md) |

## Q

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **Query** | Consulta a la base de datos, escrita en SQL (`SELECT …`). |  |

## R

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **`R`** | Clase que Android genera sola con una constante por cada recurso (`R.id.x`, `R.layout.y`). | [09](../02-conceptos/09-instalacion-estructura-proyecto.md) |
| **RecognizerIntent** | Intent del sistema para el reconocimiento de voz (`ACTION_RECOGNIZE_SPEECH`). | `VozActivity` · [23](../02-conceptos/23-voz-e-intents-implicitos.md) |
| **Recurso** | Cualquier fichero de `res/`: layouts, textos, colores, imágenes… |  |
| **RecyclerView** | Versión moderna y eficiente de `ListView`. | [14](../02-conceptos/14-recyclerview.md) |
| **Room** | Librería de Google para usar SQLite fácilmente con objetos Java. | [17](../02-conceptos/17-room-base-de-datos.md) |
| **Runnable** | Bloque de código (`run()`) que se puede entregar a un hilo para que lo ejecute. | [20](../02-conceptos/20-executors-y-livedata.md) |

## S

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **Scoped storage** | Regla de Android 10+: cada app solo accede libremente a su carpeta; lo compartido va por el `MediaStore`. | [22](../02-conceptos/22-camara-y-almacenamiento.md) |
| **SDK** | Conjunto de librerías y herramientas para programar Android. | [09](../02-conceptos/09-instalacion-estructura-proyecto.md) |
| **Selector (`<selector>`)** | Drawable que cambia según el estado de la vista (pulsado, activado…). | `boton_pulsado.xml` · [15](../02-conceptos/15-drawables-formas-y-selectores.md) |
| **Sensor / SensorManager** | Hardware que mide algo (proximidad, giro…) / servicio que da acceso a los sensores. | [21](../02-conceptos/21-sensores.md) |
| **SensorEventListener** | Interfaz con `onSensorChanged` (cada lectura) y `onAccuracyChanged`. | [21](../02-conceptos/21-sensores.md) |
| **Serializable** | Interfaz que permite convertir un objeto en bytes, para pasarlo en un `Bundle`/`Intent`. | [01](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) |
| **Shape (`<shape>`)** | Drawable XML con formas, colores, degradados, bordes y esquinas. | `fondo.xml`, `cabecera.xml` · [15](../02-conceptos/15-drawables-formas-y-selectores.md) |
| **Shared element** | Transición en la que una vista "viaja" de una pantalla a otra (la foto que crece). | Grid → Detalle de [AdapterDam2](../04-proyectos-profesor/AdapterDam2.md) |
| **SharedPreferences** | Almacén clave → valor para datos pequeños (ajustes, "recordarme"). | [Preferencias](../06-codigo-reutilizable/08-preferencias.md) |
| **Singleton** | Patrón para que exista **una sola instancia** de una clase en toda la app. | [17](../02-conceptos/17-room-base-de-datos.md) |
| **Snackbar** | Mensaje en la parte baja de la app, con un botón de acción opcional ("Deshacer"). | [Mensajes](../06-codigo-reutilizable/04-mensajes-toast-dialogos.md) |
| **Spinner** | Desplegable para elegir una opción. | [07](../02-conceptos/07-adaptadores.md) |
| **SQLite** | Base de datos ligera incluida en Android, guardada en un fichero. |  |
| **`static`** | Pertenece a la clase entera, no a un objeto concreto. | [00](../02-conceptos/00-programacion-basica.md) |
| **Sync (sincronizar)** | Que Gradle lea la configuración y descargue lo necesario. | [Guía 01](01-ejecutar-la-app.md) |

## T

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **TextWatcher** | "Vigilante" que avisa cuando cambia el texto de un `EditText`. | [19](../02-conceptos/19-textwatcher-y-eventos-de-lista.md) |
| **Toast** | Mensaje flotante que desaparece solo. **No olvides `.show()`.** | [04](../02-conceptos/04-toast-personalizado.md) |
| **Transición** | Animación al pasar de una pantalla a otra: de escena, `overridePendingTransition` o de elemento compartido. | [08](../02-conceptos/08-transiciones.md) |
| **`TypedArray`** | Lista de recursos (por ejemplo imágenes) declarada en `res/values`; se lee con `obtainTypedArray` y `getResourceId`. | [07](../02-conceptos/07-adaptadores.md) |

## U

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **UI thread (hilo principal)** | El hilo que dibuja la pantalla; es el único que puede tocar las vistas. | [12](../02-conceptos/12-hilos-en-profundidad.md) |
| **Uri** | Dirección de un recurso: `https://…`, `content://…` (proveedor), `tel:…`. | `FileProvider.getUriForFile(...)` · [23](../02-conceptos/23-voz-e-intents-implicitos.md) |
| **`uses-feature`** | Declara en el manifest hardware que la app usa; `required="false"` permite instalarla sin él. | Sensores · [24](../02-conceptos/24-manifest-y-permisos.md) |

## V

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **Version catalog (`libs.versions.toml`)** | Archivo con las versiones de las librerías en un solo sitio; se usan como `libs.nombre`. | [25](../02-conceptos/25-gradle-y-librerias.md) |
| **View / ViewGroup** | `View` es cualquier componente visual; `ViewGroup` es una `View` que contiene otras (los layouts). | [13](../02-conceptos/13-view-viewgroup.md) |
| **View Binding** | Clases generadas por layout con un campo por vista; sustituye a `findViewById`. | `ActivityMainBinding` en aaaa · [27](../02-conceptos/27-navigation-viewbinding-viewmodel.md) |
| **ViewHolder** | Objeto de un `RecyclerView` que guarda las vistas de una fila para reutilizarlas sin volver a buscarlas. | [07](../02-conceptos/07-adaptadores.md) |
| **ViewModel** | Clase que guarda los datos de una pantalla y sobrevive al giro del móvil. | `TransformViewModel` · [27](../02-conceptos/27-navigation-viewbinding-viewmodel.md) |

## W

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **`windowSoftInputMode`** | Qué hace la pantalla cuando sale el teclado (`adjustResize`: encogerse). | Todas las Activities de los proyectos |

## X

| Término | Explicación sencilla | Ejemplo / dónde verlo |
|---|---|---|
| **XML** | Formato de texto con etiquetas (`<Button …/>`) usado para layouts, recursos y el manifiesto. |  |
