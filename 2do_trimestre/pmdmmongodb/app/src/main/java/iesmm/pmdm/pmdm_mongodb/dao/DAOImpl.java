package iesmm.pmdm.pmdm_mongodb.dao;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import org.bson.Document;

import java.util.Iterator;

// Librerías para Realm-MongoDB
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
// Fin de librerías para Realm-MongoDB

public class DAOImpl implements DAO{

    private Context context; // Contexto de la aplicación
    private TextView tvNumeroElementos; // TextView que mostrará el total de elementos de la colección

    // Constructor
    public DAOImpl(Context context, TextView tvNumeroElementos) {
        this.context = context;
        this.tvNumeroElementos = tvNumeroElementos;
    }

    // Atributos de la clase
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;
    private User user;
    private App app;
    private final String AppId = "pmdm-imslf"; // Nombre del servicio de la aplicación
    private final String USERNAME = "user@user.com"; // Nombre de usuario
    private final String PASSWORD = "user123"; // Contraseña
    private final String LOGTAG = "MONGODB";

    // Método de conexión para el servicio de la aplicación
    public void connectAppService() {
        // Inicializar Realm
        Realm.init(context);
        // Conexión a la configuración de la aplicación
        app = new App(new AppConfiguration.Builder(AppId).build());
        // Autenticación con email y contraseña
        Credentials credentials = Credentials.emailPassword(USERNAME, PASSWORD);
        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if (result.isSuccess()) {
                    Log.v(LOGTAG, "Acceso usuario");
                    initializeMongoDB();
                } else {
                    Log.v(LOGTAG, "Fallo en login");
                    Toast.makeText(context, "Fallo en login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initializeMongoDB() {
        user = app.currentUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("android"); // Conecta a la base de datos
        mongoCollection = mongoDatabase.getCollection("alumnos"); // Conecta a la colección
        cantidadElementos(); // Actualiza el TextView de la cantidad de elementos
    }

    // Método que obtiene un elemento por su ID
    public void obtenerPorId(int id) {
        // Objeto Document que se usa como filtro
        Document queryFilter = new Document("id", id);
        // findOne: busca un solo elemento, le pasamos queryFilter para que busque el elemento con el ID
        mongoCollection.findOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                // AlertDialog que mostrará los datos
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Elemento con ID: " + id);
                builder.setMessage("Nombre: " + task.get().get("nombre")
                        + "\nEdad: " + task.get().get("edad")
                        + "\nEmail: " + task.get().get("email")
                        + "\nTelefono: " + task.get().get("telefono")
                        + "\nTítulos: " + task.get().get("titulos", Document.class).get("titulo1") + ", "
                        + task.get().get("titulos", Document.class).get("titulo2"));

                builder.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Log.e(LOGTAG, task.getError().toString());
            }
        });
    }

    // Método para insertar un nuevo documento en la colección
    public void insertarDocumento(Document document) {
        // insertOne: inserta un solo elemento, le pasamos el objeto Document.
        mongoCollection.insertOne(document).getAsync(task -> {
            if (task.isSuccess()) {
                Toast.makeText(context, "Elemento insertado correctamente", Toast.LENGTH_SHORT).show();
                cantidadElementos(); // Actualizamos el TextView
            } else {
                Toast.makeText(context, "No se ha podido insertar el elemento", Toast.LENGTH_SHORT).show();
                Log.e(LOGTAG, task.getError().toString());
            }
        });

    }

    // Método que actualiza un elemento por su ID
    public void actualizarDocumento(int id, Document document) {
        Document queryFilter = new Document("id", id); // Filtro para buscar el elemento por el ID
        Document documentoActualizado = new Document("$set", document); // Elemento actualizado
        // updateOne: actualizar un solo elemento: le pasamos el filtro y el elemento actualizado
        mongoCollection.updateOne(queryFilter, documentoActualizado).getAsync(task -> {
            if (task.isSuccess()) {
                Toast.makeText(context, "Elemento actualizado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "No se ha podido actualizar el elemento", Toast.LENGTH_SHORT).show();
                Log.e(LOGTAG, "Error al actualizar el elemento: " + task.getError().toString());
            }
        });
    }

    // Método que elimina un elemento por su ID
    public void eliminarDocumento(int id) {
        Document queryFilter = new Document("id", id); // Filtro para buscar el elemento por el ID
        // deleteOne: elimina un solo elemento: le pasamos el filtro
        mongoCollection.deleteOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                Toast.makeText(context, "Elemento eliminado correctamente", Toast.LENGTH_SHORT).show();
                cantidadElementos();
            } else {
                Toast.makeText(context, "No se ha podido eliminar el elemento", Toast.LENGTH_SHORT).show();
                Log.e(LOGTAG, "Error al eliminar el elemento: " + task.getError().toString());
            }
        });
    }

    // Método que obtiene todos los elementos de la lista
    public void obtenerDocumentos() {
        // Busca todos los elementos y los pasa a un Iterator
        mongoCollection.find().iterator().getAsync(task -> {
            if (task.isSuccess()) {
                String linea = "";
                Iterator<Document> iterator = task.get(); // Iterator con todos los elementos de la colección
                while (iterator.hasNext()) { // Mientras haya otro elemento...
                    Document document = iterator.next(); // Objeto que almacena el elemento
                    // linea almacena los datos de cada elemento, con 2 saltos de linea después de cada elemento
                    linea += "ID: " + document.get("id")
                            + "\nNombre: " + document.get("nombre")
                            + "\nEdad: " + document.get("edad")
                            + "\nEmail" + document.get("email")
                            + "\nTeléfono: " + document.get("telefono")
                            + "\nTítulos:\n  - Título 1: " + document.get("titulos", document.getClass()).get("titulo1")
                            + "\n  - Título 2: " + document.get("titulos", document.getClass()).get("titulo2")
                            + "\n\n";
                }

                // AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Obtener todos los elementos");
                builder.setMessage(linea);

                builder.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Log.e(LOGTAG, "Error al obtener el número de elementos: " + task.getError().toString());
            }
        });
    }

    public void cantidadElementos() {
        mongoCollection.count().getAsync(task -> {
            if (task.isSuccess()) {
                tvNumeroElementos.setText("Elementos: " + task.get());
            } else {
                Log.e(LOGTAG, "Error al obtener el número de elementos: " + task.getError().toString());
            }
        });
    }
}