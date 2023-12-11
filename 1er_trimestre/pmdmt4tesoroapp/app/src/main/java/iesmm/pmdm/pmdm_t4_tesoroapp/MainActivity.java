package iesmm.pmdm.pmdm_t4_tesoroapp;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener, SensorEventListener {

    private ImageView imagen[];

    private int diamante;
    private int lava;

    private Animation animation;

    private SensorManager sensorManager; // Gestor de sensores
    private Sensor luxometro; // Objeto sensor a medir
    private final int MAX_LUX = 10;
    private View layout;

    private MediaPlayer bloque;
    private MediaPlayer bloqueDiamante;
    private MediaPlayer bloqueLava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        for (ImageView iv : imagen) {
            iv.setOnClickListener(this);
        }

        layout = findViewById(R.id.layout);

        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        luxometro = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (luxometro != null) { // si es distinto de nulo significa que el sensor existe y funciona
            // registrar el escuchador del sensor
            sensorManager.registerListener(this, luxometro, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "No existe acelerómetro", Toast.LENGTH_SHORT).show();
        }

        generarDiamanteLava();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        bloque = MediaPlayer.create(this, R.raw.breaking);
        bloqueDiamante = MediaPlayer.create(this, R.raw.diamond);
        bloqueLava = MediaPlayer.create(this, R.raw.lava);

    }

    @Override
    public void onClick(View view) {
        bloque.start();
        boolean gana = false;
        for (int i = 0; i < 25; i++) {
            if (imagen[i].getId() == view.getId()) {
                if (imagen[i].getId() == diamante) {
                    gana = true;
                    imagen[i].setBackgroundResource(R.drawable.diamond);
                    ganaPierde(gana);
                } else if (imagen[i].getId() == lava) {
                    imagen[i].setBackgroundResource(R.drawable.lava);
                    ganaPierde(gana);
                } else {
                    loadAnimation(imagen[i], R.anim.movimiento, R.drawable.black);
                }
            }
        }
    }

    private void generarDiamanteLava() {
        int aux = (int) (Math.random() * 25);

        diamante = imagen[aux].getId();

        do {
            aux = (int) (Math.random() * 25);
            lava = imagen[aux].getId();
        } while (diamante == lava);
    }

    private void ganaPierde(boolean gana) {

        String titulo;
        if (gana) {
            titulo = "Has ganado!";
            bloqueDiamante.start();
        } else {
            titulo = "Has perdido";
            bloqueLava.start();
        }

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

    private void loadAnimation(View view, int resAnim, int imagenFinal) {
        // Cargamos la animación
        animation = AnimationUtils.loadAnimation(this, resAnim);

        // Vinculación del escuchador
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((ImageView) view).setBackgroundResource(imagenFinal);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        // Comenzar la animación
        view.startAnimation(animation);
    }

    private void reiniciar() {
        for (ImageView iv : imagen) {
            iv.setBackgroundResource(R.drawable.stone);
            generarDiamanteLava();
        }
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
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            if (event.values[0] < MAX_LUX) {
                layout.setBackgroundColor((int) R.color.black);
            } else {
                layout.setBackgroundColor((int) R.color.white);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() { // primer plano
        super.onResume();
        sensorManager.registerListener(this, luxometro, SensorManager.SENSOR_DELAY_NORMAL); // registrar de nuevo el escuchador
    }

    @Override
    protected void onPause() { // segundo plano
        super.onPause();
        sensorManager.unregisterListener(this); // desvincular el escuchador
    }
}