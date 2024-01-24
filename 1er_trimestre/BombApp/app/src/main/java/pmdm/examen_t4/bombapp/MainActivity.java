package pmdm.examen_t4.bombapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText numero;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtenemos los elementos del layout
        numero = findViewById(R.id.numero);
        boton = findViewById(R.id.button);

        // Mostramos el mensaje de segundos maximos permitidos
        Toast.makeText(getApplicationContext(), "El tiempo maximo permitido es de 60 segundos.",
                Toast.LENGTH_LONG).show();

        // Añadimos el listener al boton
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los segundos
                int segundos = Integer.parseInt(String.valueOf(numero.getText()));

                // Comprobar que el número de segundos está entre 1 y 60
                if (segundos > 0) {
                    if (segundos <= 60) {
                        // Preparar el paquete de datos a enviar
                        Bundle bundle = new Bundle();
                        bundle.putInt("segundos", segundos);

                        // Llamar siguiente actividad
                        Intent intent = new Intent(getApplicationContext(), BombaActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "El tiempo maximo permitido es de 60 segundos.",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "El tiempo minimo permitido es de 1 segundo.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}