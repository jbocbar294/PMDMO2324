package iesmm.pmdm.pmdm_t4_tesoroapp;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener, SensorEventListener {

    private ImageView[] imagen; // array de imagenes que contiene todas las imágenes de bloques
    private TextView tvAgita;

    private int diamante;
    private int lava;

    private Animation animation;

    private SensorManager sensorManager; // Gestor de sensores
    private Sensor luxometro; // Objeto sensor a medir
    private Sensor acelerometro; // Objeto sensor a medir
    private final int MAX_LUX = 10; // constante para comparar el nivel de luz
    private View layout;

    // objetos para reproducir sonidos
    private MediaPlayer bloque;
    private MediaPlayer bloqueDiamante;
    private MediaPlayer bloqueLava;

    private float val1, val2;

    private Vibrator vibrator; // Sensor de vibración
    long[] pattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // asignación de imageviews al array
        imagen = new ImageView[25];
        imagen[0] = findViewById(R.id.f1c1);
        imagen[1] = findViewById(R.id.f1c2);
        imagen[2] = findViewById(R.id.f1c3);
        imagen[3] = findViewById(R.id.f1c4);
        imagen[4] = findViewById(R.id.f1c5);
        imagen[5] = findViewById(R.id.f2c1);
        imagen[6] = findViewById(R.id.f2c2);
        imagen[7] = findViewById(R.id.f2c3);
        imagen[8] = findViewById(R.id.f2c4);
        imagen[9] = findViewById(R.id.f2c5);
        imagen[10] = findViewById(R.id.f3c1);
        imagen[11] = findViewById(R.id.f3c2);
        imagen[12] = findViewById(R.id.f3c3);
        imagen[13] = findViewById(R.id.f3c4);
        imagen[14] = findViewById(R.id.f3c5);
        imagen[15] = findViewById(R.id.f4c1);
        imagen[16] = findViewById(R.id.f4c2);
        imagen[17] = findViewById(R.id.f4c3);
        imagen[18] = findViewById(R.id.f4c4);
        imagen[19] = findViewById(R.id.f4c5);
        imagen[20] = findViewById(R.id.f5c1);
        imagen[21] = findViewById(R.id.f5c2);
        imagen[22] = findViewById(R.id.f5c3);
        imagen[23] = findViewById(R.id.f5c4);
        imagen[24] = findViewById(R.id.f5c5);

        for (ImageView iv : imagen) { // asignación del escuchador a los imageviews
            iv.setOnClickListener(this);
        }

        layout = findViewById(R.id.layout); // layout de la app
        tvAgita = findViewById(R.id.tvAgita);

        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        // comprobamos que ambos sensores existen y los registramos
        luxometro = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (luxometro != null) { // si es distinto de nulo significa que el sensor existe y funciona
            // registrar el escuchador del sensor
            sensorManager.registerListener(this, luxometro, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "No existe luxómetro", Toast.LENGTH_SHORT).show();
        }

        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acelerometro != null) {
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "No existe acelerómetro", Toast.LENGTH_SHORT).show();
        }

        val1 = 0;
        val2 = 0;

        generarDiamanteLava();

        // Obtener el servicio de vibración
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    @Override
    public void onClick(View view) {
        bloque.start(); // reproducción de un sonido
        boolean gana = false; // variable para determinar si ha ganado o no
        for (int i = 0; i < 25; i++) { // bucle que recorre todos los imageviews
            if (imagen[i].getId() == view.getId()) {
                if (imagen[i].getId() == diamante) { // si la imagen es el diamante, gana es true y llama al metodo que termina el juego
                    gana = true;
                    imagen[i].setBackgroundResource(R.drawable.diamond); // cambia la imagen del bloque
                    ganaPierde(gana);
                } else if (imagen[i].getId() == lava) { // igual pero en este caso pierde
                    imagen[i].setBackgroundResource(R.drawable.lava);
                    ganaPierde(gana);
                } else {
                    loadAnimation(imagen[i], R.anim.movimiento, R.drawable.black); // genera una animación
                }
            }
        }
    }

    private void generarDiamanteLava() { // método que empieza el juego
        int aux = (int) (Math.random() * 25); // número aleatorio entre 0 y 24
        diamante = imagen[aux].getId(); // almacena la posición del imageview del array imagen que es el diamante

        do { // bucle para asegurar que no se genera el mismo número
            aux = (int) (Math.random() * 25);
            lava = imagen[aux].getId(); // lo mismo que diamante
        } while (diamante == lava);
    }

    private void ganaPierde(boolean gana) {
        String titulo;

        if (gana) { // en función del valor de gana, cambia el título del AlertDialog, el patrón de vibración y el sonido
            titulo = "Has ganado!";
            pattern = new long[]{0, 600, 200, 600};
            vibrator.vibrate(pattern, -1);
            bloqueDiamante.start();
        } else {
            titulo = "Has perdido";
            pattern = new long[]{0, 200, 200, 200};
            vibrator.vibrate(pattern, -1);
            bloqueLava.start();
        }

        // AlertDialog que pregunta al usuario si quiere empezar otra partida o quiere cerrar la app
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage("Quieres empezar otra partida?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                reiniciar();
            }
        });
        builder.setNegativeButton("No, salir del juego", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadAnimation(View view, int animacion, int imagenFinal) {
        // cargamos la animación
        animation = AnimationUtils.loadAnimation(this, animacion);
        // vinculación del escuchador
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // con esto obtenemos que cuando termine la animación, el fondo del imageview sea una imagen nueva
                view.setBackgroundResource(imagenFinal);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        // comenzar la animación
        view.startAnimation(animation);
    }

    private void reiniciar() {
        for (ImageView iv : imagen) { // todas las imagenes vuelven a tener la imagen principal
            iv.setBackgroundResource(R.drawable.stone);
        }
        generarDiamanteLava(); // llamamos al método que empieza el juego
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) { // luxómetro
            // obtenemos ambos colores
            int negro = getResources().getColor(android.R.color.black);
            int blanco = getResources().getColor(android.R.color.white);
            // si el luxómetro obtiene menos cantidad que MAX_LUX, cambia el color del fondo a negro, y el texto a blanco
            if (event.values[0] < MAX_LUX) {
                tvAgita.setTextColor(blanco);
                layout.setBackgroundColor(negro);
            } else { // si no, hace lo contrario
                tvAgita.setTextColor(negro);
                layout.setBackgroundColor(blanco);
            }
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) { // acelerómetro
            // val1 y val2 almacenan los valores del movimiento del dispositivo
            val2 = val1; // siendo val2 el valor anterior al nuevo
            val1 = event.values[0]; // y val1 el nuevo valor
            Log.d("val1", String.valueOf(val1));
            Log.d("val2", String.valueOf(val2));
            float diferencia = val1 - val2; // calculamos la diferencia entre ambos
            if (diferencia > 6) { // si la diferencia es grande, significa que hemos agitado el dispositivo
                sensorManager.unregisterListener(this, acelerometro); // quitamos el sensor
                // AlertDialog que pregunta al usuario si quiere reiniciar la partida
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Quieres reiniciar la partida?");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        reiniciar();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                onResume(); // llamamos al método onResume para volver a registrar el sensor
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() { // primer plano
        super.onResume();
        sensorManager.registerListener(this, luxometro, SensorManager.SENSOR_DELAY_NORMAL); // registrar de nuevo el escuchador para luxómetro
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL); // registrar de nuevo el escuchador para acelerómetro
        bloque = MediaPlayer.create(this, R.raw.breaking);
        bloqueDiamante = MediaPlayer.create(this, R.raw.diamond);
        bloqueLava = MediaPlayer.create(this, R.raw.lava);
    }

    @Override
    protected void onPause() { // segundo plano
        super.onPause();
        sensorManager.unregisterListener(this); // desvincular el escuchador
    }
}