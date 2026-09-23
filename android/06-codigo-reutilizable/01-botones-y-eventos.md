---
tags:
  - android
  - reutilizable
aliases:
  - Botones y eventos
---

# 01 — Botones y eventos

## `setOnClickListener` con clase anónima ✅ Reutilizable
**Para qué:** reaccionar al clic de un botón. **Aparece en:** todos los proyectos (p. ej. `MainActivity` de [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md)).
```java
Button btnGuardar = findViewById(R.id.btnGuardar);
btnGuardar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // lo que pasa al pulsar
    }
});
```

## Lo mismo con lambda (más corto) ✅ Reutilizable
```java
btnGuardar.setOnClickListener(v -> guardar());
```
Las lambdas sirven para cualquier interfaz con **un solo método** (`OnClickListener`, `Runnable`…). Si la interfaz tiene varios métodos (`TextWatcher`, `SensorEventListener`), hay que usar la clase anónima.

## En una línea, sin variable (estilo de los proyectos)
```java
((Button) findViewById(R.id.btnPrincipal)).setOnClickListener(v -> abrir());
// El casteo (Button) no hace falta: setOnClickListener existe en cualquier View
findViewById(R.id.btnPrincipal).setOnClickListener(v -> abrir());
```

## `android:onClick` en el XML ✅ Reutilizable
```xml
<Button android:id="@+id/btnSaludar" android:onClick="saludar" … />
```
```java
// Debe ser public, void, y recibir un View. Si no, la app se cierra al pulsar.
public void saludar(View v) {
    Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show();
}
```
> [!warning] ⚠️ Cuidado
> `android:onClick` está desaconsejado: si cambias el nombre del método, **compila igual** y falla al pulsar. Además, no funciona con fragmentos. Los proyectos del curso no lo usan.

## Un mismo listener para varios botones ✅ Reutilizable
```java
View.OnClickListener menu = v -> {
    int id = v.getId();
    if (id == R.id.btnToast) mostrarToast();
    else if (id == R.id.btnAsynctask) startActivity(new Intent(this, EjemploAsynctask.class));
};
findViewById(R.id.btnToast).setOnClickListener(menu);
findViewById(R.id.btnAsynctask).setOnClickListener(menu);
```
Útil en el menú de 6 botones de DisenyoPesos.

## Activar/desactivar un botón y cambiar textos ✅ Reutilizable
**Aparece en:** `RegisterActivity` de [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) (`btnRegistrarNuevo.setEnabled(...)`).
```java
btnEnviar.setEnabled(false);                      // gris y no se puede pulsar
btnEnviar.setEnabled(!etNombre.getText().toString().trim().isEmpty());
tvResultado.setText(getString(R.string.hola, nombre));   // strings.xml: <string name="hola">Hola, %1$s</string>
tvResultado.setTextColor(Color.RED);
tvResultado.setVisibility(View.GONE);             // VISIBLE / INVISIBLE (ocupa hueco) / GONE (no ocupa)
```

## Clic y pulsación larga en listas ✅ Reutilizable
**Aparece en:** [EjercicioPokemon](../04-proyectos-profesor/EjercicioPokemon.md), [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md).
```java
lista.setOnItemClickListener((parent, view, position, id) -> {
    Elemento e = (Elemento) parent.getItemAtPosition(position);
    abrirDetalle(e);
});
lista.setOnItemLongClickListener((parent, view, position, id) -> {
    confirmarBorrado((Elemento) parent.getItemAtPosition(position));
    return true;   // true = consumido: NO se dispara también el clic normal
});
```

**Qué cambiar:** ids de los botones, nombres de los métodos y la clase `Elemento`.
