package pmdm.t2.tres_en_raya;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pmdm.t2.pmdm_tresenraya.R;

public class MainActivity extends AppCompatActivity {

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

    private MediaPlayer mJugadorMediaPlayer;
    private MediaPlayer mBackgroundMusicPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        comenzarJuego();
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
        } else {
            // mBotonesTablero[casilla].setTextColor(Color.rgb(200, 0 ,0));
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
            mInfoTexto.setText(R.string.result_human_wins);
        } else if (estado == 2) {
            mInfoTexto.setText(R.string.result_computer_wins);
        }
        return estado;
    }

    private void gameOver() {
        // Actualizo la variable del control de finalización del juego
        gameOver = true;

        // Reinicio la variable del control de finalización del juego
        for (int i = 0; i < mBotonesTablero.length; i++) {
            mBotonesTablero[i].setEnabled(false);
        }
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
}