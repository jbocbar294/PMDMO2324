package iesmm.pmdm.pmdm_mongodb.dao;

import org.bson.Document;

public interface DAO {
    void connectAppService();
    void initializeMongoDB();
    void obtenerPorId(int id);
    void insertarDocumento(Document document);
    void actualizarDocumento(int id, Document updateData);
    void eliminarDocumento(int id);
    void obtenerDocumentos();
    void cantidadElementos();
}