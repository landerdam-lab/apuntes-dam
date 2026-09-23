---
tags:
  - android
  - ejercicio
---

# Nivel 1 ⭐ — Java básico y tu primera app

**Antes de empezar, lee:** [00 — Conceptos básicos de programación](../02-conceptos/00-programacion-basica.md), [01 — Fundamentos](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md) y [04 — Toast personalizado](../02-conceptos/04-toast-personalizado.md) (solo el `Toast` normal del principio).

> Código verificado en el zip, paquete `ej/`: `Ej11JavaPuro`, `Ej12ContadorActivity`, `Ej13ToastActivity`, `Ej14OrigenActivity`, `Ej14DestinoActivity`, `EjVaciaActivity`.

---

## Ejercicio 1.1 — Java puro (sin Android)

### 📝 Enunciado
Escribe una clase con:
1. Un método `esPar(int n)` que devuelva `true` si el número es par.
2. Un método `sumarHasta(int n)` que devuelva `1 + 2 + … + n`.
3. En `main`: crea un `ArrayList<String>` con `"Ana"`, `"Luis"`, `"Marta"` y muéstralos numerados. Después llama a los dos métodos.

Salida esperada:
```
0: Ana
1: Luis
2: Marta
true
15
```

### 🎯 Qué practicas
Métodos con parámetros y retorno, `if`, `for`, `ArrayList` ([00 §3, §4, §8](../02-conceptos/00-programacion-basica.md)).

### 💡 Pistas
1. Un número es par si el **resto** de dividirlo entre 2 es 0. En Java el resto es el operador `%`.
2. Para sumar, necesitas una variable acumuladora que empiece en `0` y un `for` que vaya de `1` a `n`.
3. `nombres.get(i)` da el elemento de la posición `i`; `nombres.size()` da cuántos hay.

### ✅ Solución

```java
public class Ej11JavaPuro {

    // Devuelve true si el numero es par
    static boolean esPar(int n) {
        return n % 2 == 0;
    }

    // Suma 1 + 2 + ... + n
    static int sumarHasta(int n) {
        int total = 0;
        for (int i = 1; i <= n; i++) {
            total = total + i;
        }
        return total;
    }

    public static void main(String[] args) {
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Ana");
        nombres.add("Luis");
        nombres.add("Marta");

        for (int i = 0; i < nombres.size(); i++) {
            System.out.println(i + ": " + nombres.get(i));
        }

        System.out.println(esPar(4));       // true
        System.out.println(sumarHasta(5));  // 15
    }
}
```

**Explicación:**
- `n % 2 == 0` → el `%` da el resto; `==` **compara**. Un solo `=` sería una asignación.
- `total` empieza en `0` y en cada vuelta se le suma `i`. Con `n = 5`: 0+1+2+3+4+5 = **15**.
- El `for` de la lista empieza en **`0`** y usa `< nombres.size()` (no `<=`), porque la última posición es `size() - 1`.

### ▶ Cómo ejecutarlo
Fuera de Android, con el JDK que trae Android Studio. Guarda el código como `Ej11JavaPuro.java` y en PowerShell:

```powershell
cd carpeta\donde\esta\el\fichero
& "C:\Program Files\Android\Android Studio\jbr\bin\javac.exe" Ej11JavaPuro.java
& "C:\Program Files\Android\Android Studio\jbr\bin\java.exe" Ej11JavaPuro
```
(Comprobado: imprime exactamente la salida de arriba.)

### ⚠️ Errores típicos
- `for (int i = 0; i <= nombres.size(); i++)` → `IndexOutOfBoundsException` en la última vuelta.
- Olvidar el `return` en un método que no es `void`.

**🚀 Reto extra:** un método `mayor(ArrayList<Integer> numeros)` que devuelva el número más grande de la lista.

---

## Ejercicio 1.2 — Contador con botón

### 📝 Enunciado
Una pantalla con un número grande (empieza en `0`) y un botón **"Sumar 1"**. Cada vez que se pulsa, el número aumenta en 1.

### 🎯 Qué practicas
Layout básico, `findViewById`, `setOnClickListener` con clase anónima ([01 §1, §5, §6](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md)).

### 💡 Pistas
1. Un layout con un `TextView` (id `tvContador`) y un `Button` (id `btnSumar`). **La raíz debe llevar `android:id="@+id/main"`** (lo usa el bloque de insets).
2. El contador debe ser un **atributo de la clase** (fuera de `onCreate`).
3. `TextView.setText(...)` espera **texto**, no un número.

### ✅ Solución

**El layout `res/layout/ej_contador.xml`:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">

    <TextView
        android:id="@+id/tvContador"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="0"
        android:textSize="48sp" />

    <Button
        android:id="@+id/btnSumar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Sumar 1" />

</LinearLayout>
```

**La Activity** (aquí, la primera vez, con el bloque `EdgeToEdge` completo):

```java
public class Ej12ContadorActivity extends AppCompatActivity {

    private int contador = 0;
    private TextView tvContador;
    private Button btnSumar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_contador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvContador = findViewById(R.id.tvContador);
        btnSumar = findViewById(R.id.btnSumar);

        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador++;
                tvContador.setText(String.valueOf(contador));
            }
        });
    }
}
```

**Declararla en `AndroidManifest.xml`** (dentro de `<application>`):
```xml
<activity android:name=".ej.Ej12ContadorActivity" android:exported="false" />
```

**Explicación:**
- `contador` está **fuera** de `onCreate` → su valor se conserva entre clics.
- `String.valueOf(contador)` convierte el `int` a texto.
- El orden es siempre: `EdgeToEdge.enable` → `setContentView` → insets → `findViewById` → listeners.

### ⚠️ Errores típicos
- 📌 **`tvContador.setText(contador)`** (con el `int` directamente): la app **se cierra** con `Resources$NotFoundException`, porque Android cree que el número es el id de un texto de `strings.xml`.
- Declarar `int contador = 0;` **dentro** del `onClick`: se reinicia a 0 en cada clic.
- Llamar a `findViewById` **antes** de `setContentView` → `NullPointerException`.

**🚀 Reto extra:** un segundo botón **"Reiniciar"** que ponga el contador a 0.

---

## Ejercicio 1.3 — Leer un `EditText` y mostrar un `Toast`

### 📝 Enunciado
Un campo de texto para el nombre y un botón **"Mostrar"**. Al pulsarlo, aparece un `Toast` con `Hola, <nombre>`. Si el campo está vacío, el Toast debe decir `Escribe algo`.

*(De clase: el `Toast` normal del PDF `3-ToasPersonalizado.pdf`.)*

### 🎯 Qué practicas
`EditText.getText().toString()`, `Toast.makeText(getApplicationContext(), …)`, `if/else` ([04 §1](../02-conceptos/04-toast-personalizado.md)).

### 💡 Pistas
1. Lo escrito se obtiene con `etNombre.getText().toString()`.
2. Un `String` tiene el método `isEmpty()`.
3. Dentro de la clase anónima, `this` **no** es la Activity: en clase se usa `getApplicationContext()` (o `MiActividad.this`).

### ✅ Solución

**Layout `ej_toast.xml`:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical"
    android:padding="24dp">

    <EditText
        android:id="@+id/etNombre"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:autofillHints=""
        android:hint="Tu nombre"
        android:inputType="text" />

    <Button
        android:id="@+id/btnMostrar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Mostrar" />

</LinearLayout>
```

**Activity:**

```java
public class Ej13ToastActivity extends AppCompatActivity {

    private EditText etNombre;
    private Button btnMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_toast);
        // ... (bloque de insets de siempre, ver 01-fundamentos §5)

        etNombre = findViewById(R.id.etNombre);
        btnMostrar = findViewById(R.id.btnMostrar);

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = etNombre.getText().toString();
                if (texto.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Escribe algo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Hola, " + texto, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
```

**Explicación:**
- `getText()` devuelve un objeto `Editable`; `.toString()` lo convierte a `String` normal.
- `Toast.makeText(contexto, texto, duración).show()` → **tres** datos y el `.show()` final.

### ⚠️ Errores típicos
- 📌 **Olvidar `.show()`**: no aparece nada y no da error.
- Comparar textos con `==` en vez de `.equals(...)`.

**🚀 Reto extra:** que el Toast use `Toast.LENGTH_LONG` y muestre el nombre en mayúsculas (`texto.toUpperCase()`).

---

## Ejercicio 1.4 — Ir a otra pantalla llevándote un dato (`Bundle`)

### 📝 Enunciado
La primera pantalla tiene un campo de nombre y un botón. Al pulsarlo se abre una segunda pantalla que muestra `Hola, <nombre>`. El dato debe viajar en un **`Bundle`** con la clave `"saludo"`.

*(De clase: es exactamente el ejemplo del `Bundle` con `putString("saludo", …)` del PDF `4-asyncTask.pdf`.)*

### 🎯 Qué practicas
`Intent` explícito, `Bundle`, `putString` / `getString`, `intent.putExtras`, declarar Activities en el manifiesto ([01 §2.2, §3, §4](../02-conceptos/01-fundamentos-bundle-intent-ciclo-vida.md), [11](../02-conceptos/11-intent-en-profundidad.md)).

### 💡 Pistas
1. Necesitas **dos** clases `Activity`, **dos** layouts y **dos** entradas en el manifiesto.
2. `Bundle bundle = new Bundle(); bundle.putString("saludo", …); intent.putExtras(bundle);`
3. Se lee con `getIntent().getExtras()`. La **clave** debe ser exactamente la misma en los dos lados.

### ✅ Solución

**Pantalla origen — layout `ej_origen.xml`:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical"
    android:padding="24dp">

    <EditText
        android:id="@+id/etNombre"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:autofillHints=""
        android:hint="Tu nombre"
        android:inputType="text" />

    <Button
        android:id="@+id/btnIr"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Ir a la otra pantalla" />

</LinearLayout>
```

**Pantalla origen — Activity:**

```java
public class Ej14OrigenActivity extends AppCompatActivity {

    private EditText etNombre;
    private Button btnIr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_origen);
        // ... (bloque de insets de siempre, ver 01-fundamentos §5)

        etNombre = findViewById(R.id.etNombre);
        btnIr = findViewById(R.id.btnIr);

        btnIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creacion de un Bundle para pasar datos a la nueva activity
                Bundle bundle = new Bundle();
                bundle.putString("saludo", etNombre.getText().toString());

                Intent intent = new Intent(getApplicationContext(), Ej14DestinoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
```

**Pantalla destino — layout `ej_destino.xml`:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">

    <TextView
        android:id="@+id/tvSaludo"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="32sp" />

</LinearLayout>
```

**Pantalla destino — Activity:**

```java
public class Ej14DestinoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_destino);
        // ... (bloque de insets de siempre, ver 01-fundamentos §5)

        TextView tvSaludo = findViewById(R.id.tvSaludo);

        //Leer el bundle
        Bundle bundleRecibido = getIntent().getExtras();
        if (bundleRecibido != null && !bundleRecibido.isEmpty()) {
            tvSaludo.setText("Hola, " + bundleRecibido.getString("saludo"));
        }
    }
}
```

**Manifiesto (las dos):**
```xml
<activity android:name=".ej.Ej14OrigenActivity" android:exported="false" />
<activity android:name=".ej.Ej14DestinoActivity" android:exported="false" />
```

**Explicación:**
- `new Intent(getApplicationContext(), Destino.class)` = "quiero ir de aquí a allí".
- El `Bundle` es una **caja de pares clave-valor**: `putString("saludo", valor)` mete el dato y `getString("saludo")` lo saca.
- `intent.putExtras(bundle)` pega la caja al `Intent`; `startActivity(intent)` es lo que realmente abre la pantalla.
- `bundleRecibido != null && !bundleRecibido.isEmpty()` evita errores si no llegó nada.

### ⚠️ Errores típicos
- 📌 **`ActivityNotFoundException`**: la Activity destino no está en el manifiesto.
- Clave distinta en un lado y en otro (`"saludo"` / `"Saludo"`): el resultado es `null`.
- Olvidar `intent.putExtras(bundle)`: el destino recibe un `Bundle` vacío.

**🚀 Reto extra:** en la segunda pantalla, añade un botón **"Volver"** que llame a `finish()`.

---

## Pantalla vacía (la usarás mucho)

Varios ejercicios necesitan "una nueva actividad vacía" como destino (el botón *Nuevo* del diseño, la animación de carga de 3 segundos…). Es la plantilla de siempre:

```java
// Pantalla vacia: destino de "Nuevo" y de la animacion de carga
public class EjVaciaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ej_vacia);
        // ... (bloque de insets de siempre, ver 01-fundamentos §5)
    }
}
```

---

## ✅ Autoevaluación del Nivel 1

- [ ] Escribir un método con parámetros y `return`.
- [ ] Recorrer un `ArrayList` con `for`.
- [ ] Enlazar un componente con `findViewById` y programar su clic con una clase anónima.
- [ ] Leer un `EditText` y mostrar un `Toast` (con `.show()`).
- [ ] Abrir otra pantalla y declararla en el manifiesto.
- [ ] Pasar un dato con un `Bundle` y recibirlo en la otra pantalla.

Si los tienes todos → **[Nivel 2](02-nivel-2-disenos-xml.md)**.
