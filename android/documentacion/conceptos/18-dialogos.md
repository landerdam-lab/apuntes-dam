---
tags:
  - android
  - concepto
---

# 18 — Diálogos: `DialogFragment` personalizado y `AlertDialog`

> Proyecto donde se usan los dos: [EjemploDialogoPersonalizado](../proyectos/EjemploDialogoPersonalizado.md). PDF del curso: `pdfs/1-DialogSQLitePersonalizado.pdf`.

## Qué es un diálogo

Una ventana pequeña que aparece **encima** de la pantalla actual para pedir una decisión o un dato. No ocupa toda la pantalla y el usuario debe responder (o cancelarla) antes de seguir. Es un evento **modal**.

En este proyecto hay **dos tipos**, y conviene saber cuándo usar cada uno:

| | `AlertDialog` | `DialogFragment` personalizado |
|---|---|---|
| **Diseño** | Fijo: título + mensaje + hasta 3 botones | El que tú dibujes en un XML |
| **Cuándo** | Confirmaciones sencillas ("¿Eliminar?") | Formularios: login, registro, elegir datos... |
| **Ejemplo aquí** | Confirmar el borrado de un usuario | El login (usuario + contraseña) |
| **Sobrevive a girar la pantalla** | No (se pierde) | Sí (lo gestiona el `FragmentManager`) |
| **Se crea con** | `AlertDialog.Builder` | Una clase que hereda de `DialogFragment` |

---

## 1. `AlertDialog` — la confirmación de borrado

En `RegisterActivity`, al **mantener pulsado** un usuario:

```java
AlertDialog.Builder alerta = new AlertDialog.Builder(RegisterActivity.this);
alerta.setTitle("Advertencia");
alerta.setMessage("¿Seguro que quieres eliminar este usuario?");   // recomendable
alerta.setPositiveButton("si", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        eliminar(idEliminar);
    }
});
alerta.setNegativeButton("no", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        Toast.makeText(RegisterActivity.this, "NO SE ELIMINO", Toast.LENGTH_SHORT).show();
    }
});
alerta.show();
```

### El patrón *Builder*
Un `AlertDialog` no se crea con un constructor gigante. Se usa un **constructor auxiliar** (`Builder`) al que vas encadenando configuraciones (`setTitle`, `setMessage`, `setPositiveButton`...) y al final `show()` lo construye y lo muestra.

### Las piezas
- **`new AlertDialog.Builder(contexto)`** — necesita un `Context` (la pantalla). Dentro de una clase anónima (el `OnItemLongClickListener`), `this` sería el listener y no la `Activity`, por eso se escribe `RegisterActivity.this`.
- **`setPositiveButton` / `setNegativeButton` / `setNeutralButton`** — los tres botones posibles: aceptar, cancelar y "quizá / más tarde". Cada uno recibe el texto y un `DialogInterface.OnClickListener` con lo que hacer al pulsarlo.
- **Al pulsar cualquier botón el diálogo se cierra solo.**
- **`final int idEliminar`** — una variable local usada dentro de una clase anónima debe ser `final` (o no cambiar nunca), porque la clase interna guarda una *copia* del valor.

### Errores reales de este proyecto
1. **`AlertaDialog` en vez de `AlertDialog`** (falta la "t"): el compilador dice `cannot find symbol` porque esa clase no existe. Recuerda que **Java distingue letras exactas**; Android Studio autocompleta con `Ctrl+Espacio` para evitar estas erratas.
2. **Sin `setMessage`**: el diálogo solo tenía título ("Advertencia") y el usuario no veía *qué* se iba a eliminar. Un diálogo de confirmación siempre debería explicar la acción.
3. **Import correcto**: debe ser `androidx.appcompat.app.AlertDialog` (el que ya estaba), no `android.app.AlertDialog`, para que respete el tema de la app.

---

## 2. `DialogFragment` — el login personalizado

Un `DialogFragment` es un **Fragment** (ver [16-fragmentos](16-fragmentos.md)) que en vez de ocupar un hueco de la pantalla se muestra **flotando**. Se crea una clase que herede de él (ver `extends` en [00-programacion-basica](00-programacion-basica.md) §6).

### Los 4 pasos para crearlo

**Paso 1 — Diseñar el contenido** (`res/layout/dialog_per.xml`): un `LinearLayout` con el logo (`ImageView`), dos filas usuario/contraseña con `layout_weight` (ver [02-diseno-basado-en-pesos](02-diseno-basado-en-pesos.md)) y los botones Aceptar/Cancelar. `android:inputType="textPassword"` oculta lo que se escribe con puntitos.

**Paso 2 — La clase:**

```java
public class LoginDialogFrag extends DialogFragment {
    public LoginDialogFrag() { super(); }   // constructor VACÍO obligatorio
    ...
}
```
> El constructor vacío es **obligatorio**: cuando Android recrea el diálogo (p. ej. al girar la pantalla) lo hace llamando a ese constructor, sin argumentos.

**Paso 3 — Los métodos del ciclo de vida, en orden:**

| Método | Cuándo se llama | Qué se hace aquí |
|---|---|---|
| `onCreateDialog(...)` | Al crear la **ventana** | Ponerle el título: `dialog.setTitle("Login")` |
| `onCreateView(...)` | Al crear el **contenido** | `inflater.inflate(R.layout.dialog_per, container, false)` |
| `onViewCreated(...)` | Justo después | Enlazar los `findViewById` y programar los botones |
| `onCancel(...)` | Si el usuario cancela (fuera del diálogo / Atrás) | (vacío aquí) |
| `onDetach()` | Al separarse de la Activity | (vacío aquí) |

- **`inflate`** convierte el XML en objetos `View` reales. Sus parámetros: qué layout, en qué contenedor `parent` (ver [13-view-viewgroup](13-view-viewgroup.md)) y `false` = "no lo añadas tú, ya lo añadirá el sistema".
- **`super.onCreateDialog(...)`** ya construye una ventana básica; nosotros solo la retocamos.

**Paso 4 — Mostrarlo** (desde `MainActivity`):

```java
dialog = new LoginDialogFrag();
dialog.show(getSupportFragmentManager(), "Login");
```
`show(gestor, etiqueta)` lo añade a la pantalla. La etiqueta `"Login"` es un nombre para poder localizarlo después con `findFragmentByTag`.

### Cerrarlo: `dismiss()`
Cierra el diálogo. Se usa tanto en **Cancelar** como cuando el login falla.

### Abrir otra pantalla desde el diálogo
```java
Intent intent = new Intent(getContext(), CentralActivity.class);
startActivity(intent);
```
`getContext()` devuelve la `Activity` que contiene el fragmento (ver [11-intent-en-profundidad](11-intent-en-profundidad.md)). Tras `startActivity`, el diálogo **no se cierra solo**: sigue existiendo debajo de la nueva pantalla; si vuelves atrás, todavía estará ahí. Para que se cierre habría que llamar también a `dismiss()`.

### Login: de "usuario fijo" a "usuario en base de datos"
El PDF muestra la evolución en tres fases; en el código actual el login ya consulta la BD:

| Fase | Comprobación |
|---|---|
| 1 | `nombre.equals("Almi") && password.equals("Almi123")` — valores escritos en el código |
| 2 | Igual + abrir `CentralActivity` |
| 3 (actual) | `mDb.usuariosDao().loadUsuarioByNamePass(nombre, password)` — busca en la tabla; `null` = credenciales incorrectas |

La consulta va en un **hilo secundario** y el resultado se procesa de vuelta en el **hilo principal** (`startActivity` y `dismiss` tocan la interfaz). Se explica en [20-executors-y-livedata](20-executors-y-livedata.md).

### Detalle a mejorar
Cuando el login falla, el `Toast` de aviso está **comentado** y el diálogo simplemente desaparece, sin decir por qué. El PDF sí muestra el aviso (`"Usuario o contraseña incorrectos"`). Descomentar esa línea (dejando `dismiss()` después) es la mejora más sencilla.

---

## Comparativa rápida: qué usar para qué

- ¿Solo preguntar "sí / no"? → **`AlertDialog`**.
- ¿Pedir datos con tu propio diseño? → **`DialogFragment`** + XML propio.
- ¿Un aviso que se cierra solo a los segundos? → **`Toast`** (ver [04-toast-personalizado](04-toast-personalizado.md)).

## Ver también
- [16 — Fragmentos](16-fragmentos.md) — el `DialogFragment` es un tipo de fragmento.
- [17 — Room](17-room-base-de-datos.md) — la base de datos contra la que se valida el login.
- [19 — TextWatcher y eventos de lista](19-textwatcher-y-eventos-de-lista.md) — el otro tipo de "escuchadores" del proyecto.

## 🏋️ Practica esto

- [Nivel 6 — ejercicios 6.1 y 6.2](../ejercicios/06-nivel-6-dialogos-y-room.md) (`AlertDialog` y `DialogFragment` de login)
- [Nivel 7 — ejercicios 7.1 y 7.3](../ejercicios/07-nivel-7-mejoras-del-proyecto.md) (mensaje del diálogo y aviso del login)
