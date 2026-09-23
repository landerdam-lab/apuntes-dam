---
tags:
  - android
  - repaso
  - examen
aliases:
  - Preguntas tipo examen
  - Preguntas de repaso
---

# Preguntas tipo examen y de repaso

Pulsa cada pregunta para ver la respuesta (en Obsidian son callouts plegables). Intenta responder **antes** de abrirla. Hay más preguntas y exámenes completos en los [Simulacros](../07-ejercicios/08-simulacros-de-examen.md).

## Preguntas básicas

> [!question]- 1. ¿Qué es una Activity y qué método se ejecuta primero?
> Una pantalla de la app: una clase que hereda de `AppCompatActivity`. Android llama primero a **`onCreate(Bundle)`**, y ahí se carga el layout con `setContentView`. → [10](../02-conceptos/10-activity-en-profundidad.md)

> [!question]- 2. ¿Qué diferencia hay entre `res/layout`, `res/values` y `res/drawable`?
> `layout`: diseño de pantallas (XML). `values`: textos, colores, tamaños, estilos y arrays. `drawable`: imágenes y dibujos XML (`shape`, `selector`). → [28](../02-conceptos/28-recursos-values.md)

> [!question]- 3. ¿Para qué sirve el `AndroidManifest.xml`?
> Declara la app: sus Activities (y cuál arranca con `MAIN`/`LAUNCHER`), permisos, `uses-feature`, `provider`s y tema. → [24](../02-conceptos/24-manifest-y-permisos.md)

> [!question]- 4. ¿Qué es la clase `R`?
> Una clase **generada al compilar** con un id `int` por cada recurso: `R.layout.activity_main`, `R.id.btnEnviar`, `R.string.app_name`…

> [!question]- 5. ¿Qué es un `Intent`? ¿Explícito o implícito?
> Un mensaje para abrir otro componente. **Explícito:** dice la clase (`new Intent(this, Detalle.class)`). **Implícito:** dice la acción (`ACTION_VIEW`, `ACTION_RECOGNIZE_SPEECH`) y Android busca quién la atiende. → [11](../02-conceptos/11-intent-en-profundidad.md), [23](../02-conceptos/23-voz-e-intents-implicitos.md)

> [!question]- 6. ¿Qué es un adaptador?
> El intermediario entre una colección de datos y una vista de lista (`ListView`, `GridView`, `Spinner`, `RecyclerView`): crea y rellena una fila por dato. → [07](../02-conceptos/07-adaptadores.md)

> [!question]- 7. ¿`dp` o `sp`?
> `dp` para tamaños y márgenes; `sp` solo para textos (respeta el tamaño de letra del usuario).

> [!question]- 8. ¿Qué es Gradle? ¿Qué diferencia hay entre `minSdk` y `targetSdk`?
> Gradle construye la app. `minSdk`: la versión más antigua donde se instala. `targetSdk`: la versión para la que está pensada (qué reglas del sistema acepta). → [25](../02-conceptos/25-gradle-y-librerias.md)

> [!question]- 9. ¿Qué es un Fragment?
> Un trozo reutilizable de interfaz, con su propio layout y ciclo de vida, que vive dentro de una Activity. → [16](../02-conceptos/16-fragmentos.md)

> [!question]- 10. ¿Qué es Room?
> Una librería que facilita usar SQLite: entidad (`@Entity`), DAO (`@Dao`) y base de datos (`@Database`). Room genera el SQL. → [17](../02-conceptos/17-room-base-de-datos.md)

## Preguntas intermedias (flujo y relación XML ↔ Java)

> [!question]- 11. Explica paso a paso qué pasa desde que tocas el icono hasta que ves la pantalla de MyApplication.
> 1) Android lee el manifest y localiza la Activity `MAIN`/`LAUNCHER`. 2) Crea `MainActivity` y llama a `onCreate`. 3) `super.onCreate`. 4) `EdgeToEdge.enable`. 5) `setContentView(R.layout.activity_main)` infla el XML. 6) El listener de *insets* añade el *padding* de las barras. 7) `onStart` → `onResume` → la pantalla es visible. → [MyApplication](../04-proyectos-profesor/MyApplication.md)

> [!question]- 12. ¿Cómo se conecta `android:id="@+id/btnToast"` con el Java de DisenyoPesos?
> `@+id/btnToast` crea `R.id.btnToast`. En Java, `findViewById(R.id.btnToast)` devuelve ese `ImageButton` **después** de `setContentView`, y se le pone un `OnClickListener`.

> [!question]- 13. En `CompaniasAdapter.getView`, ¿por qué `fila.findViewById` y no `findViewById`?
> Porque las vistas `iconoTelefono`, etc. están en la **fila** recién inflada (`compania_telefonica_list.xml`), no en el layout de la Activity. Además, un adaptador no es una Activity y no tiene `findViewById` propio.

> [!question]- 14. En AdapterDam2, ¿por qué `SpinnerDam2Activity` usa `position - 1` y una variable `ver`?
> `position - 1` porque el adaptador añade una fila de aviso al principio (`getCount() = length + 1`). `ver` porque el Spinner llama a `onItemSelected` una vez al montarse; así se ignora esa primera llamada automática.

> [!question]- 15. En `ProgressAndando`, ¿qué se ejecuta en el hilo secundario y qué en el principal?
> Secundario: `doInBackground` (bucle y `sleep`). Principal: `onPreExecute`, `onProgressUpdate` (pinta imagen, barra y texto) y `onPostExecute` (`finish`). `publishProgress` es el puente entre los dos.

> [!question]- 16. ¿Cómo se comunica `FragmentoArriba` con `FragmentoAbajo` en EjemploFragmentos2?
> No se hablan directamente. Arriba llama a `activity.cambiarTexto(texto)` (la interfaz `IControlFragmentos`, obtenida en `onAttach`). La Activity crea un `Bundle` y hace `replace(contenedor2, FragmentoAbajo.newInstance(bundle))`. Abajo lee `getArguments()` en `onViewCreated`.

> [!question]- 17. ¿Por qué `RegisterActivity` puede llamar a `loadAllUsuarios()` en el hilo principal, pero `eliminar()` usa `AppExecutors`?
> `loadAllUsuarios()` devuelve `LiveData`: Room hace la consulta en su propio hilo y avisa con `observe`. `loadUsuarioById` y `delete` devuelven valores directos o `void`, así que hay que llamarlos desde otro hilo, o Room lanza `IllegalStateException`.

> [!question]- 18. ¿Por qué `SensoresActivity` registra los listeners en `onResume` y no en `onCreate`?
> Para escuchar **solo mientras la pantalla está visible**. Se registran en `onResume` y se quitan en `onPause`; si no, el sensor seguiría leyendo en segundo plano y gastando batería.

> [!question]- 19. ¿Qué papel tiene cada pieza al sacar una foto en `CameraActivity`?
> `getExternalFilesDir` → carpeta privada (sin permisos). `FileProvider` → convierte el `File` en una `Uri content://` que la cámara puede escribir. `TakePicture` + `registerForActivityResult` → lanza la cámara y recibe si hubo éxito. `MediaStore` → copia a la galería. Glide → muestra la foto sin agotar la memoria.

> [!question]- 20. ¿Qué diferencia hay entre `getView` y `getDropDownView` en un Spinner?
> `getView`: la fila que se ve con el Spinner **cerrado**. `getDropDownView`: cada fila de la lista **desplegada**.

## Preguntas prácticas (escribir o modificar código)

> [!question]- 21. Escribe el código para abrir `DetalleActivity` pasando un `int` "id" y leerlo en el destino.
> ```java
> Intent i = new Intent(this, DetalleActivity.class);
> i.putExtra("id", 7);
> startActivity(i);
> // destino
> int id = getIntent().getIntExtra("id", -1);
> ```

> [!question]- 22. Escribe un `AlertDialog` Sí/No que, con Sí, llame a `borrar()`.
> ```java
> new AlertDialog.Builder(this).setTitle("Confirmar").setMessage("¿Borrar?")
>     .setPositiveButton("Sí", (d, w) -> borrar())
>     .setNegativeButton("No", null).show();
> ```

> [!question]- 23. Escribe el método `getView` de un `BaseAdapter` para una lista de `String` con layout `item_texto` (id `tvTexto`).
> ```java
> @Override public View getView(int pos, View cv, ViewGroup parent) {
>     if (cv == null) cv = LayoutInflater.from(context).inflate(R.layout.item_texto, parent, false);
>     ((TextView) cv.findViewById(R.id.tvTexto)).setText(datos.get(pos));
>     return cv;
> }
> ```

> [!question]- 24. Escribe una entidad Room `Alumno(id autogenerado, nombre, nota)` y un DAO con insertar y listar por nota descendente (con `LiveData`).
> ```java
> @Entity public class Alumno {
>     @PrimaryKey(autoGenerate = true) public int id;
>     public String nombre; public double nota;
> }
> @Dao public interface AlumnoDao {
>     @Insert void insertar(Alumno a);
>     @Query("SELECT * FROM Alumno ORDER BY nota DESC") LiveData<List<Alumno>> listar();
> }
> ```
> (Con atributos `public`, Room no necesita getters y setters.)

> [!question]- 25. Haz que un `EditText` de edad no permita guardar si está vacío o no es un número.
> `android:inputType="number"` en el XML y, en Java:
> ```java
> String t = etEdad.getText().toString().trim();
> if (t.isEmpty()) { etEdad.setError("Obligatorio"); return; }
> int edad = Integer.parseInt(t);   // seguro gracias a inputType="number"
> ```

> [!question]- 26. Añade un menú con la opción "Acerca de" que muestre un Toast.
> `res/menu/menu_main.xml` con `<item android:id="@+id/acerca" android:title="Acerca de" app:showAsAction="never"/>`, más `onCreateOptionsMenu` (inflar y `return true`) y `onOptionsItemSelected` (`if (item.getItemId() == R.id.acerca) { Toast…; return true; }`). Requiere Toolbar + `setSupportActionBar` si el tema es `NoActionBar`. → [26](../02-conceptos/26-menus-y-toolbar.md)

> [!question]- 27. Crea el `newInstance` de un fragmento que recibe un `String` "titulo".
> Ver [Pasar datos → Fragment](../06-codigo-reutilizable/03-pasar-datos.md).

## ¿Qué pasa si cambio esto?

> [!question]- 28. ¿Qué pasa si cambio el id de un botón en el XML pero no en Java?
> **No compila** (`cannot find symbol R.id.viejoId`). Si el id viejo existiera en **otro** layout, sí compilaría, pero `findViewById` devolvería `null` y la app se cerraría con `NullPointerException` al poner el listener.

> [!question]- 29. ¿Qué pasa si no llamo a `setContentView`?
> La pantalla sale vacía y cualquier `findViewById` devuelve `null` → `NullPointerException` en cuanto uses una vista (en todos los proyectos, en `findViewById(R.id.main)` del bloque EdgeToEdge).

> [!question]- 30. ¿Qué pasa si uso mal `findViewById` (antes de `setContentView`, o buscando una vista de otro layout)?
> Devuelve `null` y la app se cierra con `NullPointerException` al usar esa vista. Típico en adaptadores y diálogos: hay que buscar en `fila.findViewById`, `vista.findViewById` o `view.findViewById`.

> [!question]- 31. ¿Qué pasa si no declaro una Activity en el manifest?
> Compila, pero al hacer `startActivity` la app se cierra con `ActivityNotFoundException: … have you declared this activity in your AndroidManifest.xml?`

> [!question]- 32. ¿Qué pasa si quito `return true` en `onItemLongClick`?
> Devolvería `false` ("no consumido") y, al soltar el dedo, **también** se ejecutaría `onItemClick`. En `RegisterActivity`, además del diálogo de borrado, se cargaría el usuario en el formulario.

> [!question]- 33. ¿Qué pasa si en AdapterDam2 llamo a `setEnterTransition` después de `setContentView`?
> La transición `slide` no se ve (o se ve la del tema), porque la ventana ya se configuró sin ella. → [08](../02-conceptos/08-transiciones.md)

> [!question]- 34. ¿Qué pasa si `Jugador` no implementa `Serializable`?
> `putSerializable("jugadorSeleccionado", jugador)` ni compila, porque espera un `Serializable`. Y si `Equipo` lo implementa pero contiene jugadores que no, al lanzar la Activity se produce `RuntimeException: Parcelable encountered IOException writing serializable object` (`NotSerializableException`).

> [!question]- 35. ¿Qué pasa si quito `annotationProcessor(room-compiler)`?
> Compila, pero al ejecutar `Room.databaseBuilder(...).build()` la app se cierra: `cannot find implementation for …AppDatabase. AppDatabase_Impl does not exist`.

> [!question]- 36. ¿Qué pasa si la `authority` del `FileProvider` en el código no coincide con la del manifest?
> `IllegalArgumentException: Couldn't find meta-data for provider with authority …` al pulsar *Sacar foto*.

> [!question]- 37. ¿Qué pasa si registro el `ActivityResultLauncher` dentro del `onClick`?
> `IllegalStateException: LifecycleOwner … is attempting to register while current state is RESUMED. LifecycleOwners must call register before they are STARTED.`

> [!question]- 38. ¿Qué pasa si no llamo a `unregisterListener` en `onPause`?
> El sensor sigue enviando lecturas con la app en segundo plano: gasto de batería y posible fuga de memoria (el listener retiene la Activity).

> [!question]- 39. ¿Qué pasa si giro el móvil en EjercicioFragmentos después de crear 2 personas?
> La Activity se recrea: la lista `personas` vuelve a estar vacía, `FragmentoMedio` pierde el nombre (constructor con datos) y los fragmentos se duplican (`add` sin comprobar `savedInstanceState`).

## Errores comunes (con respuesta explicada)

> [!question]- 40. `android.content.res.Resources$NotFoundException: String resource ID #0x50`
> Se ha hecho `setText(80)` con un `int`. `setText(int)` busca el **recurso** con ese id. Arreglo: `setText(String.valueOf(80))` o `setText(80 + "")`.

> [!question]- 41. `NumberFormatException: For input string: ""`
> `Integer.parseInt` de un `EditText` vacío (EjercicioPokemon). Validar antes y usar `inputType="number"`.

> [!question]- 42. `CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.`
> Se ha tocado una vista desde un hilo secundario (el PDF de AsyncTask con `Runnable`, o `AppExecutors` cruzado). Pintar en `onProgressUpdate`, `runOnUiThread` o un `Handler` del `Looper` principal.

> [!question]- 43. `ClassCastException: MainActivity cannot be cast to IControlFragmentos`
> La Activity no implementa la interfaz que el fragmento castea en `onAttach`. Añade `implements IControlFragmentos` y sus métodos.

> [!question]- 44. Las imágenes de Picasso salen vacías, sin error visible
> Falta `<uses-permission android:name="android.permission.INTERNET"/>`, la URL no es válida o no hay conexión. Mira Logcat filtrando por `Picasso`.

> [!question]- 45. *The project is using an incompatible version (AGP 9.3.x) of the Android Gradle plugin*
> Tu Android Studio es más antiguo. Actualízalo o baja `agp` en `gradle/libs.versions.toml` a la versión que indique el error. → [Guía 01](../01-guias/01-ejecutar-la-app.md)
