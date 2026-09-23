---
tags:
  - android
  - concepto
---

# Instalación, estructura de proyecto y cómo se compila/ejecuta una app Android

> 📘 **¿Quieres el paso a paso para abrir un proyecto, crear un emulador, ejecutar en tu móvil y leer los errores?** Está en la [Guía: cómo abrir, ejecutar y probar una app](../01-guias/01-ejecutar-la-app.md). Este documento explica **qué es** cada pieza; la guía explica **cómo se usa**.

> ℹ️ **Versiones en este equipo:** los proyectos compilan contra **API 37** (Android 17) y el emulador usa esa misma versión; el material del curso habla de Android 16 (API 36). Cualquier versión reciente sirve.

## 0. Instalación concreta (según el material del curso)

Pasos exactos para dejar el entorno listo:
1. Descargar el instalador desde `https://developer.android.com/studio/` e instalarlo.
2. Dentro de Android Studio: **Tools → SDK Manager**, pestaña **SDK Platforms**, e instalar:
   - **Android 16 (API level 36)** — la versión de Android sobre la que se compila.
   - **Android SDK Build-Tools** (pestaña *SDK Tools*).
   - **Android Emulator** (pestaña *SDK Tools*).
   - **Android SDK Platform-Tools** (pestaña *SDK Tools*) — incluye `adb` (ver §6).
3. Crear el emulador: **Tools → Device Manager** → crear un dispositivo **Pixel 8** con imagen de sistema **Android 16** (se descarga desde el propio asistente si no está ya descargada).

## 1. Las piezas necesarias

- **Android Studio** — el IDE oficial. Incluye editor de código, editor visual de layouts, emulador de dispositivos y todas las herramientas de compilación integradas.
- **Android SDK** — el conjunto de librerías y herramientas para compilar apps Android (se instala junto con Android Studio, o por separado en `%LOCALAPPDATA%\Android\Sdk` en Windows).
- **JDK** — Java Development Kit (Android Studio trae uno embebido, normalmente no hace falta instalarlo aparte).
- **Gradle** — el sistema de compilación (build system) que convierte tu código + recursos en un `.apk` instalable. No hace falta instalarlo a mano: cada proyecto trae su propio "Gradle Wrapper" (`gradlew.bat` en Windows / `gradlew` en Mac/Linux), que descarga automáticamente la versión de Gradle correcta la primera vez que se usa.
- **Un dispositivo o emulador (AVD)** — donde se instala y ejecuta la app para probarla. Un AVD (*Android Virtual Device*) es un dispositivo Android simulado que corre en tu PC.

## 2. Estructura de un proyecto Android

```
MiProyecto/
├── app/                                  ← el módulo de la aplicación
│   ├── build.gradle.kts                  ← configuración de compilación DE ESTE MÓDULO
│   └── src/main/
│       ├── AndroidManifest.xml           ← "índice" de la app (Activities, permisos...)
│       ├── java/com/example/miapp/       ← todo tu código Java/Kotlin
│       └── res/                          ← TODOS los recursos (ver §3)
├── build.gradle.kts                      ← configuración de compilación DEL PROYECTO ENTERO
├── settings.gradle.kts                   ← qué módulos forman el proyecto
├── gradlew.bat / gradlew                 ← el "Gradle Wrapper" (para compilar desde la terminal)
└── gradle/libs.versions.toml             ← catálogo centralizado de versiones de librerías
```

### `app/build.gradle.kts` — ejemplo real (`EjercicioAdaptadoresFinal`)

```kotlin
android {
    namespace = "com.example.ejercicioadaptadoresfinal"
    compileSdk { version = release(37) }        // con qué versión del SDK se COMPILA

    defaultConfig {
        applicationId = "com.example.ejercicioadaptadoresfinal"  // id único de la app (Play Store)
        minSdk = 24                              // versión mínima de Android soportada
        targetSdk = 37                           // versión para la que está pensada/probada
        versionCode = 1                          // número interno de versión (para actualizaciones)
        versionName = "1.0"                      // versión visible para el usuario
    }
}

dependencies {
    implementation(libs.appcompat)               // librerías externas que usa la app
    implementation(libs.picasso)
    ...
}
```
- **`minSdk`** — si el móvil del usuario tiene una versión de Android más antigua que esta, la app ni se puede instalar.
- **`compileSdk`/`targetSdk`** — normalmente se ponen a la versión más reciente disponible; afectan a qué comportamientos "modernos" del sistema aplican automáticamente (por ejemplo, `targetSdk` alto activa el "edge-to-edge" obligatorio que se ve en todas las Activities, ver [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md)).

## 3. La carpeta `res/` (recursos)

```
res/
├── layout/       ← ficheros XML de pantallas y filas de listas (activity_main.xml, item_equipo.xml...)
├── values/       ← strings.xml, colors.xml, styles.xml/themes.xml, arrays.xml, dimens.xml (tamaños/distancias, ver 15-drawables-formas-y-selectores)
├── values-night/ ← igual que values/, pero para modo oscuro (se aplica automáticamente)
├── drawable/     ← imágenes, formas vectoriales, selectores de estado
├── mipmap/       ← iconos de la app (en distintas resoluciones)
├── transition/   ← ficheros de transición (ver [08-transiciones](08-transiciones.md))
├── anim/         ← animaciones simples XML (para overridePendingTransition)
└── xml/          ← ficheros de configuración varios (backup_rules.xml, data_extraction_rules.xml)
```

**Regla clave**: nunca se pone texto, color o tamaño "a pelo" en el código si se puede evitar — se referencia un recurso:
```xml
<!-- res/values/strings.xml -->
<string name="titulo">Mi App</string>
```
```xml
<TextView android:text="@string/titulo" />
```
```java
String texto = getString(R.string.titulo);
```
Esto permite, por ejemplo, traducir la app entera a otro idioma sin tocar el código (solo añadiendo una carpeta `values-en/strings.xml` con las traducciones), o cambiar todos los colores desde un único sitio.

## 4. La clase `R` — el puente entre XML y Java

`R` es una clase que **Android Studio genera automáticamente** (nunca se escribe a mano) cada vez que compilas, escaneando toda la carpeta `res/`. Por cada recurso, `R` gana una constante:

```java
R.layout.activity_main     // res/layout/activity_main.xml
R.id.tvNombreEquipo        // cualquier android:id="@+id/tvNombreEquipo" de cualquier layout
R.drawable.caballo1        // res/drawable/caballo1.png
R.string.titulo            // <string name="titulo"> de strings.xml
R.array.imagenes           // <array name="imagenes"> de arrays.xml
R.transition.slide         // res/transition/slide.xml
```

Por eso, si escribes mal un `android:id` en el XML o el nombre de un recurso que no existe, el código **no compila** (`R.id.eseNombre` ni siquiera existiría) — es una red de seguridad muy útil frente a errores de tipeo.

## 5. `AndroidManifest.xml`

Ver también [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) §4. Es el fichero que declara:
- Qué Activities tiene la app (y cuál es la de arranque).
- Qué permisos necesita (`<uses-permission android:name="android.permission.INTERNET" />`, imprescindible en todos estos proyectos porque cargan imágenes de internet con Picasso/Glide).
- El tema visual general de la app (`android:theme="@style/Theme.MiApp"`).

## 6. Cómo se compila y se instala (lo que se ha usado en este proyecto de documentación)

Desde una terminal, situado en la carpeta del proyecto:

```powershell
.\gradlew.bat installDebug
```
Esto:
1. Compila todo el código Java a bytecode.
2. Empaqueta el código + recursos en un `.apk` firmado con una clave de depuración (*debug*).
3. Si hay un emulador o dispositivo conectado (`adb devices` lo confirma), instala ese `.apk` en él automáticamente.

### `adb` (Android Debug Bridge)

Es la herramienta de línea de comandos para comunicarse con un dispositivo/emulador (normalmente en `%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe`):

```powershell
adb devices                                        # lista dispositivos/emuladores conectados
adb shell am start -n com.example.paquete/.MainActivity   # lanza una Activity concreta
adb logcat -d                                       # vuelca el registro de mensajes (logs) del sistema
adb install app-debug.apk                           # instala un APK manualmente
```

`logcat` es especialmente importante al depurar: ahí aparecen los mensajes de error (`AndroidRuntime: FATAL EXCEPTION`) cuando una app crashea, con la pila de llamadas completa que indica en qué línea exacta ha fallado.

## 7. El ciclo típico de trabajo

```
1. Editas el código/XML en Android Studio
2. Pulsas ▶ Run (o gradlew installDebug desde terminal)
3. Gradle compila y genera el APK
4. Se instala en el emulador/dispositivo y se lanza automáticamente
5. Pruebas, ves logs con Logcat si algo falla
6. Vuelves al paso 1
```

## Ver también
- [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) — qué pasa dentro de la app una vez que se lanza.
