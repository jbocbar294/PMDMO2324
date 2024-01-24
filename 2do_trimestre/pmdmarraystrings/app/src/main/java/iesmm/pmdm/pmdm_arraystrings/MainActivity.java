package iesmm.pmdm.pmdm_arraystrings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    String[] animales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animales = getResources().getStringArray(R.array.animals);

        for (String animale : animales) {
            Log.i(":::ANIMALES",animale);
        }
    }
}