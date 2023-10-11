package pmdm.t2.pmdm_t2_xilofono;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    
    private TextToSpeech sintetizador; // objeto TextToSpeech que usaremos para que la app hable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getResources();

        sintetizador = new TextToSpeech(this, this);
    }

    protected void onPostResume() {
        super.onPostResume();
    }
    public void onInit(int i) {
        sintetizador.setLanguage(Locale.getDefault());
        sintetizador.setSpeechRate(1);
        sintetizador.setPitch(1);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void tararearNota(View view) {

        if (view.getId() == R.id.button1) {
            sintetizador.speak("do", TextToSpeech.QUEUE_FLUSH,null,null);
        }

        if (view.getId() == R.id.button2) {
            sintetizador.speak("re", TextToSpeech.QUEUE_FLUSH,null,null);
        }

        if (view.getId() == R.id.button3) {
            sintetizador.speak("mi", TextToSpeech.QUEUE_FLUSH,null,null);
        }

        if (view.getId() == R.id.button4) {
            sintetizador.speak("fa", TextToSpeech.QUEUE_FLUSH,null,null);
        }

        if (view.getId() == R.id.button5) {
            sintetizador.speak("sol", TextToSpeech.QUEUE_FLUSH,null,null);
        }

        if (view.getId() == R.id.button6) {
            sintetizador.speak("la", TextToSpeech.QUEUE_FLUSH,null,null);
        }

        if (view.getId() == R.id.button7) {
            sintetizador.speak("si", TextToSpeech.QUEUE_FLUSH,null,null);
        }

        if (view.getId() == R.id.button8) {
            sintetizador.speak("do", TextToSpeech.QUEUE_FLUSH,null,null);
        }

        if (view.getId() == R.id.button9) {
            sintetizador.speak("re", TextToSpeech.QUEUE_FLUSH,null,null);
        }

        if (view.getId() == R.id.button10) {
            sintetizador.speak("mi", TextToSpeech.QUEUE_FLUSH,null,null);
        }
    }
}