---
tags:
  - android
  - concepto
  - tema/voz
  - tema/intents
aliases:
  - Voz
  - RecognizerIntent
  - Intent implícito
---

# 23 — Reconocimiento de voz e intents implícitos

> PDF: [4 — Voz](../03-pdfs/15-voz.md) · Proyecto: [EjemploDialogoPersonalizado → VozActivity](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) · Base: [11 — Intent a fondo](11-intent-en-profundidad.md)

## Concepto: intent implícito 📌 Importante

### Qué es
Un `Intent` que **no dice qué clase abrir**, sino **qué quieres hacer** (una *acción*) y, a veces, sobre qué datos. Android busca las apps que saben hacerlo (las que declaran ese `intent-filter`) y abre una, o te deja elegir.

| | Explícito | Implícito |
|---|---|---|
| Se crea con | `new Intent(this, OtraActivity.class)` | `new Intent(Intent.ACTION_VIEW, uri)` |
| Qué abre | Esa clase exacta de **tu** app | Cualquier app capaz (navegador, cámara, teléfono…) |
| En los proyectos | Casi todos los `startActivity` | `VozActivity` (voz y búsqueda web) |

### Ejemplos de acciones útiles ✅ Reutilizable
```java
// Abrir una web
startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.almi.eus")));

// Marcar un teléfono (no llama: abre el marcador; no necesita permiso)
startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:944000000")));

// Enviar un email
Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:profe@almi.eus"));
email.putExtra(Intent.EXTRA_SUBJECT, "Duda de Android");
startActivity(email);

// Compartir un texto (WhatsApp, Gmail…)
Intent compartir = new Intent(Intent.ACTION_SEND);
compartir.setType("text/plain");
compartir.putExtra(Intent.EXTRA_TEXT, "¡Mira mi app!");
startActivity(Intent.createChooser(compartir, "Compartir con"));

// Abrir un mapa
startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:43.263,-2.935?q=Almi")));
```

> [!warning] ⚠️ Cuidado: puede no haber ninguna app
> Si nadie atiende la acción, `startActivity` lanza **`ActivityNotFoundException`** y la app se cierra. Envuélvelo en `try/catch`. En emuladores sin Google Play pasa a menudo con la voz.

## Concepto: reconocimiento de voz (`RecognizerIntent`)

### Qué es
Un intent implícito especial: `ACTION_RECOGNIZE_SPEECH` abre el diálogo de dictado de Google, que escucha, transcribe y **devuelve** una lista de posibles textos. Como devuelve un resultado, se lanza con la **Activity Result API** ([22](22-camara-y-almacenamiento.md)).

### Ejemplo del proyecto
```java
vozLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), resultado -> {
    if (resultado.getResultCode() == RESULT_OK && resultado.getData() != null) {
        ArrayList<String> textos = resultado.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        if (textos != null && !textos.isEmpty()) editTexto.setText(textos.get(0));
    }
});
...
Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "AHORA PUEDES HABLAR .....");
vozLauncher.launch(intent);
```

### Explicación
| Extra | Valor | Para qué |
|---|---|---|
| `EXTRA_LANGUAGE_MODEL` | `LANGUAGE_MODEL_FREE_FORM` | Dictado libre (frente a `WEB_SEARCH`, optimizado para búsquedas cortas) |
| `EXTRA_PROMPT` | Texto | Lo que se ve en el diálogo mientras hablas |
| `EXTRA_LANGUAGE` (opcional) | `"es-ES"` | Forzar el idioma |
| `EXTRA_MAX_RESULTS` (opcional) | `int` | Cuántas alternativas devolver |

- `RESULT_OK`: el usuario habló y hubo resultado. Si cancela, llega `RESULT_CANCELED`.
- **No hace falta `RECORD_AUDIO`:** graba la app de Google, no la tuya.

### Codificar texto para una URL
```java
String consulta = URLEncoder.encode("¿qué es Android?", "UTF-8");   // "%C2%BFqu%C3%A9+es+Android%3F"
```
Sin codificar, los espacios, `?`, `&` o `#` romperían la URL.

### Ejemplo reutilizable: dictado a un `EditText` ✅ Reutilizable
```java
private final ActivityResultLauncher<Intent> dictado = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), r -> {
            if (r.getResultCode() == RESULT_OK && r.getData() != null) {
                ArrayList<String> t = r.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (t != null && !t.isEmpty()) etDestino.setText(t.get(0));
            }
        });

private void dictar() {
    Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            .putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
            .putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.habla_ahora));
    try { dictado.launch(i); }
    catch (ActivityNotFoundException e) {
        Toast.makeText(this, "Reconocimiento de voz no disponible", Toast.LENGTH_SHORT).show();
    }
}
```

### Errores comunes
| Error | Causa | Solución |
|---|---|---|
| `ActivityNotFoundException` | Emulador sin Google / sin navegador | Emulador con Google Play + `try/catch` |
| El texto sale en inglés | Idioma del sistema | `EXTRA_LANGUAGE = "es-ES"` |
| `NullPointerException` con `getData()` | El usuario canceló | Comprobar `RESULT_OK` y `!= null` |

## Relacionado
- [11 — Intent a fondo](11-intent-en-profundidad.md) · [22 — Activity Result API](22-camara-y-almacenamiento.md)
