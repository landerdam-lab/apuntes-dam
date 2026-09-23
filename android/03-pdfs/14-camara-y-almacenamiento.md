---
tags:
  - android
  - pdf
  - bloque/2
  - tema/camara
aliases:
  - 3-CamaraYAlmacenamiento
---

# PDF 3 (bloque 2) — Cámara y almacenamiento

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/3-CamaraYAlmacenamiento.pdf` · **25 páginas** · **Bloque 2**
> **Proyecto:** [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) → `CameraActivity`, `FotosGridViewAdapter`, `item_foto.xml`, `res/xml/file_paths.xml`, `<provider>` del manifest
> **Concepto:** [22 — Cámara y almacenamiento](../02-conceptos/22-camara-y-almacenamiento.md) · **Reutilizable:** [Multimedia](../06-codigo-reutilizable/10-multimedia-sensores-camara-voz.md)

## Qué explica (tres versiones que se van ampliando)

**Versión 1 — sacar una foto y verla**
1. Layout `activity_camera.xml`: botón *Sacar foto*, `ImageView` (peso 1) y `GridView` de 3 columnas (peso 1).
2. Botón **Cámara** en `CentralActivity`.
3. Añadir **Glide** (`implementation("com.github.bumptech.glide:glide:4.16.0")`).
4. **`FileProvider`**: crear `res/xml/file_paths.xml` (`<external-files-path name="fotos" path="Pictures/"/>`) y declarar `<provider>` en el manifest con `authorities="${applicationId}.fileprovider"`.
5. **Activity Result API:** en `onCreate`, `registerForActivityResult(new ActivityResultContracts.TakePicture(), exito -> …)`. Debe registrarse en `onCreate`, **no** dentro del clic. Sustituye a `startActivityForResult`/`onActivityResult`, ya obsoletos.
6. `abrirCamara()`: archivo `foto_yyyyMMdd_HHmmss.jpg` en `getExternalFilesDir(DIRECTORY_PICTURES)` (**no requiere permisos**), `FileProvider.getUriForFile(...)` y `launch(uri)`.
7. Explicación: la cámara es **otra app** y no puede recibir una ruta privada; el `FileProvider` le da una `content://` con permiso temporal. Glide reescala la foto, que a resolución completa saturaría la memoria.

**Versión 2 — galería propia**
8. `item_foto.xml` y `FotosGridViewAdapter` (`BaseAdapter` de `List<File>` con Glide).
9. `cargarImagenes()`: listar `carpeta.listFiles()` y `notifyDataSetChanged()`. Las fotos se ven en *Device Explorer* → `storage/emulated/0/Android/data/<paquete>/files/Pictures`. **Se borran al desinstalar y no salen en la galería del sistema.**

**Versión 3 — guardar en la galería del sistema (`MediaStore`)**
10. Manifest: `WRITE_EXTERNAL_STORAGE` con `maxSdkVersion="28"`.
11. El adaptador pasa de `File` a **`Uri`**.
12. Permiso en tiempo de ejecución **solo en Android ≤ 9**: `RequestPermission` + `ContextCompat.checkSelfPermission`.
13. `guardarEnGaleria(File)`: `ContentValues` (`DISPLAY_NAME`, `MIME_TYPE` y, en Android 10+, `RELATIVE_PATH = Pictures/EjemploDialog` + `IS_PENDING = 1`) → `getContentResolver().insert(...)` → copiar los bytes → `IS_PENDING = 0`.
14. `cargarImagenes()` consulta el `MediaStore` (`query` con `LIKE 'foto_%'`, orden `DATE_ADDED DESC`) y construye cada `Uri` con `ContentUris.withAppendedId`.
15. Por qué no hacen falta permisos en Android 10+: el **scoped storage** permite a cada app añadir sus propias imágenes al `MediaStore`.

## Diferencias con el proyecto

| PDF | Proyecto |
|---|---|
| `RELATIVE_PATH = Pictures/EjemploDialog` | **No lo pone** (las fotos van a la carpeta de imágenes por defecto) |
| `catch (Exception e) { e.printStackTrace(); }` | `catch (IOException e) { throw new RuntimeException(e); }` (un fallo cierra la app) |
| Paquete `com.example.ejemplodialog`, adaptador en `adapter/` | `com.example.ejemplodialogopersonalizado`, adaptador en `adaptadores/` |
| `R.id.btncamara` | `R.id.btnCamara` |

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/3-CamaraYAlmacenamiento.pdf)
