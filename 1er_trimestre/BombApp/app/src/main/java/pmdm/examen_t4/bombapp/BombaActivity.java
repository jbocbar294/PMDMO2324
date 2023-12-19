package pmdm.examen_t4.bombapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BombaActivity extends AppCompatActivity {

    private TextView cronometro;
    private ImageView bomba;
    private MediaPlayer sonidoBomba;
    private int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Obtenemos el contador
        recuperarContador();

        // Obtenemos el paquete
        Bundle bundle = this.getIntent().getExtras();

        // Obtenemos los elementos del layout
        cronometro = findViewById(R.id.cronometer);
        bomba = findViewById(R.id.imageView);

        if (bundle != null) {
            // Actualizamos el contador
            contador++;
            guardarContador();

            // Si el paquete no esta vacio
            int segundos = bundle.getInt("segundos");

            // Creamos el sonido
            sonidoBomba = MediaPlayer.create(this, R.raw.bomb);

            Cronometro c = new Cronometro();
            c.execute(segundos);

        } else {

        }

    }

    public void guardarContador() {
        SharedPreferences configuracion = this.getSharedPreferences("contador", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = configuracion.edit();
        editor.putInt("contador", contador);
        editor.commit();
        // Mostar un mensaje en el log
        Log.i(":::guardarContador", "Se ha el contador");
    }

    public void recuperarContador() {
        SharedPreferences configuracion = this.getSharedPreferences("contador", Context.MODE_PRIVATE);

        contador = configuracion.getInt("contador", 0);

        // Mostar un mensaje en el log
        Log.i(":::recuperarContador", "Se ha recuperado el contador");
    }

    private class Cronometro extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            // Cuando termina la ejecucion se reproduce el sonido
            // y se cambia el sprite
            sonidoBomba.start();
            bomba.setImageResource(R.drawable.explotion);
        }

        @Override
        protected Void doInBackground(Integer... values) {

            int segundos = values[0];

            for (int i = 0; i < values[0]; i++) {
                // Actualizamos el TextView
                publishProgress(segundos);
                // Esperamos un segundo
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // Decrementamos los segundos
                segundos--;
            }
            // Actualizamos el TextView
            publishProgress(segundos);

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Actualizamos el TextView
            cronometro.setText(String.valueOf(values[0]));
        }
    }

}