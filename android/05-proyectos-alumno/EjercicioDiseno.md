---
tags:
  - android
  - proyecto
  - estado/completo
---

# Proyecto: `EjercicioDiseno`

## El enunciado original del ejercicio

El PDF del curso (`1-EjercicioDiseno.pdf`) planteaba estos requisitos concretos:
1. Color de fondo de la aplicación `#AAC1E2`.
2. Sombra en los `TextView` bajo los botones (`shadowColor`/`shadowDx`/`shadowDy`/`shadowRadius`), con estilos reutilizables tanto para títulos/pies de página como para las etiquetas bajo los botones.
3. Botones con dos imágenes cada uno (normal y "pulsado"), cambiando automáticamente al tocar.
4. La app sin barra de título ni barra de notificaciones.
5. El botón "Nuevo" debe llevar a una nueva actividad.
6. Diseño "lo más adaptativo posible".

Comparando con el código real (ver más abajo), **se cumplen los 6 puntos**: el color de fondo coincide exactamente (`fondo.xml`), hay sombra de texto vía el estilo `titulo.sombreado`, los botones usan `<selector>` con estado `pressed`, la pantalla completa se consigue con `android:windowFullscreen` en el tema, `btnNuevo` navega a `MainActivity2` (vacía), y todo el diseño usa `LinearLayout` + pesos (ver [../conceptos/02-diseno-basado-en-pesos](../02-conceptos/02-diseno-basado-en-pesos.md)) para adaptarse a cualquier tamaño de pantalla.

## Qué es

El ejercicio más completo en cuanto a **diseño de interfaz**: una pantalla de menú construida con `LinearLayout` + pesos (ver [02-diseno-basado-en-pesos](../02-conceptos/02-diseno-basado-en-pesos.md)), que da acceso a dos animaciones frame-by-frame (un caballo andando y un Pikachu) y a un "toast" personalizado — combinando casi todas las técnicas de los demás proyectos en uno solo.

## Estructura

```
app/src/main/java/com/example/ejerciciodiseno/
├── MainActivity.java          → menú principal (LinearLayout + pesos), lanza todo lo demás
├── MainActivity2.java         → pantalla vacía (destino del botón "Nuevo")
├── Caballo.java                → Activity de la animación del caballo
├── ProgresoCaballo.java        → AsyncTask que anima el caballo
├── Pikachu.java                 → Activity de la animación de Pikachu
└── AnimacionPikachu.java        → AsyncTask que anima a Pikachu
app/src/main/res/layout/
├── activity_main.xml           → menú con pesos (ver conceptos/02)
├── activity_main2.xml
├── activity_caballo.xml
├── activity_pikachu.xml
└── nueva_main.xml               → layout del toast personalizado de usuario/contraseña
```

## `activity_main.xml` — el layout de menú

Ya analizado línea a línea en [02-diseno-basado-en-pesos](../02-conceptos/02-diseno-basado-en-pesos.md) §3: una cabecera (peso 0.5), un cuerpo con una rejilla 2×2 de botones (peso 4) y un pie (peso 0.5), todo construido con `LinearLayout` anidados y `layout_weight`, sin usar `ConstraintLayout` en absoluto.

## `MainActivity.java` fragmento a fragmento

```java
private TypedArray pikachu = null;
private ImageView imageCentral = null;
```
Campos guardados como **atributos** de la Activity (ver [00-programacion-basica](../02-conceptos/00-programacion-basica.md) §5 si no sabes qué es un atributo) — aunque en este fichero en particular no se acaban usando desde fuera (a diferencia de `Caballo`/`Pikachu`, que sí los exponen a su `AsyncTask` correspondiente).

### Botón "Caballo" — navegación simple

```java
ImageButton btnCaballo = findViewById(R.id.botonCaballo);
btnCaballo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), Caballo.class);
        startActivity(intent);
    }
});
```
Un `Intent` explícito normal (ver [01-fundamentos-bundle-intent-ciclo-vida](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) §3), sin extras ni animación.

> Justo encima hay un comentario en el código con una forma alternativa de castear un botón:
> ```java
> //SI ES BOTON NORMAL PARA CASTEAR Y SI USAS IMAGE BUTTON PUEDES IGUALMENTE
> /*((Button)findViewById(R.id.botonCaballo)).setOnClickListener(...)*/
> ```
> Es una nota recordatorio de que `findViewById` con **casteo** explícito (`(Button) findViewById(...)` — ver [00-programacion-basica](../02-conceptos/00-programacion-basica.md) §12 si no sabes qué es un cast) es la forma clásica y funciona igual de bien que la asignación con inferencia de tipo moderna (`Button btn = findViewById(...)`) — ambas son válidas, la segunda es simplemente más cómoda de escribir.

### Botón "Pikachu" — Toast personalizado que encadena a OTRA navegación

```java
ImageButton btnPikachu = findViewById(R.id.btnPikachu);
btnPikachu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        View vista = getLayoutInflater().inflate(R.layout.activity_pikachu, null);
        ...
        final Dialog dialogo = new Dialog(MainActivity.this);
        dialogo.setContentView(vista);
        ...
        dialogo.show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Pikachu.class);
                startActivity(intent);
                dialogo.dismiss();
            }
        }, 3000);
    }
});
```
Esto es una variación interesante del patrón "toast con Dialog" (ver [04-toast-personalizado](../02-conceptos/04-toast-personalizado.md)): en vez de que el `Handler.postDelayed` simplemente cierre el diálogo, **primero lanza la Activity `Pikachu` y luego cierra el diálogo** — es decir, el "toast" hace de pantalla de carga/transición de 3 segundos antes de entrar de verdad a la animación.

> Nota curiosa: se infla `R.layout.activity_pikachu` (el layout completo de la Activity `Pikachu`, con su `ImageView` central) dentro de un `Dialog`, reutilizándolo como vista de aviso — funciona porque un layout XML no "sabe" si lo está usando una Activity o un Dialog, solo es una plantilla de vistas.

> **Relación con `EjercicioAnimaciones.pdf`**: este segundo enunciado del curso pide *"Crea una animación de carga al darle al botón de opciones que dure 3 segundos. Al terminar la animación se cargará una nueva activity vacía"*. Los 3000ms de este botón (`btnPikachu`, que corresponde visualmente a la celda "opciones" del menú) encajan exactamente con ese enunciado — la diferencia es que, en el estado actual del código, al terminar los 3 segundos se navega a `Pikachu` (que arranca su propia animación de ~20s) en vez de a una Activity vacía como pedía el enunciado. Si se quisiera ajustar estrictamente al enunciado, ese `Intent` debería apuntar a una Activity vacía (por ejemplo, una nueva o la propia `MainActivity2`) en vez de a `Pikachu.class`.

### Botón "Nuevo" — lambda en vez de clase anónima

```java
ImageButton btnNuevo = findViewById(R.id.btnNuevo);
btnNuevo.setOnClickListener(v -> {
    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
    startActivity(intent);
});
```
Aquí, a diferencia de los demás botones (que usan `new View.OnClickListener() { ... }`, una **clase anónima**: una clase sin nombre, escrita "al vuelo" justo donde se usa, en vez de en su propio fichero), se usa una **expresión lambda** (`v -> { ... }`). Ambas formas son equivalentes — `OnClickListener` es una **interfaz** (ver [00-programacion-basica](../02-conceptos/00-programacion-basica.md) §7) con un único método por implementar (`onClick`), así que Java permite sustituir toda la clase anónima por una lambda mucho más corta. Ver esta misma dualidad de estilos en la mayoría de tus proyectos: es habitual mezclar ambos estilos dentro de un mismo fichero porque no cambia el comportamiento, solo la verbosidad.

### Botón "Toast" — el toast personalizado más elaborado del curso

```java
View vista = getLayoutInflater().inflate(R.layout.nueva_main, null);
ImageView ivToast = vista.findViewById(R.id.ivToast);
ivToast.setImageResource(R.drawable.users);
TextView tvToast = vista.findViewById(R.id.tvToast);
tvToast.setText("USUARIO:");
EditText txtNombre = vista.findViewById(R.id.txtNombre);
TextView tvToast2 = vista.findViewById(R.id.tvPassword);
tvToast2.setText("PASSWORD:");
EditText txtPassword = vista.findViewById(R.id.txtPassword);
```
Aquí el "toast" ya no es solo informativo — el layout `nueva_main.xml` incluye campos de texto (`EditText`) para usuario y contraseña, mostrados dentro del `Dialog`. Los cuatro `findViewById` de este bloque usan cuatro ids **distintos** del layout (`tvToast`, `txtNombre`, `tvPassword`, `txtPassword`) — a pesar de que los nombres se parecen bastante entre sí (`txtNombre`/`txtPassword`, `tvToast`/`tvToast2`/`tvPassword`), no hay ningún id duplicado ni ninguna confusión real: cada variable Java apunta a su propio campo del XML. Es solo un ejemplo de nomenclatura que, por lo parecida, conviene leer con calma para no perderse entre tantos `tv`/`txt` seguidos.

```java
ivToast.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialogo.dismiss();
    }
});
```
Este es el "cierre manual" mencionado en [04-toast-personalizado](../02-conceptos/04-toast-personalizado.md) §4: en vez de un temporizador, el propio icono actúa de botón para cerrar el diálogo cuando el usuario lo desee.

## `Caballo.java` / `ProgresoCaballo.java` y `Pikachu.java` / `AnimacionPikachu.java`

Ambos pares de clases siguen **exactamente** el mismo patrón, ya explicado a fondo en [05-asynctask-e-hilos](../02-conceptos/05-asynctask-e-hilos.md) §3 (con `Caballo`/`ProgresoCaballo` como ejemplo principal) y en [06-animaciones-frame-by-frame](../02-conceptos/06-animaciones-frame-by-frame.md):
1. La Activity carga un `TypedArray` de imágenes (`R.array.imagenes` para el caballo, `R.array.pikachu` para Pikachu).
2. Expone sus vistas (`ImageView`, y en el caso del caballo también `ProgressBar`/`TextView`) mediante **getters** (métodos que solo devuelven el valor de un atributo — ver [00-programacion-basica](../02-conceptos/00-programacion-basica.md) §9).
3. Lanza una `AsyncTask` que, en un bucle de 100 pasos con `Thread.sleep(200)`, va alternando el fotograma mostrado.
4. Al terminar el bucle, la Activity se cierra sola (`activity.finish()`).

La única diferencia entre `Pikachu`/`AnimacionPikachu` y `Caballo`/`ProgresoCaballo` es que la versión de Pikachu **no tiene barra de progreso ni texto de porcentaje** — solo cambia la imagen, sin mostrar avance numérico.

## `MainActivity2.java`

```java
public class MainActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ...
    }
}
```
Una pantalla vacía, destino del botón "Nuevo" — sin contenido funcional más allá del arranque estándar (igual que [anclados](anclados.md)).

## Los drawables y estilos reales (antes sin documentar)

Este proyecto usa a fondo las técnicas explicadas en [../conceptos/15-drawables-formas-y-selectores](../02-conceptos/15-drawables-formas-y-selectores.md) — merece la pena revisarlas ahí en detalle, aquí solo el resumen de qué archivo hace qué:

- **`res/drawable/fondo.xml`** — un `<shape>` con `<gradient>` (color de fondo de toda la app, `#AAC1E2`).
- **`res/drawable/cabecera.xml`** — un `<shape>` transparente con `<stroke>` blanco y esquinas redondeadas (`corners radius="48dp"`), usado como fondo de la caja de cabecera del menú.
- **`res/drawable/boton_pulsado_nuevo.xml`**, `boton_pulsado_calendar.xml`, `boton_pulsado_users.xml`, `boton_pulsado_save.xml` — cada uno un `<selector>` que cambia de imagen (normal ↔ pulsada) según `android:state_pressed`.
- **`res/values/themes.xml`** — define los estilos `titulo`, `titulo.subTitulo` (hereda de `titulo`, solo cambia el tamaño) y `titulo.sombreado` (hereda de `titulo`, añade la sombra de texto pedida en el enunciado), además de `android:windowFullscreen="true"` en `Theme.EjercicioDiseno` para ocultar la barra de estado.
- **`res/values/dimens.xml`** — centraliza `tamanoTitulo` (20sp) y `tamanoSubTitulo` (10sp), referenciados desde los estilos de arriba.

## Relación con otros proyectos

- El patrón `AsyncTask` de animación: idéntico al de [Diseobesos](Diseobesos.md).
- El patrón "toast con Dialog": la versión más elaborada del curso está aquí (con `EditText` incluidos), comparado con la versión simple de [Diseobesos](Diseobesos.md).
- El diseño de menú con pesos: es el único proyecto que usa `LinearLayout` + pesos en vez de `ConstraintLayout` para una pantalla completa — ver [02-diseno-basado-en-pesos](../02-conceptos/02-diseno-basado-en-pesos.md).
