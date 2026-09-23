---
tags:
  - android
  - reutilizable
aliases:
  - Cambiar de pantalla
  - Intent explícito
---

# 02 — Cambiar de pantalla

## Abrir otra Activity ✅ Reutilizable
**Aparece en:** todos los menús (DisenyoPesos, AdapterDam2, CentralActivity…).
```java
Intent intent = new Intent(this, DetalleActivity.class);   // desde una Activity
startActivity(intent);
```
- Dentro de una **clase anónima** (listener), `this` es el listener, no la Activity. Usa `MiActivity.this` o `getApplicationContext()`, como hacen los proyectos.
- Desde un **Fragment**: `new Intent(requireContext(), DetalleActivity.class)`.

> [!important] 📌 Importante
> La Activity destino **debe estar declarada** en el manifest (`<activity android:name=".DetalleActivity"/>`). Si no, `ActivityNotFoundException`. Con *File → New → Activity → Empty Views Activity*, Android Studio la declara por ti.

## Volver atrás / cerrar la pantalla ✅ Reutilizable
```java
btnVolver.setOnClickListener(v -> finish());   // EjercicioSpinner
```
`finish()` destruye la Activity actual y se ve la anterior (la pila de retroceso). No hace falta un `Intent` para volver.

## Abrir y cerrar la actual (que "Atrás" no vuelva aquí)
```java
startActivity(new Intent(this, CentralActivity.class));
finish();          // típico tras un login correcto
```

## Transición sencilla con `res/anim` ✅ Reutilizable
**Aparece en:** [AdapterDam2](../04-proyectos-profesor/AdapterDam2.md).
```xml
<!-- res/anim/fade_in.xml -->
<alpha xmlns:android="http://schemas.android.com/apk/res/android"
       android:fromAlpha="0" android:toAlpha="1" android:duration="500"/>
<!-- res/anim/fade_out.xml -->
<alpha xmlns:android="http://schemas.android.com/apk/res/android"
       android:fromAlpha="1" android:toAlpha="0" android:duration="500"/>
```
```java
startActivity(new Intent(this, Otra.class));
overridePendingTransition(R.anim.fade_in, R.anim.fade_out);  // (entra la nueva, sale la actual)
```

## Transición de contenido (`slide`/`explode`) ✅ Reutilizable
```java
// En la Activity DESTINO, antes de setContentView:
getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.slide));
// En la Activity ORIGEN, al lanzar:
startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
```

## Elemento compartido (la foto "vuela") ✅ Reutilizable
**Aparece en:** `GridViewDam2Activity` → `DetalleActivity` ([AdapterDam2](../04-proyectos-profesor/AdapterDam2.md)).
```java
// DESTINO
public static final String TRANSICION_FOTO = "foto";
…
ImageView iv = findViewById(R.id.ivDetalle);
ViewCompat.setTransitionName(iv, TRANSICION_FOTO);

// ORIGEN (vistaPulsada = la ImageView de la celda tocada)
ActivityOptionsCompat op = ActivityOptionsCompat.makeSceneTransitionAnimation(
        this, new androidx.core.util.Pair<>(vistaPulsada, DetalleActivity.TRANSICION_FOTO));
ActivityCompat.startActivity(this, intent, op.toBundle());
```
**Qué cambiar:** clases, ids y la constante. El `Pair` debe ser de `androidx.core.util`.

Más en [08 — Transiciones](../02-conceptos/08-transiciones.md) y [11 — Intent a fondo](../02-conceptos/11-intent-en-profundidad.md).
