---
tags:
  - android
  - concepto
---

# `Intent`, a fondo

En [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) §3 se vio el uso práctico de `Intent` en tus proyectos (siempre Intents explícitos, para abrir una Activity concreta). Aquí se explica el mecanismo completo: qué es realmente un `Intent`, los dos tipos que existen, todas sus partes, y cómo el sistema operativo lo "resuelve".

## 1. ¿Qué es un `Intent`, conceptualmente?

Un `Intent` es un **mensaje de intención** que le mandas al sistema operativo Android, pidiéndole que haga algo — normalmente, "abre esta pantalla" o "quiero que alguna app haga esta acción". No es una llamada directa a un método de otra clase (como sería `new Detalles_Jugador()` en Java normal) — es un mensaje que **pasa por el sistema operativo**, y es el sistema quien decide qué componente concreto debe atenderlo.

Esta indirección (pasar por el sistema en vez de llamar directamente) es la que permite, por ejemplo, que una app pueda pedir "abre esta URL" sin saber de antemano qué navegador tiene instalado el usuario — Android es quien busca "algo que sepa abrir URLs" y lo lanza.

## 2. Los dos tipos de `Intent`

### 2.1. Intent explícito — el que usan todos tus proyectos

```java
Intent intent = new Intent(getApplicationContext(), Detalles_Jugador.class);
```
Le dices **exactamente** qué clase (componente) quieres que se ejecute: `Detalles_Jugador.class`. No hay ambigüedad ni búsqueda — el sistema abre esa clase directamente (si está declarada en el `AndroidManifest.xml`, ver [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) §4). Es lo que se usa siempre para navegar **dentro de tu propia app**, porque tú sabes perfectamente qué pantalla quieres abrir.

### 2.2. Intent implícito — para pedirle algo al sistema sin saber quién lo hará

```java
// (Ejemplo NO presente en tus proyectos actuales, mostrado como referencia)
Intent intent = new Intent(Intent.ACTION_VIEW);
intent.setData(Uri.parse("https://www.android.com"));
startActivity(intent);   // Android busca una app capaz de "VER" esa URL (un navegador) y la abre
```
Aquí no dices qué clase abrir — describes **una acción** (`ACTION_VIEW`, "quiero ver este dato") y unos **datos** (la URL), y es el sistema quien busca, entre todas las apps instaladas, cuál declaró en su manifiesto que sabe manejar ese tipo de acción/dato, y le ofrece al usuario elegir si hay varias candidatas (el típico menú "abrir con...").

Ninguno de tus proyectos usa Intents implícitos porque toda la navegación es interna a la propia app — pero es importante saber que existen, porque es el mecanismo detrás de acciones tan comunes como "compartir", "llamar por teléfono", "abrir el email", etc.

## 3. Las partes de un `Intent`

Aunque en tus proyectos solo se usan el **componente** y los **extras**, un `Intent` puede llevar más información:

| Parte | Para qué sirve | ¿Se usa en tus proyectos? |
|---|---|---|
| **Componente** (`ComponentName`) | La clase exacta a abrir (Intent explícito) | ✅ Siempre (`new Intent(contexto, Clase.class)`) |
| **Action** | Qué acción se quiere realizar (`ACTION_VIEW`, `ACTION_SEND`...) | ❌ No usado |
| **Data** (`Uri`) | Sobre qué dato actuar (una URL, un número de teléfono...) | ❌ No usado |
| **Category** | Información adicional sobre cómo manejar la acción | ❌ No usado (salvo `CATEGORY_LAUNCHER` en el manifiesto, que no es parte de un Intent en código sino de un `<intent-filter>`) |
| **Extras** (`Bundle`) | Datos adicionales que viajan con el Intent | ✅ Todo el rato (`putExtra`, `putExtras`, ver más abajo) |
| **Flags** | Modifican cómo se comporta el lanzamiento (p. ej. limpiar la pila de Activities) | ❌ No usado |

## 4. Los "extras" en detalle: cómo viaja realmente el `Bundle`

```java
Intent intent = new Intent(getApplicationContext(), Detalles_Jugador.class);
Bundle bundle = new Bundle();
bundle.putSerializable("jugadorSeleccionado", jugador);
intent.putExtras(bundle);
startActivity(intent);
```

Internamente, **todo `Intent` lleva ya un `Bundle` propio** para sus "extras" — cuando escribes `intent.putExtra("clave", valor)` directamente (sin crear tú un `Bundle` a mano), en realidad Android está guardando ese valor dentro de ese `Bundle` interno del `Intent`. `intent.putExtras(bundle)` simplemente **fusiona** un `Bundle` externo ya construido dentro de ese `Bundle` interno. Por eso ambas formas —crear un `Bundle` aparte, o usar `intent.putExtra` directamente— acaban en el mismo sitio y se recuperan exactamente igual con `getIntent().getXxxExtra("clave")`.

### ¿Por qué hace falta serializar los datos para que "viajen"?

Cuando lanzas una nueva `Activity`, en muchos casos (y siempre potencialmente) esa nueva Activity puede llegar a ejecutarse **en un proceso del sistema operativo distinto** (aunque normalmente sea el mismo proceso de tu app, Android lo trata de forma uniforme por si no lo fuera). Los objetos Java normales **no pueden cruzar la frontera entre procesos tal cual** (una referencia en memoria de un proceso no significa nada en otro proceso) — por eso los datos que viajan en un `Intent` tienen que poder **convertirse a una secuencia de bytes** (serializarse) para poder transmitirse, y reconstruirse al otro lado. Es exactamente el motivo por el que `Jugador`, `Equipo` y `Liga` implementan `Serializable` (ver [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) §2.3).

## 5. Resolución del Intent — qué hace el sistema exactamente

1. Llamas a `startActivity(intent)`.
2. El sistema mira el `Intent`: si es **explícito**, va directo al componente indicado, siempre que esté declarado en el `AndroidManifest.xml` (si no, lanza `ActivityNotFoundException`, un crash típico al olvidar declarar una Activity nueva).
3. Si es **implícito**, el sistema busca entre los `<intent-filter>` de **todas** las apps instaladas cuál coincide con la `action`/`data`/`category` pedidas, y lanza esa (o pregunta al usuario si hay varias).
4. El sistema crea (o reutiliza, según el `launchMode`, ver [10-activity-en-profundidad](10-activity-en-profundidad.md) §6) una instancia de la Activity destino, y llama a su `onCreate(Bundle savedInstanceState)`.
5. Dentro de ese `onCreate`, `getIntent()` te devuelve exactamente el `Intent` que se usó para lanzar esta Activity — de ahí sacas los extras.

## 6. El segundo `Bundle`: opciones de lanzamiento, no confundir con los extras

```java
ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
startActivity(intent, options.toBundle());
```

Este `options.toBundle()` es un `Bundle` **completamente distinto** al de los extras — no contiene datos de tu aplicación, sino **instrucciones para el sistema de ventanas** sobre cómo animar la transición visual entre pantallas (ver [08-transiciones](08-transiciones.md)). Es fácil confundirlos porque ambos son objetos `Bundle` y ambos se pasan cerca de `startActivity`, pero cumplen funciones totalmente distintas: uno es "qué datos le paso a la siguiente pantalla" (extras) y el otro es "cómo quiero que se vea la animación de apertura" (opciones de actividad).

## Ver también
- [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) — el uso práctico básico.
- [10-activity-en-profundidad](10-activity-en-profundidad.md) — qué le pasa a la Activity destino una vez que el Intent la lanza.
- [08-transiciones](08-transiciones.md) — el `Bundle` de opciones de animación en detalle.
