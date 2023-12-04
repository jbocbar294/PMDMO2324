package iesmm.pmdm.t4_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = this.findViewById(R.id.boton_iniciar_sesion);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. obtengo la referencia del valor de cada editText
                String username = ((EditText)findViewById(R.id.input_usuario)).getText().toString();
                String password = ((EditText)findViewById(R.id.input_contrasena)).getText().toString();;

                // 2. comprobar si los datos son correctos
                if (getAccess(username, password)) {
                    // 3. Preparar el paquete de datos a enviar
                    Bundle b = new Bundle();
                    b.putString("username", username);

                    // 4. llamar a la siguiente actividad
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Snackbar.make(v, "Error de acceso", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    public boolean getAccess(String username, String password) {



        return true;
    }



}