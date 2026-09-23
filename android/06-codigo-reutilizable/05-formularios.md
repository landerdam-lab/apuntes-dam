---
tags:
  - android
  - reutilizable
aliases:
  - Formularios
  - Validaciones
---

# 05 — Formularios y validaciones

## Leer el texto de un `EditText` ✅ Reutilizable
```java
EditText etNombre = findViewById(R.id.etNombre);
String nombre = etNombre.getText().toString().trim();   // trim() quita espacios al principio y al final
```
`getText()` devuelve un `Editable`, no un `String`: el `.toString()` es obligatorio (los proyectos a veces usan `+ ""`, que hace lo mismo).

## Validar campos vacíos con `setError` ✅ Reutilizable
No está en los proyectos: ninguno valida (ver [bugs](../01-guias/05-codigo-antiguo-y-malas-practicas.md)).
```java
private boolean validar(EditText... campos) {
    boolean ok = true;
    for (EditText c : campos) {
        if (c.getText().toString().trim().isEmpty()) {
            c.setError(getString(R.string.campo_obligatorio));   // globo rojo junto al campo
            ok = false;
        }
    }
    return ok;
}
// Uso:
btnGuardar.setOnClickListener(v -> {
    if (!validar(etNombre, etTipo, etPokedex)) return;   // sale sin guardar
    guardar();
});
```

## Convertir texto a número sin que se cierre la app ✅ Reutilizable
**Problema real:** `Integer.parseInt(etPokedex.getText().toString())` en [EjercicioPokemon](../04-proyectos-profesor/EjercicioPokemon.md) se cierra con `NumberFormatException` si el campo está vacío o tiene letras.
```java
private Integer leerEntero(EditText et) {
    try {
        return Integer.parseInt(et.getText().toString().trim());
    } catch (NumberFormatException e) {
        et.setError("Escribe un número");
        return null;
    }
}
// Uso:
Integer numero = leerEntero(etPokedex);
if (numero == null) return;
```
Y en el XML, para que salga el teclado numérico:
```xml
<EditText android:id="@+id/etPokedex" android:inputType="number" android:hint="@string/pokedex" … />
```
| `inputType` | Teclado |
|---|---|
| `text` | Normal |
| `textPersonName` | Nombres (mayúscula inicial) |
| `number` / `numberDecimal` / `numberSigned` | Números enteros / con decimales / con signo |
| `phone` | Teléfono |
| `textEmailAddress` | Con `@` |
| `textPassword` | Oculta lo que escribes (●●●) |
| `textUri` | URLs |
| `date` | Fechas |

> [!warning] ⚠️ Cuidado: `Integer.getInteger("12")` NO convierte texto a número
> Lee una *propiedad del sistema* llamada "12" y devuelve `null`. Aparece por error en `RegisterPokemon`. Lo correcto es `Integer.parseInt(...)` o `Integer.valueOf(...)`.

## Validar mientras se escribe (`TextWatcher`) ✅ Reutilizable
**Aparece en:** `RegisterActivity` de [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md).
```java
TextWatcher comprobar = new TextWatcher() {
    @Override public void afterTextChanged(Editable s) {
        boolean iguales = etPass.getText().toString().equals(etRePass.getText().toString());
        boolean lleno = !etUser.getText().toString().trim().isEmpty();
        btnRegistrar.setEnabled(iguales && lleno);
        etRePass.setError(iguales ? null : "Las contraseñas no coinciden");
    }
    @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) { }
    @Override public void onTextChanged(CharSequence s, int a, int b, int c) { }
};
// Mejora respecto al original: se escucha en LOS TRES campos
etUser.addTextChangedListener(comprobar);
etPass.addTextChangedListener(comprobar);
etRePass.addTextChangedListener(comprobar);
```

## Precargar un formulario para editar ✅ Reutilizable
**Aparece en:** `UpdatePokemon` ([EjercicioPokemon](../04-proyectos-profesor/EjercicioPokemon.md)).
```java
Pokemon p = (Pokemon) getIntent().getSerializableExtra("pokemon");
etNombre.setText(p.getNombre());
etPokedex.setText(String.valueOf(p.getPokedex()));   // int → String
btnActualizar.setOnClickListener(v -> {
    Integer n = leerEntero(etPokedex);
    if (n == null || !validar(etNombre)) return;
    p.setNombre(etNombre.getText().toString().trim());
    p.setPokedex(n);
    AppExecutors.getInstance().getDiskIO().execute(() -> db.pokemonDao().updatePokemon(p));
    finish();
});
```

## Limpiar el formulario tras guardar ✅ Reutilizable
```java
for (EditText et : new EditText[]{etNombre, etPass, etRePass}) et.setText("");
etNombre.requestFocus();
```

## Ocultar el teclado ✅ Reutilizable
```java
InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
imm.hideSoftInputFromWindow(etNombre.getWindowToken(), 0);
```
