---
tags:
  - android
  - guia
  - estudio
aliases:
  - Plan de estudio
  - Guía paso a paso para estudiar
---

# 04 — Plan de estudio paso a paso

Cómo recorrer **PDFs → proyectos del profesor → ejercicios** en orden, sin perderte. Cada etapa tiene: qué leer, qué abrir, qué ejecutar, qué tocar y qué errores provocar a propósito para aprender a reconocerlos.

> [!tip] 💡 Método para cada proyecto
> 1. Lee el **PDF** (o su [resumen](../03-pdfs/00-relacion-pdfs-proyectos.md)).
> 2. Abre el proyecto en Android Studio (*File → Open* → `android/codigo/AndroidEjemploProyectos/<Proyecto>`) y **ejecútalo** antes de leer el código.
> 3. Lee la **nota del proyecto** con el código abierto al lado.
> 4. Haz los **ejercicios** de la nota y los de esta guía.
> 5. **Rompe algo a propósito** y lee el error en Logcat.
> 6. Copia un fragmento de [código reutilizable](../06-codigo-reutilizable/00-indice.md) a una app nueva.

## Etapa 0 — Preparación (1 sesión)
| Paso | Qué |
|---|---|
| Leer | [PDF 0 — Instalación](../03-pdfs/01-instalacion-android.md) · [Guía 01 — Ejecutar la app](01-ejecutar-la-app.md) · [00 — Programación básica](../02-conceptos/00-programacion-basica.md) si nunca has programado |
| Abrir | [MyApplication](../04-proyectos-profesor/MyApplication.md) |
| Mirar | `MainActivity.java`, `activity_main.xml`, `AndroidManifest.xml`, `app/build.gradle.kts` |
| Ejecutar | En el emulador y, si puedes, en tu móvil |
| Modificar | Texto "Hello World!" → tu nombre (vía `strings.xml`) |
| Provocar | Quitar `android:id="@+id/main"` → `NullPointerException` |

## Etapa 1 — Diseño de pantallas (2–3 sesiones)
| Paso | Qué |
|---|---|
| Leer | PDFs [Pesos](../03-pdfs/02-diseno-basado-en-pesos.md) → [Constraint](../03-pdfs/04-constraint.md) · Conceptos [02](../02-conceptos/02-diseno-basado-en-pesos.md), [03](../02-conceptos/03-constraintlayout.md), [15](../02-conceptos/15-drawables-formas-y-selectores.md), [28](../02-conceptos/28-recursos-values.md) |
| Abrir | [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md) (solo `activity_main.xml` y `res/`), [DisenyoConstraint](../04-proyectos-profesor/DisenyoConstraint.md) |
| Modificar | Colores del degradado, pesos 2/7/1 → 1/8/1, sexto botón con otro icono |
| Ejercicios | [Nivel 2](../07-ejercicios/02-nivel-2-disenos-xml.md) · Ejercicios A y B de abajo |
| Provocar | `layout_height="wrap_content"` en vez de `0dp` con peso → el reparto deja de ser proporcional |

## Etapa 2 — Eventos, avisos e hilos (2 sesiones)
| Paso | Qué |
|---|---|
| Leer | PDFs [Toast](../03-pdfs/05-toast-personalizado.md) → [AsyncTask](../03-pdfs/06-asynctask.md) → [Frame by frame](../03-pdfs/07-frame-by-frame.md) · Conceptos [04](../02-conceptos/04-toast-personalizado.md), [05](../02-conceptos/05-asynctask-e-hilos.md), [12](../02-conceptos/12-hilos-en-profundidad.md) |
| Abrir | [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md) (Java), [EjercicioAsyncTaskAlumnos](../04-proyectos-profesor/EjercicioAsyncTaskAlumnos.md) |
| Modificar | Arreglar `andando4` y el caballo que no se mueve |
| Ejercicios | [Nivel 1](../07-ejercicios/01-nivel-1-java-y-primera-app.md), [Nivel 4](../07-ejercicios/04-nivel-4-toast-asynctask-animaciones-transiciones.md) · C, D y E de abajo |
| Provocar | `setText` dentro de `doInBackground` → `CalledFromWrongThreadException` |

## Etapa 3 — Listas y adaptadores (3 sesiones, la más importante)
| Paso | Qué |
|---|---|
| Leer | PDFs [Adaptadores](../03-pdfs/09-adaptadores.md) → [Transiciones](../03-pdfs/10-transiciones.md) · Conceptos [07](../02-conceptos/07-adaptadores.md), [08](../02-conceptos/08-transiciones.md), [14](../02-conceptos/14-recyclerview.md), [19](../02-conceptos/19-textwatcher-y-eventos-de-lista.md) |
| Abrir | [AdapterDam2](../04-proyectos-profesor/AdapterDam2.md) → [EjercicioSpinner](../04-proyectos-profesor/EjercicioSpinner.md) → [EjercicioAdaptadoresFinal](../04-proyectos-profesor/EjercicioAdaptadoresFinal.md) |
| Modificar | Añadir una compañía; mostrar el dorsal del jugador |
| Ejercicios | [Nivel 3](../07-ejercicios/03-nivel-3-adaptadores.md) · F y G de abajo |
| Provocar | `setText(int)` con un número → `Resources$NotFoundException`; quitar `INTERNET` → imágenes vacías |
| Reutilizar | [Listas y adaptadores](../06-codigo-reutilizable/06-listas-y-adaptadores.md) en una app nueva de "asignaturas" |

## Etapa 4 — Fragmentos (2 sesiones)
| Paso | Qué |
|---|---|
| Leer | [PDF Fragmentos](../03-pdfs/11-fragmentos.md) · [16](../02-conceptos/16-fragmentos.md), [26](../02-conceptos/26-menus-y-toolbar.md) |
| Abrir | [EjemploFragmentos2](../04-proyectos-profesor/EjemploFragmentos2.md) → [EjercicioFragmentos](../04-proyectos-profesor/EjercicioFragmentos.md) |
| Ejercicios | [Nivel 5](../07-ejercicios/05-nivel-5-fragmentos.md) · H de abajo |
| Provocar | Girar el emulador → fragmentos duplicados; quitar `implements IControlFragmentos` → `ClassCastException` |

## Etapa 5 — Datos persistentes (3 sesiones)
| Paso | Qué |
|---|---|
| Leer | [PDF Diálogo + SQLite](../03-pdfs/12-dialogo-sqlite-personalizado.md) · [17](../02-conceptos/17-room-base-de-datos.md), [18](../02-conceptos/18-dialogos.md), [20](../02-conceptos/20-executors-y-livedata.md) |
| Abrir | [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) (login y registro) → [EjercicioPokemon](../04-proyectos-profesor/EjercicioPokemon.md) |
| Ejercicios | [Nivel 6](../07-ejercicios/06-nivel-6-dialogos-y-room.md), [Nivel 7](../07-ejercicios/07-nivel-7-mejoras-del-proyecto.md) · I y J de abajo |
| Provocar | `insert` sin `AppExecutors` → `Cannot access database on the main thread` |
| Reutilizar | [Plantilla Room](../06-codigo-reutilizable/07-base-de-datos-room.md) para una app de notas |

## Etapa 6 — Hardware: sensores, cámara y voz (2 sesiones)
| Paso | Qué |
|---|---|
| Leer | PDFs [Sensores](../03-pdfs/13-sensores.md) → [Cámara](../03-pdfs/14-camara-y-almacenamiento.md) → [Voz](../03-pdfs/15-voz.md) · [21](../02-conceptos/21-sensores.md), [22](../02-conceptos/22-camara-y-almacenamiento.md), [23](../02-conceptos/23-voz-e-intents-implicitos.md), [24](../02-conceptos/24-manifest-y-permisos.md) |
| Abrir | [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) (Sensores, Camera, Voz) |
| Ejecutar | Emulador **con Google Play**; mover los sensores virtuales |
| Provocar | Cambiar la authority del `FileProvider` → `IllegalArgumentException` |
| Ejercicio | K de abajo |

## Etapa 7 — Repaso y examen
- [Preguntas tipo examen](../08-repaso-examen/01-preguntas-tipo-examen.md) · [Simulacros](../07-ejercicios/08-simulacros-de-examen.md) · [Glosario](02-glosario.md) · [Errores comunes](03-errores-comunes.md) · [Código antiguo y malas prácticas](05-codigo-antiguo-y-malas-practicas.md)
- Opcional: [aaaa](../04-proyectos-profesor/aaaa.md) y [27](../02-conceptos/27-navigation-viewbinding-viewmodel.md) para ver Android moderno.

---

# Ejercicios prácticos sobre los proyectos del profesor

## A. Cambiar textos (DisenyoPesos) ⭐
**Objetivo:** que el menú diga "DAM 2 — Tu nombre" y el pie tu email.
**Archivos:** `res/values/strings.xml`.
**Pasos:** 1) Abre `strings.xml`. 2) Cambia `curso` y `pie`. 3) Ejecuta.
**Resultado esperado:** los textos cambian sin tocar el layout ni el Java.
> [!tip]- Pista
> El layout usa `@string/curso` y `@string/pie`. Busca esos nombres.

> [!success]- Solución explicada
> ```xml
> <string name="curso">DAM 2 — Ane Etxeberria</string>
> <string name="pie">ane@almi.eus</string>
> ```
> Como el layout referencia el recurso y no el texto, basta con cambiarlo en un sitio. Esa es la ventaja de `strings.xml`.

## B. Cambiar colores (DisenyoPesos) ⭐
**Objetivo:** fondo degradado de verde a blanco y título en negro.
**Archivos:** `res/drawable/fondo.xml`, `res/values/themes.xml`.
**Pasos:** 1) Cambia `startColor`, `centerColor` y `endColor`. 2) En el estilo `cursoTitulo`, cambia `android:textColor`.
**Resultado esperado:** fondo verde y títulos negros (el subtítulo también, porque hereda).
> [!tip]- Pista
> El subtítulo usa `cursoTitulo.subtitulo`: hereda el color, así que no hay que tocarlo.

> [!success]- Solución explicada
> ```xml
> <gradient android:startColor="#2ecc71" android:centerColor="#ffffff" android:endColor="#2ecc71" android:angle="270"/>
> ```
> ```xml
> <item name="android:textColor">@color/black</item>
> ```

## C. Añadir un botón con Toast (MyApplication) ⭐
**Objetivo:** un botón "Saludar" bajo el texto que muestre "¡Hola!".
**Archivos:** `activity_main.xml`, `MainActivity.java`, `strings.xml`.
**Pasos:** 1) Añade un `Button` con id `btnSaludar`, atado debajo del `TextView`. 2) En `onCreate`, tras `setContentView`, búscalo y ponle un listener con `Toast`.
**Resultado esperado:** al pulsar sale "¡Hola!" abajo durante 2 s.
> [!tip]- Pista
> El `TextView` no tiene id. Dale uno (`tvHola`) para poder atar el botón con `layout_constraintTop_toBottomOf`.

> [!success]- Solución explicada
> ```xml
> <TextView android:id="@+id/tvHola" … />
> <Button android:id="@+id/btnSaludar" android:layout_width="wrap_content" android:layout_height="wrap_content"
>     android:layout_marginTop="16dp" android:text="@string/saludar"
>     app:layout_constraintTop_toBottomOf="@id/tvHola"
>     app:layout_constraintStart_toStartOf="parent" app:layout_constraintEnd_toEndOf="parent"/>
> ```
> ```java
> findViewById(R.id.btnSaludar).setOnClickListener(v ->
>         Toast.makeText(this, "¡Hola!", Toast.LENGTH_SHORT).show());
> ```
> Quita `app:layout_constraintBottom_toBottomOf="parent"` del `TextView` si quieres que suba; si no, el conjunto queda centrado.

## D. Crear una nueva Activity (MyApplication) ⭐⭐
**Objetivo:** que el botón del ejercicio C abra una segunda pantalla.
**Archivos:** nueva `SegundaActivity` (+ layout) y `MainActivity.java`. El manifest lo actualiza Android Studio.
**Pasos:** 1) *File → New → Activity → Empty Views Activity* → `SegundaActivity`. 2) Cambia el listener: `startActivity(new Intent(this, SegundaActivity.class))`. 3) Comprueba que el manifest tiene la nueva `<activity>`.
**Resultado esperado:** al pulsar se abre una pantalla en blanco; con *Atrás* vuelves.
> [!tip]- Pista
> Dentro de una lambda, `this` es la Activity; dentro de una clase anónima (`new View.OnClickListener(){…}`) tendrías que escribir `MainActivity.this`.

> [!success]- Solución explicada
> ```java
> findViewById(R.id.btnSaludar).setOnClickListener(v -> startActivity(new Intent(this, SegundaActivity.class)));
> ```
> Android Studio añade al manifest `<activity android:name=".SegundaActivity" android:exported="false"/>`. Si la creas como clase Java normal, tendrás que añadirla a mano o te saldrá `ActivityNotFoundException`.

## E. Pasar datos entre pantallas ⭐⭐
**Objetivo:** escribir un nombre en `MainActivity` y verlo en `SegundaActivity`.
**Archivos:** los dos layouts y las dos clases.
**Pasos:** 1) `EditText etNombre` en `activity_main`. 2) Al pulsar: `intent.putExtra("nombre", texto)`. 3) En `SegundaActivity`: `getIntent().getStringExtra("nombre")` → un `TextView`.
**Resultado esperado:** "Hola, Ane" en la segunda pantalla.
> [!tip]- Pista
> La clave (`"nombre"`) debe ser **idéntica** en los dos lados. Mejor en una constante.

> [!success]- Solución explicada
> ```java
> // MainActivity
> EditText et = findViewById(R.id.etNombre);
> findViewById(R.id.btnSaludar).setOnClickListener(v -> {
>     Intent i = new Intent(this, SegundaActivity.class);
>     i.putExtra(SegundaActivity.EXTRA_NOMBRE, et.getText().toString().trim());
>     startActivity(i);
> });
> // SegundaActivity
> public static final String EXTRA_NOMBRE = "nombre";
> …
> String nombre = getIntent().getStringExtra(EXTRA_NOMBRE);
> ((TextView) findViewById(R.id.tvSaludo)).setText("Hola, " + nombre);
> ```

## F. Modificar un layout de fila (AdapterDam2) ⭐⭐
**Objetivo:** que cada compañía muestre "80.0 €/mes" en vez de "80.0".
**Archivos:** `CompaniasAdapter.java` (y opcionalmente `strings.xml`).
**Pasos:** en `getView`, cambia el `setText` del precio.
**Resultado esperado:** "80.0 €/mes", "40.0 €/mes"…
> [!tip]- Pista
> `String.format(Locale.getDefault(), "%.2f €/mes", precio)` da dos decimales.

> [!success]- Solución explicada
> ```java
> tvPrecio.setText(String.format(Locale.getDefault(), "%.2f €/mes", compania.getPrecio()));
> ```
> Con `Locale` de España saldrá "80,00 €/mes" (coma decimal). Lo ideal sería una cadena con formato en `strings.xml`: `<string name="precio_mes">%.2f €/mes</string>` → `getString(R.string.precio_mes, precio)` (desde un adaptador: `context.getString(...)`).

## G. Crear una lista desde cero ⭐⭐⭐
**Objetivo:** una app nueva con la lista de tus asignaturas; al tocar una, un Toast con su nombre.
**Archivos:** `activity_main.xml` (`ListView`), `MainActivity.java`.
**Pasos:** 1) `ListView lvAsignaturas`. 2) `ArrayAdapter` con `android.R.layout.simple_list_item_1`. 3) `setOnItemClickListener`.
**Resultado esperado:** lista con 6 asignaturas; al tocar, un Toast.
> [!tip]- Pista
> No hace falta un adaptador propio: `ArrayAdapter<String>` ya sabe pintar textos.

> [!success]- Solución explicada
> ```java
> String[] asignaturas = {"PMDM", "AD", "DI", "PSP", "SGE", "EIE"};
> ListView lv = findViewById(R.id.lvAsignaturas);
> lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, asignaturas));
> lv.setOnItemClickListener((p, v, pos, id) ->
>         Toast.makeText(this, asignaturas[pos], Toast.LENGTH_SHORT).show());
> ```
> **Ampliación:** pasa a un modelo `Asignatura(nombre, horas, icono)` y a `ElementosAdapter` de [Listas](../06-codigo-reutilizable/06-listas-y-adaptadores.md).

## H. Fragmentos: conservar texto y color (EjemploFragmentos2) ⭐⭐⭐
**Objetivo:** que al pulsar Rojo no se pierda el texto enviado.
**Archivos:** `MainActivity.java`.
**Pasos:** 1) Atributos `ultimoTexto` y `ultimoColor`. 2) En `cambiarTexto`/`cambiarColor`, actualizar el que toque y meter **los dos** en el `Bundle`.
**Resultado esperado:** escribes "hola", pulsas Rojo → "hola" en rojo.
> [!success]- Solución explicada
> ```java
> private String ultimoTexto = "abajo";
> private int ultimoColor = Color.BLACK;
>
> private void refrescarAbajo() {
>     Bundle b = new Bundle();
>     b.putString("saludo", ultimoTexto);
>     b.putInt("color", ultimoColor);
>     getSupportFragmentManager().beginTransaction()
>             .replace(R.id.contenedor2, FragmentoAbajo.newInstance(b)).commit();
> }
> @Override public void cambiarTexto(String t) { ultimoTexto = t; refrescarAbajo(); }
> @Override public void cambiarColor(int c) { ultimoColor = c; refrescarAbajo(); }
> ```
> `FragmentoAbajo` no cambia: ya lee las dos claves si existen.

## I. Validar un formulario (EjercicioPokemon) ⭐⭐⭐
**Objetivo:** que `RegisterPokemon` no se cierre con campos vacíos y que avise en cada campo.
**Archivos:** `RegisterPokemon.java`, `activity_register_pokemon.xml`.
**Pasos:** 1) `inputType="number"` en `etPokedex`. 2) Antes de crear el Pokémon, validar con `setError`. 3) Tras insertar: `Toast` + `finish()`.
**Resultado esperado:** no se puede guardar un Pokémon incompleto y, al guardarlo, vuelves a la lista.
> [!success]- Solución explicada
> ```java
> btnCrear.setOnClickListener(v -> {
>     String nombre = etNombre.getText().toString().trim();
>     String tipo = etTipo.getText().toString().trim();
>     String numTxt = etPokedex.getText().toString().trim();
>     boolean ok = true;
>     if (nombre.isEmpty()) { etNombre.setError("Obligatorio"); ok = false; }
>     if (tipo.isEmpty())   { etTipo.setError("Obligatorio");   ok = false; }
>     if (numTxt.isEmpty()) { etPokedex.setError("Obligatorio"); ok = false; }
>     if (!ok) return;
>     Pokemon p = new Pokemon(nombre, tipo, etFoto.getText().toString().trim(), Integer.parseInt(numTxt));
>     AppExecutors.getInstance().getDiskIO().execute(() -> mydb.pokemonDao().insertPokemon(p));
>     Toast.makeText(this, "Pokémon guardado", Toast.LENGTH_SHORT).show();
>     finish();
> });
> ```
> Con `inputType="number"` solo se pueden escribir dígitos, así que `parseInt` ya no falla si el campo no está vacío.

## J. Reutilizar un fragmento de código en otro proyecto ⭐⭐⭐
**Objetivo:** llevar el `AlertDialog` de confirmación de `RegisterActivity` al borrado de EjercicioPokemon.
**Archivos:** `MainActivity.java` de EjercicioPokemon.
**Pasos:** 1) Copia el `AlertDialog` de [Mensajes](../06-codigo-reutilizable/04-mensajes-toast-dialogos.md). 2) Sustituye la detección de doble toque por `setOnItemLongClickListener`… o, mejor, arregla el doble toque ([Utilidades](../06-codigo-reutilizable/09-utilidades.md)) y **pregunta** antes de borrar.
**Resultado esperado:** "¿Borrar Pikachu?" Sí/No antes de eliminar.
> [!success]- Solución explicada
> ```java
> private void confirmarBorrado(Pokemon p) {
>     new AlertDialog.Builder(this)
>             .setTitle("Borrar")
>             .setMessage("¿Borrar " + p.getNombre() + "?")
>             .setPositiveButton("Sí", (d, w) -> AppExecutors.getInstance().getDiskIO()
>                     .execute(() -> mydb.pokemonDao().delete(p)))
>             .setNegativeButton("No", null)
>             .show();
> }
> // en el doble toque corregido: onDobleToque(pos) → confirmarBorrado((Pokemon) adapter.getItem(pos));
> ```

## K. Sensores: acelerómetro (EjemploDialogoPersonalizado) ⭐⭐⭐⭐
**Objetivo:** mostrar X, Y y Z del acelerómetro en `SensoresActivity`.
**Archivos:** `activity_sensores.xml` (3 `TextView`), `SensoresActivity.java`, manifest (`uses-feature accelerometer`).
**Pasos:** mismo patrón que la proximidad: sensor, listener, registrar en `onResume` y quitar en `onPause`.
**Resultado esperado:** con el móvil plano, Z ≈ 9,8; al inclinarlo cambian X e Y.
> [!success]- Solución
> Plantilla completa en [21 — Sensores](../02-conceptos/21-sensores.md) (ejemplo reutilizable del acelerómetro). Recuerda añadir `unregisterListener(acelerometroListener)` en `onPause`.

---

## Errores que conviene provocar una vez (para reconocerlos luego)
| Provoca | Error que verás | Lección |
|---|---|---|
| `findViewById` antes de `setContentView` | `NullPointerException` | El layout aún no existe |
| Borrar una `<activity>` del manifest | `ActivityNotFoundException` | Todas las pantallas se declaran |
| `setText(5)` | `Resources$NotFoundException` | Convierte a `String` |
| `Integer.parseInt("")` | `NumberFormatException` | Valida antes |
| Room en el hilo principal | `IllegalStateException: Cannot access database on the main thread` | `AppExecutors` |
| Tocar una vista desde otro hilo | `CalledFromWrongThreadException` | Vuelve al hilo principal |
| Quitar `implements IControlFragmentos` | `ClassCastException` en `onAttach` | El contrato es obligatorio |
| `registerForActivityResult` dentro de un clic | `IllegalStateException … RESUMED` | Registrar en `onCreate` |
| Quitar el permiso `INTERNET` | Imágenes vacías (sin cierre) | Revisa el manifest |
| Mala authority del `FileProvider` | `IllegalArgumentException` | Manifest y código deben coincidir |

Detalle de cada uno en [03 — Errores comunes](03-errores-comunes.md).
