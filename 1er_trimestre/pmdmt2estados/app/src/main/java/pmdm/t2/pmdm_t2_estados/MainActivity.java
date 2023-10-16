package pmdm.t2.pmdm_t2_estados;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech sintetizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getResources();

        sintetizador = new TextToSpeech(this, this);

    }

    public void onInit(int i) {
        //para el sintetizador al implementar esta interfaz se crea este método automáticamente
        sintetizador.setLanguage(Locale.getDefault());
        sintetizador.setPitch(1); // cambiar tono
        sintetizador.setSpeechRate(1); // cambiar velocidad al hablar
    }

    protected void onStart() {
        super.onStart();
        notificacion("onStart");
        habla("onStart");
    }

    protected void onPostResume() {
        super.onPostResume();
        notificacion("onPostResume");
        habla("onPostResume, vuelvo al primer plano");
    }

    protected void onPause() {
        super.onPause();
        notificacion("onPause");
        habla("onPause");
    }

    protected void onStop() {
        super.onStop();
        notificacion("onStop");
        habla("onStop");
    }

    protected void onDestroy() {
        super.onDestroy();
        notificacion("onDestroy");
        habla("onDestroy");
    }

    private void notificacion(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();    }

    private void habla(String msg) {
        sintetizador.speak(msg, TextToSpeech.QUEUE_FLUSH,null,null);
    }

}