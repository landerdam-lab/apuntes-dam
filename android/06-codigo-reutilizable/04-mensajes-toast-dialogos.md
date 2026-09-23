---
tags:
  - android
  - reutilizable
aliases:
  - Mensajes
  - Toast
  - AlertDialog
  - DialogFragment
  - Snackbar
---

# 04 — Mensajes: Toast, diálogos y Snackbar

## Toast ✅ Reutilizable
```java
Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();   // LENGTH_LONG ≈ 3,5 s
```
- Desde un Fragment: `requireContext()` en lugar de `this`.
- **Solo desde el hilo principal.** En otro hilo: `runOnUiThread(() -> Toast…)`.
- Olvidar `.show()` es el error más típico: no sale nada.

## "Toast" personalizado con `Dialog` ✅ Reutilizable
**Aparece en:** `MainActivity` de [DisenyoPesos](../04-proyectos-profesor/DisenyoPesos.md).
```java
private void toastPersonalizado(@DrawableRes int imagen, String texto) {
    View vista = getLayoutInflater().inflate(R.layout.toast_per, null);
    ((ImageView) vista.findViewById(R.id.ivToast)).setImageResource(imagen);
    ((TextView) vista.findViewById(R.id.tvToast)).setText(texto);

    Dialog dialogo = new Dialog(this);
    dialogo.setContentView(vista);
    if (dialogo.getWindow() != null) {
        dialogo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    dialogo.show();
    // Se cierra solo a los 2 s, como un Toast
    new Handler(Looper.getMainLooper()).postDelayed(() -> {
        if (dialogo.isShowing()) dialogo.dismiss();
    }, 2000);
}
```
**Necesita:** un layout `toast_per.xml` con `ivToast` y `tvToast`.

## AlertDialog de confirmación ✅ Reutilizable
**Aparece en:** `RegisterActivity` de [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md).
```java
new AlertDialog.Builder(this)                         // androidx.appcompat.app.AlertDialog
        .setTitle("Advertencia")
        .setMessage("¿Seguro que quieres borrar " + nombre + "?")
        .setPositiveButton("Sí", (d, w) -> borrar(id))
        .setNegativeButton("No", (d, w) -> d.dismiss())
        .setCancelable(false)                         // no se cierra tocando fuera
        .show();
```

### AlertDialog con lista de opciones
```java
String[] tipos = {"Agua", "Planta", "Fuego"};
new AlertDialog.Builder(this)
        .setTitle("Elige un tipo")
        .setItems(tipos, (d, which) -> filtrar(tipos[which]))
        .show();
```

## DialogFragment con layout propio (login) ✅ Reutilizable
**Aparece en:** `LoginDialogFrag` de [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md).
```java
public class LoginDialog extends DialogFragment {
    public interface OnLogin { void onLogin(String usuario, String pass); }
    private OnLogin callback;

    @Override public void onAttach(@NonNull Context c) { super.onAttach(c); callback = (OnLogin) c; }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inf, @Nullable ViewGroup cont, @Nullable Bundle s) {
        return inf.inflate(R.layout.dialog_personalizado, cont, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle s) {
        super.onViewCreated(v, s);
        EditText etUser = v.findViewById(R.id.etUser);
        EditText etPass = v.findViewById(R.id.etPassword);
        v.findViewById(R.id.btnAceptar).setOnClickListener(b -> {
            callback.onLogin(etUser.getText().toString(), etPass.getText().toString());
            dismiss();
        });
        v.findViewById(R.id.btnCancelar).setOnClickListener(b -> dismiss());
    }
}
// Mostrar: new LoginDialog().show(getSupportFragmentManager(), "login");
```
**Diferencia con el original:** aquí el diálogo **no** accede a la BD; avisa a la Activity con una interfaz. Es más fácil de reutilizar y de probar.

## Snackbar ✅ Reutilizable
**Aparece en:** [aaaa](../04-proyectos-profesor/aaaa.md) (FAB). Necesita `com.google.android.material`.
```java
Snackbar.make(findViewById(R.id.main), "Usuario borrado", Snackbar.LENGTH_LONG)
        .setAction("Deshacer", v -> restaurarUsuario())
        .show();
```
Frente al `Toast`: sale abajo, dentro de la app, y puede tener un **botón de acción**.

## Popup reutilizando un layout ✅ Reutilizable
**Aparece en:** `JugadorActivity` de [EjercicioAdaptadoresFinal](../04-proyectos-profesor/EjercicioAdaptadoresFinal.md).
```java
Dialog popup = new Dialog(this);
View v = getLayoutInflater().inflate(R.layout.popup_foto, null);
Picasso.get().load(url).into((ImageView) v.findViewById(R.id.ivPopup));
v.findViewById(R.id.ivPopup).setOnClickListener(x -> popup.dismiss());
popup.setContentView(v);
popup.show();
```

## ¿Cuál uso?
| Necesito… | Uso |
|---|---|
| Un aviso rápido que no requiere respuesta | `Toast` |
| Un aviso con opción de deshacer | `Snackbar` |
| Una pregunta Sí/No | `AlertDialog` |
| Un formulario flotante | `DialogFragment` |
| Un aviso con imagen propia | `Dialog` transparente |

Más: [18 — Diálogos](../02-conceptos/18-dialogos.md) · [04 — Toast personalizado](../02-conceptos/04-toast-personalizado.md).
