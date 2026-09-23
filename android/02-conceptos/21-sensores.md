---
tags:
  - android
  - concepto
  - tema/sensores
aliases:
  - Sensores
  - SensorManager
---

# 21 — Sensores (proximidad, giroscopio y demás)

> PDF: [2 — Sensores](../03-pdfs/13-sensores.md) · Proyecto: [EjemploDialogoPersonalizado → SensoresActivity](../04-proyectos-profesor/EjemploDialogoPersonalizado.md)

## Concepto: sensor 📌 Importante

### Qué es
Un **sensor** es una pieza de hardware del móvil que mide algo del mundo físico: la distancia a un objeto (proximidad), la aceleración, el giro, la luz, el campo magnético… Android los ofrece todos con la misma API, así que se programan igual.

### Para qué sirve
- **Proximidad:** apagar la pantalla al acercar el móvil a la oreja.
- **Acelerómetro:** detectar que agitas el móvil o su inclinación.
- **Giroscopio:** saber a qué velocidad gira (juegos, estabilizar vídeo).
- **Luz:** ajustar el brillo automáticamente.

### Las 4 piezas

| Pieza | Qué es | Analogía |
|---|---|---|
| `SensorManager` | Servicio del sistema que gestiona **todos** los sensores | La centralita |
| `Sensor` | Un sensor concreto (o `null` si el móvil no lo tiene) | Un aparato de la centralita |
| `SensorEventListener` | Tu código que recibe las lecturas | El que escucha el teléfono |
| `SensorEvent` | Una lectura: `values[]`, `timestamp`, `accuracy` | Un mensaje |

### Dónde aparece
`SensoresActivity` de [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md): proximidad y giroscopio.

### Ejemplo del proyecto (resumido)
```java
sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
proximityListener = new SensorEventListener() {
    @Override public void onSensorChanged(SensorEvent event) {
        tvProximidad.setText(event.values[0] < proximitySensor.getMaximumRange() ? "Cerca" : "Lejos");
    }
    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) { }
};

@Override protected void onResume() {
    super.onResume();
    if (proximitySensor != null)
        sensorManager.registerListener(proximityListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
}
@Override protected void onPause() {
    super.onPause();
    sensorManager.unregisterListener(proximityListener);
}
```

### Explicación del ejemplo
1. `getSystemService(SENSOR_SERVICE)` pide al sistema el gestor de sensores. Devuelve `Object`, por eso se castea.
2. `getDefaultSensor(TYPE_…)` devuelve el sensor de ese tipo, o **`null`** si no existe.
3. El listener tiene dos métodos obligatorios, porque es una **interfaz**: `onSensorChanged` (cada lectura) y `onAccuracyChanged` (casi siempre vacío).
4. **`registerListener` en `onResume` y `unregisterListener` en `onPause`:** solo se escucha mientras la pantalla está visible. Es el patrón **obligatorio** para no gastar batería.
5. `SENSOR_DELAY_NORMAL` es la frecuencia de lecturas. Existen también `UI`, `GAME` y `FASTEST`, cada vez más rápidas y con más gasto.

### Qué significa `values[]` en cada sensor

| Tipo (`Sensor.TYPE_…`) | `values[0]` | `values[1]` | `values[2]` | Unidad |
|---|---|---|---|---|
| `PROXIMITY` | distancia | — | — | cm (muchos móviles solo dan 0 o el máximo) |
| `ACCELEROMETER` | X | Y | Z | m/s² (incluye la gravedad: ≈ 9,8 en reposo) |
| `GYROSCOPE` | giro sobre X | Y | Z | rad/s (**velocidad**, no posición) |
| `LIGHT` | luz | — | — | lux |
| `MAGNETIC_FIELD` | X | Y | Z | µT |

### Manifest
```xml
<!-- Informa de que la app USA el sensor, pero se puede instalar aunque no lo tenga -->
<uses-feature android:name="android.hardware.sensor.proximity" android:required="false" />
<uses-feature android:name="android.hardware.sensor.gyroscope" android:required="false" />
```
Los sensores de movimiento y entorno **no necesitan permiso**. La excepción son los sensores corporales (ritmo cardíaco: `BODY_SENSORS`).

### Ejemplo reutilizable — acelerómetro ✅ Reutilizable
```java
public class AcelerometroActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private Sensor acelerometro;
    private TextView tvX, tvY, tvZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acelerometro);
        tvX = findViewById(R.id.tvX); tvY = findViewById(R.id.tvY); tvZ = findViewById(R.id.tvZ);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometro = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acelerometro == null) {
            Toast.makeText(this, "Este dispositivo no tiene acelerómetro", Toast.LENGTH_LONG).show();
        }
    }

    @Override protected void onResume() {
        super.onResume();
        if (acelerometro != null) sm.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_UI);
    }
    @Override protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);            // quita TODOS los sensores de este listener
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        tvX.setText(String.format(Locale.getDefault(), "X: %.2f", e.values[0]));
        tvY.setText(String.format(Locale.getDefault(), "Y: %.2f", e.values[1]));
        tvZ.setText(String.format(Locale.getDefault(), "Z: %.2f", e.values[2]));
    }
    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}
```
**Qué cambiar:** el `TYPE_…`, los `TextView` y la lógica de `onSensorChanged`. Aquí la propia Activity *es* el listener (`implements SensorEventListener`), una alternativa a la clase anónima del proyecto.

### Errores comunes
| Error | Causa | Solución |
|---|---|---|
| `NullPointerException` en `registerListener` | El móvil/emulador no tiene ese sensor | Comprobar `sensor != null` |
| La batería se agota | No se llama a `unregisterListener` | Hacerlo en `onPause` |
| El texto no vuelve a "Centro" | El giroscopio mide **velocidad**; al parar llega ≈ 0 y ningún `if` se cumple | Añadir un `else` o usar el acelerómetro para la inclinación |
| "No cambia nada" en el emulador | Hay que mover los sensores virtuales | ⋮ → *Virtual sensors* |
| Se actualiza demasiado rápido | `SENSOR_DELAY_FASTEST` | Usar `NORMAL` o `UI` |

## Relacionado
- [10 — Activity a fondo](10-activity-en-profundidad.md) (por qué `onResume`/`onPause`)
- [24 — Manifest y permisos](24-manifest-y-permisos.md) (`uses-feature`)
- [Código reutilizable → Multimedia](../06-codigo-reutilizable/10-multimedia-sensores-camara-voz.md)
