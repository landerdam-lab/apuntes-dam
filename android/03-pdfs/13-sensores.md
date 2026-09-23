---
tags:
  - android
  - pdf
  - bloque/2
  - tema/sensores
aliases:
  - 2-Sensores
---

# PDF 2 (bloque 2) — Sensores

> [!info] Ficha
> **Archivo:** `adjuntos/pdfs/2-Sensores.pdf` · **9 páginas** · **Bloque 2** · *"Partiendo del proyecto anterior"* (el del diálogo y SQLite)
> **Proyecto:** [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md) → `SensoresActivity` + botón `btnSensores` de `CentralActivity`
> **Concepto:** [21 — Sensores](../02-conceptos/21-sensores.md) · **Reutilizable:** [Multimedia](../06-codigo-reutilizable/10-multimedia-sensores-camara-voz.md)

## Qué explica

1. Crear **`SensoresActivity`** con dos `TextView` de 40sp: `tvProximidad` ("Lejos") y `tvGiroscopio` ("Centro").
2. Abrirla desde el botón **Sensores** de `CentralActivity` con un `Intent`.
3. Declarar en el manifest **`<uses-feature android:name="android.hardware.sensor.proximity" android:required="false"/>`**, antes de `<application>`.
4. Obtener el **`SensorManager`** con `getSystemService(SENSOR_SERVICE)` y el sensor con `getDefaultSensor(Sensor.TYPE_PROXIMITY)`, comprobando si es `null` (el móvil puede no tenerlo).
5. Crear un **`SensorEventListener`**: en `onSensorChanged`, si `event.values[0] < getMaximumRange()` → "Cerca"; si no → "Lejos".
6. **Registrar** el listener en `onResume` y **quitarlo** en `onPause`.
7. **Probarlo en el emulador:** *Extended Controls* (⋮) → **Virtual sensors** → *Additional sensors* → **Proximity**.
8. Añadir el **giroscopio** (velocidad angular sobre X, Y, Z): otro `uses-feature`, `TYPE_GYROSCOPE` y, en su listener, `values[2] > 0.5` → "Izquierda", `<= -0.5` → "Derecha".
9. Probarlo con *Device Pose* → **Z-Rot**. *"Tener en cuenta que el teléfono puede no tener giroscopio."*

## Dónde se aplica

`SensoresActivity` del proyecto es **idéntica** al código final del PDF (solo cambia `<= -0.5f` por `< -0.5f`, sin efecto práctico).

> [!pdf]- 📄 Ver el PDF original
> ![](../adjuntos/pdfs/2-Sensores.pdf)
