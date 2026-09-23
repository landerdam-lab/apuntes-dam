---
tags:
  - android
  - concepto
  - tema/camara
aliases:
  - Cámara
  - FileProvider
  - MediaStore
  - Activity Result API
---

# 22 — Cámara, almacenamiento y Activity Result API

> PDF: [3 — Cámara y almacenamiento](../03-pdfs/14-camara-y-almacenamiento.md) · Proyecto: [EjemploDialogoPersonalizado → CameraActivity](../04-proyectos-profesor/EjemploDialogoPersonalizado.md)

## Concepto: Activity Result API 📌 Importante

### Qué es
La forma **actual** de abrir otra pantalla (tuya o de otra app) y **recibir una respuesta**: una foto, un texto dictado, un "sí" a un permiso… Sustituye a `startActivityForResult` + `onActivityResult`, que están obsoletos.

### Cómo funciona (dos pasos)
1. **Registrar** un *launcher* en `onCreate` (o como atributo), indicando un **contrato** (qué operación) y un **callback** (qué hacer con el resultado).
2. **Lanzar** con `launcher.launch(entrada)` cuando el usuario pulse algo.

| Contrato (`ActivityResultContracts.`) | Entrada | Resultado | Dónde |
|---|---|---|---|
| `TakePicture()` | `Uri` donde guardar la foto | `Boolean` (éxito) | `CameraActivity` |
| `RequestPermission()` | `String` permiso | `Boolean` (concedido) | `CameraActivity` |
| `StartActivityForResult()` | `Intent` cualquiera | `ActivityResult` (código + `Intent` de datos) | `VozActivity` |
| `GetContent()` | tipo MIME (`"image/*"`) | `Uri` elegida | (galería, no está en los proyectos) |
| `RequestMultiplePermissions()` | `String[]` | `Map<String, Boolean>` | — |

> [!warning] ⚠️ Cuidado: registrar SIEMPRE en `onCreate`
> `registerForActivityResult` dentro de un `onClick` lanza `IllegalStateException: LifecycleOwner … is attempting to register while current state is RESUMED`. El sistema necesita conocer el launcher **antes** de que la pantalla esté activa, por si la Activity se destruye mientras la cámara está abierta.

## Concepto: dónde se guardan los archivos 📌 Importante

| Lugar | Cómo se obtiene | ¿Permisos? | ¿Se borra al desinstalar? | ¿Sale en la galería? |
|---|---|---|---|---|
| Interno privado | `getFilesDir()` | No | Sí | No |
| Externo **privado** de la app | `getExternalFilesDir(Environment.DIRECTORY_PICTURES)` → `/Android/data/<paquete>/files/Pictures` | No | Sí | No |
| **Compartido** (`MediaStore`) | `getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, …)` | Android 10+: no para tus propias fotos · Android ≤ 9: `WRITE_EXTERNAL_STORAGE` | No | **Sí** |

El sistema que obliga a esto desde Android 10 se llama **scoped storage**: cada app tiene "su parcela" y solo puede tocar lo común a través del `MediaStore`.

## Concepto: `FileProvider` 📌 Importante

### Qué es
Un "portero" que convierte una **ruta privada** (`/storage/…/files/Pictures/foto.jpg`) en una **URI segura** (`content://com.example.app.fileprovider/fotos/foto.jpg`) y da permiso **temporal** a otra app para usar solo ese archivo.

### Por qué hace falta
La cámara es **otra aplicación**. Android prohíbe pasar rutas `file://` entre apps desde Android 7: se lanzaría `FileUriExposedException`.

### Las tres piezas (las tres son obligatorias)
```xml
<!-- 1) AndroidManifest.xml, dentro de <application> -->
<provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data android:name="android.support.FILE_PROVIDER_PATHS"
               android:resource="@xml/file_paths" />
</provider>
```
```xml
<!-- 2) res/xml/file_paths.xml: qué carpetas se pueden compartir -->
<paths>
    <external-files-path name="fotos" path="Pictures" />
</paths>
```
```java
// 3) Java: la authority debe ser EXACTAMENTE la del manifest
Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", archivo);
```

## Concepto: `MediaStore` y `ContentResolver`

### Qué es
El `MediaStore` es la **base de datos del sistema** con todas las fotos, vídeos y audios compartidos. Se consulta con el `ContentResolver`, que es como un cliente SQL: `insert`, `query`, `update` y `delete` sobre URIs `content://`.

### Ejemplo del proyecto: insertar y consultar
Ver el código comentado completo en [EjemploDialogoPersonalizado §7](../04-proyectos-profesor/EjemploDialogoPersonalizado.md). Resumen:

| Paso | Código | Equivalente SQL |
|---|---|---|
| Crear entrada | `insert(EXTERNAL_CONTENT_URI, valores)` con `DISPLAY_NAME`, `MIME_TYPE`, `IS_PENDING=1` | `INSERT` |
| Escribir bytes | `openOutputStream(destino)` + bucle de copia | — |
| Hacerla visible | `update(destino, {IS_PENDING=0})` | `UPDATE` |
| Listar | `query(uri, {_ID}, "DISPLAY_NAME LIKE ?", {"foto_%"}, "DATE_ADDED DESC")` | `SELECT _id … WHERE … ORDER BY …` |
| URI de cada foto | `ContentUris.withAppendedId(uri, id)` | — |

### Ejemplo reutilizable: sacar una foto a la carpeta privada ✅ Reutilizable
```java
public class FotoActivity extends AppCompatActivity {
    private File fotoActual;
    private ImageView ivFoto;

    // Se registra al crear el objeto (como atributo), es decir, antes de onCreate: cumple la regla
    private final ActivityResultLauncher<Uri> tomarFoto =
            registerForActivityResult(new ActivityResultContracts.TakePicture(), ok -> {
                if (Boolean.TRUE.equals(ok)) Glide.with(this).load(fotoActual).into(ivFoto);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
        ivFoto = findViewById(R.id.ivFoto);
        findViewById(R.id.btnFoto).setOnClickListener(v -> sacarFoto());
    }

    private void sacarFoto() {
        String nombre = "foto_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";
        fotoActual = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), nombre);
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", fotoActual);
        try {
            tomarFoto.launch(uri);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No hay app de cámara", Toast.LENGTH_SHORT).show();
        }
    }
}
```
**Necesita:** el `<provider>` y el `file_paths.xml` de arriba, y Glide en Gradle.

### Errores comunes
| Error | Causa | Solución |
|---|---|---|
| `IllegalArgumentException: Failed to find configured root` | La carpeta no está en `file_paths.xml` | Añadir el `<…-path>` correcto |
| `IllegalArgumentException: Couldn't find meta-data for provider with authority …` | La authority del código ≠ la del manifest | Usar `${applicationId}.fileprovider` y `getPackageName() + ".fileprovider"` |
| `SecurityException` al abrir la cámara | Has declarado `CAMERA` en el manifest y no lo has pedido | Quitarlo (no hace falta con `TakePicture`) o pedirlo con `RequestPermission` |
| `IllegalStateException` al registrar | `registerForActivityResult` en un clic | Registrar en `onCreate` o como atributo |
| `OutOfMemoryError` al mostrar la foto | `setImageURI` con una foto de 12 MP | Glide |
| La foto no aparece en la galería | Solo se guardó en `getExternalFilesDir` | Copiar al `MediaStore` |

## Relacionado
- [24 — Manifest y permisos](24-manifest-y-permisos.md) · [23 — Voz e intents implícitos](23-voz-e-intents-implicitos.md) · [Multimedia reutilizable](../06-codigo-reutilizable/10-multimedia-sensores-camara-voz.md)
