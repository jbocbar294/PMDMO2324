package iesmm.pmdm.pmdm_t4_users;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    File archivoUsers;
    Button btnLogin;
    String usuario, contrasenya;
    EditText etEmail, etContrasenya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        archivoUsers = new File(getFilesDir() + File.separator + "users.csv");
        if (!archivoUsers.exists()) {
            Toast.makeText(this, "No existe fichero de usuarios", Toast.LENGTH_SHORT).show();
            btnLogin.setAlpha(0.5f);
        } else {
            btnLogin.setOnClickListener(this);
        }
        etEmail = findViewById(R.id.etUsuario);
        etContrasenya = findViewById(R.id.etContrasenya);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnLogin.getId()) {
            usuario = String.valueOf(etEmail.getText());
            contrasenya = String.valueOf(etContrasenya.getText());
            String[] datos = new String[5];
            try {
                BufferedReader br = new BufferedReader(new FileReader(archivoUsers));
                String linea;
                while ((linea = br.readLine()) != null) {
                    datos = linea.split(";");
                    if (usuario.equals(datos[0]) && contrasenya.equals(datos[1])) {
                        Intent intent = new Intent(this, MapsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("usuario",usuario);
                        bundle.putString("contrasenya",contrasenya);
                        intent.putExtras(bundle);
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
