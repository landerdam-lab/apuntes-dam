---
tags:
  - android
  - concepto
---

# Toast personalizado (y por qué usamos un `Dialog` en su lugar)

## 1. El `Toast` normal de Android

```java
Toast.makeText(getApplicationContext(), equipo.getNombre(), Toast.LENGTH_SHORT).show();
```

Un `Toast` es ese mensajito flotante que aparece unos segundos y desaparece solo, sin robarle el foco a la pantalla. `Toast.makeText(contexto, texto, duración)` lo crea, y **hay que llamar a `.show()`** — es un error habitual olvidarlo (de hecho aparece así, sin `.show()`, en `jugadores_Activity.java` y `EjemploAsynctask` de tus proyectos: `Toast.makeText(...)` sin `.show()` **no muestra nada**, es una llamada sin efecto).

`Toast.LENGTH_SHORT` / `Toast.LENGTH_LONG` son las dos únicas duraciones que admite (no se puede poner un número arbitrario de milisegundos con la API pública).

## 2. Por qué en tus proyectos el "toast personalizado" no es un `Toast`

Un `Toast` estándar solo admite texto simple (o, en versiones antiguas, una vista personalizada vía `toast.setView(...)`, **método eliminado/deprecado en Android moderno** por motivos de seguridad — apps maliciosas lo usaban para simular ventanas del sistema). Por eso, para conseguir un "toast" con imagen + texto y estilo propio, tus proyectos usan un **`Dialog` sin fondo, que se autocierra con un `Handler`** — visualmente parecido a un Toast personalizado, pero técnicamente es otra cosa.

## 3. Ejemplo real completo: `Diseobesos/MainActivity.java`

### 3.1. El layout del "toast" (`toast_per.xml`)

```xml
<LinearLayout
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="16dp">

    <ImageView android:id="@+id/ivToast" android:layout_width="200dp" android:layout_height="200dp"/>
    <TextView  android:id="@+id/tvToast" android:textSize="40sp" android:gravity="center"/>

</LinearLayout>
```
Un layout normal y corriente — nada especial en el XML. La "magia" está en cómo se muestra.

### 3.2. El código, paso a paso

```java
ImageButton btnToast = findViewById(R.id.btnToast);
btnToast.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        // 1. "Inflamos" el layout del toast SIN añadirlo a ninguna pantalla todavía
        View vista = getLayoutInflater().inflate(R.layout.toast_per, null);

        // 2. Rellenamos sus vistas como si fuera un layout normal
        ImageView ivToast = vista.findViewById(R.id.ivToast);
        ivToast.setImageResource(R.drawable.andando1);
        TextView tvToast = vista.findViewById(R.id.tvToast);
        tvToast.setText("DAM ---- 2");

        // 3. Creamos un Dialog y le metemos ESA vista como contenido
        final Dialog dialogo = new Dialog(MainActivity.this);
        dialogo.setContentView(vista);

        // 4. Le quitamos el fondo blanco/rectángulo típico de los diálogos
        if (dialogo.getWindow() != null) {
            dialogo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        // 5. Lo mostramos
        dialogo.show();

        // 6. Programamos su cierre automático pasados 2 segundos
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogo.dismiss();
            }
        }, 2000);
    }
});
```

### 3.3. Por qué cada paso

- **`getLayoutInflater().inflate(R.layout.toast_per, null)`** — el segundo parámetro (`null`) es el `ViewGroup` padre al que se debería adjuntar la vista inflada; al pasar `null`, la vista queda "suelta" en memoria, sin pertenecer todavía a ninguna jerarquía de pantalla. Esto es exactamente lo mismo que hace `LayoutInflater.inflate(...)` dentro de un adaptador (ver [07-adaptadores](07-adaptadores.md)) — es el mismo mecanismo, aplicado aquí a un `Dialog` en vez de a una fila de lista.

- **`Dialog` en vez de una nueva `Activity`** — un `Dialog` es una ventana flotante *encima* de la Activity actual, no una pantalla nueva; por eso es ideal para una notificación temporal que no debe interrumpir el flujo de navegación.

- **`setBackgroundDrawableResource(android.R.color.transparent)`** — por defecto, `Dialog` dibuja un fondo blanco/gris con esquinas y sombra (el estilo "cuadro de diálogo del sistema"). Poniendo el fondo de la *ventana* como transparente, solo se ve tu `LinearLayout` (imagen + texto), sin el marco del diálogo — así consigues que parezca un Toast a medida en vez de una alerta del sistema.

- **`Handler(Looper.getMainLooper()).postDelayed(runnable, 2000)`** — esto es un "temporizador": ejecuta el código de `runnable.run()` dentro de **2000 milisegundos (2 segundos)**, en el hilo principal (`Looper.getMainLooper()` = el hilo de UI). Es la manera estándar en Android de decir "dentro de X tiempo, haz esto" sin bloquear la interfaz mientras tanto (ver el porqué del hilo principal en [05-asynctask-e-hilos](05-asynctask-e-hilos.md)).

## 4. Variante con cierre manual (`EjercicioDiseno/MainActivity.java`)

En otro de tus proyectos, en vez de un `Handler` con temporizador, el diálogo personalizado se cierra al **tocar la propia imagen**:
```java
ivToast.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialogo.dismiss();
    }
});
```
Mismo patrón (`Dialog` + layout inflado a mano + fondo transparente), pero el cierre depende de una acción del usuario en vez de un tiempo fijo. Ambas variantes son válidas — se elige una u otra según si quieres que el aviso desaparezca solo o que el usuario decida cuándo cerrarlo.

## 4.1. Dos detalles de Java que explican por qué el código está escrito así

El material del curso señala explícitamente estos dos puntos, que conviene entender porque no son "casualidad" ni estilo — son **requisitos del lenguaje Java**:

1. **`MainActivity.this` en vez de `this`, dentro de una clase anónima**:
   ```java
   final Dialog dialogo = new Dialog(MainActivity.this);
   ```
   Dentro de `new View.OnClickListener() { public void onClick(View v) { ... } }`, la palabra `this` se refiere **a esa clase anónima** (la instancia del propio `OnClickListener`), no a la Activity — porque estás dentro de la definición de una clase nueva (aunque no tenga nombre), y `this` siempre apunta al objeto de la clase en la que estás escribiendo el código en ese momento. Para referirte explícitamente a la Activity exterior desde dentro de una clase anidada, Java usa la sintaxis **`NombreDeLaClaseExterior.this`** — de ahí `MainActivity.this`.

2. **`final Dialog dialogo`**:
   ```java
   final Dialog dialogo = new Dialog(MainActivity.this);
   ...
   new Handler(...).postDelayed(new Runnable() {
       public void run() {
           dialogo.dismiss();   // usando "dialogo" desde OTRA clase anónima
       }
   }, 2000);
   ```
   La variable `dialogo` se declara **fuera** del `Runnable`, pero se usa **dentro** de él — y el `Runnable` es, otra vez, otra clase anónima distinta. En Java, toda variable local que una clase anónima (o una lambda) "captura" de su entorno exterior debe ser `final`, o al menos **"efectivamente final"** (que nunca se reasigne después de su primera asignación, aunque no lleve la palabra `final` escrita — regla añadida en versiones más modernas de Java). La razón técnica es que la clase anónima puede seguir viva y ejecutarse **más tarde**, incluso después de que el método donde se declaró haya terminado — Java necesita garantizar que el valor de esa variable no pueda cambiar por sorpresa entre que se declaró y el momento (futuro, impredecible) en que la clase anónima finalmente la use.

## 5. Resumen del patrón "toast personalizado con Dialog"

1. Diseña un layout XML normal con lo que quieras mostrar.
2. Ínflalo con `getLayoutInflater().inflate(R.layout.mi_layout, null)`.
3. Rellena sus vistas (`findViewById` sobre la vista inflada, no sobre la Activity).
4. Mételo en un `new Dialog(context)` con `setContentView(vista)`.
5. Quítale el fondo (`setBackgroundDrawableResource(android.R.color.transparent)`) si quieres que se vea "flotante" y no como un cuadro de diálogo típico.
6. Muéstralo (`dialogo.show()`) y ciérralo cuando corresponda (`dialogo.dismiss()`), ya sea por temporizador (`Handler.postDelayed`) o por interacción del usuario.

## 🏋️ Practica esto

- [Nivel 1 — ejercicio 1.3](../ejercicios/01-nivel-1-java-y-primera-app.md) (`Toast` con el texto de un `EditText`)
