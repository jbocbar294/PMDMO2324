package pmdm.t2.pmdm_t3_configapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.load_settings) {
            Toast.makeText(this, "Botón Load", Toast.LENGTH_SHORT).show();

            // recupera valores de configuración
            SharedPreferences preferencias = this.getSharedPreferences("configuracion", Context.MODE_PRIVATE);

            // recupera el valor
            String email = preferencias.getString("email","");
            String password = preferencias.getString("password","");

            //
            EditText editTextEmail = findViewById(R.id.campo_email);
            editTextEmail.setText(email);

            //
            EditText editTextPassword = findViewById(R.id.campo_password);
            editTextPassword.setText(password);

        } else if (id == R.id.save_settings) {
            Toast.makeText(this, "Botón Save", Toast.LENGTH_SHORT).show();

            SharedPreferences preferencias = this.getSharedPreferences("configuracion", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferencias.edit();

            editor.putString("email", String.valueOf(((EditText) findViewById(R.id.campo_email)).getText()));
            editor.putString("password", String.valueOf(((EditText) findViewById(R.id.campo_password)).getText()));

            editor.commit();

        } else if (id == R.id.reset_settings) {
            Toast.makeText(this, "Botón Reset", Toast.LENGTH_SHORT).show();

            EditText editTextEmail = findViewById(R.id.campo_email);
            editTextEmail.setText("");

            //
            EditText editTextPassword = findViewById(R.id.campo_password);
            editTextPassword.setText("");
        }

        return super.onOptionsItemSelected(item);
    }
}