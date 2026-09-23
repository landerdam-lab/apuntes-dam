---
tags:
  - android
  - reutilizable
aliases:
  - Utilidades
  - Glide
  - Picasso
  - Handler postDelayed
---

# 09 — Utilidades

## Cargar imágenes con Glide ✅ Reutilizable
**Aparece en:** [AdapterDam2](../04-proyectos-profesor/AdapterDam2.md), [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md). Gradle: `implementation("com.github.bumptech.glide:glide:4.16.0")`.
```java
Glide.with(this)                              // Activity, Fragment o Context
     .load(fuente)                            // R.drawable.x · File · Uri · "https://…"
     .placeholder(R.drawable.cargando)        // mientras descarga
     .error(R.drawable.sin_imagen)            // si falla
     .centerCrop()
     .into(imageView);
```

## Cargar imágenes con Picasso ✅ Reutilizable
**Aparece en:** [EjercicioAdaptadoresFinal](../04-proyectos-profesor/EjercicioAdaptadoresFinal.md), [EjercicioPokemon](../04-proyectos-profesor/EjercicioPokemon.md). Gradle: `implementation("com.squareup.picasso:picasso:2.8")`.
```java
Picasso.get().load(url).placeholder(R.drawable.cargando).error(R.drawable.sin_imagen)
       .fit().centerCrop().into(imageView);
```
> Con URL, las dos librerías necesitan `<uses-permission android:name="android.permission.INTERNET"/>`.

## Ejecutar algo pasado un tiempo (`Handler.postDelayed`) ✅ Reutilizable
**Aparece en:** PDF de Toast personalizado (comentado en DisenyoPesos).
```java
new Handler(Looper.getMainLooper()).postDelayed(() -> {
    startActivity(new Intent(this, SiguienteActivity.class));   // p. ej. tras una pantalla de carga de 3 s
    finish();
}, 3000);
```

## Tarea en segundo plano con progreso (alternativa moderna a `AsyncTask`) ✅ Reutilizable
**Sustituye a:** `ProgressAndando` y `AsyntaskDamCaballo` ([DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md), [EjercicioAsyncTaskAlumnos](../04-proyectos-profesor/EjercicioAsyncTaskAlumnos.md)).
```java
private final ExecutorService ejecutor = Executors.newSingleThreadExecutor();
private final Handler principal = new Handler(Looper.getMainLooper());
private volatile boolean cancelado = false;

private void animar(TypedArray imagenes) {
    ejecutor.execute(() -> {                                   // ── hilo secundario ──
        for (int i = 0; i <= 100 && !cancelado; i++) {
            final int progreso = i;
            final int foto = i % imagenes.length();            // recorre TODAS las imágenes
            try { Thread.sleep(100); } catch (InterruptedException e) { return; }
            principal.post(() -> {                             // ── hilo principal ──
                barra.setProgress(progreso);
                tvPorcentaje.setText(progreso + "%");
                ivFoto.setImageResource(imagenes.getResourceId(foto, -1));
            });
        }
        principal.post(() -> {
            if (!cancelado) {
                Toast.makeText(this, "Tarea finalizada", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    });
}

@Override
protected void onDestroy() {
    super.onDestroy();
    cancelado = true;          // si el usuario sale antes, el bucle se detiene
    ejecutor.shutdownNow();
}
```

## Detectar doble toque en una lista (corregido) ✅ Reutilizable
**Original con bug:** `MainActivity` de [EjercicioPokemon](../04-proyectos-profesor/EjercicioPokemon.md).
```java
private static final long DOBLE_TOQUE_MS = 300;
private long ultimoToque = 0;          // long, NO float
private int ultimaPosicion = -1;

lista.setOnItemClickListener((p, v, pos, id) -> {
    long ahora = System.currentTimeMillis();
    if (pos == ultimaPosicion && ahora - ultimoToque < DOBLE_TOQUE_MS) {   // ahora - antes (positivo)
        onDobleToque(pos);
        ultimoToque = 0;                                  // evita que un triple toque cuente dos veces
    } else {
        ultimoToque = ahora;
    }
    ultimaPosicion = pos;
});
```

## Nombre de archivo con fecha y hora ✅ Reutilizable
**Aparece en:** `CameraActivity`.
```java
String nombre = "foto_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";
```

## Recorrer un array de drawables (`TypedArray`) sin fugas ✅ Reutilizable
**Aparece en:** DisenyoPesos, AdapterDam2.
```java
TypedArray ta = getResources().obtainTypedArray(R.array.imagenes);
int[] ids = new int[ta.length()];
for (int i = 0; i < ta.length(); i++) ids[i] = ta.getResourceId(i, 0);
ta.recycle();                          // liberar SIEMPRE
// a partir de aquí usa el int[] ids
```

## Log para depurar ✅ Reutilizable
```java
private static final String TAG = "MiApp";
Log.d(TAG, "valor = " + valor);        // d=debug, i=info, w=warning, e=error
```
En **Logcat**, filtra por `tag:MiApp`.

## Comprobar la versión de Android ✅ Reutilizable
```java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { /* Android 10+ */ }
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { /* Android 13+ */ }
```

## `dp` a píxeles (para tamaños desde código) ✅ Reutilizable
```java
int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
```
