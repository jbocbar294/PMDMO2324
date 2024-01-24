package iesmm.pmdm.pmdm_micine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button botonAñadir;
    GridLayout gridLayout;
    int idDinamico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonAñadir = findViewById(R.id.botonAñadir);
        botonAñadir.setOnClickListener(this);

        gridLayout = findViewById(R.id.sala);

        idDinamico = 0;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == botonAñadir.getId()) {
            ImageView imageView = new ImageView(this);
            imageView.setId(idDinamico);
            imageView.setBackgroundResource(R.drawable.inferno);
            imageView.setMaxWidth(5);
            imageView.setMaxHeight(5);
            imageView.setOnClickListener(this);
            gridLayout.addView(imageView);
            idDinamico++;
        } else {
            Toast.makeText(this, "ID: " + id, Toast.LENGTH_SHORT).show();
        }
    }
}