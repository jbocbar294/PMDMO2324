package pmdm.t2.pmdm_t3_gestoracceso;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.time.LocalDate;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        tableLayout = findViewById(R.id.tableLayout);
    }

    // método que crea el menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        try {

            if (id == R.id.registrar) {

                insertarNuevoRegistro("entrada");
            }

            if (id == R.id.salir) {

                insertarNuevoRegistro("salida");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }


    public void insertarNuevoRegistro (String tipo) {

        // fichero donde se guardarán los registros de entrada y salida
        File f = new File(getFilesDir(), "registrosES.dat");

        Date date = new Date();

        // objeto para asignar el formato de fecha que queremos obtener y lo guardamos en un String
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yy");
        String fechaActual = formato.format(date);

        // se vuelve a usar el mismo objeto para guardar la hora en otro String
        formato = new SimpleDateFormat("HH:mm");
        String horaActual = formato.format(date);

        try {
            // objeto con el que escribiremos en el archivo
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(f, true));

            // escribe el tipo de entrada, fecha y hora
            dos.writeUTF(tipo + " " + fechaActual  + " " + horaActual + "\n");

            dos.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // objeto tablerow para poder representar los datos en formato tabla
        TableRow tr = new TableRow(this);

        // textviews que tienen el tipo, la fecha y la hora
        TextView tvTipo = new TextView(this);
        TextView tvFecha = new TextView(this);
        TextView tvHora = new TextView(this);

        // asigna padding a los dos primeros textviews solo en la derecha
        tvTipo.setPadding(0,0,10,0);
        tvFecha.setPadding(0,0,10,0);

        // asignamos texto a los textviews
        tvTipo.setText(tipo);
        tvFecha.setText(fechaActual);
        tvHora.setText(horaActual);

        // añadimos los textviews al tablerow
        tr.addView(tvTipo);
        tr.addView(tvFecha);
        tr.addView(tvHora);

        // añadimos el tablerow al tablelayout
        tableLayout.addView(tr);

        // si se pulsa el botón de salida, cierra la app
        if (tipo.equals("salida")) {
            finish();
        }
    }
}