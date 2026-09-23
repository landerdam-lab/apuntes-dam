---
tags:
  - android
  - reutilizable
aliases:
  - Pasar datos entre pantallas
  - putExtra
---

# 03 — Pasar datos entre pantallas

## Tipos simples con `putExtra` ✅ Reutilizable
```java
// ORIGEN
Intent i = new Intent(this, DetalleActivity.class);
i.putExtra("nombre", "Luka Doncic");   // String
i.putExtra("dorsal", 77);              // int
i.putExtra("titular", true);           // boolean
startActivity(i);

// DESTINO (en onCreate)
String nombre = getIntent().getStringExtra("nombre");         // null si no llega
int dorsal = getIntent().getIntExtra("dorsal", 0);            // 0 = valor por defecto
boolean titular = getIntent().getBooleanExtra("titular", false);
```
**Aparece en:** `GridViewDam2Activity` → `DetalleActivity` (`"idFoto"`), [AdapterDam2](../04-proyectos-profesor/AdapterDam2.md).

## Con un `Bundle` (forma de los proyectos) ✅ Reutilizable
```java
Bundle bundle = new Bundle();
bundle.putString("jugador", datos.get(position));
intent.putExtras(bundle);                          // ojo: putExtras, en plural
// destino: getIntent().getStringExtra("jugador")  (igual que arriba)
```
**Aparece en:** [EjercicioSpinner](../04-proyectos-profesor/EjercicioSpinner.md), [AdapterDam2](../04-proyectos-profesor/AdapterDam2.md) (Spinner).

> [!tip] 💡 Recomendación: claves en constantes
> ```java
> public class DetalleActivity extends AppCompatActivity {
>     public static final String EXTRA_NOMBRE = "nombre";
> }
> // i.putExtra(DetalleActivity.EXTRA_NOMBRE, …) / getStringExtra(EXTRA_NOMBRE)
> ```
> Así, un error al escribir la clave **no compila**, en vez de devolver `null` en silencio.

## Un objeto entero (`Serializable`) ✅ Reutilizable
**Aparece en:** [EjercicioAdaptadoresFinal](../04-proyectos-profesor/EjercicioAdaptadoresFinal.md), [EjercicioPokemon](../04-proyectos-profesor/EjercicioPokemon.md).
```java
public class Jugador implements Serializable { … }          // 1) el modelo (y lo que contiene) Serializable

intent.putExtra("jugador", jugador);                         // 2) origen

Jugador j;                                                    // 3) destino
if (Build.VERSION.SDK_INT >= 33) {
    j = getIntent().getSerializableExtra("jugador", Jugador.class);  // versión moderna
} else {
    j = (Jugador) getIntent().getSerializableExtra("jugador");      // la de los proyectos (obsoleta en 33+)
}
```

## Datos a un Fragment (`newInstance`) ✅ Reutilizable
**Aparece en:** `FragmentoAbajo` de [EjemploFragmentos2](../04-proyectos-profesor/EjemploFragmentos2.md).
```java
public class DetalleFragment extends Fragment {
    private static final String ARG_TEXTO = "texto";

    public static DetalleFragment newInstance(String texto) {
        DetalleFragment f = new DetalleFragment();
        Bundle b = new Bundle();
        b.putString(ARG_TEXTO, texto);
        f.setArguments(b);           // sobrevive a la recreación (girar el móvil)
        return f;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle s) {
        super.onViewCreated(view, s);
        String texto = getArguments() != null ? getArguments().getString(ARG_TEXTO) : "";
        ((TextView) view.findViewById(R.id.tvTexto)).setText(texto);
    }
}
// Uso: getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, DetalleFragment.newInstance("hola")).commit();
```
> [!warning] ⚠️ Cuidado
> Nunca pases los datos por el **constructor** de un Fragment (`new FragmentoMedio(bundle)`, como en [EjercicioFragmentos](../04-proyectos-profesor/EjercicioFragmentos.md)): se pierden al girar el móvil.

## Datos de un Fragment a su Activity (interfaz) ✅ Reutilizable
```java
public interface OnEnviarListener { void onEnviar(String texto); }   // 1) contrato

public class MainActivity extends AppCompatActivity implements OnEnviarListener {   // 2) Activity
    @Override public void onEnviar(String texto) { … }
}

public class FormularioFragment extends Fragment {                   // 3) Fragment
    private OnEnviarListener listener;
    @Override public void onAttach(@NonNull Context c) { super.onAttach(c); listener = (OnEnviarListener) c; }
    @Override public void onDetach() { super.onDetach(); listener = null; }
    // en el clic: listener.onEnviar(et.getText().toString());
}
```

## Devolver un resultado a la pantalla anterior ✅ Reutilizable
No aparece en los proyectos, pero es habitual ("elige un color y vuelve"):
```java
// Pantalla A: registrar en onCreate (o como atributo)
ActivityResultLauncher<Intent> elegir = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), r -> {
            if (r.getResultCode() == RESULT_OK && r.getData() != null) {
                String color = r.getData().getStringExtra("color");
            }
        });
// …elegir.launch(new Intent(this, PantallaB.class));

// Pantalla B: al terminar
setResult(RESULT_OK, new Intent().putExtra("color", "rojo"));
finish();
```
