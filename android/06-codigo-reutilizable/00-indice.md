---
tags:
  - android
  - reutilizable
  - indice
aliases:
  - Fragmentos de código que puedo reutilizar
  - Snippets
---

# Fragmentos de código que puedo reutilizar

Código **limpio y listo para copiar**, sacado de los proyectos del profesor y corregido donde el original tenía fallos. Cada fragmento indica:
- **Para qué sirve** y **en qué proyecto** aparece.
- **Código original** (resumido) → **código limpio**.
- **Qué cambiar** al pegarlo en otro proyecto.

| Categoría | Contenido |
|---|---|
| [01 — Botones y eventos](01-botones-y-eventos.md) | `setOnClickListener` (clase anónima y lambda), `android:onClick`, activar/desactivar, cambiar textos, pulsación larga |
| [02 — Cambiar de pantalla](02-cambiar-de-pantalla.md) | `Intent` explícito, cerrar pantalla, transiciones, *shared element* |
| [03 — Pasar datos](03-pasar-datos.md) | `putExtra`/`getXxxExtra`, `Bundle`, objetos `Serializable`, a fragmentos con `newInstance` |
| [04 — Mensajes, Toast y diálogos](04-mensajes-toast-dialogos.md) | `Toast`, "toast" personalizado, `AlertDialog` de confirmación, `DialogFragment`, `Snackbar`, popup |
| [05 — Formularios](05-formularios.md) | Leer `EditText`, validar vacíos, `setError`, texto → número sin cerrarse, `TextWatcher` |
| [06 — Listas y adaptadores](06-listas-y-adaptadores.md) | Modelo, `BaseAdapter` con ViewHolder, `Spinner` personalizado, `GridView`, `RecyclerView` |
| [07 — Base de datos Room](07-base-de-datos-room.md) | Entidad, DAO, Database Singleton, `AppExecutors` corregido, `LiveData` |
| [08 — Preferencias](08-preferencias.md) | `SharedPreferences` (no está en los proyectos): guardar y leer ajustes |
| [09 — Utilidades](09-utilidades.md) | Glide/Picasso, tareas en segundo plano, retraso con `Handler`, doble toque, fechas, `TypedArray` |
| [10 — Multimedia: sensores, cámara y voz](10-multimedia-sensores-camara-voz.md) | Sensor con ciclo de vida, foto con `FileProvider`, guardar en galería, dictado, intents implícitos |

> [!success] ✅ Convención
> Todo lo que está en estas notas lleva la marca ✅ Reutilizable. Donde pone `R.id.xxx`, `R.layout.xxx` o `MiActivity`, **cambia el nombre por el tuyo**.
