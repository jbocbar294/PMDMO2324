package iesmm.pmdm.pmdm_t4_agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerViewContacto;
    private RecyclerViewAdaptador adaptadorContacto;
    private File archivoContactos;
    List<ContactoModelo> listaContactos;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        archivoContactos = new File(getFilesDir() + File.separator + "contactos.csv");
        if (archivoContactos.exists()) {
            listaContactos = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(archivoContactos));
                String linea;
                String[] datos = new String[3];
                while ((linea = br.readLine()) != null) {
                    datos = linea.split(";");
                    listaContactos.add(new ContactoModelo(datos[0], datos[1], datos[2]));
                }
                for (int i = 0; i < listaContactos.size() - 1; i++) {
                    for (int j = 0; j < listaContactos.size() - i - 1; j++) {
                        // Compara los elementos vecinos y realiza el intercambio si es necesario
                        if (listaContactos.get(j).getNombre().compareTo(listaContactos.get(j + 1).getNombre()) > 0) {
                            // Intercambio
                            ContactoModelo temp = listaContactos.get(j);
                            listaContactos.set(j, listaContactos.get(j + 1));
                            listaContactos.set(j + 1, temp);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(this, "No existe archivo de contactos", Toast.LENGTH_LONG).show();
        }

        recyclerViewContacto = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerViewContacto.setLayoutManager(new LinearLayoutManager(this));
        adaptadorContacto = new RecyclerViewAdaptador(obtenerContactos());
        recyclerViewContacto.setAdapter(adaptadorContacto);
        cardView = findViewById(R.id.cardView);
    }

    public List<ContactoModelo> obtenerContactos() {
        List<ContactoModelo> contacto = new ArrayList<>();
        for (ContactoModelo contactoActual : listaContactos) {
            contacto.add(new ContactoModelo(contactoActual.getNombre(), contactoActual.getEmail(), contactoActual.getTelefono()));
        }
        return contacto;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        
        if (id == cardView.getId()) {
            Toast.makeText(this, "si", Toast.LENGTH_SHORT).show();
        }
    }
}