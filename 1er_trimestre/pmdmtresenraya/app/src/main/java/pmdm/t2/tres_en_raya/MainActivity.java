package pmdm.t2.tres_en_raya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import pmdm.t2.pmdm_tresenraya.R;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    // Contadores para las victorias del jugador y de la máquina
    private int contadorHumano = 0;
    private int contadorAndroid = 0;

    private TextToSpeech sintetizador; // objeto TextToSpeech que usaremos para que la app hable

    // TextViews para actualizar las victorias
    private TextView tvHumano;
    private TextView tvAndroid;
    private TextView tvPartidas;

    private JuegoTresEnRaya mJuego;

    // Representa el estado interno del juego
    private JuegoTresEnRaya mjuego;

    // Botones del layout
    private Button mBotonesTablero[];

    // Texto informativo del estado del juego
    private TextView mInfoTexto;

    // Determina de quién será el primer turno (TURNO INICIAL)
    private char mTurno = JuegoTresEnRaya.JUGADOR;

    // Determina si ha acabado el juego
    private boolean gameOver = false;

    // Objetos que reproducen sonidos y música de fondo
    private MediaPlayer mJugadorMediaPlayer;
    private MediaPlayer mBackgroundMusicPlayer;

    public void onInit(int i) {
        // Idioma, velocidad y tono para hablar
        sintetizador.setLanguage(Locale.getDefault());
        sintetizador.setSpeechRate(1);
        sintetizador.setPitch(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sintetizador = new TextToSpeech(this, this);

        // Referencia de los botones del tablero
        mBotonesTablero = new Button[JuegoTresEnRaya.DIM_TABLERO];
        mBotonesTablero[0] = (Button) findViewById(R.id.one);
        mBotonesTablero[1] = (Button) findViewById(R.id.two);
        mBotonesTablero[2] = (Button) findViewById(R.id.three);
        mBotonesTablero[3] = (Button) findViewById(R.id.four);
        mBotonesTablero[4] = (Button) findViewById(R.id.five);
        mBotonesTablero[5] = (Button) findViewById(R.id.six);
        mBotonesTablero[6] = (Button) findViewById(R.id.seven);
        mBotonesTablero[7] = (Button) findViewById(R.id.eight);
        mBotonesTablero[8] = (Button) findViewById(R.id.nine);

        // Referencia de los textos informativos del estado del juego
        mInfoTexto = (TextView) findViewById(R.id.information);

        // Ejecución inicial de la lógica del videojuego
        mJuego = new JuegoTresEnRaya();

        tvHumano = findViewById(R.id.player_score);
        tvAndroid = findViewById(R.id.computer_score);
        tvPartidas = findViewById(R.id.tie_score);

        comenzarJuego();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Activación del efecto del sonido
        mJugadorMediaPlayer = MediaPlayer.create(this, R.raw.effect);

        // Activación de la música de fondo
        mBackgroundMusicPlayer = MediaPlayer.create(this, R.raw.background_music);

        // Comienzo de la música de fondo (con bucle)
        mBackgroundMusicPlayer.setLooping(true);
        mBackgroundMusicPlayer.start();
    }


    @Override
    protected void onPause() {
        super.onPause();

        // Liberar los recursos de efectos de sonido y música de fondo
        // Se para automáticamente y los libera
        mJugadorMediaPlayer.release();
        mBackgroundMusicPlayer.release();
    }


    private void comenzarJuego() {

        // Reinicio de la lógica del tablero
        mJuego.limpiarTablero();

        // Reinicio de las imágenes de los botones del layout
        for (int i = 0; i < mBotonesTablero.length; i++) {
            mBotonesTablero[i].setBackgroundResource(R.drawable.fondo_gris);
            mBotonesTablero[i].setEnabled(true);
        }

        // Toast para indicar que empieza el juego
        Toast.makeText(this, R.string.dialogoComienzaPartida,Toast.LENGTH_SHORT).show();

        // Reinicio de los botones del layout
        for (int i = 0; i < mBotonesTablero.length; i++) {
            mBotonesTablero[i].setText("");
            mBotonesTablero[i].setEnabled(true);
        }

        controlarTurno();
    }

    private void controlarTurno() {
        if (mTurno == JuegoTresEnRaya.JUGADOR) {
            mInfoTexto.setText(R.string.primero_jugador);

        } else if (mTurno == JuegoTresEnRaya.MAQUINA) {

            mInfoTexto.setText(R.string.primero_maquina);

            // 1. Determinamos la posición según nivel
            int casilla = mJuego.getMovimientoMaquina();

            // 2. Actualización del layout
            colocarFichaEnTablero(JuegoTresEnRaya.MAQUINA, casilla);

            // 3. Actualización del turno: JUGADOR
            if (!gameOver) {
                mTurno = JuegoTresEnRaya.JUGADOR;
                mInfoTexto.setText(R.string.turno_jugador);
            }
        }
    }

    private void colocarFichaEnTablero(char jugador, int casilla) {
        // 1. Mueve la ficha según la lógica
        mJuego.moverFicha(jugador, casilla);

        // 2. Actualización y representación en el layout
        // Se desactiva el control del botón determinado
        mBotonesTablero[casilla].setEnabled(false);
        //mBotonesTablero[casilla].setText(String.valueOf(jugador));

        // 3. Se representa la ficha
        if (jugador == JuegoTresEnRaya.JUGADOR) {
            // mBotonesTablero[casilla].setTextColor(Color.rgb(0,200,0));
            mBotonesTablero[casilla].setBackgroundResource(R.drawable.jugador);

            // Comienza el efecto de sonido
            mJugadorMediaPlayer.start();
            mInfoTexto.setText(R.string.turno_maquina);
        } else {
            mBotonesTablero[casilla].setBackgroundResource(R.drawable.maquina);
        }

        // 4. Se comprueba: ESTADO DEL JUEGO (SI AUN NO SE HA ACABADO SE CONTINUA)
        int estadoJuego = comprobarEstadoJuego();

        if (estadoJuego == 1 || estadoJuego == 2) {
            gameOver();
        } else if (estadoJuego == 0) {
            if (jugador == JuegoTresEnRaya.JUGADOR) {
                mTurno = JuegoTresEnRaya.MAQUINA;
            } else if (jugador == JuegoTresEnRaya.MAQUINA) {
                mTurno = JuegoTresEnRaya.JUGADOR;
            }
            // 5. Siguiente turno
            controlarTurno();
        }
    }

    private int comprobarEstadoJuego() {
        // 1. Comprobar el estado principal del tablero
        int estado = mJuego.comprobarGanador();

        // 2. Representar estado del juego
        if (estado == 1) {
            // Actualiza el texto
            mInfoTexto.setText(R.string.result_human_wins);
            // Aumenta el contador de victorias del jugador
            contadorHumano++;
            // Actualiza el textview
            tvHumano.setText(String.valueOf(contadorHumano));

            Toast.makeText(this, R.string.result_human_wins,Toast.LENGTH_SHORT).show();

        } else if (estado == 2) {
            // Actualiza el texto
            mInfoTexto.setText(R.string.result_computer_wins);
            // Aumenta el contador de victorias de la máquina
            contadorAndroid++;
            // Actualiza el textview
            tvAndroid.setText(String.valueOf(contadorAndroid));

            Toast.makeText(this, R.string.result_computer_wins,Toast.LENGTH_SHORT).show();
        }

        // Actualiza el textview de contador de partidas
        tvPartidas.setText(String.valueOf(contadorAndroid+contadorHumano));
        return estado;
    }

    private void gameOver() {
        // Actualizo la variable del control de finalización del juego
        gameOver = true;

        // Reinicio la variable del control de finalización del juego
        for (int i = 0; i < mBotonesTablero.length; i++) {
            mBotonesTablero[i].setEnabled(false);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.empezarPartidaNueva);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                comenzarJuego();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void onClick(View boton) {
        // 1. Localizamos cuál es el botón pulsado y su número de casilla correcto
        int id = boton.getId();
        String descripcionBoton = ((Button)findViewById(id)).getContentDescription().toString();
        int casilla = Integer.parseInt(descripcionBoton) - 1;

        // 2. Determinamos si es posible colocar la ficha en la casilla
        if (mBotonesTablero[casilla].isEnabled()) {
            // 3. Mueve y representa la ficha JUGADOR en la casilla
            colocarFichaEnTablero(JuegoTresEnRaya.JUGADOR, casilla);
        }

    }

    public void botonNewGame(View boton) {
        // Pregunta si quiere empezar otra partida, en caso afirmativo empieza otra partida, limpiando el tablero
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.empezarPartidaNueva);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                comenzarJuego();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    public void botonRestartGame(View boton) {

        hablar(this.getString(R.string.preguntaReinciar));

        // Pregunta si quiere reiniciar la partida, en caso afirmativo reinicia los contadores y actualiza los textviews
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.reiniciar);
        builder.setMessage(R.string.reiniciarMsg);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        contadorHumano=0;
                        contadorAndroid=0;

                        tvHumano.setText("0");
                        tvAndroid.setText("0");
                        tvPartidas.setText("0");

                        comenzarJuego();

                    }
                }
        );
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    // Método para hablar
    public void hablar(String msg) {

        sintetizador.speak(msg, TextToSpeech.QUEUE_FLUSH,null,null);
    }
}