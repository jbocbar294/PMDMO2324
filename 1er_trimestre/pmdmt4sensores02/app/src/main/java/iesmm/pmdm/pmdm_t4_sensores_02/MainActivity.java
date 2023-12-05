package iesmm.pmdm.pmdm_t4_sensores_02;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    private SensorManager sensorManager; // Gestor de sensores
    private Sensor acelerometro; // Objeto sensor a medir
    private Sensor luxometro;
    private View layout;
    private TextView ejeX, ejeY, ejeZ, time;
    private Button start, stop;
    private final String LOGTAG = "PMDM";
    private final int MAX_LUX = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Objetos del layout
        start = (Button) this.findViewById(R.id.start);
        start.setOnClickListener(this);

        stop = (Button) this.findViewById(R.id.stop);
        stop.setOnClickListener(this);

        ejeX = (TextView) findViewById(R.id.ejeX);
        ejeY = (TextView) findViewById(R.id.ejeY);
        ejeZ = (TextView) findViewById(R.id.ejeZ);
        time = (TextView) findViewById(R.id.time);

        layout = findViewById(R.id.layout);

        // Asociación de eventos escuchadores
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        luxometro = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Inicio por defecto
        stop();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start) {
            start();
        } else if (view.getId() == R.id.stop) {
            stop();
        }
    }

    /* Informa el sensor de un valor nuevo recogido: Un objeto SensorEvent contiene información sobre los nuevos datos del sensor
    , por ejemplo, la exactitud de los datos, el sensor que generó los datos, la marca de tiempo en la que se generaron los datos
    y los nuevos datos que registró el sensor. */
    // Comprensión de unidades y valores: https://developer.android.com/guide/topics/sensors/sensors_motion?hl=es-419
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Obtener rotación de cada eje
            float rotationX = sensorEvent.values[0];
            float rotationY = sensorEvent.values[1];
            float rotationZ = sensorEvent.values[2];

            // Actualizar el TextView con la rotación en el eje X
            ejeX.setText("Eje X: " + formatearValor(rotationX));
            ejeY.setText("Eje Y: " + formatearValor(rotationY));
            ejeZ.setText("Eje Z: " + formatearValor(rotationZ));
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            if (sensorEvent.values[0] < MAX_LUX) {
                layout.setBackgroundColor((int) R.color.black);
            } else {
                layout.setBackgroundColor((int) R.color.white);
            }
        }

        time.setText(hora());
    }


    /*
    Método para definir el cambio de exactitud y precisión de un sensor.
    La exactitud está representada por una de las cuatro constantes de estado:
    SENSOR_STATUS_ACCURACY_LOW, SENSOR_STATUS_ACCURACY_MEDIUM, SENSOR_STATUS_ACCURACY_HIGH o SENSOR_STATUS_UNRELIABLE.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /* Métodos de control de estado en la app para vincular y desvincular el escuchador del sensor */

    /* Cancela el registro de los objetos de escucha del sensor cuando se ha terminado de usar el sensor o cuando se detenga la actividad del sensor */
    @Override
    protected void onPause() {
        super.onPause();
        stop();
    }

    public void start() {
        // Obtener disponibilidad del sensor
        if (acelerometro != null) { // si es distinto de nulo significa que el sensor existe y funciona
            // registrar el escuchador del sensor
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "No existe acelerómetro", Toast.LENGTH_SHORT).show();
        }

        if (luxometro != null) { // si es distinto de nulo significa que el sensor existe y funciona
            // registrar el escuchador del sensor
            sensorManager.registerListener(this, luxometro, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "No existe luxómetro", Toast.LENGTH_SHORT).show();
        }

    }

    public void stop() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this); // desvincular el escuchador
            ejeX.setText("Eje X: 0.0");
            ejeY.setText("Eje Y: 0.0");
            ejeZ.setText("Eje Z: 0.0");
            time.setText("HH:mm:ss");
        }
    }

    public String formatearValor(float num) {
        return String.valueOf(Math.floor(num * 100) / 100);
    }

    public String hora() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}