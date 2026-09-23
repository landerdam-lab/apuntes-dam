---
tags:
  - android
  - concepto
  - tema/manifest
aliases:
  - AndroidManifest
  - Permisos
  - uses-feature
---

# 24 — AndroidManifest.xml y permisos

> Aparece en **todos** los proyectos. Casos interesantes: [EjercicioAdaptadoresFinal](../04-proyectos-profesor/EjercicioAdaptadoresFinal.md) (`INTERNET`), [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) (`uses-feature`, `maxSdkVersion`, `provider`), [aaaa](../04-proyectos-profesor/aaaa.md) (tema por Activity).

## Concepto: el Manifest 📌 Importante

### Qué es
El **carnet de identidad** de la app: un XML en `app/src/main/AndroidManifest.xml` que Android lee **antes** de ejecutar nada. Dice qué pantallas existen, cuál se abre primero, qué permisos necesita, qué hardware usa y qué componentes ofrece a otras apps.

### Estructura mínima (la de todos los proyectos)
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- 1) Permisos y hardware: FUERA de <application> y antes de ella -->
    <uses-permission android:name="android.permission.INTERNET" />

    <!-- 2) La app -->
    <application
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.MiApp"
        …>

        <!-- 3) Pantalla de arranque -->
        <activity android:name=".MainActivity" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- 4) Resto de pantallas: TODAS deben estar declaradas -->
        <activity android:name=".DetalleActivity" android:exported="false" />
    </application>
</manifest>
```

### Elementos que aparecen en los proyectos

| Elemento / atributo | Qué hace | Obligatorio | Proyectos |
|---|---|---|---|
| `<activity android:name=".X">` | Declara una pantalla | **Sí**, una por Activity | Todos |
| `intent-filter` MAIN + LAUNCHER | Marca la Activity que abre el icono | Sí (una) | Todos |
| `android:exported` | ¿Pueden abrirla otras apps? | Sí si tiene `intent-filter` (Android 12+) | Todos |
| `android:windowSoftInputMode="adjustResize"` | Al salir el teclado, la pantalla se encoge | No | Todos (plantilla) |
| `android:theme` en una `<activity>` | Tema solo para esa pantalla | No | aaaa (`NoActionBar`) |
| `android:resizeableActivity` | Multiventana | No | aaaa |
| `<uses-permission>` | Pide un permiso | Si lo necesitas | Adaptadores, Pokemon, Diálogo |
| `android:maxSdkVersion` | El permiso solo cuenta hasta esa API | No | Diálogo (`WRITE_EXTERNAL_STORAGE`, 28) |
| `<uses-feature required="false">` | Declara hardware que se usa pero no es imprescindible | No | Diálogo (sensores) |
| `<provider>` | Ofrece datos o archivos a otras apps | Si usas `FileProvider` | Diálogo |
| `${applicationId}` | Variable que Gradle sustituye por el id de la app | — | Diálogo |
| `tools:targetApi` | Solo silencia avisos del editor | No | aaaa |

## Concepto: permisos 📌 Importante

### Tipos
| Tipo | Ejemplos | ¿Qué hay que hacer? |
|---|---|---|
| **Normales** (poco riesgo) | `INTERNET`, `VIBRATE`, `ACCESS_NETWORK_STATE` | Solo declararlos en el manifest |
| **Peligrosos** (datos privados) | `CAMERA`, `RECORD_AUDIO`, `ACCESS_FINE_LOCATION`, `READ_CONTACTS`, `WRITE_EXTERNAL_STORAGE` (≤ API 28), `READ_MEDIA_IMAGES` (API 33+) | Declararlos **y** pedirlos en **tiempo de ejecución** (el usuario ve el diálogo "Permitir / No permitir") |

### Tabla de permisos de los proyectos

| Permiso | Para qué sirve | Dónde se usa |
|---|---|---|
| `INTERNET` | Descargar imágenes con Picasso | EjercicioAdaptadoresFinal, EjercicioPokemon |
| `WRITE_EXTERNAL_STORAGE` (`maxSdkVersion=28`) | Escribir en la galería en Android ≤ 9 | EjemploDialogoPersonalizado → `CameraActivity` |

### Pedir un permiso en tiempo de ejecución ✅ Reutilizable
```java
private final ActivityResultLauncher<String> pedirPermiso =
        registerForActivityResult(new ActivityResultContracts.RequestPermission(), concedido -> {
            if (concedido) hacerLaCosa();
            else Toast.makeText(this, "Sin permiso no se puede continuar", Toast.LENGTH_SHORT).show();
        });

private void comprobarYHacer() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
        hacerLaCosa();                                     // ya lo tenía
    } else {
        pedirPermiso.launch(Manifest.permission.CAMERA);   // sale el diálogo del sistema
    }
}
```
**Qué cambiar:** el permiso (`Manifest.permission.X`, **de `android.Manifest`**, no de tu paquete) y `hacerLaCosa()`. Recuerda declararlo **también** en el manifest.

### Qué se puede reutilizar del manifest
- ✅ El bloque `<activity>` + `intent-filter` LAUNCHER: cambia `.MainActivity`.
- ✅ `<uses-permission android:name="android.permission.INTERNET"/>` tal cual.
- ✅ El `<provider>` del `FileProvider` tal cual (usa `${applicationId}`), más su `res/xml/file_paths.xml`.
- ✏️ `android:theme` y `android:label`: cambia los nombres de tu proyecto.

### Errores comunes
| Error | Causa | Solución |
|---|---|---|
| `ActivityNotFoundException: Unable to find explicit activity class … have you declared this activity in your AndroidManifest.xml?` | La Activity no está declarada | Añadir `<activity android:name=".X"/>` (Android Studio lo hace si la creas con *New → Activity*) |
| La app no aparece en el móvil | No hay `MAIN`/`LAUNCHER` | Añadir el `intent-filter` |
| `INSTALL_PARSE_FAILED_MANIFEST_MALFORMED … exported` | Activity con `intent-filter` sin `exported` | Poner `android:exported="true"` |
| Imágenes de internet en blanco | Falta `INTERNET` | Declararlo |
| `SecurityException: Permission Denial` | Permiso peligroso no pedido en ejecución | `RequestPermission` |
| `uses-permission` dentro de `<application>` | Mal colocado | Fuera, antes de `<application>` |

## Relacionado
- [MyApplication §8](../04-proyectos-profesor/MyApplication.md) (manifest comentado atributo a atributo)
- [22 — Cámara y almacenamiento](22-camara-y-almacenamiento.md) · [21 — Sensores](21-sensores.md)
