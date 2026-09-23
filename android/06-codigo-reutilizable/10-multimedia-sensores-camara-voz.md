---
tags:
  - android
  - reutilizable
  - tema/sensores
  - tema/camara
  - tema/voz
aliases:
  - Multimedia reutilizable
---

# 10 — Multimedia: sensores, cámara, galería y voz

> Teoría: [21 — Sensores](../02-conceptos/21-sensores.md) · [22 — Cámara y almacenamiento](../02-conceptos/22-camara-y-almacenamiento.md) · [23 — Voz e intents implícitos](../02-conceptos/23-voz-e-intents-implicitos.md) · Proyecto: [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md)

## Sensor con registro según el ciclo de vida ✅ Reutilizable
```java
public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private Sensor sensor;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_sensor);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);         // ← cambia el tipo
        if (sensor == null) Toast.makeText(this, "Sensor no disponible", Toast.LENGTH_SHORT).show();
    }
    @Override protected void onResume() {
        super.onResume();
        if (sensor != null) sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override protected void onPause() { super.onPause(); sm.unregisterListener(this); }

    @Override public void onSensorChanged(SensorEvent e) { /* e.values[0], [1], [2] */ }
    @Override public void onAccuracyChanged(Sensor s, int a) { }
}
```
Manifest: `<uses-feature android:name="android.hardware.sensor.proximity" android:required="false"/>`.

## Sacar una foto (carpeta privada, sin permisos) ✅ Reutilizable
**Necesita:** el `<provider>` en el manifest, `res/xml/file_paths.xml` y Glide. Todo copiado en [22 — Cámara](../02-conceptos/22-camara-y-almacenamiento.md).
```java
private File fotoActual;
private final ActivityResultLauncher<Uri> camara = registerForActivityResult(
        new ActivityResultContracts.TakePicture(), ok -> {
            if (Boolean.TRUE.equals(ok)) Glide.with(this).load(fotoActual).into(ivFoto);
        });

private void sacarFoto() {
    String n = "foto_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";
    fotoActual = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), n);
    camara.launch(FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", fotoActual));
}
```

## Copiar una foto a la galería del sistema (`MediaStore`) ✅ Reutilizable
Versión del proyecto con las correcciones del PDF: `RELATIVE_PATH`, sin cerrar la app ante un error y **fuera del hilo principal**.
```java
private void guardarEnGaleria(File archivo, Runnable alTerminar) {
    AppExecutors.getInstance().diskIO().execute(() -> {             // la copia puede tardar
        ContentValues v = new ContentValues();
        v.put(MediaStore.Images.Media.DISPLAY_NAME, archivo.getName());
        v.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            v.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/MiApp");
            v.put(MediaStore.Images.Media.IS_PENDING, 1);
        }
        ContentResolver cr = getContentResolver();
        Uri destino = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, v);
        if (destino == null) return;
        try (InputStream in = new FileInputStream(archivo); OutputStream out = cr.openOutputStream(destino)) {
            byte[] buf = new byte[8192]; int n;
            while ((n = in.read(buf)) > 0) out.write(buf, 0, n);
        } catch (IOException e) {
            Log.e("Galeria", "No se pudo copiar", e);
            cr.delete(destino, null, null);                             // no dejar una entrada vacía
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues fin = new ContentValues();
            fin.put(MediaStore.Images.Media.IS_PENDING, 0);
            cr.update(destino, fin, null, null);
        }
        AppExecutors.getInstance().mainThread().execute(alTerminar);    // p. ej. recargar la galería
    });
}
```
Manifest (solo para Android ≤ 9): `<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="28"/>`.

## Elegir una foto de la galería (no está en los proyectos) ✅ Reutilizable
```java
private final ActivityResultLauncher<PickVisualMediaRequest> elegir = registerForActivityResult(
        new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) Glide.with(this).load(uri).into(ivFoto);
        });
// Lanzar (no pide permisos):
elegir.launch(new PickVisualMediaRequest.Builder()
        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
```

## Dictado por voz a un `EditText` ✅ Reutilizable
```java
private final ActivityResultLauncher<Intent> voz = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), r -> {
            if (r.getResultCode() == RESULT_OK && r.getData() != null) {
                ArrayList<String> t = r.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (t != null && !t.isEmpty()) etTexto.setText(t.get(0));
            }
        });

private void dictar() {
    Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            .putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
            .putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora…");
    try { voz.launch(i); }
    catch (ActivityNotFoundException e) { Toast.makeText(this, "Voz no disponible", Toast.LENGTH_SHORT).show(); }
}
```

## Buscar en Google / abrir una URL ✅ Reutilizable
```java
private void buscarEnGoogle(String texto) {
    try {
        String q = URLEncoder.encode(texto, "UTF-8");
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + q)));
    } catch (UnsupportedEncodingException | ActivityNotFoundException e) {
        Toast.makeText(this, "No se puede abrir el navegador", Toast.LENGTH_SHORT).show();
    }
}
```
Otros intents implícitos útiles (teléfono, email, compartir, mapa): [23](../02-conceptos/23-voz-e-intents-implicitos.md).
