---
tags:
  - android
  - concepto
  - tema/menus
aliases:
  - Menús
  - Toolbar
  - onCreateOptionsMenu
---

# 26 — Menús de opciones y Toolbar

> PDF: [8 — Fragmentos](../03-pdfs/11-fragmentos.md) (parte final) · Proyectos: [EjemploFragmentos2](../04-proyectos-profesor/EjemploFragmentos2.md), [aaaa](../04-proyectos-profesor/aaaa.md)

## Concepto: Toolbar 📌 Importante

### Qué es
La barra superior de la app (título, iconos y menú ⋮). Todos los proyectos usan un tema **`NoActionBar`**, sin barra de serie, así que si quieres una tienes que **ponerla en el layout** y "nombrarla" barra de la app con `setSupportActionBar`.

### Ejemplo del proyecto (EjemploFragmentos2)
```xml
<com.google.android.material.appbar.MaterialToolbar
    android:id="@+id/toolbar"
    android:layout_width="match_parent"
    android:layout_height="?attr/actionBarSize"
    android:background="?attr/colorPrimary"
    android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
    app:title="Fragmentos"
    app:titleTextColor="?attr/colorOnPrimary"/>
```
```java
MaterialToolbar toolbar = findViewById(R.id.toolbar);
setSupportActionBar(toolbar);    // desde ahora, los menús de opciones se pintan aquí
```
- `?attr/actionBarSize`: la altura estándar del tema (56dp en móvil).
- `?attr/colorPrimary`: el color principal **del tema** (cambia solo en modo oscuro).

## Concepto: menú de opciones

### Qué es
Los elementos que aparecen en la Toolbar: como icono (si caben) o dentro del ⋮. Se definen en **XML** (`res/menu/`) y se gestionan con **dos métodos** que Android llama solo.

### Paso a paso ✅ Reutilizable
**1. `res/menu/menu_main.xml`** (clic derecho en `res` → *New → Android Resource File → Menu*)
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto">
    <item android:id="@+id/accion_buscar"
          android:icon="@android:drawable/ic_menu_search"
          android:title="@string/buscar"
          app:showAsAction="ifRoom"/>      <!-- icono en la barra si hay sitio -->
    <item android:id="@+id/accion_ajustes"
          android:title="@string/ajustes"
          app:showAsAction="never"/>       <!-- siempre dentro de ⋮ -->
</menu>
```
**2. Inflar el menú**
```java
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;                          // true = mostrar el menú
}
```
**3. Reaccionar a la opción elegida**
```java
@Override
public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.accion_buscar) { buscar(); return true; }   // true = "lo he gestionado yo"
    if (id == R.id.accion_ajustes) { abrirAjustes(); return true; }
    return super.onOptionsItemSelected(item);                  // el resto (p. ej. la flecha ←), a Android
}
```

| `app:showAsAction` | Resultado |
|---|---|
| `never` | Siempre dentro de ⋮ (EjemploFragmentos2) |
| `ifRoom` | Icono en la barra si cabe; si no, en ⋮ |
| `always` | Siempre en la barra (con cuidado: puede no caber) |
| `withText` | Icono + texto (combinado: `ifRoom\|withText`) |

> [!warning] ⚠️ Cuidado: `switch (item.getItemId())` con `case R.id.x:`
> Desde AGP 8, los ids de `R` **ya no son constantes** (`final`) y un `switch` sobre ellos no compila (*"constant expression required"*). Usa `if / else if`, como hacen los proyectos.

### Otros menús (no están en los proyectos)
- **Menú contextual** (pulsación larga en una vista): `registerForContextMenu(view)` + `onCreateContextMenu` + `onContextItemSelected`.
- **PopupMenu** (menú flotante junto a un botón): `new PopupMenu(this, boton)`, `inflate`, `setOnMenuItemClickListener`, `show()`.

### Errores comunes
| Error | Causa | Solución |
|---|---|---|
| El menú no aparece | Falta `setSupportActionBar` o `onCreateOptionsMenu` devuelve `false` | Llamar a `setSupportActionBar(toolbar)` y devolver `true` |
| `This Activity already has an action bar supplied by the window decor` | El tema tiene ActionBar **y** llamas a `setSupportActionBar` | Tema `NoActionBar` |
| El texto de la barra no se ve | Color de texto igual al fondo | `titleTextColor="?attr/colorOnPrimary"` |

## Relacionado
- [16 — Fragmentos](16-fragmentos.md) · [27 — Navigation](27-navigation-viewbinding-viewmodel.md) (menús conectados a la navegación)
