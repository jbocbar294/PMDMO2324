package iesmm.pmdm.pmdm_t4_users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    File archivoUsers;
    Button btnLogin;
    String email, contrasenya;
    EditText etEmail, etContrasenya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        archivoUsers = new File(getFilesDir() + File.separator + "users.csv");
        if (archivoUsers.exists() && archivoUsers.isFile()) { // Controla que es un archivo y que existe
            btnLogin.setOnClickListener(this); // Asigna el escuchador al botón
        } else { // Si no se cumple, muestra un Toast y baja la opacidad del botón a la mitad
            Toast.makeText(this, "No existe fichero de usuarios", Toast.LENGTH_SHORT).show();
            btnLogin.setAlpha(0.5f);
        }
        // EditTexts del email y la contraseña
        etEmail = findViewById(R.id.etEmail);
        etContrasenya = findViewById(R.id.etContrasenya);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnLogin.getId()) {
            // Obtiene los valores introducidos del email y de la contraseña
            email = String.valueOf(etEmail.getText());
            contrasenya = String.valueOf(etContrasenya.getText());
            String[] datos = new String[5];
            try {
                BufferedReader br = new BufferedReader(new FileReader(archivoUsers));
                String linea;
                while ((linea = br.readLine()) != null) { // Mientras pueda leer...
                    datos = linea.split(";"); // Almacena los datos del usuario actual en un array
                    // Si el email y la contraseña introducidos son iguales a los del fichero...
                    if (email.equals(datos[2]) && contrasenya.equals(datos[1])) {
                        // Intent para la siguiente clase
                        Intent intent = new Intent(this, MapsActivity.class);
                        // Bundle con el email y la contraseña
                        Bundle bundle = new Bundle();
                        bundle.putString("email", email);
                        bundle.putString("contrasenya",contrasenya);
                        // Asigna el bundle al intent
                        intent.putExtras(bundle);
                        // Inicia el intent
                        startActivity(intent);
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Snackbar.make(findViewById(android.R.id.content), "Usuario no válido", Snackbar.LENGTH_LONG).show();
        }
    }
}
