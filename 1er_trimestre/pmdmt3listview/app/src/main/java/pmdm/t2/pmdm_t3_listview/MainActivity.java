package pmdm.t2.pmdm_t3_listview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Amigo> listaAmigos = new ArrayList<>();

    private AmigoAdapter adaptador;

    char[] letras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adaptador = new AmigoAdapter(this, listaAmigos);

        ListView lvLista = (ListView) findViewById(R.id.listView1);
        lvLista.setAdapter(adaptador);

    }

    public void putItem (View view) {

        EditText etNif = findViewById(R.id.editText);

        String nif = String.valueOf(etNif.getText());

        nif = nif.toUpperCase();

        Amigo amigo = new Amigo(nif);

        amigo.setNif(nif);

        boolean encontrado = false;

        int contador = 0;

        boolean numeroCorrecto = false;

        if (listaAmigos.size() != 0) {

            do {

                if (listaAmigos.get(contador).getNif().equals(nif)) {
                    encontrado = true;
                }

                contador++;

            } while (contador<listaAmigos.size());

        }

        if (nif.equals("")) {
            Toast.makeText(this, "Debes añadir un NIF", Toast.LENGTH_SHORT).show();
        } else if (!nif.matches("\\d{8}[a-zA-Z]")) {
            Toast.makeText(this, "El NIF introducido no es válido", Toast.LENGTH_SHORT).show();
        } else if (encontrado) {
            Toast.makeText(this, "El NIF introducido ya está en la lista", Toast.LENGTH_SHORT).show();
        } else {

            int restoNumeroNif = Integer.parseInt(nif.substring(0,8))%23;

            if (nif.charAt(8) == letras[restoNumeroNif]) {

                listaAmigos.add(amigo);

                listaAmigos.sort(Comparator.comparing(Amigo::getNif));

                adaptador.notifyDataSetChanged();

                etNif.setText("");

                Toast.makeText(this, "NIF " + nif + " añadido correctamente", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "El NIF introducido no es válidooo", Toast.LENGTH_SHORT).show();

            }

        }
    }

    public void borrarElemento (View view) {

        TextView tv = findViewById(R.id.nif);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Borrar elemento");

        builder.setMessage("Quieres borrar el NIF " + tv.getText() + "?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                int contador = 0;

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

        

        listaAmigos.clear();

        adaptador.notifyDataSetChanged();


    }


}