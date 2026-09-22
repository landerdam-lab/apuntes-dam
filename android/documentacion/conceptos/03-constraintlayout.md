---
tags:
  - android
  - concepto
---

# `ConstraintLayout`

## 1. La idea

`ConstraintLayout` posiciona cada vista mediante **restricciones (constraints)**: "el lado izquierdo de esta vista va pegado al lado izquierdo del padre", "el lado superior de este texto va debajo del icono", etc. En vez de anidar layouts (como con `LinearLayout`), casi todo vive en un único nivel y las vistas se "atan" unas a otras. Es el layout por defecto que genera Android Studio, y el que usan la mayoría de tus pantallas.

## 2. Anatomía de una restricción

```xml
<ImageView
    android:id="@+id/ivEquipoLogo"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="24dp"
    android:layout_marginTop="20dp"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```
*(`item_equipo.xml`, proyecto `EjercicioAdaptadoresFinal`)*

- `app:layout_constraintStart_toStartOf="parent"` → el borde **izquierdo** (`Start`) de esta vista se ata al borde izquierdo (`Start`) del **padre** (`parent`).
- `app:layout_constraintTop_toTopOf="parent"` → el borde **superior** se ata al borde superior del padre.
- `android:layout_marginStart="24dp"` → una vez atado, deja 24dp de separación respecto a ese borde.

El patrón general del nombre del atributo es siempre:
```
app:layout_constraint<MI_LADO>_to<SU_LADO>Of="<id_del_otro_view o "parent">"
```
Lados disponibles: `Start`/`End` (izquierda/derecha, respetando idiomas RTL), `Left`/`Right` (fijos, sin RTL), `Top`/`Bottom`, y para centrado también `baseline`.

## 3. Encadenar vistas entre sí (no solo al padre)

```xml
<TextView
    android:id="@+id/tvEquipoNombre"
    app:layout_constraintStart_toEndOf="@+id/ivEquipoLogo"
    app:layout_constraintTop_toTopOf="parent" />
```
Aquí el texto del nombre se coloca **a la derecha del logo** (`toEndOf="@+id/ivEquipoLogo"`), no del padre. Esto crea una cadena de dependencias: si el logo cambiase de tamaño o posición, el texto se movería con él automáticamente.

Ejemplo con tres elementos en cascada vertical (`activity_detalles_jugador.xml`):
```xml
<TextView android:id="@+id/tvNombreJ"   app:layout_constraintTop_toTopOf="parent" />
<TextView android:id="@+id/tvDorsalJ"   app:layout_constraintTop_toBottomOf="@+id/tvNombreJ" />
<TextView android:id="@+id/tvPosicionJ" app:layout_constraintTop_toBottomOf="@+id/tvDorsalJ" />
```
Cada `TextView` cuelga del anterior: nombre → debajo del nombre el dorsal → debajo del dorsal la posición.

## 4. `layout_constraintHorizontal_bias` / `_Vertical_bias`

```xml
<TextView
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintHorizontal_bias="0.498" />
```

Cuando una vista está atada **por los dos lados opuestos** (Start Y End al mismo tiempo, o Top Y Bottom), queda "flotando" en medio de ese espacio, y el `bias` (sesgo, de 0.0 a 1.0) decide en qué punto exacto: `0.0` = pegada del todo al lado Start/Top, `1.0` = pegada del todo al lado End/Bottom, `0.5` = centrada. `0.498` es prácticamente centrado (así es como queda tras arrastrar un elemento casi al centro en el editor visual de Android Studio — el editor genera ese número tan preciso automáticamente).

## 5. `wrap_content` vs tamaño fijo (`179dp`) vs `match_parent`

- `wrap_content`: la vista mide justo lo que necesita su contenido.
- `179dp` / `547dp`: tamaño fijo en *density-independent pixels* (se ve igual en pantallas de distinta densidad). Suele aparecer cuando se ha redimensionado la vista a mano arrastrando sus bordes en el editor visual — no siempre es la mejor práctica (no se adapta a otras pantallas), pero funciona.
- `match_parent` (o el equivalente `0dp` + dos restricciones opuestas, llamado "match constraints"): ocupa todo el espacio disponible entre sus restricciones.

## 6. `tools:srcCompat="@tools:sample/avatars"`

```xml
<ImageView tools:srcCompat="@tools:sample/avatars" />
```
El prefijo `tools:` (en vez de `android:`) significa que este atributo **solo se ve en el editor/vista previa de Android Studio**, nunca en la app compilada de verdad. Se usa como *placeholder* para poder ver algo con aspecto realista mientras diseñas, mientras que la imagen real se asigna luego por código (`ivLogo.setImageResource(...)` o con Picasso/Glide, ver [07-adaptadores](07-adaptadores.md)).

## 7. `tools:context=".MainActivity"`

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    tools:context=".MainActivity">
```
Le dice al editor visual "este layout pertenece a `MainActivity`", para que la vista previa aplique el tema y pueda resolver referencias del contexto correctamente. Tampoco tiene efecto en tiempo de ejecución.

## 8. El id de la raíz: `android:id="@+id/main"`

Todas las raíces de layout de tus proyectos llevan `android:id="@+id/main"`. No es un id "mágico" ni obligatorio por el sistema — es simplemente el nombre que usa el código repetitivo de `EdgeToEdge`/insets (ver [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md)) para encontrar la vista raíz vía `findViewById(R.id.main)` y aplicarle el padding de las barras del sistema.

## 9. Convertir un layout existente a `ConstraintLayout` desde Android Studio

Si ya tienes un layout con otro tipo de contenedor (por ejemplo, un `LinearLayout` vacío) y quieres pasarlo a `ConstraintLayout` sin reescribir el XML a mano, Android Studio lo hace automáticamente:

1. Abre el layout en la pestaña **Design** (o **Split**).
2. En el panel **Component Tree** (el árbol de vistas, normalmente a la izquierda), haz clic derecho sobre la vista raíz.
3. Selecciona **Convert view...** → **ConstraintLayout**.

Esto reescribe la etiqueta raíz del XML de `<LinearLayout ...>` a `<androidx.constraintlayout.widget.ConstraintLayout ...>` automáticamente (intentando preservar, cuando puede, restricciones equivalentes a la disposición anterior).

### El editor visual: vista *Blueprint*

Una vez en `ConstraintLayout`, el panel de diseño (**Design**) muestra una vista llamada **Blueprint** — una representación esquemática (sin estilos reales, solo contornos) donde puedes:
- **Arrastrar** componentes desde la paleta hasta el lienzo para añadirlos.
- **Arrastrar los "anclajes"** (los círculos en los bordes de cada vista seleccionada) hacia el padre u otra vista, para crear restricciones (`app:layout_constraint...`) sin escribir XML a mano.
- Ajustar márgenes y tamaño (`wrap_content`/`match_parent`/fijo) desde el panel de propiedades de la derecha.

Todo lo que hagas arrastrando en el Blueprint se traduce automáticamente en los atributos `app:layout_constraint...` explicados en este documento — es exactamente lo mismo, solo que generado visualmente en vez de escrito a mano. Es la forma habitual de trabajar con `ConstraintLayout` en la práctica: pocas veces se escriben las restricciones directamente en el XML desde cero.

> **Detalle práctico importante**: si un `ImageButton`/`ImageView` usa una imagen con transparencia (como los selectores de estado de [15-drawables-formas-y-selectores](15-drawables-formas-y-selectores.md) §2), hay que asignar la imagen mediante la propiedad **`background`** (no `src`) para que la transparencia se respete visualmente sobre lo que haya detrás — con `src` a veces el editor añade un fondo blanco/gris por defecto que tapa la transparencia.

## 10. `GridView` dentro de `ConstraintLayout`

```xml
<GridView
    android:id="@+id/gvJugadores"
    android:numColumns="2"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent" />
```
Un `GridView` (o `ListView`) es **una vista más** dentro de `ConstraintLayout`: se posiciona con restricciones igual que cualquier `TextView` o `ImageView`. Lo que pase *dentro* de la rejilla (cuántas columnas, qué contenido) es independiente del sistema de restricciones — eso lo controla su adaptador (ver [07-adaptadores](07-adaptadores.md)).

## `ConstraintLayout` vs pesos: ¿cuándo usar cuál?

| Situación | Mejor opción |
|---|---|
| Rejilla regular, reparto proporcional simple (mitad/mitad, filas iguales) | `LinearLayout` + pesos ([02-diseno-basado-en-pesos](02-diseno-basado-en-pesos.md)) |
| Elementos relacionados entre sí de forma libre, no en fila/columna estricta | `ConstraintLayout` |
| Quieres editar visualmente arrastrando en el editor de Android Studio | `ConstraintLayout` (el editor está pensado para él) |
| Layouts con mucha anidación que quieres "aplanar" para mejorar el rendimiento | `ConstraintLayout` (evita anidar varios `LinearLayout`) |

## Ver también
- [13-view-viewgroup](13-view-viewgroup.md) — la base (`View`/`ViewGroup`, medida y posicionado) sobre la que funciona cualquier layout, incluido `ConstraintLayout`.

## 🏋️ Practica esto

- [Nivel 2 — ejercicio 2.3](../ejercicios/02-nivel-2-disenos-xml.md) (logo, título y botón con restricciones)
