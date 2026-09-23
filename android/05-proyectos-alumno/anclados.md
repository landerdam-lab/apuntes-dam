---
tags:
  - android
  - proyecto
  - estado/esqueleto
---

# Proyecto: `anclados`

## Qué es

El proyecto más pequeño de todos: un esqueleto de app recién creado desde la plantilla "Empty Views Activity" de Android Studio, sin ninguna lógica añadida todavía. Es útil como referencia de "cuál es el punto de partida mínimo" antes de añadir nada.

## Estructura

```
app/src/main/java/com/example/anclados/MainActivity.java
app/src/main/res/layout/activity_main.xml
```

Una sola Activity, sin adaptadores, sin modelos, sin navegación a otras pantallas.

## `MainActivity.java` fragmento a fragmento

```java
package com.example.anclados;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
```

`public class MainActivity extends AppCompatActivity` significa que esta clase **hereda** (ver [00-programacion-basica](../02-conceptos/00-programacion-basica.md) §6 si "herencia"/`extends` es nuevo para ti) todo el comportamiento ya construido en `AppCompatActivity` — gestionar una pantalla, su ciclo de vida, etc. — y aquí solo se añade lo específico de esta pantalla concreta (en este caso, nada).

Esto es **exactamente** el bloque "boilerplate" explicado en detalle en [01-fundamentos-bundle-intent-ciclo-vida](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) §5:
- `super.onCreate(...)` — inicialización obligatoria.
- `EdgeToEdge.enable(this)` — pantalla completa "de borde a borde".
- `setContentView(R.layout.activity_main)` — infla el único layout del proyecto.
- El listener de `WindowInsets` — evita que la barra de estado tape el contenido.

No hay `findViewById` de ningún widget propio, ni listeners de clic, ni navegación — literalmente no hace nada más que mostrarse en pantalla.

## `activity_main.xml`

Un `ConstraintLayout` vacío (o con, como mucho, algún `TextView` de plantilla tipo "Hello World!"), sin contenido funcional. Ver [03-constraintlayout](../02-conceptos/03-constraintlayout.md) para entender la sintaxis general de este tipo de layout si se amplía en el futuro.

## Qué le falta para ser un ejercicio completo

Este proyecto no tiene ninguna funcionalidad que documentar más allá del arranque — sería el punto de partida para añadir, por ejemplo, un `RecyclerView` de elementos "anclados" (a juzgar por el nombre), pero esa parte no está implementada.
