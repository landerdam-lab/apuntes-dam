package com.example.ejemplodialogopersonalizado;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class SensoresActivity extends AppCompatActivity
{
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityListener;
    private TextView tvProximidad;

    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeListener;
    private TextView tvGiroscopio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sensores);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvProximidad = findViewById(R.id.tvProximidad);
        tvGiroscopio = findViewById(R.id.tvGiroscopio);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(proximitySensor == null)
        {
            Log.e("SENSORES", "No existe sensor de proximidad");
        }
        proximityListener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

            @Override
            public void onSensorChanged(SensorEvent event)
            {
                if(event.values[0] < proximitySensor.getMaximumRange())
                {
                    tvProximidad.setText("Cerca");
                } else
                {
                    tvProximidad.setText("Lejos");
                }
            }
        };

        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyroscopeSensor == null)
        {
            Log.e("SENSORES", "No existe giroscopio");
        }
        gyroscopeListener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

            @Override
            public void onSensorChanged(SensorEvent event)
            {
                if(event.values[2] > 0.5f)
                {
                    tvGiroscopio.setText("Izquierda");
                } else if(event.values[2] < -0.5f)
                {
                    tvGiroscopio.setText("Derecha");
                }
            }
        };
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(proximitySensor != null)
        {
            sensorManager.registerListener(proximityListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(gyroscopeSensor != null)
        {
            sensorManager.registerListener(gyroscopeListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(proximityListener);
        sensorManager.unregisterListener(gyroscopeListener);
    }
}