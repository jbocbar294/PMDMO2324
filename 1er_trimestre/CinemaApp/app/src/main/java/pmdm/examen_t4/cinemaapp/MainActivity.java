package pmdm.examen_t4.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import pmdm.examen_t4.cinemaapp.modelo.Sala;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GridLayout gridLayout;
    private ImageView butaca;
    private TextView espacio;
    private TextView asientosReservados;
    private FrameLayout frameLayout;
    private TextView precioAsiento;
    private TextView precioTotal;
    private final float PRECIO = 4.5f;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(":::Directorio Memoria Externa", String.valueOf(getExternalFilesDir(null)));

        // Obtenemos los elementos de la interfaz
        gridLayout = findViewById(R.id.sala_butacas);
        frameLayout = findViewById(R.id.frame_layout);
        precioAsiento = findViewById(R.id.reserva_precio_butaca);
        precioTotal = findViewById(R.id.reserva_precio_butaca_total);

        // Ocultamos el Table Layout donde se encuentran los precios
        tableLayout = (TableLayout) frameLayout.getChildAt(0);
        tableLayout.setVisibility(View.INVISIBLE);

        // Asignamos el listener al boton de compra
        Button botonCompra = findViewById(R.id.button);
        botonCompra.setOnClickListener(this);

        // Insertamos los datos de la cabecera
        insertarDatosCabecera();

        // Insertamo las filas
        insertarFilas();
    }

    private void insertarDatosCabecera() {
        // Cargamos las salas del fichero csv
        ArrayList<Sala> salas = cargarSalas();

        // Ahora recorreriamos el array tantas veces como salas nos haya generado
        // pero para el ejemplo cargaremos solo 1 sala
        if (salas.size() > 0) {
            // Obtenemos la primera sala
            Sala sala = salas.get(0);

            // Obtenemos los elementos del layout
            TextView numSala = findViewById(R.id.sala_numero);
            TextView horaSala = findViewById(R.id.sala_hora);
            TextView asientosLibres = findViewById(R.id.sala_numero_asientos);
            asientosReservados = findViewById(R.id.sala_butacas_reservadas);

            // Actualizamos los datos
            numSala.setText(sala.getNumero());
            horaSala.setText(horaSala.getText() + " " + sala.getHora());
            asientosLibres.setText(sala.getAsientosLibres());
            asientosReservados.setText(sala.getAsientosReservados());
        } else {
            Log.e(":::insertarDatosCabecera", "No se han cargado las salas del fichero csv.");
        }

    }

    private void insertarFilas() {
        // Cargamos las salas del fichero csv
        ArrayList<Sala> salas = cargarSalas();

        // Ahora recorreriamos el array tantas veces como salas nos haya generado
        // pero para el ejemplo cargaremos solo 1 sala
        if (salas.size() > 0) {
            // Obtenemos la primera sala
            Sala sala = salas.get(0);
            int id = 0;

            for (int i = 0; i < Integer.parseInt(sala.getNumeroFilas()); i++) {
                for (int j = 0; j < 6; j++) {
                    // Creamos la butaca
                    butaca = new ImageView(this);
                    butaca.setImageResource(R.drawable.butacalibre);
                    butaca.setId(id);
                    butaca.setOnClickListener(this);
                    // Añadimos la butaca al layout
                    gridLayout.addView(butaca);

                    // Si se han insertado 3 butacas introducimos un espacio
                    if (j == 2) {
                        // Creamos el espacio
                        espacio = new TextView(this);
                        espacio.setText("   ");
                        // Añadimos el espacio al layout
                        gridLayout.addView(espacio);
                    }

                    // Incrementamos el id
                    id++;
                }
                // Incrementamos el id
                id++;
            }

        } else {
            TextView error = new TextView(this);
            error.setText("No se ha podido cargar los datos de la sala.");
            gridLayout.addView(error);
            Log.e(":::insertarFilas", "No se han cargado las salas del fichero csv.");
        }

    }

    private ArrayList<Sala> cargarSalas() {
        ArrayList<Sala> salas = new ArrayList<>();

        try {
            // Nombre del fichero
            String filename = "cine.csv";
            // Ruta del fichero
            File root = getExternalFilesDir(null);
            // Comprobamos que tenemos permisos de escritura
            if (root.canRead()) {
                // Creamos el fichero
                File file = new File(root, filename);
                // Creamos el el BufferedReader
                BufferedReader br = new BufferedReader(new FileReader(file));
                Log.i(":::debug", String.valueOf(br.ready()));
                while (br.ready()) {
                    String[] linea = br.readLine().split(";");
                    salas.add(new Sala(linea[0], linea[1], linea[2], linea[3], linea[4], linea[5]));
                }
            } else {
                Log.e(":::cargarSala", "No se tiene permisos de lectura sobre el archivo.");
            }
        } catch (IOException e) {
            // Mostramos un mensaje en el Log
            Log.wtf(":::cargarSala", "Error al leer los datos de la memoria externa.", e);
        }

        return salas;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button) {
            // Actualizamos el precio por butaca
            precioAsiento.setText(String.valueOf(PRECIO));
            // Actualizamos el precio total
            int numAsientosReservados = Integer.parseInt(String.valueOf(asientosReservados.getText()));
            float total = numAsientosReservados * PRECIO;
            if (total > 0) {
                precioTotal.setText(String.valueOf(total));
            } else {
                precioTotal.setText("Ninguna butaca seleccionada.");
            }
            // Hacemos visible los elementos
            tableLayout.setVisibility(View.VISIBLE);
        } else {
            // Actualizamos la imagen de la butaca
            ImageView butacaSeleccionada = findViewById(id);
            butacaSeleccionada.setImageResource(R.drawable.butacareservada);
            // Incrementamos el valor
            int num = Integer.parseInt(String.valueOf(asientosReservados.getText()));
            num++;
            // Actualizamos el layout
            asientosReservados.setText(String.valueOf(num));
        }

    }
}