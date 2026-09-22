---
tags:
  - android
  - proyecto
  - estado/esqueleto
---

# Proyecto: `Pokemons`

## Qué es

Un proyecto **recién creado con la plantilla de Android Studio** ("Empty Views Activity") y **sin nada propio todavía**. Es un punto de partida: una sola pantalla que muestra el texto `Hello World!` centrado.

Por el nombre, está pensado para un ejercicio relacionado con Pokémon (por ejemplo, una lista de Pokémon), que aún no se ha empezado.

## Estructura

```
app/src/main/java/com/example/pokemons/MainActivity.java
app/src/main/res/layout/activity_main.xml
app/build.gradle.kts
```

Es la estructura mínima de cualquier proyecto: ver [09 — Instalación y estructura de proyecto](../conceptos/09-instalacion-estructura-proyecto.md).

## `MainActivity.java`

```java
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

Es **exactamente** el arranque estándar que se repite en todos los proyectos y que se explica en [01 — Fundamentos §5](../conceptos/01-fundamentos-bundle-intent-ciclo-vida.md). No hay ni un `Button`, ni un `Intent`, ni lógica propia. Igual que [anclados](anclados.md).

## `activity_main.xml`

Un `ConstraintLayout` con un único `TextView` que dice `Hello World!`, atado a los cuatro lados del padre para quedar **centrado** ([03 — ConstraintLayout](../conceptos/03-constraintlayout.md) §2 y §4):

```xml
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello World!"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```

Dependencias: solo las de la plantilla (`appcompat`, `material`, `constraintlayout`, `activity`). **No** tiene ni Room ni librerías de imágenes (Picasso/Glide).

## Cómo se podría completar (idea de ejercicio)

Siguiendo los patrones ya vistos en la documentación:

1. Una clase modelo `Pokemon` (nombre, tipo, imagen), como `Jugador` en [EjercicioAdaptadoresFinal](EjercicioAdaptadoresFinal.md) o `Equipo` y `Jugador` en el [Nivel 3](../ejercicios/03-nivel-3-adaptadores.md).
2. Un `ListView` con un adaptador propio y un layout de fila ([07 — Adaptadores](../conceptos/07-adaptadores.md)).
3. Al pulsar un Pokémon, una pantalla de detalle con `Intent` + `Serializable` ([Nivel 3 — 3.3](../ejercicios/03-nivel-3-adaptadores.md)).
4. Para cargar imágenes desde internet: `Picasso` o `Glide` y el permiso `INTERNET` ([07](../conceptos/07-adaptadores.md) y [09 §5](../conceptos/09-instalacion-estructura-proyecto.md)).

## Ver también
- [anclados](anclados.md) y [EjercicioSergio](EjercicioSergio.md) — otros proyectos en estado esqueleto.
- [01 — Fundamentos: Bundle, Intent y ciclo de vida](../conceptos/01-fundamentos-bundle-intent-ciclo-vida.md)
