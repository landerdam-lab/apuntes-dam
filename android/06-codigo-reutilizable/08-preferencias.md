---
tags:
  - android
  - reutilizable
aliases:
  - SharedPreferences
  - Preferencias
---

# 08 — Preferencias (`SharedPreferences`)

> [!note] No aparece en ningún proyecto ni PDF
> Se incluye porque es la forma más sencilla de **recordar datos pequeños** (usuario logueado, modo oscuro, última búsqueda…) y suele salir en proyectos y exámenes. Para listas de objetos usa [Room](07-base-de-datos-room.md).

## Qué es
Un pequeño "diccionario" clave → valor que Android guarda en un archivo XML privado de la app. **Sobrevive al cerrar la app**, pero se borra al desinstalarla.

| ¿Qué guardar? | SharedPreferences | Room |
|---|---|---|
| "Recordarme", nombre del usuario, ajustes | ✅ | ❌ (demasiado) |
| Lista de usuarios, Pokémon, notas | ❌ | ✅ |

## Guardar ✅ Reutilizable
```java
SharedPreferences prefs = getSharedPreferences("ajustes", MODE_PRIVATE);   // "ajustes" = nombre del archivo
prefs.edit()
     .putString("usuario", "Ane")
     .putBoolean("recordar", true)
     .putInt("nivel", 3)
     .apply();                     // apply(): en segundo plano · commit(): espera y devuelve boolean
```

## Leer ✅ Reutilizable
```java
SharedPreferences prefs = getSharedPreferences("ajustes", MODE_PRIVATE);
String usuario = prefs.getString("usuario", "");       // "" si no existe
boolean recordar = prefs.getBoolean("recordar", false);
int nivel = prefs.getInt("nivel", 1);
```

## Borrar ✅ Reutilizable
```java
prefs.edit().remove("usuario").apply();   // una clave
prefs.edit().clear().apply();             // todo (p. ej. "Cerrar sesión")
```

## Ejemplo aplicado: "Recordarme" en el login de EjemploDialogoPersonalizado ✅ Reutilizable
```java
// Tras un login correcto (en el hilo principal):
getSharedPreferences("sesion", MODE_PRIVATE).edit().putString("usuario", nombre).apply();

// En MainActivity.onCreate: si ya hay sesión, saltar directamente a CentralActivity
String guardado = getSharedPreferences("sesion", MODE_PRIVATE).getString("usuario", null);
if (guardado != null) {
    startActivity(new Intent(this, CentralActivity.class));
    finish();
}
```

## Errores comunes
| Error | Solución |
|---|---|
| No se guarda nada | Falta `.apply()` o `.commit()` al final |
| `ClassCastException` al leer | Guardaste un `int` y lees con `getString` (o al revés): usa el mismo tipo |
| Datos distintos en cada pantalla | Nombre de archivo distinto en `getSharedPreferences("…")`: usa una constante |
| Guardar contraseñas en claro | No lo hagas; guarda solo el nombre o un token |
