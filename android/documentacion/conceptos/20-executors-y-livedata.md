---
tags:
  - android
  - concepto
---

# 20 — `Executor` (`AppExecutors`) y `LiveData`

> Proyecto donde se usan: [EjemploDialogoPersonalizado](../proyectos/EjemploDialogoPersonalizado.md). Complementa a [12 — Hilos, a fondo](12-hilos-en-profundidad.md) y [17 — Room](17-room-base-de-datos.md).

## El problema

Android tiene **un hilo principal** (*main thread* o *UI thread*) que hace dos cosas a la vez: **dibujar la pantalla** y **atender los toques**. Dos reglas:

1. **Nada lento en el hilo principal.** Una consulta a la base de datos, una descarga... congelarían la app (y Android podría mostrar "La aplicación no responde"). Room, de hecho, **lanza una excepción** si lo intentas.
2. **Solo el hilo principal puede tocar la interfaz.** Mostrar un `Toast`, abrir una `Activity` o cerrar un diálogo desde otro hilo puede fallar.

La solución es el patrón:

```
hilo principal ──► manda el trabajo lento ──► hilo de fondo (consulta la BD)
       ▲                                              │
       └──────── devuelve el resultado ◄──────────────┘
                 (para actualizar la pantalla)
```

## `Executor`: "quien ejecuta tareas"

`Executor` es una interfaz de Java con un único método: `execute(Runnable)`. Le das una tarea (un `Runnable`, un bloque de código con un método `run()`) y **él decide en qué hilo** se ejecuta.

```java
executor.execute(new Runnable() {
    @Override public void run() {
        // este código se ejecuta en el hilo del executor
    }
});
```

## `AppExecutors` (`bbdd/AppExecutors.java`)

Una clase propia que agrupa los ejecutores que necesita la app. Es un **Singleton** (una sola instancia, ver [17](17-room-base-de-datos.md)):

| Ejecutor | Para qué | Cómo debería crearse (ver el aviso de más abajo) |
|---|---|---|
| `getDiskIO()` | Base de datos / disco | `Executors.newSingleThreadExecutor()` — **un solo hilo**: las operaciones se hacen **de una en una, en orden** |
| `getMainThread()` | Volver a la pantalla | `MainThreadExecutor`, con un `Handler` del `Looper` principal |
| `getNetworkIO()` | Red | `Executors.newFixedThreadPool(3)` — un grupo de **3 hilos** |

### `MainThreadExecutor`
```java
private static class MainThreadExecutor implements Executor {
    private final Handler handler = new Handler(Looper.getMainLooper());
    @Override public void execute(Runnable command) { handler.post(command); }
}
```
`Looper.getMainLooper()` es el "buzón de mensajes" del hilo principal, y `Handler.post(...)` deja una tarea en ese buzón. Es la misma idea que `Handler.postDelayed` del [Toast personalizado](04-toast-personalizado.md) (teoría en [12-hilos-en-profundidad](12-hilos-en-profundidad.md)).

### ⚠️ Un detalle del orden de los parámetros
El constructor recibe `(diskIO, mainThread, networkIO)`, pero en `getInstance()` se le pasa:

```java
new AppExecutors(Executors.newSingleThreadExecutor(),   // → diskIO      ✔
                 Executors.newFixedThreadPool(3),       // → mainThread  ✘ (es un grupo de hilos de fondo)
                 new MainThreadExecutor());             // → networkIO   ✘ (es el del hilo principal)
```

Es decir, **`getMainThread()` en realidad devuelve un grupo de hilos de fondo** y `getNetworkIO()` es el que ejecuta en el hilo principal. **El código del PDF del curso tiene exactamente el mismo orden**, así que es un despiste del material original que se ha copiado tal cual.

Consecuencia: en `LoginDialogFrag`, el `startActivity(...)` y el `dismiss()` se ejecutan en un hilo de fondo. Suele funcionar en la práctica, pero no está garantizado. La corrección es cambiar el orden en `getInstance()`:

```java
new AppExecutors(Executors.newSingleThreadExecutor(), new MainThreadExecutor(), Executors.newFixedThreadPool(3));
```
(El proyecto se ha dejado como en el PDF, sin esa corrección; se documenta aquí para que lo sepas.)

## Ejemplo completo: el login (`LoginDialogFrag`)

```java
AppExecutors.getInstance().getDiskIO().execute(new Runnable() {          // (1) a segundo plano
    @Override public void run() {
        final Usuario usu = mDb.usuariosDao().loadUsuarioByNamePass(nombre, password);   // consulta

        AppExecutors.getInstance().getMainThread().execute(new Runnable() {   // (2) de vuelta a la pantalla
            @Override public void run() {
                if (usu != null) { startActivity(new Intent(getContext(), CentralActivity.class)); }
                else             { dismiss(); }
            }
        });
    }
});
```

1. Se lee lo escrito (en el hilo principal) y se **manda la consulta a `getDiskIO()`**.
2. Con el resultado, se **vuelve al hilo principal** para actuar sobre la interfaz.

Nota de sintaxis: `nombre` y `password` se usan dentro de clases anónimas, así que deben ser `final` o no modificarse nunca (Java lo exige).

## `LiveData`: datos que avisan cuando cambian

El otro problema: la lista de usuarios de `RegisterActivity` debe **actualizarse sola** cuando se inserta, edita o borra un usuario, sin recargarla a mano.

`LiveData<T>` es un **contenedor de datos observable**. Tú te "suscribes" y él te avisa cada vez que el contenido cambia.

**En el DAO:**
```java
@Query("SELECT * FROM Usuario ORDER BY id")
LiveData<List<Usuario>> loadAllUsusarios();
```
Room vigila la tabla `Usuario`; cada vez que cambia, vuelve a lanzar la consulta y notifica.

**En `RegisterActivity`:**
```java
mDb.usuariosDao().loadAllUsusarios().observe(this, new Observer<List<Usuario>>() {
    @Override public void onChanged(List<Usuario> usuarios) {
        usuariosAdapter.setmUsuarioList(usuarios);   // → notifyDataSetChanged() → la lista se redibuja
    }
});
```

- **`observe(this, ...)`** — el primer parámetro es el **propietario del ciclo de vida** (la `Activity`). `LiveData` solo avisa mientras la pantalla está visible y deja de hacerlo al destruirse: no hay fugas de memoria ni fallos por actualizar una pantalla cerrada.
- **`onChanged`** se ejecuta **en el hilo principal**, por eso puede tocar el adaptador sin problemas.
- Se llama a `observe` **una sola vez** (en `onCreate`). Después, `insert`/`update`/`delete` en la BD → `onChanged` con la lista nueva → la pantalla se refresca. **Nunca hay que volver a "consultar".**

### El flujo completo del CRUD

```
[Botón "Nuevo"] ──► getDiskIO(): insertUsuario() ──► cambia la tabla
                                                          │ (Room lo detecta)
                                                          ▼
                              LiveData → onChanged(lista nueva) → adaptador → ListView se redibuja
```

## Resumen

| Necesito... | Uso |
|---|---|
| Hacer algo lento (BD, red) | `AppExecutors.getInstance().getDiskIO().execute(...)` |
| Tocar la interfaz tras el trabajo lento | Ejecutor del hilo principal (`getMainThread()` si el orden es el correcto) |
| Una lista que se actualiza sola | Consulta que devuelve `LiveData` + `.observe(this, ...)` |

## Ver también
- [12 — Hilos, a fondo](12-hilos-en-profundidad.md) — `Looper`, `Handler`, ANR.
- [05 — AsyncTask e hilos](05-asynctask-e-hilos.md) — la alternativa antigua (obsoleta) a los `Executor`.
- [17 — Room](17-room-base-de-datos.md) — la regla de "nada de BD en el hilo principal".

## 🏋️ Practica esto

- [Nivel 7 — ejercicio 7.2](../ejercicios/07-nivel-7-mejoras-del-proyecto.md) (arreglar el orden de `AppExecutors`)
- [Nivel 6 — ejercicio 6.3](../ejercicios/06-nivel-6-dialogos-y-room.md) (app de notas: `LiveData` + `AppExecutors`)
