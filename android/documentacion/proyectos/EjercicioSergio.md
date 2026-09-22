---
tags:
  - android
  - proyecto
  - estado/esqueleto
---

# Proyecto: `EjercicioSergio`

## Qué es

Igual que [anclados](anclados.md), es un proyecto en estado esqueleto: dos Activities (`MainActivity` y `SpinnerDam2Activity`) que solo contienen el arranque estándar, sin lógica de spinner ni de adaptadores implementada todavía (a pesar de que el nombre de la segunda Activity sugiere que estaba pensado para replicar el ejercicio de `Spinner` visto en [AdapterDam2](AdapterDam2.md)).

## Estructura

```
app/src/main/java/com/example/ejerciciosergio/MainActivity.java
app/src/main/java/com/example/ejerciciosergio/SpinnerDam2Activity.java
app/src/main/res/layout/activity_main.xml
app/src/main/res/layout/activity_spinner_dam2.xml
```

## `MainActivity.java` y `SpinnerDam2Activity.java`

Ambas clases son idénticas en contenido — solo cambia el layout que infla cada una:

```java
public class SpinnerDam2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spinner_dam2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
```

`extends AppCompatActivity` significa que cada clase **hereda** (ver [00-programacion-basica](../conceptos/00-programacion-basica.md) §6) el comportamiento ya construido de gestionar una pantalla — aquí ninguna de las dos añade nada propio encima. Es el mismo bloque repetitivo explicado en [01-fundamentos-bundle-intent-ciclo-vida](../conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) §5. Ninguna de las dos Activities:
- Tiene un `Spinner` conectado a un `Adapter` (comparar con la versión completa y funcional en [AdapterDam2](AdapterDam2.md)).
- Navega de una pantalla a otra (no hay ningún `Intent`/`startActivity` en el proyecto).

## Qué le falta para parecerse al ejercicio de referencia (`AdapterDam2`)

Para completar este proyecto siguiendo el patrón de [AdapterDam2](AdapterDam2.md), `SpinnerDam2Activity` necesitaría:
1. Un layout con un `<Spinner android:id="@+id/..." />`.
2. Un `ArrayAdapter<String>` (o un adaptador personalizado, ver [07-adaptadores](../conceptos/07-adaptadores.md) §6) cargado con datos.
3. `spinner.setAdapter(...)` en `onCreate`.
4. Opcionalmente, un `OnItemSelectedListener` para reaccionar a la selección.

Y `MainActivity` necesitaría un botón que lance `SpinnerDam2Activity` con un `Intent` (ver [01-fundamentos-bundle-intent-ciclo-vida](../conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) §3).
