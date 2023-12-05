package iesmm.pmdm.pmdm_t4_sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager; // gestor de sensores

    private Sensor sensor; // sensor de proximidad

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // obtener disponibilidad del sensor
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        
        if (sensor != null) { // si es distinto de nulo significa que el sensor existe y funciona
            // registrar el escuchador del sensor
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "No existe sensor de proximidad", Toast.LENGTH_SHORT).show();
        }

        // llamar al servicio de vibración
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        long vibraciones[] = {0, 500, 0, 1500};
        vibrator.vibrate(vibraciones, -1);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            Log.d("PMDM", String.valueOf(sensorEvent.values[0]));
            switch ((int) sensorEvent.values[0]) {
                case 1:
                    vibrator.vibrate(100);
                    Toast.makeText(this, "100", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    vibrator.vibrate(200);
                    Toast.makeText(this, "200", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    vibrator.vibrate(300);
                    Toast.makeText(this, "300", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    vibrator.vibrate(400);
                    Toast.makeText(this, "400", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    vibrator.vibrate(500);
                    Toast.makeText(this, "500", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() { // primer plano
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL); // registrar de nuevo el escuchador
    }

    @Override
    protected void onPause() { // segundo plano
        super.onPause();
        sensorManager.unregisterListener(this); // desvincular el escuchador
    }
}