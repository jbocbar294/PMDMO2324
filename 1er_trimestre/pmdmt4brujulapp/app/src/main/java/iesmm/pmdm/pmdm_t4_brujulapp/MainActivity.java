package iesmm.pmdm.pmdm_t4_brujulapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager; // Gestor de sensores
    private Sensor brujula; // Objeto sensor a medir
    TextView tvGrados;
    TextView tvDireccion;
    ImageView ivAguja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvGrados = findViewById(R.id.tvGrados);
        tvDireccion = findViewById(R.id.tvDireccion);

        ivAguja = findViewById(R.id.aguja);

        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        brujula = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        if (brujula != null) { // si es distinto de nulo significa que el sensor existe y funciona
            // registrar el escuchador del sensor
            sensorManager.registerListener(this, brujula, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "No existe sensor de orientación", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {

            int grados = (int) sensorEvent.values[0];
            Log.d("PMDM", String.valueOf(grados));
            tvGrados.setText(String.valueOf((int) sensorEvent.values[0]));

            if (grados > 338 || grados <= 23) {
                tvDireccion.setText("N");
            } else if (grados > 23 && grados <= 68) {
                tvDireccion.setText("NE");
            } else if (grados > 68 && grados <= 113) {
                tvDireccion.setText("E");
            } else if (grados > 113 && grados <= 158) {
                tvDireccion.setText("SE");
            } else if (grados > 158 && grados <= 203) {
                tvDireccion.setText("S");
            } else if (grados > 203 && grados <= 248) {
                tvDireccion.setText("SW");
            } else if (grados > 248 && grados <= 293) {
                tvDireccion.setText("W");
            } else if (grados > 293 && grados <= 338) {
                tvDireccion.setText("NW");
            }

            String gradosIvenrtido = "-" + grados;
            ivAguja.setRotation(Float.parseFloat(gradosIvenrtido));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}