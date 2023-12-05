package pmdm.t2.pmdm_t3_preferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            int colorGuardado = savedInstanceState.getInt("colorGuardado");
            View container = findViewById(R.id.container);
            container.setBackgroundColor(colorGuardado);
        } else {
            cambiarFondo((int) (Math.random()*256),(int) (Math.random()*256),(int) (Math.random()*256));
        }

        // asociar métodos de escucha
        Button boton = (Button) this.findViewById(R.id.save);
        boton.setOnClickListener(this);
        
        boton = (Button) findViewById(R.id.restore);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarToast("Has pulsado el botón de Recuperar Color");
            }
        });

    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        View container = findViewById(R.id.container);
        int color = ((ColorDrawable) container.getBackground()).getColor();
        outState.putInt("colorGuardado", color);
    }


    public void mostrarToast (String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }


    private void cambiarFondo (int red, int green, int blue) {

        LinearLayout layout = (LinearLayout) this.findViewById(R.id.container);

        //layout.setBackground(Color.BLUE);
        layout.setBackgroundColor(Color.rgb(red, green, blue));
    }


    public void guardarPreferencias() {
        // recuperar el color actual del layout
        LinearLayout layout = (LinearLayout) this.findViewById(R.id.container);

        int color = ((ColorDrawable) layout.getBackground()).getColor();

        SharedPreferences preferencias = this.getSharedPreferences("colores", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferencias.edit();

        editor.putInt("fondo", color);

        editor.commit();

        Toast.makeText(this, "Color Guardado", Toast.LENGTH_SHORT).show();
    }


    public void recuperarPreferencias() {
        // recupera valores de configuración
        SharedPreferences preferencias = this.getSharedPreferences("colores", Context.MODE_PRIVATE);

        // recupera el valor
        int color = preferencias.getInt("fondo",0);

        // define el color del layout
        if (color != 0) {
            LinearLayout layout = (LinearLayout) this.findViewById(R.id.container);
            layout.setBackgroundColor(color);
            Toast.makeText(this, "Se ha cambiado el color", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(this, "NO se ha cambiado el color", Toast.LENGTH_SHORT);
        }


    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.save) {

            Toast.makeText(this, "Almacenar Color", Toast.LENGTH_SHORT).show();

            guardarPreferencias();

        } else {

            Toast.makeText(this, "Recuperar Color", Toast.LENGTH_SHORT).show();

        }
    }
}