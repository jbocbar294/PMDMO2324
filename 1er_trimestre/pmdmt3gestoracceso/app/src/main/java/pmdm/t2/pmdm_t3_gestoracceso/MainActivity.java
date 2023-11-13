package pmdm.t2.pmdm_t3_gestoracceso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void botonSalir(View view) {
        Toast.makeText(this, "Saliendo...", Toast.LENGTH_SHORT).show();
        finish();
    }
    
}