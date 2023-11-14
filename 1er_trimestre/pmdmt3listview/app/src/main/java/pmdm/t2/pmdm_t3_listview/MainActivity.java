package pmdm.t2.pmdm_t3_listview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    // arraylist que contiene los nifs de la lista
    private ArrayList<Amigo> listaAmigos = new ArrayList<>();

    private AmigoAdapter adaptador;
    // array de chars, cada posición contiene la letra que corresponde al numero del nif entre 23
    char[] letras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // listview que está en cada elemento de la lista, que contiene los nifs
        ListView lvLista = findViewById(R.id.listView1);

        adaptador = new AmigoAdapter(this, listaAmigos);

        lvLista.setAdapter(adaptador);

    }

    public void putItem (View view) {
        // edittext donde el usuario escribe el nif
        EditText etNif = findViewById(R.id.editText);

        // nif extraído del edittext
        String nif = String.valueOf(etNif.getText());

        // pone el nif en mayúsculas
        nif = nif.toUpperCase();

        Amigo amigo = new Amigo(nif);

        boolean encontrado = false;

        int contador = 0;

        // bucle que recorre el arraylist de Amigo, si ve que el nif ya está en la lista,
        // la variable encontrado es true
        if (listaAmigos.size() != 0) {

            do {

                if (listaAmigos.get(contador).getNif().equals(nif)) {
                    encontrado = true;
                }

                contador++;

            } while (contador<listaAmigos.size());

        }

        //control de errores: la condición comprueba que...
        if (nif.equals("")) { // no se introduzcan nifs vacíos
            Toast.makeText(this, "Debes añadir un NIF", Toast.LENGTH_SHORT).show();
        } else if (!nif.matches("\\d{8}[a-zA-Z]")) { // el nif tiene el formato correcto
            Toast.makeText(this, "El NIF introducido no es válido", Toast.LENGTH_SHORT).show();
        } else if (encontrado) { // el nif ya está en la lista
            Toast.makeText(this, "El NIF introducido ya está en la lista", Toast.LENGTH_SHORT).show();
        } else {

            // obtiene el resto del número del nif entre 23
            int restoNumeroNif = Integer.parseInt(nif.substring(0,8))%23;

            // comprueba que la letra introducida es igual a la letra en la posición X,
            // siendo X el resto del número entre 23
            if (nif.charAt(8) == letras[restoNumeroNif]) {

                // se añade el objeto
                listaAmigos.add(amigo);

                // ordena el arraylist
                listaAmigos.sort(Comparator.comparing(Amigo::getNif));

                // actualiza la tabla
                adaptador.notifyDataSetChanged();

                etNif.setText("");

                Toast.makeText(this, "NIF " + nif + " añadido correctamente", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "El NIF introducido no es válidooo", Toast.LENGTH_SHORT).show();

            }

        }
    }

    public void borrarElemento (View view) {

        // obtiene el nif del textview pulsado
        TextView tv = findViewById(R.id.nif);

        // cuadro de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Borrar elemento");

        builder.setMessage("Quieres borrar el NIF " + tv.getText() + "?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                int contador = 0;

                // bucle que recorre el arraylist, cuando encuentra el objeto con el nif que se quiere eliminar, lo elimina
                do {

                    if (tv.getText().equals(listaAmigos.get(contador).getNif())) {
                        listaAmigos.remove(contador);
                    }

                    contador++;

                } while (contador < listaAmigos.size());

                adaptador.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void clearItems (View view) {

        // comprueba que el arraylist no está vacío
        if (listaAmigos.size() != 0) {

            File raiz = this.getExternalFilesDir(null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Borrar lista");

            builder.setMessage("Quieres borrar la lista de NIFs?");

            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    try {
                        if (raiz.canWrite()) {

                            // obtiene la fecha actual con formato DDMMYYYY
                            LocalDate localDate = LocalDate.now();
                            String fecha = localDate.format(DateTimeFormatter.ofPattern("ddMMyyyy"));

                            File file = new File(raiz, "clientes-" + fecha + ".txt");

                            if (!file.exists()) {
                                file.createNewFile();
                            }


                            BufferedWriter out = new BufferedWriter(new FileWriter(file));

                            int contador = 0;

                            // bucle que escribe en el fichero todos los nifs del arraylist
                            do {

                                out.write(listaAmigos.get(contador).getNif() + "\n");

                                contador++;

                            } while (contador < listaAmigos.size());

                            out.close();

                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    listaAmigos.clear();

                    adaptador.notifyDataSetChanged();

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            
        } else {
            Toast.makeText(this, "La lista ya está vacía", Toast.LENGTH_SHORT).show();
        }

    }


}