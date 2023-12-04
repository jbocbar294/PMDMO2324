package iesmm.pmdm.t4_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 1. recuperar datos del bundle
        Bundle bundle = getIntent().getExtras();

        // 2. localizar el textview en el layout y poner texto
        if (bundle != null) {
            String username = bundle.getString("username");
            TextView tv = findViewById(R.id.welcome);
            tv.setText("Bienvenido, " + username);
        }
    }


}