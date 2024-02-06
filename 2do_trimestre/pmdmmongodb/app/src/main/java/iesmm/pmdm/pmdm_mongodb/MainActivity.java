package iesmm.pmdm.pmdm_mongodb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.bson.Document;

import iesmm.pmdm.pmdm_mongodb.dao.DAOImpl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Elementos del layout
    private Button botonGet, botonInsert, botonUpdate, botonDelete, botonGetAll;
    private EditText etId, etNombre, etEdad, etEmail, etTelefono, etTitulo1, etTitulo2;
    private TextView tvNumeroElementos;

    // Objeto de la clase DAOImpl
    private DAOImpl dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asignación de los elementos del layout
        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        etEmail = findViewById(R.id.etEmail);
        etTelefono = findViewById(R.id.etTelefono);
        etTitulo1 = findViewById(R.id.etTitulo1);
        etTitulo2 = findViewById(R.id.etTitulo2);
        tvNumeroElementos = findViewById(R.id.tvNumeroElementos);
        botonGet = findViewById(R.id.botonGet);
        botonInsert = findViewById(R.id.botonInsert);
        botonUpdate = findViewById(R.id.botonUpdate);
        botonDelete = findViewById(R.id.botonDelete);
        botonGetAll = findViewById(R.id.botonGetAll);

        // Agrega el escuchador
        botonGet.setOnClickListener(this);
        botonInsert.setOnClickListener(this);
        botonUpdate.setOnClickListener(this);
        botonDelete.setOnClickListener(this);
        botonGetAll.setOnClickListener(this);

        // Objeto de la clase DAOImpl con el que se accede a los métodos para las operaciones CRUD
        dao = new DAOImpl(this, tvNumeroElementos);
        // Llamada al método para iniciar la conexión con la base de datos
        dao.connectAppService();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        // Controla el botón que se ha pulsado
        if (id == botonGet.getId()) { // Botón "Buscar por ID"
            // Controla que se introduce un ID en el campo del ID
            if (String.valueOf(etId.getText()).equals("")) {
                Toast.makeText(this, "Introduce un ID", Toast.LENGTH_SHORT).show();
            } else {
                dao.obtenerPorId(Integer.parseInt(String.valueOf(etId.getText())));
            }
        } else if (id == botonInsert.getId()) { // Botón "Insertar"
            // Controla que todos los campos tienen contenido
            if (String.valueOf(etId.getText()).equals("") || String.valueOf(etNombre.getText()).equals("") || String.valueOf(etEdad.getText()).equals("") || String.valueOf(etEmail.getText()).equals("")
                    || String.valueOf(etTelefono.getText()).equals("") || String.valueOf(etTitulo1.getText()).equals("") || String.valueOf(etTitulo2.getText()).equals("")) {
                Toast.makeText(this, "Rellena todos los campos para insertar un elemento", Toast.LENGTH_SHORT).show();
            } else {
                // Crea un objeto Document para insertarlo
                Document document = crearDocumento(Integer.parseInt(String.valueOf(etId.getText())), String.valueOf(etNombre.getText()), Integer.parseInt(String.valueOf(etEdad.getText())), String.valueOf(etEmail.getText()), String.valueOf(etTelefono.getText()), String.valueOf(etTitulo1.getText()),String.valueOf(etTitulo2.getText()));
                dao.insertarDocumento(document);
            }
        } else if (id == botonUpdate.getId()) { // Botón "Actualizar"
            // Controla que todos los campos tienen contenido
            if (String.valueOf(etId.getText()).equals("") || String.valueOf(etNombre.getText()).equals("") || String.valueOf(etEdad.getText()).equals("") || String.valueOf(etEmail.getText()).equals("")
                    || String.valueOf(etTelefono.getText()).equals("") || String.valueOf(etTitulo1.getText()).equals("") || String.valueOf(etTitulo2.getText()).equals("")) {
                Toast.makeText(this, "Rellena todos los campos para actualizar un elemento", Toast.LENGTH_SHORT).show();
            } else {
                // Crea un objeto Document para actualizarlo
                Document document = crearDocumento(Integer.parseInt(String.valueOf(etId.getText())), String.valueOf(etNombre.getText()), Integer.parseInt(String.valueOf(etEdad.getText())), String.valueOf(etEmail.getText()), String.valueOf(etTelefono.getText()), String.valueOf(etTitulo1.getText()),String.valueOf(etTitulo2.getText()));
                dao.actualizarDocumento(Integer.parseInt(String.valueOf(etId.getText())), document);
            }
        } else if (id == botonDelete.getId()) { // Botón "Eliminar"
            if (String.valueOf(etId.getText()).equals("")) {
                Toast.makeText(this, "Introduce un ID", Toast.LENGTH_SHORT).show();
            } else {
                dao.eliminarDocumento(Integer.parseInt(String.valueOf(etId.getText())));
            }
        } else if (id == botonGetAll.getId()) { // Botón "Obtener todos los elementos"
            dao.obtenerDocumentos();
        }
    }

    // Método que crea y devuelve un objeto Document a partir de los datos que se le pasan
    public Document crearDocumento(int id, String nombre, int edad, String email, String telefono, String titulo1, String titulo2) {
        Document document = new Document();
        Document titulos = new Document()
                .append("titulo1", titulo1)
                .append("titulo2", titulo2);

        document.append("id", id);
        document.append("nombre", nombre);
        document.append("edad", edad);
        document.append("email", email);
        document.append("telefono", telefono);
        document.append("titulos", titulos);

        return document;
    }
}