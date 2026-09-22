---
tags:
  - android
  - proyecto
  - estado/completo
---

# Proyecto: `Diseobesos` ("Diseño besos" — ejercicio de AsyncTask + Toast personalizado)

## Qué es

Un proyecto pequeño centrado en dos técnicas: una animación frame-by-frame controlada por `AsyncTask` (ver [05-asynctask-e-hilos](../conceptos/05-asynctask-e-hilos.md) y [06-animaciones-frame-by-frame](../conceptos/06-animaciones-frame-by-frame.md)), y un "Toast" personalizado con `Dialog` (ver [04-toast-personalizado](../conceptos/04-toast-personalizado.md)).

## Estructura

```
app/src/main/java/com/example/diseobesos/
├── MainActivity.java          → pantalla de menú con 2 botones
├── EjemploAsynctask.java      → pantalla de animación con barra de progreso
└── ProgressAndando.java       → la AsyncTask que anima la imagen y avanza la barra
app/src/main/res/layout/
├── activity_main.xml
├── activity_ejemplo_asynctask.xml
└── toast_per.xml              → layout del "toast" personalizado
```

## `MainActivity.java` fragmento a fragmento

```java
ImageButton btnAsyncTask = findViewById(R.id.asyntask);
btnAsyncTask.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), EjemploAsynctask.class);
        startActivity(intent);
    }
});
```
Botón 1: navega a `EjemploAsynctask` con un `Intent` explícito simple, sin extras y sin animación de transición (ver [01-fundamentos-bundle-intent-ciclo-vida](../conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) §3).

```java
ImageButton btnToast = findViewById(R.id.btnToast);
btnToast.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        View vista = getLayoutInflater().inflate(R.layout.toast_per, null);
        ImageView ivToast = vista.findViewById(R.id.ivToast);
        ivToast.setImageResource(R.drawable.andando1);
        TextView tvToast = vista.findViewById(R.id.tvToast);
        tvToast.setText("DAM ---- 2");

        final Dialog dialogo = new Dialog(MainActivity.this);
        dialogo.setContentView(vista);
        if (dialogo.getWindow() != null) {
            dialogo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        dialogo.show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogo.dismiss();
            }
        }, 2000);
    }
});
```
Botón 2: el patrón completo de "toast personalizado con Dialog", explicado línea a línea en [04-toast-personalizado](../conceptos/04-toast-personalizado.md) §3. Resumen rápido: infla `toast_per.xml` suelto → lo rellena → lo mete en un `Dialog` sin fondo → lo muestra → lo cierra automáticamente a los 2 segundos con un `Handler`.

## `EjemploAsynctask.java` fragmento a fragmento

```java
public class EjemploAsynctask extends AppCompatActivity {

    private TypedArray imagenes = null;
    private ProgressBar barra = null;
    private ImageView imageCentral = null;
    private TextView texto = null;

    protected void onCreate(Bundle savedInstanceState) {
        ...
        imagenes = getResources().obtainTypedArray(R.array.imagenes);
        barra = findViewById(R.id.pbAnimacion);
        imageCentral = findViewById(R.id.ivAnimacion);
        texto = findViewById(R.id.tvAnimacion);

        barra.setMax(100);
        barra.setProgress(0);
        barra.setBackgroundColor(Color.GRAY);

        ProgressAndando hilo = new ProgressAndando(this);
        hilo.execute();
    }

    public void finalizar(){ this.finish(); }   // método auxiliar, no se llega a usar desde ProgressAndando

    // getters/setters de imagenes, imageCentral, texto, barra...
}
```
- `obtainTypedArray(R.array.imagenes)` carga el array de recursos de imágenes declarado en XML (ver [06-animaciones-frame-by-frame](../conceptos/06-animaciones-frame-by-frame.md) §2).
- `barra.setMax(100)` — la `ProgressBar` va de 0 a 100 (se interpretará como porcentaje).
- Los getters/setters (métodos `public` para leer/modificar un atributo `private` desde fuera de la clase, ver [00-programacion-basica](../conceptos/00-programacion-basica.md) §9) existen para que `ProgressAndando` (una clase externa) pueda leer/actualizar estas vistas — ver el porqué en [05-asynctask-e-hilos](../conceptos/05-asynctask-e-hilos.md) §3.1.
- El método `finalizar()` está definido pero no se usa — `ProgressAndando.onPostExecute` llama directamente a `activity.finish()` en vez de a `activity.finalizar()`; son equivalentes en efecto, pero `finalizar()` queda como código muerto (sin usar) en este fichero.

## `ProgressAndando.java` — la `AsyncTask`

```java
public class ProgressAndando extends AsyncTask<Void,Integer,Void> {
    private EjemploAsynctask activity;
    public ProgressAndando(EjemploAsynctask activity) { this.activity = activity; }

    @Override
    protected Void doInBackground(Void... voids) {
        int foto = 0;
        for (int i = 0; i < 100; i++) {
            foto++;
            if (foto >= 3) foto = 0;
            Thread.sleep(200);
            publishProgress(foto, i);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        activity.finish();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        activity.getImageCentral().setImageResource(activity.getImagenes().getResourceId(values[0], -1));
        activity.getBarra().setProgress(values[1]);
        activity.getTexto().setText(values[1]+"%");
    }
}
```
Este es exactamente el patrón analizado en profundidad en [05-asynctask-e-hilos](../conceptos/05-asynctask-e-hilos.md) §3 — idéntico (línea a línea) al usado en `EjercicioDiseno` (`ProgresoCaballo`). Confirma que es un patrón "plantilla" que el curso repite deliberadamente en varios ejercicios para consolidarlo.

## `toast_per.xml`

```xml
<LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content"
    android:orientation="vertical" android:padding="16dp">
    <ImageView android:id="@+id/ivToast" android:layout_width="200dp" android:layout_height="200dp"/>
    <TextView  android:id="@+id/tvToast" android:textSize="40sp" android:gravity="center"/>
</LinearLayout>
```
Un layout muy simple: icono arriba, texto centrado debajo. Ver [04-toast-personalizado](../conceptos/04-toast-personalizado.md) para el porqué de usar `wrap_content` aquí (el "toast" debe medir justo lo que ocupa su contenido, no toda la pantalla).

## Relación con otros proyectos

- El patrón `AsyncTask` de animación es prácticamente idéntico al de [EjercicioDiseno](EjercicioDiseno.md) (`ProgresoCaballo`/`AnimacionPikachu`) — mismo código, distinto nombre de clase.
- El patrón de "Toast personalizado con Dialog" también se repite en [EjercicioDiseno](EjercicioDiseno.md) (`MainActivity`, botón "toast" y botón "Pikachu"), con una variante donde el diálogo se cierra al tocarlo en vez de por temporizador.
