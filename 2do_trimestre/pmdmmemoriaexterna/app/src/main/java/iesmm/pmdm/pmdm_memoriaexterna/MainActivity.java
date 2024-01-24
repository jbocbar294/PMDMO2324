package iesmm.pmdm.pmdm_memoriaexterna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(":::MEMORIA EXTERNA", String.valueOf(getExternalFilesDir(null)));

    }
}