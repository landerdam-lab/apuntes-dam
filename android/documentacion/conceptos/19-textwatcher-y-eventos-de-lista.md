---
tags:
  - android
  - concepto
---

# 19 — `TextWatcher` y eventos de un `ListView`

> Proyecto donde se usan: [EjemploDialogoPersonalizado](../proyectos/EjemploDialogoPersonalizado.md) (`RegisterActivity`).

## La idea general: los *listeners* (escuchadores)

En Android casi todo lo que ocurre en pantalla se programa con **listeners**: le dices a un componente *"cuando pase X, ejecuta este código"*. Ya conoces el más básico:

```java
boton.setOnClickListener(new View.OnClickListener() {
    @Override public void onClick(View v) { ... }
});
```

Un listener es una **interfaz** (ver [00-programacion-basica](00-programacion-basica.md)) cuyos métodos tú implementas, normalmente escribiendo una **clase anónima** ahí mismo. En `RegisterActivity` aparecen tres listeners más:

| Listener | Se dispara cuando... | Se pone con |
|---|---|---|
| `AdapterView.OnItemClickListener` | Pulsas **una vez** una fila de la lista | `lvUsers.setOnItemClickListener(...)` |
| `AdapterView.OnItemLongClickListener` | **Mantienes pulsada** una fila | `lvUsers.setOnItemLongClickListener(...)` |
| `TextWatcher` | **Cambia el texto** de un `EditText` | `etRePassword.addTextChangedListener(...)` |

---

## 1. Clic corto en una fila: cargar datos para editar

```java
lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Usuario usuario = usuariosAdapter.getItem(position);
        idUsuario = usuario.getId();
        etNombre.setText(usuario.getUsuario());
        etPassword.setText(usuario.getPassword());
        etRePassword.setText(usuario.getPassword());
    }
});
```

Los parámetros:
- **`position`** — el número de la fila pulsada (0, 1, 2...). Es lo que más se usa: `adaptador.getItem(position)` devuelve el objeto `Usuario` de esa fila.
- `view` — la vista de esa fila. `id` — el id de fila (en un `ArrayAdapter` suele coincidir con `position`, aquí no se usa). `parent` — el `ListView` en sí.

Qué hace: **guarda el `id` del usuario en la variable `idUsuario`** (empieza en `-1` = "ninguno seleccionado") y **vuelca sus datos en los campos de texto**. Después, el botón *Actualizar* usa ese `idUsuario` para saber a quién modificar.

> **Ojo:** si pulsas *Actualizar* sin haber seleccionado nada, se llama a `actualizar(-1, ...)`; `loadUsuarioById(-1)` devuelve `null` y no ocurre **nada, sin ningún aviso**. Sería más amable comprobar `if (idUsuario == -1)` y mostrar un `Toast`.

## 2. Clic largo: pedir confirmación y borrar

```java
lvUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ...  // mostrar el AlertDialog
        return true;
    }
});
```

La diferencia clave con el clic corto: **este método devuelve un `boolean`**.
- **`return true`** = "*yo ya he gestionado este clic largo*". El sistema **no** dispara además el clic corto.
- **`return false`** = "no lo he gestionado". Al soltar el dedo, Android lo tomaría también como clic corto y se cargarían los datos en el formulario.

El diálogo de confirmación se explica en [18-dialogos](18-dialogos.md).

## 3. `TextWatcher`: vigilar lo que se escribe

Un `TextWatcher` es un "vigilante" pegado a un `EditText`. Tiene **3 métodos obligatorios**:

```java
etRePassword.addTextChangedListener(new TextWatcher() {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        String password = etPassword.getText().toString();
        String repassword = etRePassword.getText().toString();
        if (!password.equals(repassword)) {
            btnRegistrarNuevo.setEnabled(false);
            etRePassword.setBackgroundColor(Color.RED);
        } else {
            btnRegistrarNuevo.setEnabled(true);
            etRePassword.setBackgroundColor(Color.WHITE);
        }
    }
});
```

| Método | Momento | Uso típico |
|---|---|---|
| `beforeTextChanged` | **Antes** de aplicar el cambio | Casi nunca |
| `onTextChanged` | **Durante** el cambio | Reaccionar al texto nuevo |
| `afterTextChanged` | **Después**: el `EditText` ya tiene el texto final | **El más usado** (validar) |

Como es una interfaz, hay que implementar los tres aunque solo uses uno; los otros se dejan **vacíos** (`{ }`). Se dispara **con cada letra** que se escribe o borra.

### Qué valida este ejemplo
Cada vez que cambia "Re-password" se compara con "Password":
- **No coinciden** → el botón *Nuevo* se desactiva (`setEnabled(false)`, queda gris) y el campo se pinta de rojo.
- **Coinciden** → se reactiva y vuelve a blanco.

### Limitaciones del ejemplo (útil como ejercicio de mejora)
1. **Solo vigila `etRePassword`.** Si escribes la repetición correcta y **luego cambias** "Password", nadie vuelve a comprobar: el botón sigue activo aunque ya no coincidan. Habría que añadir el mismo `TextWatcher` también a `etPassword`.
2. **Al abrir la pantalla**, ambos campos están vacíos, y `"".equals("")` es cierto: el botón *Nuevo* está **activo** y permite guardar un usuario con nombre y contraseña vacíos. Conviene comprobar también `user.isEmpty()`.
3. `Color.WHITE` fija un fondo blanco que **ignora el tema oscuro** de la app. Para respetar el tema se usaría un color de recursos.
4. Los usuarios repetidos **no se rechazan**: se puede registrar "Almi" dos veces. Solucionarlo requiere una restricción `UNIQUE` en la tabla o una consulta previa.
5. Las contraseñas se guardan **en texto plano**. Vale para un ejercicio; en una app real se guardaría un *hash* (nunca la contraseña).

## Patrón general: la clase anónima

Los tres listeners se escriben igual: `new Interfaz() { métodos }` dentro del propio argumento. Es una **clase anónima**: una clase sin nombre que se define y se usa en el mismo sitio, pensada para código que solo se necesita una vez. Desde ahí puedes leer los atributos de la `Activity` (`etNombre`, `usuariosAdapter`...) directamente.

## Ver también
- [07 — Adaptadores](07-adaptadores.md) — `getItem(position)` y cómo se rellena la lista.
- [18 — Diálogos](18-dialogos.md) — el `AlertDialog` que se abre en el clic largo.
- [01 — Fundamentos](01-fundamentos-bundle-intent-ciclo-vida.md) — `findViewById` y `onCreate`.

## 🏋️ Practica esto

- [Nivel 7 — ejercicio 7.4](../ejercicios/07-nivel-7-mejoras-del-proyecto.md) (arreglar el `TextWatcher` y la validación)
- [Nivel 3 — ejercicios 3.1 y 3.3](../ejercicios/03-nivel-3-adaptadores.md) (clic en una fila)
