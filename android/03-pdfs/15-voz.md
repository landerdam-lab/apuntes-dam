---
tags:
  - android
  - pdf
  - bloque/2
  - tema/voz
aliases:
  - 4-Voz
---

# PDF 4 (bloque 2) — Voz

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/4-Voz.pdf` · **10 páginas** · **Bloque 2**
> **Proyecto:** [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) → `VozActivity` + botón `btnVoz` de `CentralActivity`
> **Concepto:** [23 — Voz e intents implícitos](../02-conceptos/23-voz-e-intents-implicitos.md) · **Reutilizable:** [Multimedia](../06-codigo-reutilizable/10-multimedia-sensores-camara-voz.md)

## Qué explica

1. **Objetivo:** una búsqueda por voz en el navegador. Se usa el **servicio de reconocimiento de voz de Android** y el texto se pasa a Chrome.
2. Layout `activity_voz.xml`: botón **Voz**, `EditText editTexto` (hint "Pulsa el botón Voz…") y botón **Web Search**.
3. Botón **Voz** en `CentralActivity`.
4. **Reconocimiento:**
   - `Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)` + `EXTRA_LANGUAGE_MODEL = LANGUAGE_MODEL_FREE_FORM` (dictado libre) + `EXTRA_PROMPT` ("Ahora puedes hablar…").
   - Lanzado con la **Activity Result API** y el contrato genérico `StartActivityForResult`.
   - En el callback: comprobar `RESULT_OK` y `getData() != null`, leer `getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)` (lista de transcripciones de más a menos probable) y poner la primera en el `EditText`.
5. **No hace falta `RECORD_AUDIO`:** quien graba es la app de reconocimiento, no la nuestra.
6. **Búsqueda web:** `URLEncoder.encode(texto, "UTF-8")` → `Uri.parse("https://www.google.com/search?q=" + consulta)` → `new Intent(Intent.ACTION_VIEW, uri)`.

## Diferencias con el proyecto

| PDF | Proyecto |
|---|---|
| `catch (UnsupportedEncodingException e) { e.printStackTrace(); }` | `catch (Exception e) { throw new RuntimeException(e); }` |
| `EditText` vacío | `android:text="Texto"` de inicio |
| Botones `wrap_content` | Botón Voz `match_parent` |

> [!warning] ⚠️ Cuidado en el emulador
> Si la imagen del emulador no trae Google (app de voz), `launch()` lanza `ActivityNotFoundException` y la app se cierra. Usa un emulador **con Google Play** o protege la llamada con `try/catch`.

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/4-Voz.pdf)
