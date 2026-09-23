---
tags:
  - android
  - concepto
---

# Antes de empezar: conceptos básicos de programación (Java)

Este documento explica, **sin dar nada por hecho**, el vocabulario de programación que vas a encontrar en todos los demás documentos y en el código real. Si ya sabes programar (aunque sea en otro lenguaje), puedes saltártelo e ir directamente a [09-instalacion-estructura-proyecto](09-instalacion-estructura-proyecto.md). Si es tu primera vez viendo código, **empieza por aquí**.

## 1. ¿Qué es "programar"?

Programar es escribir una lista de instrucciones, muy precisas y en un idioma que el ordenador entiende, para que haga algo concreto. Es como una receta de cocina: una serie de pasos, en orden, que si sigues exactamente producen un resultado (un pastel; o, aquí, una pantalla con un botón que hace algo al pulsarlo).

El "idioma" que se usa en estos proyectos es **Java**. Un fichero de código Java tiene la extensión `.java`, y cuando lo escribes, tiene que seguir unas reglas estrictas de gramática (la **sintaxis**) — si te falta una llave `}` o un punto y coma `;`, el programa ni siquiera llega a ejecutarse: primero se **compila** (el ordenador traduce tu texto a algo que la máquina puede ejecutar) y, si hay un error de sintaxis, la compilación falla antes de que la app llegue a arrancar.

## 2. Variables: cajas con una etiqueta que guardan un valor

```java
int edad = 25;
String nombre = "Ana";
boolean activo = true;
```

Una **variable** es un hueco en la memoria del ordenador donde guardas un valor, con un nombre (la etiqueta) para poder usarlo luego. Cada variable tiene un **tipo**, que dice qué clase de valor puede guardar:

| Tipo | Guarda | Ejemplo |
|---|---|---|
| `int` | un número entero | `int edad = 25;` |
| `float` / `double` | un número con decimales | `float precio = 19.99f;` |
| `String` | texto | `String nombre = "Ana";` |
| `boolean` | verdadero o falso, nada más | `boolean activo = true;` |
| `int[]` / `ArrayList<...>` | una colección de varios valores (ver §8) | `ArrayList<String> nombres` |

`nombreDeVariable = valor;` es una **asignación**: mete ese valor en esa caja (si la caja ya tenía algo, lo sustituye). Puedes leer una variable en cualquier otro punto del código posterior a que se le haya asignado un valor, usando su nombre.

## 3. Métodos (o "funciones"): una receta con nombre, reutilizable

```java
public int sumar(int a, int b) {
    int resultado = a + b;
    return resultado;
}
```

Un **método** es un bloque de instrucciones con un nombre, que puedes "llamar" (ejecutar) desde otro sitio del código las veces que quieras, sin tener que reescribir esas instrucciones cada vez. Las piezas:
- `sumar` — el nombre del método.
- `(int a, int b)` — los **parámetros**: los datos de entrada que el método necesita para funcionar, cada uno con su tipo y su nombre. Al llamar al método (`sumar(3, 5)`), esos valores (`3`, `5`) se copian dentro de `a` y `b`.
- `int` (antes del nombre) — el tipo del valor que el método **devuelve** al terminar (con la palabra `return`). Si un método no devuelve nada, se declara `void` en vez de un tipo.
- `{ ... }` — el cuerpo del método: las instrucciones que se ejecutan, en orden, cada vez que se le llama.

```java
int total = sumar(3, 5);   // "llamar" al método: total pasa a valer 8
```

## 4. `if` / `for` — decisiones y repeticiones

```java
if (edad >= 18) {
    // esto se ejecuta SOLO si la condición es verdadera
} else {
    // esto se ejecuta si NO lo es
}
```
`if (condición) { ... }` ejecuta ese bloque de código **solo si** la condición entre paréntesis es verdadera (`true`). El `else` (opcional) es lo que pasa si no lo es.

```java
for (int i = 0; i < 5; i++) {
    // esto se repite 5 veces, con i valiendo 0, 1, 2, 3, 4
}
```
`for (inicio; condición; paso)` repite el bloque mientras la condición sea verdadera, ejecutando "paso" (normalmente `i++`, que significa "suma 1 a `i`") después de cada vuelta. Es la forma de decirle al ordenador "haz esto muchas veces" sin copiar y pegar el mismo código.

## 5. Clases y objetos: el plano y la casa construida

Este es probablemente el concepto más importante de todos, y el que más aparece en la documentación (`Activity`, `Fragment`, `Adapter`... todo son clases).

Una **clase** es un **plano** o **molde**: describe qué datos va a tener algo y qué es capaz de hacer, pero no es "la cosa" en sí. Un **objeto** (también llamado **instancia**) es una "cosa real" construida siguiendo ese plano.

```java
public class Persona {
    String nombre;    // ATRIBUTO (o "campo"): un dato que cada Persona tiene
    int edad;

    void saludar() {   // MÉTODO: algo que cada Persona sabe hacer
        System.out.println("Hola, soy " + nombre);
    }
}
```
```java
Persona ana = new Persona();   // se CREA un objeto (una "instancia") siguiendo el plano "Persona"
ana.nombre = "Ana";             // se rellena su atributo
ana.saludar();                  // se llama a su método
```

- **Clase** (`Persona`) — el plano: define que "toda Persona tiene un nombre y una edad, y sabe saludar".
- **`new Persona()`** — la palabra clave `new` **construye** (crea en memoria) un objeto nuevo siguiendo ese plano.
- **`ana`** — una variable que guarda **una** Persona concreta (un objeto). Puedes crear tantos objetos `Persona` como quieras a partir del mismo plano (`Persona pedro = new Persona();`, `Persona luis = new Persona();`...), cada uno con sus propios valores.
- **Atributos** (o **campos**) — las variables que pertenecen a la clase, y que cada objeto tiene con su propio valor (el nombre de `ana` es distinto del nombre de `pedro`).
- **Métodos** — lo que un objeto de esa clase sabe hacer (llamar a `ana.saludar()` solo saluda "por" `ana`, no por todas las Personas).

### El constructor

```java
public class Persona {
    String nombre;
    int edad;

    public Persona(String nombre, int edad) {   // CONSTRUCTOR
        this.nombre = nombre;
        this.edad = edad;
    }
}
```
```java
Persona ana = new Persona("Ana", 25);
```
El **constructor** es un método especial (mismo nombre que la clase, sin tipo de retorno) que se ejecuta automáticamente **al crear** el objeto con `new`, y sirve normalmente para rellenar sus atributos desde el principio, en vez de asignarlos uno a uno después.

### `this`

Dentro de una clase, `this` significa **"este objeto concreto, el que está ejecutando este código ahora mismo"**. Se usa sobre todo cuando un parámetro tiene el mismo nombre que un atributo (`this.nombre = nombre;` significa "el atributo `nombre` DE ESTE OBJETO = el parámetro que me acaban de pasar").

## 6. Herencia (`extends`): una clase que parte de otra y añade cosas

```java
public class Animal {
    void comer() { ... }
}

public class Perro extends Animal {
    void ladrar() { ... }
}
```
`Perro extends Animal` significa "`Perro` **hereda** todo lo de `Animal`" — un objeto `Perro` tiene automáticamente el método `comer()` (sin tener que reescribirlo) y **además** tiene su propio método `ladrar()`. Es la forma de reutilizar código: en vez de copiar y pegar, una clase "hija" (`Perro`) parte de una clase "padre" (`Animal`) y solo añade o cambia lo que necesita.

Esto es exactamente lo que pasa en `public class MainActivity extends AppCompatActivity` (ver [10-activity-en-profundidad](10-activity-en-profundidad.md)): tu `MainActivity` hereda **todo** el comportamiento ya construido de `AppCompatActivity` (gestionar una pantalla, su ciclo de vida, etc.) y tú solo añades lo específico de tu pantalla.

### `@Override`

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ...
}
```
`@Override` (una **anotación**, un aviso especial para el compilador) indica: "este método ya existía en la clase padre, y aquí lo estoy **reemplazando** con mi propia versión". `super.onCreate(...)` significa "antes de hacer lo mío, ejecuta también la versión original del padre" — así no pierdes lo que la clase padre ya hacía, solo añades tu comportamiento encima.

## 7. Interfaces (`implements`): una promesa de qué métodos va a tener algo

```java
public interface IControlFragmentos {
    void cambiarTexto(String texto);
}
```
```java
public class MainActivity extends AppCompatActivity implements IControlFragmentos {
    @Override
    public void cambiarTexto(String texto) {
        // aquí SÍ hay que escribir el código de verdad
    }
}
```
Una **interfaz** es como un **contrato**: dice "cualquier clase que me implemente **promete** tener estos métodos", pero no dice **cómo** deben funcionar por dentro — eso lo decide cada clase que la implementa. `implements IControlFragmentos` obliga a `MainActivity` a escribir el método `cambiarTexto(String texto)` (si no lo hiciera, el código no compilaría).

La diferencia con herencia (`extends`): con `extends` heredas código ya hecho de una clase padre; con `implements` solo heredas la **obligación** de escribir ciertos métodos tú mismo. Es la técnica que usan todos los proyectos con `Fragment` (ver [16-fragmentos](16-fragmentos.md)) para que un fragmento pueda "avisar" a la Activity que lo contiene sin depender de qué Activity concreta sea.

## 8. Colecciones: `ArrayList`, una lista que crece

```java
ArrayList<String> nombres = new ArrayList<>();
nombres.add("Ana");
nombres.add("Luis");
String primero = nombres.get(0);   // "Ana"
int cuantos = nombres.size();       // 2
```
Un `ArrayList` es una **lista** de elementos que puede crecer o encogerse mientras el programa se ejecuta (a diferencia de un array normal `String[]`, de tamaño fijo). El `<String>` entre los símbolos "menor que"/"mayor que" es un **genérico**: le dice a Java "esta lista concreta solo va a guardar `String`" — podrías tener un `ArrayList<Persona>`, un `ArrayList<Integer>`, etc., cambiando solo lo que hay dentro de `<...>`.

- `.add(elemento)` — añade un elemento al final.
- `.get(posicion)` — devuelve el elemento en esa posición (empezando a contar en `0`, no en `1`).
- `.size()` — cuántos elementos hay ahora mismo.

## 9. `public` / `private`: quién puede tocar cada cosa

```java
public class Persona {
    private String nombre;    // solo accesible DESDE DENTRO de esta clase

    public String getNombre() {   // accesible desde cualquier sitio
        return nombre;
    }
}
```
- **`public`** — accesible desde cualquier otra clase.
- **`private`** — accesible **solo** desde dentro de la propia clase donde se declaró. Es habitual declarar los atributos `private` y ofrecer métodos `public` (`getNombre()`/`setNombre(...)`, llamados **getters/setters**) para controlar cómo se lee o modifica ese dato desde fuera, en vez de dejar que cualquiera lo cambie directamente.

## 10. `static`: pertenece a la clase entera, no a un objeto concreto

```java
public class FragmentoMedio extends Fragment {
    public static FragmentoMedio newInstance(Bundle argumentos) { ... }
}
```
```java
FragmentoMedio.newInstance(bundle);   // se llama SOBRE LA CLASE, sin haber creado ningún objeto antes
```
Un método (o campo) `static` no pertenece a ningún objeto concreto — pertenece a la clase en sí. Por eso se llama como `Clase.metodo(...)` en vez de `objeto.metodo(...)`: no hace falta (ni se puede) tener ya un objeto creado para usarlo. Se usa mucho para "métodos de fábrica" que construyen y devuelven un objeto nuevo, como el patrón `newInstance(...)` explicado en [16-fragmentos](16-fragmentos.md).

## 11. Paquetes (`package`) e importaciones (`import`)

```java
package com.example.fragmentosnombres.fragmentos;

import android.os.Bundle;
import com.example.fragmentosnombres.R;
```
Un **paquete** es una carpeta que agrupa clases relacionadas (evita, entre otras cosas, que dos clases con el mismo nombre de proyectos distintos choquen entre sí). La primera línea de cada fichero `.java` declara a qué paquete pertenece esa clase. `import` le dice al fichero "voy a usar esta clase, que vive en otro paquete" — sin el `import` correspondiente, tendrías que escribir el nombre completo (`android.os.Bundle`) cada vez en vez de solo `Bundle`.

## 12. Casting: "trata este objeto como si fuera de otro tipo"

```java
Object algo = "Hola";
String texto = (String) algo;   // CASTING: "trátalo como String"
```
Un **cast** (`(Tipo) valor`) le dice a Java "sé que esto es en realidad de este otro tipo, trátalo como tal". Aparece mucho en Android porque ciertos métodos genéricos devuelven un tipo muy general (`Object`, o el `Context` que se ve en [10-activity-en-profundidad](10-activity-en-profundidad.md) §4) y tú sabes, por el contexto, qué tipo concreto es en realidad:
```java
this.mainActivity = (IControlFragmentos) context;
```
Aquí se le dice a Java "sé que este `Context` en realidad es (además) algo que implementa `IControlFragmentos` — trátalo como tal a partir de ahora". Si el objeto real **no** fuera de ese tipo, el cast falla en tiempo de ejecución con un error (`ClassCastException`).

## 13. `null` y `NullPointerException` (el error más común de todos)

```java
String texto = null;   // esta variable "no apunta a ningún objeto"
texto.length();          // 💥 NullPointerException: no hay ningún objeto ahí para preguntarle su longitud
```
`null` es un valor especial que significa **"esta variable no contiene ningún objeto todavía"** (una caja vacía). Cuando intentas usar un método sobre una variable que vale `null` (como `texto.length()` arriba), el programa **crashea** con un `NullPointerException` (abreviado "NPE") — es, con diferencia, el error más frecuente en Android, y aparece explicado en casi todos los documentos de proyecto de esta carpeta (por ejemplo, cuando `findViewById` no encuentra el id que le pides, devuelve `null` en silencio, y el crash aparece más tarde, al intentar usar esa vista).

## 14. Comentarios en el código

```java
// esto es un comentario de una sola línea, Java lo ignora al ejecutar

/*
   esto es un comentario de varias líneas,
   también ignorado
*/
```
Un **comentario** es texto que escribes en el código **para las personas que lo leen**, no para el ordenador — Java lo ignora por completo al compilar/ejecutar. Se usa para explicar el "por qué" de algo que no sea obvio con solo leer el código. En este proyecto de documentación, se han añadido comentarios de este tipo en los ficheros `.java` de tus proyectos reales, explicando en lenguaje sencillo qué hace cada parte.

## 15. Cuatro cosas más que verás constantemente

### 15.1. `==` frente a `.equals()`
```java
String a = "Ana";
String b = new String("Ana");
a == b        // false: son DOS objetos distintos
a.equals(b)   // true : tienen el MISMO contenido
```
Con **números** (`int`, `double`) se compara con `==`. Con **textos** (`String`) se compara **siempre con `.equals(...)`**. Es un fallo de examen clásico.

### 15.2. Clases anónimas: `new Interfaz() { ... }`
```java
boton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) { ... }
});
```
`setOnClickListener` necesita un objeto que cumpla la interfaz `View.OnClickListener`. En vez de crear una clase con nombre, se escribe **ahí mismo** una clase sin nombre que la implementa. Dentro, `this` es esa clase anónima (para referirte a la Activity: `MiActividad.this`), y las variables locales que uses deben ser `final`. Explicación completa en [04 §4.1](04-toast-personalizado.md).

### 15.3. Lambdas: `(v, insets) -> { ... }`
```java
ViewCompat.setOnApplyWindowInsetsListener(vista, (v, insets) -> {
    ...
    return insets;
});
```
Una **lambda** es una forma **abreviada** de escribir una clase anónima cuando la interfaz tiene **un solo método**. `(v, insets) -> { ... }` significa "recibo `v` e `insets` y hago esto". Es lo que trae el bloque de `EdgeToEdge` de todos los proyectos; los ejercicios usan la forma larga (`new ... { }`) porque es la que enseña el curso, pero **hacen lo mismo**.

### 15.4. El operador ternario `? :` (🧪 extra, no visto en clase)
```java
String estado = nota >= 5 ? "APROBADO" : "SUSPENSO";
```
Es un `if/else` en una sola línea: `condición ? valorSiTrue : valorSiFalse`. En clase se escribe siempre con un `if/else` normal; los ejercicios también, así que **no lo necesitas**, pero es posible que lo veas en código ajeno.

Y recuerda que **`+` con textos concatena**: `"Hola, " + nombre` une los dos; `"Nota: " + 8.5` da `"Nota: 8.5"`.

## Ver también
- [09-instalacion-estructura-proyecto](09-instalacion-estructura-proyecto.md) — el siguiente paso: qué es Android Studio y cómo está organizado un proyecto.
- [01-fundamentos-bundle-intent-ciclo-vida](01-fundamentos-bundle-intent-ciclo-vida.md) — el primer concepto específico de Android (`Activity`), que usa constantemente clases, herencia (`extends`) e interfaces tal y como se explican aquí.

## 🏋️ Practica esto

- [Nivel 1 — Java y primera app](../07-ejercicios/01-nivel-1-java-y-primera-app.md) (ejercicio 1.1: métodos, `for`, `ArrayList`)
- [Glosario A–Z](../01-guias/02-glosario.md)
