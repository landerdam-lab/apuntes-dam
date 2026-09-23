---
tags:
  - android
  - indice
  - inventario
aliases:
  - Inventario de materiales
---

# Inventario completo de materiales

Todo lo que hay en el vault de Android, archivo por archivo. Las rutas de código son relativas a `android/codigo/AndroidEjemploProyectos/<Proyecto>/app/src/main/`.

> [!info] Totales
> **15 PDFs** · **12 proyectos del profesor** · **12 proyectos del alumno** (+ 1 copia con soluciones) · **71 archivos Java** (clases e interfaces, profesor) · **61 layouts XML** (profesor, contando variantes `w600dp`/`w1240dp`) · **12 manifest** · **36 archivos `.gradle.kts`** (3 por proyecto) + 12 `libs.versions.toml` · **8 librerías** externas o de AndroidX distintas de la plantilla.

## 1. PDFs del curso

Carpeta: `android/adjuntos/pdfs/`. Resumen de cada uno en [03-pdfs](03-pdfs/00-relacion-pdfs-proyectos.md).

| Tipo | Nombre | Pág. | Qué explica | Nota |
|---|---|---|---|---|
| PDF | `0-instalacionAndroid.pdf` | 1 | Instalar Android Studio, SDK Manager y crear un emulador (Pixel 8) | [→](03-pdfs/01-instalacion-android.md) |
| PDF | `1-DiseñoBasadoEnPesos.pdf` | 18 | `LinearLayout` + `layout_weight`, `dimens.xml`, estilos, degradado, bordes, selector, pantalla completa | [→](03-pdfs/02-diseno-basado-en-pesos.md) |
| PDF | `1-EjercicioDiseno.pdf` | 1 | Enunciado: menú "agenda" con sombras, selectores y pantalla completa | [→](03-pdfs/03-ejercicio-diseno.md) |
| PDF | `2-constraint.pdf` | 2 | Rehacer el menú con `ConstraintLayout` y el *Blueprint* | [→](03-pdfs/04-constraint.md) |
| PDF | `3-ToasPersonalizado.pdf` | 4 | `Toast` normal y "toast" propio con `Dialog` + `Handler` | [→](03-pdfs/05-toast-personalizado.md) |
| PDF | `4-asyncTask.pdf` | 7 | `AsyncTask`, `ProgressBar`, `TypedArray`, `Toast` final, pasar datos con `Bundle` | [→](03-pdfs/06-asynctask.md) |
| PDF | `5-frameByFrame.pdf` | 4 | `AnimationDrawable` (`animation-list`), botones Play/Stop/Back | [→](03-pdfs/07-frame-by-frame.md) |
| PDF | `EjercicioAnimaciones.pdf` | 2 | Enunciado: carga de 3 s y pantalla vacía | [→](03-pdfs/08-ejercicio-animaciones.md) |
| PDF | `6-Adaptadores.pdf` | 27 | `ArrayAdapter`, `BaseAdapter`, `ListView`, `Spinner`, `GridView`, Glide, `RecyclerView` | [→](03-pdfs/09-adaptadores.md) |
| PDF | `7-Transiciones.pdf` | 16 | Transiciones de entrada/salida (explode/slide/fade), por tema y por código; *shared element* | [→](03-pdfs/10-transiciones.md) |
| PDF | `8-Fragmentos.pdf` | 13 | Ciclo de vida del fragmento, `FragmentContainerView`, interfaz, `newInstance`, `MaterialToolbar` + menú | [→](03-pdfs/11-fragmentos.md) |
| PDF | `1-DialogSQLitePersonalizado.pdf` | 43 | `DialogFragment`, Room, `AppExecutors`, `LiveData`, `AlertDialog`, `TextWatcher`, CRUD | [→](03-pdfs/12-dialogo-sqlite-personalizado.md) |
| PDF | `2-Sensores.pdf` | 9 | `SensorManager`, proximidad, giroscopio, `uses-feature`, sensores virtuales del emulador | [→](03-pdfs/13-sensores.md) |
| PDF | `3-CamaraYAlmacenamiento.pdf` | 25 | Activity Result API, `TakePicture`, `FileProvider`, galería propia, `MediaStore`, permisos por versión | [→](03-pdfs/14-camara-y-almacenamiento.md) |
| PDF | `4-Voz.pdf` | 10 | `RecognizerIntent`, `StartActivityForResult`, búsqueda web con `ACTION_VIEW` | [→](03-pdfs/15-voz.md) |

## 2. Proyectos Android

| Tipo | Nombre | Ruta | Qué enseña |
|---|---|---|---|
| Proyecto | MyApplication | `codigo/AndroidEjemploProyectos/MyApplication` | Plantilla vacía ([nota](04-proyectos-profesor/MyApplication.md)) |
| Proyecto | DisenyoConstraint | `…/DisenyoConstraint` | `ConstraintLayout` ([nota](04-proyectos-profesor/DisenyoConstraint.md)) |
| Proyecto | DisenyoPesos | `…/DisenyoPesos` | Pesos, drawables, toast, AsyncTask ([nota](04-proyectos-profesor/DisenyoPesos.md)) |
| Proyecto | EjercicioAsyncTaskAlumnos | `…/EjercicioAsyncTaskAlumnos` | AsyncTask ([nota](04-proyectos-profesor/EjercicioAsyncTaskAlumnos.md)) |
| Proyecto | AdapterDam2 | `…/AdapterDam2` | Adaptadores y transiciones ([nota](04-proyectos-profesor/AdapterDam2.md)) |
| Proyecto | EjercicioSpinner | `…/EjercicioSpinner` | Spinner ([nota](04-proyectos-profesor/EjercicioSpinner.md)) |
| Proyecto | EjercicioAdaptadoresFinal | `…/EjercicioAdaptadoresFinal` | 3 niveles, Picasso ([nota](04-proyectos-profesor/EjercicioAdaptadoresFinal.md)) |
| Proyecto | EjemploFragmentos2 | `…/EjemploFragmentos2` | Fragmentos + menú ([nota](04-proyectos-profesor/EjemploFragmentos2.md)) |
| Proyecto | EjercicioFragmentos | `…/EjercicioFragmentos` | 3 fragmentos ([nota](04-proyectos-profesor/EjercicioFragmentos.md)) |
| Proyecto | EjercicioPokemon | `…/EjercicioPokemon` | Room CRUD ([nota](04-proyectos-profesor/EjercicioPokemon.md)) |
| Proyecto | EjemploDialogoPersonalizado | `…/EjemploDialogoPersonalizado` | Diálogo, Room, sensores, cámara, voz ([nota](04-proyectos-profesor/EjemploDialogoPersonalizado.md)) |
| Proyecto | aaaa | `…/aaaa` | Plantilla Navigation/ViewBinding ([nota](04-proyectos-profesor/aaaa.md)) |
| Proyectos del alumno | 12 proyectos + `EjemploDialogoPersonalizado-con-soluciones` | `codigo/proyectos-alumno/` | Ver [05-proyectos-alumno](INICIO.md) |
| Zips | 13 zips | `codigo/proyectos-zip/` | Export de los proyectos del alumno + `…-con-soluciones.zip` |
| Material suelto | 6 archivos | `codigo/recursos/` | Exports antiguos, plantillas `.rar` de ejercicio, imágenes |

## 3. Clases Java (proyectos del profesor)

| Tipo | Clase | Proyecto · ruta (`java/com/example/<paquete>/`) | Para qué sirve |
|---|---|---|---|
| Clase | `MainActivity` | MyApplication | Plantilla: carga `activity_main` |
| Clase | `MainActivity` | DisenyoConstraint | Plantilla |
| Clase | `MainActivity` | DisenyoPesos | Menú por pesos; botón TOAST (`Dialog`) y ASYNCTASK (`Intent`) |
| Clase | `EjemploAsynctask` | DisenyoPesos | Pantalla de la animación; expone vistas por *getters* |
| Clase | `ProgressAndando` | DisenyoPesos | `AsyncTask<Void,Integer,Void>`: 100 pasos de 200 ms |
| Clase | `MainActivity` | EjercicioAsyncTaskAlumnos | Botón que abre la animación |
| Clase | `AsyntaskDamActivity` | EjercicioAsyncTaskAlumnos | Pantalla del caballo |
| Clase | `AsyntaskDamCaballo` | EjercicioAsyncTaskAlumnos | `AsyncTask`: 100 pasos de 50 ms, Toast "Adios" |
| Clase | `MainActivity` | AdapterDam2 | Menú de 3 botones con 3 técnicas de transición |
| Clase | `ListDam2Activity` | AdapterDam2 | `ListView` de compañías + transición `slide` |
| Clase | `SpinnerDam2Activity` | AdapterDam2 | `Spinner` de cursos → detalle |
| Clase | `DetalleSpinnerDam2Activity` | AdapterDam2 | Muestra el curso |
| Clase | `GridViewDam2Activity` | AdapterDam2 | `GridView` de paisajes → detalle con *shared element* |
| Clase | `DetalleActivity` | AdapterDam2 | Foto grande (destino de la transición) |
| Clase | `adaptadores/CompaniasAdapter` | AdapterDam2 | `BaseAdapter` con filas en cebra |
| Clase | `adaptadores/SpinnerDam2Adapter` | AdapterDam2 | `ArrayAdapter<String>` con fila de aviso |
| Clase | `adaptadores/ImageAdapter` | AdapterDam2 | `BaseAdapter` de `TypedArray` + Glide |
| Clase | `model/CompaniaTelefonica` | AdapterDam2 | Modelo: nombre, logo, precio |
| Clase | `MainActivity` | EjercicioSpinner | Spinner de jugadores |
| Clase | `EjercicioSpinerAdapter` | EjercicioSpinner | `ArrayAdapter` (genérico mal puesto) |
| Clase | `DetalleEjercicioSpinnerActivity` | EjercicioSpinner | Nombre + botón Volver |
| Clase | `MainActivity` | EjercicioAdaptadoresFinal | `ListView` de equipos |
| Clase | `jugadores_Activity` | EjercicioAdaptadoresFinal | `GridView` de jugadores |
| Clase | `JugadorActivity` | EjercicioAdaptadoresFinal | Detalle + popup `Dialog` |
| Clase | `adaptadores/EquiposAdapter` | EjercicioAdaptadoresFinal | `BaseAdapter` + Picasso |
| Clase | `adaptadores/JugadoresAdapter` | EjercicioAdaptadoresFinal | `BaseAdapter` + Picasso |
| Clase | `modelos/Liga`, `Equipo`, `Jugador` | EjercicioAdaptadoresFinal | Modelos `Serializable` anidados |
| Interfaz | `IControlFragmentos` | EjemploFragmentos2 | `cambiarColor`, `cambiarTexto` |
| Clase | `MainActivity` | EjemploFragmentos2 | Aloja 2 fragmentos, Toolbar y menú |
| Clase | `fragmentos/FragmentoArriba` | EjemploFragmentos2 | Formulario que avisa a la Activity |
| Clase | `fragmentos/FragmentoAbajo` | EjemploFragmentos2 | Muestra texto/color (`newInstance`) |
| Interfaz | `interfaces/IControlFragmentos` | EjercicioFragmentos | `pasarTexto`, `pasarTextos` |
| Clase | `MainActivity` | EjercicioFragmentos | Aloja 3 fragmentos y la lista de personas |
| Clase | `fragmentos/FragmentoArriba` | EjercicioFragmentos | Nombre + Crear |
| Clase | `fragmentos/FragmentoMedio` | EjercicioFragmentos | Apellido + fecha (⚠️ constructor con `Bundle`) |
| Clase | `fragmentos/FragmentoAbajo` | EjercicioFragmentos | `GridView` de personas |
| Clase | `adaptadores/AdaptadorPersona` | EjercicioFragmentos | `BaseAdapter` de `item_persona` |
| Clase | `modelo/Persona` | EjercicioFragmentos | Modelo `Serializable` |
| Clase | `MainActivity` | EjercicioPokemon | Filtros, lista, doble toque, pulsación larga |
| Clase | `RegisterPokemon` | EjercicioPokemon | Alta |
| Clase | `UpdatePokemon` | EjercicioPokemon | Edición |
| Clase | `adapter/PokemonAdapter` | EjercicioPokemon | `BaseAdapter` + Picasso |
| Clase | `bbdd/AppDatabase` | EjercicioPokemon | `@Database` Singleton |
| Interfaz | `bbdd/PokemonsDao` | EjercicioPokemon | `@Dao` |
| Clase | `bbdd/AppExecutors` | EjercicioPokemon | Hilos (⚠️ parámetros cruzados) |
| Clase | `model/Pokemon` | EjercicioPokemon | `@Entity` |
| Clase | `model/Tipo` | EjercicioPokemon | Modelo sin usar |
| Clase | `MainActivity` | EjemploDialogoPersonalizado | Acceder / Registrar |
| Clase | `RegisterActivity` | EjemploDialogoPersonalizado | CRUD de usuarios |
| Clase | `CentralActivity` | EjemploDialogoPersonalizado | Menú Sensores/Voz/Cámara |
| Clase | `SensoresActivity` | EjemploDialogoPersonalizado | Proximidad + giroscopio |
| Clase | `CameraActivity` | EjemploDialogoPersonalizado | Foto + galería `MediaStore` |
| Clase | `VozActivity` | EjemploDialogoPersonalizado | Dictado + búsqueda web |
| Clase | `fragmentos/LoginDialogFrag` | EjemploDialogoPersonalizado | `DialogFragment` de login |
| Clase | `adaptadores/UsuariosAdapter` | EjemploDialogoPersonalizado | `ArrayAdapter<Usuario>` |
| Clase | `adaptadores/FotosGridViewAdapter` | EjemploDialogoPersonalizado | `BaseAdapter<Uri>` + Glide |
| Clase | `bbdd/AppDatabase`, `AppExecutors` | EjemploDialogoPersonalizado | Igual que en Pokemon |
| Interfaz | `bbdd/UsuariosDao` | EjemploDialogoPersonalizado | Incluye la consulta de login |
| Clase | `model/Usuario` | EjemploDialogoPersonalizado | `@Entity` con `@Ignore` |
| Clase | `MainActivity` | aaaa | View Binding + Navigation |
| Clase | `ui/transform/TransformFragment`, `TransformViewModel` | aaaa | `RecyclerView` + `ListAdapter` + `ViewModel` |
| Clase | `ui/reflow`, `ui/slideshow`, `ui/settings` (Fragment + ViewModel) | aaaa | Un texto con `LiveData` |

## 4. Layouts XML (proyectos del profesor)

| Tipo | Layout | Proyecto | Qué pantalla representa |
|---|---|---|---|
| Layout | `activity_main.xml` | MyApplication | "Hello World!" |
| Layout | `activity_main.xml` | DisenyoConstraint | 4 TextView con restricciones |
| Layout | `activity_main.xml` | DisenyoPesos | Menú por pesos 2/7/1 con rejilla 2×3 |
| Layout | `activity_ejemplo_asynctask.xml` | DisenyoPesos | % + barra + muñeco |
| Layout | `toast_per.xml` | DisenyoPesos | Contenido del "toast" |
| Layout | `activity_main.xml` | EjercicioAsyncTaskAlumnos | Un botón |
| Layout | `activity_asyntask_dam.xml` | EjercicioAsyncTaskAlumnos | Caballo + barra + % |
| Layout | `activity_main.xml` | AdapterDam2 | 3 botones grandes |
| Layout | `activity_list_dam2.xml` · `compania_telefonica_list.xml` | AdapterDam2 | Lista y su fila |
| Layout | `activity_spinner_dam2.xml` · `spinner_per.xml` · `activity_detalle_spinner_dam2.xml` | AdapterDam2 | Spinner, su fila y el detalle |
| Layout | `activity_grid_view_dam2.xml` · `grid_item_view.xml` · `activity_detalle.xml` | AdapterDam2 | Rejilla, su celda y el detalle |
| Layout | `activity_main.xml` · `item_spinner.xml` · `activity_detalle_ejercicio_spinner.xml` | EjercicioSpinner | Spinner, fila y detalle |
| Layout | `activity_main.xml` · `item_equipo.xml` | EjercicioAdaptadoresFinal | Lista de equipos |
| Layout | `activity_jugadores.xml` · `jugador_item_view.xml` | EjercicioAdaptadoresFinal | Rejilla de jugadores |
| Layout | `activity_jugador.xml` | EjercicioAdaptadoresFinal | Detalle (y popup) |
| Layout | `activity_main.xml` · `fragment_fragmento_arriba.xml` · `fragment_fragmento_abajo.xml` | EjemploFragmentos2 | Toolbar + 2 fragmentos |
| Menú | `menu/menu_main.xml` | EjemploFragmentos2 | Rojo / Verde |
| Layout | `activity_main.xml` · `fragment_fragmento_arriba/medio/abajo.xml` · `item_persona.xml` | EjercicioFragmentos | 3 fragmentos + celda |
| Layout | `activity_main.xml` · `item_pokemon.xml` · `activity_register_pokemon.xml` · `activity_update_pokemon.xml` | EjercicioPokemon | Lista, fila, alta y edición |
| Layout | `activity_main.xml` · `dialog_personalizado.xml` · `activity_register.xml` · `activity_central.xml` | EjemploDialogoPersonalizado | Inicio, login, registro, menú |
| Layout | `activity_sensores.xml` · `activity_camera.xml` · `item_foto.xml` · `activity_voz.xml` | EjemploDialogoPersonalizado | Sensores, cámara, miniatura, voz |
| Layout | `activity_main` · `app_bar_main` · `content_main` · `nav_header_main` · `fragment_transform` · `item_transform` · `fragment_reflow/slideshow/settings` (+ variantes `w600dp` y `w1240dp`) | aaaa | Navegación adaptable |
| Menú | `bottom_navigation.xml` · `navigation_drawer.xml` · `overflow.xml` | aaaa | Menús de navegación |
| Navegación | `navigation/mobile_navigation.xml` | aaaa | Grafo de 4 destinos |

## 5. AndroidManifest.xml

| Proyecto | Activities | Permisos / features / providers |
|---|---|---|
| MyApplication, DisenyoConstraint, EjemploFragmentos2, EjercicioFragmentos | 1 (`MainActivity`) | — |
| DisenyoPesos | 2 | — |
| EjercicioAsyncTaskAlumnos | 2 | — |
| EjercicioSpinner | 2 | — |
| EjercicioAdaptadoresFinal | 3 | `INTERNET` |
| EjercicioPokemon | 3 | `INTERNET` |
| AdapterDam2 | 6 | — |
| EjemploDialogoPersonalizado | 7 | `uses-feature` proximidad y giroscopio (`required=false`) · `WRITE_EXTERNAL_STORAGE` (`maxSdkVersion=28`) · `FileProvider` |
| aaaa | 1 (tema `NoActionBar`, `resizeableActivity`) | — |

Explicación: [24 — Manifest y permisos](02-conceptos/24-manifest-y-permisos.md).

## 6. Gradle, dependencias y librerías

Cada proyecto tiene `settings.gradle.kts`, `build.gradle.kts` (raíz), `app/build.gradle.kts`, `gradle/libs.versions.toml` y `gradle/wrapper/gradle-wrapper.properties`.

| Proyecto | AGP | Gradle | compile/target/min SDK | Dependencias extra |
|---|---|---|---|---|
| Todos | 9.3.2 o 9.3.3 | 9.5.0 | 37 / 37 / 24 | — |
| AdapterDam2 | 9.3.2 | 9.5.0 | 37/37/24 | Glide 4.16.0 · Picasso 2.8 (sin usar) |
| EjercicioAdaptadoresFinal | 9.3.2 | 9.5.0 | 37/37/24 | Picasso 2.8 |
| EjercicioPokemon | 9.3.3 | 9.5.0 | 37/37/24 | Room 2.8.5 (runtime + compiler) · Picasso 2.8 |
| EjemploDialogoPersonalizado | 9.3.3 | 9.5.0 | 37/37/24 | Room 2.8.5 · Glide 4.16.0 |
| aaaa | 9.3.3 | 9.5.0 | 37/37/24 | `viewBinding=true` · lifecycle 2.11.0 · navigation 2.10.1 · recyclerview 1.3.0 |

| Librería | Para qué sirve | Dónde se usa |
|---|---|---|
| `androidx.appcompat` | `AppCompatActivity`, `AlertDialog`, `DialogFragment`, fragmentos | Todos |
| `com.google.android.material` | Temas Material, `MaterialToolbar`, `Snackbar`, FAB, `BottomNavigationView` | Todos |
| `androidx.constraintlayout` | `ConstraintLayout` | Todos |
| `androidx.activity:activity-ktx` | `EdgeToEdge`, Activity Result API | Todos menos aaaa |
| `com.github.bumptech.glide:glide` | Carga de imágenes | AdapterDam2, EjemploDialogoPersonalizado |
| `com.squareup.picasso:picasso` | Carga de imágenes por URL | EjercicioAdaptadoresFinal, EjercicioPokemon |
| `androidx.room` (runtime + compiler) | Base de datos SQLite | EjercicioPokemon, EjemploDialogoPersonalizado |
| `androidx.lifecycle` (livedata, viewmodel) | `LiveData`, `ViewModel` | aaaa (y `LiveData` vía Room en los otros dos) |
| `androidx.navigation` (fragment, ui) | Navigation Component | aaaa |
| `androidx.recyclerview` | `RecyclerView` | aaaa |
| `junit`, `espresso-core`, `ext-junit` | Tests (plantilla) | Todos (sin tests propios) |

Explicación: [25 — Gradle y librerías](02-conceptos/25-gradle-y-librerias.md).

## 7. Recursos importantes

| Tipo | Archivo | Proyecto | Contenido |
|---|---|---|---|
| Drawable XML | `fondo.xml`, `cabecera.xml`, `boton_pulsado.xml` | DisenyoPesos | Degradado, marco redondeado, selector |
| Values | `dimens.xml`, estilos `cursoTitulo(.subtitulo)` | DisenyoPesos | Tamaños y estilos reutilizables |
| Values | `frames.xml` (`cursos`, `imagenes`) | DisenyoPesos | Arrays de strings y drawables |
| Values | `array.xml` (`cursos`), `recursos.xml` (`paisajes`) | AdapterDam2 | Datos del Spinner y del Grid |
| Anim | `anim/entrada.xml`, `salida.xml` | AdapterDam2 | Fundidos `alpha` |
| Transition | `transition/explode.xml`, `slide.xml` | AdapterDam2 | Transiciones de ventana |
| Transition | `transition/opacidad.xml` | EjercicioAdaptadoresFinal | `slide` sin aplicar |
| Tema | `themes.xml` con `windowEnterTransition` | AdapterDam2 | Transiciones globales |
| XML | `xml/file_paths.xml` | EjemploDialogoPersonalizado | Carpetas del `FileProvider` |
| Imágenes | `andando1..4`, `caballo1..8`, `paisaje1..10`, `logo*.png`, `logo_almi.jpg`, `avatar_1..16.xml` | Varios | Fotogramas, logos, vectores |

## 8. Conceptos Android que aparecen

Activity y ciclo de vida · `onCreate`/`onResume`/`onPause` · `setContentView` · `findViewById` · View Binding · listeners (`OnClickListener`, `OnItemClick`, `OnItemLongClick`, `OnItemSelected`, `TextWatcher`, `SensorEventListener`) · `Intent` explícito e implícito · `Bundle`, `putExtra`/`getExtra`, `Serializable` · `Toast`, `Snackbar`, `Dialog`, `AlertDialog`, `DialogFragment` · `LinearLayout` + pesos, `ConstraintLayout`, `ScrollView`, `CoordinatorLayout`, `DrawerLayout` · `ListView`, `GridView`, `Spinner`, `RecyclerView` · `BaseAdapter`, `ArrayAdapter`, `ListAdapter` · `AsyncTask`, `Handler`, `Executor` · Room (`@Entity`, `@Dao`, `@Database`), `LiveData`, `ViewModel` · Fragments, `FragmentManager`, `newInstance` · `MaterialToolbar`, menú de opciones · Navigation Component · transiciones y *shared element* · recursos (`strings`, `colors`, `dimens`, `themes`, `styles`, `arrays`, `drawable`, `anim`, `transition`, `menu`, `navigation`, `xml`) · Glide y Picasso · permisos (`INTERNET`, `WRITE_EXTERNAL_STORAGE`) y `uses-feature` · `SensorManager` · Activity Result API · `FileProvider`, `MediaStore`, `ContentResolver`, `Cursor` · `RecognizerIntent` · Gradle y *version catalog*.

Todos explicados en [02-conceptos](INICIO.md) y resumidos en el [Glosario](01-guias/02-glosario.md).

## 9. Métodos importantes (índice rápido)

| Método | Dónde verlo |
|---|---|
| `onCreate` + bloque EdgeToEdge | [MyApplication](04-proyectos-profesor/MyApplication.md) |
| `getView` de `BaseAdapter` | [AdapterDam2 → CompaniasAdapter](04-proyectos-profesor/AdapterDam2.md) |
| `getView` + `getDropDownView` | [AdapterDam2 → SpinnerDam2Adapter](04-proyectos-profesor/AdapterDam2.md), [EjercicioSpinner](04-proyectos-profesor/EjercicioSpinner.md) |
| `doInBackground` / `onProgressUpdate` / `onPostExecute` | [DisenyoPesos → ProgressAndando](04-proyectos-profesor/DisenyoPesos.md) |
| `onAttach` / `onCreateView` / `onViewCreated` / `newInstance` | [EjemploFragmentos2](04-proyectos-profesor/EjemploFragmentos2.md) |
| `onCreateOptionsMenu` / `onOptionsItemSelected` | [EjemploFragmentos2](04-proyectos-profesor/EjemploFragmentos2.md) |
| `getInstance` (Singleton Room) | [EjercicioPokemon → AppDatabase](04-proyectos-profesor/EjercicioPokemon.md) |
| `observe` de `LiveData` | [EjemploDialogoPersonalizado → consultarUsuarios](04-proyectos-profesor/EjemploDialogoPersonalizado.md) |
| `onSensorChanged` + `registerListener`/`unregisterListener` | [EjemploDialogoPersonalizado → SensoresActivity](04-proyectos-profesor/EjemploDialogoPersonalizado.md) |
| `registerForActivityResult` | [EjemploDialogoPersonalizado → Camera/Voz](04-proyectos-profesor/EjemploDialogoPersonalizado.md) |

## 10. Fragmentos de código reutilizables

Índice en [06-codigo-reutilizable](06-codigo-reutilizable/00-indice.md).

## 11. Archivos que no se entienden solos

> [!warning] ⚠️ Cuidado: archivos que dependen de otros
> | Archivo | Qué hay que leer a la vez |
> |---|---|
> | `ProgressAndando.java` / `AsyntaskDamCaballo.java` | La Activity que las crea (sus *getters*) y el `TypedArray` de `frames.xml` |
> | `SpinnerDam2Adapter.java` | `SpinnerDam2Activity` (el `position - 1`) |
> | `DetalleActivity.java` | `GridViewDam2Activity` (el `Pair` con `VIEW_NAME_HEADER_IMAGE`) y `themes.xml` |
> | `ListDam2Activity.java` | `MainActivity` (`makeSceneTransitionAnimation`) y `transition/slide.xml` |
> | `FragmentoArriba.java` (ambos proyectos) | `IControlFragmentos` y la `MainActivity` que la implementa |
> | `LoginDialogFrag.java`, `RegisterActivity.java` | `AppExecutors`, `AppDatabase`, `UsuariosDao` |
> | `CameraActivity.java` | `AndroidManifest.xml` (`provider`) y `res/xml/file_paths.xml` |
> | `MainActivity.java` de aaaa | `mobile_navigation.xml`, los tres menús y las variantes `layout-w600dp`/`w1240dp` |
