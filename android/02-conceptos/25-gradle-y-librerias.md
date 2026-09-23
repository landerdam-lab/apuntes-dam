---
tags:
  - android
  - concepto
  - tema/gradle
aliases:
  - Gradle
  - Dependencias
  - libs.versions.toml
---

# 25 — Gradle, dependencias y librerías

> Aparece en **todos** los proyectos. Tabla de dependencias por proyecto: [INVENTARIO §6](../INVENTARIO.md). Problemas al abrir un proyecto: [Guía 01](../01-guias/01-ejecutar-la-app.md).

## Concepto: Gradle 📌 Importante

### Qué es
**Gradle** es el programa que **construye** la app: compila el Java, procesa los XML, descarga las librerías y empaqueta todo en un APK. El **Android Gradle Plugin (AGP)** le enseña a Gradle a construir apps Android. Tú le das instrucciones en los archivos `*.gradle.kts` (Kotlin DSL; los antiguos eran `build.gradle` en Groovy, como el del [PDF de Transiciones](../03-pdfs/10-transiciones.md)).

### Los archivos de un proyecto

| Archivo | Qué contiene | ¿Lo tocas? |
|---|---|---|
| `settings.gradle.kts` | Nombre del proyecto, módulos (`include(":app")`) y **repositorios** (`google()`, `mavenCentral()`) | Casi nunca |
| `build.gradle.kts` (raíz) | Plugins disponibles para los módulos (`apply false`) | Casi nunca |
| **`app/build.gradle.kts`** | SDKs, id de la app, versión, **dependencias** | **Sí** |
| **`gradle/libs.versions.toml`** | **Catálogo de versiones**: nombres cortos → librería + versión | Sí, al añadir librerías |
| `gradle/wrapper/gradle-wrapper.properties` | Versión de Gradle que se descarga (9.5.0) | Si Android Studio lo pide |
| `gradle.properties` | Opciones (memoria, AndroidX…) | No |

### `app/build.gradle.kts` explicado (el de todos los proyectos)
```kotlin
plugins {
    alias(libs.plugins.android.application)     // "esto es una app Android" (AGP del catálogo)
}

android {
    namespace = "com.example.adapterdam2"         // paquete de la clase R
    compileSdk { version = release(37) }          // con qué versión de Android se COMPILA

    defaultConfig {
        applicationId = "com.example.adapterdam2" // id único en el móvil y en Google Play
        minSdk = 24                               // versión MÍNIMA donde se puede instalar (Android 7.0)
        targetSdk = 37                            // versión para la que está pensada y probada
        versionCode = 1                           // número interno de versión (entero, siempre sube)
        versionName = "1.0"                       // versión que ve el usuario
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release { optimization { enable = false } }  // sin minificar/ofuscar la versión release
    }
    compileOptions {                              // versión del lenguaje Java
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    // buildFeatures { viewBinding = true }       // solo en aaaa
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation("com.github.bumptech.glide:glide:4.16.0")   // también se puede escribir directo
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}
```

### `compileSdk`, `minSdk` y `targetSdk` 📌 Importante
| | Significa | Si lo subes… | Si lo bajas… |
|---|---|---|---|
| `compileSdk` | Con qué SDK compila (qué métodos "existen") | Puedes usar APIs nuevas | Error si usas algo más nuevo |
| `minSdk` | Móviles más antiguos donde se instala | Menos móviles compatibles | Más móviles, pero debes comprobar `Build.VERSION.SDK_INT` antes de usar APIs nuevas |
| `targetSdk` | Qué comportamiento del sistema aceptas (edge-to-edge, permisos…) | Aplican las reglas nuevas | Google Play no acepta `targetSdk` antiguos |

Por eso [CameraActivity](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) tiene `if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)`: con `minSdk 24`, la app también corre en Android 7–9, donde `IS_PENDING` no existe.

### Tipos de dependencia
| Palabra | Cuándo se incluye | Ejemplo |
|---|---|---|
| `implementation` | En la app | appcompat, Glide, Room runtime |
| `annotationProcessor` | Solo al **compilar**, para generar código (Java) | `room-compiler` |
| `ksp` / `kapt` | Lo mismo, en Kotlin | — |
| `testImplementation` | Tests unitarios en el PC (`src/test`) | JUnit |
| `androidTestImplementation` | Tests en móvil/emulador (`src/androidTest`) | Espresso |

### El catálogo `libs.versions.toml`
```toml
[versions]                       # versiones, en un solo sitio
agp = "9.3.2"
picasso = "2.8"

[libraries]                      # alias → librería
picasso = { module = "com.squareup.picasso:picasso", version.ref = "picasso" }
activity-ktx = { group = "androidx.activity", name = "activity-ktx", version.ref = "activityKtx" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
```
Los guiones del alias se convierten en puntos al usarlo: `activity-ktx` → `libs.activity.ktx`.

### Tabla de librerías de los proyectos
| Librería/dependencia | Para qué sirve | Dónde se usa | ¿Reutilizable? |
|---|---|---|---|
| `androidx.appcompat:appcompat` | `AppCompatActivity`, compatibilidad, `AlertDialog`, fragmentos | Todos | ✅ Siempre |
| `com.google.android.material:material` | Temas Material, `MaterialToolbar`, `Snackbar`, FAB | Todos | ✅ Siempre |
| `androidx.constraintlayout:constraintlayout` | `ConstraintLayout` | Todos | ✅ |
| `androidx.activity:activity-ktx` | `EdgeToEdge`, `registerForActivityResult` | Todos menos aaaa | ✅ |
| `com.github.bumptech.glide:glide:4.16.0` | Imágenes (drawable, `File`, `Uri`, URL) | AdapterDam2, EjemploDialogoPersonalizado | ✅ |
| `com.squareup.picasso:picasso:2.8` | Imágenes por URL | EjercicioAdaptadoresFinal, EjercicioPokemon | ✅ (poco mantenida) |
| `androidx.room:room-runtime` + `room-compiler` 2.8.5 | Base de datos | EjercicioPokemon, EjemploDialogoPersonalizado | ✅ misma versión en ambas |
| `androidx.lifecycle:lifecycle-*-ktx` | `ViewModel`, `LiveData` | aaaa | ✅ |
| `androidx.navigation:navigation-fragment/ui` | Navigation Component | aaaa | ✅ |
| `androidx.recyclerview:recyclerview` | `RecyclerView` | aaaa | ✅ |

### Añadir una librería nueva (paso a paso) ✅ Reutilizable
1. En `libs.versions.toml`, en `[versions]`: `glide = "4.16.0"`.
2. En `[libraries]`: `glide = { module = "com.github.bumptech.glide:glide", version.ref = "glide" }`.
3. En `app/build.gradle.kts`: `implementation(libs.glide)`.
4. Pulsa **Sync Now** (barra amarilla) y espera.
5. Si la librería pide internet (Glide/Picasso con URLs), añade el permiso `INTERNET`.

### Errores comunes
| Error | Causa | Solución |
|---|---|---|
| *The project is using an incompatible version of the Android Gradle plugin* | Tu Android Studio es más antiguo que el AGP 9.3 | Actualizar Android Studio o bajar `agp` en el `.toml` ([Guía 01](../01-guias/01-ejecutar-la-app.md)) |
| `Could not resolve com.xxx:yyy` | Sin internet o repositorio no declarado | Conexión + `google()`/`mavenCentral()` en `settings.gradle.kts` |
| `Unresolved reference: libs.xxx` | El alias no está en el `.toml` | Añadirlo y sincronizar |
| `cannot find symbol class Glide` | Dependencia no añadida o sin sincronizar | *Sync Now* |
| Room: `cannot find implementation for …AppDatabase` | Falta `annotationProcessor` o versiones distintas | Mismas versiones de runtime y compiler |
| `Duplicate class …` | Dos versiones de la misma librería | Una sola versión, en el catálogo |

> [!warning] ⚠️ Cuidado: versiones antiguas del PDF
> El [PDF de Transiciones](../03-pdfs/10-transiciones.md) muestra `compileSdkVersion 30`, Java 1.8 y `implementation 'androidx.appcompat:appcompat:1.2.0'` (Groovy). No lo copies en un proyecto nuevo: usa la sintaxis `.kts` de arriba.

## Relacionado
- [09 — Instalación y estructura](09-instalacion-estructura-proyecto.md) · [24 — Manifest](24-manifest-y-permisos.md)
