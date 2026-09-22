---
tags:
  - android
  - guia
---

# Glosario A–Z

Todos los términos que aparecen en la documentación, explicados en una o dos frases. Cuando hay un documento que lo explica a fondo, se enlaza.

> Si un término no está aquí, búscalo en el [INDICE](../INDICE.md). Para Java básico (variable, clase, método…) empieza por [00 — Programación básica](../conceptos/00-programacion-basica.md).

## A
- **`@Override`** — Anotación que dice "este método ya existía en la clase padre y lo estoy reemplazando". [00](../conceptos/00-programacion-basica.md)
- **Activity** — Una pantalla de la app. Es una clase Java que hereda de `AppCompatActivity`. [10](../conceptos/10-activity-en-profundidad.md)
- **`adb`** — *Android Debug Bridge*. Programa de línea de comandos para hablar con un móvil o emulador. [09](../conceptos/09-instalacion-estructura-proyecto.md)
- **Adaptador (Adapter)** — Pieza que convierte una lista de datos en filas visibles dentro de un `ListView`, `GridView` o `Spinner`. [07](../conceptos/07-adaptadores.md)
- **AGP** — *Android Gradle Plugin*. El plugin que sabe compilar apps Android. Su versión debe ser compatible con tu Android Studio. [Guía 01](01-ejecutar-la-app.md)
- **AlertDialog** — Ventanita emergente estándar con título, mensaje y botones (sí/no). [18](../conceptos/18-dialogos.md)
- **AnimationDrawable** — Objeto que reproduce una animación frame a frame (`animation-list`); se arranca con `start()`. [06](../conceptos/06-animaciones-frame-by-frame.md)
- **Anotación** — Etiqueta que empieza por `@` (`@Entity`, `@Override`…) que da instrucciones al compilador o a una librería.
- **APK** — El fichero instalable de una app Android (`.apk`).
- **AsyncTask** — Clase antigua (obsoleta) para hacer trabajo en segundo plano. [05](../conceptos/05-asynctask-e-hilos.md)
- **AVD** — *Android Virtual Device*: un móvil virtual (emulador).

## B
- **Builder (patrón)** — Objeto auxiliar al que encadenas configuraciones y al final construye el resultado. Ej.: `AlertDialog.Builder`. [18](../conceptos/18-dialogos.md)
- **Bundle** — "Caja" de datos con etiqueta (clave→valor) para pasar información entre pantallas. [01](../conceptos/01-fundamentos-bundle-intent-ciclo-vida.md)

## C
- **Casting** — Decir a Java "trata este objeto como de otro tipo": `(String) objeto`. [00](../conceptos/00-programacion-basica.md)
- **Clase** — El "plano" de un tipo de objeto: qué datos tiene y qué sabe hacer. [00](../conceptos/00-programacion-basica.md)
- **Clase anónima** — Clase sin nombre que se define y se usa en el mismo sitio, típica de los *listeners*. [04](../conceptos/04-toast-personalizado.md)
- **Compilar** — Traducir tu código a algo que el móvil pueda ejecutar. Si hay errores de sintaxis, falla.
- **Constructor** — Método especial que se ejecuta al crear un objeto con `new`. [00](../conceptos/00-programacion-basica.md)
- **ConstraintLayout** — Layout donde cada vista se ata a otras mediante "restricciones". [03](../conceptos/03-constraintlayout.md)
- **Context** — Objeto que representa "el entorno de la app" (la pantalla actual o la app entera). Muchos métodos lo piden. [10](../conceptos/10-activity-en-profundidad.md)
- **CRUD** — *Create, Read, Update, Delete*: crear, leer, actualizar y borrar datos. Es lo que hace `RegisterActivity`.

## D
- **DAO** — *Data Access Object*: interfaz con las operaciones sobre una tabla. [17](../conceptos/17-room-base-de-datos.md)
- **`dp` / `sp`** — Unidades de tamaño independientes de la pantalla. `dp` para distancias, `sp` para texto.
- **`dismiss()`** — Cierra un diálogo. [18](../conceptos/18-dialogos.md)
- **DialogFragment** — Fragmento que se muestra como ventana emergente con diseño propio. [18](../conceptos/18-dialogos.md)

## E
- **EdgeToEdge** — Modo en que la app se dibuja bajo las barras del sistema (estado y navegación). [01](../conceptos/01-fundamentos-bundle-intent-ciclo-vida.md)
- **Emulador** — Móvil virtual que se ejecuta en tu PC. [Guía 01](01-ejecutar-la-app.md)
- **Entidad (`@Entity`)** — Clase Java que Room convierte en una tabla. [17](../conceptos/17-room-base-de-datos.md)
- **Executor** — Objeto al que le das una tarea y decide en qué hilo ejecutarla. [20](../conceptos/20-executors-y-livedata.md)
- **`extends`** — Palabra clave de herencia: "esta clase parte de esa otra". [00](../conceptos/00-programacion-basica.md)

## F
- **`final`** — Variable que no puede reasignarse. Obligatoria (o "efectivamente final") si la usa una clase anónima. [04](../conceptos/04-toast-personalizado.md)
- **`findViewById`** — Busca en el layout el componente con un `id` concreto. [01](../conceptos/01-fundamentos-bundle-intent-ciclo-vida.md)
- **Fragment** — Trozo reutilizable de pantalla con su propio layout y ciclo de vida. [16](../conceptos/16-fragmentos.md)

## G
- **Getter / Setter** — Métodos `getX()` / `setX()` para leer / cambiar un atributo `private`. [00](../conceptos/00-programacion-basica.md)
- **Glide** — Librería para cargar imágenes en un `ImageView` de forma eficiente: `Glide.with(contexto).load(...).into(imagen)`. [07](../conceptos/07-adaptadores.md)
- **Gradle** — Sistema que compila el proyecto y descarga las librerías. [09](../conceptos/09-instalacion-estructura-proyecto.md)
- **GridView** — Rejilla de elementos (como `ListView` pero con columnas). [07](../conceptos/07-adaptadores.md)

## H
- **Handler** — Objeto que permite dejar una tarea en la cola de un hilo (por ejemplo, "en 2 segundos haz esto"). [12](../conceptos/12-hilos-en-profundidad.md)
- **Hilo (thread)** — Línea de ejecución. La app tiene un **hilo principal** (interfaz) y puede crear **hilos secundarios**. [12](../conceptos/12-hilos-en-profundidad.md)
- **Herencia** — Que una clase reutilice todo lo de otra (`extends`). [00](../conceptos/00-programacion-basica.md)

## I
- **`id` (`android:id`)** — Nombre único de un componente del layout (`@+id/btnAceptar`), para encontrarlo desde Java.
- **Inflar (`inflate`)** — Convertir un fichero XML de layout en objetos `View` reales en memoria. [13](../conceptos/13-view-viewgroup.md)
- **Interfaz** — "Contrato" que lista métodos que una clase promete implementar (`implements`). [00](../conceptos/00-programacion-basica.md)
- **Intent** — Objeto que representa una intención, normalmente "abre esta pantalla". [11](../conceptos/11-intent-en-profundidad.md)

## L
- **Layout** — Fichero XML que describe cómo se ve una pantalla. `LinearLayout`, `ConstraintLayout`… [02](../conceptos/02-diseno-basado-en-pesos.md) [03](../conceptos/03-constraintlayout.md)
- **`layout_weight`** — Peso que reparte el espacio sobrante dentro de un `LinearLayout`. [02](../conceptos/02-diseno-basado-en-pesos.md)
- **Listener** — Código que se ejecuta cuando ocurre un evento (clic, cambio de texto…). [19](../conceptos/19-textwatcher-y-eventos-de-lista.md)
- **ListView** — Lista vertical de filas, alimentada por un adaptador. [07](../conceptos/07-adaptadores.md)
- **LiveData** — Contenedor de datos "observable" que avisa cuando cambian. [20](../conceptos/20-executors-y-livedata.md)
- **Logcat** — Panel de Android Studio con los mensajes y errores de la app. [Guía 01](01-ejecutar-la-app.md)

## M
- **Manifest (`AndroidManifest.xml`)** — "Índice" de la app: sus pantallas, permisos y cuál se abre primero. [01](../conceptos/01-fundamentos-bundle-intent-ciclo-vida.md)
- **Método** — Bloque de instrucciones con nombre que se puede llamar. [00](../conceptos/00-programacion-basica.md)
- **Modal** — Ventana que exige una respuesta antes de seguir (un diálogo).

## N
- **`null`** — "Ninguna cosa": una variable que no apunta a ningún objeto. Usarla como si tuviera algo provoca `NullPointerException`. [00](../conceptos/00-programacion-basica.md)

## O
- **Objeto (instancia)** — Una "cosa real" creada a partir de una clase con `new`. [00](../conceptos/00-programacion-basica.md)
- **ORM** — *Object-Relational Mapping*: traducir automáticamente entre objetos Java y filas de tablas. Room es un ORM. [17](../conceptos/17-room-base-de-datos.md)

## P
- **Paquete (`package`)** — Carpeta que agrupa clases relacionadas. [00](../conceptos/00-programacion-basica.md)
- **Parámetro** — Dato de entrada de un método.
- **Picasso** — Librería parecida a Glide, típica para imágenes de internet (necesita el permiso `INTERNET`). [07](../conceptos/07-adaptadores.md)
- **Primary key (clave primaria)** — Columna que identifica de forma única cada fila (`id`). [17](../conceptos/17-room-base-de-datos.md)

## Q
- **Query** — Consulta a la base de datos, escrita en SQL (`SELECT …`).

## R
- **`R`** — Clase que Android genera sola con una constante por cada recurso (`R.id.x`, `R.layout.y`). [09](../conceptos/09-instalacion-estructura-proyecto.md)
- **RecyclerView** — Versión moderna y eficiente de `ListView`. [14](../conceptos/14-recyclerview.md)
- **Recurso** — Cualquier fichero de `res/`: layouts, textos, colores, imágenes…
- **Room** — Librería de Google para usar SQLite fácilmente con objetos Java. [17](../conceptos/17-room-base-de-datos.md)
- **Runnable** — Bloque de código (`run()`) que se puede entregar a un hilo para que lo ejecute. [20](../conceptos/20-executors-y-livedata.md)

## S
- **SDK** — Conjunto de librerías y herramientas para programar Android. [09](../conceptos/09-instalacion-estructura-proyecto.md)
- **Serializable** — Interfaz que permite convertir un objeto en bytes, para pasarlo en un `Bundle`/`Intent`. [01](../conceptos/01-fundamentos-bundle-intent-ciclo-vida.md)
- **Singleton** — Patrón para que exista **una sola instancia** de una clase en toda la app. [17](../conceptos/17-room-base-de-datos.md)
- **Spinner** — Desplegable para elegir una opción. [07](../conceptos/07-adaptadores.md)
- **SQLite** — Base de datos ligera incluida en Android, guardada en un fichero.
- **`static`** — Pertenece a la clase entera, no a un objeto concreto. [00](../conceptos/00-programacion-basica.md)
- **Sync (sincronizar)** — Que Gradle lea la configuración y descargue lo necesario. [Guía 01](01-ejecutar-la-app.md)

## T
- **TextWatcher** — "Vigilante" que avisa cuando cambia el texto de un `EditText`. [19](../conceptos/19-textwatcher-y-eventos-de-lista.md)
- **Toast** — Mensaje flotante que desaparece solo. **No olvides `.show()`.** [04](../conceptos/04-toast-personalizado.md)

## U
- **Transición** — Animación al pasar de una pantalla a otra: de escena, `overridePendingTransition` o de elemento compartido. [08](../conceptos/08-transiciones.md)
- **`TypedArray`** — Lista de recursos (por ejemplo imágenes) declarada en `res/values`; se lee con `obtainTypedArray` y `getResourceId`. [07](../conceptos/07-adaptadores.md)
- **UI thread (hilo principal)** — El hilo que dibuja la pantalla; es el único que puede tocar las vistas. [12](../conceptos/12-hilos-en-profundidad.md)

## V
- **View / ViewGroup** — `View` es cualquier componente visual; `ViewGroup` es una `View` que contiene otras (los layouts). [13](../conceptos/13-view-viewgroup.md)

## X
- **ViewHolder** — Objeto de un `RecyclerView` que guarda las vistas de una fila para reutilizarlas sin volver a buscarlas. [07](../conceptos/07-adaptadores.md)
- **XML** — Formato de texto con etiquetas (`<Button …/>`) usado para layouts, recursos y el manifiesto.
